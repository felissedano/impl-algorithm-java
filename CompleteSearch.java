import java.util.*;

public class CompleteSearch {
	
	public static int[] game(String[][] board){
		
		int[] result = {-1,-1};
		int ball = 0;
		int move = 0; // number of moves performed

		// count the number of balls
		for(int i = 0; i < board.length; i++) {
			for(int k = 0; k < board[i].length; k++) {
				if (board[i][k].equals("o")){
					ball++;
				}
			}
		}

		
		
		String[][] boardT = board;
		
		//do a recursive call of the function to try find the largest performable moves
		move = action(boardT);
		
		//since each moves indicate a ball being removed, the number of balls
		//is basically original num substracted by the number of moves performed
		result[0] = ball - move;
		result[1] = move;
		
		System.out.println("restult ball is " + result[0]);
		System.out.println("restult move is " + result[1]);
		
		return result;	
		                
	}
	
	
	
	private static int action(String[][] boardT) {
		
		boolean legal = false;
		
		int moves = 0; //to count the deepest recursion performed
		
		//for each element in the 2d array
		for(int i = 0; i < boardT.length; i++) {
			for(int k = 0; k < boardT[0].length; k++) {
				if (boardT[i][k].equals("o")){
					
					
					//check all four angles of the given ball, for each angle that has
					//a legal move, do a subroutine for the modified board, and restore
					//the board when the recursion ends, count the number of total moves
					//performed, and return the one with the largest number
					if( (i-1) >=0 && boardT[i-1][k].equals("o")) {
						if((i-2)>=0 && boardT[i-2][k].equals(".")) {
							legal = true;
							String[][] boardTemp = boardT;
							boardTemp[i][k]="."; //do the move and proceed to the stage
							boardTemp[i-1][k]=".";
							boardTemp[i-2][k] = "o";
							int movetemp = 1 + action(boardTemp); 
							if(movetemp > moves) {
								moves = movetemp;
							}
							boardTemp[i][k]="o"; //undo the moves finding all possible 
							boardTemp[i-1][k]="o"; //moves
							boardTemp[i-2][k] = ".";
						}
					}
					
					
					if( (i+1) <=4 && boardT[i+1][k].equals("o")) {
						if((i+2)<=4 && boardT[i+2][k].equals(".")) {
							legal = true;
							String[][] boardTemp = boardT;
							boardTemp[i][k]=".";
							boardTemp[i+1][k]=".";
							boardTemp[i+2][k] = "o";
							int movetemp = 1 + action(boardTemp);
							if(movetemp > moves) {
								moves = movetemp;
							}
							boardTemp[i][k]="o";
							boardTemp[i+1][k]="o";
							boardTemp[i+2][k] = ".";
						}
					}
					
					
					if( (k-1) >=0 && boardT[i][k-1].equals("o")) {
						if((k-2)>=0 && boardT[i][k-2].equals(".")) {
							legal = true;
							String[][] boardTemp = boardT;
							boardTemp[i][k]=".";
							boardTemp[i][k-1]=".";
							boardTemp[i][k-2] = "o";
							int movetemp = 1 + action(boardTemp);
							if(movetemp > moves) {
								moves = movetemp;
							}
							boardTemp[i][k]="o";
							boardTemp[i][k-1]="o";
							boardTemp[i][k-2] = ".";
						}
					}
					
					
					if( (k+1) <=8 && boardT[i][k+1].equals("o")) {
						if( (k+2)<=8 && boardT[i][k+2].equals(".")) {
							legal = true;
							String[][] boardTemp = boardT;
							boardTemp[i][k]=".";
							boardTemp[i][k+1]=".";
							boardTemp[i][k+2] = "o";
							int movetemp = 1 + action(boardTemp);
							if(movetemp > moves) {
								moves = movetemp;
							}			
							boardTemp[i][k]="o";
							boardTemp[i][k+1]="o";
							boardTemp[i][k+2] = ".";
						}
					}														
					
					
					
				}
			}
		}
		
		return moves; //would return 0 if no legal move, and return the largest number
		//of moves from the four angles otherwise
		
		
	}
	
	
	public static void main (String[] args){
		
		String[][] test = 
			   {{"#","#","#",".","o",".","#","#","#"},
				{".",".",".",".","o",".","o",".","."},
				{".","o","o",".","o",".","o",".","."},
				{".","o",".",".",".",".",".",".","."},
				{"#","#","#",".",".",".","#","#","#"}};
		
		String[][] test2 = 
			   {{"#","#","#",".","o",".","#","#","#"},
				{".",".",".","o","o",".","o",".","."},
				{".",".","o",".",".",".","o",".","."},
				{"o",".",".","o",".",".",".",".","."},
				{"#","#","#",".",".",".","#","#","#"}};
		
		String[][] test3 = 
			   {{"#","#","#","o","o","o","#","#","#"},
				{".",".",".","o",".",".",".",".","."},
				{".",".",".",".",".",".",".",".","."},
				{".",".",".",".",".",".",".",".","."},
				{"#","#","#",".",".",".","#","#","#"}};
		
		
		System.out.println(game(test3)[0] );
		
		
		String[][] t1= {{"hi","hello"},{"wow","waaaa"}};
		String[][] t2 = t1.clone();
		t2[0][0] = "test1";
		System.out.println(t1[0][0]);
		
	}
	
	
	
	

}

