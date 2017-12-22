package jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import funcionalities.Rule;

class RuleTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	Rule r = new Rule("Regra",0.1);
	
	@Test
	void testRule() {
		Rule r2 = new Rule("Regra2",1.0);
		assertSame("Regra2", r2.getName());
		assertEquals(1.0, r2.getWeight());
	}

	@Test
	void testGetName() {
		assertSame("Regra",r.getName());
	}

	@Test
	void testGetWeight() {
		assertEquals(0.1, r.getWeight());
	}
	
	@Test
	void testToString() {
		String text = "Regra - 0.1";
		assertEquals(text, r.toString());
	}

}
