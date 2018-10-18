package tsp.metaheuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;
import tsp.neighborhood.Swap;

public class LocalSearch extends AMetaheuristic {

	public LocalSearch(Instance instance) throws Exception {
		super(instance,"Local Search");
	}

	//première version non optimisée
	public Solution solve(Solution sol) throws Exception {
		long delta=Integer.MAX_VALUE;
		
		//initialisation de la solution
		for(int i=0;i<sol.getInstance().getNbCities();i++) {
			sol.setCityPosition(i, i);
		}
		
		while (delta>0) {
			//Un swap représente un voisinage
			Swap voisins=new Swap(sol.getInstance());
			
			//actualisation du delta
			delta=sol.getObjectiveValue()-voisins.bestSolution(sol).getObjectiveValue();
			if (delta>0) sol=voisins.bestSolution(sol);
			
		}
		return sol;
	}
	

}
