
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.IOException;

public class pnlCropImage extends JDialog
{
	private String imgPath;
	private String imgName;
	
	private Image image;
	private Image image1;
	private boolean isEnable=true;
	
	private JLabel lbl;	
	private JLabel lblPreview;
	private JScrollPane jsp;
	
	private Point2D[] points;
	private Point start;
	private Point end;
    private int SIZE = 4;
    private int pos;
	
	public pnlCropImage(String fileName,Image img)
	{
		points = new Point2D[2];
		start=new Point(0,0);
		end=new Point(0,0);
		
		this.imgName=fileName;
		this.image=img;
		
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setLayout(null);
		this.setSize(660,600);
		CenterLocation loc=new CenterLocation(this.getSize());
		this.setLocation(loc.getLocation());
		this.setTitle("Crop Image");		
		
		lbl=new JLabel(new ImageIcon(img));	
		lbl.setHorizontalAlignment(JLabel.LEFT);
		lbl.setVerticalAlignment(JLabel.TOP);
		lbl.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent me) 
			{
				if(!isEnable)
					return;
				Point p = me.getPoint();
				start=p;
				
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
			
			public void mouseReleased(MouseEvent me)
			{
				isEnable=false;
				end=me.getPoint();			
				getCropImage();				
			}
		});
		
		lbl.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent me)
			{
				if(isEnable)
				{
					if (pos == -1)
						return;
			        points[pos] = me.getPoint();
			        repaint();
			        drawRect();			       
				}
			}
		});			
		jsp=new JScrollPane(lbl);
		jsp.setBounds(5,5,483,500);
		this.add(jsp);		
		
		lblPreview=new JLabel("Preview",JLabel.CENTER);
		lblPreview.setBounds(495, 170, 150, 160);
		this.add(lblPreview);
		
		JButton btnCrop=new JButton("Crop");
		btnCrop.setIcon(new GetImage().getImageIcon("img/icons/crop.png"));
		btnCrop.setBounds(90, 520, 100, 30);
		btnCrop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				isEnable=true;
				lbl.setToolTipText("Click on image to crop");
			}			
		});
		this.add(btnCrop);
		
		Image icon=new GetImage().getIcon("img/icons/save.png");
		icon=icon.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
		JButton btnSave=new JButton("Save");
		btnSave.setIcon(new ImageIcon(icon));
		btnSave.setBounds(200, 520, 100, 30);
		btnSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnSave_ActionPerformed();
			}			
		});
		this.add(btnSave);
		
		JButton btnCancel=new JButton("Cancel");
		btnCancel.setIcon(new GetImage().getImageIcon("img/icons/cancel.png"));
		btnCancel.setBounds(310, 520, 100, 30);
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				btnCancel_ActionPerformed();
			}			
		});
		this.add(btnCancel);	
	}

	protected void getCropImage() 
	{
		drawRect();
		String str=""+start+","+end;
		pnlCropImage.this.setTitle(str);
		int x=0;
		int y=0;
		int w=0;
		int h=0;
		
		if(start.x<end.x)
			w=end.x-start.x;			
		else
			w=start.x-end.x;			
		if(start.y<end.y)
			h=end.y-start.y;			
		else
			h=start.y-end.y;
			
		image1 = createImage(new FilteredImageSource(image.getSource(),new CropImageFilter(start.x, start.y, w, h)));
		lblPreview.setText(null);
		lblPreview.setIcon(new ImageIcon(image1.getScaledInstance(150, 160, Image.SCALE_DEFAULT)));	
	}
	
	private void drawRect()
	{
		Graphics2D g2 = (Graphics2D) lbl.getGraphics();

        for (int i = 0; i < points.length; i++) 
        {
            double x = points[i].getX() - SIZE / 2;
            double y = points[i].getY() - SIZE / 2;
            g2.fill(new Rectangle2D.Double(x, y, SIZE, SIZE));
        }

        Rectangle2D s = new Rectangle2D.Double();
        s.setFrameFromDiagonal(points[0], points[1]);
        
        g2.draw(s);
	}
	
	private void btnCancel_ActionPerformed() 
	{	
		Var.temp="temp";
		this.dispose();
	}
	
	private void btnSave_ActionPerformed() 
	{
		Image img=image1;
		BufferedImage bimg = new BufferedImage(image1.getWidth(null),image1.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bimg .createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		String name=JOptionPane.showInputDialog(pnlCropImage.this, "Enter the file name : ")+".jpg";
		try 
		{
			Var.temp="tempImg.jpg";
			java.io.File f=new java.io.File(Var.temp);
			ImageIO.write(bimg, "jpg", f);
		}
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		Var.refPnlImg.setPhoto(img);
		Var.refPnlImg.setPhotoTooltipText(name);
		ContactDialog.ref.setImagePath(name);
		this.dispose();
	}
}
