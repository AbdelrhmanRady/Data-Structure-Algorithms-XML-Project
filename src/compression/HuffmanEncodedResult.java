package compression;

public class HuffmanEncodedResult {
     public String encodedData;
      public NodeCD root;

    public HuffmanEncodedResult( String encodedData, NodeCD root) {
        this.encodedData = encodedData;
        this.root = root;
    }
}
