package funcionalities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LeitorDeFicheiro {

	private ArrayList<Rule> rulesList = new ArrayList<Rule>();
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

	public void fillTable(Rule r) {
		defaultManualTable.addRow(new Object[] {r.getName(),r.getWeight()});
		defaultAutomaticTable.addRow(new Object[] {r.getName(),r.getWeight()});
	}

	public ArrayList<Rule> getRulesList(){
		return rulesList;
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
