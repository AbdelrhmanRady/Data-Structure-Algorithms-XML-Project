//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application;

public class Main {
	public Main() {
	}

	public static void main(String[] args) {
		Frame frame = new Frame();
		System.out.println(Parsing.parse("topic", frame.returnXML()).toString());
	}
}
