// Author: Vasil Slavov, DBIS Lab, UMKC, 2013

#include <algorithm>
#include <assert.h>
#include <fstream>
#include <iostream>
#include <limits>
#include <sstream>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>

#include <google/sparse_hash_map>
//#include <unordered_map>

#include <raptor2.h>

#include "utils.h"

// TODO: make arg?
#define MAX_URI_LEN 128
#define VEC_CAPACITY 300000
//#define VEC_CAPACITY 10000

using namespace std;

#define _DEBUG_

extern char *__progname;

// poly of degree 31?
POLY irrpoly = 1675850375;
int sig_version = 2;
int SPOLISTS = 7;
bool pattern_list[8] = { false };
bool verbose = false;
bool disable_write = false;
string format = "ntriples";
string action = "both";

int max_sizet = 0;
int graph_triples = 0;
int total_triples = 0;

typedef vector<vector<POLY> > polysig;
// 7 lists: spo, sp, so, po, s, p, o
polysig sig(SPOLISTS);

// TODO: represent triple ids differently?
typedef vector<POLY> TripleIds;
typedef vector<TripleIds> GraphIds;
GraphIds graph_ids;

// URI to ID
typedef google::sparse_hash_map<POLY, long int> UriMap;
//typedef google::sparse_hash_map<string, long int> UriMap;
//typedef unordered_map<string, unsigned long> UriMap;
UriMap uri2id;
long int nuri = 0;
long int longest_uri_len = 0;
long int nshortened_uri = 0;

// parser stuff
raptor_world *world = NULL;
raptor_parser *rdf_parser = NULL;
unsigned char *uri_string;
raptor_uri *uri, *base_uri;

void set_pattern(string, bool[]);
void usage(void);

bool process_graph(string, string, int, unsigned long long, int &);
static void process_triple(void *, raptor_statement *);

void free_raptor(void);
void set_raptor(string);
void clear_lists(void);
void reserve_lists(void);
void show_list(vector<POLY>);
void write_list(FILE *, vector<POLY>);
void write_sig(string, int, unsigned long long);
void write_ids(string);

long int add_to_map(unsigned char *uri);
void get_max_uri_len(unsigned char *uri);

