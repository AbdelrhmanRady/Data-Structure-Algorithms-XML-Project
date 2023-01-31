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
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.*;

public class Main {
		public Main() {
	}
		/**
		 * Launch the application.
		 * @throws IOException 
		 */
		public static void main(String[] args) throws IOException {
			
//	        String dotFormat="1->2;1->3;1->4;4->5;4->6;6->7;5->7;3->8;3->6;8->7;2->8;2->5;";
	        
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						GUI frame = new GUI();
//						frame.setVisible(true);
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				
//			});
	        
			SNA sna = new SNA();
			
			sna.mostActive();
			sna.mostInfluencer();
			sna.mutualFollowers("Mohamed Sherif", "YasSer Ahmed");
			sna.drawConnections();
	        
//			XMLtoGraph gr = new XMLtoGraph();
//			
//			gr.convert();
			
			
			
		}				
	}
