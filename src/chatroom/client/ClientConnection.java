package chatroom.client;

import java.io.*;
import java.net.Socket;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

import chatroom.util.MessageType;
import chatroom.util.XMLUtil;

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
			JOptionPane.showMessageDialog(client, "Cannot connect to server!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		client.setVisible(false);
		ChatClient chatClient = new ChatClient(username, this);
		
		sendMessage(XMLUtil.constructLoginXML(username));
		
		try {
			InputStream is = socket.getInputStream();
			StringBuilder sb = new StringBuilder();
			String message;
	        int c;
			
	        while ((c = is.read()) != 255) {
	        	sb.append((char)c);
	        }
	        message = sb.toString();
			
			List<String> userList = XMLUtil.extractUserList(message);
			chatClient.updateUserList(userList);
			
			while (true) {
				
				sb = new StringBuilder();
				
				while ((c = is.read()) != 255) {
		        	sb.append((char)c);
		        }
				
				message = sb.toString();
				
				MessageType messageType = XMLUtil.extractType(message);
				
				switch (messageType) {
					case USER_LIST:
						userList = XMLUtil.extractUserList(message);
						chatClient.updateUserList(userList);
						break;
					case USER_MESSAGE:
						chatClient.addMessage(XMLUtil.extractContent(message));
						break;
					default:
						
				}
				
				
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(chatClient, "Server disconnect!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
			
		
	}


	private Socket createSecureSocket(String host, int port) throws Exception {
		
		String CLIENT_KEY_STORE = "keystore/client_ks";
		System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);
//		System.setProperty("javax.net.debug", "ssl,handshake");  //for debug
		
		SocketFactory sf = SSLSocketFactory.getDefault();  
		Socket socket = sf.createSocket(host, port);
        return socket; 
        
	}
	
	public void sendMessage(String message) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(message.getBytes());
			os.write(255); // end of message
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}










