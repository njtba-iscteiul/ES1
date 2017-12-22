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
	private LeitorDeFicheiro lf = new LeitorDeFicheiro();
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

	/**
	 * Procura ficheiro no computador, caso ja tenhamos procurado uma vez os ficheiros ficam criados na pasta do projecto
	 *
	 * @param Descrição directoria ficheiros
	 * @return ....
	 */
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

	/**
	 * Guarda as alterações efetuadas na tabela no ficheiro rules.cf
	 *
	 * @param Descrição directoria ficheiro e a tabela
	 * @return ....
	 */
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
	
	/**
	 * Gera os falsos positivos e falsos negativos
	 *
	 * @param Descrição Tabela e botão para diferencia configuração manual e automatica
	 * @return ....
	 */
	public void gerar(JTable table, JButton button){

		//counterFN = 0;
		//counterFP = 0;
		
		if(button.getText().equals("Avaliar Qualidade")){
			table = manualTable;
			double[] x = new double[335]; 

			for (int i = 0; i < manualTable.getRowCount(); i++) {
				x[i] = (double) manualTable.getValueAt(i, 1);
			}


			
			//lf.lerFicheiroLog(spamDirectory);

			calculateFP(x);

			//lf.lerFicheiroLog(hamDirectory);

			calculateFN(x);
		}

		if(button.getText().equals("Gerar")){
			
			try {
				antiSpamFilter.init(this);
				lf.lerValoresAutomatico();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			for (int i = 0; i < lf.getValores().size(); i++) {
				
				automaticTable.setValueAt(Double.parseDouble(lf.getValores().get(i)), i, 1);
			}
			
			table = automaticTable;
			
			counterFP = (int) lf.getFP();
			counterFN = (int) lf.getFN();
		}
		
	}

	
	/**
	 * Calcula os falsos positivos consoante os valores na tabela
	 *
	 * @param Descrição array com pesos das regras
	 * @return ....
	 */
	public void calculateFP(double[] x){
		
		counterFP = 0;
		
		contaHam = 0;

		lf.fillArray();
		
		lf.lerFicheiroLog("./ham.log");
		
		rulesList = new ArrayList<>();
		
		for(int i = 0; i < lf.getRulesList().size(); i++){
			Rule rule = new Rule(lf.getRulesList().get(i).getName(), x[i]);
			rulesList.add(rule);
		}
		
		for(int i = 0; i < lf.getLog().size(); i++){

			if(!lf.getLog().get(i).equals("0.0")){
				String rule = lf.getLog().get(i);

				for(int j = 0; j < x.length; j++){
					if(rule.equals(rulesList.get(j).getName())){
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

	/**
	 * Calcula os falsos negativos consoante os valores na tabela
	 *
	 * @param Descrição array com pesos das regras
	 * @return ....
	 */
	public void calculateFN(double[] x){

		contaSpam = 0;
		
		counterFN = 0;
		
		lf.lerFicheiroLog("./spam.log");
		
		for(int i = 0; i < lf.getLog().size(); i++){
			
			if(!lf.getLog().get(i).equals("0.0")){
				String rule = lf.getLog().get(i);
				
				for(int j = 0; j < rulesList.size(); j++){
					if(rule.equals(rulesList.get(j).getName())){
						
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
	
	/**
	 * Retorna directoria do ficheiro
	 *
	 * @return JTextField com a directoria
	 */
	public JTextField getSearchDirectory(){
		return searchDirectory;
	}
	
	/**
	 * Adiciona a tabela manual a uma JTable a null
	 *
	 * @param Descrição JTable manual
	 * @return ....
	 */
	public void setManualTable(JTable table){
		manualTable = table;
	}

	/**
	 * Adiciona a tabela automatica a uma JTable a null
	 *
	 * @param Descrição JTable automatica
	 * @return ....
	 */
	public void setAutomaticTable(JTable table){
		automaticTable = table;
	}

	/**
	 * Retorna a tabela manual
	 *
	 * @return JTable manual
	 */
	public JTable getManualTable(){
		if(manualTable == null)
			return lf.getManualTable();

		return manualTable;
	}

	/**
	 * Retorna a tabela automatica
	 *
	 * @return JTable automatica
	 */
	public JTable getAutomaticTable(){
		if(automaticTable == null)
			return lf.getAutomaticTable();

		return automaticTable;
	}
	
	/**
	 * Retorna o valor do falso negativo
	 *
	 * @return int
	 */
	public int getCounterFN(){
		return counterFN;
	}

	/**
	 * Retorna o valor do falso positivo
	 *
	 * @return int
	 */
	public int getCounterFP(){
		return counterFP;
	}
	
	/**
	 * Retorna o ficheiro spam.log
	 *
	 * @return File
	 */
	public File getSpam() {
		return spam;
	}
	
	/**
	 * Retorna o ficheiro ham.log
	 *
	 * @return File
	 */
	public File getHam() {
		return ham;
	}
}