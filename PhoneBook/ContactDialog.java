import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

public class ContactDialog extends JDialog
{
	private pnlContactDetails pnlDetails;
	private pnlContactImage pnlImage;

	private JButton btnBrowse;
	private JButton btnCancel;
	private JButton btnSave_Update;
	private JTextField jtfPath;

	private boolean isNew=true;
	private boolean isPhotoChanged=false;

	private Image img;

	public static ContactDialog ref;

	public ContactDialog(boolean isNew)
	{
		ref=this;

		this.isNew=isNew;
		this.setModal(true);
		this.setSize(352, 572);
		this.setResizable(false);
		this.setLocation(new CenterLocation(this.getSize()).getLocation());
		this.getContentPane().setBackground(Var.bgColor);
		this.setLayout(null);
		this.addComponents();
		if(isNew)
			this.displayContact(new Contact("","","","","","","","","photo_not_available.jpg",null));
		//this.setVisible(true);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				btnCancel_ActionPerformed(null);
			}
		});
	}

	private void addComponents()
	{
		pnlImage=new pnlContactImage();
		pnlImage.setSize(335, 215);
		pnlImage.setBounds(5, 0, 335, 215);
		pnlImage.setBorder(Var.pnlImageBorder);
		this.add(pnlImage);

		jtfPath=new JTextField("Makarand");
		jtfPath.setBackground(Var.bgColor);
		jtfPath.setEditable(false);
		jtfPath.setBounds(20,185,270,20);
		pnlImage.add(jtfPath);

		pnlDetails=new pnlContactDetails();
		pnlDetails.setBounds(5, 215, 335, 280);
		pnlDetails.setBorder(Var.detailsBorder);
		this.add(pnlDetails);
		pnlDetails.setAllFieldEnable(true);

		this.addButtons();
	}

	private void addButtons()
	{
		btnBrowse=new JButton("..");
		btnBrowse.setSize(20,20);
		btnBrowse.setBounds(295,185,20,20);
		btnBrowse.setToolTipText("Browse contact image");
		btnBrowse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnBrowse_ActionPerformed(ae);
			}
		});
		pnlImage.add(btnBrowse);

		String caption="Save";
		if(isNew)
			img=new GetImage().getIcon("img/icons/save.png");
		else
		{
			img=new GetImage().getIcon("img/icons/update.png");
			caption="Update";
		}

		img=img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		btnSave_Update=new JButton(caption);
		if(isNew)
		{
			btnSave_Update.setToolTipText("Save contact");
			btnSave_Update.setMnemonic('S');
		}
		else
		{
			btnSave_Update.setToolTipText("Update contact");
			btnSave_Update.setMnemonic('U');
		}
		btnSave_Update.setIcon(new ImageIcon(img));
		btnSave_Update.setSize(40, 40);
		btnSave_Update.setBounds(75,500,100,30);
		btnSave_Update.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(isNew)
					btnSave_ActionPerformed(ae);
				else
					btnUpdate_ActionPerformed(ae);
			}
		});
		this.add(btnSave_Update);

		img=new GetImage().getIcon("img/icons/close.png");
		img=img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		btnCancel=new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(img));
		btnCancel.setToolTipText("Cancel");
		btnCancel.setSize(40, 40);
		btnCancel.setBounds(180,500,100,30);
		btnCancel.setMnemonic('C');
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				btnCancel_ActionPerformed(ae);
			}
		});
		this.add(btnCancel);
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
				this.jtfPath.setText(con.getFileName());
			}
			else
				throw new Exception("can not load the contact");
		}
		catch(Exception err)
		{
			JOptionPane.showMessageDialog(ref,"dia"+ err.getMessage());
		}
	}

	private Image getImageFromPath(String imgPath)
	{
		img=null;
		try
		{
			java.io.InputStream in=new java.io.FileInputStream(imgPath);
			img=new ImageIcon(ImageIO.read(in)).getImage();
			if(in!=null)
				in.close();
		}
		catch(Exception err)
		{
			JOptionPane.showMessageDialog(ref, "Can not load image");
		}
		return img;
	}

	private void btnBrowse_ActionPerformed(ActionEvent ae)
	{
		JFileChooser open=new JFileChooser();
		FileFilter filter = new ExtFileFilter("Bitmap Image", new String[] { "BMP" });
		open.addChoosableFileFilter(filter);
		filter = new ExtFileFilter("GIF Image", new String[] { "GIF" });
		open.addChoosableFileFilter(filter);
		filter = new ExtFileFilter("JPEG Image", new String[] { "JPG", "JPEG" });
		open.setFileFilter(filter);

		int dResult=open.showOpenDialog(ref);
		if(dResult==JFileChooser.APPROVE_OPTION)
		{
			String imgPath=open.getSelectedFile().getPath();
			String fileName=open.getSelectedFile().getName();

			pnlCropImage obj=new pnlCropImage(fileName,this.getImageFromPath(imgPath));
			obj.show();
			if(Var.temp.equals("temp"))
			{
				pnlImage.setPhoto(this.getImageFromPath(imgPath));
				this.jtfPath.setText(imgPath);
				pnlImage.setPhotoTooltipText(fileName);
			}
			isPhotoChanged=true;
		}
	}

	private void btnSave_ActionPerformed(ActionEvent ae)
	{
		Contact con=pnlDetails.getDetails();
		con.setFileName(pnlImage.getPhotoTooltipText());
		con.setPhoto(pnlImage.getPhoto());
		if(con.isValidContact())
		{
			Var.contactID=this.getRandomString();
			String values="'"+Var.LoginID+"','"+Var.contactID+"','"+con.getName()+"','"+
							con.getContactGroup()+"','"+con.getAltNo()+"','"+con.getMobNo()+"','"+
							con.getOffNo()+"','"+con.getEmail()+"','"+con.getAddr()+"','"+
							con.getNote()+"','"+con.getFileName()+"',?";
			String query="Insert into contacts values("+values+")";
			boolean flag=DataLayer.saveContact(query, Var.temp.equals("temp")?jtfPath.getText():Var.temp);
			if(flag)
			{
				javax.swing.tree.DefaultMutableTreeNode node=new javax.swing.tree.DefaultMutableTreeNode(con.getName());
				Var.refPnlTreeView.addNode(con.getContactGroup(), node);
				Var.refPnlTreeView.setlblTotContacts(Var.TotalContact+1);
				Var.refPnlTreeView.sortContacts(con.getContactGroup());
				JOptionPane.showMessageDialog(ref, "Contact saved....");
				Var.refPnlEditCon.displayContact(con);
				Var.setNames();

				if(!Var.temp.equals("temp"))
				{
					try
					{
						java.io.File f=new java.io.File("tempImg.jpg");
						f.delete();
						Var.temp="temp";
					}
					catch(Exception err)
					{
						err.printStackTrace();
					}
				}

				this.dispose();
			}
			else
				JOptionPane.showMessageDialog(ref, "Contact not saved....");
		}
		else
			JOptionPane.showMessageDialog(ref, "Not valid contact");
	}

	public static String getRandomString()
	{
		final int Str_Length = 10;
	    StringBuffer str = new StringBuffer();
	    for (int x=0;x<Str_Length;x++)
	    {
		    str.append((char)((int)(Math.random()*26)+97));
	    }
	    return str.toString();
	}

	private void btnUpdate_ActionPerformed(ActionEvent ae)
	{
		int i=0;
		String d[]=new String[9];
		Contact con=pnlDetails.getDetails();
		con.setFileName(pnlImage.getPhotoTooltipText());
		con.setPhoto(pnlImage.getPhoto());

		d[i++]=con.getName();
		d[i++]=con.getContactGroup();
		d[i++]=con.getAltNo();
		d[i++]=con.getMobNo();
		d[i++]=con.getOffNo();
		d[i++]=con.getEmail();
		d[i++]=con.getAddr();
		d[i++]=con.getNote();
		d[i++]=con.getFileName();

		i=0;

		if(con.isValidContact())
		{
			String query="update contacts set PerName='"+d[i++]+"',GroupName='"+
							d[i++]+"',AltNo='"+d[i++]+"',MobNo='"+
							d[i++]+"',OffNo='"+d[i++]+"',Email='"+
							d[i++]+"',Addr='"+d[i++]+"',SpNote='"+
							d[i++]+"',FileName='"+d[i]+"' where LoginID='"+
							Var.LoginID+"' and ContactID='"+Var.contactID+"'";
			boolean flag=DataLayer.updateContact(query, Var.temp.equals("temp")?jtfPath.getText():Var.temp, isPhotoChanged);
			if(flag)
			{
				JOptionPane.showMessageDialog(ref, "Contact updated....");
				Var.refPnlEditCon.displayContact(con);
				Var.refPnlTreeView.updateNodeName(con.getName());
				Var.setNames();

				if(!Var.temp.equals("temp"))
				{
					try
					{
						java.io.File f=new java.io.File("tempImg.jpg");
						f.delete();
						Var.temp="temp";
					}
					catch(Exception err)
					{
						err.printStackTrace();
					}
				}

				this.dispose();
			}
			else
				JOptionPane.showMessageDialog(ref, "Can not update contact....");
		}
	}

	public void setImagePath(String path)
	{
		jtfPath.setText(path);
	}

	private void btnCancel_ActionPerformed(ActionEvent ae)
	{
		this.dispose();
	}
}
