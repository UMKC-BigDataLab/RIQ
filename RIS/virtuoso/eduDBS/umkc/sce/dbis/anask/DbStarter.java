package eduDBS.umkc.sce.dbis.anask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class DbStarter {

	public static void main(String[] args) throws IOException, InterruptedException {
		// another way to pass args (for testing):
		/*
		args = new String[] {  
				"-index","/usr/local/Cellar/virtuoso/7.1.0/var/lib/virtuoso/db",
				"-candidates", "/Users/anask/Downloads/RIQ/Data10/dir/ndF.txt",
				};
		*/
		Options options = getProgramOptions();
		HelpFormatter formatter = new HelpFormatter();
		String candidates_file;
		String index_dir;
		
		
		if (args.length <= 1) {
			formatter.printHelp("querier", options);
			return;
		} else {
			CommandLineParser parser = new PosixParser();
			try {
				CommandLine line = parser.parse(options, args);
				candidates_file = line.getOptionValue("candidates");
				index_dir = line.getOptionValue("index");
				if (line.hasOption("h")) {
					formatter.printHelp("querier", options);
					return;
				}

			} catch (ParseException exp) {
				System.err
						.println("error: parsing failed. " + exp.getMessage());
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
		
		ArrayList<String> candProtList = configureAndStartDBs(db_list);
		FileOutputStream fos = new FileOutputStream(candidates_file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		 
		for (String i : candProtList ) {
			bw.write(i);
			bw.newLine();
		}
	 
		bw.close();

		
	
	}

	private static ArrayList<String> configureAndStartDBs(ArrayList<String> db_list) throws IOException, InterruptedException {
		int baseHttpPort = 8890;
		int baseServerPort = 1111;
		int numDBs = db_list.size();
		int httpPort = baseHttpPort + 1;
		int srvPort = baseServerPort + 1;
		ArrayList<String> candisates = new ArrayList<String>();

		for (int i = 0; i < numDBs; i++) {

			String db = db_list.get(i);
			System.err.println("Checking ports for "+db);
			// check if ports are available
			while (!available(httpPort))
				httpPort += 1;

			while (!available(srvPort))
				srvPort += 1;

			System.err.println("Ports selected: "+httpPort+" and "+srvPort);

			// configure db virtuoso.ini file to run on these ports
			new DbInitiater(db, httpPort, srvPort);

			String[] fullpath = db.split("/");
			String cand = fullpath[fullpath.length - 1];

			// store cand to port mapping
			candisates.add(cand + ":" + srvPort);

			startDb(db);

			httpPort += 1;
			srvPort += 1;

		}
		System.err.println("DB Start Request Initiated, Sleeping ..");
		Thread.sleep(12000);
		System.err.println("Woke up.");
		
		return candisates;// to be used for querying

	}

	private static void startDb(String db) {
		String[] cmd = { "/bin/sh", "-c","/usr/bin/virtuoso-t -f"};
		//String[] cmd = { "/bin/sh", "-c","/usr/local/bin/virtuoso-t -f"};

		File DB = new File(db);
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.directory(DB);
		Process p = null;
		try {
			p = pb.start();
		} catch (IOException e) {
			System.err.println("Error: Starting DB (VirtQuerier.java/startDb())!");
			e.printStackTrace();
		}
		

	}
	// http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
		private static boolean available(int port) {
			// System.err.println("--------------Testing port " + port);
			Socket s = null;
			try {
				s = new Socket("localhost", port);

				// If the code makes it this far without an exception it means
				// something is using the port and has responded.
				// System.err.println("--------------Port " + port +
				// " is not available");
				return false;
			} catch (IOException e) {
				// System.err.println("--------------Port " + port +
				// " is available");
				return true;
			} finally {
				if (s != null) {
					try {
						s.close();
					} catch (IOException e) {
						throw new RuntimeException("You should handle this error.",
								e);
					}
				}
			}
		}
		private static ArrayList<String> readFile(File fin, String indDir)
				throws IOException {
			FileInputStream fis = new FileInputStream(fin);
			ArrayList<String> files = new ArrayList<String>();

			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			
			while ((line = br.readLine()) != null) {
				try{
					String[] cand_Port = line.split(":");
					
					files.add(indDir + "/" +cand_Port[0]);	
				}
				catch (Exception e){
					files.add(indDir + "/" +line);	
				}
			}
			br.close();
			return files;
		}
			

		

		private static Options getProgramOptions() {
			Options options = new Options();
			Option help = new Option("h", "print this message");
			Option index = OptionBuilder.withArgName("dir").hasArg(true)
					.withDescription("candidate dbs are stored in this dir")
					.isRequired(true).create("index");
			Option candidates = OptionBuilder.withArgName("file").hasArg(true)
					.withDescription("list of candidates").isRequired(true)
					.create("candidates");

			options.addOption(help);
			options.addOption(index);
			options.addOption(candidates);
			return options;
		}

}

