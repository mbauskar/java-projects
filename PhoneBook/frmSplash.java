
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class frmSplash extends JWindow
{
	JLabel lblMsg;
	
	public frmSplash()
	{
		//this.setLayout(null);
		this.setSize(400,400);
		this.setLocation(new CenterLocation(this.getSize()).getLocation());
		
		Image img=new GetImage().getIcon("img/splash.jpg");
		img=img.getScaledInstance(400, 400,Image.SCALE_DEFAULT);
		JLabel lblImg=new JLabel(new ImageIcon(img));
		lblImg.setBounds(0, 0, 400, 400);
		this.add(lblImg);		
		
		this.setVisible(true);		
		try
		{
			Thread.sleep(4000);
		}
		catch(Exception err)	{	}
		this.setVisible(false);
		this.dispose();
	}
}
