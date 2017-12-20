package funcionalities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LeitorDeFicheiro {

	private ArrayList<Rule> rulesList = new ArrayList<Rule>();
	private ArrayList<String> log = new ArrayList<String>();
	private ArrayList<String> valores;
	private DefaultTableModel defaultManualTable;
	private DefaultTableModel defaultAutomaticTable;
	private JTable manualTable;
	private JTable automaticTable;

	public void createTables(JTextField searchDirectory) {

		defaultManualTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				//Only the second column
				return column == 1;
			}
		};

		defaultAutomaticTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		manualTable = new JTable(defaultManualTable);
		automaticTable = new JTable(defaultAutomaticTable);

		defaultManualTable.addColumn("Rules");
		defaultManualTable.addColumn("Weight");

		defaultAutomaticTable.addColumn("Rules");
		defaultAutomaticTable.addColumn("Weight");
		
		File f = new File(searchDirectory.getText());
		
		Scanner sc = null;
		PrintWriter writer = null;

		try {
			sc = new Scanner(f);
			if(!(searchDirectory.getText().equals("./rules.cf"))){
				writer = new PrintWriter(f.getName());
			}
		} catch (FileNotFoundException e1) {
			System.out.println("");
		}

		
		try{
		
		while(sc.hasNextLine()){
			String linha = sc.nextLine();
			String[] tokens = linha.split(" ");

			String ruleName = tokens[0];
			Rule r;
			
			if(!(searchDirectory.getText().equals("./rules.cf"))){
				writer.append(linha + " 0.0\n" );
				r = new Rule(ruleName, 0.0);
				
			}
			else{
				double weight = Double.parseDouble(tokens[1]);
				r = new Rule(ruleName, weight);
			}
			
			rulesList.add(r);
			fillTable(r);
		}
		} catch (Exception e) {
			System.out.println("");
		}

		sc.close();
		if(!(searchDirectory.getText().equals("./rules.cf"))){
			writer.close();
		}
	}
	
	public void lerFicheiroLog(String Directory){
		
		log = new ArrayList<String>();
		
		File f = new File(Directory);

		Scanner sc;
		String[] lineParts = null;
				
		try {
			sc = new Scanner(f);
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				lineParts = line.split("	");
				for(int i = 1; i < lineParts.length; i++){
					
					if(i == lineParts.length - 1){
						String[] lineParts2 = lineParts[i].split(" ");
						log.add(lineParts2[0]);
						log.add(lineParts2[1]);
					}
					else{
						log.add(lineParts[i]);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void lerValoresAutomatico(){
		
		File f = new File("./experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
		
		Scanner sc;
		valores = new ArrayList<>();
		String[] parts;
		
		try {
			sc = new Scanner(f);

			System.out.println("");
			String line = sc.nextLine();
			parts = line.split(" ");
			for(int i = 0; i < parts.length; i++){
				valores.add(parts[i]);
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void fillTable(Rule r) {
		defaultManualTable.addRow(new Object[] {r.getName(),r.getWeight()});
		defaultAutomaticTable.addRow(new Object[] {r.getName(),r.getWeight()});
	}

	public ArrayList<Rule> getRulesList(){
		return rulesList;
	}
	
	public ArrayList<String> getLog(){
		return log;
	}
	
	public ArrayList<String> getValores(){
		return valores;
	}

	public DefaultTableModel getDefaultManualTable(){
		return defaultManualTable;
	}
	
	public JTable getManualTable(){
		return manualTable;
	}

	public JTable getAutomaticTable(){
		return automaticTable;
	}
}
