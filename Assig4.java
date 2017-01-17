
//Joe Bender
//CS 401
//Ramirez Assignment #4
//Tues/Thurs 9:30-10:45



import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Assig4
{
	public JFrame theWindow;
	public JPanel thePanel;
	public ArrayList<Ballot> Ballotdata = new ArrayList<Ballot>();
	public JButton Login;
	public JButton CastVote;
	public int userid;
	
	public Assig4(String y) throws IOException
	{
		readIn(y);
		thePanel = new JPanel();
		theWindow = new JFrame("E-Vote Version 2.0");
		theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theWindow.add(thePanel);
		thePanel.setLayout(new GridLayout(1,(Ballotdata.size()+2)));
		for(int x = 0; x < Ballotdata.size(); x++)
		{
			thePanel.add(Ballotdata.get(x));
		}
		Login = new JButton("Login to Vote");
		CastVote = new JButton("Cast your Votes!");
		
		MainListener mListener = new MainListener();
		
		Login.addActionListener(mListener);
		CastVote.addActionListener(mListener);
		
		CastVote.setEnabled(false);
		
		thePanel.add(Login);
		thePanel.add(CastVote);
		theWindow.pack();
		theWindow.setVisible(true);
	}
	
	
	
	public static void main(String [] args) throws IOException
	{
		String X = args[0];
		
		new Assig4(X);
		
	
	}
	
	public class MainListener implements ActionListener 
	{

		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == Login)
			{
				String uID = JOptionPane.showInputDialog(null,"Please enter your voter ID number:");
				userid = Integer.parseInt(uID);
				checkid(userid);
				
				
			}
			if(e.getSource() == CastVote)
			{
				int thecheck = JOptionPane.showConfirmDialog(null, "Confirm Votes", "Confirm your votes and submit?", JOptionPane.YES_NO_OPTION);
				
				if (thecheck != 1)
				{
					confirmvotes();
					
					
					for(int i = 0; i < Ballotdata.size();i++)
					{
						
						Ballotdata.get(i).setD();
						Ballotdata.get(i).makeBlack();
						
					}
					
					Login.setEnabled(true);
					CastVote.setEnabled(false);
				}

			}
		}
	
	}
	
	
	public void confirmvotes()
	{
		
		for(int i = 0; i <Ballotdata.size();i++)
		{
			try
			{

				PrintWriter outputFile = new PrintWriter("temptemp.txt");
				FileInputStream fstream = new FileInputStream(Ballotdata.get(i).Ballotid+".txt");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				String check = br.readLine();
				
				int count = 0;
				
				while(check != null)
				{

					if(Ballotdata.get(i).selected == count)
					{
					
						String[] c = check.split(":");
						int x = Integer.parseInt(c[1]);
						outputFile.println(c[0]+":"+(x+1));
						
						
					}
					else
					{
						outputFile.println(check);	
					}
					check = br.readLine();
					count++;
				}
				
				outputFile.close();
				File file = new File(Ballotdata.get(i).Ballotid+".txt");
				File file2 = new File("temptemp.txt");
				file.delete();
				file2.renameTo(new File(Ballotdata.get(i).Ballotid+".txt"));
			}catch(Exception e)
			{
				
			}
		}
		try
		{
			PrintWriter outputFile = new PrintWriter("temptemptemp.txt");
			FileInputStream fstream = new FileInputStream("voters.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String check = br.readLine();
			
			while(check != null)
			{

				String[] q = check.split(":");
				if(userid == Integer.parseInt(q[0]))
				{
					outputFile.println(q[0]+":"+q[1]+":true");
				}
				else
				{
					outputFile.println(check);
				}
				check = br.readLine();
			}
			
			outputFile.close();
			File file = new File("voters.txt");
			File file2 = new File("temptemptemp.txt");
			file.delete();
			file2.renameTo(new File("voters.txt"));
			
		}catch(Exception e)
		{
		}
		
		
	}
	
	public void checkid(int u) 
	{
		try 
		{
			FileInputStream fstream = new FileInputStream("voters.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line = br.readLine();
			
			while(line != null)
			{
				String[] idcheck = line.split(":");
				
				if(Integer.parseInt(idcheck[0]) == u)
				{
					
					if(idcheck[2].equals("true"))
					{
						JOptionPane.showMessageDialog(null, idcheck[1] + ", you have already voted!");
						return;
					}
					else
					{
						Login.setEnabled(false);
						CastVote.setEnabled(true);
						
						for(int i = 0; i < Ballotdata.size();i++)
						{
							Ballotdata.get(i).setE();	
						}
						
						JOptionPane.showMessageDialog(null, idcheck[1] + ", please make your choices.");
						return;
						
					}
				}
				
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());

		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}
		
		JOptionPane.showMessageDialog(null, "Invalid User ID");
		
	}
	
	
	public void readIn(String name) throws IOException 
	{
		FileInputStream fstream = new FileInputStream(name);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		int numofballots = Integer.parseInt(br.readLine());
		
		
		for(int i = 0; i < numofballots; i++)
		{
			ArrayList<String> options = new ArrayList<String>();
			
			String ballot = br.readLine();
			String[] info = ballot.split(":");
			String id = info[0];
			String uname = info[1];
			String[] info2 = info[2].split(",");
			for(int j = 0; j < info2.length; j++)
			{
				options.add(info2[j]);
			}
			
			Ballotdata.add(new Ballot(id,uname,options));
			
			File file = new File(id+".txt");
			if(!file.exists())
			{
				PrintWriter outputFile = new PrintWriter(id+".txt");
				
				for(int m = 0; m <options.size(); m++)
				{
					outputFile.println(options.get(m)+":0");
				}
				outputFile.close();

			}
			
		}
	}
}


