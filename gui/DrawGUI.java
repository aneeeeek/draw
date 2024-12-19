package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import logic.DrawingController;

/**
 * Graphical user interface for the Drawing editor "Draw"
 * 
 * @author Alex Lagerstedt
 * 
 */

public class DrawGUI extends JFrame {


//	public class StatusBar extends JLabel {
//
//		private static final long serialVersionUID = 0;
//
//		public StatusBar() {
//			super();
//			super.setPreferredSize(new Dimension(100, 16));
//			setMessage("Ready");
//		}
//
//		public void setMessage(String message) {
//			setText(" " + message);
//		}
//	}

	private DrawingController controller;
	private DrawingPanel drawingPanel;
	private ToolBox tools;
	private JScrollPane scrollpane;

	// private StatusBar statusBar;

	private static final long serialVersionUID = 0;

	public static void main(String[] args) {

		new DrawGUI();

	}

	/**
	 * Constructs a new graphical user interface for the program and shows it.
	 */
	public DrawGUI() {

		this.setTitle("Draw 0.2");
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		controller = new DrawingController();
		tools = new ToolBox(controller);

		MouseListener mouse = new MouseListener(controller,tools);
		drawingPanel = new DrawingPanel(mouse);
		mouse.setPanel(drawingPanel);

		scrollpane = new JScrollPane(drawingPanel);
		controller.setPanel(drawingPanel);
		controller.newDrawing(new Dimension(500, 380));

		// statusBar = new StatusBar();

		getContentPane().add(tools, BorderLayout.WEST);
		getContentPane().add(scrollpane, BorderLayout.CENTER);
		// getContentPane().add(statusBar, BorderLayout.SOUTH);

		MenuListener mainMenuListener = new MenuListener(controller);
		JMenuBar mainMenu = new MainMenu(mainMenuListener);
		this.setJMenuBar(mainMenu);

		pack();
		setVisible(true);

	}

	/**
	 * Updates the GUI to show the Drawing instance that is currently controlled
	 * by the DrawingController.
	 */
	public void updateDrawing() {

		drawingPanel.setDrawing(controller.getDrawing());
		scrollpane.setPreferredSize(new Dimension(drawingPanel
				.getPreferredSize().width + 100, drawingPanel
				.getPreferredSize().height + 100));
		pack();
		repaint();
	}
}
