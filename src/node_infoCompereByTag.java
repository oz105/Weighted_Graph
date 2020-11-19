package ex1.src;

import java.util.Comparator;

public class node_infoCompereByTag implements Comparator<node_info> {

    @Override
    public int compare(node_info node1, node_info node2) {
        return Double.compare(node1.getTag(),node2.getTag()) ;
    }
}
