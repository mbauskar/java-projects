
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;

public class pnlContactTreeView extends JPanel
{
	private JTree jtContacts;
	private TreePath treePath;
	private DefaultMutableTreeNode nodeRoot;
	private DefaultMutableTreeNode nodeFriends;
	private DefaultMutableTreeNode nodeRelative;
	private DefaultMutableTreeNode nodeWork;
	private DefaultMutableTreeNode nodeOther;
	
	private JLabel lblTotContacts;	
	//static int countContacts=0;
	
	public pnlContactTreeView()
	{
		Var.refPnlTreeView=this;
		
		this.setLayout(null);
		this.setSize(250, 480);
		this.addComponents();
		this.setVisible(true);
	}
	
	private void addComponents()
	{
		Image img=new GetImage().getIcon("img/contacts.jpg");
		img=img.getScaledInstance(250, 180,Image.SCALE_DEFAULT);
		JLabel lblImg=new JLabel(new ImageIcon(img));
		lblImg.setBounds(0, 0, 250, 175);
		this.add(lblImg);
		
		nodeRoot=new DefaultMutableTreeNode("Contacts");
		jtContacts=new JTree(nodeRoot);
		JScrollPane jsp=new JScrollPane(jtContacts);
		jsp.setBounds(5, 180, 235, 280);
		jtContacts.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				jtContacts_mouseClicked(me);
			}
		});
		this.add(jsp);
		
		this.addGroupNodes();
		this.expandAll();
		Var.TotalContact=DataLayer.countContacts("Select * from contacts where LoginID='"+Var.LoginID+"'");
		
		lblTotContacts=new JLabel("Total : "+Var.TotalContact+" contacts",JLabel.CENTER);
		lblTotContacts.setVerticalAlignment(JLabel.CENTER);
		lblTotContacts.setForeground(Color.RED);
		lblTotContacts.setFont(new Font("Arial",Font.PLAIN,11));
		lblTotContacts.setBounds(0, 460,250, 20);		
		this.add(lblTotContacts);
	}
	
	private void addGroupNodes()
	{
		nodeFriends=new DefaultMutableTreeNode("Friends");
		nodeRelative=new DefaultMutableTreeNode("Relatives");
		nodeWork=new DefaultMutableTreeNode("Office\\work");
		nodeOther=new DefaultMutableTreeNode("Other");
		nodeRoot.add(nodeFriends);
		this.addContactsToGroup("Friend");
		nodeRoot.add(nodeRelative);
		this.addContactsToGroup("Relative");
		nodeRoot.add(nodeWork);
		this.addContactsToGroup("Work");
		nodeRoot.add(nodeOther);
		this.addContactsToGroup("Other");
	}
	
	private void expandAll()
	{
		int row = 0;
	    while (row < jtContacts.getRowCount())
	    {
	    	jtContacts.expandRow(row);
	      	row++;
		}	
	}
	
	public void addContactsToGroup(String grpName)
	{
		String names[]=DataLayer.getNames("select PerName from contacts where GroupName='"+grpName+"' and LoginID='"+Var.LoginID+"'");
		if(names!=null)
		{
			java.util.Arrays.sort(names);
			for(int i=0;i<names.length;i++)
			{
				DefaultMutableTreeNode node=new DefaultMutableTreeNode(names[i]);
				this.addNode(grpName,node);
			}
		}
	}
	
	public void addNode(String grpName,DefaultMutableTreeNode node)
	{
		DefaultTreeModel model = (DefaultTreeModel)jtContacts.getModel();
		if(grpName=="Friend")
			model.insertNodeInto(node,nodeFriends,nodeFriends.getChildCount());
		else if(grpName=="Relative")
			model.insertNodeInto(node,nodeRelative,nodeRelative.getChildCount());
		else if(grpName=="Work")
			model.insertNodeInto(node,nodeWork,nodeWork.getChildCount());
		else
			model.insertNodeInto(node,nodeOther,nodeOther.getChildCount());
	}
	
	public void removeNode(TreePath tp)
	{
		DefaultTreeModel model = (DefaultTreeModel)jtContacts.getModel();
		MutableTreeNode node = (MutableTreeNode)tp.getLastPathComponent();
		model.removeNodeFromParent(node);
	}
	
	public void changeGroup(String newGroupName)
	{
		TreePath tp=jtContacts.getSelectionPath();

		String Name=tp.getLastPathComponent().toString();
		this.removeNode(tp);

		DefaultMutableTreeNode node=new DefaultMutableTreeNode(Name);
		this.addNode(newGroupName,node);
	}
	
	@SuppressWarnings("unchecked")
	public void sortContacts(String GroupName)
	{
		int count=0;
		java.util.Enumeration<DefaultMutableTreeNode> e=null;
		DefaultMutableTreeNode node=null;

		if(GroupName=="Friend")
			node=nodeFriends;
		else if(GroupName=="Relative")
			node=nodeRelative;
		else if(GroupName=="Work")
			node=nodeWork;
		else
			node=nodeOther;

		e=node.children();
		count=node.getChildCount();

		String names[]=new String[count];
		int i=0;

		for (;e.hasMoreElements();)
			names[i++]=e.nextElement().toString();

		if(names!=null)
			java.util.Arrays.sort(names);

		DefaultTreeModel model = (DefaultTreeModel)jtContacts.getModel();
		model.reload();
		node.removeAllChildren();

		if(names!=null)
		{
			for(i=0;i<names.length;i++)
			{
				DefaultMutableTreeNode n=new DefaultMutableTreeNode(names[i]);
				this.addNode(GroupName,n);
			}
		}
		model.reload();
		this.expandAll();
	}
	
	public void setlblTotContacts(int total)
	{
		Var.TotalContact=total;
		lblTotContacts.setText("Total : "+total+" contacts");
	}
	
	public TreePath getTreePath()
	{
		return treePath;
	}
	
	private void jtContacts_mouseClicked(MouseEvent me)
	{
		try
		{
			treePath=jtContacts.getSelectionPath();			
			String perName=jtContacts.getSelectionPath().getLastPathComponent().toString();			
			Contact contact[]=DataLayer.getContacts("select * from contacts where PerName='"+perName+"' and LoginID='"+Var.LoginID+"'");			
			//	Display contact details on another panel
			if(contact!=null&&contact[0]!=null)
			{				
				Var.refPnlEditCon.btns_SetEnable(true);
				Var.refFrmPhBk.setJMenuItemEnable(true);
				Var.refPnlEditCon.displayContact(contact[0]);
			}
			else
				throw new Exception("Error");
		}
		catch(Exception err)
		{
			Var.refPnlEditCon.btns_SetEnable(false);
			Var.refFrmPhBk.setJMenuItemEnable(false);
			Contact c=new Contact();
			Var.refPnlEditCon.displayContact(c);			
		}
	}
	
	public void updateNodeName(String name)
	{
		try
		{
			this.removeNode(getTreePath());						
			DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
			this.addNode(Var.refPnlDetails.getContactGroup(), node);
			this.sortContacts(Var.refPnlDetails.getContactGroup());			
		}
		catch(Exception err){	err=null;	}		
	}
}
