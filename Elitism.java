/*********************************************************
 * Filename: Elitism.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 12/1/23
 * Fixed the bug causing the children to unintentually replicate in the population by having the createOffspring method reinitialize the 
 * children arrays every time its called. 
 * 
 * Purpose: 
 * Selects the top percentage of the population to mate and survive into
 * the next generation. Creates 4 offspring that replace 4 of the lowest 
 * scoring population.
 * 
 * Attributes:
 * 		-totSelected: double
 * 		-newBottom: int
 * 		#TOTGENES: final int
 * 		#HGENES: final int
 * 		#child1: int[]
 *	 	#child2: int[]
 * 		#child3: int[]
 * 		#child4: int[]
 * 
 * Methods: 
 * 		+mate(Class[]):Class[]
 * 		+createOffspring(int[], int[]): void
 * 		+fixOffspring(int[],int[]): void
 * 		+getTotSelected(): double
 * 		+setTotSelected(double): void
 * 		+getNewBottom(): int
 * 
 *********************************************************/
public class Elitism {

	private double totSelected;
	private int newBottom;
	protected final int TOTGENES = 49;
	protected final int HGENES = (TOTGENES-1)/2;
	protected int[] child1;
	protected int[] child2;
	protected int[] child3;
	protected int[] child4;
	
	public Individual[] mate(Individual[] population) {
		int count = population.length-1;
		for(int i = 0; i < totSelected; i+=2) {
			createOffspring(population[i].getChromosome(), population[i+1].getChromosome());
			population[count].setChromosome(child1);
			population[count-1].setChromosome(child2);
			population[count-2].setChromosome(child3);
			population[count-3].setChromosome(child4);
			count -=4;
			if(count == (population.length-(totSelected*2)-1)) {
				newBottom = count;
				//System.out.println(count);
			}
		}
		return population;
	}
	
	public void createOffspring(int[] p1, int[] p2) {
		child1 = new int[TOTGENES];
		child2 = new int[TOTGENES];
		child3 = new int[TOTGENES];
		child4 = new int[TOTGENES];
    	child1[0] = 0;
    	child2[0] = 0;
    	child3[0] = 0;
    	child4[0] = 0;
    	for (int i = 1; i < HGENES+1; i++) {
    		child1[i] = p1[i];
    		child2[HGENES+i] = p1[HGENES+i];
    		child1[HGENES+i] = p2[HGENES+i];
    		child2[i] = p2[i];
    		child3[i] = p1[HGENES+i];
    		child4[HGENES+i] = p1[i];
    		child3[HGENES+i] = p2[i];
    		child4[i] = p2[HGENES+i];
    	}
    	fixOffspring(child1, child2);
    	fixOffspring(child3, child4);
	}
	
public void fixOffspring(int[] kid1, int[] kid2) {
	int temp;
	boolean stop;
	for (int i = HGENES+1; i < TOTGENES; i++) {
		for (int j = 1; j < HGENES+1; j++) {
			if (kid1[i]==kid1[j]) {
				stop = false;
				for (int k = HGENES+1; k < TOTGENES && stop == false; k++) {
					for (int l = 1; l < HGENES+1 && stop == false; l++) {
						if (kid2[k] == kid2[l]) {
							temp = kid2[k];
							kid2[k] = kid1[i];
							kid1[i]=temp;
							stop = true;
						}
					}
				}
			}
		}
	}
}

	public double getTotSelected() {
		return totSelected;
	}
	
	public void setTotSelected(double value) {
		totSelected = value;
	}
	
	public int getNewBottom() {
		return newBottom;
	}
}

