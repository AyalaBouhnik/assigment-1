package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class TestJunit {
    private static Random _rnd = null;
    /**
     * Checking the copy of the graph.
     */
    @Test
    void copy() {
        weighted_graph g= myGraph();
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(g);
        weighted_graph cop=ga.copy();
        assertEquals(cop,g);
    }
    /**
     * Checks the amount of vertices in the graph.
     */
    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(0);
        g.removeNode(0);
        g.removeNode(2);
        g.removeNode(4);
        int s = g.nodeSize();
        assertEquals(2,s);
    }
    /**
     * Checks the amount of arcs in the graph.
     */
    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.connect(0,1,2.0);
        g.connect(0,2,2.0);
        g.connect(1,5,1.0);
        g.connect(1,1,10.0);
        g.connect(3,2,8.0);
        g.connect(4,6,4.0);
        g.connect(2,3,1.0);
        g.connect(5,1,1.0);
        int sizeEdge =  g.edgeSize();
        assertEquals(5, sizeEdge);
        double x1 = g.getEdge(0,2);
        double x2 = g.getEdge(2,0);
        assertEquals(x1, x2);
        assertEquals(x2, 2.0);
    }
    /**
     * Checks if there is no vertex that does not exist in the collection.
     */
    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        Collection<node_info> x = g.getV();
        Iterator<node_info> i = x.iterator();
        while (i.hasNext()) {
            node_info n = i.next();
            assertNotNull(n);
        }
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1.0);
        g.connect(1,0,1.0);
        g.connect(1,2,4.0);
        g.connect(2,3,1.4);
        g.connect(3,3,1.4);
        g.connect(3,0,5.8);
        g.connect(4,5,6.0);
        g.connect(7,1,2.0);
        Collection<node_info> c = g.getV();
        Iterator<node_info> path = c.iterator();
        while (path.hasNext()) {
            node_info node = path.next();
            assertNotNull(node);
        }
    }
    /**
     *Checks whether the arc connection worked according to the following rules:
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        System.out.println(g.getMC());
        g.connect(0,1,1.0);
        g.connect(1,0,1.0);
        System.out.println(g.getMC());
        g.connect(1,2,4.0);
        g.connect(2,3,1.4);
        g.connect(3,3,1.4);
        g.connect(3,0,5.8);
        g.connect(4,5,6.0);
        g.connect(4,5,2.4);
        g.connect(1,3,-1);
        System.out.println(g.getV());
        assertEquals(2.4,g.getEdge(5,4));
        int x1= g.getMC();
        System.out.println(g.getMC());
        assertEquals(13,g.getMC());
        assertEquals(-1,g.getEdge(1,3));
        assertFalse(g.hasEdge(8,3));
        g.removeEdge(0,3);
        g.removeEdge(3,0);
        g.removeEdge(0,1);
        g.connect(0,1,1);
        double x = g.getEdge(8,2);
        assertEquals(x,-1);
        double x2 = g.getEdge(2,3);
        assertEquals(x2,1.4);
    }
    /**
     *Checks whether the vertex has been deleted from
     *the graph and so have all the arcs that were attached to it.
     */
    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1.0);
        g.connect(1,0,1.0);
        g.connect(1,2,4.0);
        g.connect(2,3,1.4);
        g.connect(3,3,1.4);
        g.connect(3,0,5.8);
        g.connect(4,5,6.0);
        g.removeNode(2);
        g.removeNode(2);
        g.removeNode(0);
        g.removeNode(10);
        assertFalse(g.hasEdge(1,0));
        int s = g.edgeSize();
        assertEquals(1,s);
        assertEquals(4,g.nodeSize());
    }
    /**
     * Checks that the arc has been deleted from the graph.
     */
    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1.0);
        g.connect(1,0,1.0);
        g.connect(1,2,4.0);
        g.connect(2,3,1.4);
        g.connect(3,3,1.4);
        g.connect(3,0,5.8);
        g.connect(4,5,6.0);
        g.removeEdge(0,1);
        g.removeEdge(5,4);
        g.removeEdge(3,3);
        g.removeEdge(0,3);
        g.removeEdge(4,5);
        double x = g.getEdge(0,1);
        assertEquals(x,-1);
        double x1 = g.getEdge(4,5);
        assertEquals(x1,-1);
        double x2 = g.getEdge(1,2);
        assertEquals(x2,4.0);
    }
    /**
     * Checks that there is an arc between two specific vertices in the graph.
     */
    @Test
    void hasEdge() {
        int n = 20;
        int e = n*(n-1)/2;
        weighted_graph g = graph_creator(n,e,10);
        for(int i=0;i<n;i++) {
            for(int j=i+1;j<n;j++) {
                boolean b = g.hasEdge(i,j);
                assertTrue(b);
                assertTrue(g.hasEdge(j,i));
            }
        }
    }
    /**
     * Checks that the graph is a link graph - that is, if there is an arc between any two vertices in the graph.
     */
    @Test
    void isConnected() {
        weighted_graph g0=graph_creator(0,0,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0=graph_creator(0,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0=graph_creator(1,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0=graph_creator(4,3,1);
        ag0=new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0=graph_creator(2,1,1);
        ag0=new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0=graph_creator(15,15,1);
        ag0=new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0=graph_creator(15,23,1);
        ag0=new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0=graph_creator(8,16,1);
        ag0.init(g0);
        boolean b=ag0.isConnected();
        assertTrue(b);
    }
    /**
     * Examines the shortest path in a weighted graph from one vertex to another.
     */
    @Test
    void shortestPathDist() {
        weighted_graph g0 = myGraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double ans = ag0.shortestPathDist(0,10);
        assertEquals(ans, 3.4);
    }
    /**
     * Checking the vertices through them is the shortest way to get from one vertex to another.
     */
    @Test
    void shortestPath() {
        weighted_graph g0 = myGraph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> list = ag0.shortestPath(0,10);
        int[] checkKey = {0, 1, 6, 10};
        int i = 0;
        for(node_info n: list) {
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
    }

    @Test
    void save_load() {
        weighted_graph g0 = graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 =graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }

    private weighted_graph myGraph() {
        weighted_graph g0 =graph_creator(11,0,1);
        g0.connect(0,1,1.4);
        g0.connect(1,3,4.0);
        g0.connect(1,2,0.1);
        g0.connect(2,10,100.0);
        g0.connect(1,6,1.0);
        g0.connect(2,3,10.0);
        g0.connect(2,8,14.0);
        g0.connect(4,7,31.0);
        g0.connect(4,3,0.1);
        g0.connect(5,10,4.0);
        g0.connect(6,5,2.0);
        g0.connect(6,7,0.5);
        g0.connect(8,9,1.0);
        g0.connect(9,10,1.5);
        g0.connect(9,7,4.0);
        g0.connect(6,10,1.0);
        g0.connect(10,2,100.0);
        g0.connect(9,1,12.0);
        return g0;
    }


    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes);
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}