package tsp.metaheuristic;

import java.util.ArrayList;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class LocalSearch extends AMetaheuristic {

	public LocalSearch(Instance instance, String name) throws Exception {
		super(instance,"Local Search");
	}

	public Solution solve(Solution sol) throws Exception {
		long delta=100000;
		List<Long> swaps=new ArrayList<Long>();	
		swaps.add(sol.getObjectiveValue());
		while (delta>0) {
			//on réalise tous les swaps possibles à partir de la solution initiale
			for(int d=0;d<sol.getInstance().getNbCities()-1;d++) {
				for(int i=sol.getInstance().getNbCities()-d-1;i>=0;i--) {
					swaps.add(this.swap(sol, sol.getInstance().getNbCities()-d, i).getObjectiveValue());
				}
			}
			
			//on choisit le meilleur swap en terme de objectiveValue
			
		}
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
