package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * This Class represents an undirectional weighted graph.
 * It support a large number of nodes (over 10^6, with average degree of 10).
 * The implementation based on an efficient compact representation
 * That Class contains inner Class called NodeInfo that represents Vertex in the graph
 */
public class WGraph_DS implements weighted_graph, java.io.Serializable {
    private HashMap<Integer, HashMap<node_info, Double>> edgesOfGraph;
    private HashMap<Integer, node_info> verticesOfGraph;
    private int verticesSize, edgeSize, modeCount;


    /**
     * Inner Class that represents Vertex in the weighed graph .
     */
    private static class NodeInfo implements node_info, java.io.Serializable {
        private int key;
        private String info;
        private double tag;

        //CONSTRUCTOR
        public NodeInfo(int key) {
            this.key = key;
            this.tag = Integer.MAX_VALUE;
            this.info = "";
        }

        //COPY CONSTRUCTOR
        public NodeInfo(node_info n) {
            this.key = n.getKey();
            this.tag = n.getTag();
            this.info = n.getInfo();
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * Indicates whether some other node_info is "equal to" this one.
         *
         * @param o
         * @return true if equals and false if not .
         */
        @Override
        public boolean equals(Object o) {
            NodeInfo nodeInfo = (NodeInfo) o;
            return (this.key == nodeInfo.getKey());
        }

        /**
         * Returns a hash code value for the node_info.
         * This method is supported for the benefit of hash tables such as those provided by HashMap.
         *
         * @return int
         */
        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return "" + key;
        }
    }

    //CONSTRUCTOR
    public WGraph_DS() {
        this.verticesSize = 0;
        this.edgeSize = 0;
        this.modeCount = 0;
        this.verticesOfGraph = new HashMap<Integer, node_info>();
        this.edgesOfGraph = new HashMap<Integer, HashMap<node_info, Double>>();
    }

    /**
     * Copy CONSTRUCTOR
     *
     * @param wg
     */
    //Copy CONSTRUCTOR
    public WGraph_DS(weighted_graph wg) {
        double weight = 0.0;
        this.verticesOfGraph = new HashMap<Integer, node_info>();
        this.edgesOfGraph = new HashMap<Integer, HashMap<node_info, Double>>();
        for (node_info node : wg.getV()) {
            node_info tempNode = new NodeInfo(node);
            this.verticesOfGraph.put(tempNode.getKey(), tempNode);
            HashMap tempMap = new HashMap<node_info, Double>();
            this.edgesOfGraph.put(node.getKey(), tempMap);
        }
        for (node_info node : wg.getV()) {
            for (node_info neighbor : wg.getV(node.getKey())) {
                weight = wg.getEdge(node.getKey(), neighbor.getKey());
                this.connect(node.getKey(), neighbor.getKey(), weight);
            }
        }
        this.verticesSize = wg.nodeSize();
        this.edgeSize = wg.edgeSize();
        this.modeCount = wg.getMC();
    }

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (verticesOfGraph.containsKey(key)) {
            return verticesOfGraph.get(key);
        }
        return null;
    }


    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return true if there is edge between node1 , node2 false otherwise .
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (this.getNode(node1) == null) return false;
        if (edgesOfGraph.containsKey(node1)) {
            if (edgesOfGraph.get(node1).containsKey(this.getNode(node2))) {
                return edgesOfGraph.get(node1).containsKey(this.getNode(node2));
            }
        }
        return false;
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge -  return -1
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            return edgesOfGraph.get(node1).get(this.getNode(node2));
        }
        return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!(verticesOfGraph.containsKey(key))) {
            node_info addedNode = new NodeInfo(key);
            verticesOfGraph.put(key, addedNode);
            HashMap tempMap = new HashMap<node_info, Double>();
            edgesOfGraph.put(key, tempMap);
            this.verticesSize++;
            this.modeCount++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 != node2) {
            if (verticesOfGraph.containsKey(node1) && verticesOfGraph.containsKey(node2)) {
                if (this.edgesOfGraph.containsKey(node1)) {
                    if (!(this.edgesOfGraph.get(node1).containsKey(this.getNode(node2)))) {
                        this.edgesOfGraph.get(node1).put(this.getNode(node2), w);
                        this.edgesOfGraph.get(node2).put(this.getNode(node1), w);
                        this.edgeSize++;
                        this.modeCount++;
                    } else if (edgesOfGraph.get(node1).get(this.getNode(node2)) != w) {
                        edgesOfGraph.get(node1).put(this.getNode(node2), w);
                        edgesOfGraph.get(node2).put(this.getNode(node1), w);
                        this.modeCount++;
                    }
                }
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return verticesOfGraph.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @return Collection<node_data>
     */

    @Override
    public Collection<node_info> getV(int node_id) {
        if (verticesOfGraph.containsKey(node_id)) {
            return edgesOfGraph.get(node_id).keySet();
        }
        return null;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (verticesOfGraph.containsKey(key)) { //check if this node is in the graph.
            node_info node = this.getNode(key); //remove the node from the graph and save it in temp node to remove his edges
            for (node_info n : this.getV(key)) {
                edgesOfGraph.get(n.getKey()).remove(this.getNode(key));
                edgeSize--;
                modeCount++;
            }
            node = verticesOfGraph.remove(key);
            verticesSize--;
            modeCount++;
            edgesOfGraph.remove(key);
            return node;
        }
        return null;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (verticesOfGraph.containsKey(node1)) {
            if (edgesOfGraph.get(node1).containsKey(this.getNode(node2))) {
                edgesOfGraph.get(node1).remove(this.getNode(node2));
                edgesOfGraph.get(node2).remove(this.getNode(node1));
                this.edgeSize--;
                this.modeCount--;
            }
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return verticesSize;
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method run in O(1) time.
     *
     * @return
     * @Override
     */
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return modeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return this.nodeSize() == wGraph_ds.nodeSize() &&
                this.edgeSize == wGraph_ds.edgeSize() && this.getMC() == wGraph_ds.getMC() &&
                Objects.equals(edgesOfGraph, wGraph_ds.edgesOfGraph) &&
                Objects.equals(verticesOfGraph, wGraph_ds.verticesOfGraph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edgesOfGraph, verticesOfGraph, verticesSize, edgeSize, modeCount);
    }

    @Override
    public String toString() {
        String str = "";
        for (Integer x : edgesOfGraph.keySet()) {
            str += "" + x + "{ " + getNode(x).getTag() + " }--> [";
            for (node_info i : edgesOfGraph.get(x).keySet()) {
                NodeInfo n = new NodeInfo((NodeInfo) i);
                str += n.toString() + " (" + edgesOfGraph.get(x).get(n) + ") , ";//EdgeMap.get(x).keySet().toString() + " \n ";
            }
            str += "] \n";
        }
        return str + " ";
    }

}
