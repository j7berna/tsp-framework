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
	
	

	public Solution solve(Solution sol) throws Exception {
		Solution sol2=sol.copy();
		long delta=Integer.MAX_VALUE;

		
		//Initialisation dans l'ordre
		//for(int i=0;i<this.getInstance().getNbCities();i++) {sol2.setCityPosition(i, i);}
		
		//Initialisation aléatoire
		List<Integer> rand = new ArrayList<Integer>(this.m_instance.getNbCities());
		for(int i=1;i<this.m_instance.getNbCities();i++) {rand.set(i,i);}
		Collections.shuffle(rand);
		for(int i=0;i<this.getInstance().getNbCities();i++) {
			if (i!=0) {
				sol2.setCityPosition(i, rand.get(i));
			}

		}
		sol2.print(System.err);
		
		
		
		sol2.evaluate();
		
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
