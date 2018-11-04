package tsp.heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tsp.Instance;
import tsp.Solution;

public class Genetic extends AHeuristic {
	
	/**
	 * Vaut true si la méthode solve a été exécutée
	 */
	private boolean isDone;
	
	/**
	 * Nombre de villes de l'instance
	 */
	public final int NB_VILLES=this.getInstance().getNbCities();
	
	/**
	 * Nombre de générations
	 */
	public final int NB_GENERATIONS=1000;
	/**
	 * Nombre d'individus par génération
	 */
	public final int TAILLE_POP=1000;
	/**
	 * Probabilité de mutation
	 */
	public final double P_MUTATION=0.01;
	
	/**
	 * this.m_name="Genetic Algorithm"
	 * this.isDone=false
	 * @param instance
	 * @throws Exception
	 */
	public Genetic(Instance instance) throws Exception {
		super(instance,"Genetic algorithm");
		this.isDone=false;
	}
	
	/**
	 * 
	 * @return super.m_instance
	 */
	public Instance getInstance() {return super.m_instance;}
	/**
	 * 
	 * @return this.isDone
	 */
	public boolean isDone() {return this.isDone;}
	
	/**
	 * 
	 * @return Solution au parcours initialisé aléatoirement
	 * @throws Exception
	 */
	public Solution randSolution() throws Exception {
		Solution sol=new Solution(m_instance);
		List<Integer> rand = new ArrayList<Integer>();
		for(int i=1;i<NB_VILLES;i++) {rand.add(i);}
		Collections.shuffle(rand);
		for(int i=1;i<NB_VILLES;i++) {sol.setCityPosition(i, rand.get(i-1));}
		sol.evaluate();
		return sol;
	}
	
	/**
	 * 
	 * @return ArrayList de this.TAILLE_POP Solution initialisées aléatoirement
	 * @throws Exception
	 */
	public List<Solution> newPopulation() throws Exception{
		List<Solution> pop = new ArrayList<Solution>();
		for(int i=0;i<TAILLE_POP;i++) {
			pop.add(this.randSolution());
		}
		return pop;
	}
	
	/**
	 * 
	 * @param pop population de Solution
	 * @return ArrayList des valeurs objectif de chaque Solution
	 */
	public List<Long> getObjectiveValues(List<Solution> pop){
		List<Long> ov=new ArrayList<Long>();
		for(Solution i:pop) {ov.add(i.getObjectiveValue());}
		return ov;
	}
	
	/**
	 * 
	 * @param s une Solution
	 * @return ArrayList des villes de la Solution dans l'ordre
	 * @throws Exception
	 */
	public List<Integer> getVilles(Solution s) throws Exception{
		List<Integer> res=new ArrayList<Integer>();
		for(int i=0;i<=NB_VILLES;i++) {res.add(s.getCity(i));}
		return res;
	}
	
	/**
	 * 
	 * @param pop ArrayList de Solution
	 * @return ArrayList des DEUX meilleures Solution de pop selon leur objectiveValue
	 */
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
	
	/**
	 * 
	 * @param pop population de Solution
	 * @return objectiveValue de la meilleure Solution de pop 
	 */
	public long getBestOV(List<Solution> pop) {
		return Collections.min(this.getObjectiveValues(pop));
	}
	
	/**
	 * 
	 * @param parent1
	 * @param parent2
	 * @return Nouvelle Solution issue du croisement entre les trajets de parent1 et parent21
	 * @throws Exception
	 */
	public Solution crossover(Solution parent1, Solution parent2) throws Exception {
		
		List<Integer> villesParent1=this.getVilles(parent1);
		List<Integer> villesParent2=this.getVilles(parent2);

		//Calcul des index de début et de fin
		Random r=new Random();
		int start_mut=1+r.nextInt(NB_VILLES-1);
		int end_mut = 1+r.nextInt(NB_VILLES-1);
		if (start_mut>end_mut) {
			int a=end_mut;
			end_mut=start_mut;
			start_mut=a;}
		if (start_mut==end_mut) end_mut++;
		
		//Création et initialisation de la liste de villes de bebe
		int[] villesBebe=new int[NB_VILLES+1];
		for (int i=start_mut;i<end_mut;i++) {
			villesBebe[i]=villesParent1.get(i);
			villesParent2.remove(villesParent1.get(i));
		}
		villesParent2.remove(0);
		villesParent2.remove(villesParent2.size()-1);
		
		//Remplissage des valeurs restantes avec les valeurs de parent2
		int i=1;
		while (!villesParent2.isEmpty()) {
			if (villesBebe[i]==0) {
				villesBebe[i]=villesParent2.get(0);
				villesParent2.remove(0);
			}
			i++;
		}

		//Conversion de la liste de villes en Solution
		Solution bebe=new Solution(this.getInstance());
		for(int e=0;e<this.getInstance().getNbCities();e++) {bebe.setCityPosition(villesBebe[e], e);}
		bebe.evaluate();
		return bebe;
	}
	
	/**
	 * 
	 * @param s
	 * @param pos_i
	 * @param pos_j
	 * @return Nouvelle Solution valant s avec les villes en pos_i et pos_j interverties
	 * @throws Exception
	 */
 	public Solution swap(Solution s, int pos_i, int pos_j) throws Exception{
		Solution sol= s.copy();
		int ville_arrivee=sol.getCity(pos_j);
		sol.setCityPosition(sol.getCity(pos_i),pos_j);
		sol.setCityPosition(ville_arrivee, pos_i);
		sol.evaluate();
		
		return sol;
	}
	
	/**
	 * 
	 * @param s
	 * @return Pour chaque ville de s, il y a une probabilité this.P_MUTATION d'être swap avec une ville au hasard de la Solution
	 * @throws Exception
	 */
	public Solution mutation(Solution s) throws Exception {
		for(int i=1;i<NB_VILLES;i++) {
			if (Math.random()<P_MUTATION) {
				Random r=new Random();
				s=this.swap(s, i,1+r.nextInt(NB_VILLES-1));
			}
		}
		s.evaluate();
		return s;
	}
	
	/**
	 * 
	 * @param prec génération précédente
	 * @return Nouvelle population issue de la première génération
	 * @throws Exception
	 */
	public List<Solution> generation(List<Solution> prec) throws Exception{
		List<Solution> gen=new ArrayList<Solution>();
		List<Solution> parents=this.getTwoBest(prec);
		for(int i=0;i<TAILLE_POP;i++) {
			gen.add(this.mutation(this.crossover(parents.get(0),parents.get(1))));
		}
		return gen;
	}
	

	public void solve() throws Exception {
		
		List<Solution> gen=this.newPopulation();
		
		for(int g=0;g<NB_GENERATIONS;g++) {
			gen=generation(gen);
		}
		
		this.m_solution=gen.get(gen.indexOf(this.getTwoBest(gen).get(0)));
		this.m_solution.evaluate();
		this.isDone=true;

	}
	
}
