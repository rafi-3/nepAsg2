package as22;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	static Scanner scanner = new Scanner(System.in);
	static String[][] que = 
		{
				{"How long is the Nile river? (in km)", "Colour of the sky?"},
				{"6853", "blue"}
		};
	int score= 0;
	
	public static void main(String[] args) throws IOException {
		
		Socket socket = new Socket("localhost", 9101);

		
		DataInputStream fromServer =
				new DataInputStream(socket.getInputStream());
		DataOutputStream toServer =
				new DataOutputStream(socket.getOutputStream());
		
		for(int s = 10; s <50; s++) {
//			score+=10;
		}
		
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
						System.out.println("Next question:");
					}

					break;
				}

				else {
					System.out.println("try again");
				}
			}
		}
	}
}
