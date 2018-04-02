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
	static int receiveScore;
	static int score;
	static String[][] que = 
		{
				{"How long is the Nile river? (in km)", "Colour of the sky?"},
				{"6853", "blue"}
		};
	
	public static void main(String[] args) {
		
		
		
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
//								String receivedMsg = fromServer.readUTF();
//								System.out.println(name +": " + receivedMsg);
								
								receiveScore = fromServer.readInt();
								chatActive = fromServer.readBoolean();
								System.out.println(receiveScore);
								compareScore();
								}
							} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}).start();
			
			while(chatActive) {
//				String sendMsg = scanner.nextLine();
//				toServer.writeUTF(sendMsg);
				
				score= 0;
				for(int i = 0; i < que.length; i++)	{
					while(true) {
						System.out.println(que[0][i]);
						String userin = scanner.nextLine();
						
						if( i >= que.length) {
							toServer.writeInt(score);
							break;
						}
						
						if(userin.equals(que[1][i])) {
							if(i == que.length) {
								System.exit(0);
							}else {
								score= score+10;
								System.out.println("Current score" +score);
								System.out.println("Next question:");
								break;
							}

						}

						else {
							System.out.println("try again");
							score = score-10;
							System.out.println("Current score" +score);
						}
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void compareScore() {
		
		System.out.println("\nYour Score:" + score);
		System.out.println("\nOponent Score:" + receiveScore);
		
		if(score < receiveScore) {
			System.out.println("\nYou Lose!");
		} else if(score == receiveScore) {
			System.out.println("\nDraw!");
		} else {
			System.out.println("\nYou Win!");
		}
		
		int w = scanner.nextInt();
	}
	
}
