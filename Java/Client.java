import java.rmi.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.swing.filechooser.*;
public class Client extends JFrame implements ActionListener
{
	JTextField filename,clgname;
	JTextArea display;
	JButton browse,send,result;
	String d[][]=new String[10][3];
	String serverURL = "rmi://127.0.0.1/ServerImpl";
	ServerInterface server;
	boolean isSent=false;
	String oldFileName="";
	
	ArrayList<Student> list=new ArrayList<Student>();
	Client()
	{	
	super("Client");
		try
		{	

			JLabel l1=new JLabel("College Name :"),l2=new JLabel("Select File :");
			
			browse=new JButton("browse");
			filename=new JTextField(20);
			clgname=new JTextField(20);
			send=new JButton("send");
			result=new JButton("Get Result");
			display=new JTextArea();
			
			filename.setEditable(false);
			display.setEditable(false);
			
			result.setVisible(false);
			send.setVisible(false);
			
			setLayout(null); 
			l1.setBounds(10,10,100,30);
			add(l1);
			clgname.setBounds(130,10,320,30);
			add(clgname);
			l2.setBounds(10,50,100,30);
			add(l2);
			browse.setBounds(130,50,100,30);
			add(browse);
			filename.setBounds(250,50,200,30);
			add(filename);
			send.setBounds(10,90,100,30);
			add(send);
			result.setBounds(130,90,100,30);
			add(result);
			display.setBounds(10,150,450,300);
			add(display);
			
			setSize(500,500);
			setVisible(true);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
			browse.addActionListener(this);
			send.addActionListener(this);
			result.addActionListener(this);
			
			server=(ServerInterface)Naming.lookup(serverURL);
			while(true)
			{
				Thread.sleep(300);
				if(server.isResultReady())
				{
					result.setVisible(true);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object o=e.getSource();
		if(browse==o)
		{
			try
			{

				JFileChooser fc=new JFileChooser(System.getProperty("user.dir"));
				
				int returnVal=fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					File f=fc.getSelectedFile();
					
					BufferedReader br = new BufferedReader(new FileReader(f.getName()));
					String line = "";
					String separator="-";
					
					//.csv    	Microsoft Office Excel Comma Separated Values File
					//.txt 		Text Document
					
					if(!isSent)
					{
						
						System.out.println(f.getName());
	 					if(fc.getTypeDescription(f).equals("Text Document"))
						{
							filename.setText(f.getName());
							separator="-"; 
							int i=0;
							while ((line = br.readLine()) != null && i<10) {             
								d[i] = line.split(separator);
								i++;
							}
							send.setVisible(true);
						}
						else if(fc.getTypeDescription(f).equals("Microsoft Office Excel Comma Separated Values File"))
						{
							filename.setText(f.getName());
							separator=","; 
							int i=0;
							while ((line = br.readLine()) != null && i<10) {             
								d[i] = line.split(separator);
								i++;
							}
							send.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(null,"Invalid File Type..!! \nPlease Select .txt or .csv file..!!");
						
						
					}
					else
					{
						JOptionPane.showMessageDialog(null,"You can submit student list only once..!!");
						send.setVisible(false);
					}
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else if(send==o)
		{
			try 
			{	
				if(oldFileName.length()==0 && (clgname==null || clgname.getText().length()==0))
				{
					JOptionPane.showMessageDialog(null,"Please Enter College Name..!!");
					return;
				}
				else if(!isSent)
				{
					
					for(int i=0;i<d.length;i++)
					{
						System.out.println(d[i][0]+" "+d[i][1]+"  "+d[i][2]);
					}
					server.sendData(d,clgname.getText());
					isSent=true;
				}
				else
					JOptionPane.showMessageDialog(null,"You can submit student list only once..!!");
			}
			catch(Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Exception: " + ex);
			}
		}
		else
		{
			try
			{
				display.setText(server.getTop5());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) 
	{
		new Client();		
	}
}
