//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application;

public class Main {
	public Main() {
	}

	public static void main(String[] args) {
		ArrStack stack = new ArrStack();
		Frame frame = new Frame();

		System.out.println(frame.returnXML());
		System.out.println(Parsing.parse("topic", frame.returnXML()).toString());
		Validator v = new Validator();
		if(v.isCorrect(frame.XML))
		{
			System.out.println("correct");
		}
		else
			System.out.println("false");
	}
}
