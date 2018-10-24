package tsp.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class Genetic extends AHeuristic {
	
	private boolean isDone;
	
	//Constructeur
	public Genetic(Instance instance) throws Exception {
		super(instance,"Genetic algorithm");
		this.isDone=false;
	}
	
	//Accesseurs
	public Instance getInstance() {return super.m_instance;}
	public boolean isDone() {return this.isDone;}
	
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
	
	//retourne les deux meilleures solutions d'une population
	public List<Solution> getTwoBest(List<Solution> pop){
		List<Solution> res=new ArrayList<Solution>();
		List<Long> ov=this.getObjectiveValues(pop);
		int min1=ov.indexOf(Collections.min(ov));
		ov.remove(min1);
		int min2=ov.indexOf(Collections.min(ov));
		if (min2>=min1) min2++;
		res.add(pop.get(min1));
		res.add(pop.get(min2));
		return res;
	}
	
	// Génère une solution fille
	public Solution crossover(List<Solution> deuxsol) throws Exception {
		Solution bebe=new Solution(this.getInstance());
		int nbVilles=deuxsol.get(0).getInstance().getNbCities();
		for(int i=0;i<nbVilles;i++) {
			if(Math.random()<.5) {
				bebe.setCityPosition(deuxsol.get(0).getCity(i), i);
			}
			else {
				bebe.setCityPosition(deuxsol.get(1).getCity(i), i);
			}
		}
		bebe.evaluate();
		return bebe;
	}
	
	//swap des villes en position i et j dans la solution s
	public Solution swap(Solution s, int pos_i, int pos_j) throws Exception{
		Solution sol= s.copy();
		int ville_arrivee=sol.getCity(pos_j);
		sol.setCityPosition(sol.getCity(pos_i),pos_j);
		sol.setCityPosition(ville_arrivee, pos_i);
		sol.evaluate();
		
		return sol;
	}
	
	//mute aléatoirement la solution s, le nombre de mutations dépend d'une probabilité de mutation
	public Solution mutation(Solution s) throws Exception {
		double p_mutation=.01;
		int nbVilles=s.getInstance().getNbCities();
		for(int i=1;i<nbVilles;i++) {
			if (Math.random()<p_mutation)
				s=this.swap(s, i,(int)(nbVilles*Math.random()));
		}
		s.evaluate();
		return s;
	}
	
	public List<Solution> generation(int taille, List<Solution> prec) throws Exception{
		List<Solution> gen=new ArrayList<Solution>();
		for(int i=0;i<taille;i++) {
			gen.add(this.mutation(this.crossover(this.getTwoBest(prec))));
		}
		return gen;
	}
	
	
	public void solve() throws Exception {
		int nb_generations=3;
		int taille=100;
		
		List<Solution> gen=this.newPopulation(taille);
		
		for(int g=0;g<nb_generations;g++) {
			gen=generation(taille,gen);
		}
		
		this.m_solution=gen.get(gen.indexOf(this.getTwoBest(gen).get(0)));
		this.m_solution.evaluate();
		this.isDone=true;

	}
	

}
