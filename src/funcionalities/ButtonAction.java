package funcionalities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.portable.ValueBase;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;

public class ButtonAction {

	private PrintWriter writer;
	private JTextField searchDirectory;
	private LeitorDeFicheiro lf;
	private int counterFN = 0;
	private int counterFP = 0;
	private JTable manualTable;
	private JTable automaticTable;
	private File spam;
	private File ham;
	private ArrayList<Rule> rulesList;
	private double contaHam = 0.0;
	private double contaSpam = 0.0;
	private AntiSpamFilterAutomaticConfiguration antiSpamFilter = new AntiSpamFilterAutomaticConfiguration();

	public void searchFile(JTextField searchDirectory) {

		this.searchDirectory = searchDirectory;

		File f = new File("./" + searchDirectory.getName());

		if(!f.exists()){

			JFileChooser fileChooser = new JFileChooser();

			// For File
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			fileChooser.setAcceptAllFileFilterUsed(false);

			int rVal = fileChooser.showOpenDialog(null);

			//Escolher o ficheiro correcto
			if (rVal == JFileChooser.APPROVE_OPTION) {

				if(searchDirectory.getName().equals("rules.cf")){
					if(fileChooser.getSelectedFile().getName().equals("rules.cf")){
						searchDirectory.setText(fileChooser.getSelectedFile().toString());
					}
					else{
						JOptionPane.showMessageDialog(null, "Não escolheste o ficheiro rules.cf");
					}
				}
				if(searchDirectory.getName().equals("spam.log")){
					if(fileChooser.getSelectedFile().getName().equals("spam.log")){
						searchDirectory.setText(fileChooser.getSelectedFile().toString());
					}
					else{
						JOptionPane.showMessageDialog(null, "Não escolheste o ficheiro spam.log");
					}
				}
				if(searchDirectory.getName().equals("ham.log")){
					if(fileChooser.getSelectedFile().getName().equals("ham.log")){
						searchDirectory.setText(fileChooser.getSelectedFile().toString());	

					}
					else{
						JOptionPane.showMessageDialog(null, "Não escolheste o ficheiro ham.log");
					}
				}
			}

			lf = new LeitorDeFicheiro();
			try{
				lf.createTables(searchDirectory);   
			}catch (Exception e) {
				System.out.println("");
			}
		}

		else{
			searchDirectory.setText("./" + f.getName());
			if(searchDirectory.getName().equals("spam.log"))
				spam = new File("./spam.log");
			if(searchDirectory.getName().equals("ham.log"))
				ham = new File("./ham.log");
			lf = new LeitorDeFicheiro();
			if(searchDirectory.getName().equals("rules.cf"))
				lf.createTables(searchDirectory); 
		}
	}

	public void save(JTextField fileDirectory, JTable table) {

		File f = new File(fileDirectory.getText());

		Scanner sc = null;
		FileWriter fw = null;

		try {
			sc = new Scanner(f);
			fw = new FileWriter(f);

			for(int j = 0 ; j < table.getRowCount(); j++){
				fw.write(table.getValueAt(j,0) + " " + table.getValueAt(j, 1) + "\n");
			}

		} catch (Exception e) {

		}

		try {
			sc.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gerar(JTable table, String spamDirectory, String hamDirectory, JButton button){

		counterFN = 0;
		counterFP = 0;
		
		if(button.getText().equals("Avaliar Qualidade")){
			table = manualTable;
			
			//Criar Double[] x com os pesos;
			
//			lf.lerFicheiroLog(spamDirectory);
//
//			calculateFN(x);
//
//			lf.lerFicheiroLog(hamDirectory);
//
//			calculateFP(x);
		}

		if(button.getText().equals("Gerar")){
			
			try {
				antiSpamFilter.init();
				lf.lerValoresAutomatico();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			for (int i = 0; i < lf.getValores().size(); i++) {
				
				automaticTable.setValueAt(Double.parseDouble(lf.getValores().get(i)), i, 1);
			}
			
			table = automaticTable;
		}
		
	}

	public void calculateFP(double[] x){
		contaHam = 0;
		
		File f = new File("./rules.cf");
		JTextField tf = new JTextField(f.getName());
		lf.createTables(tf);
		
		System.out.println("");
		for(int i = 0; i < lf.getRulesList().size(); i++){
			Rule rule = new Rule(lf.getRulesList().get(i).getName(), x[i]);
			rulesList.add(rule);
		}

		for(int i = 0; i < lf.getLog().size(); i++){

			if(!lf.getLog().get(i).equals("0.0")){
				String rule = lf.getLog().get(i);

				for(int j = 0; j < x.length; j++){
					if(rule.equals(rulesList.get(j))){
						contaHam += (double) x[j];
					}
				}
			}

			else{
				if(contaHam > 5.0)
					counterFP++;

				contaHam = 0;
			}
		}
	}

	public void calculateFN(double[] x){

		contaSpam = 0;
		
		for(int i = 0; i < lf.getLog().size(); i++){

			if(!lf.getLog().get(i).equals("0.0")){
				String rule = lf.getLog().get(i);

				for(int j = 0; j < rulesList.size(); j++){
					if(rule.equals(rulesList.get(j))){
						contaSpam += (double) x[j];
					}
				}
			}

			else{
				if(contaSpam < -5.0)
					counterFN++;

				contaSpam = 0;
			}
		}
	}

	public JTextField getSearchDirectory(){
		return searchDirectory;
	}

	public void setManualTable(JTable table){
		manualTable = table;
	}

	public void setAutomaticTable(JTable table){
		automaticTable = table;
	}

	public JTable getManualTable(){
		if(manualTable == null)
			return lf.getManualTable();

		return manualTable;
	}

	public JTable getAutomaticTable(){
		if(automaticTable == null)
			return lf.getAutomaticTable();

		return automaticTable;
	}
	
	public int getCounterFN(){
		return counterFN;
	}

	public int getCounterFP(){
		return counterFP;
	}
	
	public File getSpam() {
		return spam;
	}
	public File getHam() {
		return ham;
	}
}