package eduDBS.umkc.sce.dbis.anask;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class DbInitiater {

	private String newConfig;
	private String filename;
	public DbInitiater(String dir, int httpPort, int srvPort) throws IOException{
		filename = dir+"/virtuoso.ini";
		BufferedReader initFile = new BufferedReader(new FileReader(filename));

		newConfig = "";

		String line;
			while ((line = initFile.readLine()) != null)
				if(line.startsWith("[Parameters]")){
					newConfig += line + System.lineSeparator() + "ServerPort = "+srvPort+System.lineSeparator();
					//skip next line, assuming the next line has the ServerPort parameter
					initFile.readLine();
				}
				else if (line.startsWith("[HTTPServer]")){
					newConfig += line + System.lineSeparator() + "ServerPort = "+httpPort+System.lineSeparator();
					//skip next line, assuming the next line has the ServerPort parameter
					initFile.readLine();
				}
				else if (line.startsWith("DefaultHost")){
					newConfig +=  "DefaultHost = localhost:"+httpPort+System.lineSeparator();
				}
				else
					newConfig += line + System.lineSeparator();
			overwriteConfig();
	}

	public void overwriteConfig() throws IOException{
		FileOutputStream out;
		out = new FileOutputStream(filename);
		out.write(newConfig.getBytes(Charset.forName("UTF-8")));
		out.close();
	}


}

