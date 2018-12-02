package ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * Die Klasse stellt die View mit den Menübefehlen bereit
 * 
 * @version 2018-11-07
 * @author Schmutz
 *
 */
@SuppressWarnings("serial")
public class HauptView extends JFrame {
	private JMenuItem ausleiheM = new JMenu("Ausleihe");
	private JMenuItem ausleiheAusleiheM = new JMenuItem("Ausleihe");
	private JMenuItem ausleiheRueckgabeM = new JMenuItem("Rückgabe");
	private JMenuItem medienM = new JMenu("Medien");
	private JMenuItem medienBuchM = new JMenuItem("Buch");
	private JMenuItem medienAutorM = new JMenuItem("Autor");
	private JMenuItem medienVerlagM = new JMenuItem("Verlag");
	private JMenuItem benutzerM = new JMenu("Benutzer");
	private JMenuItem benutzerBenutzerM = new JMenuItem("Benutzer");
	private JMenuItem administrationM = new JMenu("Administration");
	private JMenuItem administrationMitarbeiterM = new JMenuItem("Mitarbeiter");
	private JMenuItem administrationSchlagworteM = new JMenuItem("Schlagworte");
	private JMenuItem administrationStammdatenM = new JMenuItem("Stammdaten");
	private JMenuItem beendenM = new JMenuItem("Beenden");
	private JMenuBar menuBar = new JMenuBar();

	public HauptView(String name) {
		//this.add(new JLabel(new ImageIcon("")));
		setJMenuBar(erstelleMenuBar());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 1000);
		setVisible(true);
	}

	private JMenuBar erstelleMenuBar() {
		menuBar.add(erstelleAusleiheMenu());
		menuBar.add(erstelleMedienMenu());
		menuBar.add(erstelleBenutzerMenu());
		menuBar.add(erstelleAdministrationMenu());
		menuBar.add(erstelleBeenden());
		return menuBar;
	}

	private JMenuItem erstelleAusleiheMenu() {
		ausleiheM.add(ausleiheAusleiheM);
		ausleiheM.add(ausleiheRueckgabeM);
		return ausleiheM;
	}

	private JMenuItem erstelleMedienMenu() {
		medienM.add(medienBuchM);
		medienM.add(medienAutorM);
		medienM.add(medienVerlagM);
		return medienM;

	}

	private JMenuItem erstelleBenutzerMenu() {
		benutzerM.add(benutzerBenutzerM);
		return benutzerM;
	}



	private JMenuItem erstelleAdministrationMenu() {
		administrationM.add(administrationMitarbeiterM);
		administrationM.add(administrationSchlagworteM);
		administrationM.add(administrationStammdatenM);
		return administrationM;
	}

	private JMenuItem erstelleBeenden() {
		return beendenM;
	}

	public JMenuItem getAusleiheM() {
		return ausleiheM;
	}

	public void setAusleiheM(JMenu ausleiheM) {
		this.ausleiheM = ausleiheM;
	}

	public JMenuItem getAusleiheAusleiheM() {
		return ausleiheAusleiheM;
	}

	public void setAusleiheAusleiheM(JMenuItem ausleiheAusleiheM) {
		this.ausleiheAusleiheM = ausleiheAusleiheM;
	}

	public JMenuItem getAusleiheRueckgabeM() {
		return ausleiheRueckgabeM;
	}

	public void setAusleiheRueckgabeM(JMenuItem ausleiheRueckgabeM) {
		this.ausleiheRueckgabeM = ausleiheRueckgabeM;
	}

	public JMenuItem getMedienM() {
		return medienM;
	}

	public void setMedienM(JMenuItem medienM) {
		this.medienM = medienM;
	}

	public JMenuItem getMedienBuchM() {
		return medienBuchM;
	}

	public void setMedienBuchM(JMenuItem medienBuchM) {
		this.medienBuchM = medienBuchM;
	}

	public JMenuItem getMedienAutorM() {
		return medienAutorM;
	}

	public void setMedienAutorM(JMenuItem medienAutorM) {
		this.medienAutorM = medienAutorM;
	}

	public JMenuItem getMedienVerlagM() {
		return medienVerlagM;
	}

	public void setMedienVerlagM(JMenuItem medienVerlagM) {
		this.medienVerlagM = medienVerlagM;
	}

	public JMenuItem getBenutzerM() {
		return benutzerM;
	}

	public void setBenutzerM(JMenuItem benutzerM) {
		this.benutzerM = benutzerM;
	}

	public JMenuItem getBenutzerBenutzerM() {
		return benutzerBenutzerM;
	}

	public void setBenutzerBenutzerM(JMenuItem benutzerBenutzerM) {
		this.benutzerBenutzerM = benutzerBenutzerM;
	}



	public JMenuItem getAdministrationM() {
		return administrationM;
	}

	public void setAdministrationM(JMenuItem administrationM) {
		this.administrationM = administrationM;
	}

	public JMenuItem getAdministrationMitarbeiterM() {
		return administrationMitarbeiterM;
	}

	public void setAdministrationMitarbeiterM(JMenuItem administrationMitarbeiterM) {
		this.administrationMitarbeiterM = administrationMitarbeiterM;
	}

	public JMenuItem getAdministrationSchagworteM() {
		return administrationSchlagworteM;
	}

	public void setAdministrationSchagworteM(JMenuItem administrationSchlagworteM) {
		this.administrationSchlagworteM = administrationSchlagworteM;
	}

	public JMenuItem getAdministrationStammdatenM() {
		return administrationStammdatenM;
	}

	public void setAdministrationStammdatenM(JMenuItem administrationStammdatenM) {
		this.administrationStammdatenM = administrationStammdatenM;
	}

	public JMenuItem getBeendenM() {
		return beendenM;
	}

	public void setBeendenM(JMenuItem beendenM) {
		this.beendenM = beendenM;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

}
