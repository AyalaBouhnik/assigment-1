package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private static int white = 0;
    private static int yellow = 1;
    private static int blue = 2;
    private weighted_graph GA;

    /**
     * weighted undirected graph implemention
     */
    public WGraph_Algo() {
        this.GA = new WGraph_DS();
    }
    @Override
    /**
     * Init the graph on which this set of algorithms operates on.
     */
    public void init(weighted_graph g) {
        this.GA = g;
    }

    /**
     * returns the graph
     * @return GA (graph).
     */
    @Override
    public weighted_graph getGraph() {
        return GA;
    }

    /**
     *  Compute a deep copy of this weighted graph.
     * @return return new graph.
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(GA);
    }

    /**
     *1.Create a queue.
     *2. "Color" all the nodes in white (initially, all the nodes should be white),
     *white means that the node is not processed yet.
     *3. We color the first node in yellow (when the node is yellow it means that
     *the node is being processed. We "review" the specific node's neighbors and
     *add them to the queue (and color them yellow too.)
     *4. When the node doesn't have children anymore, and all his children are in
     *the queue, we color the node in blue.
     *5.We pull the first node that is in the queue, we delete him from the queue
     *and we "review" his children, coloring them in yellow and putting them in the queue.
     *This process is repeated until we complete the entire graph.
     *If all the nodes are blue, it means that the graph is connected.
     *@return true if and only if (iff) there is a valid path from every node to each.
     *     */

    @Override
    public boolean isConnected() {
        if(GA.nodeSize()<2)    return true; //edge cases
        if(GA.getV()==null||GA.getV().size()==0||GA.getV().size()==1){
            return true;
        }

        Iterator<node_info> neighbor=GA.getV().iterator();
        for (node_info n : GA.getV()){
            n.setTag(white); //initially, all the nodes are white.
        }
        Queue<node_info> bfs = new LinkedList<node_info>();
        node_info start = GA.getV().iterator().next();
        start.setTag(yellow); // coloring the first node in yellow
        bfs.add(start);
        while (!bfs.isEmpty()) { //while the queue is not empty
            start = bfs.poll();
            if(!(GA.getV(start.getKey()).isEmpty())){
                //if((  GA.getV(start.getKey()) != null )){
                Iterator<node_info> x=GA.getV(start.getKey()).iterator();
                while(x.hasNext()){
                    node_info n=x.next();
                    if (n.getTag() == white) {
                        n.setTag(yellow);
                        bfs.add(n);
                    }
                }}
            start.setTag(blue);
        }

        for (node_info n : GA.getV()){ // scan the nodes
            if(n.getTag()!=blue) //if we have a non blue node, it means the ex0.graph is not connected
                return false;
        }
        return true;
    }

    /**
     *  the Pseudocode code of dijkstra algoritem which I have based on in the next functions:
     *     function Dijkstra(Graph, source):
     *
     *       create vertex set Q
     *
     *       for each vertex v in Graph:
     *           dist[v] ← INFINITY
     *           prev[v] ← UNDEFINED
     *           add v to Q
     *       dist[source] ← 0
     *
     *       while Q is not empty:
     *           u ← vertex in Q with min dist[u]
     *
     *           remove u from Q
     *
     *           for each neighbor v of u:           // only v that are still in Q
     *               alt ← dist[u] + length(u, v)
     *               if alt < dist[v]:
     *                   dist[v] ← alt
     *                   prev[v] ← u
     *
     *       return dist[], prev[]
     * @param src - start node
     * @param dest - end (target) node
     * @return return the length of the shortest path between src to dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        Map.Entry<Map<node_info, Double>, Map<node_info, node_info>> distanceAndPrevs = getAllShortestPaths(src);
        Map<node_info, Double> distances = distanceAndPrevs.getKey();
        if (!distances.containsKey(GA.getNode(dest))) { //if distances dose not contain dest- return -1.
            return -1;
        }
        return distances.get(GA.getNode(dest));
    }

    /**
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        Map.Entry<Map<node_info, Double>, Map<node_info, node_info>> distanceAndPrevs = getAllShortestPaths(src);
        Map<node_info, node_info> prevs = distanceAndPrevs.getValue();
        LinkedList<node_info> path = new LinkedList<>();
        if(!prevs.containsKey(GA.getNode(dest))) return null; //if prevs dose not contain dest- return null.
        node_info current = GA.getNode(dest);
        while (current != null) {
            path.addFirst(current);
            current = prevs.get(current);
        }
        return path;
    }

    /**
     * helper function-
     * @param src
     * @return
     */
    public Map.Entry<Map<node_info, Double>, Map<node_info, node_info>> getAllShortestPaths(int src) {
        Map<node_info, Double> distances=new HashMap<>();
        Map<node_info, node_info> prev=new HashMap<>();
        PriorityQueue<NodeAndDistance> queue=new PriorityQueue<>(this.GA.nodeSize(),
                (node1, node2) -> Double.compare(node1.getDistance(), node2.getDistance()));
        Map<node_info, NodeAndDistance> mapping=new HashMap<>();

        node_info nodeSrc = GA.getNode(src);
        distances.put(nodeSrc, 0.0);
        mapping.put(nodeSrc,new NodeAndDistance(nodeSrc, 0.0));
        queue.add(mapping.get(nodeSrc));
        prev.put(nodeSrc, null);

        while (!queue.isEmpty()) {
            NodeAndDistance nodeAndDistance=queue.poll();
            node_info currNode=nodeAndDistance.getNode();
            distances.put(currNode, nodeAndDistance.getDistance());
            for (node_info neighbor : GA.getV(currNode.getKey())) {
                double distance=nodeAndDistance.getDistance() + GA.getEdge(currNode.getKey(), neighbor.getKey());
                if (distances.get(neighbor)==null ||distance<distances.get(neighbor)) {
                    prev.put(neighbor, currNode);
                    if (mapping.get(neighbor)!=null) {
                        queue.remove(mapping.get(neighbor));
                    }
                    mapping.put(neighbor, new NodeAndDistance(neighbor,distance));
                    queue.add(mapping.get(neighbor));
                    distances.put(neighbor,distance);
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(distances,prev);
    }

    /**
     * Saves this weighted (undirected) graph to the given file name.
     * @param file - the file name (may include a relative path).
     * @return iff the file was successfully saved
     */
    @Override
    public boolean save(String file){
        try {
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(this.GA);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        // Deserialize HashMap of user-defined object values
        try {
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(new FileInputStream(file));
            this.GA = (WGraph_DS) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private static class NodeAndDistance {
        private double distance;
        private node_info node;

        /**
         * constructor
         * @param node
         * @param d
         */
        public NodeAndDistance(node_info node, double d) {
            this.distance = d;
            this.node = node;
        }

        /**
         * @return the distance.
         */
        public double getDistance() {
            return this.distance;
        }

        /**
         * @return the node.
         */
        public node_info getNode() {
            return this.node;
        }

        /**
         *
         * @param o
         * @return true iff the two Objects are the same.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeAndDistance that = (NodeAndDistance) o;
            return Double.compare(that.distance, distance) == 0 &&
                    Objects.equals(node, that.node);
        }

        /**
         * @return Objects.hash(distance, node)
         */
        @Override
        public int hashCode() {
            return Objects.hash(distance, node);
        }
    }
}
