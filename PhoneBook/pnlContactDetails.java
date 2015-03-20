
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class pnlContactDetails extends JPanel 
{
	private JTextField jtfName;
	private JTextField jtfGroup;
	private JComboBox jcbGroup;
	private JTextField jtfMobNo;
	private JTextField jtfAltNo;
	private JTextField jtfOffNo;
	private JTextField jtfEmail;
	private JTextArea jtaAddr;
	private JTextArea jtaNote;
	
	private String groups[]={"Friend","Relative","Work","Other"};
	
	JScrollPane jspAddr;
	JScrollPane jspNote;	
	
	public pnlContactDetails()
	{
		Var.refPnlDetails=this;
		this.setLayout(null);
		this.setSize(335, 280);
		this.setBackground(Var.bgColor);
		this.addComponents();
		this.setVisible(true);		
	}
	
	private void addComponents()
	{		
		this.addLabels();
		this.addTextFields();
		this.addTextAreas();
	}
	
	private void addLabels()
	{
		JLabel lblName=new JLabel("Name",JLabel.RIGHT);
		lblName.setFont(new Font("Arial",Font.PLAIN,13));
		lblName.setForeground(Color.BLUE);
		lblName.setBounds(10, 20, 60, 20);
		this.add(lblName);
		
		JLabel lblGroup=new JLabel("Group",JLabel.RIGHT);
		lblGroup.setFont(new Font("Arial",Font.PLAIN,13));
		lblGroup.setForeground(Color.BLUE);
		lblGroup.setBounds(10, 45, 60, 20);		
		this.add(lblGroup);
		
		JLabel lblMobNo=new JLabel("Mobile No",JLabel.RIGHT);
		lblMobNo.setFont(new Font("Arial",Font.PLAIN,13));
		lblMobNo.setForeground(Color.BLUE);
		lblMobNo.setBounds(10, 70, 60, 20);		
		this.add(lblMobNo);
		
		JLabel lblAltNo=new JLabel("Alt. No",JLabel.RIGHT);
		lblAltNo.setFont(new Font("Arial",Font.PLAIN,13));
		lblAltNo.setForeground(Color.BLUE);
		lblAltNo.setBounds(10, 95, 60, 20);
		this.add(lblAltNo);
		
		JLabel lblOffNo=new JLabel("Office No",JLabel.RIGHT);
		lblOffNo.setFont(new Font("Arial",Font.PLAIN,13));
		lblOffNo.setForeground(Color.BLUE);
		lblOffNo.setBounds(10, 120, 60, 20);
		this.add(lblOffNo);
		
		JLabel lblEmail=new JLabel("Email ID",JLabel.RIGHT);
		lblEmail.setFont(new Font("Arial",Font.PLAIN,13));
		lblEmail.setForeground(Color.BLUE);
		lblEmail.setBounds(10, 145, 60, 20);
		this.add(lblEmail);
		
		JLabel lblAddr=new JLabel("Address",JLabel.RIGHT);
		lblAddr.setFont(new Font("Arial",Font.PLAIN,13));
		lblAddr.setForeground(Color.BLUE);
		lblAddr.setBounds(10, 170, 60, 20);
		this.add(lblAddr);
		
		JLabel lblNote=new JLabel("Note",JLabel.RIGHT);
		lblNote.setFont(new Font("Arial",Font.PLAIN,13));
		lblNote.setForeground(Color.BLUE);
		lblNote.setBounds(10, 222, 60, 20);
		this.add(lblNote);
	}

	private void addTextFields()
	{
		jtfName=new JTextField("Contact Name");
		jtfName.setBackground(Var.bgColor);
		jtfName.setBorder(null);
		jtfName.setBounds(80,20,245, 20);
		//jtfName.addKeyListener(NumericOnly);
		this.add(jtfName);
		
		jtfGroup=new JTextField("Contact Group");
		jtfGroup.setBackground(Var.bgColor);
		jtfGroup.setBorder(null);		
		jtfGroup.setBounds(80,45,245, 20);
		jtfGroup.setEditable(false);
		this.add(jtfGroup);
		
		jcbGroup=new JComboBox(this.groups);
		jcbGroup.setBackground(Var.bgColor);		
		jcbGroup.setBorder(null);
		jcbGroup.setEditable(false);
		jcbGroup.setVisible(false);
		jcbGroup.setBounds(80,45,245, 20);		
		this.add(jcbGroup);
		
		jtfMobNo=new JTextField("Mobile Number");
		jtfMobNo.setBackground(Var.bgColor);
		jtfMobNo.setBorder(null);
		jtfMobNo.setBounds(80,70,245, 20);		
		this.add(jtfMobNo);
		
		jtfAltNo=new JTextField("Alternative Number");
		jtfAltNo.setBackground(Var.bgColor);
		jtfAltNo.setBorder(null);
		jtfAltNo.setBounds(80,95,245, 20);
		this.add(jtfAltNo);
		
		jtfOffNo=new JTextField("Office/Work Number");
		jtfOffNo.setBackground(Var.bgColor);
		jtfOffNo.setBorder(null);
		jtfOffNo.setBounds(80,120,245, 20);
		this.add(jtfOffNo);
		
		jtfEmail=new JTextField("Email ID");
		jtfEmail.setBackground(Var.bgColor);
		jtfEmail.setBorder(null);
		jtfEmail.setBounds(80,145,245, 20);		
		this.add(jtfEmail);
	}

	private void addTextAreas()
	{
		jtaAddr=new JTextArea("Person address");
		jtaAddr.setBackground(Var.bgColor);			
		jtaAddr.setLineWrap(true);
			
		jspAddr=new JScrollPane(jtaAddr);
		jspAddr.setBounds(80, 170, 245, 47);
		jspAddr.setBorder(null);
		this.add(jspAddr);
		
		jtaNote=new JTextArea("Contact Note");
		jtaNote.setBackground(Var.bgColor);		
		jtaNote.setLineWrap(true);
		
		jspNote=new JScrollPane(jtaNote);		
		jspNote.setBorder(null);
		jspNote.setBounds(80, 222, 245, 47);
		this.add(jspNote);
	}
	
	public void setAllFieldEnable(boolean flag)
	{
		javax.swing.border.LineBorder lb=new javax.swing.border.LineBorder(Color.LIGHT_GRAY);
		if(flag)
		{
			jtfName.setBorder(lb);
			//jcbGroup.setBorder(lb);
			this.remove(jtfGroup);
			jcbGroup.setVisible(true);
			jtfMobNo.setBorder(lb);
			jtfAltNo.setBorder(lb);
			jtfOffNo.setBorder(lb);
			jtfEmail.setBorder(lb);
			jspAddr.setBorder(lb);
			jspNote.setBorder(lb);
		}
		else
		{
			jtfName.setBorder(null);
			jtfGroup.setBorder(null);
			jtfMobNo.setBorder(null);
			jtfAltNo.setBorder(null);
			jtfOffNo.setBorder(null);
			jtfEmail.setBorder(null);
			jspAddr.setBorder(null);
			jspNote.setBorder(null);
		}
		jtfName.setEditable(flag);
		jtfMobNo.setEditable(flag);
		jtfAltNo.setEditable(flag);
		jtfOffNo.setEditable(flag);
		jtfEmail.setEditable(flag);
		jtaAddr.setEditable(flag);
		jtaNote.setEditable(flag);
	}
	
	public void clearAll()
	{
		Contact temp=new Contact("","","","","","","","","",null);
		this.setDetails(temp);
	}

	public void setPersonName(String name){	jtfName.setText(name);		}
	public void setContactGroup(String group)
	{
		if(jcbGroup.isVisible())
			jcbGroup.setSelectedItem(group);
		else
			jtfGroup.setText(group);
	}
	public void setMobNo(String mobNo){	jtfMobNo.setText(mobNo);	}
	public void setAltNo(String altNo){	jtfAltNo.setText(altNo);	}
	public void setOffNo(String offNo){	jtfOffNo.setText(offNo);	}
	public void setEmail(String email){	jtfEmail.setText(email);	}
	public void setAddr(String addr){	jtaAddr.setText(addr);		}
	public void setNote(String note){	jtaNote.setText(note);		}
	public void setDetails(Contact con)
	{
		this.setPersonName(con.getName()==null?"":con.getName());
		this.setContactGroup(con.getContactGroup()==null?"Friend":con.getContactGroup());
		this.setMobNo(con.getMobNo()==null?"":con.getMobNo());
		this.setAltNo(con.getAltNo()==null?"":con.getAltNo());
		this.setOffNo(con.getOffNo()==null?"":con.getOffNo());
		this.setEmail(con.getEmail()==null?"":con.getEmail());
		this.setAddr(con.getAddr()==null?"":con.getAddr());
		this.setNote(con.getNote()==null?"":con.getNote());
	}
	
	public String getPersonName(){	return jtfName.getText();	}
	public String getContactGroup()
	{
		if(jcbGroup.isVisible())
			return jcbGroup.getSelectedItem().toString();
		else
			return jtfGroup.getText();
	}
	public String getMobNo(){	return jtfMobNo.getText();	}
	public String getAltNo(){	return jtfAltNo.getText();	}
	public String getOffNo(){	return jtfOffNo.getText();	}
	public String getEmail(){	return jtfEmail.getText();	}
	public String getAddr(){	return jtaAddr.getText();	}
	public String getNote(){	return jtaNote.getText();	}
	public Contact getDetails()
	{
		Contact con=new Contact();	
		con.setContact(getPersonName(), getContactGroup(), getAltNo(), getMobNo(), getOffNo(), getEmail(), getAddr(), getNote(), null, null);
		return con;
	}
}