package chatroom.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("localhost", 8443);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println(br.readLine());
		socket.close();
	}

}
