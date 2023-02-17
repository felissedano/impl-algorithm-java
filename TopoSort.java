import java.util.*;

public class TopoSort {

    public static int rings(Hashtable<Integer, ArrayList<Integer>> graph, int[] location) {
       
    	int total = location.length; // get total nunber of nodes
    	
    	
    	int[] sorted = top_sort(graph, total); // topological-sort the nodes
    	
    	Boolean[] fixed = new Boolean[total]; // record if a node is fixed 	
    	for(int i = 0; i < total; i++) {
    		fixed[i] = false;
    	}
    	int planet;
    	int transferCount = 0;
    	
    	
    	for(int i = 0; i < sorted.length; i++) { // for each nodes
    		int curNode = sorted[i];
    		if(fixed[curNode] == true) {	// if the node is already fixed previously
    			continue;
    		}
    		
    		fixed[curNode] = true;	// fix the current node
    		planet = location[curNode];	// record we planet we are in
    		if(i != 0) {
    			transferCount++;	//since in new planet, transfer count increase
    		}
    		
    		//this loop check for the subsequent nodes to check if they are in the same
    		// planet, if so, see if we can fix it using nested loop
    		for(int j = i + 1; j < sorted.length; j++) {
    			Boolean canFix = true;
    			int nextNode = sorted[j];
    			if(location[nextNode] == planet) { //if a node is in the same curPlanet
    				for(int k = i + 1; k < j; k++) { //for all in-between nodes
    					//if node not been fixed, check if its also a dependent node
    					// if yes, then we cant fix node sorted[j]
    					if(fixed[sorted[k]] == false) {
    						ArrayList<Integer> tempArray = graph.get(sorted[k]);
    						for(int e : tempArray) {
    							if( e == nextNode) { //nextNode depends on e
    								canFix = false;						
    							}
    						}
    					}
    					if(canFix == false) {
    						break;
    					}
    				}
    				if(canFix == true) {
    					fixed[nextNode] = true; // mark node as fixed
    				}
    			}
    		}
    		
    		
    	}
    	
    	return transferCount;
    }
    
    
    // function to sort array topologically
    private static int[] top_sort(Hashtable<Integer, ArrayList<Integer>> graph, int t) {
    	
    	Boolean[] visited = new Boolean[t]; // store visited nodes, index the the node
    	
    	for(int j = 0; j < t; j++) { // initialize the boolean array
    		visited[j] = false;
    	}
    	
    	int[] ordered = new int[t]; // store sorted nodes
    	
    	// this backward counter is the position to insert a node, since we use a stack
    	// to sort nodes, the first node to insert in ordered[] is the last element
    	// visited
    	int countB = t - 1; 
    	
    	// iterate through each nodes, and if they haven't been visited by the previous
    	// iteration, then depth-first-search the node
    	for(int i = 0; i < t; i++) {
    		if(visited[i] == false) {
    			countB=dfs_search(graph,i, visited,ordered,countB);
    			
    		}
    		
    	}
    	
    	// return sorted array
    	return ordered;
    }
    
    // DFS search on a node
    private static int dfs_search(Hashtable<Integer, ArrayList<Integer>> graph, int i,
    		Boolean[] visited, int[] ordered, int countB) {
    	
    	ArrayList<Integer> tempList = graph.get(i); //get the dependency array
    	
    	if(tempList.size() == 0) { //if no dependency, then it means it's a leaf
    		ordered[countB] = i;	// insert node to last empty slot in sorted array
    		visited[i] = true;		// mark node as visited
    		countB--;		// go to the next empty slot of sorted array
    		return countB;
    	}
    	
    	for(int k = 0; k < tempList.size(); k++) { // if not a leaf, try visit each nodes
    		int tempNode = tempList.get(k);
    		if (visited[tempNode] == false) { // if not visited, the visit the node
    			countB = dfs_search(graph,tempNode,visited,ordered,countB);
    		}
    	}
    	
    	//after no more nodes to visit, insert current node and return
    	visited[i] = true; 
    	ordered[countB] = i;
    	countB--;
    	
    	return countB;
    }
    
    
    

    public static void main(String[] args) {
    	Hashtable<Integer, ArrayList<Integer>> graph = new Hashtable<>();
    	
    	ArrayList<Integer> test0 = new ArrayList<>();
    	test0.add(3); test0.add(1);
    	
    	ArrayList<Integer> test1 = new ArrayList<>();
    	test1.add(3);
    	
    	ArrayList<Integer> test2 = new ArrayList<>();
    	test2.add(1); test2.add(4);test2.add(0);test2.add(3);
    	
    	ArrayList<Integer> test3 = new ArrayList<>();
    	
    	ArrayList<Integer> test4 = new ArrayList<>();
    	test4.add(3); test4.add(1);test4.add(0);
    	
    	int[] location = {2,1,2,2,1};
    	
    	graph.put(0, test0);
    	graph.put(1, test1);
    	graph.put(2, test2);
    	graph.put(3, test3);
    	graph.put(4, test4);
    	
    	rings(graph, location);
    	
    	

    }

}
