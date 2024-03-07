/*********************************************************
 * Filename: FileRW.java
 * Author: Charles Walker
 * Created: 11/08/23
 * Modified: 12/1/23
 * Added the fileRecord attribute and writeToFile method so the class can record the final results of the population.
 * 
 * Purpose: 
 * The class is responsible for all of the reading from and writing to
 * files throughout the programs process. 
 * 
 * Attributes:
 * 		-fileData: File
 * 		-data: String[][]
 * 		-previousPop: File
 * 		-lastSession: File
 * 		-testCase: File
 * 		 
 * Methods: 
 * 		+readFromFile(): void
 * 		+startFromFile(): void
 * 		+startFromFileTest(): void
 * 		+rowCountTest(): void
 * 		+writeToFileTest(String[][], String, int): void
 * 		+rowCount(): void
 * 		+writeToFile(String[][], String, int): void
 * 		+parseDistances(): double[][]
 * 		+createLocations(): String[]
 * 
 *********************************************************/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileRW {
	File fileData = new File("distances.csv");
	String[][] data = new String[50][50];
	int[][] previousPop;
	File lastSession = new File("Last Session.csv");
	File testCase = new File("TestStart.csv");
	
	public void readFromFile() {
		try {
			data = new String[50][50];
			FileReader fr = new FileReader("distances.csv");
			BufferedReader br = new BufferedReader(fr);
			String row;
			for(int i = 0; i < 50; i++) {
				row = br.readLine();
				int numOfColumns = 50;
				String[] columns = row.split(",", numOfColumns);
				for(int j = 0; j < 50; j++) {
					data[i][j] = columns[j];
					data[i][j] = data[i][j].replace(",","");
				}
			}
			br.close();
			fr.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int[][] startFromFile() {
		try {
			int rows = 0;
			int rowCount=rowCount()-1;
			previousPop = new int[rowCount][49];
			FileReader fr = new FileReader("Last Session.csv");
			BufferedReader br = new BufferedReader(fr);
			String row;
			br.readLine();
			while ((row = br.readLine())!=null) {
				String[] columns = row.split(",", 50);
				for (int i = 0; i < columns.length; i++) {
					columns[i] = columns[i].replace(" ", "");
				}
				for(int j = 0; j < columns.length-1; j++) {
					previousPop[rows][j] = Integer.parseInt(columns[j]);
				}
				rows++;
			}
			br.close();
			fr.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return previousPop;
	}
	
	public int[][] startFromFileTest() {
		try {
			int rows = 0;
			int rowCount=rowCountTest();
			previousPop = new int[rowCount][49];
			FileReader frt = new FileReader("TestStart.csv");
			BufferedReader brt = new BufferedReader(frt);
			String row;
			while ((row = brt.readLine())!=null) {
				String[] columns = row.split(",", 50);
				for (int i = 0; i < columns.length; i++) {
					columns[i] = columns[i].trim();
					columns[i] = columns[i].replace(" ", "");
				}
				for(int j = 0; j < columns.length-1; j++) {
					previousPop[rows][j] = Integer.parseInt(columns[j]);
				}
				rows++;
			}
			brt.close();
			frt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return previousPop;
	}
	
	public int rowCountTest() {
		int count = 0;
		try {
			FileReader fr = new FileReader("TestStart.csv");
			BufferedReader br = new BufferedReader(fr); 
			count = 0;
	            while (br.readLine() != null) {
	                count++;
	            }
	        br.close();
			fr.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void writeToFileTest(String[][] data1, String data2, int popSize) {
		try {
			FileWriter fw = new FileWriter("TestEnd.csv");
			fw.write(data2+"\n");
			for (int i = 0; i < popSize; i++) {
				for(int j = 0; j < 50; j++) {
					fw.write(data1[i][j]+", ");
				}
				fw.write("\n");
			}
			fw. close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int rowCount() {
		int count = 0;
		try {
			FileReader fr = new FileReader("Last Session.csv");
			BufferedReader br = new BufferedReader(fr); 
			count = 0;
	            while (br.readLine() != null) {
	                count++;
	            }
	        br.close();
			fr.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	public void writeToFile(String[][] data1, String data2, int popSize) {
		try {
			FileWriter fw = new FileWriter("Last Session.csv");
			fw.write(data2+"\n");
			for (int i = 0; i < popSize; i++) {
				for(int j = 0; j < 50; j++) {
					fw.write(data1[i][j]+", ");
				}
				fw.write("\n");
			}
			fw. close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double[][] parseDistances() {
		double[][] distances = new double[49][49];
		for(int i = 1; i < 50; i++) {
			for(int j = 1; j < 50; j++) {
				distances[i-1][j-1] = Double.parseDouble(data[i][j]);
			}
		}
		return distances;
	}
	
	public String[] createLocations() {
		String[] locations = new String[49];
		for(int i = 1; i < 50; i++) {
			locations[i-1] = data[i][0];
		}
		return locations;
	}
}
