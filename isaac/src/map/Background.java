package map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Background {
	private JLabel laBackground;
	
	public Background(JFrame app) {
		laBackground = new JLabel(new ImageIcon("images/structure/map.png"));
		app.setContentPane(laBackground);
	}
}
