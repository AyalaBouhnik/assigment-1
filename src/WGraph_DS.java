package ex1.src;

import java.util.*;
import java.io.Serializable;

public class WGraph_DS implements weighted_graph, Serializable {
    private int edges;
    private int changes;
    HashMap<Integer, node_info> graph;

    /**
     * constructor
     */
    public WGraph_DS() {
        this.graph = new HashMap<Integer, node_info>();
        this.edges = 0;
        this.changes = 0;
    }

    /**
     * for the deep copy function in WGraph_Algo.
     * @param g
     */
    public WGraph_DS(weighted_graph g) {
        this.graph = new HashMap<Integer, node_info>();
        for (node_info node : g.getV()) {
            addNode(node.getKey());
        }
        for (node_info node : g.getV()) {
            for (node_info neighbor : g.getV(node.getKey())) {
                this.connect(node.getKey(), neighbor.getKey(), g.getEdge(node.getKey(), neighbor.getKey()));
            }
        }
        this.edges = ((WGraph_DS) g).edges;
        this.changes = ((WGraph_DS) g).changes;
    }

    /**
     * return the node_data by the node_id.
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return graph.get(key);
    }

    /**
     * check if there is edge between two nodes
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        Node first = (Node) graph.get(node1);
        Node second = (Node) graph.get(node2);
        return first != null && second != null && first.hasNi(node2) && second.hasNi(node1);
    }

    /**
     * return the weight between two nodes.In case here is no such edge - should return -1
     * complexity-O(1).
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) {
            return -1;
        }
        Node first = (Node) graph.get(node1);
        return first.getNiWeight(node2);
    }

    /**
     * add a new node to the graph with the given key.
     * compxity: O(1).
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (graph.containsKey(key)) {
            return;
        }
        Node node = new Node(key);
        graph.put(key, node); // Associates the specified value with the specified key in this map.
        changes++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * complexity: O(1).
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        // if at least one of them not in the graph
        if (!graph.containsKey(node1) || !graph.containsKey(node2) || node1==node2) {
            return;
        }
        if (w < 0) {
            return;
        }

        if(hasEdge(node1,node2)) {
            if(getEdge(node1,node2)!=w){
            ((Node) graph.get(node1)).weights.put(node2, w);
            ((Node) graph.get(node2)).weights.put(node1, w);
            changes++;}
        }else{
        Node first=(Node) graph.get(node1);
        Node second=(Node) graph.get(node2);
        if (first.addNi(second, w) && second.addNi(first, w)) {
            edges++;
            changes++;

        }
    }
    }

    /**
     * this method return a pointer (shallow copy) for a Collection representing all the nodes in the graph.
     * complexity: O(1).
     * @return Collection<node_data>.
     */
    @Override
    public Collection<node_info> getV() {
        return graph.values(); // The method is used to return a collection view containing all the values of
        // the map. }
    }

    /**
     * This method returns a Collection containing all the nodes connected to node_id
     * complexity: O(k) time, k - being the degree of node_id.
     * @param node_id
     * @return Collection<node_data>.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        return ((Node) graph.get(node_id)).getNi();
    }

    /**
     *  Delete the node (with the given ID) from the graph -and removes all edges which starts or ends at this node.
      * complexity: O(n), |V|=n, as all the edges should be removed.
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!graph.containsKey(key)) {// if the key is not in the ex0.graph- return null.
            return null;
        }
        node_info ans = graph.get(key);
        if (!((Node) graph.get(key)).getNi().isEmpty()) { // if there are neighbors
            LinkedList<node_info> newl = new LinkedList<>(((Node) graph.get(key)).getNi());
            while (!newl.isEmpty())
                removeEdge(key, newl.pollFirst().getKey());
        }
        graph.remove(key);
        changes++;
        return ans;
    }

    /**
     * Delete the edge from the graph.
     * complexity: O(1) time.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (!graph.containsKey(node1) || !graph.containsKey(node2)) {
            return;
        }
        if (!hasEdge(node1, node2) || !hasEdge(node2, node1)) {
            return;
        }
        Node first = (Node) graph.get(node1);
        Node second = (Node) graph.get(node2);
        first.removeNode(second);
        first.removeNode(first);
        edges--;
        changes++;
    }

    /**
     * @return he number of vertices (nodes) in the graph.
     * complexity: O(1).
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * @return the number of edges (undirectional graph).
     * complexity: O(1).
     */
    @Override
    public int edgeSize() {
        return edges;
    }

    /**
     * Any change in the inner state of the graph  cause an increment in the ModeCounter.
     * @return the Mode Count - for testing changes in the graph.
     */
    @Override
    public int getMC() {
        return changes;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edges == wGraph_ds.edges && Objects.equals(graph, wGraph_ds.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edges, changes, graph);
    }

    private static class Node implements node_info, Serializable {

        int key;
        private static int taz = 0;
        private String info;
        private double tag;
        HashMap<Integer, node_info> struct;
        HashMap<Integer, Double> weights;

        /**
         * constructors
         * @param key
         */
        public Node(int key) {
            this.key = key;
            this.info = "";
            this.tag = 0;
            this.struct = new HashMap<>();
            this.weights = new HashMap<>();
        }

        public Node() {
            this(taz++);
        }

        /**
         * @return the key (id) associated with this node.
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * @return the remark (meta data) associated with this node.
         */
        @Override
        public String getInfo() {
            return info;
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
         * Temporal data (aka distance, color, or state) which can be used be algorithms.
         *
         * @return
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        /**
         * @param key
         * @return
         */
        public node_info getNi(int key) {
            // Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
            return struct.get(key);
        }

        /**
         *This method returns a collection with all the Neighbor nodes.
         * @return
         */
        public Collection<node_info> getNi() {
            return this.struct.values();
        }

        /**
         * Removes the edge this-key.
         * @param node
         */
        public void removeNode(node_info node) {
            struct.remove(node.getKey(), node); // Removes the entry for the specified key only if it is currently
            // mapped to the specified value.
        }

        /**
         * return true iff this<==>key are adjacent, as an edge between them.
         * complexity: O(1).
         * @param key
         * @return
         */
        public boolean hasNi(int key) {
            return struct.containsKey(key); // Returns true if this map contains a mapping for the specified key.
        }

        /**
         *this function is for adding neighbors.
         * @param t
         * @param weight
         * @return
         */
        public boolean addNi(node_info t, double weight) {
            if (t == null || t.getKey() == this.key) {
                return false;
            }
            // insert a mapping into a map
            node_info neighbor = struct.put(t.getKey(), t);
            // insert a weight into a map
            this.weights.put(t.getKey(), weight);
            return neighbor == null;
        }

        /**
         * this unction is for getting the weight.
         * @param dest
         * @return
         */
        public double getNiWeight(int dest) {
            return this.weights.get(dest);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key == node.key &&
                    Double.compare(node.tag, tag) == 0 &&
                    Objects.equals(info, node.info) &&
                    Objects.equals(weights, node.weights);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", struct=" + struct.keySet() +
                    '}';
        }
    }
}

