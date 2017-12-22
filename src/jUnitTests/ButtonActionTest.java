package jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import funcionalities.ButtonAction;
import funcionalities.Rule;

class ButtonActionTest {

	ButtonAction ba = new ButtonAction();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSearchFile() {
		JTextField searchDirectory = new JTextField();
		searchDirectory.setText("spam.log");
		ba.searchFile(searchDirectory);
	}

	@Test
	void testSave() {
		JTextField searchDirectory = new JTextField();
		searchDirectory.setText("C:\\Users\\Ricardo\\Documents\\spam.log");
		DefaultTableModel model = new DefaultTableModel();
		Rule r = new Rule("Regra",1.0);
		model.addRow(new Object[] {r.getName(),r.getWeight()});
		JTable table = new JTable(model);
		ba.save(searchDirectory, table);
	}

	@Test
	void testGerar() {

		JTable table = new JTable();
		JButton button = new JButton("Avaliar Qualidade");
		
		ba.gerar(table, button);
		
	}

	@Test
	void testCalculateFP() {
		double[] x = {1.0,1.3};
		ba.calculateFP(x);
	}

	@Test
	void testCalculateFN() {
		double[] x = {1.0,1.3};
		ba.calculateFN(x);
	}

	@Test
	void testGetSearchDirectory() {
		JTextField tf = null;
		assertSame(tf,ba.getSearchDirectory());
	}

	@Test
	void testSetManualTable() {
		JTable table = new JTable();
		ba.setManualTable(table);
		assertSame(table,ba.getManualTable());
	}

	@Test
	void testSetAutomaticTable() {
		JTable table = new JTable();
		ba.setAutomaticTable(table);
		assertSame(table,ba.getAutomaticTable());
	}

	@Test
	void testGetManualTable() {
		JTable table = null;
		assertSame(table,ba.getManualTable());
	}

	@Test
	void testGetAutomaticTable() {
		JTable table = null;
		assertSame(table,ba.getAutomaticTable());
	}

	@Test
	void testGetCounterFN() {
		assertSame(0,ba.getCounterFN());
	}

	@Test
	void testGetCounterFP() {
		assertSame(0,ba.getCounterFP());
	}

	@Test
	void testGetSpam() {
		File f = null;
		assertSame(f,ba.getSpam());
	}

	@Test
	void testGetHam() {
		File f = null;
		assertSame(f,ba.getHam());
	}

}
