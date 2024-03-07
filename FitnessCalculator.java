/*********************************************************
 * Filename: FitnessCalculator.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 12/1/23
 * Updated the calcAvg and calcMedian methods to update the median and average fitness of the population.
 * 
 * Purpose: 
 * This class calculates the fitness of the population and the median
 * and average of the population's fitness. This class holds the 
 * matrix containing all of the distances between each location.
 * 
 * Attributes:
 * 		-distances: double[][]
 * 
 * Methods: 
 * 		+calcFitness(int): double
 * 		+calcAvg(Individual[]): void
 * 		+calcMedian(Individual[]): void
 * 		+createMatrix(double[][]): void
 * 		+printMatrix(): void
 * 
 *********************************************************/
public class FitnessCalculator {
	double[][] distances = new double[49][49];
	
	public double calcFitness(int[] chromosome) {
		double fitness = 0;
		//int count = 0;
		for (int i = 0; i < 49; i++) {
			//count++;
			if(i < 48) {
				fitness += distances[chromosome[i]][chromosome[i+1]];
				//System.out.println(count+") row:"+chromosome[i]+" col:"+chromosome[i+1]+" fitness:"+fitness);
			}else {
				fitness += distances[chromosome[i]][0];
				//System.out.println("row:"+chromosome[i]+" col:"+chromosome[0]+" fitness:"+fitness);
			}
		}
		return fitness;
	}
	
	public double calcAvg(Individual[] population) {
		int average=0;
		for (int i = 0; i < population.length; i++) {
			average += population[i].getFitness();
		}
		average = average / population.length;
		return average;
	}
	
	public double calcMedian(Individual[] population) {
		double median;
		median = (population[population.length/2 -1].getFitness() + population[population.length/2].getFitness())/2;
		return median;
	}
	
	public void createMatrix(double[][] matrix) {
		distances = matrix;
	}
	
	public void printMatrix() {
		for(int i = 0; i < 49; i++) {
			System.out.print(i+")");
			for(int j = 0; j < 49; j++) {
				System.out.print(j+":"+distances[i][j]+",");
			}
			System.out.println("");
		}
	}
	
	

}
