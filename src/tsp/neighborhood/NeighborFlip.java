package tsp.neighborhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class NeighborFlip extends ANeighborhood{

	public NeighborFlip(Instance instance) throws Exception {
		super(instance, "NeighborFlip");
	}

	public List<Solution> getNeighborhood(Solution sol) throws Exception {
		int nbVilles=sol.getInstance().getNbCities();
		List<Solution> res=new ArrayList<Solution>();
		
		for(int startFlip=1;startFlip<nbVilles;startFlip++) {
			for(int endFlip=startFlip+1;endFlip<nbVilles;endFlip++) {
				
				List<Integer> villesNewSol=new ArrayList<Integer>();
				
				for(int i=0;i<startFlip;i++) {villesNewSol.add(sol.getCity(i));}
				for(int i=endFlip-1;i>=startFlip;i--) {villesNewSol.add(sol.getCity(i));}
				for(int i=endFlip;i<nbVilles;i++) {villesNewSol.add(sol.getCity(i));}
				villesNewSol.add(0);
				
				Solution newSol=new Solution(sol.getInstance());
				for(int i=0;i<nbVilles;i++) {newSol.setCityPosition(villesNewSol.get(i), i);}
				newSol.evaluate();
				res.add(newSol);
				
			}
		}
		
		return res;
	}
	
	public List<Long> getObjectiveValues(List<Solution> pop){
		List<Long> ov=new ArrayList<Long>();
		for(Solution i:pop) {ov.add(i.getObjectiveValue());}
		return ov;
	}
	
	public Solution bestSolution(Solution sol) throws Exception {
		List<Solution> voisins=this.getNeighborhood(sol);
		List<Long> ov=this.getObjectiveValues(voisins);
		return voisins.get(ov.indexOf(Collections.min(ov)));
	}
	
	

}
