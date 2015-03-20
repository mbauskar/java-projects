
public class Var 
{
	static int contactNo=0;
	static int TotalContact;
	
	static String LoginID;
	static String UserName;
	
	static String mode="random";
	static String temp="temp";
	static String contactID;
	static String _names[];
	
	public static void setNames()
	{
		if(mode.equals("random"))
			_names=DataLayer.getNames("Select PerName from contacts where LoginID='"+LoginID+"'");
		else if(mode.equals("alphabetic"))
		{
			_names=DataLayer.getNames("Select PerName from contacts where LoginID='"+LoginID+"'");
			java.util.Arrays.sort(_names);
		}
		else
		{
			_names=sortContact_Groupwise();
		}
	}
	
	private static String[] sortContact_Groupwise()
	{
		String names[]=null;
		int c=0;
		try
		{
			c=DataLayer.countContacts("Select * from contacts where LoginID='"+Var.LoginID+"'");
			names=new String[c];
			c=0;
			
			String friends[]=DataLayer.getNames("Select PerName from contacts where LoginID='"+Var.LoginID+"' and GroupName='Friend'");
			String relatives[]=DataLayer.getNames("Select PerName from contacts where LoginID='"+Var.LoginID+"' and GroupName='Relative'");
			String work[]=DataLayer.getNames("Select PerName from contacts where LoginID='"+Var.LoginID+"' and GroupName='Work'");
			String other[]=DataLayer.getNames("Select PerName from contacts where LoginID='"+Var.LoginID+"' and GroupName='Other'");
			if(friends!=null)
			{				
				java.util.Arrays.sort(friends);
				for(int i=0;i<friends.length;i++)
					names[c++]=friends[i];				
			}			
			if(relatives!=null)
			{
				java.util.Arrays.sort(relatives);
				for(int i=0;i<relatives.length;i++)
					names[c++]=relatives[i];				
			}
			if(work!=null)
			{
				java.util.Arrays.sort(work);
				for(int i=0;i<work.length;i++)
					names[c++]=work[i];				
			}
			if(other!=null)
			{
				java.util.Arrays.sort(other);
				for(int i=0;i<other.length;i++)
					names[c++]=other[i];				
			}			
		}
		catch(Exception err)
		{
			err.printStackTrace();
			return null;
		}
		return names;
	}
	
	static frmPhoneBook refFrmPhBk;
	static pnlContactDetails refPnlDetails;
	static pnlContactImage refPnlImg;
	static pnlContactList refConList;
	static pnlContactTreeView refPnlTreeView;
	static pnlEditContact refPnlEditCon;
	static pnlSearchContact refPnlSearch;
	
	static java.awt.Color bgColor=new java.awt.Color(248,248,237);
	static javax.swing.border.TitledBorder pnlImageBorder=new javax.swing.border.TitledBorder("Image");
	static javax.swing.border.TitledBorder detailsBorder=new javax.swing.border.TitledBorder("Contact Details");
}
