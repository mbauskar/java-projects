
import java.awt.*;

public class CenterLocation 
{
	private int frmWidth;
	private int frmHeight;
	private Point frmLoc;
	
	public CenterLocation(Dimension frmSize)
	{
		this.frmWidth=frmSize.width;
		this.frmHeight=frmSize.height;
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.frmLoc=this.calculateLocation(screenSize);
	}
	
	private Point calculateLocation(Dimension screenSize)
	{
		int screenWidth=screenSize.width;
		int screenHeight=screenSize.height;
		
		int x=(screenWidth/2)-(this.frmWidth/2);
		int y=(screenHeight/2)-(this.frmHeight/2);
		
		return new Point(x,y);
	}
	
	public Point getLocation()
	{
		return this.frmLoc;
	}
}
