
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class frmPhoneBook extends JFrame
{
	private JMenuItem edit=new JMenuItem("Edit Contact");;
	private JMenuItem delete=new JMenuItem("Delete Contact");

	public frmPhoneBook()
	{
		this(Var.LoginID,Var.UserName);
	}

	public frmPhoneBook(String logID,String usrName)
	{
		Var.refFrmPhBk=this;
		Var.UserName=usrName;
		Var.LoginID=logID;
		Var._names=DataLayer.getNames("Select PerName from contacts where LoginID='"+logID+"'");
		Var.TotalContact=Var._names.length;

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Phone Book");
		this.setResizable(false);
		this.setSize(600, 600);		//After addind menubar height of frame is 540
		this.setLocation(new CenterLocation(this.getSize()).getLocation());
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				if(confirmBeforeExit())
					System.exit(0);
			}
		});
		this.setIconImage(new GetImage().getIcon("img/icons/phoneBook.png"));
		this.addUserComponent();

		JLabel lblWelcome=new JLabel("Wellcome today is : "+new java.util.Date(),JLabel.CENTER);
		lblWelcome.setBounds(0, 520, 600, 20);
		lblWelcome.setFont(new Font("Arial",Font.ITALIC,12));
		lblWelcome.setForeground(Color.BLUE);
		this.add(lblWelcome);

		this.setVisible(true);
	}

	public void addUserComponent()
	{
		this.addPanels();
		this.addMenu();
	}

	private void addMenu()
	{
		JMenuBar mbPhoneBook=new JMenuBar();
		JMenu user=new JMenu("User");
		user.setMnemonic('U');
		JMenuItem jmiLogout=new JMenuItem("Logout");
		jmiLogout.setIcon(new GetImage().getImageIcon("img/icons/logout.png"));
		jmiLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
		jmiLogout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				jmiLogout_ActionPerformed();
			}
		});

		JMenuItem jmideleteAcc=new JMenuItem("Delete Account");
		jmideleteAcc.setIcon(new GetImage().getImageIcon("img/icons/deleteAcc.png"));
		jmideleteAcc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
		jmideleteAcc.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				jmideleteAcc_ActionPerformed();
			}
		});

		JMenuItem jmiExit=new JMenuItem("Exit");
		jmiExit.setIcon(new GetImage().getImageIcon("img/icons/cancel.png"));
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		jmiExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(confirmBeforeExit())
					System.exit(0);
			}
		});

		user.add(jmiLogout);
		user.add(jmideleteAcc);
		user.addSeparator();
		user.add(jmiExit);

		JMenu contact=new JMenu("Contact");
		contact.setMnemonic('C');
		JMenuItem addNew=new JMenuItem("New Contact");
		Image img=new GetImage().getIcon("img/icons/new.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		addNew.setIcon(new ImageIcon(img));
		addNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		addNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Var.refPnlEditCon.btnNew_ActionPerformed(ae);
			}
		});

		img=new GetImage().getIcon("img/icons/edit.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		edit.setIcon(new ImageIcon(img));
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		edit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Var.refPnlEditCon.btnEdit_ActionPerformed(ae);
			}
		});

		img=new GetImage().getIcon("img/icons/delete.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		delete.setIcon(new ImageIcon(img));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
		delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Var.refPnlEditCon.btnDelete_ActionPerformed(ae);
			}
		});

		JMenuItem first=new JMenuItem("First Contact");
		first.setIcon(new GetImage().getImageIcon("img/icons/first.png"));
		first.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					Var.contactNo=0;
					Contact con[]=DataLayer.getContacts("Select * from contacts where LoginID='"+Var.LoginID+"' and PerName='"+Var._names[Var.contactNo]+"'");
					Var.refPnlEditCon.displayContact(con[0]);
					Var.refPnlEditCon.btns_SetEnable(true);
					setJMenuItemEnable(true);
				}
				catch(Exception err){	err=null;	}
			}
		});

		JMenuItem prev=new JMenuItem("Previous Contact");
		prev.setIcon(new GetImage().getImageIcon("img/icons/_prev.png"));
		prev.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				prevContact();
			}
		});

		JMenuItem next=new JMenuItem("Next Contact");
		next.setIcon(new GetImage().getImageIcon("img/icons/_next.png"));
		next.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				nextContact();
			}
		});

		JMenuItem last=new JMenuItem("Last Contact");
		last.setIcon(new GetImage().getImageIcon("img/icons/last.png"));
		last.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					Var.contactNo=Var.TotalContact-1;
					Contact con[]=DataLayer.getContacts("Select * from contacts where LoginID='"+Var.LoginID+"' and PerName='"+Var._names[Var.contactNo]+"'");
					Var.refPnlEditCon.displayContact(con[0]);
					Var.refPnlEditCon.btns_SetEnable(true);
					setJMenuItemEnable(true);
				}
				catch(Exception err){	err=null;	}
			}
		});

		JMenuItem dispRandom=new JMenuItem("Random Contact");
		dispRandom.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Var.mode="random";
				Var.setNames();
			}
		});

		JMenuItem dispAlphabetic=new JMenuItem("Sort Alphabetically");
		dispAlphabetic.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					Var.mode="alphabetic";
					Var.setNames();
				}
				catch(Exception err){	err.printStackTrace();	}
			}
		});

		JMenuItem dispGroup=new JMenuItem("Sort Groupwise");
		dispGroup.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Var.mode="Groupwise";
				Var.setNames();
			}
		});

		contact.add(addNew);
		contact.add(edit);
		contact.add(delete);
		contact.addSeparator();
		contact.add(first);
		contact.add(prev);
		contact.add(next);
		contact.add(last);
		contact.addSeparator();
		contact.add(dispRandom);
		contact.add(dispAlphabetic);
		contact.add(dispGroup);

