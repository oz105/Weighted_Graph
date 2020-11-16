package ex1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    private weighted_graph g ;
    private weighted_graph emptyGraph ;
    private weighted_graph fullGraph5 ;
    private weighted_graph randomGraph ;
    private weighted_graph_algorithms algo ;
    private Random rand ;



    weighted_graph smallGraph () {
        weighted_graph small = new WGraph_DS() ;
        for (int i = 0; i < 15 ; i++) {
            small.addNode(i);
        }
        small.connect(0,7,2);
        small.connect(0,2,4);
        small.connect(0,5,3);
        small.connect(0,8,4);
        small.connect(1,6,2);
        small.connect(1,14,3);
        small.connect(1,8,4);
        small.connect(2,3,1);
        small.connect(2,9,5);
        small.connect(3,11,4);
        small.connect(4,9,2);
        small.connect(4,14,3);
        small.connect(5,10,2);
        small.connect(7,12,3);
        small.connect(7,13,1);
        small.connect(8,11,6);
        return small ;
    }
    /**
     * build a specific graph that I know all about.
     * To perform a short-distance weight test and a short-distance test list.
     */
    @BeforeEach
    void setupGraphs() {
        algo = new WGraph_Algo() ;
        randomGraph = new WGraph_DS();
        g = new WGraph_DS();
        for (int i = 1; i < 11; i++) {
            g.addNode(i);
        }
        g.connect(1, 8, 5);
        g.connect(1, 3, 2);
        g.connect(1, 10, 1);
        g.connect(3, 9, 4);
        g.connect(9, 10, 3);
        g.connect(3, 6, 3);
        g.connect(7, 8, 2);
        g.connect(2, 6, 3);
        g.connect(2, 4, 2);
        g.connect(4, 5, 1);
        emptyGraph = new WGraph_DS();
        fullGraph5 = setupFullGraph(5);
    }

    /**
     * Create graph unConnected with (nodes -2 ) edges .
     *
     * @param vSize - the size of nodes we want in the graph .
     * @param seed
     * @return
     */
    weighted_graph createGraphUnConnected(int vSize, int seed) {
        weighted_graph wg = new WGraph_DS();
        rand = new Random(seed);
        for (int i = 0; i < vSize; i++) {
            wg.addNode(i);
        }
        while (wg.edgeSize() < vSize - 2) {
            wg.connect((int) (Math.random() * vSize), (int) (Math.random() * vSize), 1);
        }
        return wg;
    }

    /**
     * This method init a full graph with the size of nodes we give .
     *
     * @param fullSize
     */
    weighted_graph setupFullGraph(int fullSize) {
        weighted_graph fullGraph = new WGraph_DS();
        for (int i = 0; i < fullSize; i++) {
            fullGraph.addNode(i);
        }
        for (int i = 0; i < fullSize; i++) {
            for (int j = i + 1; j < fullSize; j++) {
                fullGraph.connect(i, j, 1);
            }
        }
        return fullGraph;
    }


    @Test
    void init() {
        algo.init(emptyGraph);
        assertAll(
                () -> assertEquals(0, algo.getGraph().edgeSize()),
                () -> assertEquals(0, algo.getGraph().nodeSize())
        );
        algo.init(g);
        assertAll(
                () -> assertEquals(10, algo.getGraph().edgeSize()),
                () -> assertEquals(10, algo.getGraph().nodeSize())
        );
        algo.init(fullGraph5);
        assertAll(
                () -> assertEquals(10, algo.getGraph().edgeSize()),
                () -> assertEquals(5, algo.getGraph().nodeSize())
        );
    }

    @Test
    void getGraph() {
        algo.init(emptyGraph);
        assertAll(
                () -> assertEquals(emptyGraph, algo.getGraph()),
                () -> assertEquals(emptyGraph.getMC(), algo.getGraph().getMC())
        );
        algo.init(g);
        assertAll(
                () -> assertEquals(g, algo.getGraph()),
                () -> assertEquals(g.getMC(), algo.getGraph().getMC())
        );
        algo.init(fullGraph5);
        assertAll(
                () -> assertEquals(fullGraph5, algo.getGraph()),
                () -> assertEquals(fullGraph5.getMC(), algo.getGraph().getMC())
        );
    }

    @Test
    void copy() {
        algo.init(emptyGraph);
        weighted_graph deepCopyG = algo.copy();
        assertEquals(emptyGraph.getMC() , deepCopyG.getMC());
        assertEquals(emptyGraph, deepCopyG, "after copy should be equals");
        assertNotEquals(g , deepCopyG);
        algo.init(g);
        deepCopyG = algo.copy();
        assertEquals(g.getMC() , deepCopyG.getMC());
        assertEquals(g, deepCopyG, "after copy should be equals");
        assertNotEquals(emptyGraph , deepCopyG);
        algo.init(fullGraph5);
        deepCopyG = algo.copy();
        assertEquals(fullGraph5.getMC() , deepCopyG.getMC());
        assertEquals(fullGraph5, deepCopyG, "after copy should be equals");
        assertNotEquals(g , deepCopyG);
    }

    @Test
    void isConnected() {
        for (int i = 1; i <= 10 ; i++) {
            randomGraph = createGraphUnConnected(i*22 , 1) ;
            weighted_graph fullGraphI = setupFullGraph(i);
            algo.init(randomGraph);
            assertFalse(algo.isConnected());
            algo.init(fullGraphI);
            assertTrue(algo.isConnected());
        }
        algo.init(emptyGraph);
        assertTrue(algo.isConnected());
        emptyGraph.addNode(1);
        assertTrue(algo.isConnected());
        algo.init(fullGraph5);
        assertTrue(algo.isConnected());
        fullGraph5.addNode(6);
        assertFalse(algo.isConnected());
        algo.init(g);
        assertTrue(algo.isConnected());
        g.removeEdge(7,8);
        assertFalse(algo.isConnected());
        weighted_graph e = smallGraph() ;
        algo.init(e);
    }

    @Test
    void shortestPathDist() {
        weighted_graph e = smallGraph() ;
        algo.init(e);
        assertAll(
                () -> assertEquals(11 ,algo.shortestPathDist(0,14) , "the weight should be 11" ),
                () -> assertEquals(9 ,algo.shortestPathDist(0,11) ,"the weight should be 9" ),
                () -> assertEquals(14 ,algo.shortestPathDist(5,14) , "the weight should be 14" ),
                () -> assertEquals(12 ,algo.shortestPathDist(5,11) , "the weight should be 12" )
        );
        algo.init(g);
        assertAll(
                () -> assertEquals(10 ,algo.shortestPathDist(1,4) , "the weight should be 10" ),
                () -> assertEquals(11 ,algo.shortestPathDist(1,5) , "the weight should be 11" ),
                () -> assertEquals(7 ,algo.shortestPathDist(3,8) ,"the weight should be 7" )
        );
        g.connect(2,3,1);
        g.connect(5,9,1);
        g.connect(3,7,3);
        assertAll(
                () -> assertEquals(5 ,algo.shortestPathDist(1,4) , "the weight should be 5" ),
                () -> assertEquals(5 ,algo.shortestPathDist(1,5) , "the weight should be 6" ),
                () -> assertEquals(5 ,algo.shortestPathDist(3,8) ,"the weight should be 5" )
        );
        algo.init(emptyGraph);
        assertAll(
                () -> assertEquals(-1 ,algo.shortestPathDist(1,4) , "the weight should be 5" ),
                () -> assertEquals(-1 ,algo.shortestPathDist(1,5) , "the weight should be 6" ),
                () -> assertEquals(-1 ,algo.shortestPathDist(3,8) ,"the weight should be 5" )
        );

    }

    @Test
    void shortestPath() {
        int i = 0 ;
        weighted_graph e = smallGraph() ;
        algo.init(e);
        List <node_info> path = algo.shortestPath(0,14) ;
        ArrayList<Integer> keys = new ArrayList<Integer>() ;
        keys.add(0); keys.add(8); keys.add(1); keys.add(14);
        ArrayList<Double> weight = new ArrayList<Double>() ;
        weight.add(0.0); weight.add(4.0); weight.add(8.0); weight.add(11.0);
        for (node_info node : path){
            assertEquals(keys.get(i) , node.getKey());
            assertEquals(weight.get(i) , node.getTag());
            i++ ;
        }
        i = 0 ;
        path = algo.shortestPath(0,11) ;
        keys.clear();  keys.add(0); keys.add(2); keys.add(3); keys.add(11);
        weight.clear();  weight.add(0.0); weight.add(4.0); weight.add(5.0); weight.add(9.0);
        for (node_info node : path){
            assertEquals(keys.get(i) , node.getKey());
            assertEquals(weight.get(i) , node.getTag());
            i++ ;
        }
        i = 0 ;
        path = algo.shortestPath(5,11) ;
        keys.clear();  keys.add(5); keys.add(0); keys.add(2); keys.add(3); keys.add(11);
        weight.clear();  weight.add(0.0); weight.add(3.0); weight.add(7.0); weight.add(8.0); weight.add(12.0);
        for (node_info node : path){
            assertEquals(keys.get(i) , node.getKey());
            assertEquals(weight.get(i) , node.getTag());
            i++ ;
        }
        algo.init(g);
        g.connect(2,3,1);
        g.connect(5,9,1);
        g.connect(3,7,3);
        i = 0 ;
        path = algo.shortestPath(1,4) ;
        keys.clear();  keys.add(1); keys.add(3); keys.add(2); keys.add(4);
        weight.clear();  weight.add(0.0); weight.add(2.0); weight.add(3.0); weight.add(5.0);
        for (node_info node : path){
            assertEquals(keys.get(i) , node.getKey());
            assertEquals(weight.get(i) , node.getTag());
            i++ ;
        }
        i = 0 ;
        path = algo.shortestPath(1,5) ;
        keys.clear();  keys.add(1); keys.add(10); keys.add(9); keys.add(5);
        weight.clear();  weight.add(0.0); weight.add(1.0); weight.add(4.0); weight.add(5.0);
        for (node_info node : path){
            assertEquals(keys.get(i) , node.getKey());
            assertEquals(weight.get(i) , node.getTag());
            i++ ;
        }


    }

    @Test
    void save() {
        algo.init(g);
        assertTrue(algo.save("this is g graph"));
        algo.init(fullGraph5);
        assertTrue(algo.save("this is fullGraph5"));
        algo.init(emptyGraph);
        assertTrue(algo.save("this is emptyGraph"));
    }

    @Test
    void load() {
        assertTrue(algo.load("this is g graph"));
        assertTrue(algo.load("this is fullGraph5"));
        assertTrue(algo.load("this is emptyGraph"));
    }
    @Test
    void saveAndLoad() {
        algo.init(g);
        weighted_graph gCopy = algo.copy() ;
        weighted_graph_algorithms temp = new WGraph_Algo();
        algo.save("this is g graph");
        temp.load("this is g graph");
        assertEquals(gCopy,temp.getGraph());
        gCopy.removeNode(1);
        assertNotEquals(gCopy,temp.getGraph());

        algo.init(fullGraph5);
        algo.save("this is fullGraph5");
        gCopy = algo.copy() ;
        temp.load("this is fullGraph5");
        assertEquals(gCopy,temp.getGraph());
        gCopy.removeNode(1);
        assertNotEquals(gCopy,temp.getGraph());

        algo.init(emptyGraph);
        algo.save("this is emptyGraph");
        temp.load("this is emptyGraph");
        gCopy = algo.copy() ;
        assertEquals(gCopy,temp.getGraph());
        gCopy.removeNode(1);
        assertEquals(gCopy,temp.getGraph());
    }
}


