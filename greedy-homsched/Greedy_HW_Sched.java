import java.util.*;

public class Greedy_HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}	
		
		// To iterate throgh all assignments in the sorted array
		int listPointer = 0;
		
		// for each slot, find the most fitting assignment
		for(int i = 0; i < homeworkPlan.length; i++) {
			
			// if more slots than total number of assignment then cant fill in more
			if( i >= Assignments.size()) {
				break;
			}
			
			//if no more assignment to be fill in
			if(listPointer >= Assignments.size()){
				break;
			}
			
			// if assignment already past due date, find the next not yet due assignment
			if( (i + 1) > Assignments.get(listPointer).deadline) {
					
				while(Assignments.get(listPointer).deadline < (i+1)){
					listPointer++;
					if(listPointer >= Assignments.size()) {
						return homeworkPlan;
					}
					
				}
			}
			
			// the remaining slots of homework plan, used to judge if we should give
			// up current assignment for another higher weighted assignment with longer
			// due date
			int remainSlot = homeworkPlan.length - i;			
			
			// find the nearest fitting assignment with the relatively higher weight.
			// if more assignments with later due date have higher weight than current
			// one, might as well sacrifice current one to fit more higher value
			// assignments
			listPointer= locate(Assignments, remainSlot, listPointer);
			
			homeworkPlan[i] = Assignments.get(listPointer).number;
			listPointer++;
		}
	
		
		return homeworkPlan;
	}
	
	private static int locate(ArrayList<Assignment> Assignments, int remain, int p) {
		
		System.out.println("remain is " + remain + " p is " + p);
		
		Assignment temp = Assignments.get(p);		
		int weightCount = 0;
		int startPos = p + 1;
		
		for(int k = startPos; k < Assignments.size(); k++) {
			System.out.println("k is " + k);
			System.out.println("assign size is " + (Assignments.size()-p));
			if(Assignments.get(k).weight > temp.weight) {
				weightCount++;
				System.out.println("p is " + p + " weight count is " + weightCount);
			}
			
			if(weightCount >= remain) {
				System.out.println("we are here");
				while(Assignments.get(p).deadline == temp.deadline) {
					p++;
				}
				return locate(Assignments, remain, p);
		   }
		
		
		}
		
		return p;
		
	}
	
		public static void main(String[] args) {
		
		int[] weights = new int[] {23, 60, 14, 25, 7}; 
		int[] deadlines = new int[] {3, 1, 2, 1, 3};
		int m = weights.length;
		
		//This is the declaration of a schedule of the appropriate size
		HW_Sched schedule =  new HW_Sched(weights, deadlines, m);
		
		//This call organizes the assignments and outputs homeworkPlan
		int[] res = schedule.SelectAssignments();
		System.out.println(Arrays.toString(res));
	}
}
	



