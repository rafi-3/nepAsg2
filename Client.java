package asg2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import asg1.EmployeeManagement;

public class Client {

	static boolean chatActive = true;
	static Scanner scanner = new Scanner(System.in);
	static int p2Score;
	static int score;
	static String[][] que = 
		{
//				Add question here
				{"How long is the Nile river? (in km)", "Colour of the sky?"},
//			  	Add answer to here
				{"6853", "blue"}
		};
	
	public static void main(String[] args) {
		
		
		
		try {
			System.out.println("Hi! Welcome to online quiz");
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
								chatActive = fromServer.readBoolean();
								}
							} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}).start();
			
			
			while(chatActive) {

				try {
					
					for(int i = 0; i < que.length; i++) {
						if( i < 2) {
							while(true) {
								System.out.println(que[0][i]);
								String userin = scanner.nextLine();
								
								if(userin.equals(que[1][i])) {
									System.out.println("correct!");
									score = score+10;
									System.out.println(name + " current score is " +score);
									break;
								} else {
									System.out.println("wrong!");
									score = score-10;
									System.out.println(name + " current score is " +score);
								}
							}
						}
					}
					
					toServer.writeInt(score);
					int newScore = fromServer.readInt();
					System.out.println("Final score is: " +score);
//					System.out.println(newScore);
					if(score > newScore) {
						System.out.println(name +" you win to " + otherClientName);
					} if (score == newScore) {
						System.out.println("Its a draw!");
					} if (newScore > score) {
						System.out.println(name +" you lose to " + otherClientName);
					}

					break;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
