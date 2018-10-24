package tsp.heuristic;


import tsp.Instance;
import tsp.Solution;

public class LocalSearchInsertion extends AHeuristic {
	private boolean isDone;
	
	public LocalSearchInsertion(Instance instance) throws Exception {
		super(instance,"Insertion");
		this.isDone=false;
	}
	
	public boolean isDone() {
		return this.isDone;
	}
	
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
	
	public static int longueur(int[] sol, long[][] M) {
		int l=0;
		for (int i=0;i<sol.length-1;i++) {
			l+=M[sol[i]][sol[i+1]];
		}
		return l;
	}
	
	public static int indexMin(int[] longueurs) {
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
		int[] sol= {0,0};
		Solution s=new Solution(this.m_instance);
		for (int i=1;i<M.length;i++) {
			int[][] sols= new int[i][sol.length+1];
			int[] longueurs= new int[i];
			for (int j=1;j<=i;j++) {
				sols[j-1]=ajouter(j,i,sol);
				longueurs[j-1]=longueur(sols[j-1], M);
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
