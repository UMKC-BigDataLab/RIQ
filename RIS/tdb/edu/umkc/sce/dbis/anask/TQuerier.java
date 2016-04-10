package edu.umkc.sce.dbis.anask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.FilenameUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

public class TQuerier {
    public static void main(String[] args) {
        Options options = getProgramOptions();

        // another way to pass args (for testing):
        //args = new String[]{"-results","xml","-index","/Users/anask/Downloads/RIQ/Data10/dir","-candidates","/Users/anask/Downloads/RIQ/Data10/dir/ndF.txt","-query","/Users/anask/Downloads/RIQ/Data10/nq1.q","-t","3"};

        String candidates_file;
        String query_file;
        String optqueries_dir = null;
        String index_dir;
        String format;
        boolean print_count = false;
        int numThreads = 2;

        // Get Input Arguments
        HelpFormatter formatter = new HelpFormatter();
        if (args.length <= 1) {
            formatter.printHelp("querier", options);
            return;
        } else {
            CommandLineParser parser = new PosixParser();
            try {
                CommandLine line = parser.parse(options, args);

                query_file = line.getOptionValue("query");

                if (line.hasOption("optqueries")) {
                    optqueries_dir = line.getOptionValue("optqueries");
                }

                candidates_file = line.getOptionValue("candidates");
                index_dir = line.getOptionValue("index");

                format = line.getOptionValue("results").toUpperCase();

                if (!format.equals("TEXT") && !format.equals("CSV")
                        && !format.equals("XML") && !format.equals("TSV")) {

                    System.err.println("warn: unrecognized format: " + format);
                }

                if (line.hasOption("c"))
                    print_count = true;

                if (line.hasOption("h")) {
                    formatter.printHelp("querier", options);
                    return;
                }
                if(line.hasOption("t")){
                    numThreads=Integer.valueOf(line.getOptionValue("t"));
                }
            } catch (ParseException exp) {
                System.err
                        .println("error: parsing failed. " + exp.getMessage());
                formatter.printHelp("querier", options);
                return;

            }
        }

        File candidates = new File(candidates_file);

        ArrayList<File> tdb_list = null;
        try {
            tdb_list = readFile(candidates, index_dir);
        } catch (IOException exp) {
            System.err.println("error: IOException caught");
            return;
        }

        System.err.println("FILES FOUND: " + tdb_list.size());

        // Create tdb index-file queue
        BlockingQueue<File> indexQueue = new ArrayBlockingQueue(tdb_list.size(), false, tdb_list);

        // Store raw query
        Query query = QueryFactory.read(query_file);

        // ********** Create Candidate to Query Map **********
        HashMap<String, String> cand2query = new HashMap<String, String>();

        if (optqueries_dir != null) {
            System.err.println("READING optqueries in dir: "
                    + FilenameUtils.getName(optqueries_dir));
            File optqueries = new File(optqueries_dir);
            File[] optqueries_list = optqueries.listFiles();
            if (tdb_list.size() != optqueries_list.length) {
                System.err.println("warn: number of candidates and "
                        + "number of rewritten queries differ");
                // return;
            }
            for (File rqmod : optqueries_list) {
                if (rqmod.isFile()) {
                    String path = rqmod.getAbsolutePath();
                    String file_name = FilenameUtils.getName(path);
                    // System.err.println(file_name);
                    String[] rqmod_parts = file_name.split("\\.");
                    // XXX: format is datahub.g.q4.1001000001000000.rqmod
                    int cand_idx = rqmod_parts.length - 2;
                    // System.err.println(rqmod_parts[cand_idx]);
                    // TODO: store only file_name?
                    cand2query.put(rqmod_parts[cand_idx], path);
                } else {
                    System.err.println("warn: not a file");
                }
            }

            // tdb_list = optqueries_dir;
        } else {
            System.err.println("READING single query file: "
                    + FilenameUtils.getName(query_file));
        }
        // ***************************************************

        SynchronizedResultSet results = new SynchronizedResultSet(print_count);


        System.err.println("QUERYING INDICES ..");

        // Spawn threads and start processing
        ArrayList<ExecThread> threads = new ArrayList<ExecThread>(numThreads);
        for (int i=0;i<numThreads; i++){
            ExecThread t = new ExecThread( "T"+i, indexQueue, cand2query, optqueries_dir, query_file, query,results);
            t.start();
            threads.add(t);

        }
        for (int j=0;j<numThreads; j++){
            threads.get(j).waitTofinish();
        }

        //long startTime = System.currentTimeMillis();
        long startTime = System.nanoTime();
        results.printResults(format);
        //long endTime = System.currentTimeMillis();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.err.println("printResults (ms): " + duration);
    }

    private static Options getProgramOptions() {
        Options options = new Options();

        Option help = new Option("h", "print this message");
        // Option m = new Option("m", false,
        // "output query matches for each index");
        // Option f = new Option("f", false,"format output as a text table");
        Option count = new Option("c", false,
                "count the number of query matches");
        Option index = OptionBuilder.withArgName("dir").hasArg(true)
                .withDescription("candidate indices are stored in this dir")
                .isRequired(true).create("index");
        Option candidates = OptionBuilder.withArgName("file").hasArg(true)
                .withDescription("list of candidates").isRequired(true)
                .create("candidates");
        Option query = OptionBuilder.withArgName("file").hasArg(true)
                .withDescription("SPARQL query").isRequired(true)
                .create("query");
        Option opt_queries = OptionBuilder
                .withArgName("dir")
                .hasArg(true)
                .withDescription(
                        "dir with optimized SPARQL queries (one per candidate)")
                .isRequired(false).create("optqueries");
        Option results = OptionBuilder
                .withArgName("format")
                .hasArg(true)
                .withDescription(
                        "Results format: text (default), XML, CSV, TSV")
                .isRequired(false).create("results");
        Option threads = OptionBuilder
                .withArgName("num_threads")
                .hasArg(true)
                .withDescription(
                        "Number of threads to spwan (defualt is 2)")
                .isRequired(false).create("t");

        options.addOption(help);
        // options.addOption(m);
        // options.addOption(f);
        options.addOption(count);
        options.addOption(index);
        options.addOption(candidates);
        options.addOption(query);
        options.addOption(opt_queries);
        options.addOption(results);
        options.addOption(threads);
        return options;
    }

    private static ArrayList<File> readFile(File fin, String indDir)
            throws IOException {
        FileInputStream fis = new FileInputStream(fin);
        ArrayList<File> files = new ArrayList<File>();

        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            String path = indDir + "/" + line;
            // System.err.println(path);
            files.add(new File(path));
        }

        br.close();

        return files;
    }
}
