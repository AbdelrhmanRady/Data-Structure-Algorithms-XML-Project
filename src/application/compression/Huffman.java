package application.compression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;




public class Huffman {
    private static final int MAX_SIZE=256;
    private static String path=" ";

    public Huffman(String path){
        this.path = path;
    }


    public  HuffmanEncodedResult compress(final String data) throws IOException {
        final int [] freq = buildFrequencyTable(data);
        final NodeCD root = buildHuffmanTree(freq);
        final Map<Character,String> lookUpTable = buildLookUpTable(root);
        HuffmanEncodedResult result =new HuffmanEncodedResult(generateEncodedData(data,lookUpTable),root);
        CreateFile.writeBinaryToFile(result.encodedData,path);
    return result;

    }


    private String generateEncodedData(final String data,final Map<Character, String> lookUpTable) {
    final StringBuilder sb = new StringBuilder();
    for(final char character:data.toCharArray()){
        sb.append(lookUpTable.get(character));
    }
    return sb.toString();

    }

    /*
    we are going to map each character into binary encoding and this code depends on
    frequency of character (more repeats, less binary encoding length)
    */
    public Map<Character,String> buildLookUpTable(final NodeCD root){
        final Map<Character,String> lookUpTable = new HashMap<>();
        buildLookUpTablehelper(root,"",lookUpTable);//helper method
        return lookUpTable;
    }

    private  void buildLookUpTablehelper(NodeCD node, String s, Map<Character, String> lookUpTable) {
        if(!node.isLeaf()){
            buildLookUpTablehelper(node.getLeftNode(),s+'0',lookUpTable);
            buildLookUpTablehelper(node.getRightNode(),s+'1',lookUpTable);
        }else{
            lookUpTable.put(node.getCharacter(),s);
        }
    }

    public  int[] buildFrequencyTable(final String data){
        final int[] freq = new int[MAX_SIZE];
        for(final char character:data.toCharArray()){
            freq[character]++;
        }
        return freq;

    }

    public NodeCD buildHuffmanTree(int [] freq){
        final PriorityQueue<NodeCD> priorityQueue= new PriorityQueue<NodeCD>(256,new Comparing());
        for(char i=0;i<MAX_SIZE;i++){
            if(freq[i] > 0 ){
                priorityQueue.add(new NodeCD(i,freq[i],null,null));
            }
        }
        if(priorityQueue.size()==1){ //message with one character
            priorityQueue.add(new NodeCD('\0',1,null,null));
        }

        while( priorityQueue.size()>1 ){
            //getting next two leaf nodes from pq
            final NodeCD left = priorityQueue.poll();
            final NodeCD right = priorityQueue.poll();

            //merging them in one parent node
            final NodeCD parent= new NodeCD('\0', left.getFrequency()+right.getFrequency(),left,right); // '\0' indicates it's not a leaf node
            priorityQueue.add(parent);
        }
        return  priorityQueue.poll();

    }


    //decompressing
public  String decompress(final NodeCD result,String path2) throws IOException {



        String read = CreateFile.readBinaryFromFile(path2);


        final StringBuilder resultBuilder = new StringBuilder();
        NodeCD current = result;
        int i=0;
        while(i<read.length()){
            while(!current.isLeaf()){
                char bit = read.charAt(i);
                if(bit=='1'){
                    current=current.getRightNode();
                }else if(bit=='0'){
                    current=current.getLeftNode();
                }
                else{
                    throw new IllegalArgumentException("invalid bit exists!"+bit);
                }
                i++;
            }
            resultBuilder.append(current.getCharacter());
            current = result;
        }

        return resultBuilder.toString();
}















}




//    public void printCodes() {
//        huffmanCodes.forEach(((character, code) -> System.out.println(character + " : " + code)));
//
//    }
