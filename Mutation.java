import java.util.Random;

/*********************************************************
 * Filename: Mutation.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 12/1/23
 * Updated the class to perform as intended and added the setFirstChild method to get the index of the first child from the 
 * Crossover class. 
 * 
 * Purpose: 
 * Rolls a user chosen chance for each offspring to mutate. If selected,
 * the class selects two random genes of the offspring's chromosome to 
 * swap.
 * 
 * Attributes:
 * 		-chance: double
 * 		-firstChild: int
 * 
 * Methods: 
 * 		+selectChild(): void
 * 		+mutateGenes(): void
 * 		+mutateGenesTest(): void
 * 		+setChance(double): void
 * 		+getChance(): int
 * 		+setFirstChild(double): void
 * 
 *********************************************************/
public class Mutation {

	private int chance;
	private int firstChild;
	
	public void selectChild(Individual[] population) {
		int num = 0;
		for (int i = firstChild; i < population.length; i++) {
			Random ran = new Random();
			num = ran.nextInt(100) + 1;
			if (num <= chance) {
				population[i].setChromosome(mutateGenes(population[i].getChromosome()));
			}
		}
	}
	
	public int[] mutateGenes(int[] chrom) {
		int gene1;
		int gene2;
		int temp;
		Random ran1 = new Random();
		Random ran2 = new Random();
		gene1 = ran1.nextInt(48)+1;
		gene2 = ran2.nextInt(48)+1;
		temp = chrom[gene1];
		chrom[gene1] = chrom[gene2];
		chrom[gene2] = temp;
		return chrom;
	}
	public int[] mutateGenesTest(int[] chrom) {
		int gene1;
		int gene2;
		int temp;
		Random ran1 = new Random();
		Random ran2 = new Random();
		gene1 = ran1.nextInt(48)+1;
		gene2 = ran2.nextInt(48)+1;
		System.out.println("Swapping gene "+chrom[gene1]+" in index "+gene1+" with gene "+chrom[gene2]+" in index "+gene2+".");
		temp = chrom[gene1];
		chrom[gene1] = chrom[gene2];
		chrom[gene2] = temp;
		return chrom;
	}
	
	public void setChance(double value) {
		chance = (int) value;
	}
	
	public int getChance() {
		return chance;
	}
	
	public void setFirstChild(double value) {
		firstChild = (int)value;
	}
}
