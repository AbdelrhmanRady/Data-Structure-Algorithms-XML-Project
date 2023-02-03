package compression;

import java.util.Comparator;

class Comparing implements Comparator<NodeCD> {
    @Override
    public int compare(NodeCD n1, NodeCD n2) {
        return n1.getFrequency()-n2.getFrequency();

    }
}
