package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ConstraintSatisfactionProblem {

	GeneralCSP problem;
	final static int UNASSIGNED = -1;
	final static int posInfinity = 1000000;
	int[] assignment;
	int vNum;
	int revisions = 0;
	int nodes=0;
	
	List<ArrayList<Integer>> vList;
	HashMap<Integer, int[]> aList;
	Constraint constraint;
	
	public ConstraintSatisfactionProblem(GeneralCSP prob) {
		
		//PASS IN CSP
		problem = prob;
		vNum = problem.getVariableList().size();

		//INITIALIZE ASSIGNMENT ARRAY
		assignment = new int[vNum];
		
		//INITIALIZE UNASSIGNED AS ARBITRARY NUMBER (-1)
		for(int i=0; i<vNum; i++){
			assignment[i] = UNASSIGNED;
		}
		
		//CREATE REFERENCES TO CSP'S VARIABLE LIST AND CONSTRAINT
		vList = prob.getVariableList();
		aList = prob.getAdjacency();
		constraint = prob.getConstraint();
		
	}
	
	public int[] backtracker() {
		
		//ALL ASSIGNED
		if (isComplete()){
			return assignment;
		}
		
		//COPY VARIABLE LIST FOR RESET LATER
		List<ArrayList<Integer>> copy = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<vList.size(); i++){
			copy.add(vList.get(i));
		}
		
		//GET UNASSIGNED VARIABLE USING MRV HEURISTIC
		int var = getUnassignedMRV();
		//int var = getFirstUnassigned();
		
		//TRY EACH DOMAIN VALUE
		for(int i=0; i<vList.get(var).size(); i++){
			
			//INCREMENT # OF TRIED ASSIGNMENTS
			nodes++;
			
			//ASSIGN
			int value = vList.get(var).get(i);
			assignment[var]=value;
			
			//IF CONSISTENT
			if(isConsistent(var)){

				//UPDATE DOMAIN WITH ASSIGNMENT
				vList.get(var).clear();
				vList.get(var).add(value);
				
				//INFERENCE
				boolean inferences = inference(var);
				
				//IF PASS INFERENCE
				if (inferences) {
					int[] result = backtracker();
					if (result != null)
						return result;
				}
				
			}
			
			//UNDO ASSIGNMENT AND DOM CHANGES WHEN BACKTRACKING
			assignment[var] = UNASSIGNED;
			vList = copy;
		}
	
		//NOTHING FOUND
		return null;
	}
	
	//INFERENCE USING MAC3
	public boolean inference(int var) {
		
		//GET UNASSIGNED NEIGHBORS
		List<Integer> uaNeighbors = new ArrayList<Integer>();
		for (int neighbor : aList.get(var)) {
			if (assignment[neighbor] == UNASSIGNED)
				uaNeighbors.add(neighbor);
		}
		
		//QUEUE OF ARCS
		Queue<int[]> exploreQueue = new LinkedList<int[]>();
		
		//MAKE ARCS
		for (int uaNeighbor : uaNeighbors) {
			int[] tempArc = {uaNeighbor, var};
			exploreQueue.add(tempArc);
		}
		
		//ITERATE THROUGH ARCS
		while (!exploreQueue.isEmpty()) {
			
			//PULL FIRST ARC AND TEST FOR REVISE
			int[] arc = exploreQueue.poll();
			if (revise(arc[0], arc[1])) {
				
				//IF NO DOMAINS LEFT, FAILS INFERENCE
				if (vList.get(arc[0]).size()==0){
					return false;
				}
				
				//OTHERWISE, CONSIDER REVISED VARIABLE'S NEIGHBORS
				else{
					int[] newNeighbors = aList.get(arc[0]);
					for (int newNeighbor : newNeighbors) {
						
						//DON'T REPEAT THE PREVIOUS ARC
						if (newNeighbor != arc[1]){
							int[] newArc = {newNeighbor, arc[0]};
							exploreQueue.add(newArc);
						}
					}
				}
			}
		}
		return true;
		
	}
	
	public boolean revise(int xi, int xj) {
	
		//INCREMENT NUMBER OF REVISIONS
		revisions++;
		
		boolean revise = false;
		
		//GET XI DOMAINS
		ArrayList<Integer> xiDomains = new ArrayList<Integer>();
		for(int i: vList.get(xi)){
			xiDomains.add(i);
		}
		
		//LOOP THROUGH XI'S DOMAINS
		for (int di : xiDomains) {
			
			boolean pass = false;
			
			//GET XJ DOMAINS
			ArrayList<Integer> xjDomains = new ArrayList<Integer>();
			for(int j: vList.get(xj)){
				xjDomains.add(j);
			}
			
			//LOOP THROUGH XJ'S DOMAINS
			for (int dj : xjDomains) {

				//TEST CONSTRAINT - IF ANY DOM SATISFIES CONSTRAINT, THEN PASS
				if (problem.getConstraint().isSatisfied(xi, xj, di, dj)){
					pass = true;
					break;
				}
			}
			
			//IF DID NOT PASS CONSTRAINT TEST
			if (!pass) {

				//REMOVE DOM VALUE FROM XI'S DOM
				vList.get(xi).remove(vList.get(xi).indexOf(di));
				revise = true;
			}
		}
		return revise;
	}

	public int getFirstUnassigned() {
		
		//GET FIRST UNASSIGNED
		for (int i = 0; i < assignment.length; i++) {
			if (assignment[i] == UNASSIGNED) {
				return i;
			}
		}

		System.out.println("all assigned");
		return UNASSIGNED;
		
	}
	
	//MRV HEURISTIC - SELECTS UNASSIGNED WITH SMALLEST DOMAIN
	public int getUnassignedMRV() {
		
		//INITIALIZE BEST VALUE AS VERY LARGE NUMBER
		int best = posInfinity;
		int var = UNASSIGNED;

		//LOOP THROUGH ASSIGNMENTS
		for (int i = 0; i<assignment.length; i++) {
			
			//FOR EACH UNASSIGNED VALUE
			if (assignment[i] == UNASSIGNED){
				
				//IF BETTER THAN PREVIOUS BEST VALUE, KEEP
				if (vList.get(i).size() < best) {
					best = vList.get(i).size();
					var = i;
				}
			}
		}
		
		if(var == UNASSIGNED){
			System.out.println("all assigned");
		}

		return var;
	}
	
		
	//CHECK CURRENT ASSIGNMENT CONSISTENCY
	public boolean isConsistent(int var) {

		boolean consistent = true;
		
		for (int i = 0; i < vNum; i++) {
			if (constraint.isSatisfied(var, i, assignment[var], assignment[i])==false)
				consistent = false;
		}
		
		return consistent;
	}
	
	//CHECK IF ANY UNASSIGNED LEFT
	public boolean isComplete(){
		
		boolean complete = true;

		for (int i = 0; i<assignment.length; i++) {
			if (assignment[i] == UNASSIGNED)
				complete = false;
		}
	
		return complete;

		
	}
	
	public static void main (String arg[]){
		
		//TESTING
		/*
		System.out.println("MAP PROBLEM");
		System.out.println("---------");
		MapCSP problem = new MapCSP();

		ConstraintSatisfactionProblem b = new ConstraintSatisfactionProblem(problem);
		b.backtrack();
		
		for(int i=0; i<b.assignment.length; i++){
			System.out.println((b.assignment)[i]);
		}
		
		System.out.println("node count: "+b.nodes);
		System.out.println("revision count: "+b.revisions);
		
		System.out.println("\n");
		System.out.println("CIRCUIT PROBLEM");
		System.out.println("---------");
		
		CircuitCSP problem1 = new CircuitCSP();

		ConstraintSatisfactionProblem b1 = new ConstraintSatisfactionProblem(problem1);
		b1.backtrack();
		
		for(int i=0; i<b1.assignment.length; i++){
			System.out.println((b1.assignment)[i]);
		}
		
		System.out.println("node count: "+b1.nodes);
		System.out.println("revision count: "+b1.revisions);
		*/

	}

}
