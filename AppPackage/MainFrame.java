package AppPackage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	private int currentCard = 1;
	private final JPanel upPanel, downPanel, mainPanel;
	private JPanel main5;
	private JLabel main4Label;
	private final CardLayout cl1, cl2, cl3;
	private String vidFile;
	private final JFXPanel fxPanel = new JFXPanel();
	private ImageIcon imCon, imGrey;
	
	public MainFrame() throws IOException {

		setTitle("Video Frame Enhancer");
		setSize(1365, 730);
		cl1 = new CardLayout();
		cl2 = new CardLayout();
		cl3 = new CardLayout();
		upPanel = new JPanel();
		upPanel.setLayout(cl1);
		downPanel = new JPanel();
		downPanel.setLayout(cl2);
		mainPanel = new JPanel();
		mainPanel.setLayout(cl3);

		final JPanel main1 = new JPanel();
		main1.setBackground(java.awt.Color.WHITE);
		BufferedImage gr = ImageIO.read(new File("graphic.png"));
		Image scaledGR = gr.getScaledInstance(gr.getWidth()/2, gr.getHeight()/2, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon (scaledGR);
		JLabel labelIcon = new JLabel(icon);
		main1.add(labelIcon);

		final JPanel mainUp = new JPanel();
		final JPanel main2 = new JPanel();
		main2.setLayout(new BoxLayout(main2, BoxLayout.Y_AXIS));
		main2.add(mainUp);
		main2.add(fxPanel);
		
		final JPanel main3 = new JPanel();
		
		final JPanel main4 = new JPanel();
		
		main5 = new JPanel();

		mainPanel.add(main1, "1");
		mainPanel.add(main2, "2");
		mainPanel.add(main3, "3");
		mainPanel.add(main4, "4");
		mainPanel.add(main5, "5");

		final JPanel up1 = new JPanel();
		final JPanel up2 = new JPanel();
		final JPanel up3 = new JPanel();
		final JPanel up4 = new JPanel();
		final JPanel up5 = new JPanel();
/*
		final JLabel jl2 = new JLabel("Video Details");
		final JLabel jl3 = new JLabel("Adjust Parameter");

		up2.add(jl2);
		up3.add(jl3);*/

		final JPanel jp1 = new JPanel();
		final JPanel jp2 = new JPanel();
		jp2.setLayout(new BoxLayout(jp2, BoxLayout.Y_AXIS));
		final JPanel jp3 = new JPanel();
		final JPanel jp4 = new JPanel();
		final JPanel jp5 = new JPanel();

		upPanel.add(up1, "1");
		upPanel.add(up2, "2");
		upPanel.add(up3, "3");
		upPanel.add(up4, "4");
		upPanel.add(up5, "5");

		downPanel.add(jp1, "1");
		downPanel.add(jp2, "2");
		downPanel.add(jp3, "3");
		downPanel.add(jp4, "4");
		downPanel.add(jp5, "5");

		final JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		sidePanel.setPreferredSize(new Dimension(200, 250));
		sidePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, java.awt.Color.GRAY));

		sidePanel.add(upPanel);
		sidePanel.add(downPanel);

		final JButton load = new JButton("Select Video");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e){

				startFileChooser();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						initFX(fxPanel);
					}
				});

				cl3.show(mainPanel, "" + (currentCard));
				cl1.show(upPanel, "" + (currentCard));
				cl2.show(downPanel, "" + (currentCard));
			}
		});

		final JButton capture = new JButton("Capture");
		capture.setAlignmentX(Component.CENTER_ALIGNMENT);
		capture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				int x = fxPanel.getLocationOnScreen().x+42;
				int y = fxPanel.getLocationOnScreen().y;
				int w = fxPanel.getBounds().width-84;
				int h = fxPanel.getBounds().height-42;
				SceneGrabber sg = new SceneGrabber ();
				//sg.setValue(sidePanel.getWidth(), fxPanel.getWidth(), 42, fxPanel.getHeight(), mainPanel.getSize());
				sg.setValue(x, y, w, h, mainPanel.getSize());
				setImageIcon(sg.Capture());
				JLabel labelim = new JLabel(imCon);
				main3.add(labelim);
				
				currentCard = 3;
				cl3.show(mainPanel, "" + (currentCard));
				cl1.show(upPanel, "" + (currentCard));
				cl2.show(downPanel, "" + (currentCard));
			}
		});
		
		final JButton clear = new JButton("Clear");
		clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		clear.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				currentCard = 1;
				cl3.show(mainPanel, "" + (currentCard));
				cl1.show(upPanel, "" + (currentCard));
				cl2.show(downPanel, "" + (currentCard));
			}
		});

		final JButton greyscale = new JButton("Change to greyscale");
		greyscale.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				ConvertGreyscale cg = new ConvertGreyscale ();
				cg.setImage(imCon, mainPanel);
				imGrey = cg.Convert();
				setMain4Label (imGrey);
				main4.add(main4Label);
				
				currentCard = 4;				
				cl3.show(mainPanel, "" + (currentCard));
				cl1.show(upPanel, "" + (currentCard));
				cl2.show(downPanel, "" + (currentCard));
			}
		});

		final JButton enhance = new JButton ("Enhance");
		enhance.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				EnhanceGrey eg = new EnhanceGrey();
				//try {
					eg.setValue(imGrey);
				/*} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				/*final BufferedImage im = eg.getImage();
				JPanel pane = new JPanel() {
					@Override
					public void paint(final Graphics g){
						final Graphics2D g2d = (Graphics2D)g;
						g2d.clearRect(0, 0, getWidth(), getHeight());
						g2d.drawImage(im, 0, 0, this);
					}
				};*/
				
				/*JPanel pane = eg.getPane();
				System.out.println(pane.getHeight());
				System.out.println(pane.getWidth());
				setMain5(pane);
				JPanel p = eg.getSlider();
				jp5.add(p);*/
				/*final JSlider s1 = eg.slider1();
				final JSlider s2 = eg.slider2();
				
				s1.addChangeListener(event1);
				
				class event1 implements ChangeListener {
					public void stateChanged (ChangeEvent e){
						int value = s1.getValue();
						m = value;
					}
					
				}

				class event2 implements ChangeListener {
					public void stateChanged (ChangeEvent e){
						int value = s2.getValue();
						n = value;
					}
					
				}
				
				
				jp5.add(panel);
				final BufferedImage imG=eg.getImage();
				final Image scaledImg = imG.getScaledInstance(main5.getWidth(), main5.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon (scaledImg);
				JLabel labelIcon = new JLabel(icon);
				main5.add(labelIcon);
				
				JPanel pane = new JPanel() {
					@Override
					public void paint(final Graphics g){
						Graphics2D g2d = (Graphics2D)g;
						g2d.clearRect(0, 0, getWidth(), getHeight());
						g2d.drawImage(scaledImg, 0, 0, this);
					}
				};
				
				pane.addComponentListener((ComponentListener) panel);
				pane.repaint();*/

				//main5.add(pane);

				currentCard = 4;
				cl3.show(mainPanel, "" + (currentCard));
				cl1.show(upPanel, "" + (currentCard));
				cl2.show(downPanel, "" + (currentCard));
			}
		});
		
		jp1.add(load);
		jp2.add(capture);
		jp2.add(Box.createRigidArea(new Dimension(0, 50)));
		jp2.add(clear);
		jp3.add(greyscale);
		jp4.add(enhance);

		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(sidePanel, BorderLayout.EAST);

	}

	private void startFileChooser () {
		final SelectVideo sv = new SelectVideo();
		try {
			sv.PickFile();
			final String vid = sv.getFile();
			setMedia(vid);
			currentCard = 2;

		} catch (final Exception e1) {
			e1.printStackTrace();
			currentCard = 1;//<<--click close condition
		}
	}

	private void initFX (final JFXPanel fxPanel) {

		final VideoPlayer vp = new VideoPlayer ();
		vp.setMediaFile(getMedia());
		final Scene sc = vp.getScene();
		fxPanel.setScene(sc);
	}

	protected String getMedia() {
		return this.vidFile;
	}

	protected void setMedia(final String string) {
		this.vidFile = string;
	}
	
	protected ImageIcon getImageIcon() {
		return this.imCon;
	}
	
	protected void setImageIcon (ImageIcon imageIcon){
		this.imCon = imageIcon;
	}

	
	private void setMain4Label (ImageIcon icon) {
		this.main4Label = new JLabel (icon);
	}
	
	private void setMain5 (JPanel p) {
		this.main5 = p;
	}
	
	public static void main(final String[] args) throws IOException {
		final MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


}
