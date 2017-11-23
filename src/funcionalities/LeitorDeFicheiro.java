package funcionalities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LeitorDeFicheiro {
	
	private DefaultTableModel defaultManualTable;
	private DefaultTableModel defaultAutomaticTable;
	private JTable manualTable;
	private JTable automaticTable;

	public void createTables(JTextField searchDirectory) {
		
		defaultManualTable = new DefaultTableModel();
		defaultAutomaticTable = new DefaultTableModel();
		
		manualTable = new JTable(defaultManualTable);
		automaticTable = new JTable(defaultAutomaticTable);
		
		defaultManualTable.addColumn("Rules");
		defaultManualTable.addColumn("Weight");
		
		defaultAutomaticTable.addColumn("Rules");
		defaultAutomaticTable.addColumn("Weight");
		
		String directory = searchDirectory.getText();
	
        File f = new File(directory);
        
        Scanner sc = null;
        
        try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
//        while(sc.hasNextLine()){
//        	
//        }
        
        sc.close();	
	}

	public JTable getManualTable(){
		return manualTable;
	}
	
	public JTable getAutomaticTable(){
		return automaticTable;
	}
}
