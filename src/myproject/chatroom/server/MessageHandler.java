package myproject.chatroom.server;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import myproject.chatroom.util.XMLUtil;

/**
 * Message Broadcasting class
 * @author Fan Yang
 *
 */
public class MessageHandler extends Thread{

	private LinkedBlockingQueue<String> messageQueue;
	private LinkedBlockingQueue<ClientHandler> clientHandlers;
	private Server server;
	
	/**
	 * Create a new MessageHandlerThread
	 */
	public MessageHandler(Server server) {
		this.messageQueue = new LinkedBlockingQueue<String>();
		this.clientHandlers = new LinkedBlockingQueue<ClientHandler>();
		this.server = server;
	}
	
	public void addMessage(String message) {
		this.messageQueue.add(message);
	}
	
	public void addClientHandler(ClientHandler clientHandler) {
		this.clientHandlers.add(clientHandler);
		updateUserList();
		
	}
	
	public void removeClientHandler(ClientHandler clientHandler) {
		this.clientHandlers.remove(clientHandler);
		updateUserList();
	}
	
	private void updateUserList () {
		String str = "";
		List<String> users = new ArrayList<String>();
		
		for (ClientHandler c : clientHandlers) {
			users.add(c.getUsername());
			str += c.getUsername() + "\n";
		}
		server.updateUserList(str);
		
		addMessage(XMLUtil.constructUserList(users));
	}
	
	@Override
	public void run() {
		while (true) {
			
			//Take message from message queue
			String message = null;
			try {
				message = this.messageQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
						
			//Broadcast message to all clients
			for (ClientHandler clientHandler : this.clientHandlers) {
				clientHandler.sendMessage(message);
			}
		}
		
	}
}
