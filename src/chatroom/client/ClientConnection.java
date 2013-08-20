package chatroom.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
			byte[] buf = new byte[5000];
			int length = is.read(buf);
			
			List<String> userList = XMLUtil.extractUserList(new String(buf, 0, length));
			chatClient.updateUserList(userList);
			
			while (true) {
				length = is.read(buf);
				String message1 = new String(buf, 0, length);
				length = is.read(buf);
				String message2 = new String(buf, 0, length);
				
				String message = message1 + message2;
				System.out.println("message :" + message);
//				MessageType messageType = XMLUtil.extractType(message);
				MessageType messageType = MessageType.LOGIN;
				
				switch (messageType) {
					case USER_LIST:
						userList = XMLUtil.extractUserList(message);
						chatClient.updateUserList(userList);
						break;
					default:
						chatClient.addMessage(message);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}










