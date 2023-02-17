import java.math.BigInteger;
import java.util.Scanner;
import java.util.*;

public class DivideAndConquer {

	public static String mod_fibonacci(int N, BigInteger K) {
		
		if( N == 0) {
			return "Y";
		}
		
				
		String temp;
		
		HashMap<Integer, BigInteger> resultMap = new HashMap<>();
		
		fillmap(N,resultMap);
		
		temp = findfibo(N,K, resultMap);
		
//		System.out.println(resultMap.toString());
		
		
		return temp ;
	}
	
	
	
	private static String findfibo(int N, BigInteger k, HashMap<Integer, BigInteger> map) {
		
	//	System.out.println("value of N is " + N);
		
		if(N == 1) {
			return "X";
		}
		
		if(N == 2) {
			return "Y";
		}
	//	System.out.println("value of K is " + k.toString());
		if(k.compareTo(map.get(N-2)) == 1) {
	//		System.out.println("case1");
			
			k = k.subtract(map.get(N-2));
			return findfibo(N-1,k,map);
		}else {
			return findfibo(N-2,k,map);
		}
		
		
	}
	
	private static BigInteger fillmap(int N, HashMap<Integer, BigInteger> map) {
		
		BigInteger temp = BigInteger.valueOf(0);
		
	//	System.out.println("value of n is " + N);
		if(N == 1 || N == 2){
			temp = BigInteger.valueOf(1);
			map.put(N, temp);
			return temp;
		}
		
		if(map.containsKey(N)) {
			return map.get(N);
		}
		
		if(!map.containsKey(N)) {
			
			BigInteger temp2 = fillmap(N-2,map);
			BigInteger temp3 = fillmap(N-1,map);
	//		System.out.println("value of temp2 is " + temp2.toString());
	//		System.out.println("value of temp3 is " + temp3.toString());
			temp = temp2.add(temp3);
			
	//		System.out.println("value of temp is " + temp.toString());
			map.put(N, temp);
		}
		
		return temp;
	}
	
	
	public static void main(String[] args) {
		
		BigInteger t = new BigInteger("1");
		System.out.println(mod_fibonacci(7783, t));

	}

}
