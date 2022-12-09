//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
//
//package application;
//
//public class Main {
//	public Main() {
//	}
//
//	public static void main(String[] args) {
//		ArrStack stack = new ArrStack();
//		Frame frame = new Frame();
//
//		System.out.println(frame.returnXML()); 			/*printing all data in the xml file*/
//		System.out.println(Parsing.parse("topic", frame.returnXML()).toString());
//		Validator v = new Validator();
//		v.Bracket_isCorrect(frame.XML);						/*function that check the correctness of the XML file format*/
//		v.Tag_isCorrect(frame.XML);
//	}
//}


package application;
import java.io.*;

public class Main {
		public Main() {
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
			Frame F = new Frame();
			Validator V = new Validator();
			//V.Bracket_isCorrect(F.get_XML_file());

			V.Fix_Missing_Bracket_Error(F.get_XML_file());
			//V.isValid();
	}
}