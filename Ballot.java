//Joe Bender
//CS 401
//Ramirez Assignment #4 - Ballot Class
//Tues/Thurs 9:30-10:45


import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ballot extends JPanel
{
	public String Ballotid;
	public JLabel Ballotname;
	public ArrayList<JButton> choices = new ArrayList<JButton>();
	public int selected = -1;
	
	
	public Ballot()
	{
	}
	public Ballot(String bid, String bn, ArrayList<String> choicesfromfile)
	{
		int numofrows = 1 + choicesfromfile.size();
		
		Ballotid = bid;
		
		setLayout(new GridLayout(numofrows,1));
		Ballotname = new JLabel(bn);
		
		add(Ballotname);
		ButtonListener theListener = new ButtonListener();
		
		
		
		JButton theButton;
		
		for(int i = 0; i < choicesfromfile.size(); i++)
		{
			theButton = new JButton(choicesfromfile.get(i));
			theButton.setEnabled(false);
			
			theButton.addActionListener(theListener);
			
			choices.add(theButton);
			add(choices.get(i));
		}
	}
	
	public void setE() 
	{
		for(int i = 0; i < choices.size(); i++)
		{
			choices.get(i).setEnabled(true);
		}
	}
	
	public void setD() 
	{
		for(int i = 0; i < choices.size(); i++)
		{
			choices.get(i).setEnabled(false);
		}
	}
	
	public void makeBlack()
	{
		if(selected != -1)
		{
			choices.get(selected).setForeground(Color.BLACK);
		}
		selected = -1;
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for(int i = 0; i < choices.size(); i++)
			{

				if(e.getSource() == choices.get(i))
				{
					if(selected >= 0)
					{
						choices.get(selected).setForeground(Color.BLACK);
					}
					selected = i;
					choices.get(i).setForeground(Color.RED);
					
				}
				
				
			}
		}
	}
	
	
	

}
