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
	
	//retourne la liste de villes d'une solution
	public List<Integer> getVilles(Solution s) throws Exception{
		List<Integer> res=new ArrayList<Integer>();
		for(int i=0;i<=this.getInstance().getNbCities();i++) {res.add(s.getCity(i));}
		return res;
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
	//Méthode non fonctionnelle !
	public Solution crossover(Solution parent1, Solution parent2) throws Exception {
		int nbVilles=this.getInstance().getNbCities();

		List<Integer> villes_parent1=this.getVilles(parent1);
		List<Integer> villes_parent2=this.getVilles(parent2);
		
		
		
		List<Integer> villesBebe=this.crossover(villes_parent1, villes_parent2);
		
		Solution bebe=new Solution(this.getInstance());
		for(int i=0;i<nbVilles;i++) {bebe.setCityPosition(villesBebe.get(i), i);}
		bebe.evaluate();
		return bebe;
		
	}
	
	//méthode identique à crossover avec les listes 
	public List<Integer> crossover(List<Integer> villes_parent1, List<Integer> villes_parent2) {
		//ligne différente
		int nbVilles=villes_parent1.size()-1;

		
		//lignes différentes
		List<Integer> villesParent1=new ArrayList<Integer>(villes_parent1);
		List<Integer> villesParent2=new ArrayList<Integer>(villes_parent2);

		//Calcul des index de début et de fin, FONCTIONNEL
		int start_mut=1+(int)(Math.random()*nbVilles);
		int end_mut = 1+(int)(Math.random()*nbVilles);
		if (start_mut>end_mut) {
			int a=end_mut;
			end_mut=start_mut;
			start_mut=a;}
		if (start_mut==end_mut) end_mut++;
		
		int[] villesBebe=new int[nbVilles+1];
		for (int i=start_mut;i<end_mut;i++) {
			villesBebe[i]=villesParent1.get(i);
			villesParent2.remove(villesParent1.get(i));
		}
		villesParent2.remove(0);
		villesParent2.remove(villesParent2.size()-1);
				
		int i=1;
		while (!villesParent2.isEmpty()) {
			if (villesBebe[i]==0) {
				villesBebe[i]=villesParent2.get(0);
				villesParent2.remove(0);
			}
			i++;
		}

		List<Integer> bebe=new ArrayList<Integer>();
		for(int e:villesBebe) {bebe.add(e);}
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
				s=this.swap(s, i,1+(int)(nbVilles*Math.random()));
		}
		s.evaluate();
		return s;
	}
	
	public List<Solution> generation(int taille, List<Solution> prec) throws Exception{
		List<Solution> gen=new ArrayList<Solution>();
		for(int i=0;i<taille;i++) {
			List<Solution> parents=this.getTwoBest(prec);
			gen.add(this.mutation(this.crossover(parents.get(0),parents.get(1))));
		}
		return gen;
	}
	
	
	public void solve() throws Exception {
		int nb_generations=2;
		int taille=4;
		
		List<Solution> gen=this.newPopulation(taille);
		
		for(int g=0;g<nb_generations;g++) {
			gen=generation(taille,gen);
		}
		
		this.m_solution=gen.get(gen.indexOf(this.getTwoBest(gen).get(0)));
		this.m_solution.evaluate();
		this.isDone=true;

	}
	

}
