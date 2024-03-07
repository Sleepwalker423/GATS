/*********************************************************
 * Filename: Test_Case.java
 * Author: Charles Walker
 * Created: 12/08/23
 * 
 * Modified: 
 * Purpose: 
 * Runs the automated test case process for the program. 
 * 
 * Attributes:
 * 		-maxGenTest: int
 * 		-test: double
 * 
 * Methods: * 		
 * 		+Test_Case: void
 *		+Test_Case.start(): void
 *		+Test_Case.testCase1(): void
 *		+Test_Case.testCase2(): void
 *		+Test_Case.testCase3(): void
 *		+Test_Case.testCase4(): void
 *		+Test_Case.testCase5_1(): void
 *		+Test_Case.testCase5_2(): void
 *		+Test_Case.testCase5_3(): void
 *		+Test_Case.testCase6(): void
 *		+Test_Case.testCase7(): void
 *		+Test_Case.printMutTest(int[], int): void
 *		+Test_Case.genLoop(): void
 *		+Test_Case.baseAlgorithmTest(): void
 *		+Test_Case.fitLoop(): void
 *		+Test_Case.recordFinalDataTest(): void
 * 
 *********************************************************/

public class Test_Case extends PopulationControl {

	private int maxGenTest=0;
	private double test=0.0;
	@Override
	public void start() {
		testCase1();
		testCase2();
		testCase3();
		testCase4();
		testCase5_1();
		testCase5_2();
		testCase5_3();
		testCase6();
		testCase7();
		
	}
	
	public void testCase1(){
		
		System.out.println("TEST CASE 1: TEST FOR REQUIREMENT #4 USING A REQUESTED POPULATION OF 10.");
		pop.createPop(10);
		calcPopFitness();
		pop.fitnessSort();
		pop.printPop(1);
		System.out.println("TEST CASE 1 COMPLETE!!!");
	}
	
	public void testCase2() {
		System.out.println("\r\nTEST CASE 2: TEST FOR REQUIREMENNT #2 USING THE LAST 3 INDIVIDUALS IN THE 'TestStart.csv' FILE.");
		pop.createPopFromfile(rw.startFromFileTest());
		calcPopFitness();
		pop.printPopTest(1);
		pop.fitnessSort();
		System.out.println("TEST CASE 2 COMPLETE!!!");
	}
	
	public void testCase3() {
		
		System.out.println("\r\nTEST CASE 3: TEST THE REQUIREMENTS #6, 7, 9, AND 10 USING THE POPULATION FROM THE 'TestStart.csv' FILE WITH AN ELITISM PERCENTAGE OF 20%.");
		pop.printPop(1);
		calcElites(20);
		baseAlgorithmTest();
		setFinalData();
		recordFinalDataTest();
		pop.printPop(1);
		System.out.println("\r\nTHEESE RESULTS ARE SAVED IN FILE 'TestEnd.csv' TO BE COMPARED TO FILE 'Compare.csv'.");
		System.out.println("TEST CASE 3 COMPLETE!!!");
	}
	public void testCase4() {
		System.out.println("\r\nTEST CASE 4.1 - 4.3: TEST FOR REQUIREMENNT #8 USING THE FIRST 3 INDIVIDUALS IN THE ABOVE PRINTED POPULATION.");
		int[] test1 = pop.getInd(0).getChromosome();
		int[] test2 = pop.getInd(1).getChromosome();
		int[] test3 = pop.getInd(2).getChromosome();
		mutation.mutateGenesTest(test1);
		mutation.mutateGenesTest(test2);
		mutation.mutateGenesTest(test3);
		System.out.println("Ending chromosomes.");
		printMutTest(test1,1);
		System.out.println("");
		printMutTest(test2,2);
		System.out.println("");
		printMutTest(test3,3);
		System.out.println("");
		System.out.println("TEST CASE 4 COMPLETE!!!");
	}
	
	public void testCase5_1() {
		System.out.println("\r\nTEST CASE 5.1: TEST FOR REQUIREMENNT #11 USING THE 'fitLoop()' METHOD TO SHOW THE USER CAN"+
				" CHOOSE FITNESS THRESHOLD FOR TERMINATION.");
		System.out.println("PROGRAM IS SETTING THE THRESHOLD TO 74000 AND THE 'topFitness' ATTRIBUTE TO THAT OF INDIVIDUAL 1 ABOVE TO ENSURE THE ALGORITHM TERMINATES AS INTENDED.");
		topFitness = pop.getInd(0).getFitness();
		fitLoop();
		System.out.println("TEST CASE 5.1 COMPLETE!!!");
	}
	
