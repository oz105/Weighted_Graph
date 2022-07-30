
# Weighted_Graph
### EX1 OOP in java
Task 1	

### Weighted graph = a graph whose edges have weights

##
![alt text](http://www.mathcs.emory.edu/~cheung/Courses/171/Syllabus/11-Graph/FIGS/Dijkstra/weight01.gif)

In this Project there is 3 Classes : WGraph_DS , WGraph_Algo ,node_infoCompereByTag and there is 3 interfaces : node_info , weighted_graph, weighted_graph_algorithms and we have directory of test , one for the WGraph_DS and one for WGraph_Algo .
Together they implement a weighted graph which functions such as add new node to the graph , set an edge between 2 nodes and give the edge weight checking if it connected or what is the path between 2 nodes and more.

The first Class WGraph_DS implements the interfaces weighted_graph and represents the weighted graph It contains Hashmap inside Hashmap in name edgesOfGraph 
edgeOfGraph - this represents all the edge of the graph the key is Intger the key of the node 
and than we get the other Hashmap keys - nodeInfo and value Double - it is means that there is all the nodes this Intger key have edge with them 
and the double represents the weight of this edge . that Hashmap contains all the edges of the graph.
In addition we have another Hashmap of the graph in this Hashmap the keys is Intger and the value is the node of this key.
this Hashmap contains all the nodes of the graph.
Moreover we have 3 int variables verticesSize, edgeSize, modeCount.
verticesSize - this count the number of the vertices in the graph . 
edgeSize - this count the number of the edges in the graph .
modeCount - his count the number of the changes in the graph .

### the methods we have in this Class are :
1. getNode - we need to give this method a key of the node we want and this will return the node or null if there is not node with such key .
2. hasEdge - we need to give 2 keys of nodes and if there is an edge between them it will return true , and false otherwise. 
3. getEdge - we need to give 2 keys of nodes and it will return the weight of the edge , if there is not such edge it will return -1 . 
4. addNode - we need to give this method a key we want to add to the graph if there is such key will do nothing , add it otherwise.
5. connect - we need to give 2 keys and weight and than it will make an edge between this keys and the weight of this edge will be the weight we put in.
6. getV -  we dont need to give this method nothing it will return a Collection of nodeInfo - meanning all the vertices of the graph.
7. getV - same name but diffrent - for this method we need to give a key and it will return a Collection of the nodes he has edges with ,in other words his neighbors.
8. removeNode - we need to give this method a key and it will delete the node from the graph including all his edges . 
9. removeEdge - we need to give this method 2 keys and it will remove the specific edge between them if there is no edge like this it will do noting.
10. nodeSize - this method will return the number of the vertices in the graph .
11. edgeSize - this method will return the number of the edges in the graph .
12. getMC - this method will return the number of the changes in the graph .
13. equals - we need to give this method a weighted_graph and it will return true if this graph and the graph we gave is the same . 




the secound Class WGraph_Algo implements the interfaces weighted_graph_algorithms . 
this class deals in more complex functions on the graph itself with the implement of BFS and Dijkstra algorithm .



### the methods we have in this Class are :
1. init - this method initialize the graph on which this set of algorithms operates on .
2. getGraph - this method will return the graph of which this class works on . 
3. copy - this method will return a weighted_graph with will be deep copy of the graph we init .
4. isConnected - this method will check if the graph is Connectivity or not by use BFS algorithm
I Used here in BFS algorithm instead of Dijkstra algorithm becasue BFS algorithm more efficient, and for Connectivity testing it is enough.





#### In the pictures you can see here the first one is Connectivity and the other one is not :




![alt_text](https://www.tutorialspoint.com/graph_theory/images/cut_set_of_a_graph.jpg)






![alt_text](https://www.tutorialspoint.com/graph_theory/images/removing_cut_set.jpg)








5. shortestPathDist - we need to give 2 keys of nodes and this method will returns the length of the shortest path between them 
if no such path it will returns -1. Use in Dijkstra algorithm
6. shortestPath - we need to give 2 keys of nodes and this method will reurns a List of nodes of the shortest path between them
if no such path it will returns null. Use in Dijkstra algorithm.
7. save - saves this weighted graph to the given file name. use Serializable.
8. load - This method load a graph to this graph algorithm . if the file was successfully loaded will return true otherwise return false . use Serializable.


## Dijkstra algorithm:






![alt_text](https://upload.wikimedia.org/wikipedia/commons/5/57/Dijkstra_Animation.gif)










9. Dijkstra - this method implements the Dijkstra algorithm .
in this method we will mark all the nodes as unvisited (empty info = unvisited , info = "")
and we will mark the weight of every node as infinity (Tag = Integer.MAX_VALUE)
we will create a PriorityQueue that will be give Priority base on the smallest weight
During the algorithm for every node we will saved 2 things
his weight form the src node - this will be store in the Tag
and from who he gets that weight - this will be store in the info (the key of the node)
and when we first visit in some node (means his info is empty)
we add him to the PriorityQueue .
the algorithm ends when the PriorityQueue is empty .
In the end of the algorithm each node will hold 2 things
1.the smallest weight from src node - will be store in the Tag.
2.from who he gets this weight - will be store in the Info.
if the Info contains a number (the key from who he gets his weight its means he already have been visited).
10. BFS - this method implements the BFS algorithm . 
This method should be run over all the vertices if the graph is connected.
and when it get to node it changes the tag of the node to 1 .
this method will be used in the function is connected .
if all the tags changes to 1 we will return true.
and if even 1 tag not equal to 1 we will return false.
11.NumberOrNot - this method create to help in the Dijkstra function.
This method get String and return true iff(if and only if) there is only number in the String return false otherwise.



The last Class is node_infoCompereByTag that implements the Comparator<node_info> 
We need this Class because we want to compare our vertices by their weight. And we hold their weight in a varilable called tag
So we compare them by the size of their tag.



In this task I found that the data structures Hashmap will fit because every vertex must be different and thanks to the Hashmap 
I can put any Vertex in his place by the key he gets in addition the access to a particular Vertex in the Hashmap 
and to add new node to the hash map and even search for weight between 2 vertices its really simple and fast with high efficiency.  
Also in Hashmap there is alot action we can do in O(1) like add a vertex to the graph , get all the vertices of the graph in O(1) , get all the neighbors of node in O(1) so this data structures was fit to this project . 

