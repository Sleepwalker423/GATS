/*********************************************************
 * Filename: PopulationControl.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 11/09/23
 * break up my request methods into a single method that checks inputs and passes a string prompt to it. Look at lab5? in the student database class
 * 
 * Modified: 12/1/2023
 * Added the ability to choice how the program will end and created the base algorithm loop for the program. Added the ability to save the information once the 
 * program completes. Added multithreading by having the class implement the Running class to allow the user to stop the algorithm 
 * at any time by pressing enter. 
 * Purpose: 
 * Controls the workings of all classes that allows GATS to operate. This
 * class is responsible for creation of and manipulation of the population.
 * This class creates instances of all but 2 other classes; the main and 
 * the individual class.This class also handles taking information from
 * the user and feeds it to the other classes as needed. 
 * The entire cycle of the genetic algorithm starts and ends with this class.
 * 
 * Attributes:
 * 		#elite:Elitism();
 * 		#cross:Crossover();
 * 		#mutation:Mutation();
 * 		#pop:Population();
 * 		#rw:FileRW();
 * 		#fc:FitnessCalculator();
 * 		#locations: String[]
 * 		#lastGen: int
 * 		#go: boolean
 * 		#topFitness: int
 * 		#avgFitness: double
 * 		#medianFitness: double
 * 		#input: Scanner
 * 		#thread1: Thread
 * 
 * Methods: 
 * 		+<<constructor>>PopulationControl
 * 		+start(): void
 * 		+startFromFile(): void
 * 		+exceptionHandleInt(String, String): int
 *		+exceptionHandleDouble(String, String): double
 * 		+requestPopSize(): void
 *		+requestPercElites(): void
 *		+calcElites(double): void
 *		+requestMutationPerc(): void
 *		+calcPopFitness(): void
 *		+terminateChoice(): void
 *		+switchCase(): void
 *		+baseAlgorithm(): void
 *		+manualLoop(): void
 *		+genLoop(): void
 *		+fitLoop(): void
 *		+requestFitnessThreshold(): double
 *		+requestMaxGen(): int
 *		+requestStop(): int
 *		+setFinalData(): void
 *		+recordFinalData(): void
 *		+run(): void
 *		+createLocations(): void
 *		+printLocations(): void
 *		+genVariation(): void
 *		+printProgress(): void
 *		+testOrNot(): int
 *********************************************************/

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class PopulationControl implements Runnable {
	protected Elitism elite = new Elitism();
	protected Crossover cross = new Crossover();
	protected Mutation mutation = new Mutation();
	protected Population pop = new Population();
	protected FileRW rw = new FileRW();
	protected FitnessCalculator fc = new FitnessCalculator();
	
	protected String[] locations;
	protected int lastGen =1;
	protected volatile boolean go = true;
	protected volatile Scanner scan = new Scanner(System.in);
	protected double topFitness;
	protected double avgFitness;
	protected double medianFitness;
	protected int genVariation;
	protected int choice;
	protected int fromFile=0;
	protected Scanner input = new Scanner(System.in);
	protected Thread thread1;
	
	public PopulationControl() {
		rw.readFromFile();
		createLocations();
		fc.createMatrix(rw.parseDistances());
	}

	public void start() {
		startFromFile();
		if (fromFile==2) {
			requestPopSize();
		}else {
			pop.createPopFromfile(rw.startFromFile());
		}
		requestPercElites();
		requestMutationPerc();
		calcPopFitness();
		pop.fitnessSort();
		//pop.printPop(1);
		topFitness = pop.getInd(0).getFitness();
		terminateChoice();
		switchCase();
		setFinalData();
	    recordFinalData();
		//printLocations();
		//fc.printMatrix();
	}
	
	public void startFromFile() {
		do {
			System.out.println("Would you like to continue from a previous file?");
			fromFile = exceptionHandleInt("1 for yes or 2 for no", "answer.");
			if ( fromFile < 1 || fromFile > 2) {
				System.out.println(fromFile+" is not 1 or 2.");
			}
		}while (fromFile < 1 || fromFile > 2);
	}
	
	public int exceptionHandleInt(String condition, String purpose) {
		boolean cont = true;
		int value = 0;
		do {
			try {
				input = new Scanner(System.in);
				System.out.println("Please enter "+condition+" for the "+purpose);
				value = input.nextInt();
				cont = false;
			}catch(Exception e){
				cont = true;
				System.out.println("!!!Exception occurred for given "+purpose+"!!!");
			}	
		}while (cont);
		return value;
	}
	
	public Double exceptionHandleDouble(String condition, String purpose) {
		boolean cont = true;
		double value = 0;
		do {
			try {
				input = new Scanner(System.in);
				System.out.println("Please enter "+condition+" for the "+purpose);
				value = input.nextDouble();
				cont = false;
			}catch(Exception e){
				cont = true;
				System.out.println("!!!Exception occurred for given "+purpose+"!!!");
			}	
		}while (cont);
		return value;
	}
	
	public void requestPopSize() {
		int size;
		do {
			size = exceptionHandleInt("an integer greater than 9 and a multiple of 10", "population size.");
			if ( size < 10 || size%10 != 0) {
				System.out.println(size+" is not an integer greater than 9 and a multiple of 10.");
			}
		}while ( size < 10 || size%10 != 0);
		pop.createPop(size);
	}
	
	public void requestPercElites() {
		double perc;
		do {
			perc = exceptionHandleDouble("an integer from 1 to 30", "percentage of elites.");
			if (perc < 1 || perc > 30) {
				System.out.println(perc+" is not an integer from 1 to 30.");
			}	
		}while (perc < 1 || perc > 30);	
		calcElites(perc);
	}
	
	public void calcElites(double value){
		value = pop.getPopSize() * (value * .01);
		value = Math.ceil(value);
		if (value % 2 != 0) {
			value = value + 1;
		}
		elite.setTotSelected(value);
		mutation.setFirstChild(value + 1);
	}
	
	public void requestMutationPerc() {
		int perc;
		do {
			perc = exceptionHandleInt("an integer from 1 to 100", "chance of mutation.");
			if (perc < 1 || perc > 100) {
				System.out.println(perc+" is not a positive integer from 1 to 100.");	
			}
		}while (perc < 1 || perc > 100);	
		mutation.setChance((double)perc);
	}
	
	public void calcPopFitness() {
		
		for (int i = 0; i < pop.getPopSize(); i++) {
			pop.getInd(i).setFitness(fc.calcFitness(pop.getInd(i).getChromosome()));
		}
	}
	
	public void terminateChoice() {
		do {
			input = new Scanner(System.in);
			System.out.println("1: Manual Stoppage\r\n"+
								"2: Maximum number of generations\r\n"+
								"3: Desired Fitness Level Threshold\r\n");
			choice = exceptionHandleInt("1, 2, or 3", "termination method.");
			if (choice < 1 || choice > 3) {
				System.out.println(choice+" is not a valid choice.");	
			}	
		}while (choice < 1 || choice > 3);	
	}
	
	public void switchCase() {
		
		switch (choice) {
	    case 1:
	        manualLoop();
	        break;
	    case 2:
	        genLoop();
	        break; 
	    case 3:
	    	fitLoop();
	    	break;
	    default:
	        System.out.println("How did you get here?!?\r\n Keep calm and find someone who knows what is going on.");
		}
	}
	
	public void baseAlgorithm() {
		pop.setPop(elite.mate(pop.getPop()));
		cross.setFirstInd((int) (elite.getTotSelected()+1));
		cross.setLastInd((pop.getPop().length-((int)elite.getTotSelected()*2)-1));
		pop.setPop(cross.mate(pop.getPop()));
		mutation.setFirstChild(cross.getFirstInd());
		calcPopFitness();
		pop.fitnessSort();
		topFitness = pop.getInd(0).getFitness();
		lastGen++;
		genVariation();
		printProgress();
	}
	
	public void manualLoop() {
		int check = 2;
		while(check==2) {
			thread1 = new Thread(this);
			thread1.start();
			go = true;
			while(go) {
				baseAlgorithm();
			}
			check = requestStop();
		}
	}
	
	public void genLoop() {
		int maxGen = 0;
		maxGen = requestMaxGen();
		int check = 2;
		while(check==2) {
			thread1 = new Thread(this);
			thread1.start();
			go = true;
			while(go&&maxGen!=lastGen) {
				baseAlgorithm();
			}
			if(go==false) {
				check = requestStop();
			}
			if(maxGen==lastGen) {
				check = 1;
				thread1.interrupt();
				System.out.println("Last generation complete and data saved. Press enter to end program.");

			}
		}
	}
	
	public void fitLoop() {
		double threshold = 0;
		threshold = requestFitnessThreshold();
		int check = 2;
		while(check==2) {
			thread1 = new Thread(this);
			thread1.start();
			go = true;
			while(go&&threshold<=topFitness) {
				baseAlgorithm();
			}
			if (go==false) {
				check = requestStop();
			}
			if(threshold>=topFitness) {
				check=1;
				thread1.interrupt();
				System.out.println("Threshold met and data saved. Press enter to end program.");
			}
		}
	}
	
	public double requestFitnessThreshold() {
		double value = 0;
		do {
			value = exceptionHandleDouble("an integer from 0 to 80000", "desired fitness threshold.");
				if (value <= 0 || value >= 80000) {	
					System.out.println(value+" is not a positive integer from 0 to 80,000.");
				}	
		}while (value <= 0 || value >= 80000);
		return value;
	}
	
	public int requestMaxGen() {
		int value = 0;
		do {
			value = exceptionHandleInt("an integer greater than 1", "desired number of generations.");
			if (value < 1) {	
				System.out.println(value+" is not greater than 1");
			}
		}while (value < 1);
		return value;
	}
	
	public int requestStop() {
		int value = 0;
		do {
			System.out.println("Would you like to stop?");
			value = exceptionHandleInt("1 for yes or 2 for no", "answer.");
				if (value > 2 || value < 1) {	
					System.out.println(value+" is not 1 or 2.");
				}
	
		}while (value > 2 || value < 1);
		return value;
	}
	
	public void setFinalData() {
		medianFitness = fc.calcMedian(pop.getPop());
		avgFitness = fc.calcAvg(pop.getPop());
	}
	
	public void recordFinalData() {
		String[][] data1 = new String[pop.getPopSize()][50];
		String data2 = String.join(", ", "Population Size: "+String.valueOf(pop.getPopSize()),"Last Gen: "+
		String.valueOf(lastGen), "Genetic Variation: "+String.valueOf(genVariation), "Best Fitness: "+String.valueOf(topFitness), "Median: "+String.valueOf(medianFitness), "Average: "+
		String.valueOf(avgFitness));
		
		for (int i = 0; i < pop.getPopSize(); i++) {
			for(int j = 0; j < 49; j++) {
				data1[i][j]=String.valueOf(pop.getInd(i).getGene(j));
			}
			data1[i][49]=String.valueOf(pop.getInd(i).getFitness());
		}
		rw.writeToFile(data1,data2, pop.getPopSize());
	}
	
	@Override
	public void run() {
		scan = new Scanner(System.in);
		scan.nextLine();
		go = false;
	}

	public void createLocations() {
		locations = new String[49];
		locations = rw.createLocations();
	}
		
	public void printLocations() {
		for(int i = 0; i < 49; i++) {
			System.out.println(i+")"+locations[i]);
		}
	}
	
	public void genVariation() {
		int count = 0;
		genVariation=pop.getPopSize();
		for(int i = 0; i < pop.getPopSize()-1; i+=count+1) {
			count = 0;
			for(int j = i+1; j < pop.getPopSize(); j++) {
				if(Arrays.equals(pop.getInd(i).getChromosome(), pop.getInd(j).getChromosome())) {
					genVariation--;
					count++;
				}
			}
		}
	}
	
	//Finish in next sprint.
	public void printProgress() {
		System.out.print("\033[H\033[2J");
		System.out.println("Current Generation: "+lastGen+
				"\nTop Fitness: "+topFitness+
				"\r\nPRESS ENTER AT ANY TIME TO STOP.");
	}
	
	public int testOrNot() {
		int value = 0;
		do {
			value = exceptionHandleInt("1 for yes or 2 for no", "answer.");
			if (value < 1 || value > 2) {	
				System.out.println(value+" is not 1 or 2");
			}
		}while (value < 1 || value > 2);
		return value;
	}
}
