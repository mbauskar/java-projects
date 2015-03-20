
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.awt.event.*;

public class ImageLabel extends JLabel
{
	private Point2D[] points;
    private int SIZE = 4;
    private int pos;
    private boolean enableRectangle=false;
    
    private Icon icon;
    
	public ImageLabel()
	{
		this(null);
	}
	
	public ImageLabel(Icon icon)
	{
		this.icon=icon;
		points = new Point2D[2];
		this.setIcon(icon);	
		this.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent event) 
		    {
				if(!enableRectangle)
					return;
				Point p = event.getPoint();
				points[0]=p;
				points[1]=p;
		        for (int i = 0; i < points.length; i++) 
		        {
		        	double x = points[i].getX() - SIZE / 2;
		            double y = points[i].getY() - SIZE / 2;

		            Rectangle2D r = new Rectangle2D.Double(x, y, SIZE, SIZE);

		            if (r.contains(p)) 
		            {
		            	pos = i;
		                return;
		            }
		        }
		    }
			
			public void mouseReleased(MouseEvent event) 
			{
				pos = -1;
		    }			
		});
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent event) 
			{
				if (pos == -1)
					return;
		        points[pos] = event.getPoint();
		        repaint();
		     }
		});
		this.repaint();
	}
	
	public void paintComponent(Graphics g) 
	{
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < points.length; i++) 
        {
            double x = points[i].getX() - SIZE / 2;
            double y = points[i].getY() - SIZE / 2;
            g2.fill(new Rectangle2D.Double(x, y, SIZE, SIZE));
        }

        Rectangle2D s = new Rectangle2D.Double();
        s.setFrameFromDiagonal(points[0], points[1]);
        
        this.setIcon(icon);
        g2.draw(s);
    }
	
	public void enableResizeRect(boolean flag)
	{
		this.enableRectangle=flag;
	}	
}


