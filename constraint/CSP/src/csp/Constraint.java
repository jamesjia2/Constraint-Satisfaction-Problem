//Author: James Jia

package csp;

import java.util.ArrayList;
import java.util.HashMap;


//WRAPS CONSTRAINT MAP
public class Constraint {

	//CONSTRAINT MAP
	public static HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> constraints = new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();
	
	//TEST IF BINARY CONSTRAINTS ARE SATISFIED
	public boolean isSatisfied(int xi, int xj, int iv, int jv){

		
		ArrayList<Integer> varPair = new ArrayList<Integer>();
		varPair.add(xi);
		varPair.add(xj);
		

		ArrayList<Integer> valPair = new ArrayList<Integer>();
		valPair.add(iv);
		valPair.add(jv);
		
		
		ArrayList<Integer> flipVarPair = new ArrayList<Integer>();
		flipVarPair.add(xj);
		flipVarPair.add(xi);
		
		ArrayList<Integer> flipValPair = new ArrayList<Integer>();
		flipValPair.add(jv);
		flipValPair.add(iv);
	
		//IF THE KEYS ARE IN THE TABLE
		if(constraints.get(varPair)!=null){
			
			//IF THE VALUES CORRESPOND TO THE KEYS
			if(constraints.get(varPair).contains(valPair)){
				return false;
			}
			
			if(constraints.get(flipVarPair).contains(flipValPair)){
				return false;
			}
		}
		
		return true;
	}
	

	public static void main (String arg[]){
		
		/*
		//testing
		MapCSP problem = new MapCSP();
		*/

		
	}
}
