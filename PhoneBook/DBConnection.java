
import java.sql.*;

import javax.swing.JOptionPane;

public class DBConnection
{
	static Connection cn;

	//	Connection with MS-ACCESS

	public static Connection getConnection()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn=DriverManager.getConnection("jdbc:odbc:dbPhoneBook");
			return cn;

		}
		catch(Exception e)
		{
			return tryDBfile();
		}
	}

	private static Connection tryDBfile()
	{
		try
		{
			String filename = "dbPhoneBook.mdb";
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			database+= filename.trim() + ";DriverID=22;READONLY=true}";
			cn = DriverManager.getConnection( database ,"","");
			return cn;
		}
		catch(Exception err)
		{
			return createDB();
		}
	}

	private static Connection createDB()
	{
		try
		{
			java.io.InputStream in=DBConnection.class.getResourceAsStream("db/dbPhoneBook.mdb");
			java.io.OutputStream os = new java.io.FileOutputStream("dbPhoneBook.mdb");
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0) {
			    os.write(buffer, 0, length);
			}
			os.close();
			in.close();

			javax.swing.JOptionPane.showMessageDialog(null, "Please restart the application");
			System.exit(0);
		}
		catch(Exception ex)
		{
			return null;
		}
		return null;
	}

	// Connection with MS-EXCEL

	public static Connection getXLConnection(String absoluteFilePath)
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			String database = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=";
			database+= absoluteFilePath.trim() + ";DriverID=22;READONLY=false}";
			cn = DriverManager.getConnection( database ,"","");
			return cn;
		}
		catch(Exception err)
		{
			cn=null;
		}
		return cn;
	}

	public static void createXLFile(String absoluteFilePath)
	{
		try
		{
			java.io.InputStream in=DBConnection.class.getResourceAsStream("db/dbPhoneBook.xls");
			java.io.OutputStream os = new java.io.FileOutputStream(absoluteFilePath+".xls");
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0)
			{
			    os.write(buffer, 0, length);
			}
			os.close();
			in.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "File Not found....");
		}
	}
}