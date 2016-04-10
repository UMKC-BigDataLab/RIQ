#!/usr/bin/env python3

'''
Run RIQ to process and time SPARQL queries
'''

# TODO: capture run times in Python too?

# from __future__ import print_function
import argparse
from configparser import ConfigParser, ExtendedInterpolation
import glob
import json
import os
import re
import subprocess
import sys

# local
from utils import *


def main():
    # process arguments
    parser = argparse.ArgumentParser(
        description='Run RIQ to process and time SPARQL queries')
    parser.add_argument('-C', '--config', required=True, help='config file')
    parser.add_argument('-c', '--cache', required=False, default='none',
                        choices=('cold', 'warm', 'none'),
                        help='cache setting (default: none)')
    parser.add_argument('-D', '--dontstartvrtdb', required=False,
                        action='store_true',
                        help="don't start Virtuoso instances")
    parser.add_argument('-f', '--format', required=False, default='csv',
                        choices=('csv', 'json', 'text', 'xml'),
                        help='default results format (default: csv)')
    parser.add_argument('-l', '--candlog', required=False,
                        help='RIQ candidate log')
    parser.add_argument('-O', '--optimize', required=False,
                        action='store_true',
                        help="use RIQ's query optimizer")
    parser.add_argument('-o', '--order', required=False, default='min',
                        choices=('min', 'max'),
                        help='query SPO order'
                        ' (default: min-sized list first)')
    parser.add_argument('-p', '--phases', required=False, default='all',
                        choices=('filter', 'refine', 'all'),
                        help='run filter only or all (filter and refine)'
                        ' (default: all)')
    parser.add_argument('-q', '--query', required=True,
                        help='SPARQL query file')
    parser.add_argument('-V', '--vrtquery', required=False,
                        help='Virtuoso SPARQL query file')
    parser.add_argument('-r', '--repeat', required=False, type=int, default=1,
                        help='run N times and calc avg (default: 1)')
    parser.add_argument('-u', '--using', required=False, default='tdb2',
                        choices=('rdf3x', 'tdb', 'tdb2', 'vrt'),
                        help='application used for refinement phase'
                        ' (default: tdb2)')
    parser.add_argument('-v', '--verbose', required=False,
                        action='store_true',
                        help='Print JSON object results')
    args = parser.parse_args()
    # print(args)

    if args.cache != 'none':
        if not os.geteuid() == 0:
            print('error: script must be run as root for this cache setting')
            return 1

        # getuid() doesn't work for detecting sudo
        #if os.getenv('SUDO_USER'):
        #    print('error: be root or use su, not sudo')
        #    return 1

    # TODO: verify query file exists

    # process config file
    config_path = args.config
    config = ConfigParser(interpolation=ExtendedInterpolation())
    config.read(config_path)
    # print_config(config)

    if not verify_config(config):
        print('error: invalid config')
        return 1

    log_pre = build_log_pre(config, args)

    if args.optimize:
        # set up dir for rewritten queries
        rqmods_dir = '.'.join([log_pre, args.cache, 'filter', 'rqmod'])
        check_rewritten_queries(rqmods_dir)
        query_cfg = config['Query']
        query_cfg['rqmods_dir'] = rqmods_dir

    # run filter phase
    if args.phases in ['filter', 'all']:
        filter_logs = repeat(call_riq, config, args, log_pre)
        if not filter_logs:
            print('error: no filter logs produced')
            return 1

        filter_results_d = {}
        print('calculating filtering time...')
        get_filter_results(args.cache, filter_logs, filter_results_d)

        # doesn't matter which log, candidates are the same
        cand_log, ncandidates = get_candidates(args, log_pre, filter_logs[0])
        filter_results_d['candidates'] = ncandidates
        filter_results_d['optimize'] = args.optimize
        filter_results_d['spo_order'] = args.order
        filter_results_d['repeat'] = args.repeat
        filter_results_d['cache'] = args.cache
        query_file = os.path.basename(args.query)
        filter_results_d['query'] = query_file

        print('writing filter json file...')
        filter_json = '.'.join([log_pre, args.cache, 'filter', 'json'])
        write_json(filter_json, filter_results_d, args.verbose)

        print('filtering phase completed')

    if args.phases == 'filter':
        return 0

    if args.phases == 'refine':
        if not args.candlog:
            print('candidate log (-l) argument missing')
            return 1
        else:
            cand_log = args.candlog

    # run refinement phase
    if args.using == 'tdb2':
        refine_logs = repeat(call_tdb2, config, args, log_pre, cand_log)
    elif args.using == 'vrt':
        if args.dontstartvrtdb:
            print('not starting Virtuoso instances')
        else:
            vrtdb_log, vrt_started = call_vrtdb(config, args, log_pre, cand_log)
            print('vrt_started:{0}'.format(vrt_started))
        # TODO: doesn't return status
        #if not vrt_started:
        #    print("can't start Virtuoso instances")
        #    return 1
        refine_logs = repeat(call_vrt, config, args, log_pre, cand_log)
        #return 0
    else:
        print('not implemented')
        return 1

    if not refine_logs:
        print('error: no refine logs produced')
        return 1

    print('calculating refinement time...')
    refine_results_d = {}

    if args.using == 'tdb2':
        get_jena_refine_results(args.cache, refine_logs, refine_results_d)
        tdb_cfg = config['TDB']
        refine_results_d['threads'] = tdb_cfg['nthreads']
        refine_results_d['java_args'] = tdb_cfg['java_args']
    elif args.using == 'vrt':
        get_vrt_refine_results(args.cache, refine_logs, refine_results_d)
        vrt_cfg = config['Virtuoso']
        refine_results_d['threads'] = vrt_cfg['nthreads']
        #refine_results_d['java_args'] = vrt_cfg['java_args']

    refine_results_d['refine_using'] = args.using
    refine_results_d['results_format'] = args.format
    refine_results_d['repeat'] = args.repeat
    refine_results_d['cache'] = args.cache
    query_file = os.path.basename(args.query)
    refine_results_d['query'] = query_file
    nmatched_groups = refine_results_d.get('matched_groups')

    ncandidates = count_lines2(cand_log)
    #ncandidates = filter_results_d.get('candidates')

    refine_results_d['precision'] = nmatched_groups / ncandidates

    print('writing refine json file...')
    refine_json = '.'.join([log_pre, args.cache, 'refine', 'json'])
    write_json(refine_json, refine_results_d, args.verbose)

    print('refinement phase completed')

    # in Python 3, use list()
    # all_results_d = dict(list(filter_results_d.items()) +
    #                      list(refine_results_d.items()))

    all_results_d = {}
    if args.phases in ['filter', 'all']:
        all_results_d['filter'] = filter_results_d
    all_results_d['refine'] = refine_results_d

    print('writing all json file...')
    all_json = '.'.join([log_pre, args.cache, 'all', 'json'])
    write_json(all_json, all_results_d, args.verbose)

    print ('Done.')
    return 0


