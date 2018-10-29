package tsp.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;
import tsp.neighborhood.NeighborFlip;

public class TwoOpt extends AHeuristic {
	
	public boolean isDone;

	public TwoOpt(Instance instance) throws Exception {
		super(instance, "2-Opt algorithm");
		this.isDone=false;
	}
	
	public boolean isDone() {
		return this.isDone;
	}
	
	public Solution randSolution() throws Exception {
		Solution sol=new Solution(m_instance);
		
		int nbVilles=sol.getInstance().getNbCities();
		
		List<Integer> rand = new ArrayList<Integer>();
		for(int i=1;i<nbVilles;i++) {rand.add(i);}
		Collections.shuffle(rand);
		for(int i=1;i<nbVilles;i++) {sol.setCityPosition(i, rand.get(i-1));}
		sol.evaluate();
		return sol;
	}

	public void solve() throws Exception {
		Solution init=this.randSolution();
		Solution best=init;

		NeighborFlip nf=new NeighborFlip(init.getInstance());
		
		long delta=Long.MAX_VALUE;
		
		while(delta>0) {
			best=nf.bestSolution(init);
			delta=init.getObjectiveValue()-best.getObjectiveValue();
			if (delta>0) init=best;
		}
		
		this.isDone=true;
		best.evaluate();
		this.m_solution=best;
	}
	
	

}
