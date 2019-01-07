//Author: James Jia

package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapCSP implements GeneralCSP{

	public String[] countries = { "WESTERN AUSTRALIA", "SOUTH AUSTRALIA", "NORTHERN TERRITORY", 
									"QUEENSLAND", "NEW SOUTH WALES", "VICTORIA", "TASMANIA" };
	
	public int[] variables = { 0, 1, 2, 3, 4, 5, 6 };
	
	public String[] colors = { "RED", "GREEN", "BLUE" };
	int[] domains = {0,1,2};
	
	public List<ArrayList<Integer>> variableList = new ArrayList<ArrayList<Integer>>();
	public HashMap<Integer, int[]> adjacency = new HashMap<Integer, int[]>();
	Constraint constraint = new Constraint();
	
	//INITIALIZE VARIABLE LIST, ADJACENCY LIST, AND CONSTRAINT MAP
	public MapCSP() {
		
		//MAKE VARIABLELIST - INITIALIZE WITH ALL DOMAINS
		for(int i=0; i<variables.length; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j = 0; j<domains.length; j++){
				temp.add(domains[j]);
			}
			variableList.add(temp);
		}
		
		//MAKE ADJACENCY LIST
		int[] WAneighbors = {2,1};
		adjacency.put(0, WAneighbors);
		int[] SAneighbors = {0,2,3,4,5};
		adjacency.put(1, SAneighbors);
		int[] NTneighbors = {0,1,3};
		adjacency.put(2, NTneighbors);
		int[] Qneighbors = {2,1,4};
		adjacency.put(3, Qneighbors);
		int[] NSWneighbors = {3,1,5};
		adjacency.put(4, NSWneighbors);
		int[] Vneighbors = {1,4};
		adjacency.put(5, Vneighbors);
		int[] Tneighbors = {};
		adjacency.put(6, Tneighbors);
		
		
		//SET CONSTRAINTS
		HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> consMap = new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();
		
		//loop through all pairs of variables
		for (int i: variables){
			
			for (int j: variables){
				
				//if neighbors
				if(isAdjacent(adjacency.get(i),j)){
					
					ArrayList<Integer> varPair = new ArrayList<Integer>();
					varPair.add(i);
					varPair.add(j);
					
					//neighbors can't be same value - put invalid combinations into the constraint map
					ArrayList<ArrayList<Integer>> badVals = new ArrayList<ArrayList<Integer>>();
					
					ArrayList<Integer> cons1 = new ArrayList<Integer>();
					cons1.add(0);
					cons1.add(0);
					
					ArrayList<Integer> cons2 = new ArrayList<Integer>();
					cons2.add(1);
					cons2.add(1);
					
					ArrayList<Integer> cons3 = new ArrayList<Integer>();
					cons3.add(2);
					cons3.add(2);

					badVals.add(cons1);
					badVals.add(cons2);
					badVals.add(cons3);
					
					consMap.put(varPair,  badVals);
				}
			}
		}
		
		//set the constraint map in the constraint
		constraint.constraints = (consMap);
	}
	
	
	public boolean isAdjacent(int[] array, int value) {
		
	    for (int i : array) {
	        if (i == value) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	@Override
	public List<ArrayList<Integer>> getVariableList() {
		return variableList;
	}
	
	@Override
	public HashMap<Integer, int[]> getAdjacency() {
		return adjacency;
	}

	@Override
	public Constraint getConstraint() {
		return constraint;
	}


	//test MapCSP initialization
	public static void main (String arg[]){
		
		/*
		MapCSP problem = new MapCSP();

		System.out.println(problem.variableList.get(0));
		System.out.println(problem.variableList.get(1));
		
		ArrayList<Integer >temp = new ArrayList<Integer>();
		temp.add(1);
		
		System.out.println(problem.adjacency.get(5)[1]);
		*/

	}




}
