package tsp.heuristic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tsp.Instance;

public class Insertion extends AHeuristic {
	public Insertion(Instance instance) throws Exception {
		super(instance,"Insertion");
	}
	
	public static int[] ajouter(int index,int element, int[] l) {
		int a=1;
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
		Instance inst = new Instance("/tsp-framework/tsp-framework-master/instances/brazil58.tsp", 1);
		long[][] M=inst.getDistances();
		int[] sol= {0,0};
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
		System.out.println(Arrays.toString(sol));
	}
	
	public static void main(String[] args) {
		
	}
}
