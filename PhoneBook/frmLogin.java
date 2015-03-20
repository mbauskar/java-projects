
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class frmLogin extends JFrame
{
	private JTextField jtfLoginID;
	private JPasswordField jpfPwd;
	private JLabel lblNewUsr;
	private JLabel lblforgetPwd;

	public frmLogin()
	{
		this.setLayout(null);
		this.setTitle("Login");
		this.setResizable(false);
		this.setIconImage(new GetImage().getIcon("img/icons/phoneBook.png"));
		this.setSize(460, 220);
		this.addComponent();
		CenterLocation loc=new CenterLocation(this.getSize());
		this.setLocation(loc.getLocation());
		this.getContentPane().setBackground(Var.bgColor);
	}

	public void addComponent()
	{
		Image img=new GetImage().getIcon("img/login.jpg");
		img=img.getScaledInstance(152, 167, Image.SCALE_DEFAULT);
		JLabel lblImg=new JLabel(new ImageIcon(img));
		lblImg.setBounds(5, 5, 152, 175);
		this.add(lblImg);

		JLabel lbl=new JLabel("Login to Phone Book",JLabel.CENTER);
		lbl.setForeground(Color.BLUE);
		lbl.setFont(new Font("Arial",Font.PLAIN,20));
		lbl.setBounds(165, 5, 290, 40);
		this.add(lbl);

		JLabel lblID=new JLabel("Login ID",JLabel.RIGHT);
		lblID.setFont(new Font("Arial",Font.PLAIN,15));
		lblID.setBounds(165, 50, 70, 30);
		this.add(lblID);

		jtfLoginID=new JTextField();
		jtfLoginID.setFont(new Font("Arial",Font.PLAIN,20));
		jtfLoginID.setBounds(255, 50, 190, 30);
		this.add(jtfLoginID);

		JLabel lblPwd=new JLabel("Password",JLabel.RIGHT);
		lblPwd.setFont(new Font("Arial",Font.PLAIN,15));
		lblPwd.setBounds(165, 85, 70, 30);
		this.add(lblPwd);

		jpfPwd=new JPasswordField();
		jpfPwd.setFont(new Font("Arial Black",Font.PLAIN,20));
		jpfPwd.setBounds(255, 85, 190, 30);
		this.add(jpfPwd);

		JButton btnLogin=new JButton("Login");
		btnLogin.setIcon(new GetImage().getImageIcon("img/icons/login.png"));
		btnLogin.setBounds(240,125,100,30);
		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				btnLogin_ActionPerformed();
			}

		});
		this.add(btnLogin);

		JButton btnCancel=new JButton("Cancel");
		btnCancel.setIcon(new GetImage().getImageIcon("img/icons/cancel.png"));
		btnCancel.setBounds(345,125,100,30);
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				btnCancel_ActionPerformed();
			}
		});
		this.add(btnCancel);

		lblNewUsr=new JLabel("Register",JLabel.CENTER);
		lblNewUsr.setForeground(Color.BLACK);
		lblNewUsr.setFont(new Font("Arial",Font.PLAIN,12));
		lblNewUsr.setBounds(370,165, 50, 15);
		lblNewUsr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblNewUsr.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				lblNewUsr_MouseClicked();
			}

			public void mouseEntered(MouseEvent me)
			{
				lblNewUsr.setForeground(Color.BLUE);
			}

			public void mouseExited(MouseEvent me)
			{
				lblNewUsr.setForeground(Color.BLACK);
			}
		});
		this.add(lblNewUsr);

		lblforgetPwd=new JLabel("Forget password ?",JLabel.CENTER);
		lblforgetPwd.setForeground(Color.BLACK);
		lblforgetPwd.setFont(new Font("Arial",Font.PLAIN,12));
		lblforgetPwd.setBounds(245,165, 105, 15);
		lblforgetPwd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblforgetPwd.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				lblforgetPwd_MouseClicked();
			}

			public void mouseEntered(MouseEvent me)
			{
				lblforgetPwd.setForeground(Color.BLUE);
			}

			public void mouseExited(MouseEvent me)
			{
				lblforgetPwd.setForeground(Color.BLACK);
			}
		});
		this.add(lblforgetPwd);
	}

	private void btnLogin_ActionPerformed()
	{
		if(jtfLoginID.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Login ID field can not be empty..");
			jtfLoginID.requestFocus(true);
		}
		else if(jpfPwd.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Password field can not be empty..");
			jtfLoginID.requestFocus(true);
		}
		else if(isValidUser())
		{
			this.setVisible(false);
			frmPhoneBook phbk=new frmPhoneBook();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please check login id and password...");
			jtfLoginID.setText("");
			jpfPwd.setText("");
			jtfLoginID.requestFocus(true);
		}
	}

	private void btnCancel_ActionPerformed()
	{
		System.exit(0);
	}

	private boolean isValidUser()
	{
		boolean flag=false;
		try
		{
			String usrInfo[]=DataLayer.getUserInfo("Select Password,UserName from registration where LoginID='"+jtfLoginID.getText()+"'");
			if(usrInfo!=null)
			{
				if(usrInfo[0].equals(jpfPwd.getText()))
				{
					Var.LoginID=jtfLoginID.getText();
					Var.UserName=usrInfo[1];
					flag=true;
				}
				else
					flag=false;
			}
			else
				flag=false;
		}
		catch(Exception err)
		{
			err=null;
			flag=false;
		}
		return flag;
	}

	private void lblNewUsr_MouseClicked()
	{
		frmNewUser newUsr=new frmNewUser();
		newUsr.show();
		this.dispose();
	}

	protected void lblforgetPwd_MouseClicked()
	{
		try
		{
			String logID=JOptionPane.showInputDialog("Enter your Login ID : ");
			String QueAns[]=DataLayer.getUserInfo("Select Question,Ans from registration where LoginID='"+logID+"'");
			if(QueAns!=null)
			{
				String Ans=JOptionPane.showInputDialog("Que : "+QueAns[0]);
				if(QueAns[1].equals(Ans))
				{
					String pwd_UName[]=DataLayer.getUserInfo("Select Password,UserName from registration where LoginID='"+logID+"'");
					String str="User Name : "+pwd_UName[1]+"\nLogin ID : "+logID
								+"\nPassword : "+pwd_UName[0];
					JOptionPane.showMessageDialog(null, str);
				}
				else
					JOptionPane.showMessageDialog(null, "Sorry your answer does not match");
			}
			else
				JOptionPane.showMessageDialog(null, logID+" Login ID does not exist...");
		}
		catch(Exception err)
		{
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
	}
}