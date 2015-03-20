
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

public class pnlSearchContact extends JPanel 
{
	private JTextField jtfSearchQuery;
	private JButton btnSearch;
	private JCheckBox jcbAutoSearch;
	private JList jlSearchResult;
	private JScrollPane jspSearchResult;
	private JLabel lblTotContacts;
	
	DefaultListModel listModel;	
	
	public pnlSearchContact()
	{
		Var.refPnlSearch=this;
		
		this.setLayout(null);
		this.setSize(250, 480);
		this.addComponents();
		this.setVisible(true);
	}

	private void addComponents() 
	{	
		Image img=new GetImage().getIcon("img/search_contacts.jpg");
		img=img.getScaledInstance(250, 180,Image.SCALE_DEFAULT);
		JLabel lblImg=new JLabel(new ImageIcon(img));
		lblImg.setBounds(0, 0, 250, 175);
		this.add(lblImg);
		
		jtfSearchQuery=new JTextField("");
		jtfSearchQuery.setBounds(5, 180, 210, 20);
		jtfSearchQuery.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				if(jcbAutoSearch.isSelected())
				{
					String PerName=getSearchString();
					searchContact(PerName);
				}
			}

			public void removeUpdate(DocumentEvent e)
			{
				if(jcbAutoSearch.isSelected())
				{
					String PerName=getSearchString();
					searchContact(PerName);
				}
			}
			public void insertUpdate(DocumentEvent e)
			{
				if(jcbAutoSearch.isSelected())
				{
					String PerName=getSearchString();
					searchContact(PerName);
				}
			}
		});
		this.add(jtfSearchQuery);
		
		img=new GetImage().getIcon("img/icons/search.png");
		img=img.getScaledInstance(16, 16,Image.SCALE_DEFAULT);
		btnSearch=new JButton(new ImageIcon(img));
		btnSearch.setToolTipText("Search Contact");
		btnSearch.setBounds(220, 180, 20, 20);
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				searchContact(getSearchString());
			}			
		});
		this.add(btnSearch);
		
		jcbAutoSearch=new JCheckBox("Automatic Search",false);
		jcbAutoSearch.setToolTipText("Search contacts without clicking 'Search' button");
		jcbAutoSearch.setBounds(1, 200, 130, 20);
		this.add(jcbAutoSearch);
		
		listModel=new DefaultListModel();
		jlSearchResult=new JList(listModel);
		jspSearchResult=new JScrollPane(jlSearchResult);
		jspSearchResult.setBounds(5,225,235,235);
		jspSearchResult.setVisible(true);
		jlSearchResult.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse)
			{				
				jlSearchResult_ValueChanged();				
			}
		});
		this.add(jspSearchResult);
		
		lblTotContacts=new JLabel("0 Matches found",JLabel.CENTER);
		lblTotContacts.setVerticalAlignment(JLabel.CENTER);
		lblTotContacts.setForeground(Color.RED);
		lblTotContacts.setFont(new Font("Arial",Font.PLAIN,11));
		lblTotContacts.setBounds(0, 460,250, 20);		
		this.add(lblTotContacts);
	}

	private void setSearchResult(int count)
	{
		lblTotContacts.setText(count+" Match found");
	}
	
	private String getSearchString() 
	{		
		return jtfSearchQuery.getText();
	}

	private void searchContact(String perName) 
	{		
		listModel.clear();

		if(perName.equals(""))
		{
			this.setSearchResult(0);
			return;
		}
		String loginID=Var.LoginID;
		try
		{
			String Names[]=DataLayer.getNames("select PerName from contacts where LoginID='"+loginID+"' and PerName like '%"+perName+"%'");
			if(Names!=null)
			{
				this.addItemsToJList(Names);
				this.setSearchResult(Names.length);
			}
		}
		catch(Exception ex){	ex=null;	}
	}

	private void addItemsToJList(String[] names) 
	{
		listModel.clear();
		for(int i=0;i<names.length;i++)
			listModel.add(i,names[i]);
	}
	
	private void jlSearchResult_ValueChanged()
	{
		try
		{
			String name=jlSearchResult.getSelectedValue().toString();
			Contact con[]=DataLayer.getContacts("Select * from contacts where PerName='"+name+"' and LoginID='"+Var.LoginID+"'");
			if(con[0]!=null)
			{
				Var.refPnlDetails.setDetails(con[0]);
				Var.refPnlImg.setPhoto(con[0].getPhoto());
				Var.refPnlImg.setPhotoTooltipText(con[0].getFileName());
			}
		}
		catch(Exception err)
		{					
			Contact c=new Contact();
			Var.refPnlDetails.setDetails(c);
			Var.refPnlImg.setPhoto(null);
			Var.refPnlImg.setPhotoTooltipText(c.getFileName());
		}
	}
}
