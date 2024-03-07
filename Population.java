/*********************************************************
 * Filename: Population.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 11/09/23
 * 	printPop(): Removed the double-nested for loop that checks for all numbers of a given individual
 *  and moved it into a new method called indCheck() that takes the given individual and its number in the population array as
 *  arguments. 
 * 
 * Purpose: 
 * This class is responsible for the control of the class array of the
 * Individual class that makes up the population. It also sorts the population
 * by best fitness score. 
 * 
 * Attributes:
 * 		-popSize: int
 * 		-TOTGENES: final int
 * 		-population: Individual[]
 * 
 * Methods: 
 * 		+fitnessSort(): void
 * 		+createPop(int): void
 * 		+createPopFromFile(int): void
 * 		+printPop(): void
 * 		+printPopTest(): void 
 * 		+indCheck(Individual, int); void
 * 		+getInd(int): Individual
 * 		+getPopSize(): int
 * 		+setPopSize(int): void
 * 		+getPop(): Individual[]
 * 		+setPop(Individual[]):void
 * 
 *********************************************************/
import java.util.Random;
public class Population {
	private int popSize;
	private final int TOTGENES=49;
	private Individual[] population;
	
	//Update this to quicksort
	public void fitnessSort() {
		Individual temp;
		boolean cont = true;
		for (int i = 1; i < popSize&&cont; i++) {
			cont = false;
			for (int j = 0; j < popSize-i; j++) {

				if (population[j].getFitness() > population[j+1].getFitness()) {
					temp = population[j];
					population[j] = population[j+1];
					population[j+1] = temp;
					cont=true;
				}
			}
		}
	}
	
	public void createPop(int size) {
		popSize= size;
		population = new Individual[popSize];
		int loc1;
		int loc2;
		int temp;
		for (int i = 0; i < popSize; i++) {
			Individual ind = new Individual(TOTGENES);
			for (int j = 0; j < TOTGENES; j++) {
				ind.setGene(j,j);
			}
			for (int l = 1; l < TOTGENES; l++) {
				Random ran1 = new Random();
				Random ran2 = new Random();
				loc1 = ran1.nextInt(TOTGENES-1) + 1;
				loc2 = ran2.nextInt(TOTGENES-1) + 1;
				temp = ind.getGene(loc1);
				ind.setGene(ind.getGene(loc2),loc1);
				ind.setGene(temp,loc2);
	    	}
			population[i] = ind;
		}
	}
	
	public void createPopFromfile(int[][] chroms) {
		popSize = chroms.length;
		population = new Individual[popSize];
		for (int i = 0; i < popSize; i++) {
			Individual ind = new Individual(TOTGENES);
			for (int j = 0; j < TOTGENES; j++) {
				ind.setGene(chroms[i][j],j);
			}
			population[i] = ind;
		}
	}
	
	public void printPop(int test) {
		int complete=0;
		System.out.println("");
		for (int i = 0; i < popSize; i++) {
			System.out.print((i+1)+")");
			population[i].printChromosome();
			System.out.println(population[i].getFitness());
			if(test==1) {
				complete = indCheck(population[i], i, complete);
			}
			System.out.println(" ");
    	}
		if (complete==popSize) {
			System.out.println("POPULATION PASS!!!");
		}else {
			System.out.println("POPULATION FAIL!!!");
		}
	}
	
	public void printPopTest(int test) {
		System.out.println("");
		for (int i = 9; i > 6; i--) {
			System.out.print(i+")");
			population[i].printChromosome();
			System.out.println(population[i].getFitness());
    	}
	}
	
	public int indCheck(Individual ind, int i, int complete) {
		int pass = 0;
		boolean check;
		for (int j = 1; j < TOTGENES; j++) {
    		check = false;
    		for (int k = 1; k < TOTGENES&&check==false; k++) {
    			if (ind.getGene(k) == j) {
    				check = true;
    				pass++;
    			}
    		}
    		if (check == false) {
    			System.out.print("-Is missing gene "+ j+"-");
    		}	
		}
		if (pass != TOTGENES-1) {
			System.out.println(" ");
		}
		if (pass == TOTGENES-1) {
			System.out.println("Individual "+(i+1)+" is complete!");
			complete++;
		}
		return complete;
	}
	public Individual getInd(int index) {
		return population[index];
	}
	
	public int getPopSize() {
		return popSize;
	}
	
	public void setPopSize(int size) {
		popSize=size;
	}
	
	public Individual[] getPop() {
		return population;
	}
	
	public void setPop(Individual[] pop) {
		population = pop;
	}
	
	

}
