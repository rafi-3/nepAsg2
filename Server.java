package as22;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(9101);
			System.out.println("Server started on " + new Date());
			
			while(true) {
				Socket client1 = serverSocket.accept();
				Socket client2 = serverSocket.accept();
				
				Session session =
						new Session(client1, client2);
				
				new Thread(session).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
