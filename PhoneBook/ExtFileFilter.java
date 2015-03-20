
import java.io.File;

import javax.swing.filechooser.*;

public class ExtFileFilter extends FileFilter 
{
	private String desc;
	private String[] ext;
	
	public ExtFileFilter(String desc,String ext)
	{
		this(desc,new String[]{ext});
	}
	
	public ExtFileFilter(String desc,String[] ext)
	{
		this.ext=ext;
		this.toLower(ext);
		
		if(desc==null)
			this.desc=ext[0];
		else
		{
			String _desc=" (";
			for(int i=0;i<ext.length;i++)
			{
				if(i==ext.length-1)
					_desc+="*."+ext[i]+")";
				else
					_desc+="*."+ext[i]+",";
			}
			this.desc=desc+_desc;
		}				
	}
	
	private void toLower(String[] str)
	{
		for(int i=0;i<str.length;i++)
			this.ext[i]=str[i].toLowerCase();
	}

	@Override
	public boolean accept(File file)
	{		
		if (file.isDirectory())
			return true;
		else
		{
			String path = file.getAbsolutePath().toLowerCase();
		    for (int i = 0, n = ext.length; i < n; i++)
		    {
		    	String extension = ext[i];
		        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) 
		        	return true;
		    }
		}
		return false;
	}

	@Override
	public String getDescription() 
	{		
		return this.desc;
	}
}