	public void testCase5_2() {
		System.out.println("\r\nTEST CASE 5.2: TEST FOR REQUIREMENT #11 USING THE 'genLoop()' METHODTO SHOW THE USER CAN"+
				" CHOOSE MAXIMUM GENERATION FOR TERMINATION.");
		System.out.println("PROGRAM IS SETTING THE 'maxGen' and 'lastGen' EQUAL TO EACHOTHER TO ENSURE THE ALGORITHM TERMINATES AS INTENDED.");
		lastGen=1;
		genLoop();
		System.out.println("TEST CASE 5.2 COMPLETE!!!");
	}
	
	public void testCase5_3() {
		System.out.println("\r\nTEST CASE 5.3: TEST FOR REQUIREMENNT #11 AND USING THE 'manualLoop()' METHOD TO SHOW THE USER CAN "+
				"CHOOSE MANUAL TERMINATION.\r\n THIS PROCESS ALSO TESTS FOR THE ABILITY TO SHOW THE ALGORITHMS PROCESS AND WILL ERASE THE CURRENT TEST RESULTS."+
				"\r\nPRESS ENTER WHEN YOU ARE READY TO CONTINUE AND AGIAN TO STOP. AFTER PRESSING ENTER THE SECOND TIME, FOLLOW THE PROMPT TO CONTINUE THE TEST.");
		input.nextLine();
		manualLoop();
		System.out.println("TEST CASE 5.3 COMPLETE!!!");
	}
	
	public void testCase6() {
		System.out.println("\r\nTEST CASE 6: TEST FOR REQUIREMENNT #13 USING THE 'Last Session.csv' FILE TO CREATE THE INITIAL POPULATION.");
		pop.createPop(10);
		pop.fitnessSort();
		calcPopFitness();
		setFinalData();
	    recordFinalData();
		pop.createPopFromfile(rw.startFromFile());
		System.out.println("POPULATION BELOW CREATED");
		pop.fitnessSort();
		pop.printPop(1);
		topFitness = pop.getInd(0).getFitness();
		baseAlgorithmTest();
		setFinalData();
	    recordFinalData();
	    pop.printPop(1);
	    System.out.println("THE ABOVE POPULATION SHOULD MATCH THE RESULTS IN 'Last Session.csv'.");
	    System.out.println("TEST CASE 6 COMPLETE!!!");
	}
	
	public void testCase7() {
		System.out.println("TEST CASE 7: TEST FOR REQUIREMENT #14 USING THE 'exceptionHandleInt()' AND 'exceptionHandleDouble()' METHODS USING USER INPUT.");
		System.out.println("ENTER 'asdf', '1.1', AND THEN '1' AFTER THE NEXT PROMPT.");
		maxGenTest = exceptionHandleInt("something", "integer exception handle test");
		System.out.println("ENTER 'asdf' AND THEN '1' AFTER THE NEXT PROMPT.");
		test = exceptionHandleDouble("something", "double exception handle test");
		System.out.println("TEST CASE 7 COMPLETE!!!");
		
		
	}
	
	public void printMutTest(int[] chrom, int num) {
		System.out.print(num+") ");
		for(int i = 0; i < chrom.length; i++) {
			System.out.print(chrom[i]+",");
		}
	}
	
	@Override
	public void genLoop() {
		int maxGen = 0;
		maxGen = 1;
		int check = 2;
		while(check==2) {
			//thread1 = new Thread(this);
			//thread1.start();
			go = true;
			while(go&&maxGen!=lastGen) {
				baseAlgorithmTest();
			}
			if(go==false) {
				check = requestStop();
			}
			if(maxGen==lastGen) {
				check = 1;
				//scan.close();
				//thread1.interrupt();
			}
		}
	}
	
	//just has the printProgress() removed.
	public void baseAlgorithmTest() {
		pop.setPop(elite.mate(pop.getPop()));
		cross.setFirstInd((int) (elite.getTotSelected()+1));
		cross.setLastInd((pop.getPop().length-((int)elite.getTotSelected()*2)-1));
		pop.setPop(cross.mate(pop.getPop()));
		calcPopFitness();
		pop.fitnessSort();
		topFitness = pop.getInd(0).getFitness();
		lastGen++;
		genVariation();
	}
	
	@Override
	public void fitLoop() {
		double threshold = 0;
		threshold = 74000;
		int check = 2;
		while(check==2) {
			//thread1 = new Thread(this);
			//thread1.start();
			go = true;
			while(go&&threshold<=topFitness) {
				baseAlgorithm();
			}
			if (go==false) {
				check = requestStop();
			}
			if(threshold>=topFitness) {
				check=1;
				//scan.close();
				//thread1.interrupt();
			}
		}
	}

	public void recordFinalDataTest() {
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
		rw.writeToFileTest(data1,data2, pop.getPopSize());
	}
}

	
