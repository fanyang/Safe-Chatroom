package chatroom.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class ClientConnection extends Thread {
	
	private Client client;
	private String username;
	private String host;
	private int port;
	private Socket socket;
	
	public ClientConnection(Client client, String username, String host,
			int port) {
		this.client = client;
		this.username = username;
		this.host = host;
		this.port = port;
	}
	
	
	@Override
	public void run() {
		try {
			socket = this.createSecureSocket(this.host, this.port);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		new ChatClient(username, this);
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				System.out.println(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}


	private Socket createSecureSocket(String host, int port) throws Exception {
		
		String CLIENT_KEY_STORE = "keystore/client_ks";
		System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
		
		SocketFactory sf = SSLSocketFactory.getDefault();  
        Socket socket = sf.createSocket(host, port);  
        return socket; 
        
	}
	
	public void sendMessage(String message) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(message);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}










