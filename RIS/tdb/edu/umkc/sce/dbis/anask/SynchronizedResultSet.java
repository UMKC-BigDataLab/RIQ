package edu.umkc.sce.dbis.anask;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.ResultSetRewindable;
import com.hp.hpl.jena.sparql.util.ResultSetUtils;

public class SynchronizedResultSet {
    private ResultSet results = null;
    private int total_results = 0;
    boolean print_count  = false;
    public SynchronizedResultSet(boolean count) {
        print_count = count;
    }
    public int getTotalResults(){
        return total_results;
    }
     public synchronized void append(ResultSet result, String file_name) {
         // reset rewindable stream when counting
         if (print_count) {
             ResultSetRewindable rsrw = ResultSetFactory.copyResults(result);
             int candidate_results = rsrw.size();

             System.err.println("MATCHES FOUND IN " +file_name + ": " +candidate_results);
             total_results += candidate_results;

             rsrw.reset();
             if (results != null)
                 results = ResultSetUtils.union(rsrw, results);
             else
                 results = ResultSetFactory.copyResults(rsrw);

         } else {
             if (results != null)
                 results = ResultSetUtils.union(result, results);
             else
                 results = ResultSetFactory.copyResults(result);
        }

    }
    public void printResults(String format) {
            if (print_count)
                System.err.println("TOTAL MATCHES FOUND: " + total_results);

            if (format.equals("XML"))
                ResultSetFormatter.outputAsXML(results);
            else if (format.equals("CSV"))
                ResultSetFormatter.outputAsCSV(results);
            else if (format.equals("TSV"))
                ResultSetFormatter.outputAsTSV(results);
            else
                System.out.print(ResultSetFormatter.asText(results));

    }

}
