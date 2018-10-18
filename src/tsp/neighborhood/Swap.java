package tsp.neighborhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class Swap extends ANeighborhood {
	
	public Swap(Instance instance) throws Exception {
		super(instance,"Swap");
	}

	public List<Solution> getNeighborhood(Solution sol) throws Exception {
		List<Solution> swap = new ArrayList<Solution>();
		swap.add(sol);
		
		for(int d=0;d<sol.getInstance().getNbCities()-2;d++) {
			for(int i=d+1;i<sol.getInstance().getNbCities();i++) {
				swap.add(this.swap(sol, d, i));
			}
		}
		
		return swap;
		
	}
	
	//réalise un swap entre les villes en position i et j dans la solution s
		public Solution swap(Solution s, int pos_i, int pos_j) throws Exception{
			Solution sol= s.copy();
			
			int ville_arrivee=sol.getCity(pos_j);
			sol.setCityPosition(sol.getCity(pos_i),pos_j);
			sol.setCityPosition(ville_arrivee, pos_i);
			sol.evaluate();
			return sol;
		}
		
		//meilleure solution de l'entourage de sol
		public Solution bestSolution(Solution sol) throws Exception {
			List<Long> swaps_o = new ArrayList<Long>();
			List<Solution> swaps = this.getNeighborhood(sol);
			for(Solution i:swaps) { swaps_o.add(i.getObjectiveValue());	}
			return swaps.get(swaps_o.indexOf(Collections.min(swaps_o)));
		}
	
	

}
