package ui.standardKomponenten;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Standard-Titel-Panel zur Verwendung in jeder View
 * @version 1.0 30.10.2018
 * @author Schmutz
 *
 */
public class StandardTitelPanel extends JPanel{
	private JLabel titelLabel;
	
	public StandardTitelPanel(String titel) {
		titelLabel = new JLabel(titel, SwingConstants.LEFT);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		Font f = titelLabel.getFont();
		titelLabel.setFont(new Font(f.getFontName(), Font.BOLD, f.getSize() * 2));
		setBorder(new EmptyBorder(0, 10, 0, 0));
		
		add(titelLabel);
	}

}
