/*********************************************************
 * Filename: GATS_Main.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 
 * 
 * Purpose: 
 * Initializes the program by creating an instance of the PopulationControl
 * class and calling on its start() method.
 * 
 * Attributes:
 * 		-popControl: PopulationControl()
 * 
 * Methods: 
 * 		+main(String[]): void
 * 
 *********************************************************/
public class GATS_Main {
	
	public static void main(String[] args) {
		PopulationControl popControl = new PopulationControl();
		Test_Case test = new Test_Case();
		int choice=0;
		System.out.println("Would you like to run test cases?");
		choice = popControl.testOrNot();
		if(choice==1) {
			test.start();
		}else {
			popControl.start();
		}
	}
	
}
