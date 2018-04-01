package as22;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {

	private Socket client1, client2;

	public Session(Socket client1, Socket client2) {
		// TODO Auto-generated constructor stub
		this.client1 = client1;
		this.client2 = client2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			DataInputStream fromClient1 =
					new DataInputStream(client1.getInputStream());
			DataOutputStream toClient1 =
					new DataOutputStream(client1.getOutputStream());
			
			DataInputStream fromClient2 =
					new DataInputStream(client2.getInputStream());
			DataOutputStream toClient2 =
					new DataOutputStream(client2.getOutputStream());
			
			String client1Name = fromClient1.readUTF();
			String client2Name = fromClient2.readUTF();
			toClient1.writeUTF(client2Name);
			toClient2.writeUTF(client1Name);
			
			String client1IP = client1.getInetAddress().getHostAddress();
			String client2IP = client2.getInetAddress().getHostAddress();
			toClient1.writeUTF(client2IP);
			toClient2.writeUTF(client1IP);
			
			System.out.println(client1Name + " (" + client1IP + ") " 
					+ " is connected to " + client2Name + " (" + 
					client2IP + ").");
			
//			int client1Score = fromClient1.readInt();
//			int client2Score = fromClient2.readInt();
//			fromClient1.readInt();
//			fromClient2.readInt();
			
			int score1 = fromClient1.readInt();
			int score2 = fromClient2.readInt();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