////////////////////////////////////// NEW ////////////////////////////////////////

//package ex1;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class WGraph_AlgoTest {
//
//    @Test
//    void isConnected() {
//        weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(1,0,1);
//        ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(2,0,1);
//        ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertFalse(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(2,1,1);
//        ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(10,30,1);
//        ag0.init(g0);
//        boolean b = ag0.isConnected();
//        assertTrue(b);
//    }
//
//    @Test
//    void shortestPathDist() {
//        weighted_graph g0 = small_graph();
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//        double d = ag0.shortestPathDist(0,10);
//        assertEquals(d, 5.1);
//    }
//
//    @Test
//    void shortestPath() {
//        weighted_graph g0 = small_graph();
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        List<node_info> sp = ag0.shortestPath(0,10);
//        //double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
//        int[] checkKey = {0, 1, 5, 7, 10};
//        int i = 0;
//        for(node_info n: sp) {
//            //assertEquals(n.getTag(), checkTag[i]);
//            assertEquals(n.getKey(), checkKey[i]);
//            i++;
//        }
//    }
//
//    @Test
//    void save_load() {
//        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        String str = "g0.obj";
//        ag0.save(str);
//        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
//        ag0.load(str);
//        assertEquals(g0,g1);
//        g0.removeNode(0);
//        assertNotEquals(g0,g1);
//    }
//
//    private weighted_graph small_graph() {
//        weighted_graph g0 = WGraph_DSTest.graph_creator(11,0,1);
//        g0.connect(0,1,1);
//        g0.connect(0,2,2);
//        g0.connect(0,3,3);
//
//        g0.connect(1,4,17);
//        g0.connect(1,5,1);
//        g0.connect(2,4,1);
//        g0.connect(3, 5,10);
//        g0.connect(3,6,100);
//        g0.connect(5,7,1.1);
//        g0.connect(6,7,10);
//        g0.connect(7,10,2);
//        g0.connect(6,8,30);
//        g0.connect(8,10,10);
//        g0.connect(4,10,30);
//        g0.connect(3,9,10);
//        g0.connect(8,10,10);
//
//        return g0;
//    }
//}


