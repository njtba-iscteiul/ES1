package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import funcionalities.ButtonAction;

public class Frame {
	
	JFrame frame;
	
	public Frame(){
		
		frame = new JFrame("Software ES");
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		
		addComponents();
		
	}

	private void addComponents() {
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(3,3,0,10));
		frame.add(searchPanel, BorderLayout.NORTH);
		
		ButtonAction buttonAction = new ButtonAction();
		
		//Search Files
		
		JLabel fileLabel = new JLabel("Ficheiro: ");
		JLabel spamLabel = new JLabel("Spam: ");
		JLabel hamLabel = new JLabel("Ham: ");
		
		JTextField fileDirectory = new JTextField();
		
		JTextField spamDirectory = new JTextField();
		
		JTextField hamDirectory = new JTextField();
		
		JButton searchButton = new JButton("Procurar...");
		JButton spamButton = new JButton("Procurar...");
		JButton hamButton = new JButton("Procurar...");
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction.searchFile(fileDirectory);
			}
		});
		
		spamButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction.searchFile(spamDirectory);
			}
		});
		
		hamButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction.searchFile(hamDirectory);
			}
		});
		
		searchPanel.add(fileLabel);
		searchPanel.add(fileDirectory);
		searchPanel.add(searchButton);
		searchPanel.add(spamLabel);
		searchPanel.add(spamDirectory);
		searchPanel.add(spamButton);
		searchPanel.add(hamLabel);
		searchPanel.add(hamDirectory);
		searchPanel.add(hamButton);		
	}

	public void init() {
		frame.setSize(800, 500);
		frame.setVisible(true);
	}

}