def get_candidates(args, log_pre, riq_log):
    '''Extract candidates and output them to a log file'''
    # TODO: change riq's reporting of candidates
    if args.optimize:
        pattern = '^candidate GRAPHID:'
    else:
        pattern = '^All lists matched'

    candidates = extract_pattern(riq_log, pattern, False)

    # print("# of candidates: {0}".format(len(candidates)))
    # print("candidates: {0}".format(str(candidates)))

    set_candidates = set(candidates)

    cand_log = '.'.join([log_pre, args.cache, 'filter', 'candidates'])
    with open(cand_log, 'w') as f:
        for c in set_candidates:
            print("{0}".format(c), file=f)

    return cand_log, len(set_candidates)


# TODO: combine with get_refine
def get_filter_results(cache, logs, results_d):
    '''Extract and calculate filter results'''
    total_major_pf = 0
    total_minor_pf = 0
    total_t = 0
    filter_t = []
    for i, f in enumerate(logs, 1):
        log_pre = f.rsplit('.', 1)[0]
        # print('log_pre: {0}'.format(log_pre))
        timev_log = '.'.join([log_pre, 'timev'])
        total_major_pf += int(extract_pattern(timev_log, 'Major'))
        total_minor_pf += int(extract_pattern(timev_log, 'Minor'))
        query_t = float(extract_pattern(f, '^Total query time'))
        total_t += query_t
        filter_t.append(query_t)

    results_d['avg_filter_t'] = total_t / i
    results_d['filter_t'] = filter_t
    results_d['avg_major_pf'] = total_major_pf / i
    results_d['avg_minor_pf'] = total_minor_pf / i


