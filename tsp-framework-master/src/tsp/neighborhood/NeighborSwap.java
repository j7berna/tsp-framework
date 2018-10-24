package tsp.neighborhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class NeighborSwap extends ANeighborhood {
	
	public NeighborSwap(Instance instance) throws Exception {
		super(instance,"Swap");
	}
	
	public Instance getInstance() {
		return super.m_instance;
	}
	
	
	/** getNeighborhood(Solution sol)
	 * @return Retourne les positions voisines de sol (règle des swap) 
	 * 
	 * Méthode fonctionnelle
	 */
	public List<Solution> getNeighborhood(Solution sol) throws Exception {
		List<Solution> voisins = new ArrayList<Solution>();
		
		for(int d=1;d<this.getInstance().getNbCities()-1;d++) {
			for(int i=d+1;i<this.getInstance().getNbCities();i++) {
				voisins.add(this.swap(sol, d, i));
			}
		}
		
		return voisins;
		
	}
	
	/** swap(Solution s, int pos_i, int pos_j)
	 * 
	 * @param s la solution à étudier
	 * @param pos_i 
	 * @param pos_j 
	 * @return Copie la solution s, échange les villes en position i et j et retourne la solution recalculée
	 * 
	 * Méthode fonctionnelle
	 */
		public Solution swap(Solution s, int pos_i, int pos_j) throws Exception{
			Solution sol= s.copy();
			
			int ville_arrivee=sol.getCity(pos_j);
			sol.setCityPosition(sol.getCity(pos_i),pos_j);
			sol.setCityPosition(ville_arrivee, pos_i);
			sol.evaluate();
			
			return sol;
		}
		
		/** bestSolution(Solution sol)
		 * 
		 * @param s la solution à étudier
		 * @return Retourne la meilleure solution parmi l'entourage de la solution sol
		 * 
		 */
		public Solution bestSolution(Solution sol) throws Exception {
			List<Solution> voisins=this.getNeighborhood(sol);
			long j=Long.MAX_VALUE;
			Solution best=null;

			for(Solution i:voisins) {
				if (i.getObjectiveValue()<j) {
					best=i;
					j=i.getObjectiveValue();
				}
			}
			return best;
		}
	
	

}
