package compression;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateFile {


    public static void writeBinaryToFile(String binary, String filePath) throws IOException {
        FileOutputStream fileOS = new FileOutputStream(filePath + ".txt");
        DataOutputStream dataOS = new DataOutputStream(fileOS);
        byte flag = 0;
        byte toByte = 0;
        byte count = 7;
        byte remainder = (byte) (binary.length() % 8);
        if (remainder != 0) {
            flag = (byte) (8 - remainder);
        }
        dataOS.writeByte(flag);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                toByte |= (1 << count);
            }
            // everytime you form 8 bits write them as a byte to the file
            if (count == 0) {
                dataOS.writeByte(toByte);
                toByte = 0;
                count = 8;
            }
            count--;
        }
        // the last byte will be padded by zeros equal to the flag value
        dataOS.writeByte(toByte);
        dataOS.close();
    }

    public static StringBuilder charToBinaryString(char c) {
        StringBuilder result = new StringBuilder();
        String binary = Integer.toBinaryString(c & 0xFF);
        for (int i = 0; i < (8 - binary.length()); i++) {
            result.append('0');
        }
        result.append(binary);
        return result;
    }

    public static String readBinaryFromFile(String filePath) throws IOException {
        FileInputStream fileIS = new FileInputStream(filePath);
        DataInputStream dataIS = new DataInputStream(fileIS);
        StringBuilder encoded = new StringBuilder();
        char[] read = new char[dataIS.available()];
        int count = 0;
        while (dataIS.available() > 0) {
            read[count++] = (char) dataIS.readByte();
        }
        dataIS.close();
        char flag = read[0];
        for (int i = 1; i < count; i++) {
            encoded.append(charToBinaryString(read[i]));
        }
        if (flag != 0) {

            encoded.delete(encoded.length() - flag, encoded.length());
        }
        return encoded.toString();
    }


    public static String generateOutputDecompressed(String p) throws IOException {
        String path = p + " decompressed.txt";
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return path;
    }

    public static void writeToDecFile(String path, String s) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(s);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void clearFile(String path) {

        try {

            FileWriter fw = new FileWriter(path, false);

            PrintWriter pw = new PrintWriter(fw, false);

            pw.flush();

            pw.close();

            fw.close();

        } catch (Exception exception) {

            System.out.println("Exception have been caught");

        }

    }

}