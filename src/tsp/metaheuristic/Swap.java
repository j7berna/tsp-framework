package tsp.metaheuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;
import tsp.neighborhood.NeighborSwap;

public class Swap extends AMetaheuristic {
	
	/**
	 * Vaut true si la méthode solve a été exécutée
	 */
	protected boolean isDone;

	/**
	 * this.m_name="Local Search"
	 * this.isDone=false
	 * @param instance
	 * @throws Exception
	 */
	public Swap(Instance instance) throws Exception {
		super(instance,"Local Search");
		this.isDone=false;
	}
	
	/**
	 * 
	 * @return super.m_instance 
	 */
	public Instance getInstance() {return super.m_instance;}
	/**
	 * 
	 * @return this.isDone
	 */
	public boolean isDone() {return this.isDone;}
	
	/**
	 * 
	 * @return Solution au parcours initialisé aléatoirement
	 * @throws Exception
	 */
	public Solution randInit() throws Exception {
		Solution sol=new Solution(m_instance);
		List<Integer> rand = new ArrayList<Integer>();
		for(int i=1;i<this.getInstance().getNbCities();i++) {rand.add(i);}
		Collections.shuffle(rand);
		for(int i=1;i<this.getInstance().getNbCities();i++) {sol.setCityPosition(i, rand.get(i-1));}
		sol.evaluate();
		return sol;
	}
	
	/**
	 * 
	 * @return Solution au parcours initialisé dans l'ordre, ville i en position i
	 * @throws Exception
	 */
	public Solution ordInit() throws Exception{
		Solution sol=new Solution(this.m_instance);
		for(int i=0;i<this.getInstance().getNbCities();i++) {sol.setCityPosition(i, i);}
		sol.evaluate();
		return sol;
	}
	
	/**
	 * @return Solution construite par recherche locale/swap
	 */
	public Solution solve(Solution init) throws Exception {
		long delta=Integer.MAX_VALUE;
		
		//Solution sol2=init;
		
		Solution sol2=init;
		
		//Initialisation avec une autre heuristique
		//InsertionB init=new InsertionB(this.getInstance());
		//sol2=init.solve(sol2);
		
		//Création d'un voisinage
		NeighborSwap voisins=new NeighborSwap(this.getInstance());
		Solution best=sol2;

		while (delta>0) {
			//best est la meilleure solution du voisinage de sol2
			best=voisins.bestSolution(sol2);
			//actualisation du delta
			delta=sol2.getObjectiveValue()-best.getObjectiveValue();
			//Si best est meilleure que sol2 on remplace sol2
			if (delta>0) sol2=best;
		}
					
		this.isDone=true;
		
		best.evaluate();
		
		return best;
		
	}

	

}
