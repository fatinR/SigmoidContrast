package AppPackage;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SceneGrabber {

	private int xPoint, yPoint, xWidth, yLength;
	Dimension panelSize;
	
	public void setValue (int a, int b, int c, int d, Dimension mainPanelSize) {
		/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.xPoint = screenSize.width-(a+b);
		this.yPoint = screenSize.height-(c+d);
		this.xWidth = screenSize.width-(a+xPoint);
		this.yLength = screenSize.height-(c+yPoint)-42;*/
		this.xPoint=a;
		this.yPoint=b;
		this.xWidth=c;
		this.yLength=d;
		this.panelSize = mainPanelSize;
	}
	
	
	public ImageIcon Capture () {
		Robot robot = null;
		try {
			robot = new Robot ();
		} catch (AWTException awte) {
			awte.printStackTrace();
		}
 
		BufferedImage i = null;
		//i = robot.createScreenCapture( new Rectangle(xPoint+42, yPoint, xWidth-84, yLength) );
		i = robot.createScreenCapture( new Rectangle(xPoint, yPoint, xWidth, yLength) );
		Image scaledI = i.getScaledInstance(panelSize.width, panelSize.height, Image.SCALE_SMOOTH);
		ImageIcon im = new ImageIcon (scaledI);
		/*JLabel labelim = new JLabel(im);
		return labelim;*/
		return im;
	}
	
}
