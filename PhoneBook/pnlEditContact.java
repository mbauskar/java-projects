
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class pnlEditContact extends JPanel
{	
	private pnlContactDetails pnlDetails;
	private pnlContactImage pnlImage;
	private JButton btnEdit;
	private JButton	btnDelete;
	
	public pnlEditContact()
	{
		Var.refPnlEditCon=this;
		
		this.setSize(345,520);
		this.setLayout(null);
		this.setBackground(Var.bgColor);		
		this.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY));
		this.addComponents();		
		this.setVisible(true);
	}
	
	public void addComponents()
	{
		this.addButtons();		
		this.addPanels();		
	}
	
	private void addButtons()
	{
		Image img=new GetImage().getIcon("img/icons/prev.png");
		img=img.getScaledInstance(40, 40,Image.SCALE_DEFAULT);
		JButton btnPrev=new JButton(new ImageIcon(img));
		btnPrev.setToolTipText("Previous Contact");
		btnPrev.setBounds(65,10,40,40);
		btnPrev.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnPrev_ActionPerformed(ae);
			}
		});
		this.add(btnPrev);
		
		img=new GetImage().getIcon("img/icons/new.png");
		img=img.getScaledInstance(40, 40,Image.SCALE_DEFAULT);
		JButton btnNew=new JButton(new ImageIcon(img));		
		btnNew.setToolTipText("New Contact");
		btnNew.setBounds(110,10,40,40);
		btnNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnNew_ActionPerformed(ae);
			}
		});
		this.add(btnNew);		
		
		img=new GetImage().getIcon("img/icons/edit.png");
		img=img.getScaledInstance(40, 40,Image.SCALE_DEFAULT);
		btnEdit=new JButton(new ImageIcon(img));		
		btnEdit.setToolTipText("Edit Contact");
		btnEdit.setBounds(155,10,40,40);
		btnEdit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnEdit_ActionPerformed(ae);
			}
		});
		this.add(btnEdit);
		
		img=new GetImage().getIcon("img/icons/delete.png");
		img=img.getScaledInstance(40, 40,Image.SCALE_DEFAULT);
		btnDelete=new JButton(new ImageIcon(img));		
		btnDelete.setToolTipText("Delete Contact");
		btnDelete.setBounds(200,10,40,40);
		btnDelete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnDelete_ActionPerformed(ae);
			}
		});
		this.add(btnDelete);
		
		img=new GetImage().getIcon("img/icons/next.png");
		img=img.getScaledInstance(40, 40,Image.SCALE_DEFAULT);
		JButton btnNext=new JButton(new ImageIcon(img));		
		btnNext.setToolTipText("Next Contact");
		btnNext.setBounds(245,10,40,40);
		btnNext.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnNext_ActionPerformed(ae);
			}
		});
		this.add(btnNext);
		
		this.btns_SetEnable(false);
		Var.refFrmPhBk.setJMenuItemEnable(false);
	}	

	private void addPanels()
	{		
		pnlImage=new pnlContactImage();		
		pnlImage.setBounds(5, 50, 335, 185);
		pnlImage.setBorder(Var.pnlImageBorder);		
		this.add(pnlImage);
		
		pnlDetails=new pnlContactDetails();		
		pnlDetails.setBounds(5, 235, 335, 280);
		pnlDetails.setBorder(Var.detailsBorder);
		pnlDetails.setAllFieldEnable(false);
		this.add(pnlDetails);
	}
		
	public void btnPrev_ActionPerformed(ActionEvent ae)
	{
		Var.refFrmPhBk.prevContact();
	}
	
	public void btnNext_ActionPerformed(ActionEvent ae)
	{
		Var.refFrmPhBk.nextContact();
	}
	
	public void btnNew_ActionPerformed(ActionEvent ae)
	{
		ContactDialog dialog=new ContactDialog(true);		
		dialog.show();
	}
	
	public void btnEdit_ActionPerformed(ActionEvent ae)
	{
		ContactDialog dialog=new ContactDialog(false);
		Contact con=pnlDetails.getDetails();
		con.setPhoto(pnlImage.getPhoto());
		con.setFileName(pnlImage.getPhotoTooltipText());
		dialog.displayContact(con);
		dialog.show();
	}
	
	public void btnDelete_ActionPerformed(ActionEvent ae) 
	{		
		String query="Delete from contacts where LoginID='"+Var.LoginID+"' and ContactID='"+Var.contactID+"'";
		boolean flag=DataLayer.deleteContact(query);
		if(flag)
		{
			this.displayContact(new Contact());
			Var.refPnlTreeView.removeNode(Var.refPnlTreeView.getTreePath());
			Var.refPnlTreeView.setlblTotContacts(Var.TotalContact-1);
			JOptionPane.showMessageDialog(null, "Contact deleted...");
			this.btns_SetEnable(false);
			Var.refFrmPhBk.setJMenuItemEnable(false);
			Var.setNames();
		}
		else
			JOptionPane.showMessageDialog(null, "Error can not delete the contact...");
	}
	
	public void btns_SetEnable(boolean flag)
	{
		btnEdit.setEnabled(flag);
		btnDelete.setEnabled(flag);		
	}
	
	public void displayContact(Contact con)
	{
		try
		{
			if(con!=null)
			{
				pnlDetails.setDetails(con);
				pnlImage.setPhoto(con.getPhoto());
				pnlImage.setPhotoTooltipText(con.getFileName());
			}
			else
				throw new Exception("can not load the contact");
		}
		catch(Exception err)
		{
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
	}	
}
