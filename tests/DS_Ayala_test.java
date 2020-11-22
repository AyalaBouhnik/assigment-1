package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DS_Ayala_test {
    private static Random _rnd = null;

    @Test
    void nodeSize() {
        weighted_graph ayala = new WGraph_DS();
        ayala.addNode(0);
        ayala.addNode(2);
        ayala.addNode(3);
        ayala.addNode(4);
        ayala.addNode(1);
        ayala.addNode(1);
        ayala.addNode(5);
        ayala.removeNode(2);
        ayala.removeNode(1);
        ayala.removeNode(8);
        ayala.removeNode(1);
        int s = ayala.nodeSize();
        assertEquals(4,s);
        ayala.removeNode(0);
        s = ayala.nodeSize();
        assertEquals(3,s);
        ayala.addNode(1);
        ayala.addNode(1);
        ayala.addNode(12);
        s = ayala.nodeSize();
        assertEquals(5,s);

    }

    @Test
    void edgeSize() {
        weighted_graph ayala = new WGraph_DS();
        ayala.addNode(0);
        ayala.addNode(1);
        ayala.addNode(2);
        ayala.addNode(3);
        ayala.addNode(4);
        ayala.addNode(5);
        ayala.addNode(6);
        ayala.connect(0,1,6.5);
        ayala.connect(1,2,2.2);
        ayala.connect(0,3,3.8);
        ayala.connect(3,1,4);
        ayala.connect(1,3,4);
        ayala.connect(1,3,9.1);
        ayala.connect(2,1,4.0);
        ayala.connect(5,6,4.7);
        int e_size =  ayala.edgeSize();
        assertEquals(5, e_size);
        double w03 = ayala.getEdge(0,3);
        double w30 = ayala.getEdge(3,0);
        assertEquals(w03, w30);
        assertEquals(w03, 3.8);
    }

    @Test
    void getV() {
        weighted_graph ayala = new WGraph_DS();
        ayala.addNode(0);
        ayala.addNode(1);
        ayala.addNode(2);
        ayala.addNode(3);
        ayala.addNode(4);
        ayala.addNode(5);
        ayala.addNode(6);
        ayala.connect(0,1,6.5);
        ayala.connect(1,2,2.2);
        ayala.connect(0,3,3.8);
        ayala.connect(3,1,4);
        ayala.connect(1,3,4);
        ayala.connect(1,3,9.1);
        ayala.connect(2,1,4.0);
        ayala.connect(5,6,4.7);
        Collection<node_info> v = ayala.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }
    }

    @Test
    void hasEdge() {
        int s = 10, e = s*(s-1)/2;
        weighted_graph ayala = graph_creator(s,e,1);
        for(int i=0;i<s;i++) {
            for(int j=i+1;j<s;j++) {
                boolean b = ayala.hasEdge(i,j);
                assertTrue(b);
                assertTrue(ayala.hasEdge(j,i));
            }
        }
    }

    @Test
    void connect() {
        weighted_graph ayala = new WGraph_DS();
        ayala.addNode(0);
        ayala.addNode(1);
        ayala.addNode(2);
        ayala.addNode(3);
        ayala.addNode(4);
        ayala.addNode(5);
        ayala.addNode(6);
        ayala.connect(0,1,6.5);
        ayala.connect(1,2,2.2);
        ayala.connect(0,3,3.8);
        ayala.connect(3,1,4);
        ayala.connect(1,3,4);
        ayala.connect(1,3,9.1);
        ayala.connect(2,1,4.0);
        ayala.connect(5,6,4.7);
        ayala.removeEdge(0,1);
        assertFalse(ayala.hasEdge(1,0));
        ayala.removeEdge(2,1);
        ayala.connect(0,1,7.0);
        double w = ayala.getEdge(1,0);
        assertEquals(w,7);
    }


    @Test
    void removeNode() {
        weighted_graph ayala = new WGraph_DS();
        ayala.addNode(0);
        ayala.addNode(1);
        ayala.addNode(2);
        ayala.addNode(3);
        ayala.addNode(4);
        ayala.addNode(5);
        ayala.addNode(6);
        ayala.connect(0,1,6.5);
        ayala.connect(1,2,2.2);
        ayala.connect(0,3,3.8);
        ayala.connect(3,1,4);
        ayala.connect(1,3,4);
        ayala.connect(1,3,9.1);
        ayala.connect(2,1,4.0);
        ayala.connect(5,6,4.7);
        ayala.removeNode(4);
        ayala.removeNode(0);
        assertFalse(ayala.hasEdge(1,0));
        int e = ayala.edgeSize();
        assertEquals(3,e);
        assertEquals(5,ayala.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph ayala = new WGraph_DS();
        ayala.addNode(0);
        ayala.addNode(1);
        ayala.addNode(2);
        ayala.addNode(3);
        ayala.addNode(4);
        ayala.addNode(5);
        ayala.addNode(6);
        ayala.connect(0,1,6.5);
        ayala.connect(1,2,2.2);
        ayala.connect(0,3,3.8);
        ayala.connect(3,1,4);
        ayala.connect(1,3,4);
        ayala.connect(1,3,9.1);
        ayala.connect(2,1,4.0);
        ayala.connect(5,6,4.7);
        ayala.removeEdge(0,3);
        double w = ayala.getEdge(0,3);
        assertEquals(w,-1);
    }

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph ayala = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            ayala.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(ayala);
        while(ayala.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            ayala.connect(i,j, w);
        }
        return ayala;
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

    private static int[] nodes(weighted_graph c) {
        int size = c.nodeSize();
        Collection<node_info> V = c.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }

}