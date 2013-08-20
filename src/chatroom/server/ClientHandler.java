package chatroom.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * ClientHandler class handle every client
 * @author Fan Yang
 *
 */
public class ClientHandler extends Thread {
	
	private Socket socket;
	private Server server;

	
	/**
	 * Create a new client handler
	 * @param clientHandlerSocket
	 */
	public ClientHandler(Socket socket, Server server) {
		
		this.socket = socket;
		this.server = server;
		
	}
	
	
	/**
	 * receive message from client
	 */
	@Override
	public void run() {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.server.setText(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	

}
