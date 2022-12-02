//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package application;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Frame extends JFrame implements ActionListener {
	JButton button;
	String XML = "";
	static boolean check = false;

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

	public String returnXML() {
		do {
			System.out.print("");
		} while(!check);

		return this.XML;
	}
}
