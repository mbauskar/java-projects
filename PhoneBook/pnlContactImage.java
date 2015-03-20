
import java.awt.*;
import javax.swing.*;

public class pnlContactImage extends JPanel 
{
	private JLabel lblPhoto;
	private Image contactImg;
	private String fileName="photo_not_available.jpg";
	
	public pnlContactImage()
	{
		Var.refPnlImg=this;
		this.setLayout(null);
		this.setSize(335, 185);
		this.setBackground(Var.bgColor);
		this.addLabel();
		this.setVisible(true);
	}
	
	private void addLabel()
	{
		contactImg=new GetImage().getIcon("img/photo_not_available.jpg");
		contactImg=contactImg.getScaledInstance(150,160,Image.SCALE_DEFAULT);
		lblPhoto=new JLabel(new ImageIcon(contactImg));
		lblPhoto.setToolTipText(fileName);
		lblPhoto.setBounds(92,15,150,160);
		this.add(lblPhoto);
	}
	
	public void setPhotoTooltipText(String imgName){	lblPhoto.setToolTipText(imgName);	}
	public void setPhoto(Image img)
	{
		if(img==null)
			img=new GetImage().getIcon("img/photo_not_available.jpg");
		contactImg=img.getScaledInstance(150,160,Image.SCALE_DEFAULT);
		lblPhoto.setIcon(new ImageIcon(contactImg));
	}
	
	public String getPhotoTooltipText(){	return lblPhoto.getToolTipText();	}
	public Image getPhoto()
	{
		if(contactImg==null)
			contactImg=new GetImage().getIcon("img/photo_not_available.jpg");
		return contactImg;
	}
}
