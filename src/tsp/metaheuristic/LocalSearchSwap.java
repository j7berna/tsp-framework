package tsp.metaheuristic;

import tsp.Instance;
import tsp.Solution;
import tsp.neighborhood.Swap;

public class LocalSearchSwap extends AMetaheuristic {
	
	//Variable d'instance valant true si la recherhe locale est terminée (condition de sortie de boucle dans TSPSolver)
	protected boolean isDone;

	//Constructeur
	public LocalSearchSwap(Instance instance) throws Exception {
		super(instance,"Local Search");
		this.isDone=false;
	}
	
	//Accesseurs
	public Instance getInstance() {return super.m_instance;}
	public boolean isDone() {return this.isDone;}
	
	

	public Solution solve(Solution sol) throws Exception {
		Solution sol2=sol.copy();
		
		//Initialisation
		long delta=Integer.MAX_VALUE;
		for(int i=0;i<this.getInstance().getNbCities();i++) {sol2.setCityPosition(i, i);}
		sol2.evaluate();
		
		//Création d'un voisinage
		Swap voisins=new Swap(this.getInstance());
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
		return sol2;
	}
	

}
