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
			
			byte[] buf = new byte[5000];
			int length = is.read(buf);
			
			String loginXML = new String(buf, 0, length); 
			
			username = XMLUtil.extractUsername(loginXML);
			messageHandler.addClientHandler(this);
			
			while (true) {
//				length = is.read(buf);
//				String message1 = new String(buf, 0, length);
				length = is.read(buf);
				String message2 = new String(buf, 0, length);
				
				String message = message2;
				messageHandler.addMessage(message);
			}
			
		} catch (IOException e) {
			this.messageHandler.removeClientHandler(this);
			System.out.println("User logout!");;
		}

		
	}


	public void sendMessage(String message) {
		
		try {
			OutputStream os = socket.getOutputStream();
			os.write(message.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getUsername() {
		return username;
	}
	

}
