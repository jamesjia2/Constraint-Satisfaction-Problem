//Author: James Jia

package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircuitCSP implements GeneralCSP{

	public String[] shapes = { "A", "B", "C", "E"};
	public int[] variables = { 0, 1, 2, 3};

	Constraint constraint = new Constraint();
	
	public List<ArrayList<Integer>> variableList = new ArrayList<ArrayList<Integer>>();
	public HashMap<Integer, int[]> adjacency = new HashMap<Integer, int[]>();
	public ArrayList<int[]> dimensions = new ArrayList<int[]>();
	public HashMap<Integer, int[]> coordinateMap = new HashMap<Integer, int[]>();

	int height=3;
	int width=10;
	
	public CircuitCSP() {

		//INITIALIZE DOMAINS AS ALL POSSIBLE DOMAIN KEYS FOR COORD MAP
		int coordKey = 0;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				int[] coordinate = {x,y};
				coordinateMap.put(coordKey, coordinate);
				coordKey++;
			}
		}
		
		//MAKE DIMENSIONS LIST - ADDS SHAPES IN ORDER
		int[] aDim = {3,2};
		dimensions.add(aDim);
		int[] bDim = {5,2};
		dimensions.add(bDim);
		int[] cDim = {2,3};
		dimensions.add(cDim);
		int[] eDim = {7,1};
		dimensions.add(eDim);
		
		//MAKE VARIABLE LIST
		for (int i : variables) {
			ArrayList<Integer> var = new ArrayList<Integer>();
			int[] dim = dimensions.get(i);
			int domKey = 0;
			
			//LOOP THROUGH ALL XY COMBINATIONS
			for (int y=0; y<height; y++) {
				for (int x=0; x<width; x++) {
					
					//IF WITHIN 10X3 BOUNDS, ADD INTO DOM
					if ((x+dim[0]<=width) && (y+dim[1]<=height)){
							var.add(domKey);
					}
					domKey++;
				}
			}
		variableList.add(var);
		}
		
		//MAKE ADJACENCY
		int[] Aneighbors = {1,2,3};
		adjacency.put(0, Aneighbors);
		int[] Bneighbors = {0,2,3};
		adjacency.put(1, Bneighbors);
		int[] Cneighbors = {0,1,3};
		adjacency.put(2, Cneighbors);
		int[] Eneighbors = {0,1,2};
		adjacency.put(3, Eneighbors);
		
		//SET CONSTRAINTS - REFERENCED http://www.geeksforgeeks.org/find-two-rectangles-overlap/
		HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>> consMap 
		= new HashMap<ArrayList<Integer>, ArrayList<ArrayList<Integer>>>();
		
		//LOOP THROUGH ALL ADJACENT VARIABLE COMBINATIONS
		for(int i: variables){
			for(int j: variables){
				
				if(isAdjacent(adjacency.get(i),j)){
					
					//DIMENSIONS
					int[] dim1 = dimensions.get(i);
					int[] dim2 = dimensions.get(j);
					
					//MAKE KEYS
					ArrayList<Integer> varPair = new ArrayList<Integer>();
					varPair.add(i);
					varPair.add(j);
					
					//LOOP THROUGH DOMAINS
					ArrayList<ArrayList<Integer>> badValues = new ArrayList<ArrayList<Integer>>();
					for (int x : variableList.get(i)) {
						for (int y : variableList.get(j)) {
							
							//GET ACTUAL XY COORDS FROM HASHMAP
							int[] coord1 = coordinateMap.get(x);
							int[] coord2 = coordinateMap.get(y);
							
							//BOTTOM LEFT1
							int[] bottomleft1 = {coord1[0], coord1[1]};
							
							//TOP RIGHT1
							int[] topright1 = {coord1[0] + dim1[0] - 1, coord1[1] + dim1[1] - 1};
							
							//BOTTOM LEFT2
							int[] bottomleft2 = {coord2[0], coord2[1]};
							
							//TOP RIGHT2
							int[] topright2 = {coord2[0] + dim2[0] - 1, coord2[1] + dim2[1] - 1};
							
							//RECTANGLE OVERLAP CHECK
							if(!( (bottomleft1[0] > topright2[0] || bottomleft1[1]>topright2[1]) || 
									(topright1[0]<bottomleft2[0] || topright1[1]<bottomleft2[1]) )){

								//IF DOESN'T PASS CHECK, ADD IN DISALLOWED VALUES
								ArrayList<Integer> temp = new ArrayList<Integer>();
								temp.add(x);
								temp.add(y);
								badValues.add(temp);
							}
						}
					}
					consMap.put(varPair, badValues);
				}
			}
		}
		constraint.constraints = consMap;
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


	public static void main (String arg[]){
		
		/*
		//testing
		CircuitCSP problem = new CircuitCSP();
		*/
	}







}