def get_vrt_refine_results(cache, logs, results_d):
    '''Extract and calculate refine results'''
    nresults = extract_pattern(logs[0], 'TOTAL MATCHES FOUND',
                               unique=True, nonempty=False)
    if not nresults:
        nresults = 'n/a'
    # print(nresults)

    #matches_p_cand = extract_pattern(logs[0], 'MATCHES FOUND IN', False)
    matches_p_cand = extract_pattern(logs[0], 'MATCHES FOUND IN', False, False)
    nmatched_groups = 0
    for n in matches_p_cand:
        if int(n) != 0:
            nmatched_groups += 1

    results_d['results'] = nresults
    results_d['matched_groups'] = nmatched_groups

    total_major_pf = 0
    total_minor_pf = 0
    total_t = 0
    total_vrt_t = 0
    timev_t = []
    vrt_t = []
    for i, f in enumerate(logs, 1):
        log_pre = f.rsplit('.', 1)[0]
        # print('log_pre: {0}'.format(log_pre))
        timev_log = '.'.join([log_pre, 'timev'])
        total_major_pf += int(extract_pattern(timev_log, 'Major'))
        total_minor_pf += int(extract_pattern(timev_log, 'Minor'))
        sec = hms2sec(extract_pattern(timev_log, 'Elapsed'))
        total_t += sec
        timev_t.append(sec)
        vrt_log = '.'.join([log_pre, 'log'])
        # in ms
        vrt_sec = int(extract_pattern(vrt_log, 'Total Time')) / 1000
        total_vrt_t += vrt_sec
        vrt_t.append(vrt_sec)

    results_d['avg_refine_t'] = total_t / i
    results_d['refine_t'] = timev_t
    results_d['avg_major_pf'] = total_major_pf / i
    results_d['avg_minor_pf'] = total_minor_pf / i
    results_d['total_vrt_t'] = total_vrt_t / i
    results_d['vrt_t'] = vrt_t


def get_jena_refine_results(cache, logs, results_d):
    '''Extract and calculate refine results'''
    nresults = extract_pattern(logs[0], 'TOTAL MATCHES FOUND',
                               unique=True, nonempty=False)
    if not nresults:
        nresults = 'n/a'
    # print(nresults)

    #matches_p_cand = extract_pattern(logs[0], 'MATCHES FOUND IN', False)
    matches_p_cand = extract_pattern(logs[0], 'MATCHES FOUND IN', False, False)
    nmatched_groups = 0
    for n in matches_p_cand:
        if int(n) != 0:
            nmatched_groups += 1

    results_d['results'] = nresults
    results_d['matched_groups'] = nmatched_groups

    total_major_pf = 0
    total_minor_pf = 0
    total_t = 0
    timev_t = []
    for i, f in enumerate(logs, 1):
        log_pre = f.rsplit('.', 1)[0]
        # print('log_pre: {0}'.format(log_pre))
        timev_log = '.'.join([log_pre, 'timev'])
        total_major_pf += int(extract_pattern(timev_log, 'Major'))
        total_minor_pf += int(extract_pattern(timev_log, 'Minor'))
        sec = hms2sec(extract_pattern(timev_log, 'Elapsed'))
        total_t += sec
        timev_t.append(sec)

    results_d['avg_refine_t'] = total_t / i
    results_d['refine_t'] = timev_t
    results_d['avg_major_pf'] = total_major_pf / i
    results_d['avg_minor_pf'] = total_minor_pf / i


