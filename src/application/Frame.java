//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;



public class Frame extends JFrame implements ActionListener {
	JButton button;
	String XML = "";
	static boolean check = false;
	String path = "";
	Frame() {
		this.setDefaultCloseOperation(3);
		this.setLayout(new FlowLayout());
		this.setBounds(900, 400, 450, 300);
		this.button = new JButton("Select XML File");
		this.button.addActionListener(this);
		this.add(this.button);
		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.button) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int response = fileChooser.showOpenDialog((Component)null);
			File file = fileChooser.getSelectedFile();
			path = fileChooser.getSelectedFile().getAbsolutePath();
			BufferedReader br = null;
			if (response == 0) {
				try {
					br = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException var8) {
					var8.printStackTrace();
				}

				try {
					String st;
					while((st = br.readLine()) != null) {
						this.XML = this.XML + st;
					}

					check = true;
				} catch (IOException var9) {
					var9.printStackTrace();
				}
			}
		}

	}
	public String[] get_XML_file() throws IOException {
		try {
			File F = new File("\"F:\\College_Projrcts\\Data_Structure_Algorithms_XML_Project\\Intelij_Data_Structure\\Data-Structure-Algorithms-XML-Project\\sample.xml\"");
			Scanner myfile1 = new Scanner(F);
			int ctr = 0;
			while (myfile1.hasNext()) {
				ctr = ctr + 1;
				myfile1.nextLine();
			}
			Scanner myfile2 = new Scanner(F);
			String[] file_lines = new String[ctr];
			for (int i = 0; i < ctr; i++) {
				if (myfile2.hasNext()) {
						file_lines[i] = myfile2.nextLine();
				}
			}
			return file_lines;
		}
		catch(FileNotFoundException e){
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		return new String[]{"false"};
	}
	public String returnXML() {
		do {
			System.out.print("");
		} while(!check);

		return path;
	}
}
