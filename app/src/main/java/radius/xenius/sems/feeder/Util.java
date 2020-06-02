package radius.xenius.sems.feeder;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Util {
	
	public static String readFile(InputStream fileInputStream, Map<String, String> params) {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(fileInputStream));
			String line;
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		String str = builder.toString();
		if(params != null) {
			for(Map.Entry<String, String> param : params.entrySet()) {
				String value = param.getValue();
				String key = param.getKey();
				System.out.println(value+" "+key);
				str = str.replaceAll(key, value);
			}
		}
		
		return str;
	}
	
	public static String getdata(String u) {
		System.out.println(u);
		String d = null;
		BufferedReader reader = null;
        try {
	    	StrictMode.ThreadPolicy policy = new StrictMode.
	          ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy); 
	        URL url = new URL(u);
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	 	    String line = "";
	 	    while ((line = reader.readLine()) != null) {
	 	    	d = line;
	 	    }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }  finally {
	 	    if (reader != null) {
	 	      try {
	 	        reader.close();
	 	      } catch (IOException e) {
	 	        e.printStackTrace();
	 	      }
	 	    }
 	  }
		return d;
	}

}
