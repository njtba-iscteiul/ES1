package jUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.Frame;

class FrameTest {
	
	JFrame frame;
	Frame f = new Frame();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFrame() {
		frame = new JFrame("Software ES");
		assertEquals(frame,f);
	}

	@Test
	void testInit() {
		frame.setSize(800,500);
		assertSame(frame.getSize(),f.getFrame().getSize());
	}
	
	@Test
	void testGetFrame() {
		assertEquals(frame, f.getFrame());
	}

}
