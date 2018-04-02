package asg2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ChatSession implements Runnable {

	private Socket client1, client2;
	private boolean sentMsgToClient1 = true;
	private boolean sentMsgToClient2 = true;
	
	public ChatSession(Socket client1, Socket client2) {
		this.client1 = client1;
		this.client2 = client2;
	}
	
	@Override
	public void run() {
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
			
			int p1s = fromClient1.readInt();
			int p2s = fromClient2.readInt();
			toClient1.writeInt(p2s);
			toClient2.writeInt(p1s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}