package tsp;

import java.io.IOException;

public class Benchmark {
	public static Instance[] buildInstances() throws IOException {
		Instance[] inst = new Instance[12];
		inst[0] = new Instance("instances/brazil58.tsp",1);
		inst[1] = new Instance("instances/d198.tsp",0);
		inst[2] = new Instance("instances/d657.tsp",0);
		inst[3] = new Instance("instances/eil10.tsp",0);
		inst[4] = new Instance("instances/eil101.tsp",0);
		inst[5] = new Instance("instances/eil51.tsp",0);
		inst[6] = new Instance("instances/kroA100.tsp",0);
		inst[7] = new Instance("instances/kroA150.tsp",0);
		inst[8] = new Instance("instances/kroA200.tsp",0);
		inst[9] = new Instance("instances/lin318.tsp",0);
		inst[10] = new Instance("instances/pcb442.tsp",0);
		inst[11] = new Instance("instances/rat575.tsp",0);
		return inst;
	}
	
	public static void main(String[] args) throws IOException {
		Instance[] instances = buildInstances();
		for(int i = 0; i<instances.length; i++) {
			// TODO apply code for instances[i]
		}
	}
}
