package AppPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ConvertGreyscale {

	private BufferedImage im;
	private JPanel panel;

	public void setImage (final ImageIcon ic, final JPanel p) {

		final ImageIcon icon = ic;
		final BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
		final Graphics g = bi.createGraphics();
		icon.paintIcon(null, g, 0,0);
		g.dispose();
		this.im = bi;
		this.panel = p;
	}

	public ImageIcon Convert () {

		final Dimension panelSize = panel.getSize();
		for(int w = 0; w < im.getWidth() ; w++)		
		{			
			for(int h = 0 ; h < im.getHeight() ; h++)			
			{				
				final Color color = new Color(im.getRGB(w, h));
				final int redC = color.getRed();
				final int greenC = color.getGreen();
				final int blueC = color.getBlue();
				final int averageColor = (int) (0.21*redC + 0.71*greenC + 0.07*blueC);
				final Color avg = new Color(averageColor, averageColor, averageColor);
				im.setRGB(w, h, avg.getRGB());			
			}		

		}

		final Image scaledIM = im.getScaledInstance(panelSize.width, panelSize.height, Image.SCALE_SMOOTH);
		final ImageIcon icon = new ImageIcon (scaledIM);
		return icon;

	}


}
