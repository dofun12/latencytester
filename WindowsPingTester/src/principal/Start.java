package principal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Start {

	public static void main(String[] args) {
		try {
			int seconds = 120;
			for(int i=0;i<seconds;i++){
				Thread.sleep(1000);
				//System.out.println(pingGetResponse("192.168.25.1"));
				//System.out.println(pingGetResponse("www.google.com.br"));
				System.out.println("Google: "+getMS(pingGetResponse("www.google.com.br"))+"ms");
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static Integer getMS(String pingResponse){
		if(pingResponse!=null&&!pingResponse.isEmpty()){
			for(String parte:pingResponse.split(" ")){
				if(parte.contains("tempo")){
					String ms = parte.split("=")[1]; 
					ms = ms.substring(0,ms.length()-2);
					return Integer.parseInt(ms);
				}
			}
		}else{
			return 0;
		}
		return 0;
	}
	private static boolean ping(String host) throws IOException, InterruptedException {
	    //boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

	    ProcessBuilder processBuilder = new ProcessBuilder("ping","-n","1",host);
	    Process proc = processBuilder.start();
	    
	   
	    BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    StringBuilder builder = new StringBuilder();
	    String line = null;
	    while ( (line = br.readLine()) != null) {
	       if(line.contains("Resposta")){
	    	   builder.append("("+new Date()+") "+line);
		       break;
	       }else if(line.contains("Esgotado")){
	    	   builder.append("("+new Date()+") "+"*******Sem sinal********");
		       break;
	       }
	    }
	    String result = builder.toString();
	    int returnVal = proc.waitFor();
	    System.out.println(result);
	    
	    return returnVal == 0;
	}
	private static String pingGetResponse(String host) throws IOException, InterruptedException {
	    //boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

	    ProcessBuilder processBuilder = new ProcessBuilder("ping","-n","1","-l","10",host);
	    Process proc = processBuilder.start();
	    
	   
	    BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	    StringBuilder builder = new StringBuilder();
	    String line = null;
	    while ( (line = br.readLine()) != null) {
	       if(line.contains("Resposta")){
	    	   builder.append("("+new Date()+") "+line);
		       break;
	       }else if(line.contains("Esgotado")){
	    	   builder.append("("+new Date()+") "+"*******Sem sinal********");
		       break;
	       }
	    }
	    String result = builder.toString();
	    return result;
	}
}
