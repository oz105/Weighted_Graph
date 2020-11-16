package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms ,java.io.Serializable {
    private weighted_graph wGraphAlgo ;

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.wGraphAlgo= g ;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return wGraphAlgo;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph deepCopy = new WGraph_DS(wGraphAlgo) ;
        return deepCopy ;
    }
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * Used BFS algorithm instead of dijkstra algorithm because BFS is more efficient
     * and we only need to know here if it is connected.
     * @return
     */
    @Override
    public boolean isConnected() {
        if(wGraphAlgo.nodeSize() == 0 ) return true ;
        if(wGraphAlgo.nodeSize() == 1 ) return true ;
        if(wGraphAlgo.nodeSize() > wGraphAlgo.edgeSize() + 2 ) return false ;
        int key = 0 ;
        int countVisit = 0 ;
        for (node_info n : wGraphAlgo.getV()){
            key = n.getKey() ;
            break ;
        }
//        countVisit = Dijkstra(wGraphAlgo.getNode(key));
        countVisit = BFS(key);
        if(countVisit == wGraphAlgo.nodeSize()) return true ;
        return false;
    }

    /**
     * returns the length of the shortest path between src to dest
     * use in dijkstra algorithm.
     * Note: if no such path --> returns -1
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if((wGraphAlgo.getNode(src) != null) && (wGraphAlgo.getNode(dest) != null )){
            if(src==dest) return 0 ;
            Dijkstra(wGraphAlgo.getNode(src));
            if(wGraphAlgo.getNode(dest).getTag() != Integer.MAX_VALUE){
                return wGraphAlgo.getNode(dest).getTag() ;
            }
        }
        return -1 ;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Use in dijkstra algorithm.
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(wGraphAlgo.getNode(src) == null || wGraphAlgo.getNode(dest) == null ) return null  ;
        int tempkey = dest ;
        boolean flag = true ;
        LinkedList<node_info> path = new LinkedList<node_info>() ;
        Dijkstra(wGraphAlgo.getNode(src));
        if(!(wGraphAlgo.getNode(dest).getTag() < Integer.MAX_VALUE)) return null ;
        if(src==dest) {
            path.add(wGraphAlgo.getNode(src));
            return path ;
        }
        path.addLast(wGraphAlgo.getNode(dest)); ;
        while (flag) {
            if(tempkey == src){
                flag = false ;
            }
            else if(NumberOrNot(wGraphAlgo.getNode(tempkey).getInfo())){
                tempkey = Integer.parseInt(wGraphAlgo.getNode(tempkey).getInfo());
                path.addFirst(wGraphAlgo.getNode(tempkey)); ;
            }
        }
        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name.
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file) ;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream) ;
            objectOutputStream.writeObject(wGraphAlgo);
            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
        return true ;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.wGraphAlgo = (weighted_graph) (objectInputStream.readObject());
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Dijkstra Algorithm
     * in this method we will mark all the nodes as unvisited (empty info = unvisited , info = "")
     * and we will mark the weight of every node as infinity (Tag = Integer.MAX_VALUE)
     * we will create a PriorityQueue that will be give Priority base on the smallest weight
     * During the algorithm for every node we will saved 2 things
     * his weight form the src node - this will be store in the Tag
     * and from who he gets that weight - this will be store in the info (the key of the node)
     * and when we first visit in some node (means his info is empty)
     * we add him to the PriorityQueue .
     * the algorithm ends when the PriorityQueue is empty .
     * In the end of the algorithm each node will hold 2 things
     * 1.the smallest weight from src node - will be store in the Tag.
     * 2.from who he gets this weight - will be store in the Info.
     * if the Info contains a number (the key from who he gets the weight its means he visited).
     * @param src
     * @return
     */
    public int Dijkstra (node_info src) {
        double weight = 0 ;
        int countVisit = 0;
        node_info tempKey ;
        PriorityQueue<node_info> PQ = new PriorityQueue<node_info>(new node_infoCompereByTag());
        for (node_info n : wGraphAlgo.getV()) {
            n.setTag(Integer.MAX_VALUE);
            n.setInfo("");
        }
        if(wGraphAlgo.getNode(src.getKey()) != null ) {
            src.setTag(0);
            src.setInfo(""+src.getKey());
            PQ.add(src) ;
            countVisit ++ ;
        }
        while(!(PQ.isEmpty())) {
            if(PQ.peek() != null) {
                tempKey = PQ.poll();
                for (node_info n : wGraphAlgo.getV(tempKey.getKey())) {
                   weight = tempKey.getTag() + (wGraphAlgo.getEdge(n.getKey(),tempKey.getKey())) ;
                   if(!(NumberOrNot(n.getInfo()))) {
                       PQ.add(n) ;
                       n.setTag(weight);
                       n.setInfo(""+tempKey.getKey());
                       countVisit ++ ;
                    }
                   else if(n.getTag() > weight) {
                       n.setTag(weight);
                       n.setInfo(""+tempKey.getKey());
                   }
                }
            }
        }
        return countVisit ;
    }

    /**
     * this function get a string
     * and return true iff(if and only if) there is only number in the String
     * return false otherwise.
     * this function will be used in Dijkstra algorithm.
     * @param s
     * @return
     */
    public boolean NumberOrNot (String s) {
        try { Integer.parseInt(s) ; }
        catch (NumberFormatException e) { return false ; }
        return true ;
    }

    /**
     * The BFS Method
     * This method should be run over all the vertices if the graph is connected.
     * and when it get to node it changes the tag of the node to 1 .
     * this method will be used in the function is connected .
     * if all the tags changes to 1 we will return true.
     * and if even 1 tag not equal to 1 we will return false.
     * @param key we start to run on the graph from this node .
     * @return countVisit - the number of vertices the BFS visited .
     */
    public int BFS(int key) {
        for (node_info n : wGraphAlgo.getV()) {
            n.setTag(-1);
        }
        int countVisit = 0;
        int tempKey = 0;
        Queue verticesQueue = new LinkedList<Integer>();
        verticesQueue.add(key);
        if(wGraphAlgo.getNode(key) != null ) {
            wGraphAlgo.getNode(key).setTag(1);
            countVisit ++ ;
        }
        while (!(verticesQueue.isEmpty())) {
            if (verticesQueue.peek() != null) {
                tempKey = Integer.parseInt("" + verticesQueue.poll());
                node_info temp = wGraphAlgo.getNode(tempKey);
                if(temp != null ){
                    for (node_info node : wGraphAlgo.getV(tempKey)) {
                        if (node.getTag() < 0) {
                            verticesQueue.add(node.getKey());
                            node.setTag(1);
                            countVisit++;
                        }
                    }
                }
            }
        }
        return countVisit;
    }
}