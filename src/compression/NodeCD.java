package compression;

import parsing.Node;

public class NodeCD implements Comparable<NodeCD> {

    private final char character;
    private final int frequency;
    private NodeCD leftNode;
    private NodeCD rightNode;

    public NodeCD(final char character,final int frequency,final  NodeCD leftNode,final  NodeCD rightNode) {
        this.character = character;
        this.frequency = frequency;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public int compareTo(NodeCD node) {

        final int freqComparison =Integer.compare(frequency,node.getFrequency());
        if(freqComparison!=0){
            return  freqComparison;
        }
            return Integer.compare(this.character,node.character);
    }

    public int getFrequency() {
        return frequency;
    }

    public NodeCD getLeftNode() {
        return leftNode;
    }

    public NodeCD getRightNode() {
        return rightNode;
    }

    boolean isLeaf(){
     return this.leftNode ==null && this.rightNode==null;
    }
}