def repeat(call_prog, config, args, log_pre, cand_log=None):
    '''Repeat phase and clear cache in correct order'''
    logs = []
    if args.cache == 'warm':
        clear_cache()
        # don't append this log
        if cand_log:
            call_prog('cold4warm', config, args, log_pre, 0, cand_log)
        else:
            call_prog('cold4warm', config, args, log_pre, 0)

    for i in range(1, args.repeat + 1):
        if args.cache == 'cold':
            clear_cache()

        # XXX: better way?
        if cand_log:
            logs.append(call_prog(args.cache, config, args, log_pre, i,
                        cand_log))
        else:
            logs.append(call_prog(args.cache, config, args, log_pre, i))

    return logs


# don't use
def call_bin(bin_str, cache, config, args, log_pre, n, cand_log=None):
    '''Call binary'''

    methods = {'riq': build_riq_cmd,
               'jena': build_jena_cmd,
               'vrt': build_vrt_cmd,
               'vrtdb': build_vrtdb_cmd}

    if bin_str == 'riq':
        log_pre = '.'.join([log_pre, cache, 'filter'])
        cmd = methods[bin_str](config, args, log_pre, n)
    elif bin_str in methods:
        log_pre = '.'.join([log_pre, cache, args.using, args.format])
        cmd = methods[bin_str](config, args, log_pre, n, cand_log)
    else:
        print("method {0} not implemented".format(bin_str))
        # TODO: raise exception?
        sys.exit(1)

    print("log_pre: {0}".format(log_pre))
    print("cmd: {0}".format(' '.join(cmd)))

    log = '.'.join([log_pre, str(n), 'log'])
    results = '.'.join([log_pre, str(n), 'results'])
    print(log)

    if bin_str == 'riq':
        del_bloom_filters(config)
        print('{n}/{total}: identifying candidates ({cache})'.
            format(n=n, total=args.repeat, cache=cache))
    elif bin_str == 'jena':
        print('{n}/{total}: running Jena TDB API on all candidates ({cache})'.
            format(n=n, total=args.repeat, cache=cache))
    elif bin_str == 'vrt':
        print('{n}/{total}: running Virtuoso API on all candidates ({cache})'.
            format(n=n, total=args.repeat, cache=cache))
    elif bin_str == 'vrtdb':
        print('running Virtuoso DB starter')

    with open(log, 'w') as err, open(results, 'w') as out:
        proc = subprocess.Popen(cmd, shell=False, stdout=out, stderr=err)
        result = proc.communicate()

    return log


def build_vrtdb_cmd(config, args, log_pre, cand_log):
    '''Build Virtuoso DB start command line call string'''
    vrt_cfg = config['Virtuoso']
    query_cfg = config['Query']

    vrt_cmd = [vrt_cfg['time'], '-v', '-o',
                '.'.join([log_pre, 'timev']),
                vrt_cfg['java'],
                vrt_cfg['java_args'],
                '-jar', vrt_cfg['vrtdbstartjar'],
                '-index', vrt_cfg['index'],
                '-candidates', cand_log]

    return vrt_cmd


