package edu.umkc.sce.dbis.anask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.ResultSetRewindable;
import com.hp.hpl.jena.sparql.resultset.ResultSetFormat;

public class SynchronizedResultSet {
    
    private int total_results = 0;
    ArrayList<ResultSet> results = new ArrayList<ResultSet>();
    
    boolean print_count  = false;
    public SynchronizedResultSet(boolean count) {
        print_count = count;
    }
    public int getTotalResults(){
        return total_results;
    }
     public synchronized void append(ResultSet result, String file_name) {
    	 
    	 // reset rewindable stream when counting
    	 /*
         if (print_count) {
        	 try{
        	 ResultSetRewindable rsrw = (ResultSetRewindable) result;
             int candidate_results = rsrw.size();

             System.err.println("MATCHES FOUND IN " +file_name + ": " +candidate_results);
             total_results += candidate_results;

             rsrw.reset();
        	 }catch (Exception e){
        		 System.err.println("SynchronizedResultSet: Counting Error!");
        	 }
        	 
         }*/
         
    	 
         results.add(result);

    }
    public void printResults(String format) {

            if(format.equals("CSV"))//manually generated, not safe for demo
            {
            	int iAnswers = results.size();
        		int s = 0;
        		ResultSet answerSet = results.get(s);
        		List vars = answerSet.getResultVars();
        		int numVars = vars.size();
        		total_results=0;
        		for(s=0;s<iAnswers;s++){
        			 
        			answerSet = results.get(s);
        			int candidate_results = 0;
        			while (answerSet.hasNext()){
        				QuerySolution sol = answerSet.nextSolution();
        				int i;
        				for(i=0;i<numVars-1;i++){
        					System.out.print(sol.get(vars.get(i).toString())+",");
        				}
        				System.out.println(sol.get(vars.get(i).toString()));
        				candidate_results++;
        				
        			}
        			if(print_count)
        				System.err.println("MATCHES FOUND IN DB" +s+ ": " +candidate_results);
                    total_results += candidate_results;
        		}

            }
            else if (format.equals("XML"))
            for(int i = 0; i<results.size(); i++)
                ResultSetFormatter.output(results.get(i),  ResultSetFormat.lookup("XML"));
                

            if (print_count)
                System.err.println("TOTAL MATCHES FOUND [DISABLED FOR XML]: " + total_results);

            //else if (format.equals("CSV"))
            //    ResultSetFormatter.outputAsCSV(results);
            //else if (format.equals("TSV"))
            //    ResultSetFormatter.outputAsTSV(results);
            //else
            //    System.out.print(ResultSetFormatter.asText(results));

    }

}