int
main(int argc, char *argv[])
{
    int ch;
    int iflag, oflag, Gflag, fflag, Aflag;
    string infile, outfile, pattern;

    iflag = oflag = Gflag = fflag = Aflag = 0;
    pattern = "0";

    while ((ch = getopt(argc, argv, "A:f:Gi:o:P:p:hWv")) != -1)
        switch(ch) {
        case 'A':
            Aflag = 1;
            action = optarg;
            break;
        case 'f':
            fflag = 1;
            format = optarg;
            break;
        case 'G':
            Gflag = 1;
            break;
        case 'i':
            iflag = 1;
            infile = optarg;
            break;
        case 'o':
            oflag = 1;
            outfile = optarg;
            break;
        case 'P':
            irrpoly = strtol(optarg, NULL, 10);
            break;
        case 'p':
            pattern = optarg;
            break;
        case 'W':
            disable_write = true;
            break;
        case 'v':
            verbose = true;
            break;
        case 'h':
        case '?':
        default:
            usage();
            break;
        }
    argc -= optind;
    argv += optind;

    if (iflag == 0 || oflag == 0) usage();

    if (fflag) {
      if (format != "ntriples" && format != "nquads")
        usage();
    }

    if (Aflag) {
      if (action != "rdf2ids" && action != "ids2pvs" && action != "both")
        usage();
    }

    // pattern 0 (all) by default
    if (pattern == "0") {
        for (int i = 1; i <= 7; i++)
            pattern_list[i] = true;
    } else {
        set_pattern(pattern, pattern_list);
    }

    clear_lists();
    reserve_lists();

    int nparserr = 0;
    int graph_id = 0;
    int cur_size = 0;
    unsigned long long seek_loc = 0;
    unsigned long long ullmax = numeric_limits<unsigned long long>::max();
    long int limax = numeric_limits<long int>::max();
    cout << "format/parser: " << format << endl;
    cout << "action: " << action << endl;
    cout << "unsigned long long max: " << ullmax << endl;
    cout << "long int max: " << limax  << endl;
    cout << "sizeof(int): " << sizeof(int) << endl;
    cout << "sizeof(POLY): " << sizeof(POLY) << endl;

    // process as one giant graph
    int rc;
    if (Gflag == 1) {
        set_raptor(infile);
        rc = raptor_parser_parse_file(rdf_parser, uri, base_uri);
        if (rc) {
            cout << "parse_file: " << rc << endl;
        }

        if (!disable_write)
            write_sig(outfile, graph_id, seek_loc);

        cout << "List size: " << sig[0].size() << " * 7 = " << cur_size << endl;

        free_raptor();

        return 0;
    }

    // process as multiple graphs separated by a new line
    ifstream infilestream;
    infilestream.open(infile.c_str());
    if (!infilestream.is_open()) {
        cout << "Can't open " << infile << endl;
        exit(2);
    }

    int graph_skip = 0;
    double begin_time, end_time;
    string line;
    stringstream linestream("");
    bool ret;
    set_raptor(infile);
    while (getline(infilestream, line)) {
        // end of graph
        if (line.empty()) {
            ret = process_graph(outfile, linestream.str(), graph_id, seek_loc, nparserr);
            if (ret)
                ++graph_id;
            else
                ++graph_skip;

            //linestream.str("");
            linestream.str(std::string());
            linestream.clear();
            clear_lists();
            graph_triples = 0;
            seek_loc = infilestream.tellg();
            free_raptor();
            set_raptor(infile);
            continue;
        }
        linestream << line << "\n";
    }

    free_raptor();
    set_raptor(infile);
    // last graph (if last line is not blank)
    if (!linestream.str().empty()) {
        ret = process_graph(outfile, linestream.str(), graph_id, seek_loc, nparserr);
        if (ret)
            ++graph_id;
        else
            ++graph_skip;
    }

    infilestream.close();
    clear_lists();
    free_raptor();

    cout << endl << "Parsing errors: " << nparserr << endl;
    cout << "Longest URI length: " << longest_uri_len << endl;
    cout << "# of shortened URIs: " << nshortened_uri << endl;
    cout << "Skipped graphs: " << graph_skip << endl;
    cout << "Written graphs: " << graph_id << endl;
    cout << "Avg graph size: " << total_triples / graph_id << " triples" << endl;
    cout << "Max graph size: " << max_sizet << " triples" << endl;
    cout << "Total size: " << total_triples << " triples" << endl;
    cout << "Total URIs/literals: " << nuri << endl;
    cout << "uri2id map size: " << uri2id.size() << endl;

    return 0;
}

void
clear_lists(void)
{
    for (int i = 0; i < sig.size(); i++) {
        sig[i].clear();
    }
}

void
reserve_lists(void)
{
    for (int i = 0; i < sig.size(); i++) {
        sig[i].reserve(VEC_CAPACITY);
    }
}

void
set_raptor(string infile)
{
    world = raptor_new_world();
    rdf_parser = raptor_new_parser(world, format.c_str());
    raptor_parser_set_statement_handler(rdf_parser, NULL, process_triple);
    uri_string = raptor_uri_filename_to_uri_string(infile.c_str());
    uri = raptor_new_uri(world, uri_string);
    base_uri = raptor_uri_copy(uri);
}

void
free_raptor(void)
{
    raptor_free_parser(rdf_parser);
    raptor_free_uri(base_uri);
    raptor_free_uri(uri);
    raptor_free_memory(uri_string);
    raptor_free_world(world);

}

void
show_list(vector<POLY> vec)
{
    for (int i = 0; i < vec.size(); i++) {
        cout << vec[i] << endl;
    }
}

void
set_pattern(string pattern, bool pattern_list[])
{
    int count = 0;
    while (count < pattern.length()) {
        int digit = pattern[count] - '0';
        pattern_list[digit] = true;
        ++count;
    }
}

