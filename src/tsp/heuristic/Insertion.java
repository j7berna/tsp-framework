package tsp.heuristic;

import tsp.Instance;
import tsp.Solution;

public class Insertion extends AHeuristic {

	// variables d'instances 
/**
 * Vaut true si la méthode solve a été exécutée
 */
	private boolean isDone;
	
	// constructeurs
/**
 *  this.m_name="Insertion"
 * this.isDone=false
 * @param instance
 * @throws Exception
 */
	public Insertion(Instance instance) throws Exception {
		super(instance,"Insertion");
		this.isDone=false;
	}
/**
 * 
 * @return isDone
 */
	public boolean isDone() {
		return this.isDone;
	}
	
	// fonction
/**
 * Ajoute la Ville element à la liste l à la position index 
 * @param index
 * @param element
 * @param l
 * @return la liste modifiée 
 */
	public static int[] ajouter(int index,int element, int[] l) {
		int[] lbis=new int[l.length+1];
		for (int i=0;i<index;i++) {
			lbis[i]=l[i];
		}
		lbis[index]=element;
		for (int i=index+1;i<l.length;i++) {
			lbis[i]=l[i-1];
		}
		return lbis;
	}
/**
 * renvoie la longueur de la liste temporaire
 * @param sol
 * @param M
 * @param inst
 * @return la longueur de la liste l
 * @throws Exception
 */
	public static double longueur(int[] sol, long[][] M,Instance inst) throws Exception {
		Solution tempSol = new Solution(inst);
		while (sol.length<inst.getNbCities()) {
			sol =ajouter(sol.length,0,sol);
		}
		for(int j=0; j<sol.length;j++) {
			tempSol.setCityPosition(sol[j],j);
		}
		tempSol.setCityPosition(0, inst.getNbCities());
		return tempSol.evaluate() ;
	}
/**
 * 	
 * @param longueurs
 * @return l'index auquel la ville en cours d'insertion doit être ajoutée
 */
	public static int indexMin(double[] longueurs) {
		int res=0;
		for (int i=0;i<longueurs.length;i++) {
			if (longueurs[i]<longueurs[res]) {
				res=i;
			}
		}
		return res;
	}
	
/**
 * renvoie une solution 
 * @throws Exception 
 */
	public void solve() throws Exception {
		long[][] M=this.m_instance.getDistances();
		int[] sol= {0,0};
		Solution s=new Solution(this.m_instance);
		for (int i=1;i<M.length;i++) {
			int[][] sols= new int[i][sol.length+1];
			double[] longueurs= new double[i];
			for (int j=1;j<=i;j++) {
				sols[j-1]=ajouter(j,i,sol);
				longueurs[j-1]=longueur(sols[j-1], M,this.m_instance);
			}
			int imin=indexMin(longueurs);
			sol=sols[imin];
		}
		
		for(int i=0;i<sol.length-1;i++) {
			s.setCityPosition(i,sol[i]);
		}
		
		this.isDone=true;
		this.m_solution=s;
		this.m_solution.evaluate();
	}


}


