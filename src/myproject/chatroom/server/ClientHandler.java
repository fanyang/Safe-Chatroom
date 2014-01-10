package myproject.chatroom.server;

import java.io.*;
import java.net.Socket;

import myproject.chatroom.util.XMLUtil;


/**
 * ClientHandler class handle every client
 * @author Fan Yang
 *
 */
public class ClientHandler implements Runnable {
	
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
		
		try(InputStream is = socket.getInputStream();
			) {
			
	        String message = takeMessage(is);
	        
			username = XMLUtil.extractUsername(message);
			messageHandler.addClientHandler(this);
			
			while (true) {
		        message = takeMessage(is);
				messageHandler.addMessage(message);
			}
			
		} catch (IOException e) {
			this.messageHandler.removeClientHandler(this); // user disconnect
			return;
		}

		
	}


	private String takeMessage(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder("");
		int c;
		while ((c = is.read()) != 255) {
			sb.append((char)c);
		}
		
		return sb.toString();
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
