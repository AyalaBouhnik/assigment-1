package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Algo_Ayala_test {

    @Test
    void isConnected() {
        weighted_graph g0 = DS_Ayala_test.graph_creator(0, 0, 1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = DS_Ayala_test.graph_creator(4, 0, 1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0 = DS_Ayala_test.graph_creator(2, 1, 1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = DS_Ayala_test.graph_creator(8, 8, 1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = DS_Ayala_test.graph_creator(10, 30, 1);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        assertTrue(b);
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());
        double d = ag0.shortestPathDist(0, 2);
        assertEquals(d, 10.5);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0, 2);
        int[] checkKey = {0,1,2};
        int i = 0;
        for (node_info n : sp) {
            //assertEquals(n.getTag(), checkTag[i]);
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
    }

    @Test
    void save_load() {
        weighted_graph ayala = DS_Ayala_test.graph_creator(12, 22, 1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(ayala);
        String str = "done!!";
        ag0.save(str);
        weighted_graph g = DS_Ayala_test.graph_creator(12, 22, 1);
        ag0.load(str);
        assertEquals(ayala, g);
        ayala.removeNode(0);
        assertNotEquals(ayala, g);
    }

    private weighted_graph small_graph() {
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

        return ayala;
    }
}