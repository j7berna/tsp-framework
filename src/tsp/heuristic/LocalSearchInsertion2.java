package tsp.heuristic;

import java.util.Arrays;

import tsp.Instance;
import tsp.Solution;

public class LocalSearchInsertion2 extends AHeuristic {

	// variables d'instances 
	private boolean isDone;
	
	// constructeurs
	
	public LocalSearchInsertion2(Instance instance) throws Exception {
		super(instance,"Insertion");
		this.isDone=false;
	}
	
	public boolean isDone() {
		return this.isDone;
	}
	
	// fonction
	
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
	
	public static double longueur(int[] sol, long[][] M,Instance inst) throws Exception {
		Solution tempSol = new Solution(inst);
		int l=0;
		int i= sol.length ;
		while (i<inst.getNbCities()) {
			sol =ajouter(i,0,sol);
		}
		for(int j=0; j<sol.length;j++) {
			tempSol.setCityPosition(sol[j],j);
		}
		System.out.println(tempSol.evaluate());
		return tempSol.evaluate() ;
	}
	
	public static int indexMin(double[] longueurs) {
		int res=0;
		for (int i=0;i<longueurs.length;i++) {
			if (longueurs[i]<longueurs[res]) {
				res=i;
			}
		}
		return res;
	}
	

	public void solve() throws Exception {
		long[][] M=this.m_instance.getDistances();
		System.out.println(Arrays.deepToString(M));
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
			s.evaluate();
		}
		
		this.isDone=true;
		this.m_solution=s;
		this.m_solution.evaluate();
	}


}


