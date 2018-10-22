package tsp.heuristic;

import java.util.ArrayList;
import java.util.List;

import tsp.Instance;

public class Heuristic1 extends AHeuristic {
	
	public Heuristic1(Instance instance) throws Exception {
		super(instance,"Plus proche voisin");
	}

	public void solve() throws Exception {
		
		int nb_cities=this.m_instance.getNbCities();
		
		List<Integer> solution = new ArrayList<Integer>(nb_cities+1);
		
		solution.set(0, 0);
		solution.set(nb_cities, 0);
		
		for(int i=0;i<nb_cities;i++) {
			if (!solution.contains)
		}
		
	}

}
