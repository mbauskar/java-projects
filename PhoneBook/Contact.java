
public class Contact
{
	private String Name;
	private String ContactGroup;
	private String MobNo;
	private String AltNo;	
	private String OffNo;
	private String Email;
	private String Addr;
	private String Note;
	
	private String fileName="photo_not_available.jpg";
	private java.awt.Image photo;

	public static String contactID;

	public Contact()
	{
		this("Contact Name","Contact Group","Mobile Number","Alternative Number","Office/Work Number","Email ID","Person address","Contact Note","photo_not_available.jpg",new GetImage().getIcon("img/photo_not_available.jpg"));				
	}
	
	public Contact(String name,String group,String mobNo,String altNo,String offNo,String email,String addr,String note,String fName,java.awt.Image img)
	{
		Name=name;
		ContactGroup=group;
		MobNo=mobNo;
		AltNo=altNo;	
		OffNo=offNo;
		Email=email;
		Addr=addr;
		Note=note;
		fileName=fName;
		photo=img;
	}

	public void setName(String Name){	this.Name=Name;	}
	public void setContactGroup(String group){	this.ContactGroup=group;	}
	public void setAltNo(String altNo){	this.AltNo=altNo;	}
	public void setMobNo(String mobNo){	this.MobNo=mobNo;	}
	public void setOffNo(String offNo){	this.OffNo=offNo;	}
	public void setEmail(String email){	this.Email=email;	}
	public void setAddr(String addr){	this.Addr=addr;	};
	public void setNote(String note){	this.Note=note;	}
	public void setFileName(String fileName){	this.fileName=fileName;	}
	public void setPhoto(java.awt.Image img)
	{
		if(img==null)
			img=new GetImage().getIcon("img/photo_not_available.jpg");
		else
			this.photo=img;		
	}

	public void setContact(String name,String group,String altNo,String mobNo,String offNo,String email,String addr,String note,String fileName,java.awt.Image photo)
	{
		this.setName(name);
		this.setContactGroup(group);
		this.setAltNo(altNo);
		this.setMobNo(mobNo);
		this.setOffNo(offNo);
		this.setEmail(email);
		this.setAddr(addr);
		this.setNote(note);
		this.setFileName(fileName);
		this.setPhoto(photo);
	}

	public String getName(){	return this.Name;	}
	public String getContactGroup(){	return this.ContactGroup;	}
	public String getAltNo(){	return this.AltNo;	}
	public String getMobNo(){	return this.MobNo;	}
	public String getOffNo(){	return this.OffNo;	}
	public String getEmail(){	return this.Email;	}
	public String getAddr(){	return this.Addr;	}
	public String getNote(){	return this.Note;	}
	public String getFileName(){	return this.fileName;	}
	public java.awt.Image getPhoto(){	return this.photo;	}

	public String[] getContact()
	{
		int index=0;
		String contact[]=new String[9];
		contact[index++]=this.getName();
		contact[index++]=this.getContactGroup();
		contact[index++]=this.getAltNo();
		contact[index++]=this.getMobNo();
		contact[index++]=this.getOffNo();
		contact[index++]=this.getEmail();
		contact[index++]=this.getAddr();
		contact[index++]=this.getNote();
		contact[index++]=this.getFileName();

		return contact;
	}

	public boolean isValidContact()
	{
		boolean flag=true;
		String mobNo=this.getMobNo();
		String altNo=this.getAltNo();
		String offNo=this.getOffNo();
		if(this.getName().equals("")||this.getName().equals("Contact Name"))
			return false;
		else
		{
			if(mobNo.equals("")||mobNo.equals("Mobile Contact"))
				flag=false;
			else
				return true;
			if(altNo.equals("")||altNo.equals("Mobile Contact"))
				flag=false;
			else
				return true;
			if(offNo.equals("")||offNo.equals("Mobile Contact"))
				flag=false;
			else
				return true;
		}	
		return flag;		
	}
	
	public String toString()
	{
		String temp="";
		String t[]=this.getContact();
		for(int i=0;i<t.length;i++)
			temp+=t[i]+"\n";
		return temp;
	}
}