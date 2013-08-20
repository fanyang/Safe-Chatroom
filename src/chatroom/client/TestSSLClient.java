package chatroom.client;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class TestSSLClient {

	public static void main(String[] args) throws Exception{
		TestSSLClient testSSLClient = new TestSSLClient();
		
		Socket socket = testSSLClient.createSecureSocket("localhost", 8443);
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		
		
		String msg = "hellohellohellohello";

		bos.write(msg.getBytes()); bos.flush();bos.write("\0".getBytes());bos.flush();
		bos.write(msg.getBytes()); bos.flush();bos.write(0);bos.flush();
		bos.write(msg.getBytes()); bos.flush();bos.write(0);bos.flush();
		bos.write(msg.getBytes()); bos.flush();bos.write(0);bos.flush();
		bos.write(msg.getBytes()); bos.flush();bos.write(0);bos.flush();
		bos.write(msg.getBytes()); bos.flush();bos.write(0);bos.flush();
		
	}
	
	private Socket createSecureSocket(String host, int port) throws Exception {
		
		String CLIENT_KEY_STORE = "keystore/client_ks";
		System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
//		System.setProperty("javax.net.debug", "ssl,handshake");  //for debug
		
		SocketFactory sf = SSLSocketFactory.getDefault();  
		Socket socket = sf.createSocket(host, port);
        return socket; 
        
	}

}
