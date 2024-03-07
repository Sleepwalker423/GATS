/*********************************************************
 * Filename: Crossover.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 12/1/23
 * Fixed replicating children bug by having the createOffspringOddHalf() method reinitialize the children arrays every time its called. 
 * 
 * Purpose: 
 * Creates the offspring of parents not selected by the Elitism class and replaces
 * the parents and two other individuals with the new offspring.
 * 
 * Attributes:
 * 		-firstInd: int
 * 		-lastInd: int
 * 
 * Methods: 
 * 		+mate(int):void
 *		+createOffspringOddHalf(object, object):void
 *		+seltLastInd(int):void
 *		+setFirstInd(int):void
 *		+getFirstInd(): int
 * 
 *********************************************************/
public class Crossover extends Elitism{

	private int firstInd;
	private int lastInd;
	
	@Override 
	public Individual[] mate(Individual[] population) {
		//System.out.println(firstInd+" "+lastInd);
		int count = lastInd-1;
		for(int i = firstInd; i < (lastInd-firstInd)/2; i+=2) {
			createOffspring(population[i].getChromosome(), population[i+1].getChromosome());
			population[i].setChromosome(child1);
			population[i+1].setChromosome(child2);
			population[count].setChromosome(child3);
			population[count-1].setChromosome(child4);
			count -=2;
			if((i-count) == 1) {
				createOffspringOddHalf(population[i].getChromosome(), population[i+1].getChromosome());
				population[i].setChromosome(child1);
				population[i+1].setChromosome(child2);
			}
		}
		return population;
	}
		
	public void createOffspringOddHalf(int[] p1, int[] p2) {
		
		child1 = new int[TOTGENES];
		child2 = new int[TOTGENES];
		child1[0] = 0;
    	child2[0] = 0;
    	for (int i = 1; i < HGENES+1; i++) {
    		child1[i] = p1[i];
    		child2[HGENES+i] = p1[HGENES+i];
    		child1[HGENES+i] = p2[HGENES+i];
    		child2[i] = p2[i];
    	}
    	fixOffspring(child1, child2);
}
	
	public void setLastInd(int num) {
		lastInd = num;
	}
	
	public void setFirstInd(int num) {
		firstInd = num;
	}
	
	public int getFirstInd() {
		return firstInd;
	}
}
