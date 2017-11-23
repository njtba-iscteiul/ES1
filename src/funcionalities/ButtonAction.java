package funcionalities;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ButtonAction {

	private JTextField searchDirectory;
	private LeitorDeFicheiro lf;
	public void searchFile(JTextField searchDirectory) {
		
		this.searchDirectory = searchDirectory;
	
		JFileChooser fileChooser = new JFileChooser();
 
        // For File
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
 
        fileChooser.setAcceptAllFileFilterUsed(false);
 
        int rVal = fileChooser.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
        	searchDirectory.setText(fileChooser.getSelectedFile().toString());
        }
        
        lf = new LeitorDeFicheiro();
        lf.createTables(searchDirectory);   
        
	}
	
	
	public JTextField getSearchDirectory(){
		return searchDirectory;
	}

	public JTable getManualTable(){
		return lf.getManualTable();
	}
	
	public JTable getAutomaticTable(){
		return lf.getAutomaticTable();
	}
	
}