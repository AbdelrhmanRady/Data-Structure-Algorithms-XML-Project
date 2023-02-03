package main_gui;

import java.awt.Font;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.EmptyBorder;

import java.io.*;

import compression.CreateFile;
import compression.Huffman;
import compression.HuffmanEncodedResult;
import compression.NodeCD;
import format.*;
import sna_graph.*;
import validation.*;
import parsing.*;
import java.util.Map;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private String path;
	private HuffmanEncodedResult h = null;
	private String XML="";
    boolean check = false;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private ArrayList<String> s= new ArrayList<String>();
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JButton btnNewButton_5;
	private BufferedReader br = null;
	private int startIndex = 0, endIndex = 0;
	private DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
	private JButton btnNewButton_8;
	private SNA sna = null;
	private StringBuilder sb = new StringBuilder();
	private Tree tree = new Tree();
	ArrayList<String> xml = new ArrayList<String>();
	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("XML Reader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.setPreferredSize(new Dimension(50, 40));
		contentPane.setVisible(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(500, 84, 470, 570);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(15, 84, 470, 570);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane_1.setViewportView(textArea_1);
		
		btnNewButton = new JButton("Read File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.getHighlighter().removeAllHighlights();
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
		btnNewButton.setBounds(15, 10, 130, 20);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Error detection");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				Validator v = new Validator();
				ArrayList<Integer> a=new ArrayList<Integer>();
				int error_line=0;String errorin="";
		        Pattern slash = Pattern.compile("-");
		        Pattern underscore = Pattern.compile("_");
		        int indexofslash = 0;
				try {
					s = v.Fix_Missing_Bracket_Error(get_XML_file());
					a.addAll(v.Bracket_isCorrect(get_XML_file()));				
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] str = new String[s.size()];
			    for (int i = 0; i < s.size(); i++) {
			            str[i] = s.get(i);
			        }
				errorin =v.tag_is_correct(str);
		        Matcher matcher4 = slash.matcher(errorin);
		        Matcher matcher5 = underscore.matcher(errorin);
	            while (matcher4.find() && matcher5.find()) {
	                if (errorin.contains("^")) {
	                    indexofslash = matcher4.start();
	                    error_line = Integer.parseInt(errorin.substring(errorin.indexOf("^") + 1, matcher5.start()));
	                    a.add(error_line);
	                    errorin = errorin.substring(errorin.indexOf("^") + 1,errorin.length());
	                    a.add(error_line);
	                }
	                else {
	                    error_line = Integer.parseInt(errorin.substring(indexofslash , matcher5.start()-1));
	                    a.add(error_line);
	                    indexofslash = matcher4.start();
	                }
	            }
				try {

					for(int i=0; i<a.size();i++){
						int space_counter =0;
						while (s.get(a.get(i)).charAt(space_counter) == ' ') {        /*get number of spaces before the tag*/
			                space_counter++;
			            }
						(startIndex) = textArea_1.getLineStartOffset(a.get(i));
						(endIndex) = textArea_1.getLineEndOffset(a.get(i));
						textArea_1.getHighlighter().addHighlight(startIndex+space_counter, endIndex, painter);
					}
					if(a.isEmpty()) {
						textArea.append("XML is valid");
						
					}
					
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(160, 10, 130, 20);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Error Correction");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				Validator V = new Validator();
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
				s=V.adding_missing_tag(t, str);
				for(int i=0; i<s.size(); i++) {
					textArea.append(s.get(i)+ '\n');
				}
			}
		});
		btnNewButton_2.setBounds(305, 10, 130, 20);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("XML2JSON");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				textArea.setText(null);
				Tree tree = new Tree();
				StringBuilder sb = new StringBuilder();
				try {
				tree.insertXML(new FileInputStream(path));				
				XML2JSON xml2JSON = new XML2JSON(sb,0,XML);
				xml2JSON.Convert(tree.getTreeRoot());
				xml2JSON.createJsonFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textArea.append(sb.toString());

		}});
		btnNewButton_3.setBounds(450, 10, 130, 20);
		contentPane.add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Prettifying");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				try {
				tree.insertXML(new FileInputStream(path));				
				Prettify pretty = new Prettify(sb);
				sb =pretty.prettify(tree.getTreeRoot());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textArea.append(sb.toString());

		}
		});
		btnNewButton_4.setBounds(595, 10, 130, 20);
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
				JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Save a File");
				 fileChooser.setFileFilter(new FileTypeFilter(".txt","Text File"));
				 int res = fileChooser.showSaveDialog(null);
				 if(res == JFileChooser.APPROVE_OPTION) {
				File fi = fileChooser.getSelectedFile();
				Huffman huffman = new Huffman(fi.getAbsolutePath()+" compressed");
				final int[] table =huffman.buildFrequencyTable(s2);
				final NodeCD n =huffman.buildHuffmanTree(table);
				final Map<Character,String> lookUpTable = huffman.buildLookUpTable(n);
				try {
					h = huffman.compress(s2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//System.out.println(h.encodedData);
			}
		}});
		btnNewButton_5.setBounds(160, 40, 130, 20);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Decompression");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			textArea.setText(null);
			JFileChooser fileChooser = new JFileChooser();
			 fileChooser.setDialogTitle("Choose a File");
			 fileChooser.setFileFilter(new FileTypeFilter(".txt","Text File"));
			 int res = fileChooser.showSaveDialog(null);
			 if(res == JFileChooser.APPROVE_OPTION) {
			File fi = fileChooser.getSelectedFile();
			Huffman huffman = new Huffman(fi.getAbsolutePath());
			String s = null;
			try {
				s = huffman.decompress(h.root, fi.getAbsolutePath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        String decompressedPath = null;
			try {
				decompressedPath = CreateFile.generateOutputDecompressed(fi.getAbsolutePath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        CreateFile.writeToDecFile(decompressedPath,s);


	        FileInputStream fileInputStream2 = null;
			try {
				fileInputStream2 = new FileInputStream(decompressedPath);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        Tree tree2 = new Tree();
	        try {
				tree2.insertXML(fileInputStream2);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        Prettify p = new Prettify(new StringBuilder());
	        StringBuilder prettified=p.prettify(tree2.getTreeRoot());
	        CreateFile.clearFile(decompressedPath);
	        CreateFile.writeToDecFile(decompressedPath,prettified.toString());
	        textArea.append(prettified.toString());
			}
		}});
		btnNewButton_6.setBounds(305, 40, 130, 20);
		contentPane.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Save File");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Save a File");
				 fileChooser.setFileFilter(new FileTypeFilter(".txt","Text File"));
				 int res = fileChooser.showSaveDialog(null);
				 if(res == JFileChooser.APPROVE_OPTION) {
					 String content = textArea.getText();
					 File fi = fileChooser.getSelectedFile();
					 try {
						FileWriter fw = new FileWriter(fi.getPath());
						fw.write(textArea.getText());
						fw.flush();
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 }
				 
             }

		});
		btnNewButton_7.setBounds(15, 40, 130, 20);
		contentPane.add(btnNewButton_7);
		
		btnNewButton_8 = new JButton("Graph Analysis");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sna = new SNA(path, XML);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GUI1();
			}
		});
		btnNewButton_8.setBounds(450, 40, 130, 20);
		contentPane.add(btnNewButton_8);
		
		
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
	public void GUI1() {
		JFrame jframe = new JFrame(("Graph Analysis"));
		jframe.setVisible(true);
		jframe.setBounds(400, 150, 620, 480);
		JPanel contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));			
		contentPane1.setPreferredSize(new Dimension(50, 40));
		contentPane1.setVisible(true);
		contentPane1.setLayout(null);
		jframe.setContentPane(contentPane1);
		
		JScrollPane scrollPane1 = new JScrollPane();
		JTextField textField = new JTextField();
		JLabel label = new JLabel("USER 1");
		scrollPane1.setBounds(20, 220, 565, 200);
		contentPane1.add(scrollPane1);
		label.setBounds(165, 75, 110, 30);
		textField.setBounds(215, 75, 160,30);
		jframe.getContentPane().add(label);
		jframe.getContentPane().add(textField);
		
		JLabel label1 = new JLabel("USER 2");
		JTextField textField1 = new JTextField();
		JLabel label2 = new JLabel("Mutual Followers");
		label2.setFont(new Font("Dialog.bold", 5,13));
		label1.setBounds(380, 75, 110, 30);
		textField1.setBounds(430, 75, 160,30);
		jframe.getContentPane().add(label1);
		jframe.getContentPane().add(textField1);
		label2.setBounds(35, 35, 110, 30);
		jframe.getContentPane().add(label2);
		
		JTextArea textArea1 = new JTextArea();
		textArea1.setEditable(false);
		scrollPane1.setViewportView(textArea1);
		
		JLabel label3 = new JLabel("Search by");
		label3.setBounds(50, 125, 110, 30);
		label3.setFont(new Font("Dialog.bold", 5,15));
		jframe.getContentPane().add(label3);
		
		JTextField textField2 = new JTextField();
		textField2.setBounds(130, 160, 300, 40);
		jframe.getContentPane().add(textField2);
		
		JCheckBox checkbox= new JCheckBox("Body");
		checkbox.setBounds(50, 150, 80, 30);
		jframe.getContentPane().add(checkbox);
		JCheckBox checkbox1= new JCheckBox("Topic");
		checkbox1.setBounds(50, 175, 80, 30);
		jframe.getContentPane().add(checkbox1);
		//ButtonGroup group = new ButtonGroup();
		 
		 
		checkbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==1) {
					try {
						//char k =PostSearch.searchKey();
						//String inputWord =PostSearch.matchWord();
						checkbox1.setEnabled(false);
						textArea1.setText(null);
						PostSearch ps =  new PostSearch(sb.toString());
						StringBuilder s =PostSearch.searchPost(textField2.getText(),'1');
						textArea1.append(s.toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					textArea1.setText(null);
					checkbox1.setEnabled(true);
				}
			}});

		checkbox1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==1) {
					try {
						checkbox.setEnabled(false);
						textArea1.setText(null);
						PostSearch ps =  new PostSearch(sb.toString());
						StringBuilder s =PostSearch.searchPost(textField2.getText(),'2');
						textArea1.append(s.toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					textArea1.setText(null);
					checkbox.setEnabled(true);
				}
			}});

		JButton btnNewButton_9 = new JButton("Most Active");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> s =sna.mostActive();
				textArea1.setText(null);
				for(int i=0;i<s.size(); i++) {
					textArea1.append(s.get(i)+"\n");
				}
				
			}});
		btnNewButton_9.setBounds(20, 10, 140, 20);
		contentPane1.add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("Most Influencer");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> s =sna.mostInfluencer();
				textArea1.setText(null);
				for(int i=0;i<s.size(); i++) {
					textArea1.append(s.get(i)+"\n");
				}
			}});
		btnNewButton_10.setBounds(165, 10, 140, 20);
		contentPane1.add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("By Names");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> s = sna.mutualFollowers(textField.getText(), textField1.getText());
				textArea1.setText(null);
				for(int i=0;i<s.size(); i++) {
					textArea1.append(s.get(i)+"\n");
				}
			}});
		btnNewButton_11.setBounds(20, 65, 140, 20);
		contentPane1.add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("By Ids");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> s = sna.mutualFollowers(Integer.valueOf(textField.getText()), Integer.valueOf(textField1.getText()));
				textArea1.setText(null);
				for(int i=0;i<s.size(); i++) {
					textArea1.append(s.get(i)+"\n");
				}
			}});
		btnNewButton_12.setBounds(20, 95, 140, 20);
		contentPane1.add(btnNewButton_12);
		
		JButton btnNewButton_14 = new JButton("Suggest Followers");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> s = sna.suggestUsers();
				textArea1.setText(null);
				for(int i=0;i<s.size(); i++) {
					textArea1.append(s.get(i));
				}
			}});
		btnNewButton_14.setBounds(310, 10, 140, 20);
		contentPane1.add(btnNewButton_14);
		
		JButton btnNewButton_13 = new JButton("Draw Connection");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sna.drawConnections();
				textArea1.setText(null);
			}});
		btnNewButton_13.setBounds(455, 10, 140, 20);
		contentPane1.add(btnNewButton_13);
		
	}
}