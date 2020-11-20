package ex1.tests;


import ex1.src.*;

import org.junit.jupiter.api.*;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private static long start;
    private Random rand;
    private weighted_graph g;
    private weighted_graph emptyGraph;
    private weighted_graph fullGraph5;

    /**
     * this Test check the method getNode
     * this try on empty graph and on graph that i knows how it is look like
     * and what it contains .
     */
    @org.junit.jupiter.api.Test
    void getNode() {
        assertAll(" ",
                () -> assertEquals(null, emptyGraph.getNode(0), "should be null"),
                () -> assertEquals(null, emptyGraph.getNode(1), "should be null"),
                () -> assertEquals(null, emptyGraph.getNode(2), "should be null"),
                () -> assertEquals(null, emptyGraph.getNode(3), "should be null"),
                () -> assertEquals(1, g.getNode(1).getKey(), "should be 1"),
                () -> assertEquals(2, g.getNode(2).getKey(), "should be 2"),
                () -> assertEquals(3, g.getNode(3).getKey(), "should be 3"),
                () -> assertEquals(4, g.getNode(4).getKey(), "should be 4"),
                () -> assertEquals(5, g.getNode(5).getKey(), "should be 5"),
                () -> assertNotEquals(9, g.getNode(8).getKey(), "should not be 9"),
                () -> assertNotEquals(0, g.getNode(1).getKey(), "should not be 0"),
                () -> assertNotEquals(1, g.getNode(2).getKey(), "should not be 1"),
                () -> assertNotEquals(6, g.getNode(7).getKey(), "should not be 6")
        );
    }

    /**
     * this Test check the method hasEdge.
     * this try on 3 graphs empty graph , full graph with random nodes , and g graph that i know it .
     */

    @Test
    void hasEdge() {
        assertAll(" ",
                () -> assertTrue(g.hasEdge(1, 3), "should be edge between node 1 to 3 "),
                () -> assertTrue(g.hasEdge(1, 8), "should be edge between node 1 to 8 "),
                () -> assertTrue(g.hasEdge(1, 10), "should be edge between node 1 to 10 "),
                () -> assertTrue(g.hasEdge(9, 10), "should be edge between node 3 to 10 "),
                () -> assertTrue(g.hasEdge(3, 6), "should be edge between node 3 to 6 "),
                () -> assertTrue(g.hasEdge(2, 6), "should be edge between node 2 to 6 "),
                () -> assertTrue(g.hasEdge(2, 4), "should be edge between node 2 to 4 "),
                () -> assertTrue(g.hasEdge(4, 5), "should be edge between node 4 to 5 "),
                () -> assertTrue(g.hasEdge(7, 8), "should be edge between node 7 to 8 "),
                () -> assertFalse(g.hasEdge(1, 5), "should not be edge between node 1 to 5 "),
                () -> assertFalse(g.hasEdge(1, 4), "should not be edge between node 1 to 4 "),
                () -> assertFalse(g.hasEdge(1, 6), "should not be edge between node 1 to 6 "),
                () -> assertFalse(g.hasEdge(1, 7), "should not be edge between node 1 to 7 "),
                () -> assertFalse(g.hasEdge(2, 3), "should not be edge between node 2 to 3 "),
                () -> assertFalse(g.hasEdge(2, 7), "should not be edge between node 2 to 7 "),
                () -> assertFalse(g.hasEdge(7, 9), "should not be edge between node 7 to 9 "),
                () -> assertFalse(g.hasEdge(1, 1), "should not be edge between node 1 to 1 "),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertTrue(checkEdgeInFullGraph(fullGraph5, fullGraph5.nodeSize()), "should be edge its full graph"),
                () -> assertFalse(emptyGraph.hasEdge(0, 1), "should not be edge its empty graph"),
                () -> assertFalse(emptyGraph.hasEdge(0, 2), "should not be edge its empty graph"),
                () -> assertFalse(emptyGraph.hasEdge(1, 1), "should not be edge its empty graph"),
                () -> assertFalse(emptyGraph.hasEdge(2, 2), "should not be edge its empty graph"),
                () -> assertFalse(emptyGraph.hasEdge(1, 3), "should not be edge its empty graph")
        );
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
        weighted_graph fullGraph6;
        fullGraph6 = setupFullGraph(6);
        assertAll(
                () -> assertEquals(-1, emptyGraph.getEdge(0, 1), "should not be edge its empty graph should return -1"),
                () -> assertEquals(-1, emptyGraph.getEdge(1, 2), "should not be edge its empty graph should return -1"),
                () -> assertEquals(-1, emptyGraph.getEdge(2, 3), "should not be edge its empty graph should return -1"),
                () -> assertEquals(1, edgeWeightInFullGraph(fullGraph6, 6)),
                () -> assertEquals(1, edgeWeightInFullGraph(fullGraph6, 6)),
                () -> assertEquals(1, edgeWeightInFullGraph(fullGraph6, 6)),
                () -> assertEquals(1, edgeWeightInFullGraph(fullGraph6, 6)),
                () -> assertEquals(5, g.getEdge(1, 8)),
                () -> assertEquals(2, g.getEdge(2, 4)),
                () -> assertEquals(3, g.getEdge(2, 6)),
                () -> assertEquals(3, g.getEdge(3, 6)),
                () -> assertEquals(4, g.getEdge(3, 9)),
                () -> assertEquals(3, g.getEdge(9, 10))
        );
    }

    @org.junit.jupiter.api.Test
    void addNode() {
        weighted_graph wg = createRandomGraph(10, 10, 1);
        wg.getNode(0).setTag(8);
        wg.addNode(0);
        assertEquals(8, wg.getNode(0).getTag());
        assertEquals(null, wg.getNode(10));
        wg.addNode(10);
        wg.getNode(10).setTag(6);
        assertEquals(6, wg.getNode(10).getTag());
        int size = wg.nodeSize();
        wg.addNode(11);
        assertEquals(size + 1, wg.nodeSize());
        size = wg.nodeSize();
        wg.addNode(8);
        assertEquals(size, wg.nodeSize());
        wg.addNode(8);
        assertEquals(size, wg.nodeSize());
    }

    @org.junit.jupiter.api.Test
    void connect() {
        fullGraph5.connect(1, 1, 1);
        assertFalse(fullGraph5.hasEdge(1, 1));
        fullGraph5.connect(1, 2, 2);
        assertEquals(2, fullGraph5.getEdge(1, 2));
        assertNotEquals(1, fullGraph5.getEdge(1, 2));
        fullGraph5.connect(3, 4, 0);
        assertEquals(0, fullGraph5.getEdge(3, 4));
        assertNotEquals(1, fullGraph5.getEdge(3, 4));
        try {
            emptyGraph.connect(1, 2, 1);
            fullGraph5.connect(3, 5, 0);
        } catch (NullPointerException e) {
            System.out.println("this is empty graph no need to enter the function");
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void getV() {
        weighted_graph rand = new WGraph_DS();
        int i = 3;
        while (i < 12) {
            rand = createRandomGraph(i * 2, i * 2 + 3, 1);
            weighted_graph finalRand = rand;
            int size = i * 2;
            int notSize = (i * 2) - 1;
            assertAll(
                    () -> assertEquals(0, emptyGraph.getV().size()),
                    () -> assertEquals(size, finalRand.getV().size()),
                    () -> assertNotEquals(notSize, finalRand.getV().size())
            );
            finalRand.addNode(12 * 8);
            assertEquals(size + 1, finalRand.getV().size(), "after add nodes should be " + size + "1");
            i++;
        }
        emptyGraph.addNode(0);
        emptyGraph.addNode(1);
        assertEquals(2, emptyGraph.getV().size(), "after add 2 nodes should be 2");
    }

    @Test
    void testGetV() {
        assertAll(
                () -> assertEquals(4, fullGraph5.getV(1).size()),
                () -> assertEquals(4, fullGraph5.getV(2).size()),
                () -> assertEquals(4, fullGraph5.getV(3).size()),
                () -> assertNotEquals(3, fullGraph5.getV(1).size()),
                () -> assertNotEquals(3, fullGraph5.getV(2).size()),
                () -> assertNotEquals(3, fullGraph5.getV(3).size())
        );
        fullGraph5.removeEdge(0, 1);
        fullGraph5.removeEdge(2, 3);
        fullGraph5.removeEdge(3, 4);
        assertAll(
                () -> assertEquals(3, fullGraph5.getV(0).size()),
                () -> assertEquals(3, fullGraph5.getV(2).size()),
                () -> assertEquals(2, fullGraph5.getV(3).size())
        );
    }

    @Test
    void removeNode() {
        int i = 0;
        assertAll(
                () -> assertEquals(null, emptyGraph.removeNode(0)),
                () -> assertEquals(null, emptyGraph.removeNode(1)),
                () -> assertEquals(null, emptyGraph.removeNode(2))
        );
        while (i < 5) {
            node_info temp = fullGraph5.getNode(0);
            node_info removed = fullGraph5.removeNode(0);
            assertAll(
                    () -> assertEquals(4, fullGraph5.nodeSize(), "after remove the should have 4 nodes"),
                    () -> assertEquals(temp, removed),
                    () -> assertEquals(6, fullGraph5.edgeSize(), "after remove it K4 so its need to contains 6 edges"),
                    () -> assertFalse(fullGraph5.hasEdge(0, 1), "the node 0 removed should not be edge"),
                    () -> assertFalse(fullGraph5.hasEdge(0, 2), "the node 0 removed should not be edge"),
                    () -> assertFalse(fullGraph5.hasEdge(0, 3), "the node 0 removed should not be edge"),
                    () -> assertFalse(fullGraph5.hasEdge(0, 4), "the node 0 removed should not be edge")
            );
            i++;
        }
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
        int i = 0;
        int edgeSize = 10;
        try {
            emptyGraph.removeEdge(1, 2);
            emptyGraph.removeEdge(0, 2);
        } catch (Exception e) {
            System.out.println("try to remove edge from empty graph");
            e.printStackTrace();
        }
        while (i < 4) {
            fullGraph5.removeEdge(i, i + 1);
            try {
                fullGraph5.removeEdge(i, i + 1);
            } catch (Exception e) {
                System.out.println("try to delete the same edge twice ");
                e.printStackTrace();
            }
            edgeSize--;
            assertEquals(edgeSize, fullGraph5.edgeSize());
            i++;
        }
    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
        assertAll(
                () -> assertEquals(10, g.nodeSize(), "should be 10 nodes "),
                () -> assertEquals(5, fullGraph5.nodeSize(), "should be 5 nodes "),
                () -> assertEquals(0, emptyGraph.nodeSize(), "should be 0 nodes ")
        );
        g.removeNode(1);
        g.removeNode(2);
        g.removeNode(3);
        g.removeNode(11);
        fullGraph5.removeNode(0);
        fullGraph5.removeNode(4);
        emptyGraph.removeNode(1);
        assertAll(
                () -> assertEquals(7, g.nodeSize(), "should be 7 nodes after delete "),
                () -> assertEquals(3, fullGraph5.nodeSize(), "should be 3 nodes after delete "),
                () -> assertEquals(0, emptyGraph.nodeSize(), "should be 0 nodes ")
        );
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
        assertAll(
                () -> assertEquals(10, g.edgeSize(), "should be 10 edges "),
                () -> assertEquals(10, fullGraph5.edgeSize(), "should be 10 edges "),
                () -> assertEquals(0, emptyGraph.edgeSize(), "should be 0 edges ")
        );
        g.removeEdge(1, 10);
        g.removeEdge(3, 6);
        g.removeEdge(3, 6);
        g.removeEdge(3, 6);
        g.removeEdge(3, 8);
        fullGraph5.removeEdge(0, 2);
        fullGraph5.removeEdge(1, 4);
        fullGraph5.removeEdge(8, 9);
        emptyGraph.removeEdge(2, 6);
        emptyGraph.removeEdge(1, 3);
        assertAll(
                () -> assertEquals(8, g.edgeSize(), "should be 10 edges after delete 2 "),
                () -> assertEquals(8, fullGraph5.edgeSize(), "should be 10 edges after delete 2 "),
                () -> assertEquals(0, emptyGraph.edgeSize(), "should be 0 edges ")
        );
    }

    @org.junit.jupiter.api.Test
    void getMC() {
        int mcOfg = g.getMC();
        int mcOfFullGraph = fullGraph5.getMC();
        int mcOfEmptyGraph = emptyGraph.getMC();
        g.removeNode(1); // has 3 edges so mc +3 and delete 1 node so mc +1 = mc + 4
        assertEquals(mcOfg + 4, g.getMC(), "delete 3 edges and 1 node total 4 changes");
        fullGraph5.removeNode(0);
        fullGraph5.removeNode(1); // total 1+4+1+3 = 9
        assertEquals(mcOfFullGraph + 9, fullGraph5.getMC(), "total 9 changes");
        emptyGraph.removeNode(0);
        emptyGraph.removeEdge(1, 2);
        assertEquals(mcOfEmptyGraph + 0, emptyGraph.getMC(), "didnt change nothing");
        emptyGraph.addNode(0);
        assertEquals(mcOfEmptyGraph + 1, emptyGraph.getMC(), "didnt change nothing");
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        weighted_graph copyG = new WGraph_DS();
        for (int i = 1; i < 11; i++) {
            copyG.addNode(i);
        }
        copyG.connect(1, 8, 5);
        copyG.connect(1, 3, 2);
        copyG.connect(1, 10, 1);
        copyG.connect(3, 9, 4);
        copyG.connect(9, 10, 3);
        copyG.connect(3, 6, 3);
        copyG.connect(7, 8, 2);
        copyG.connect(2, 6, 3);
        copyG.connect(2, 4, 2);
        copyG.connect(4, 5, 1);
        assertEquals(g, copyG, "the graph should be the same");
        assertNotEquals(g, emptyGraph, "the g Graph not equal to the emptyone");
        weighted_graph notGood_copyG = new WGraph_DS();
        for (int i = 1; i < 11; i++) {
            copyG.addNode(i);
        }
        notGood_copyG.connect(1, 8, 5);
        notGood_copyG.connect(1, 3, 2);
        notGood_copyG.connect(1, 10, 1);
        notGood_copyG.connect(3, 9, 4);
        notGood_copyG.connect(9, 10, 3);
        notGood_copyG.connect(3, 6, 3);
        notGood_copyG.connect(7, 8, 2);
        notGood_copyG.connect(2, 6, 3);
        notGood_copyG.connect(2, 4, 2);
        notGood_copyG.connect(4, 8, 1);
        assertNotEquals(g, notGood_copyG, "little change in the graph");


        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(g);
        weighted_graph deepCopyG = algo.copy();
        assertEquals(g, deepCopyG, "after copy should be equals");
    }

    @Test
    void millionNodes() {
        weighted_graph million = new WGraph_DS();
        int counterOfEdge = 0;
        int v = 1;
        for (int i = 0; i < 10000000; i++) {
            million.addNode(i);
        }
        for (int i = 0; i < 1000000;i++ ) {
            for (int j = v; j < 10+v; j++) {
                million.connect(i, j, 1);
                counterOfEdge++;
            }
            v++;
        }
    }


//Functions

    /**
     * This method run before all the tests
     * its simple method that check the run time of the tests .
     */

    @BeforeAll
    static void startRunTime(){
        start=new Date().getTime();
    }

    /**
     * This method run after all the test done
     * its it print the time it took in seconds .
     */
    @AfterAll
    static void endRunTime(){
        long end=new Date().getTime();
        double dt=(end-start)/1000.0;
        System.out.println("run in : "+dt+" seconds");
    }

    /**
     * This method create a random grpah with
     * vSize nodes
     * eSize edges
     *
     * @param vSize
     * @param eSize
     * @param seed
     * @return
     */
    weighted_graph createRandomGraph(int vSize,int eSize,int seed){
        if(eSize>(vSize*(vSize-1))/2)return null;
        weighted_graph wg=new WGraph_DS();
        rand=new Random(seed);
        for(int i=0;i<vSize; i++){
            wg.addNode(i);
        }
        while(wg.edgeSize()<eSize){
            wg.connect((int)(Math.random()*vSize),(int)(Math.random()*vSize),1);
        }
        return wg;
    }


    /**
     * build a specific graph that I know all about.
     * To perform a short-distance weight test and a short-distance test list.
     */
    @BeforeEach
    void setupGraphs(){
        g=new WGraph_DS();
        for(int i=1;i< 11;i++){
            g.addNode(i);
        }
        g.connect(1,8,5);
        g.connect(1,3,2);
        g.connect(1,10,1);
        g.connect(3,9,4);
        g.connect(9,10,3);
        g.connect(3,6,3);
        g.connect(7,8,2);
        g.connect(2,6,3);
        g.connect(2,4,2);
        g.connect(4,5,1);
        emptyGraph=new WGraph_DS();
        fullGraph5=setupFullGraph(5);
    }

    /**
     * This method init a full graph with the size of nodes we give .
     *
     * @param fullSize
     */
    weighted_graph setupFullGraph(int fullSize){
        weighted_graph fullGraph=new WGraph_DS();
        for(int i=0;i<fullSize; i++){
            fullGraph.addNode(i);
        }
        for(int i=0;i<fullSize; i++){
            for(int j=i+1;j<fullSize; j++){
                fullGraph.connect(i,j,1);
            }
        }
        return fullGraph;
    }

    /**
     * Simple method that get only full graph and his size.
     * pick randoms nodes and check if there is an edge between them
     * this method should always return true because its full graph .
     */
    boolean checkEdgeInFullGraph(weighted_graph g,int maxNumOfNodes){
        int a=(int)(Math.random()*maxNumOfNodes);
        int b=a;
        while(b==a){
            b=(int)(Math.random()*maxNumOfNodes);
        }
        return g.hasEdge(a,b);
    }

    /**
     * check weight between 2 randoms nodes in FullGraph
     *
     * @param g
     * @param maxNumOfNodes
     * @return the weight of the edge
     */
    double edgeWeightInFullGraph(weighted_graph g,int maxNumOfNodes){
        int a=(int)(Math.random()*maxNumOfNodes);
        int b=a;
        while(b==a){
            b=(int)(Math.random()*maxNumOfNodes);
        }
        return g.getEdge(a,b);
    }
}