bool
process_graph(string outfile, string graph, int graph_id, unsigned long long seek_loc, int &nparserr)
{
    int rc;

    // delete ids of previous graph
    graph_ids.clear();

    if (sig[0].size() <= VEC_CAPACITY) {
        rc = raptor_parser_parse_start(rdf_parser, base_uri);
        if (rc) {
            cout << "parse_start: " << rc << endl;
        }

        //cout << graph << endl;
        rc = raptor_parser_parse_chunk(rdf_parser, graph.c_str(), graph.length(), 1);
        if (rc) {
            cout << "parse_chunk: " << rc << endl;
            ++nparserr;
            return false;
        }

        // empty graph
        if (sig[0].empty() && action != "rdf2ids") {
            cout << "graph " << graph_id << " is empty" << endl;
            return false;
        }

        if (graph_triples > max_sizet) {
            max_sizet = graph_triples;
        }

        if (disable_write) {
            cout << "graph id: " << graph_id
                 << " triples: " << graph_triples << endl;
        } else {
            cout << "writing graph id: " << graph_id
                 << " triples: " << graph_triples << endl;
            if (action == "rdf2ids")
                write_ids(outfile);
            else
                write_sig(outfile, graph_id, seek_loc);
        }
    } else {
        cout << "skipping graph id " << graph_id << " with list size " << sig[0].size() << endl;
        return false;
    }

    return true;
}

static void
process_triple(void *user_data, raptor_statement *triple)
{
    unsigned char *s = raptor_term_to_string(triple->subject);
    unsigned char *p = raptor_term_to_string(triple->predicate);
    unsigned char *o = raptor_term_to_string(triple->object);

// TODO: only in DEBUG mode?
#if 1
    get_max_uri_len(s);
    get_max_uri_len(p);
    get_max_uri_len(o);
#endif

#ifdef _USEMAP_
    long int sid = add_to_map(s);
    long int pid = add_to_map(p);
    long int oid = add_to_map(o);
#elif _USEHASHIRRPOLY_
    POLY spoly = hashIrrPoly(s, irrpoly);
    POLY ppoly = hashIrrPoly(p, irrpoly);
    POLY opoly = hashIrrPoly(o, irrpoly);
#endif

    raptor_free_statement(triple);
    ++total_triples;
    ++graph_triples;

    if (action == "rdf2ids") {
        TripleIds tids = {spoly, ppoly, opoly};
        graph_ids.push_back(tids);
        free(s);
        free(p);
        free(o);
        return;
    }

    stringstream ss("");
    string store = "";
    POLY pol = 0;

    // XXX: format of string which is hashed is VERY IMPORTANT
    // (has to be the same during query sig. gen.)
    int i = 0;
    // ( s, p, o )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << sid << " , " << pid << " , " << oid << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << spoly << " , " << ppoly << " , " << opoly << " )";
#else
        ss << "( " << s << " , " << p << " , " << o << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    // ( s, p, ? )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << sid << " , " << pid << " , " << "?" << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << spoly << " , " << ppoly << " , " << "?" << " )";
#else
        ss << "( " << s << " , " << p << " , " << "?" << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    // ( s, ?, o )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << sid << " , " << "?" << " , " << oid << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << spoly << " , " << "?" << " , " << opoly << " )";
#else
        ss << "( " << s << " , " << "?" << " , " << o << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    // ( ?, p, o )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << "?" << " , " << pid << " , " << oid << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << "?" << " , " << ppoly << " , " << opoly << " )";
#else
        ss << "( " << "?" << " , " << p << " , " << o << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    // ( s, ?, ? )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << sid << " , " << "?" << " , " << "?" << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << spoly << " , " << "?" << " , " << "?" << " )";
#else
        ss << "( " << s << " , " << "?" << " , " << "?" << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    // ( ?, p, ? )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << "?" << " , " << pid << " , " << "?" << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << "?" << " , " << ppoly << " , " << "?" << " )";
#else
        ss << "( " << "?" << " , " << p << " , " << "?" << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    // ( ?, ?, o )
    if (pattern_list[i+1]) {
        ss.str("");
#ifdef _USEMAP_
        ss << "( " << "?" << " , " << "?" << " , " << oid << " )";
#elif _USEHASHIRRPOLY_
        ss << "( " << "?" << " , " << "?" << " , " << opoly << " )";
#else
        ss << "( " << "?" << " , " << "?" << " , " << o << " )";
#endif
        store = ss.str();
        if (verbose)
            cout << store << endl;
        pol = hashIrrPoly(store.c_str(), irrpoly);
        if (verbose)
            cout << pol << endl;
        sig[i++].push_back(pol);
    }

    free(s);
    free(p);
    free(o);
}

