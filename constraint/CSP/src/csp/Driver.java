//Author: James Jia

package csp;

public class Driver {

	public static void main(String[] args) {
		
		System.out.println("MAP PROBLEM");
		System.out.println("---------");
		MapCSP problem = new MapCSP();

		ConstraintSatisfactionProblem p = new ConstraintSatisfactionProblem(problem);
		p.backtracker();
		
		for(int i=0; i<p.assignment.length; i++){
			
			int color = p.assignment[i];
			String realColor = problem.colors[color];
			String variableName = problem.countries[i];
			
			System.out.println("State: "+variableName+"  Color: "+realColor);
		}
		
		System.out.println("node count: "+p.nodes);
		System.out.println("revision count: "+p.revisions);
		
		System.out.println("\n");
		
		System.out.println("CIRCUIT PROBLEM");
		System.out.println("---------");
		
		CircuitCSP problem1 = new CircuitCSP();

		ConstraintSatisfactionProblem p1 = new ConstraintSatisfactionProblem(problem1);
		p1.backtracker();
		
		for(int i=0; i<p1.assignment.length; i++){
			
			String variableName = problem1.shapes[i];
			int assign = p1.assignment[i];
			int[] realCoord = problem1.coordinateMap.get(assign);
			
			System.out.println("Shape: "+variableName+"  Bottom Left Corner Coordinate: "
								+"("+realCoord[0]+","+realCoord[1]+")");
		}
		
		System.out.println("node count: "+p1.nodes);
		System.out.println("revision count: "+p1.revisions);
		
		System.out.println("\n");
		System.out.println("CIRCUITBOARD ASCII REPRESENTATION");
		
		for(int y=2; y>=0; y--){
			
			String s = "";
			for(int x=0; x<10; x++){
				
				String val = "*";
				
				if(x==2&&y==0){
					val = "A";
				}

				if(x==5&&y==0){
					val = "B";
				}
				
				if(x==0&&y==0){
					val = "C";
				}
				
				if(x==2&&y==2){
					val = "E";
				}
				
				s+=val;
			}
			System.out.println(s);
		}

	}

}
