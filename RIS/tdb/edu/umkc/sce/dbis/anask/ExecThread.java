package edu.umkc.sce.dbis.anask;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.io.FilenameUtils;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.tdb.TDBFactory;


public class ExecThread implements Runnable {
       private Thread t;
       private String threadName;
       private BlockingQueue<File> mainQ;
       private HashMap<String, String> map;
       private boolean optQsExists;
       private String query_file;
       private Query query;
       SynchronizedResultSet results ;
       ExecThread( String name, BlockingQueue<File> q, HashMap<String, String> cand2query, String optqueries_dir, String qFile, Query origQuery,SynchronizedResultSet Results){
           threadName = name;
           mainQ = q;
           map = cand2query;
           optQsExists = optqueries_dir == null ? false : true;
           query_file = qFile;
           query = origQuery;
           results = Results;
           //System.err.println("Creating " +  threadName );
       }

       public void run() {
           long startThreadTime = System.nanoTime();
           while (mainQ.isEmpty() == false) {
                long startCandTime = System.nanoTime();
                File loc = mainQ.poll();
                System.err.println("Thread: " + threadName + " Proccessing "+loc.getName() );
                // System.err.println(loc);
                if (loc.isDirectory()) {
                    // get path of the index to be queried
                    String path = loc.getAbsolutePath();
                    String file_name = FilenameUtils.getName(path);

                    // connect to the index
                    Dataset dataset = TDBFactory.createDataset(path);
                    dataset.begin(ReadWrite.READ);

                    try {
                        // issue query
                        QueryExecution qexec = null;
                        if (optQsExists && map.containsKey(file_name)) {

                            String rqmod_file = map.get(file_name);
                            System.err.println("Executing rqmod query: " +FilenameUtils.getName(rqmod_file));
                            Query rqmod_query = QueryFactory.read(rqmod_file);
                            qexec = QueryExecutionFactory.create(rqmod_query,dataset);
                        } else {
                            System.err.println("Executing original query: " +FilenameUtils.getName(query_file));
                            qexec = QueryExecutionFactory.create(query, dataset);
                        }

                        long startTime = System.nanoTime();
                        try {
                            ResultSet result = qexec.execSelect();
                            results.append(result, file_name);

                        } finally {
                            qexec.close();
                        }
                        long endTime = System.nanoTime();
                        // in s
                        long duration = (endTime - startTime) / 1000000;
                        System.err.println("results.append (ms): " + duration);
                    } finally {
                        dataset.end();
                        // not necessary
                        //dataset.close();
                    }
                } else {
                    System.err.println("warn: not a dir");
                }
                long endCandTime = System.nanoTime();
                // in ms
                long duration = (endCandTime - startCandTime) / 1000000;
                System.err.println("candidate (ms): " + duration);
         }
         long endThreadTime = System.nanoTime();
         // in ms
         long duration = (endThreadTime - startThreadTime) / 1000000;
         System.err.println("Thread " +  threadName + " exiting after (ms) "+
                            duration);
       }

       public void start()
       {
          System.err.println("Starting " +  threadName );
          if (t == null)
          {
             t = new Thread (this, threadName);
             t.start ();
          }
       }

       public void waitTofinish(){
           try {
              t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
       }

    }