def build_vrt_cmd(config, args, log_pre, n, cand_log):
    '''Build Virtuoso command line call string'''
    vrt_cfg = config['Virtuoso']
    query_cfg = config['Query']

    vrt_cmd = [vrt_cfg['time'], '-v', '-o',
                '.'.join([log_pre, str(n), 'timev']),
                vrt_cfg['java'],
                vrt_cfg['java_args'],
                '-jar', vrt_cfg['vrtjar'],
                vrt_cfg['vrtjar_args'],
                '-results', args.format,
                '-index', vrt_cfg['index'],
                '-candidates', cand_log]

    if args.vrtquery:
        print('using query modified for Virtuoso')
        vrt_cmd.extend(['-query', args.vrtquery])
    else:
        print('using original query')
        vrt_cmd.extend(['-query', args.query])

    if vrt_cfg.getint('nthreads') > 1:
        print('using multi-threaded Virtuoso TDB API')
        vrt_cmd.extend(['-t', vrt_cfg['nthreads']])

    if args.optimize:
        vrt_cmd.extend(['-optqueries', query_cfg['rqmods_dir']])

    return vrt_cmd


def build_jena_cmd(config, args, log_pre, n, cand_log):
    '''Build Jena command line call string'''
    tdb_cfg = config['TDB']
    query_cfg = config['Query']

    jena_cmd = [tdb_cfg['time'], '-v', '-o',
                '.'.join([log_pre, str(n), 'timev']),
                tdb_cfg['java'],
                tdb_cfg['java_args'],
                tdb_cfg['jenajar_logging'],
                '-jar', tdb_cfg['jenajar'],
                '-query', args.query,
                tdb_cfg['jenajar_args'],
                '-results', args.format,
                '-index', tdb_cfg['index'],
                '-candidates', cand_log]

    if tdb_cfg.getint('nthreads') > 1:
        print('using multi-threaded Jena TDB')
        jena_cmd.extend(['-t', tdb_cfg['nthreads']])

    if args.optimize:
        jena_cmd.extend(['-optqueries', query_cfg['rqmods_dir']])

    return jena_cmd


def build_riq_cmd(config, args, log_pre, n):
    '''Build RIQ command line call string'''
    dablooms_cfg = config['Dablooms']
    riq_cfg = config['RIQ']
    query_cfg = config['Query']

    riq_cmd = [riq_cfg['time'], '-v', '-o',
               '.'.join([log_pre, str(n), 'timev']),
               riq_cfg['riq'],
               '-d', '/'.join([riq_cfg['risrun'], 'index']),
               '-Q',
               '-r', args.order,
               '-A', args.query,
               '-c', dablooms_cfg['capacity'],
               '-e', dablooms_cfg['error_rate'],
               '-M', dablooms_cfg['cbfmeta_file']]

    if args.optimize:
        riq_cmd.extend(['-O', '-R', query_cfg['rqmods_dir']])

    return riq_cmd


def build_log_pre(config, args):
    '''Build prefix string used for constructing log file names'''
    pvs_cfg = config['Dataset']
    riq_cfg = config['RIQ']
    query_file = os.path.basename(args.query)
    pv_file = os.path.basename(pvs_cfg['name'])
    #pv_file = os.path.basename(pvs_cfg['PV_FILE']).split('.')[0]
    opt = 'nopt'
    if args.optimize:
        opt = 'opt'
    log_pre = '.'.join(['query', pv_file, query_file, opt])
    log_pre = '/'.join([riq_cfg['log'], log_pre])
    # print("log_pre: {0}".format(log_pre))

    return log_pre


def call_riq(cache, config, args, log_pre, n):
    '''Call RIQ'''
    # slows down warm filtering?
    # if cache != 'warm':
    #     del_bloom_filters(config)

    del_bloom_filters(config)

    log_pre = '.'.join([log_pre, cache, 'filter'])
    # print("log_pre: {0}".format(log_pre))

    cmd = build_riq_cmd(config, args, log_pre, n)
    # print("cmd: {0}".format(' '.join(cmd)))

    log = '.'.join([log_pre, str(n), 'log'])
    # print("log: {0}".format(log))

    print('{n}/{total}: identifying candidates ({cache})...'.
          format(n=n, total=args.repeat, cache=cache))

    with open(log, 'w') as outerr:
        proc = subprocess.Popen(cmd, shell=False,
                                stdout=outerr, stderr=outerr)
        result = proc.communicate()
        # print(result)

    return log


