package asg2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import asg1.EmployeeManagement;

public class Client {

	static boolean chatActive = true;
	static String[][] que = 
		{
				{"How long is the Nile river? (in km)", "Colour of the sky?"},
				{"6853", "blue"}
		};
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		try {
			System.out.println("Hi! Welcome online quiz");
			System.out.println("Let's play the game!");
			System.out.println("Please enter your name to begin... ");
			String name = scanner.nextLine();
			
			Socket socket = new Socket("localhost", 9101);
			System.out.println("Connected to server.");
			
			
			
			DataInputStream fromServer =
					new DataInputStream(socket.getInputStream());
			DataOutputStream toServer =
					new DataOutputStream(socket.getOutputStream());
			
			toServer.writeUTF(name);
			String otherClientName = fromServer.readUTF();
			String otherClientIP = fromServer.readUTF();
			
			System.out.println("Connected with " + otherClientName 
					+ " (" + otherClientIP + ").");
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					while(chatActive) {
						try {
							if(fromServer.available() > 0) {
								String receivedMsg = fromServer.readUTF();
								System.out.println(name +": " + receivedMsg);
								chatActive = fromServer.readBoolean();
								for(int i = 0; i < que.length; i++)	{
									while(true) {
										System.out.println(que[0][i]);
										String userin = scanner.nextLine();
										
										if(i == que.length) {
											System.out.println("game over");
											System.exit(0);
										}
										
										if(userin.equals(que[1][i])) {
											if(i == que.length) {
												System.exit(0);
											}else {
												System.out.println("Right! next question:");
											}
											
											break;
										}
										
										else {
											System.out.println("try again");
										}
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}).start();
			
			while(chatActive) {
				String sendMsg = scanner.nextLine();
				toServer.writeUTF(sendMsg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
