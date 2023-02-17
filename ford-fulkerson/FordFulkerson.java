import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE*/
		
		WGraph resGc = graph; // copy residual graph
		ArrayList<Edge> edges = resGc.getEdges(); // get all edges
		Boolean[] visited = new Boolean[resGc.getNbNodes()]; //to check if an edge is visited
		Arrays.fill(visited, Boolean.FALSE);
		
		dfs_search(resGc.getSource(),edges,visited,path,resGc);
		return path;
	}



	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;
		
		/* YOUR CODE GOES HERE		*/
		
		// copy the entire graph to a new residual graph
		WGraph resG = new WGraph(graph);
		ArrayList<Edge> tempEdges = graph.getEdges();
		for(Edge e: tempEdges) {
			// add backward and forward edge
			Edge tempbEdge = new Edge(e.nodes[1],e.nodes[0],0); // new backward edge
			resG.addEdge(tempbEdge);
		}
		
		
		
		boolean hasAug = true;
		
		while(hasAug) {
			ArrayList<Integer> augPath = pathDFS(resG.getSource(),resG.getDestination(),resG);
			if(!augPath.contains(resG.getDestination())) {
				break;				
			}
			
			int neck = -1;
			// iterate through arraylist and find bottleneck
			for(int i = 0; i < augPath.size() - 1; i++) {
				if(neck == -1) {
					neck = resG.getEdge(augPath.get(i), augPath.get(i+1)).weight;
				}else {
					neck = Math.min(neck, resG.getEdge(augPath.get(i), augPath.get(i+1)).weight);
				}
			}
			
			// update residual graph with the bottleneck value
			Boolean updated = false;
			for(int i = 0; i < augPath.size() - 1 ; i++) {
				resG.setEdge(augPath.get(i), augPath.get(i+1), 
						resG.getEdge(augPath.get(i), augPath.get(i+1)).weight-neck);
				resG.setEdge(augPath.get(i+1), augPath.get(i), 
						resG.getEdge(augPath.get(i+1), augPath.get(i)).weight+neck);
				// if at maximum capacity and is forward edge, update bottlenck
				Edge oriEdge = graph.getEdge(augPath.get(i), augPath.get(i+1));
				Edge oriEdge2 = graph.getEdge(augPath.get(i+1), augPath.get(i+1));
				Edge tempBackEdge = resG.getEdge(augPath.get(i+1), augPath.get(i));
				Edge tempFEdge = resG.getEdge(augPath.get(i), augPath.get(i+1));
				if(oriEdge != null && oriEdge.weight == tempBackEdge.weight&& !updated ){
					maxFlow+= neck;
					updated = true;
				}else if(oriEdge2 != null && oriEdge2.weight == tempFEdge.weight && !updated){
					maxFlow -= neck;
					updated = true;
				}
			}
			
		}
		
		for(Edge e: graph.getEdges()) {
			Edge resEdge = resG.getEdge(e.nodes[1], e.nodes[0]);
			graph.setEdge(e.nodes[0], e.nodes[1], resEdge.weight);
			
		}
		
		

		answer += maxFlow + "\n" + graph.toString();	
		return answer;
	}
	
	private static Boolean dfs_search(int s, ArrayList<Edge> edges, 
			Boolean[] v, ArrayList<Integer> p, WGraph g) {
		
		v[s] = true;
		
		if(s == g.getDestination()) {
			p.add(s);			
			return true;
		}
		
		p.add(s); // insert current node to potential path arraylist
		
		// for each edge, check in the starting node is current node and unvisited
		for(int i = 0; i < edges.size(); i++) {
			Edge curEdge = edges.get(i);
			if(curEdge.nodes[0] == s && curEdge.weight > 0 && v[curEdge.nodes[1]] == false) {
				v[curEdge.nodes[1]] = true; // mark as visited 
				int nextNode = curEdge.nodes[1];
				
				Boolean pathFound = dfs_search(nextNode,edges,v,p,g);
				
				if(! pathFound) { // if no path to destination available 
					v[nextNode] = false; //undo the step and continue the loop
					continue;	//find next available edge				
				}
				// if we found destination in one of the recursion, return true
				return true;							
			}
		}
		// undo the steps if no destination found
		p.remove(p.size()-1);
		return false;
		
	}
	

	 public static void main(String[] args){
		 
		String file = "C:\\Users\\felis\\eclipse-workspace\\CS251\\src\\cs251a3\\ff0.txt";//args[0];
		File f = new File(file);
		WGraph g1 = new WGraph(file);
	    System.out.println(fordfulkerson(g1));
	     
	 }
}


