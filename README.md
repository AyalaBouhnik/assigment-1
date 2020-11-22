

This project (-'assignment 1' ) is  about structures and undirectional and weighted graphs.
This task improves task 0, by generalizing the data structure that I have developed so that it can be supported in weighted graphs, and not directed. After adjusting the data structure, a number of algorithms are implemented on the graph is weighted, including the ability to save and restore the graph from a file, calculating a short route (Minimum distances according to the weight of the ribs) , and finding the shortest path (a collection of vertices between the source and the destination).
The main functions that are in the project are: "isConnected()","shortedPathDist(int src, int dest)" and a function that returns a list of the path.
The basic idea of what I did in " isConnected"- 
1.Create a queue.
2. "Color" all the nodes in white (initially, all the nodes should be white), white means that the node is not processed yet.
3. We color the first node in yellow (when the node is yellow it means that the node is being processed. We "review" the specific node's neighbors and add them to the queue (and color them yellow too.)
4. When the node doesn't have children anymore, and all his children are in the queue, we color the node in blue.
5.We pull the first node that is in the queue, we delete him from the queue and we "review" his children, coloring them in yellow and putting them in the queue.
This process is repeated until we complete the entire graph.
If all the nodes are blue, it means that the graph is connected.
(Here is a video which I based my algorithm on - https://www.youtube.com/watch?v=YhU2EvAoKOI).
The basic idea of what I did in " shortedPathDist(int src, int dest)"- 
Let the node at which we are starting be called the initial node. Let the distance of node Y be the distance from the initial node to Y. Dijkstra's algorithm will assign some initial distance values and will try to improve them step by step.
1.	Mark all nodes unvisited. Create a set of all the unvisited nodes called the unvisited set.
2.	Assign to every node a tentative distance value: set it to zero for our initial node and to infinity for all other nodes. Set the initial node as current. 
3.	For the current node, consider all of its unvisited neighbors and calculate their tentative distances through the current node. Compare the newly calculated tentative distance to the current assigned value and assign the smaller one. For example, if the current node A is marked with a distance of 6, and the edge connecting it with a neighbor B has length 2, then the distance to B through A will be 6 + 2 = 8. If B was previously marked with a distance greater than 8 then change it to 8. Otherwise, the current value will be kept.
4.	When we are done considering all of the unvisited neighbors of the current node, mark the current node as visited and remove it from the unvisited set. A visited node will never be checked again.
5.	If the destination node has been marked visited (when planning a route between two specific nodes) or if the smallest tentative distance among the nodes in the unvisited set is infinity (when planning a complete traversal; occurs when there is no connection between the initial node and remaining unvisited nodes), then stop. The algorithm has finished.
6.	Otherwise, select the unvisited node that is marked with the smallest tentative distance, set it as the new "current node", and go back to step 3.
When planning a route, it is actually not necessary to wait until the destination node is "visited" as above: the algorithm can stop once the destination node has the smallest tentative distance among all "unvisited" nodes (and thus could be selected as the next "current").
(Here is a website which I based my algorithm on –
https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm ).
The rest of the functions ( "removeNode"-O(1), "addNode"-O(1), "getEdge"-O(1)...) are documented in the project




