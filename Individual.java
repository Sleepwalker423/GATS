import java.util.Arrays;

/*********************************************************
 * Filename: Individual.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 
 * 
 * Purpose: 
 * Acts as the base that all individuals that make up the population are 
 * copied from. Holds an integer array called chromosome that holds the 
 * route locations, or genes, as well as the fitness level of that route.
 * 
 * Attributes:
 * 		-fitness: double
 * 		-chromosome: int[]
 * 		
 * 
 * Methods: 
 * 		+<<constructor>>Individual(int)
 * 		+getFitness(): double
 * 		+setFitness(int): void
 * 		+getChromosome: int[]
 * 		+setChromosome(int[]): void
 * 		+setGene(int,int): void
 * 		+getGene(): int
 * 		+printChromosome(): void
 * 
 *********************************************************/
public class Individual {

	private double fitness;
	private int[] chromosome;
	
	public Individual(int genes) {
		chromosome = new int[genes];
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double value) {
		fitness = value;
	}
	
	public int[] getChromosome() {
		return this.chromosome;
	}
	
	public void setChromosome(int[] genes) {
		this.chromosome = genes;
	}
	
	public void setGene(int value, int index){
		chromosome[index] = value;
	}
	
	public int getGene(int index) {
		return chromosome[index];
	}
	
	public void printChromosome() {
		System.out.print("");
		for(int i = 0; i < chromosome.length; i++) {
			System.out.print(chromosome[i]+",");
		}
	}
}
/*public void genVariation() {
	boolean next = false;
	int nextI = 0;
	genVariation=pop.getPopSize();
	for(int i = 0; i < pop.getPopSize()-1; i=nextI) {
		for(int j = i+1; j < pop.getPopSize()&&!next; j++) {
			if(Arrays.equals(pop.getInd(i).getChromosome(), pop.getInd(j).getChromosome())) {
				genVariation--;
				next = true;
				nextI = j+1;
			}
		}
		next = false;
	}
}*/
