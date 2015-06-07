package AppPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EnhanceGrey{

	private static byte[] ori;
	boolean frozen = false;
	double m, n;
	JPanel p3, pane;
	
	static JSlider parameter1, parameter2;
	JLabel label1, label2;

	byte[] sigm;
	BufferedImage im;
	int width;
	
	public void setValue(ImageIcon ic) {
		final JFrame frame = buildFrame();
		frame.setSize(1365, 730);
		/*final String dirName="C:\\";
		final BufferedImage imGrey = ImageIO.read(new File(dirName, "grayscale.jpg"));
		BufferedImage image = new BufferedImage(imGrey.getWidth(), imGrey.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
		Graphics g = image.getGraphics();  
		g.drawImage(imGrey, 0, 0, null);  
		g.dispose();*/
		
		final ImageIcon icon = ic;
		final BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_BYTE_GRAY);
		final Graphics g = image.createGraphics();
		icon.paintIcon(null, g, 0,0);
		g.dispose();
		
		width = image.getData().getWidth();
		byte[] val = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		ori = val; 
		//sigmOpr();
		//img = getGreyscale(width);
		setM(50);
		setN(15);
		pane = new JPanel() {
			@Override
			public void paint(final Graphics g){
				final Graphics2D g2d = (Graphics2D)g;
				g2d.clearRect(0, 0, getWidth(), getHeight());
				g2d.drawImage(getGreyscale(), 0, 0, this);
			}
		};
		//pane.setSize(image.getWidth(), image.getHeight());
		
		final int p1Min = 0;
		final int p1Max = 100;
		final int p1Init = 50;

		parameter1 = new JSlider(JSlider.HORIZONTAL, p1Min, p1Max, p1Init);
		//parameter1.setMajorTickSpacing(5);
		parameter1.setMinorTickSpacing(2);
		//parameter1.setPaintTicks(true);
		//parameter1.setPaintLabels(true);
		//parameter1.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		parameter1.setPreferredSize(new java.awt.Dimension(100, 46));
		parameter1.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  int value = parameter1.getValue();
		    	  setM(value);
		    	  pane.repaint();
		    	  label1.setText(""+value);
		        }
		      });
		
		label1 = new JLabel("" + p1Init);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(parameter1);
		p1.add(label1);
		
		final int p2Min = 0;
		final int p2Max = 100;
		final int p2Init = 15;

		parameter2 = new JSlider(JSlider.HORIZONTAL, p2Min, p2Max, p2Init);
		//parameter2.setMajorTickSpacing(5);
		parameter2.setMinorTickSpacing(10);
		//parameter2.setPaintTicks(true);
		//parameter2.setPaintLabels(true);
		//parameter2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		parameter2.setPreferredSize(new java.awt.Dimension(100, 46));
		parameter2.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		    	  int value = parameter2.getValue();
		    	  setN(value);
		    	  pane.repaint();
		    	  label2.setText(""+value);
		        }
		      });
		
		label2 = new JLabel("" + p2Init);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(parameter2);
		p2.add(label2);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				setImage(getGreyscale());
				frame.dispose();
			}
		});
		
		JLabel l1 = new JLabel("MidValue");
		JLabel l2 = new JLabel("Contrast");
		
		p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		p3.add(l1);
		p3.add(p1);
		p3.add(l2);
		p3.add(p2);
		//p3.add(ok);
		
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		JPanel up = new JPanel();
		JPanel down = new JPanel();
		down.setPreferredSize(new Dimension(200, 250));
		jp.add(up);
		jp.add(p3);
		jp.add(down);
	
		//setLayout(new BorderLayout());
		
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.getContentPane().add(jp, BorderLayout.EAST);
	}

	private void setM(int value) {
		
		this.m = (double)value/100;
	}
	
	private double getM () {
		return this.m;
	}
	
	private void setN(int val) {
		this.n = (double)val/100;
	}
	
	private double getN () {
		return this.n;
	}
	
	private void setPane(JPanel p) {
		this.pane=p;
	}
	
	public JPanel getPane() {
		return this.pane;
	}
	
	private void setImage (BufferedImage img) {
		this.im = img;
	}
	
	public BufferedImage getImage () {
		return this.im;
	}
	
	public JPanel getSlider() {
		return this.p3;
	}
	
	private BufferedImage getGreyscale() {
		double m = getM();
		double n = getN();
		final byte[] newData = new byte[ori.length];
		final int[] intArray=new int[newData.length];
		for(int i=0;i<newData.length;i++){
			intArray[i]=ori[i];
		}
		double max=intArray[0];
		double min=intArray[0];
		for(int i=0;i<intArray.length;i++){
			if(intArray[i]>max)
				max=intArray[i];
			if(intArray[i]<min)
				min=intArray[i];
		}
		final double c=max-min;
		double x=0.0;
		double y=0.0;
		/*final double a=.5;
		final double b=10;*/
		for(int i=0;i<intArray.length;i++){
			x=(intArray[i]-min)/c; //<--normalization
			y=1/(1+Math.pow(Math.E, -((x-m)/n))); //<--sigmoid
			//y=(1/(1+Math.exp(m*(n-x))) - 1/(1+Math.exp(m))) / (1/(1+Math.exp(m*(n-1))) - 1/(1+Math.exp(m*n)));
			final double d=y*c+min;
			intArray[i]=(int) d;
			newData[i]=(byte) intArray[i];
		}
		
		final byte[] buffer = newData;
		final int height = buffer.length / width;
		final ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		final int[] nBits = { 8 };
		final ColorModel cm = new ComponentColorModel(cs, nBits, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		final SampleModel sm = cm.createCompatibleSampleModel(width, height);
		final DataBufferByte db = new DataBufferByte(buffer, width * height);
		final WritableRaster raster = Raster.createWritableRaster(sm, db, null);
		final BufferedImage result = new BufferedImage(cm, raster, false, null);
		return result;
	}

	private static JFrame buildFrame() {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}

	
	/*public static void main(final String[] args) throws IOException  {
		EnhanceGrey eg2 = new EnhanceGrey();
		eg2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eg2.setVisible(true);
	}*/
	
}
