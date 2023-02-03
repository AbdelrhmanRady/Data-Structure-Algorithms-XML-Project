package sna_graph;


import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class PostSearch {
    public static String reader;
    public static HashMap<Integer, String> publishers;
    public static int index = 0;


    public PostSearch(String reader) throws IOException {
        this.reader = reader;
        //fillPublishers();
    }


    public void fillPublishers() throws IOException {
        XMLtoGraph xtg = new XMLtoGraph();
        //xtg.convert();
        publishers = xtg.getName();
    }

    // to determine whether user will search based on topic or body.
    public static char searchKey() {
        char x;

        Scanner sc = new Scanner(System.in);
        System.out.println("1: search by body...");
        System.out.println("2: search by topic...");
        x = sc.next().charAt(0);


        return x;
    }

    // word to search for
    public static String matchWord() {
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter word to search: ");
        s = sc.nextLine();
        return s;
    }

    // reading input XML file line by line
    public static String[] readFromFileToStringArray(String s) throws IOException {
//        String text = new String(Files.readAllBytes(Paths.get(s)),
//                StandardCharsets.UTF_8);
        String text = s;
        return text.split("\n");
    }

    public static StringBuilder searchPost(String word, char x) throws IOException {

        String wordLowerCase = word.toLowerCase();
        boolean wordFound = false;
        boolean topicFound = false;
        boolean first = false;
        String[] line = readFromFileToStringArray(reader);
        int size = line.length - 1;
        String name = "";
        StringBuilder r= new StringBuilder();


        if (x == '1') {
            for (int j = 0; j < size; j++) {

                if (line[j].contains("<name>")) {
                    for (int i = j + 1; !line[i].contains("</name>"); i++) {
                        name = line[i].trim();
                    }
                }


                if (line[j].contains("<body>")) {
                    for (int i = j + 1; !line[i].contains("</body>"); i++) {
                        if (line[i].toLowerCase().contains(wordLowerCase)) {
                            wordFound = true;
                            first = true;
                            break;
                        }
                    }
                }

                if (wordFound) {
                    int t = line[j].indexOf(line[j].lastIndexOf("</body>"));

                    for (int i = j + 1; !line[i].contains("</body>"); i++) {
                        System.out.print(line[i].trim());
                        r.append(line[i].trim());
                    }


                    System.out.println();

                    System.out.println("----------published by: " + name + " ----------");
                    r.append("\n----------published by: " + name + " ----------\n\n");
                    wordFound = false;
                }
            }
        } else if (x == '2') {
            for (int j = 0; j < size; j++) {
                if (line[j].contains("<name>")) {
                    for (int i = j + 1; !line[i].contains("</name>"); i++) {
                        name=line[i].trim();
                    }
                }
                if (line[j].contains("<body>")) {
                    for (int i = j + 1; !line[i].contains("</topics>"); i++) {
                        if (line[i].contains("<topic>")) {
                            if (line[i + 1].toLowerCase().contains(wordLowerCase)) {
                                topicFound = true;
                                first = true;
                                break;
                            }
                        }
                    }
                }

                if (topicFound == true) {
                    for (int i = j + 1; !line[i].contains("</body>"); i++) {
                        System.out.print(line[i].trim());
                        r.append(line[i].trim());
                    }
                    System.out.println();
                    System.out.println("----------published by: " + name + " ----------");
                    r.append("\n----------published by: " + name + " ----------\n\n");
                    topicFound = false;
                }
            }

        }

        if (!first) {
            System.out.println("no matches found");
            r.append("no matches found");
        }
		return r;

    }


}
