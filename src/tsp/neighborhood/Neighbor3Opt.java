package tsp.neighborhood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tsp.Instance;
import tsp.Solution;

public class Neighbor3Opt extends ANeighborhood{
	
	public final int B_=0;
	public final int B=1;
	public final int C_=2;
	public final int C=3;

	public Neighbor3Opt(Instance instance, String name) throws Exception {
		super(instance, name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param s une Solution
	 * @return ArrayList des villes de la Solution dans l'ordre
	 * @throws Exception
	 */
	public List<Integer> getVilles(Solution s) throws Exception{
		List<Integer> res=new ArrayList<Integer>();
		for(int i=0;i<=s.getInstance().getNbCities();i++) {res.add(s.getCity(i));}
		return res;
	}
	
	public Solution permutation(Solution init,int i1,int i2, int i3, String ordre) throws Exception {
		List<Integer> final=new ArrayList<Integer>();
		List<Integer> portion2=new ArrayList<Integer>();
		List<Integer> portion3=new ArrayList<Integer>();
		
		for(int i=i3;i<init.getInstance().getNbCities();i++) {portion1.add(init.getCity(i));}
		for(int i=0;i<i1;i++) {portion1.add(init.getCity(i));}
		for(int i=i1;i<i2;i++) {portion2.add(init.getCity(i));}
		for(int i=i2;i<i3;i++) {portion3.add(init.getCity(i));}

		
		
	}
	
	

	@Override
	public List<Solution> getNeighborhood(Solution sol) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
