package ui;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import domain.DezKlassifikationGrpe;

public class DezimalKlassifikationView extends JFrame {
	private JComboBox<DezKlassifikationGrpe> dezKlassifikationGrpe ;
	
	
	public DezimalKlassifikationView(String titel) {
		super.setTitle(titel);
	}

}
