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

public class VirtQuerier {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		long MAINTIMER_START =System.currentTimeMillis();
		Options options = getProgramOptions();

		// another way to pass args (for testing):
		/*
		args = new String[] { "-vc","-results", "CSV", "-index",
				"/usr/local/Cellar/virtuoso/7.1.0/var/lib/virtuoso/db",
				"-candidates", "/Users/anask/Downloads/RIQ/Data10/dir/ndF.txt",
				"-query", "/Users/anask/Downloads/RIQ/Data10/nq1.q", "-t", "2" };
		*/

		String candidates_file;
		String query_file;
		String optqueries_dir = null;
		String index_dir;
		String format;
		boolean print_count = false;
		int numThreads = 2;
		boolean verbose = false;

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
				if (line.hasOption("t")) {
					numThreads = Integer.valueOf(line.getOptionValue("t"));
				}
				if(line.hasOption("v")){
					verbose=true;
				}
			} catch (ParseException exp) {
				System.err.println("error: parsing failed. " + exp.getMessage());
				formatter.printHelp("querier", options);
				return;

			}
		}

		File candidates = new File(candidates_file);

		ArrayList<String> db_list = null;
		try {
			db_list = readFile(candidates, index_dir);
		} catch (IOException exp) {
			System.err.println("error: IOException caught");
			return;
		}

		if(verbose)
			System.err.println("FILES FOUND: " + db_list.size());

		// Create db cand/port queue
		BlockingQueue<String> indexQueue = new ArrayBlockingQueue(db_list.size(), false, db_list);

		if(verbose)
			System.err.println("Reading query file..");

		// Store raw query
		Query query = QueryFactory.read(query_file);

		if(verbose)
			System.err.println("Creating candidate to query map..");
		// ********** Create Candidate to Query Map **********
		HashMap<String, String> cand2query = new HashMap<String, String>();

		if (optqueries_dir != null) {
			if(verbose)
				System.err.println("READING optqueries in dir: "+ FilenameUtils.getName(optqueries_dir));
			File optqueries = new File(optqueries_dir);
			File[] optqueries_list = optqueries.listFiles();
			if (db_list.size() != optqueries_list.length && verbose) {
					System.err.println("warn: number of candidates and "+ "number of rewritten queries differ");
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
			if(verbose)
				System.err.println("READING single query file: "+ FilenameUtils.getName(query_file));
		}
		// ***************************************************

		SynchronizedResultSet results = new SynchronizedResultSet(print_count);
		long startTime=0,endTime=0,duration=0;
		if(verbose){
			System.err.println("QUERYING INDICES ..");		
			//startTime = System.nanoTime();
			startTime = System.currentTimeMillis();
		}

		long SETUP =System.currentTimeMillis()-MAINTIMER_START;
		// Spawn threads and start processing
		ArrayList<ExecThread> threads = new ArrayList<ExecThread>(numThreads);
		for (int i = 0; i < numThreads; i++) {
			ExecThread t = new ExecThread("T" + i, indexQueue, cand2query,optqueries_dir, query_file, query, results,verbose);
			t.start();
			threads.add(t);

		}
		for (int j = 0; j < numThreads; j++) {
			threads.get(j).waitTofinish();
		}
		if(verbose){
			//endTime = System.nanoTime();
			endTime = System.currentTimeMillis();
			
			//duration = (endTime - startTime) / 1000000;
			duration = endTime - startTime;
			System.err.println("Query Execution Time: " + duration+" ms");
		

		//startTime = System.nanoTime();
		startTime = System.currentTimeMillis();
		}
		
		results.printResults(format);
		
		if(verbose){
			//endTime = System.nanoTime();
			endTime = System.currentTimeMillis();
			
			//long durationP = (endTime - startTime) / 1000000;
			long durationP = (endTime - startTime);
			System.err.println("Printing Time: " + durationP+" ms");
			System.err.println("Setup Time: "+SETUP+" ms");
			System.err.println("Total Time (Query+Printing): " + (duration+durationP)+" ms");
			System.err.println("Total Time (Setup+Query+Printing): " + (SETUP+duration+durationP)+" ms");
		}


		// kill all running db instances
		//Runtime.getRuntime().exec("killall virtuoso-t");
		System.err.println("Program Runtime: "+(System.currentTimeMillis()-MAINTIMER_START)+" ms");
		
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
				.withDescription("candidate dbs are stored in this dir")
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
		Option threads = OptionBuilder.withArgName("num_threads").hasArg(true)
				.withDescription("Number of threads to spwan (defualt is 2)")
				.isRequired(false).create("t");
		Option verbose = OptionBuilder.hasArg(false)
				.withDescription("Print Status and Timings")
				.isRequired(false).create("v");
		options.addOption(help);
		options.addOption(count);
		options.addOption(index);
		options.addOption(candidates);
		options.addOption(query);
		options.addOption(opt_queries);
		options.addOption(results);
		options.addOption(threads);
		options.addOption(verbose);
		return options;
	}

	private static ArrayList<String> readFile(File fin, String indDir)
			throws IOException {
		FileInputStream fis = new FileInputStream(fin);
		ArrayList<String> files = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		while ((line = br.readLine()) != null) {
			//String path = indDir + "/" + line;
			// System.err.println(path);
			files.add(line);
		}
		br.close();
		return files;
	}
}
