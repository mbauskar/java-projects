
//import java.awt.Image;
//import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;

public class DataLayer 
{
	private static Connection cn;
	private static ResultSet rs;
	private static Statement stmt;
	private static PreparedStatement pstmt;
	
	public static String[] getNames(String query)
	{
		String names[]=null;
		int count;
		try
		{
			cn=DBConnection.getConnection();
			if(cn!=null)
			{
				stmt=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=stmt.executeQuery(query);
				rs.last();
				count=rs.getRow();
				names=new String[count];
				rs.beforeFirst();
				int i=0;
				while(rs.next())
					names[i++]=rs.getString(1);
				cn.close();
			}
			else
				throw new Exception("Connection not establish");
		}
		catch(Exception err)
		{
			javax.swing.JOptionPane.showMessageDialog(null, err.getMessage());
		}
		return names;
	}
	
	public static Contact[] getContacts(String query)
	{
		Contact contact[]=null;
		int count=0;
		int i=0;
		try
		{
			cn=DBConnection.getConnection();
			if(cn!=null)
			{
				stmt=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=stmt.executeQuery(query);				
				rs.last();
				count=rs.getRow();
				contact=new Contact[count];
				rs.beforeFirst();

				while(rs.next())
				{
					String name=rs.getString("PerName");
					String group=rs.getString("GroupName");
					String altNo=rs.getString("AltNo");
					String MobNo=rs.getString("MobNo");
					String OffNo=rs.getString("OffNo");
					String email=rs.getString("Email");
					String addr=rs.getString("Addr");
					String note=rs.getString("SpNote");
					String fileName=rs.getString("FileName");
					java.io.InputStream in=rs.getBinaryStream("Photo");
					java.awt.Image photo=javax.imageio.ImageIO.read(in);
					
					Var.contactID=rs.getString("ContactID");

					contact[i]=new Contact();
					contact[i++].setContact(name,group,altNo,MobNo,OffNo,email,addr,note,fileName,photo);
				}
				cn.close();
			}
		}
		catch(Exception err)
		{
			err=null;
			return null;
		}
		return contact;
	}
	
	public static int countContacts(String query)
	{
		int count=0;
		try
		{
			cn=DBConnection.getConnection();
			stmt=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=stmt.executeQuery(query);
			rs.last();
			count=rs.getRow();
			cn.close();
		}
		catch(Exception err){	}
		return count;
	}
	
	public static boolean saveContact(Contact con)
	{		
		Var.contactID=ContactDialog.getRandomString();
		String d[]=con.getContact();
		String values="'"+Var.LoginID+"','"+Var.contactID+"','"+con.getName()+"','"+
						con.getContactGroup()+"','"+con.getAltNo()+"','"+con.getMobNo()+"','"+
						con.getOffNo()+"','"+con.getEmail()+"','"+con.getAddr()+"','"+
						con.getNote()+"','"+con.getFileName()+"',?";
		
		String query="Insert into contacts values("+values+")";
		boolean flag=saveContact(query, con.getFileName());
		return flag;
	}
	
	public static boolean saveContact(String query,String filePath)
	{
		boolean flag=false;
		try
		{
			cn=DBConnection.getConnection();
			pstmt=cn.prepareStatement(query);
			java.io.File imgFile=null;
			java.io.FileInputStream fin=null;
			if(!filePath.equals("photo_not_available.jpg"))
			{
				imgFile=new java.io.File(filePath);
				fin=new java.io.FileInputStream(imgFile);
				pstmt.setBinaryStream(1, fin, (int)imgFile.length());
			}
			else
				pstmt.setString(1, null);
			
			flag=pstmt.execute();
			
			if(fin!=null)
				fin.close();
			cn.close();
		}
		catch(Exception err)
		{	
			javax.swing.JOptionPane.showMessageDialog(null, err.getMessage());	
		}
		return !flag;	
	}
	
	public static boolean updateContact(String query,String fileName,boolean isPhotoChanged)
	{
		boolean flag=false;
		try
		{
			cn=DBConnection.getConnection();
			stmt=cn.createStatement();
			flag=stmt.execute(query);
			if(!flag&&isPhotoChanged)
			{
				if(!DataLayer.updatePhoto(fileName))
					JOptionPane.showMessageDialog(null, "Photo can not be updated");
			}
			cn.close();
		}
		catch(Exception err)
		{
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		return !flag;
	}
	
	public static boolean updatePhoto(String filePath)
	{
		boolean flag=false;
		try
		{
			cn=DBConnection.getConnection();
			java.io.File imgFile=new java.io.File(filePath);;
			java.io.FileInputStream fin=new java.io.FileInputStream(imgFile);;
			pstmt=cn.prepareStatement("update contacts set Photo=? where LoginId=?");
			pstmt.setBinaryStream(1, fin,(int)imgFile.length());
			pstmt.setString(2,Var.LoginID);
			flag=pstmt.execute();
			
			if(fin!=null)
				fin.close();
			cn.close();
		}
		catch(Exception err)
		{
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		return !flag;		
	}
	
	public static boolean deleteContact(String query)
	{
		boolean flag=false;
		try
		{
			cn=DBConnection.getConnection();
			stmt=cn.createStatement();
			flag=stmt.execute(query);
			cn.close();
		}
		catch(Exception err)
		{
			flag=false;
		}
		return !flag;
	}
	
	public static String[] getUserInfo(String query)
	{
		String usrInfo[]=new String[2];
		try
		{			
			cn=DBConnection.getConnection();
			stmt=cn.createStatement();
			rs=stmt.executeQuery(query);
			while(rs.next())
			{
				usrInfo[0]=rs.getString(1);		// usrInfo[0] = Password
				usrInfo[1]=rs.getString(2);		// usrInfo[0] = UserName
			}			
		}
		catch(Exception err)
		{
			usrInfo=null;
		}
		return usrInfo;
	}
	
	public static boolean insertUserInfo(String query)
	{
		boolean flag=false;
		try
		{
			cn=DBConnection.getConnection();
			stmt=cn.createStatement();
			flag=stmt.execute(query);
			cn.close();
		}
		catch(Exception err)
		{
			err.printStackTrace();
			return false;
		}
		return !flag;
	}
}
