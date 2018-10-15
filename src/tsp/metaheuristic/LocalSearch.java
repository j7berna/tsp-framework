package tsp.metaheuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class LocalSearch extends AMetaheuristic {

	public LocalSearch(Instance instance) throws Exception {
		super(instance,"Local Search");
	}

	//première version non optimisée
	public Solution solve(Solution sol) throws Exception {
		long delta=Integer.MAX_VALUE;
		while (delta>0) {
			
			List<Solution> swaps=new ArrayList<Solution>();
			swaps.add(sol);
			//on fait une liste de l'ensemble des swaps possibles
			for(int d=0;d<sol.getInstance().getNbCities()-1;d++) {
				for(int i=sol.getInstance().getNbCities()-d-1;i>=0;i--) {
					swaps.add(this.swap(sol, sol.getInstance().getNbCities()-d, i));
				}
			}
			
			//on cherche la meilleure solution en terme d'objectiveValue
			List<Long> swaps_o = new ArrayList<Long>();
			for(Solution i:swaps) { swaps_o.add(i.getObjectiveValue());	}
			Solution best=swaps.get(swaps_o.indexOf(Collections.min(swaps_o)));
			
			//actualisation du delta
			delta=sol.getObjectiveValue()-best.getObjectiveValue();
			if (delta>0) sol=best;
		}
		return sol;
	}
	
	//réalise un swap entre les villes en position i et j dans la solution s
	public Solution swap(Solution s, int pos_i, int pos_j) throws Exception{
		int ville_arrivee=s.getCity(pos_j);
		s.setCityPosition(s.getCity(pos_i),pos_j);
		s.setCityPosition(ville_arrivee, pos_i);
		s.evaluate();
		return s;
	}
	

}
