
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class GetImage 
{
	private ImageIcon img;
	private Image icon;
	
	public GetImage()
	{	
	}
	
	public Image getIcon(String imgName)
	{
		try
		{
			ImageIcon img=this.getImageIcon(imgName);
			icon=img.getImage();
		}
		catch(Exception err)
		{
			System.out.println("Error in loading image"+imgName);
		}
		return icon;
	}
	
	public ImageIcon getImageIcon(String imgName)
	{
		try
		{
			img=new ImageIcon(ImageIO.read(getClass().getResource(imgName)));
		}
		catch(Exception ex)
		{
			System.out.println("Error in loading image"+imgName);			
		}
		return img;
	}
}
