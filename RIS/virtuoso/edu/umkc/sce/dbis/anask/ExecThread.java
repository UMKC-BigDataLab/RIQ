package edu.umkc.sce.dbis.anask;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.io.FilenameUtils;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class ExecThread implements Runnable {
	private Thread t;
	private String threadName;
	private BlockingQueue<String> mainQ;
	private HashMap<String, String> map;
	private boolean optQsExists;
	private String query_file;
	private Query query;
	SynchronizedResultSet results;
	private boolean verbose;

	ExecThread(String name, BlockingQueue<String> indexQueue,
			HashMap<String, String> cand2query, String optqueries_dir,
			String qFile, Query origQuery, SynchronizedResultSet Results, boolean verbose) {
		threadName = name;
		mainQ = indexQueue;
		map = cand2query;
		optQsExists = optqueries_dir == null ? false : true;
		query_file = qFile;
		query = origQuery;
		results = Results;
		this.verbose=verbose;
	}

	public void run() {
		long startThreadTime = System.nanoTime();
		while (mainQ.isEmpty() == false) {

			String loc = mainQ.poll();
			long startProc=0;
			if(verbose){
			System.err.println("Thread: " + threadName + " proccessing " + loc);	
			startProc = System.nanoTime();
			}
			
			String file_name = loc.split(":")[0];
			String port = loc.split(":")[1];

			// setup db connection
			String url = "jdbc:virtuoso://localhost:" + port;
			VirtGraph db = new VirtGraph(url, "dba", "dba");
			
			// create query
			VirtuosoQueryExecution qexec = null;
			if (optQsExists && map.containsKey(file_name)) {
                String rqmod_file = map.get(file_name);
                Query rqmod_query = QueryFactory.read(rqmod_file);
                
                if(verbose){
                	System.err.println("Executing rqmod query: " +FilenameUtils.getName(rqmod_file));
                	System.err.println(rqmod_query.toString());
                }
                qexec = VirtuosoQueryExecutionFactory.create(rqmod_query,db);
            } else {
            	if(verbose){
            		System.err.println("Executing original query: " +FilenameUtils.getName(query_file));
            		System.err.println(query.toString());
            	}
                qexec = VirtuosoQueryExecutionFactory.create(query, db);
            }

			//execute query
			ResultSet result = qexec.execSelect();
			
			results.append(result, file_name);
			if(verbose){
				long endProc = System.nanoTime();
				long durationProc = (endProc - startProc) / 1000000;
				System.err.println("Thread " + threadName + " finished proccessing " + loc +" in "+ durationProc+" ms");
			}
		}
		if(verbose){
			long endThreadTime = System.nanoTime();
			long duration = (endThreadTime - startThreadTime) / 1000000;
			System.err.println("Thread " + threadName + " exiting after: "+ duration+ " ms");
		}
	}

	public void start() {
		if(verbose)
			System.err.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public void waitTofinish() {
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
