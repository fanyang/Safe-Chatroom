package chatroom.server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class TestSSLServer {

	public static void main(String[] args) throws Exception {

		TestSSLServer testSSLServer = new TestSSLServer();
		ServerSocket ss = testSSLServer.createSecureServerSocket(8443);
		SSLSocket socket = (SSLSocket)(ss.accept());
		
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		byte[] buf = new byte[5000];
		
		int length = is.read(buf);
		String msg = new String(buf, 0, length);
		System.out.println("c1 " + msg);
		
		
		length = is.read(buf);
		msg = new String(buf, 0, length);
		System.out.println("c2 " + msg);
		
		
		length = is.read(buf);
		msg = new String(buf, 0, length);
		System.out.println("c3 " + msg);
		length = is.read(buf);
		msg = new String(buf, 0, length);
		System.out.println("c4 " + msg);
		length = is.read(buf);
		msg = new String(buf, 0, length);
		System.out.println("c5 " + msg);
		length = is.read(buf);
		msg = new String(buf, 0, length);
		System.out.println("c6 " + msg);
		
		
		
	}
	
	private ServerSocket createSecureServerSocket(int port) throws Exception{
		 String SERVER_KEY_STORE = "keystore/server_ks";  
		 String SERVER_KEY_PASSWORD = "def456";
		 
		 System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);
		 System.setProperty("javax.net.debug", "ssl,handshake");  //for debug
	     SSLContext context = SSLContext.getInstance("TLS");
	          
	     KeyStore ks = KeyStore.getInstance("jceks");  
	     ks.load(new FileInputStream(SERVER_KEY_STORE), null);  
	     KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");  
	     kf.init(ks, SERVER_KEY_PASSWORD.toCharArray());  
	          
	     context.init(kf.getKeyManagers(), null, null);  
	  
	     SSLServerSocketFactory factory = context.getServerSocketFactory();  
	     SSLServerSocket serverSocket = (SSLServerSocket)(factory.createServerSocket(port));  
	     serverSocket.setNeedClientAuth(false);
	     return serverSocket;
		
	}

}