def call_tdb2(cache, config, args, log_pre, n, cand_log):
    '''Call Jena TDB API jar'''
    log_pre = '.'.join([log_pre, cache, args.using, args.format])
    # print("log_pre: {0}".format(log_pre))

    cmd = build_jena_cmd(config, args, log_pre, n, cand_log)
    # print("cmd: {0}".format(' '.join(cmd)))

    log = '.'.join([log_pre, str(n), 'log'])
    results = '.'.join([log_pre, str(n), 'results'])
    # print(log)

    print('{n}/{total}: running Jena TDB API on all candidates ({cache})...'.
          format(n=n, total=args.repeat, cache=cache))

    with open(log, 'w') as err, open(results, 'w') as out:
        proc = subprocess.Popen(cmd, shell=False,
                                stdout=out, stderr=err)
        result = proc.communicate()

    return log


def call_vrtdb(config, args, log_pre, cand_log):
    '''Start Virtuoso instances'''
    log_pre = '.'.join([log_pre, args.cache, 'vrtdb'])
    #print("log_pre: {0}".format(log_pre))

    cmd = build_vrtdb_cmd(config, args, log_pre, cand_log)
    #print("cmd: {0}".format(' '.join(cmd)))

    log = '.'.join([log_pre, 'log'])
    #print(log)

    print('starting Virtuoso instances ({cache})...'.
          format(cache=args.cache))

    #cmd = tuple(cmd.split())
    with open(log, 'w') as outerr:
        proc = subprocess.Popen(cmd, shell=False, stdout=outerr, stderr=outerr)
        result = proc.communicate()
        #print(result)

    return log,result


def call_vrt(cache, config, args, log_pre, n, cand_log):
    '''Call Virtuoso API jar'''
    log_pre = '.'.join([log_pre, cache, args.using, args.format])
    # print("log_pre: {0}".format(log_pre))

    cmd = build_vrt_cmd(config, args, log_pre, n, cand_log)
    #print("cmd: {0}".format(' '.join(cmd)))

    log = '.'.join([log_pre, str(n), 'log'])
    results = '.'.join([log_pre, str(n), 'results'])
    # print(log)

    print('{n}/{total}: running Virtuoso API on all candidates ({cache})...'.
          format(n=n, total=args.repeat, cache=cache))

    #cmd = tuple(' '.join(cmd).split())
    #print("cmd tuple: {0}".format(cmd))
    with open(log, 'w') as err, open(results, 'w') as out:
        proc = subprocess.Popen(cmd, shell=False, stdout=out, stderr=err)
        result = proc.communicate()

    return log


def check_rewritten_queries(rqmods_dir):
    '''Check for old rewritten queries: create dir or delete old queries'''
    rqmods_glob = '/'.join([rqmods_dir, '*.rqmod'])
    # print("rqmods_glob: {0}".format(rqmods_glob))

    if not os.path.exists(rqmods_dir):
        print("creating rqmods dir...")
        os.makedirs(rqmods_dir)
    else:
        print("deleting old rewritten queries...")
        rqmods_list = glob.glob(rqmods_glob)
        # print("rqmods_list: {0}".format(rqmods_list))
        for f in rqmods_list:
            os.remove(f)


def del_bloom_filters(config):
    '''Delete old query Counting Bloom Filters'''
    dablooms_cfg = config['Dablooms']
    qcbfs = dablooms_cfg['qcbfs']

    qcbfs_list = glob.glob(qcbfs)
    # print("qcbfs_list: {0}".format(qcbfs_list))
    if qcbfs_list:
        print("deleting old query CBFs...")
        for f in qcbfs_list:
            try:
                os.remove(f)
            except OSError as e:
                print('error: {0}'.format(e))
                sys.exit(1)


if __name__ == "__main__":
    sys.exit(main())
