package tsp.metaheuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class Genetic extends AMetaheuristic {
	
	//Constructeur
	public Genetic(Instance instance) throws Exception {
		super(instance,"Genetic algorithm");
	}
	
	//Accesseur
	public Instance getInstance() {
		return super.m_instance;
	}
	
	//Crée une solution au parcours généré aléatoirement
	public Solution randSolution() throws Exception {
		Solution sol=new Solution(m_instance);
		List<Integer> rand = new ArrayList<Integer>();
		for(int i=1;i<this.getInstance().getNbCities();i++) {rand.add(i);}
		Collections.shuffle(rand);
		for(int i=1;i<this.getInstance().getNbCities();i++) {sol.setCityPosition(i, rand.get(i-1));}
		sol.evaluate();
		return sol;
	}
	
	//Crée une liste de solutions générées aléatoirement
	public List<Solution> newPopulation(int taille) throws Exception{
		List<Solution> pop = new ArrayList<Solution>();
		for(int i=0;i<taille;i++) {
			pop.add(this.randSolution());
		}
		return pop;
	}
	
	//retourne la liste des valeurs objectif d'une population
	public List<Long> getObjectiveValues(List<Solution> pop){
		List<Long> ov=new ArrayList<Long>();
		for(Solution i:pop) {
			ov.add(i.getObjectiveValue());
		}
		return ov;
	}
	
	
	
	public Solution solve(Solution sol) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

}
