import java.util.*;


public class BFSMaze {

	public static int find_exit(String[][][] jail) {
		
		HashMap<String,Integer> nodeMap = new HashMap<>(); // record visited nodes
		LinkedList<int[]> nodeQ = new LinkedList<>();	// queue next node
		
		int exitL = 0;	// shortest length to exit
		int iS = -1;	//start locations of level, row and columns
		int jS = -1;
		int kS = -1;
		
		for(int i = 0; i < jail.length; i++) {	// find starting location
			for(int j = 0; j < jail[i].length; j++) {
				for (int k = 0; k < jail[i][j].length; k++){
					if (jail[i][j][k].contentEquals("S")){
						iS = i;
						jS = j;
						kS = k;
						break;
						
					}
				}
			}
		}
		
		
		int[] sLoc = {iS,jS,kS}; // this int array acts as a node
		nodeQ.add(sLoc);	//queue starting location

		//countB is a backward counter, it record the number of nodes that belong to the 
		// same level (they are the same distance from "S") when a node is being dequeued
		// countB decreases, and every nodes discovered near current node goes to countF
		// as it counts the number of nodes in the next level (current distance + 1), when
		// countB reaches 0, the next iteration countF will go to countB as we now gonna
		// process the next level of nodes, countF reset to 0, and exitL increases
		int countB = 0;	
		int countF = 1;
		
		while (! nodeQ.isEmpty()) { // when there are still nodes to process
			int[] tempNode = nodeQ.poll(); // get node
			if (countB == 0) { //check if we reached next level
				countB = countF;
				countF = 0;
				exitL++;				
			}
			
			//get i,j,k value from node class
			int iT = tempNode[0];
			int jT = tempNode[1];
			int kT = tempNode[2];
					
			// if tempijk +- 1 exist and not visited, countF increases and queue the node
			// there are six scenerios to check
			if(kT -1 >= 0 && !jail[iT][jT][kT-1].contentEquals("#")) {
				if(jail[iT][jT][kT-1].contentEquals("E")) {
					return exitL;
				}
				String tempString = convert(iT,jT,kT -1);
				if (!nodeMap.containsKey(tempString)){ // if hasnt visited
					nodeMap.put(tempString, 0); // record unvisited node
					int[] newNode = {iT,jT,kT - 1};
					nodeQ.add(newNode); // add the node to Queue
					countF++;	//total of new node + 1
				}			
			}
			
			if(kT + 1 < jail[iT][jT].length  && !jail[iT][jT][kT+1].contentEquals("#")) {
				if(jail[iT][jT][kT+1].contentEquals("E")) {
					return exitL;
				}	
				String tempString = convert(iT,jT,kT + 1);
				if (!nodeMap.containsKey(tempString)){ // if hasnt visited
					nodeMap.put(tempString, 0); // record unvisited node
					int[] newNode = {iT,jT,kT + 1};
					nodeQ.add(newNode); // add the node to Queue
					countF++;	//total of new node + 1
				}				
			}
			
			if(jT -1 >= 0 && !jail[iT][jT - 1][kT].contentEquals("#")) {
				if(jail[iT][jT - 1][kT].contentEquals("E")) {
					return exitL;
				}
				String tempString = convert(iT,jT - 1,kT);
				if (!nodeMap.containsKey(tempString)){ // if hasnt visited
					nodeMap.put(tempString, 0); // record unvisited node
					int[] newNode = {iT,jT - 1,kT};
					nodeQ.add(newNode); // add the node to Queue
					countF++;	//total of new node + 1
				}				
			}
			
			if(jT + 1 < jail[iT].length  && !jail[iT][jT + 1][kT].contentEquals("#")) {
				if(jail[iT][jT + 1][kT].contentEquals("E")) {
					return exitL;
				}
				String tempString = convert(iT,jT + 1,kT);
				if (!nodeMap.containsKey(tempString)){ // if hasnt visited
					nodeMap.put(tempString, 0); // record unvisited node
					int[] newNode = {iT,jT + 1,kT};
					nodeQ.add(newNode); // add the node to Queue
					countF++;	//total of new node + 1
				}				
			}
			
			if(iT -1 >= 0 && !jail[iT - 1][jT][kT].contentEquals("#")) {
				if(jail[iT - 1][jT][kT].contentEquals("E")) {
					return exitL;
				}
				String tempString = convert(iT - 1,jT,kT);
				if (!nodeMap.containsKey(tempString)){ // if hasnt visited
					nodeMap.put(tempString, 0); // record unvisited node
					int[] newNode = {iT - 1,jT,kT};
					nodeQ.add(newNode); // add the node to Queue
					countF++;	//total of new node + 1
				}				
			}
			
			if(iT + 1 < jail.length  && !jail[iT + 1][jT][kT].contentEquals("#")) {
				if(jail[iT + 1][jT][kT].contentEquals("E")) {
					return exitL;
				}
				String tempString = convert(iT + 1,jT,kT);
				if (!nodeMap.containsKey(tempString)){ // if hasnt visited
					nodeMap.put(tempString, 0); // record unvisited node
					int[] newNode = {iT + 1,jT,kT};
					nodeQ.add(newNode); // add the node to Queue
					countF++;	//total of new node + 1
				}				
			}
			
			countB--;			
			
			
			
		}
		
		// if loop ended and still no "E" found, it means no exit available :(
		// hence return -1
		
		return -1;
	}
	
	// This function basically combine i, j and k value to a single string and use it 
	// as the key name for the visted nodes hashmap
	private static String convert(int i, int j, int k) {
		
		String stringNode = i + ":" + j + ":" + k;
		return stringNode;
	}
	
	


	public static void main(String[] args) {
		
		String[][][] test1 = {{{"S", ".", "#", "#"}, {".", "#", "#","#"}},{{"#", "#",".","E"},{".", "#", "#","#"}}};
		
		System.out.println(find_exit(test1));
	}

}
