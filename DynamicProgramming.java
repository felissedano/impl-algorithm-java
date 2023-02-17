import java.util.Scanner;
import java.util.*;

public class DynamicProgramming {

	public static int weight(int[] plates) {
		
		
		//use a hashmap to store all possible weight combinations. The key is the distance
		// from 1000, while the value is an array of size 2, index 0 is the value smaller
		HashMap<Integer,int[]> combo = new HashMap<>();
		int tempVal; // store the value of index i in the list
		int tempNewV; //use to store the combo value
		
		for (int i = 0; i < plates.length; i++) {
			
			tempVal = plates[i];
			
			//to temporally store the new combos during the dynamic calculation part
			ArrayList<Integer> tempList = new ArrayList<>();
			
			// if the number is 1000 then return itself as it is already the answer
			if(tempVal == 1000) {
				return 1000;
			}
			
			int res = mapCheck(combo,tempVal); //store the value of i in Hashmap
			
			if(res == 3 ) {	//if the same value exist with current i, do i + itself			

				for(Integer k : combo.keySet()) {
					//calculate all new combos and store them in tempList
					if(combo.get(k)[0]!= -1) {
						tempNewV = tempVal + combo.get(k)[0];
						if(tempNewV == 1000) {
							return 1000;
						}
						tempList.add(tempNewV);
					}		
					
				}
				//if combo value is new then store them into hashmap, ignore them otehrwise
				for(int k = 0; k<tempList.size(); k++) {
					mapCheck(combo,tempList.get(k));
				}							
			}
			
			
			if(res == 1) { //first time i appears, no need to include i + itself
				//same as above but skip the key where value == tempVal
				for(Integer k : combo.keySet()) {
					if(combo.get(k)[0] != tempVal && combo.get(k)[0] != -1) {
						tempNewV = tempVal + combo.get(k)[0];
						if(tempNewV == 1000) {
							return 1000;
						}
						tempList.add(tempNewV);
												
					}
				}
				for(int k = 0; k<tempList.size(); k++) {
					mapCheck(combo,tempList.get(k));
				}	
				
			}
						
		}
		
		
		//find the number nearest to 1000 and return the "over 1000 one" if possible		
		for (int i = 1; i <= 1000 ; i++) {
			if(combo.containsKey(i)) {
				if(combo.get(i)[1] != -1) {
					return combo.get(i)[1];
				}else {
					return combo.get(i)[0];
				}
			}
		}
		
		return 0;
	}
	
	
	
	private static int mapCheck(HashMap<Integer,int[]> theMap, int val) {
		
		//calculate the distance of given value val to 1000, store the distance as key
		//and store the actual value val to the corresponding index of int array
		//return a certain number depends on the situation for later if statements
		
		int temp;
		int dif = Math.abs(1000 - val);
		
		if( ! theMap.containsKey(dif) ) {
			int[] tempArr = {-1,-1};
			if(val < 1000) {
				tempArr[0] = val;
				temp = 1;
			}else {
				tempArr[1] = val;
				temp = 2;
			}				
			theMap.put(dif, tempArr);
			return temp;
		}else {
			
			if(val < 1000 && theMap.get(dif)[0] == -1) {
				theMap.get(dif)[0] = val;
				return 1;
			}
			
			if(val < 100 & theMap.get(dif)[0] != -1) {
				return 3;
			}
			
			if(val > 1000 && theMap.get(dif)[1] == -1) {
				theMap.get(dif)[1] = val;
				return 2;
			}			
		}		
		return 0;
			
		
	}
	
	public static void main(String[] args) {
		System.out.println("test test");
		
		int[] test1 = {4,900,500,498,4};
		
		System.out.println(weight(test1));
	}

}

