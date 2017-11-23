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

		String directory = searchDirectory.getText();
		System.out.println(directory);

		File f = new File(directory);

		Scanner sc = null;
		PrintWriter writer = null;


		try {
			sc = new Scanner(f);
			writer = new PrintWriter(f.getName());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		while(sc.hasNextLine()){
			String linha = sc.nextLine();
			String[] tokens = linha.split(" ");

			String ruleName = tokens[0];

			writer.append(linha + " 0.0\n" );
			Rule r = new Rule(ruleName,0.0);
			rulesList.add(r);
			fillTable(r);
		}

		sc.close();
		writer.close();
	}

	public void fillTable(Rule r) {
		defaultManualTable.addRow(new Object[] {r.getName(),r.getWeight()});
		defaultAutomaticTable.addRow(new Object[] {r.getName(),r.getWeight()});
	}


	public JTable getManualTable(){
		return manualTable;
	}

	public JTable getAutomaticTable(){
		return automaticTable;
	}
}
