package jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import funcionalities.LeitorDeFicheiro;
import funcionalities.Rule;

class LeitorDeFicheiroTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	LeitorDeFicheiro lf = new LeitorDeFicheiro();
	ArrayList<String> lista;
	ArrayList<String> valores;

	@Test
	void testCreateTables() {
		JTextField textField = new JTextField();
		textField.setText("C:\\Users\\Ricardo\\Documents\\rules.cf");
		lf.createTables(textField);
	}

	@Test
	void testLerFicheiroLog() {
		
		String dir = "C:\\Users\\Ricardo\\Documents\\spam.log";
		lf.lerFicheiroLog(dir);
		lista = new ArrayList<String>();
		assertEquals(2576, lf.getLog().size());
	}

	@Test
	void testLerValoresAutomatico() {
		lf.lerValoresAutomatico();
		assertEquals(0,lf.getValores().size());
	}

	@Test
	void testFillTable() {
		fail("Not yet implemented");
	}

	@Test
	void testFillArray() {
		lf.fillArray();
		assertEquals(335, lf.getRulesList().size());
	}

	@Test
	void testGetRulesList() {
		ArrayList<Rule> lista = new ArrayList<Rule>();
		assertEquals(lista,lf.getRulesList());
	}

	@Test
	void testGetLog() {
		ArrayList<String> lista = new ArrayList<String>();
		assertEquals(lista,lf.getLog());
	}

	@Test
	void testGetValores() {
		ArrayList<String> lista = null;
		assertEquals(lista,lf.getValores());
	}

	@Test
	void testGetDefaultManualTable() {
		DefaultTableModel model = null;
		assertEquals(model, lf.getDefaultManualTable());
	}

	@Test
	void testGetManualTable() {
		JTable table = null;
		assertEquals(table, lf.getManualTable());
	}

	@Test
	void testGetAutomaticTable() {
		JTable table = null;
		assertEquals(table, lf.getAutomaticTable());
	}

	@Test
	void testGetFP() {
		double fp = 0.0;
		assertEquals(0.0,lf.getFP());
	}

	@Test
	void testGetFN() {
		double fn = 0.0;
		assertEquals(0.0, lf.getFN() );
	}

}
