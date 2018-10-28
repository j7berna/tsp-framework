package Tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test_genetic {
	
	
	public static List<Integer> crossover(List<Integer> villes_parent1, List<Integer> villes_parent2) {
		//ligne différente
		int nbVilles=villes_parent1.size()-1;
		
		//lignes différentes
		List<Integer> villesParent1=new ArrayList<Integer>(villes_parent1);
		List<Integer> villesParent2=new ArrayList<Integer>(villes_parent2);

		//Calcul des index de début et de fin, FONCTIONNEL
		int start_mut=1+(int)(Math.random()*nbVilles);
		int end_mut = 1+(int)(Math.random()*nbVilles);
		//si le début est après la fin, on les échange
		if (start_mut>end_mut) {
			int a=end_mut;
			end_mut=start_mut;
			start_mut=a;}
		//si le début est égal à la fin (càd copie du parent 2), on augmente end_mut
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
	
	 public static void main(String[] args) {
		
		List<Integer> parent1=new ArrayList<Integer>();
		List<Integer> parent2=new ArrayList<Integer>();
		parent1.add(0);
		parent2.add(0);
		for(int i=1;i<10;i++) {
			parent1.add(i);
			parent2.add(10-i);
		}
		parent1.add(0);
		parent2.add(0);
		
		for(int i=0;i<10000;i++) {System.out.println(crossover(parent1,parent2).toString());}
		
	
		
	}

}
