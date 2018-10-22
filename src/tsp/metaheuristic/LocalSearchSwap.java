package tsp.metaheuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;
import tsp.neighborhood.NeighborSwap;

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
	
	//Initialisation aléatoire
	public Solution randInit(Solution sol) throws Exception {
		List<Integer> rand = new ArrayList<Integer>();
		for(int i=1;i<this.getInstance().getNbCities();i++) {rand.add(i);}
		Collections.shuffle(rand);
		for(int i=1;i<this.getInstance().getNbCities();i++) {sol.setCityPosition(i, rand.get(i-1));}
		sol.evaluate();
		return sol;
	}
	
	

	public Solution solve(Solution sol) throws Exception {
		Solution sol2=sol.copy();
		long delta=Integer.MAX_VALUE;
		
		//Initialisation dans l'ordre
		//for(int i=0;i<this.getInstance().getNbCities();i++) {sol2.setCityPosition(i, i);}
		
		//Initialisation aléatoire
		sol2=this.randInit(sol2);
		
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
		return sol2;
	}
	

}
