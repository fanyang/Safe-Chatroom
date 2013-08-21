package chatroom.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JOptionPane;



/**
 * This class must be a thread otherwise the UI will be hold.
 * @author think
 *
 */
public class ServerConnection extends Thread {
	
	private Server server;
	private int port;
	
	
	public ServerConnection(Server server, int port) {
		this.server = server;
		this.port = port;
	}

	
	@Override
	public void run() {
		ServerSocket serverSocket;
		
		try	{
			serverSocket = createSecureServerSocket(this.port);
			this.server.setRunningState();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(this.server, "Port already in use!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		MessageHandler messageHandler = new MessageHandler(this.server);
		messageHandler.start();

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				new ClientHandler(socket, messageHandler).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private ServerSocket createSecureServerSocket(int port) throws Exception{
		 String SERVER_KEY_STORE = "keystore/server_ks";  
		 String SERVER_KEY_PASSWORD = "def456";
		 
		 System.setProperty("javax.net.ssl.trustStore", SERVER_KEY_STORE);
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















