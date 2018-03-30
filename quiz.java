package asg2;

import java.util.ArrayList;


public class quiz {
	static ArrayList<question> que = new ArrayList<question>();
	static ArrayList<answer> ans = new ArrayList<answer>();
	
	public static void list() {
//		insert question here
		que.add(new question("how long is the Nile river? (in KM)"));
//		que.add(new question("how long is the Nile riverdsfffs?"));
		
//		insert answer here
		ans.add(new answer("6,853"));
	}
	
	public static ArrayList<question> getQue() {
//		for(int i=1; i>=1; i++){
//			list();
//       }		
//		return que;
		
		list();
		return que;
	}
	
	public static ArrayList<answer> getAns() {
		list();
		return ans;
	}
}
