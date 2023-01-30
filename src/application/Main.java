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
import java.awt.EventQueue;
import java.io.*;

public class Main {
		public Main() {
	}
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GUI frame = new GUI();
						frame.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
		}				
	}
