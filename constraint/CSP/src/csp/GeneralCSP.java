//Author: James Jia

package csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Generic csp interface
public interface GeneralCSP {
	public List<ArrayList<Integer>> getVariableList();
	public HashMap<Integer, int[]> getAdjacency();
	public Constraint getConstraint();
}
