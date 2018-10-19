package tsp.metaheuristic;

import tsp.Instance;
import tsp.Solution;
import tsp.neighborhood.Swap;

public class LocalSearch extends AMetaheuristic {
	
	protected boolean isDone;

	//Constructeur
	public LocalSearch(Instance instance) throws Exception {
		super(instance,"Local Search");
		this.isDone=false;
	}
	
	//accesseur de la variable d'instance
	public Instance getInstance() {
		return super.m_instance;
	}
	
	public boolean isDone() {
		return this.isDone;
	}
	
	

	//première version non optimisée
	public Solution solve(Solution sol) throws Exception {
		//initialisation de delta à l'infini
		long delta=Integer.MAX_VALUE;
			
		Solution sol2=sol.copy();
		
		for(int i=0;i<this.getInstance().getNbCities();i++) {
			sol2.setCityPosition(i, i);
		}
		
		Swap voisins=new Swap(this.getInstance());
		Solution best=sol2;

		while (delta>0) {
			best=voisins.bestSolution(sol2);
						
			//actualisation du delta
			delta=sol2.getObjectiveValue()-best.getObjectiveValue();
			
			if (delta>0) 
				sol2=best;
			
		}
		this.isDone=true;
		return sol2;
	}
	

}
