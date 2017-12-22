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
	private double fp = 0;
	private double fn = 0;

	/**
	 * Cria tabelas para colocar na frame
	 *
	 * @param Descrição directoria ficheiro rules.cf
	 * @return ....
	 */
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
	
		}

		sc.close();
		if(!(searchDirectory.getText().equals("./rules.cf"))){
			writer.close();
		}
	}
	
	/**
	 * Cria arrayList com as regras dos ficheiros spam.log e ham.log
	 *
	 * @param Descrição directoria ficheiros
	 * @return ....
	 */
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
		
		//System.out.println(log.size());
		
	}
	
	/**
	 * Cria arrayList com os pesos criados pelo jMetal
	 *
	 * @return ....
	 */
	public void lerValoresAutomatico(){
		
		File f = new File("./experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");
		double menor = 0;
		int linha = 1;
		int contador = 1;
		String[] split;
		Scanner sc;
		
		try {
			sc = new Scanner(f);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				split = line.split(" ");
				if (contador == 1){
					menor = Double.parseDouble(split[1]);
					fp = Double.parseDouble(split[0]);
					fn = Double.parseDouble(split[1]);
					linha = contador;
				}
				else {
					if(menor > Double.parseDouble(split[1])){
						
						menor = Double.parseDouble(split[1]);
						fp = Double.parseDouble(split[0]);
						fn = Double.parseDouble(split[1]);
						linha = contador;
					}
				}
					
				contador++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		File f2 = new File("./experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
		
		//Scanner sc;
		valores = new ArrayList<>();
		String[] parts = null;
		String line = null;
		
		try {
			sc = new Scanner(f2);

			while(linha != 0){
				line = sc.nextLine();
				linha--;
			}

			parts = line.split(" ");
			for(int i = 0; i < parts.length; i++){
				valores.add(parts[i]);
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	/**
	 * Preenche a tabela com a regra e o seu peso
	 *
	 * @param Descrição regra
	 * @return ....
	 */
	public void fillTable(Rule r) {
		defaultManualTable.addRow(new Object[] {r.getName(),r.getWeight()});
		defaultAutomaticTable.addRow(new Object[] {r.getName(),r.getWeight()});
	}
	
	/**
	 * Cria arrayList com as regras e pesos do ficheiro rules.cf
	 *
	 * @return ....
	 */
	public void fillArray(){
		
		rulesList.clear();
		
		File f = new File("./rules.cf");
		
		Scanner sc;
		
		try {
			sc = new Scanner(f);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] split = line.split(" ");
				Rule r = new Rule(split[0], Double.parseDouble(split[1]));
				rulesList.add(r);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retorna arrayList com as regras e pesos
	 *
	 * @return arrayList
	 */
	public ArrayList<Rule> getRulesList(){
		return rulesList;
	}
	
	/**
	 * Retorna arrayList com as regras do ficheiro spam.log ou ham.log
	 *
	 * @return arrayList
	 */
	public ArrayList<String> getLog(){
		return log;
	}
	
	/**
	 * Retorna arrayList com os pesos gerados pelo jMetal
	 *
	 * @return arrayList
	 */
	public ArrayList<String> getValores(){
		return valores;
	}

	/**
	 * Retorna o modelo para a tabela manual
	 *
	 * @return DefaultTableModel
	 */
	public DefaultTableModel getDefaultManualTable(){
		return defaultManualTable;
	}
	
	/**
	 * Retorna tabela manual
	 *
	 * @return JTable
	 */
	public JTable getManualTable(){
		return manualTable;
	}

	/**
	 * Retorna tabela automatica
	 *
	 * @return JTable
	 */
	public JTable getAutomaticTable(){
		return automaticTable;
	}
	
	/**
	 * Retorna o valor do falso positivo
	 *
	 * @return double
	 */
	public double getFP(){
		return fp;
	}
	
	/**
	 * Retorna o valor do falso negativo
	 *
	 * @return double
	 */
	public double getFN(){
		return fn;
	}
}
