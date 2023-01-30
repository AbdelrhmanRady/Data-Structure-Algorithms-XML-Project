package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.*;
import application.compression.Huffman;
import application.compression.HuffmanEncodedResult;
import application.compression.NodeCD;
import java.util.Map;
public class JLabel extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private String path;
	HuffmanEncodedResult h = null;
	private String XML="";
    boolean check = false;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private ArrayList<String> s= new ArrayList<String>();
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	BufferedReader br = null;
	/**
	 * Create the frame.
	 * @return 
	 */
	public GUI() {
		setTitle("XML Reader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//contentPane.setLayout(new BorderLayout());
		
		contentPane.setPreferredSize(new Dimension(50, 40));
		contentPane.setVisible(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(600, 55, 575, 580);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		//contentPane.add(textField);
		textArea.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 55, 575, 580);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		btnNewButton = new JButton("Read File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				int response = fileChooser.showOpenDialog((Component)null);
				File file = fileChooser.getSelectedFile();
			    path = file.getAbsolutePath();
				System.out.println(path);

				if (response == 0) {
					try {
						br = new BufferedReader(new FileReader(path));
						textArea_1.read(br, e);
						textArea_1.requestFocus();
					} catch (FileNotFoundException var8) {
						var8.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						String st;
						while((st = br.readLine()) != null) {
							XML = XML + st;
						}
						check = true;
					} catch (IOException var9) {
						var9.printStackTrace();
					}
		}}});
		btnNewButton.setBounds(0, 10, 120, 20);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Error detection");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				Validator v = new Validator();
				ArrayList<Integer> a=new ArrayList<Integer>();
				int error_line=0;String t="";
				int startIndex = 0, endIndex = 0;
				DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
				String[] str = new String[s.size()]; 
				try {
					a.addAll(v.Bracket_isCorrect(get_XML_file()));				
					s = v.Fix_Missing_Bracket_Error(get_XML_file());
			        for (int i = 0; i < s.size(); i++) {
			            str[i] = s.get(i);
			        }
					t=v.tag_is_correct(str);
					if(t == "-1")
					{
						System.out.println("tags are correct");
					}
					else
					{
						error_line = Integer.parseInt(t.substring(0, t.indexOf("_")));
						a.add(error_line);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					int i=0;
					while(a.get(i) != null) {
						System.out.println(a.get(i));
						int space_counter =0;
						while (s.get(a.get(i)).charAt(space_counter) == ' ') {        /*get number of spaces before the tag*/
			                space_counter++;
			            }
						(startIndex) = textArea_1.getLineStartOffset(a.get(i));
						(endIndex) = textArea_1.getLineEndOffset(a.get(i));
						textArea_1.getHighlighter().addHighlight(startIndex+space_counter, endIndex, painter);
						i++;
					}
					
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(125, 10, 120, 20);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Correction");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				Validator V = new Validator();
				int error_line=0;
				String missing_tag="";
				String t="";
				try {
					s = V.Fix_Missing_Bracket_Error(get_XML_file());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String[] str = new String[s.size()];
			    for (int i = 0; i < s.size(); i++) {
			            str[i] = s.get(i);
			        }
				t=V.tag_is_correct(str);
				if(t == "-1")
				{
					System.out.println("tags are correct");
				}
				else
				{
					error_line = Integer.parseInt(t.substring(0, t.indexOf("_")));
					missing_tag= t.substring(t.indexOf("_")+1, t.length());
					s=V.adding_missing_tag(str,error_line,missing_tag);
				}
				for(int i=0; i<s.size(); i++) {
					textArea.append(s.get(i)+ '\n');
				}
			}
		});
		btnNewButton_2.setBounds(250, 10, 120, 20);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("JSON");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				textArea.setText(null);
				Tree tree = new Tree();
				StringBuilder sb = new StringBuilder();
				try {
				tree.insertXML(s);				
				XML2JSON xml2JSON = new XML2JSON(sb,0,XML);
				xml2JSON.Convert(tree.getTreeRoot());
				xml2JSON.createJsonFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textArea.append(sb.toString());

		}});
		btnNewButton_3.setBounds(375, 10, 120, 20);
		contentPane.add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Prettifying");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				Tree tree = new Tree();
				StringBuilder sb = new StringBuilder();
				try {
				tree.insertXML(s);				
				Prettify pretty = new Prettify(sb);
				sb =pretty.prettify(tree.getTreeRoot());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textArea.append(sb.toString());

		}
		});
		btnNewButton_4.setBounds(500, 10, 120, 20);
		contentPane.add(btnNewButton_4);
		
		btnNewButton_5 = new JButton("Compression");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				Minify min = new Minify();
		        File file = new File(path);
		        String s2 = null;
				try {
					s2 = min.minifyKeepingSpaces(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textArea.append(s2.toString());
				String outputPath = path.substring(0,path.lastIndexOf("\\"));
				Huffman huffman = new Huffman(outputPath+"compressed.xml");
				final int[] table =huffman.buildFrequencyTable(s2);
				final NodeCD n =huffman.buildHuffmanTree(table);
				final Map<Character,String> lookUpTable = huffman.buildLookUpTable(n);
				//HuffmanEncodedResult h = null;
				try {
					h = huffman.compress(s2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//System.out.println(h.encodedData);

			}
		});
		btnNewButton_5.setBounds(625, 10, 120, 20);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Decompression");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			textArea.setText(null);
			String outputPath = path.substring(0,path.lastIndexOf("\\"));
			Huffman huffman = new Huffman(outputPath+"compressed.xml");
			String s = null;
			try {
				s = huffman.decompress(h.root,outputPath+"compressed.xml.txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				System.out.println("decompressed");
			//System.out.println(s);
				textArea.append(s.toString());
			}
		});
		btnNewButton_6.setBounds(750, 10, 120, 20);
		contentPane.add(btnNewButton_6);
		
		
	}
	
	public String[] get_XML_file() throws IOException {
		try {
			File F = new File(path);
			try (Scanner myfile1 = new Scanner(F)) {
				int ctr = 0;
				while (myfile1.hasNext()) {
					ctr = ctr + 1;
					myfile1.nextLine();
				}
				try (Scanner myfile2 = new Scanner(F)) {
					String[] file_lines = new String[ctr];
					for (int i = 0; i < ctr; i++) {
						if (myfile2.hasNext()) {
								file_lines[i] = myfile2.nextLine();
						}
					}
					return file_lines;
				}
			}
		}
		catch(FileNotFoundException e){
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		return new String[]{"false"};
	}
}
