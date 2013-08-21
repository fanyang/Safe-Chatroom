package chatroom.server;

import java.io.*;
import java.net.Socket;

import chatroom.util.XMLUtil;


/**
 * ClientHandler class handle every client
 * @author Fan Yang
 *
 */
public class ClientHandler extends Thread {
	
	private Socket socket;
	private MessageHandler messageHandler;
	private String username;

	
	/**
	 * Create a new client handler
	 * @param messageHandler 
	 * @param clientHandlerSocket
	 */
	public ClientHandler(Socket socket, MessageHandler messageHandler) {
		
		this.socket = socket;
		this.messageHandler = messageHandler;
		
	}
	
	
	/**
	 * receive message from client
	 */
	@Override
	public void run() {
		
		try {
			InputStream is = socket.getInputStream();
			StringBuilder sb = new StringBuilder();
			String message;
	        int c;
			
	        while ((c = is.read()) != 255) {
	        	sb.append((char)c);
	        }
	        
	        message = sb.toString();
			username = XMLUtil.extractUsername(message);
			messageHandler.addClientHandler(this);
			
			while (true) {
				
				sb = new StringBuilder();
		        while ((c = is.read()) != 255) {
		        	sb.append((char)c);
		        }
		        message = sb.toString();
				
				messageHandler.addMessage(message);
			}
			
		} catch (IOException e) {
			this.messageHandler.removeClientHandler(this); // user disconnect
			return;
		}

		
	}


	/**
	 * Send message to client
	 * @param message message to send
	 */
	public void sendMessage(String message) {
		
		try {
			OutputStream os = socket.getOutputStream();
			os.write(message.getBytes());
			os.write(255); // end of message
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getUsername() {
		return username;
	}
	

}
