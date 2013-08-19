package chatroom.client;

import java.io.*;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class Client {
	 private static String CLIENT_KEY_STORE = "keystore/client_ks";  
	  
	    public static void main(String[] args) throws Exception {  
	        // Set the key store to use for validating the server cert.  
	        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);  
	          
//	        System.setProperty("javax.net.debug", "ssl,handshake");
	        System.setProperty("javax.net.debug", "ssl");  
	  
	        Client client = new Client();  
	        Socket s = client.clientWithoutCert();  
	  
	        PrintWriter writer = new PrintWriter(s.getOutputStream());  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(s  
	                .getInputStream()));  
	        writer.println("hello2");  
	        writer.flush();  
	        System.out.println(reader.readLine());  
	        s.close();  
	    }  
	  
	    private Socket clientWithoutCert() throws Exception {  
	        SocketFactory sf = SSLSocketFactory.getDefault();  
	        Socket s = sf.createSocket("localhost", 8443);  
	        return s;  
	    }  
}  