void
get_max_uri_len(unsigned char *uri)
{
    long int uri_len = strlen(uri);

    if (uri_len > longest_uri_len)
        longest_uri_len = uri_len;

    if (MAX_URI_LEN < uri_len)
        ++nshortened_uri;
}

long int
add_to_map(unsigned char *uri)
{
    string uri_str((char *)uri, MAX_URI_LEN);
    //string uri_str(uri);

    //if (MAX_URI_LEN < strlen(uri)) {
    //    cout << "shortened uri: " << uri_str << endl;
    //}

    POLY pol = 0;
    pol = hashIrrPoly(uri_str.c_str(), irrpoly);

    UriMap::iterator itr = uri2id.find(pol);
    if (itr != uri2id.end()) {
        return itr->second;
    } else {
        uri2id[pol] = nuri;
        return nuri++;
    }
}

void
write_list(FILE *fp, vector<POLY> vec)
{
    int size = vec.size();

    for (int i = 0; i < size; i++) {
        fwrite(&vec[i], sizeof(POLY), 1, fp);
    }
}

// format:
// <sig ver> <graph id> <list size> <spo POLY list>...
void
write_sig(string outfile, int graph_id, unsigned long long seek_loc)
{
    if (sig[0].empty()) {
        cout << "nothing to write" << endl;
        return;
    }

    string datfile = outfile + ".DAT";
    string metafile = outfile + ".META";

    // DAT (sig) file
    FILE *datfp;
    datfp = fopen(datfile.c_str(), "a+");
    if (datfp == NULL) {
        cout << "Can't append to DAT file" << endl;
        exit(2);
    }

    for (int i = 0; i < SPOLISTS; i++) {
        sort(sig[i].begin(), sig[i].end());
    }

    fwrite(&sig_version, sizeof(int), 1, datfp);
    fwrite(&graph_id, sizeof(int), 1, datfp);
    // length of a list, not whole sig!
    int len = sig[0].size();
    fwrite(&len, sizeof(int), 1, datfp);

    for (int i = 0; i < SPOLISTS; i++) {
        write_list(datfp, sig[i]);
    }

    fclose(datfp);

    // META (offset) file
    ofstream ofs(metafile.c_str(), ios::app);
    if (!ofs.is_open()) {
        cout << "Can't open META file" << endl;
        exit(2);
    }

    assert(seek_loc >= 0);
    ofs << seek_loc << endl;
    ofs.close();
}

void
write_ids(string outfile)
{
    string idfile(outfile + ".ID");

    ofstream ofs(idfile.c_str(), ios::app);
    if (!ofs.is_open()) {
        cout << "Can't open ID file" << endl;
        exit(2);
    }

    for (int i = 0; i != graph_ids.size(); ++i) {
        ofs << graph_ids[i][0] << " "
            << graph_ids[i][1] << " "
            << graph_ids[i][2] << endl;
    }

    // separation b/w graphs
    ofs << endl;

    ofs.close();
}

void
usage(void)
{
    cout << "Usage: " << __progname << " [-h] [options...]\n\n";

    cout << "OPTIONS:\n"
    << "\t-A\taction: rdf2ids | ids2pvs | both (default both)\n\n"

    << "\t-i\t<input RDF file>\n"
    << "\t-f\tformat parser: ntriples | nquads (default: ntriples)\n\n"

    << "\t-o\t<output files prefix>\n\n"

    << "\t-P\t<irreducible POLY>\n\n"
    << "\t-p\t<pattern> (default 0)\n"
    << "\t-G\tprocess as one giant graph\n\n"
    << "\t-W\tdon't write PVs to disk\n\n"
    << "\t-h\tprint help/usage\n"
    << "\t-v\tverbose\n\n"

    << "PATTERNS:\n"
    << "\t0\tALL (default)\n"
    << "\t1\ts,p,o\n"
    << "\t2\ts,p,?\n"
    << "\t3\ts,?,o\n"
    << "\t4\t?,p,o\n"
    << "\t5\ts,?,?\n"
    << "\t6\t?,p,?\n"
    << "\t7\t?,?,o\n";
    exit(0);
}