/*		JMenu option=new JMenu("Options");
		option.setMnemonic('O');

		/*JMenuItem editContact=new JMenuItem("Edit Contacts in Excel file");
		img=new GetImage().getIcon("img/icons/excel.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		editContact.setIcon(new ImageIcon(img));
		editContact.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				editContact_ActionPerformed();
			}
		});
		option.add(editContact);

		option.addSeparator();

		JMenuItem importContact=new JMenuItem("Copy Contacts From Excel file");
		img=new GetImage().getIcon("img/icons/import.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		importContact.setIcon(new ImageIcon(img));
		importContact.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				importContact_ActionPerformed();
			}
		});
		option.add(importContact);

		JMenuItem exportContact=new JMenuItem("Save Contacts to Excel file");
		img=new GetImage().getIcon("img/icons/export.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		exportContact.setIcon(new ImageIcon(img));
		exportContact.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				exportContact_ActionPerformed();
			}
		});
		option.add(exportContact);*/

		mbPhoneBook.add(user);
		mbPhoneBook.add(contact);
		//mbPhoneBook.add(option);
		this.setJMenuBar(mbPhoneBook);
	}

	public void setJMenuItemEnable(boolean flag)
	{
		edit.setEnabled(flag);
		delete.setEnabled(flag);
	}

	private void addPanels()
	{
		pnlContactList LHSpnl=new pnlContactList(Var.UserName);
		LHSpnl.setBounds(0, 0, LHSpnl.getWidth(), LHSpnl.getHeight());
		this.add(LHSpnl);

		pnlEditContact RHSpnl=new pnlEditContact();
		RHSpnl.setBounds(250,0,RHSpnl.getWidth(),RHSpnl.getHeight());
		this.add(RHSpnl);
	}

	private void jmiLogout_ActionPerformed()
	{
		frmLogin obj=new frmLogin();
		obj.show();
		this.dispose();
	}

	protected void jmideleteAcc_ActionPerformed()
	{
		String msg="Do you want to delete your account.\nPlease note that your contact will be deleted\nif you delete your account";
		int Option=JOptionPane.showConfirmDialog(null, msg, "Close", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(Option==JOptionPane.YES_OPTION)
		{
			boolean flag=false;
			flag=DataLayer.deleteContact("Delete from contacts where LoginID='"+Var.LoginID+"'");
			if(flag)
			{
				flag=DataLayer.deleteContact("Delete from registration where LoginID='"+Var.LoginID+"'");
				if(flag)
				{
					JOptionPane.showMessageDialog(null, "Your account has been deleted..");
					System.exit(0);
				}
				else
					JOptionPane.showMessageDialog(null, "Error occured while deleting your account..");
			}
			else
				JOptionPane.showMessageDialog(null, "Error occured while deleting your account..");
		}
	}

	public void nextContact()
	{
		try
		{
			if(Var.contactNo<0)
				Var.contactNo=0;
			else
			{
				if(Var.contactNo>=Var._names.length-1)
					Var.contactNo=Var._names.length-1;
				else
					Var.contactNo+=1;
			}
			Contact con[]=DataLayer.getContacts("Select * from contacts where LoginID='"+Var.LoginID+"' and PerName='"+Var._names[Var.contactNo]+"'");
			Var.refPnlEditCon.displayContact(con[0]);
			Var.refPnlEditCon.btns_SetEnable(true);
			setJMenuItemEnable(true);
		}
		catch(Exception err){	err=null;	}
	}

	public void prevContact()
	{
		try
		{
			Var.contactNo-=1;
			if(Var.contactNo<0)
				Var.contactNo=0;
			Contact con[]=DataLayer.getContacts("Select * from contacts where LoginID='"+Var.LoginID+"' and PerName='"+Var._names[Var.contactNo]+"'");
			Var.refPnlEditCon.displayContact(con[0]);
			Var.refPnlEditCon.btns_SetEnable(true);
			setJMenuItemEnable(true);
		}
		catch(Exception err){	err=null;	}
	}
	private boolean confirmBeforeExit()
	{
		boolean flag=false;

		int Option=JOptionPane.showConfirmDialog(null, "Do you want to Exit", "Close", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(Option==JOptionPane.YES_OPTION)
			flag=true;

		return flag;
	}

	private boolean check_SaveContact(Contact con)
	{
		String group=con.getContactGroup();
		group=group.toLowerCase();
		if(group.equals("friend"))
			con.setContactGroup("Friend");
		else if(group.equals("relative"))
			con.setContactGroup("Relative");
		else if(group.equals("office"))
			con.setContactGroup("Office");
		else
			con.setContactGroup("Other");

		boolean flag=DataLayer.saveContact(con);
		return flag;
	}
}
