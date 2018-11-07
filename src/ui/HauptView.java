package ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class HauptView extends JFrame{
	private JMenu ausleiheM = new JMenu("Ausleihe");
	private JMenuItem ausleiheAusleiheM = new JMenuItem("Ausleihe");
	private JMenuItem ausleiheRueckgabeM = new JMenuItem("Rückgabe");
	private JMenu medienM = new JMenu("Medien");
	private JMenuItem medienBuchM = new JMenuItem("Buch");
	private JMenuItem medienAutorM = new JMenuItem("Autor");
	private JMenuItem medienVerlagM = new JMenuItem("Verlag");
	private JMenu benutzerM = new JMenu("Benutzer");
	private JMenuItem benutzerBenutzerM = new JMenuItem("Benutzer");
	private JMenuItem benutzerLeihlisteM = new JMenuItem("Leihliste");
	private JMenu auswertungM = new JMenu("Auswertung");
	private JMenu inventurM = new JMenu("Inventur");
	private JMenu administrationM = new JMenu("Administration");
	private JMenuItem administrationMitarbeiterM = new JMenuItem("Mitarbeiter");
	private JMenuItem administrationSchagworteM = new JMenuItem("Schlagworte");
	private JMenuItem administrationStammdatenM = new JMenuItem("Stammdaten");
	private JMenuItem beendenM = new JMenuItem("Beenden");

	
	public HauptView(String name) {

		setJMenuBar(erstelleMenuBar());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setVisible(true);
	}
	
	private JMenuBar erstelleMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(erstelleAusleiheMenu());
		menuBar.add(erstelleMedienMenu());
		menuBar.add(erstelleAuswertungMenu());
		menuBar.add(erstelleInventurMenu());
		menuBar.add(erstelleAdministrationMenu());
		menuBar.add(erstelleBeenden());
		return menuBar;
	}
		
	
	private JMenu erstelleAusleiheMenu() {
		ausleiheM.add(ausleiheAusleiheM);
		ausleiheM.add(ausleiheRueckgabeM);
		return ausleiheM;
	}
	
	private JMenu erstelleMedienMenu() {
		medienM.add(medienBuchM);
		medienM.add(medienAutorM);
		medienM.add(medienVerlagM);
		return medienM;
				
	}
	
	private JMenu erstelleBenutzerMenu() {
		benutzerM.add(benutzerBenutzerM);
		benutzerM.add(benutzerLeihlisteM);
		return benutzerM;
	}
	
	private JMenu erstelleAuswertungMenu() {
		return auswertungM;
	}
	
	private JMenu erstelleInventurMenu() {
		return inventurM;
	}
	
	private JMenu erstelleAdministrationMenu() {
		administrationM.add(administrationMitarbeiterM);
		administrationM.add(administrationSchagworteM);
		administrationM.add(administrationStammdatenM);
		return administrationM;
	}
	
	private JMenuItem erstelleBeenden() {
		return beendenM;
	}


	public JMenu getAusleiheM() {
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


	public JMenu getMedienM() {
		return medienM;
	}


	public void setMedienM(JMenu medienM) {
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


	public JMenu getBenutzerM() {
		return benutzerM;
	}


	public void setBenutzerM(JMenu benutzerM) {
		this.benutzerM = benutzerM;
	}


	public JMenuItem getBenutzerBenutzerM() {
		return benutzerBenutzerM;
	}


	public void setBenutzerBenutzerM(JMenuItem benutzerBenutzerM) {
		this.benutzerBenutzerM = benutzerBenutzerM;
	}


	public JMenuItem getBenutzerLeihlisteM() {
		return benutzerLeihlisteM;
	}


	public void setBenutzerLeihlisteM(JMenuItem benutzerLeihlisteM) {
		this.benutzerLeihlisteM = benutzerLeihlisteM;
	}


	public JMenu getAuswertungM() {
		return auswertungM;
	}


	public void setAuswertungM(JMenu auswertungM) {
		this.auswertungM = auswertungM;
	}


	public JMenu getInventurM() {
		return inventurM;
	}


	public void setInventurM(JMenu inventurM) {
		this.inventurM = inventurM;
	}


	public JMenu getAdministrationM() {
		return administrationM;
	}


	public void setAdministrationM(JMenu administrationM) {
		this.administrationM = administrationM;
	}


	public JMenuItem getAdministrationMitarbeiterM() {
		return administrationMitarbeiterM;
	}


	public void setAdministrationMitarbeiterM(JMenuItem administrationMitarbeiterM) {
		this.administrationMitarbeiterM = administrationMitarbeiterM;
	}


	public JMenuItem getAdministrationSchagworteM() {
		return administrationSchagworteM;
	}


	public void setAdministrationSchagworteM(JMenuItem administrationSchagworteM) {
		this.administrationSchagworteM = administrationSchagworteM;
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


	public void setBeendenM(JMenu beendenM) {
		this.beendenM = beendenM;
	}


	

}