////////////////////////////////////////////////// OLD /////////////////////
//package ex1;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class WGraph_AlgoTest {
//
//    @Test
//    void isConnected() {
//        weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(1,0,1);
//        ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(2,0,1);
//        ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertFalse(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(2,1,1);
//        ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//
//        g0 = WGraph_DSTest.graph_creator(10,30,1);
//        ag0.init(g0);
//        boolean b = ag0.isConnected();
//        assertTrue(b);
//    }
//
//    @Test
//    void shortestPathDist() {
//        weighted_graph g0 = small_graph();
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        assertTrue(ag0.isConnected());
//        double d = ag0.shortestPathDist(0,10);
//        assertEquals(d, 5.1);
//    }
//
//    @Test
//    void shortestPath() {
//        weighted_graph g0 = small_graph();
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        List<node_info> sp = ag0.shortestPath(0,10);
//        System.out.println(sp);
//        System.out.println(g0);
//        double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
//        int[] checkKey = {0, 1, 5, 7, 10};
//        int i = 0;
//        for(node_info n: sp) {
//            assertEquals(n.getTag(), checkTag[i]);
//            assertEquals(n.getKey(), checkKey[i]);
//            i++;
//        }
//    }
//
//    @Test
//    void save_load() {
//        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
//        weighted_graph_algorithms ag0 = new WGraph_Algo();
//        ag0.init(g0);
//        String str = "g0.obj";
//        ag0.save(str);
//        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
//        ag0.load(str);
//        assertEquals(g0,g1);
//        g0.removeNode(0);
//        assertNotEquals(g0,g1);
//    }
//
//    private weighted_graph small_graph() {
//        weighted_graph g0 = WGraph_DSTest.graph_creator(11,0,1);
//        g0.connect(0,1,1);
//        g0.connect(0,2,2);
//        g0.connect(0,3,3);
//
//        g0.connect(1,4,17);
//        g0.connect(1,5,1);
//        g0.connect(2,4,1);
//        g0.connect(3, 5,10);
//        g0.connect(3,6,100);
//        g0.connect(5,7,1.1);
//        g0.connect(6,7,10);
//        g0.connect(7,10,2);
//        g0.connect(6,8,30);
//        g0.connect(8,10,10);
//        g0.connect(4,10,30);
//        g0.connect(3,9,10);
//        g0.connect(8,10,10);
//
//        return g0;
//    }
//}