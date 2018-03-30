package asg2;

public class question {

	public question(String que) {
		super();
		this.que = que;
	}

	public String getQue() {
		return que;
	}

	public void setQue(String que) {
		this.que = que;
	}

	String que;

	@Override
	public String toString() {
		return "question: "+ que;
	}
}
