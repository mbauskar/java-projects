
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class frmNewUser extends JFrame 
{
	private JTextField jtfUsrName;
	private JTextField jtfID;
	private JPasswordField jpfPwd;
	private JPasswordField jpfCPwd;
	private JTextArea jtaQue;	
	private JTextField jtfAns;
	
	public frmNewUser()
	{
		this.setLayout(null);
		this.setTitle("Create Account");
		this.setSize(350, 400);
		this.setResizable(false);
		this.getContentPane().setBackground(Var.bgColor);
		CenterLocation loc=new CenterLocation(this.getSize());
		this.setLocation(loc.getLocation());
		this.setIconImage(new GetImage().getIcon("img/icons/phoneBook.png"));
		this.addComponents();		
	}
	
	private void addComponents()
	{
		this.addLabels();
		this.addTextFields();
		
		JButton btnCreate=new JButton("Create");
		btnCreate.setIcon(new GetImage().getImageIcon("img/icons/login.png"));
		btnCreate.setBounds(65,310,100,30);		
		btnCreate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnCreate_ActionPerformed();
			}
			
		});
		this.add(btnCreate);
		
		JButton btnCancel=new JButton("Cancel");
		btnCancel.setIcon(new GetImage().getImageIcon("img/icons/cancel.png"));
		btnCancel.setBounds(180,310,100,30);
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnCancel_ActionPerformed();				
			}			
		});
		this.add(btnCancel);
	}
	
	protected void btnCancel_ActionPerformed()
	{
		System.exit(0);
	}

	protected void btnCreate_ActionPerformed()
	{	
		if(isValidInfo())
		{
			String values="'"+jtfID.getText()+"','"+jpfPwd.getText()+"','"+
							jtfUsrName.getText()+"','"+jtaQue.getText()+"','"+
							jtfAns.getText()+"'";
			String query="Insert into registration values("+values+")";
			boolean flag=DataLayer.insertUserInfo(query);
			if(flag)
			{
				JOptionPane.showMessageDialog(null, "Account created..");
				frmLogin obj=new frmLogin();
				obj.show();
			}
			else
				JOptionPane.showMessageDialog(null, "Error can not create account");
			this.dispose();
		}
	}

	private void addLabels()
	{
		JLabel lbl=new JLabel("Create New Account..",JLabel.CENTER);
		lbl.setForeground(Color.BLUE);
		lbl.setBackground(Color.white);
		lbl.setFont(new Font("Arial",Font.PLAIN,20));
		lbl.setBounds(5, 5, 340, 40);
		this.add(lbl);
		
		JLabel lblUsrName=new JLabel("Enter User Name",JLabel.RIGHT);		
		lblUsrName.setFont(new Font("Arial",Font.PLAIN,12));
		lblUsrName.setBounds(15, 50, 100, 30);
		this.add(lblUsrName);
		
		JLabel lblID=new JLabel("Enter Login ID",JLabel.RIGHT);		
		lblID.setFont(new Font("Arial",Font.PLAIN,12));
		lblID.setBounds(15, 85, 100, 30);
		this.add(lblID);
		
		JLabel lblPwd=new JLabel("Enter Password",JLabel.RIGHT);		
		lblPwd.setFont(new Font("Arial",Font.PLAIN,12));
		lblPwd.setBounds(15, 120, 100, 30);
		this.add(lblPwd);
		
		JLabel lblCPwd=new JLabel("Confirm Password",JLabel.RIGHT);		
		lblCPwd.setFont(new Font("Arial",Font.PLAIN,12));
		lblCPwd.setBounds(10, 155, 105, 30);
		this.add(lblCPwd);
		
		JLabel lblQue=new JLabel("Enter Question",JLabel.RIGHT);		
		lblQue.setFont(new Font("Arial",Font.PLAIN,12));
		lblQue.setBounds(15, 190, 100, 30);
		this.add(lblQue);
		
		JLabel lblAns=new JLabel("Enter Answer",JLabel.RIGHT);		
		lblAns.setFont(new Font("Arial",Font.PLAIN,12));
		lblAns.setBounds(15, 260, 100, 30);
		this.add(lblAns);
	}
	
	private void addTextFields()
	{
		jtfUsrName=new JTextField();
		jtfUsrName.setFont(new Font("Arial",Font.PLAIN,15));
		jtfUsrName.setBounds(125, 50, 205, 30);
		this.add(jtfUsrName);
		
		jtfID=new JTextField();
		jtfID.setFont(new Font("Arial",Font.PLAIN,15));
		jtfID.setBounds(125, 85, 205, 30);
		this.add(jtfID);
		
		jpfPwd=new JPasswordField();
		jpfPwd.setFont(new Font("Arial",Font.PLAIN,15));
		jpfPwd.setBounds(125, 120, 205, 30);
		this.add(jpfPwd);
		
		jpfCPwd=new JPasswordField();
		jpfCPwd.setFont(new Font("Arial",Font.PLAIN,15));
		jpfCPwd.setBounds(125, 155, 205, 30);
		this.add(jpfCPwd);
		
		jtaQue=new JTextArea();
		jtaQue.setFont(new Font("Arial",Font.PLAIN,15));
		JScrollPane jsp=new JScrollPane(jtaQue);
		jsp.setBounds(125, 190, 205, 65);
		this.add(jsp);
				
		jtfAns=new JTextField();
		jtfAns.setFont(new Font("Arial",Font.PLAIN,15));
		jtfAns.setBounds(125, 260, 205, 30);
		this.add(jtfAns);
	}
	
	private boolean isValidInfo()
	{
		boolean flag=false;
		if(jtfUsrName.getText().equals("")||jtfID.getText().equals("")||jpfPwd.getText().equals("")||jpfCPwd.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "User Name,Login ID,Password & Confirm Password field can not be empty..");
			return false;
		}			
		else
		{
			if(isIDAvailable())
			{				
				if(jpfPwd.getText().equals(jpfCPwd.getText()))
					return true;
				else
					JOptionPane.showMessageDialog(null, "Password not matched..");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Login ID not available..");
				jtfUsrName.setText("");
				jtfID.setText("");
				jpfPwd.setText("");
				jpfCPwd.setText("");
				jtaQue.setText("");	
				jtfAns.setText("");
			}
		}
		return flag;
	}

	private boolean isIDAvailable()
	{
		boolean flag=false;
		String IDs[]=DataLayer.getNames("Select LoginID from registration");
		if(IDs.length==0)
			return true;
		else
		{
			for(int i=0;i<IDs.length;i++)
			{
				if(IDs[i].equals(jtfID.getText()))
					flag=false;
				else
					flag=true;
			}
		}
		return flag;
	}
}
