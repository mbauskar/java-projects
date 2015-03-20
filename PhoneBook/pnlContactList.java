
import java.awt.*;
import javax.swing.*;

public class pnlContactList extends JPanel
{
	static pnlContactList ref;
	private JLabel lblUsrName;
	private String usrName;
	private JTabbedPane tabPane;
	
	public pnlContactList(String usrName)
	{
		ref=this;
		this.usrName=usrName;
		this.setLayout(null);
		this.setSize(250,520);
		this.addComponent();
		this.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY));
		this.setVisible(true);
	}
	
	private void addComponent()
	{
		lblUsrName=new JLabel("Hi "+this.usrName+" !!",JLabel.CENTER);
		lblUsrName.setFont(new Font("Arial",Font.BOLD,13));
		lblUsrName.setBounds(0, 0, 250, 15);
		this.add(lblUsrName);
		
		tabPane=new JTabbedPane();
		tabPane.setBounds(0, 15,250,505);
		this.add(tabPane);
		
		pnlContactTreeView pnlList=new pnlContactTreeView();
		tabPane.addTab("All Contacts", pnlList);
		
		pnlSearchContact pnlSearch=new pnlSearchContact();
		tabPane.add("Search Contacts",pnlSearch);
	}
}
