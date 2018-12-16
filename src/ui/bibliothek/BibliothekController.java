package ui.bibliothek;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import domain.Adresse;
import domain.Bibliothek;
import domain.Ort;
import hilfsklassen.ButtonNamen;
import hilfsklassen.TextComponentLimit;
import services.BibliothekService;
import services.OrtService;
import ui.HauptController;
import ui.renderer.PlzRenderer;

/**
 * Controller f�r die die Stammdaten der Bibliothek.
 * 
 * @version 2.0 16.12.2018
 * @author Mike
 *
 */

public class BibliothekController {
	private BibliothekView bibliothekView;
	private BibliothekService bibliothekService;
	private OrtService ortService;
	private HauptController hauptController;

	public BibliothekController(BibliothekView view, HauptController hauptController) {
		bibliothekView = view;
		this.hauptController = hauptController;
		bibliothekService = new BibliothekService();
		ortService = new OrtService();
		initialisieren();
		control();
	}
	
	/**
	* Weist den Buttons ActionListeners zu und definiert MouseListeners.
	*/
	private void control() {
		// Speichern
		ActionListener sichernBibliothekActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bibliothek b = new Bibliothek();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					JOptionPane.showMessageDialog(null, "Bibliotheksstammdaten erfasst");
					// Pr�fung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					bibliothekService.aktualisierenBibliothek(b);
				}
			}
		};
		bibliothekView.getButtonPanel().getButton3().addActionListener(sichernBibliothekActionListener);

		// Schliessen
		ActionListener schliessenButtonActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hauptController.panelEntfernen();
			}
		};
		bibliothekView.getButtonPanel().getButton4().addActionListener(schliessenButtonActionListener);
		
		// Dropdown PLZ Neu/Bearbeiten
		ActionListener plzCbxListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<Ort> c = (JComboBox<Ort>) e.getSource();
				int ortId = c.getSelectedIndex();
				Ort ortFromService = ortService.suchenOrtById(ortId);
				Ort ort = new Ort();
				ort.setId(ortFromService.getId());
				ort.setPlz(ortFromService.getPlz());
				ort.setOrt(ortFromService.getOrt());
			}
		};
		bibliothekView.getPlzOrtCbx().addActionListener(plzCbxListener);
	}

	/**
	* Prueft die Feldwerte auf obligatorische Eingaben und korrekte Daten im Bereich Neuerfassung/Bearbeitung.
	* @return true: wenn alles korrekt, false: wenn nicht alle Pflichtfelder ausgef�llt oder ein falsches Datum eingegeben wurde. 
	* 				Die Leihfrist muss zwischen 0 und 365 Tagen sein.
	*/
	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (bibliothekView.getNameT().getText().isEmpty() || (bibliothekView.getLeihfristT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen.");
			keinInputFehler = false;
		}
		try {
			Integer.parseInt(bibliothekView.getLeihfristT().getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Ung�ltige Leihfrist. Die Leihfrist muss zwischen 0 und 365 Tagen liegen.");
			keinInputFehler = false;
			return keinInputFehler;
		} finally {

		}
		if (Integer.parseInt(bibliothekView.getLeihfristT().getText()) < 0
				|| Integer.parseInt(bibliothekView.getLeihfristT().getText()) > 365) {
			JOptionPane.showMessageDialog(null, "Ung�ltige Leihfrist. Die Leihfrist muss zwischen 0 und 365 Tagen liegen.");
			keinInputFehler = false;
		}
		return keinInputFehler;
	}

	/**
	* Kreiert ein Objekt aus den eingegebenen Werten im Bereich Neuerfassung/Bearbeitung.
	* @return Stammdaten-Objekt mit Werten aus der Neuerfassung/Bearbeitung
	*/
	private Bibliothek feldwertezuObjektSpeichern() {
		Bibliothek b = new Bibliothek();
		b.setId(1); // Es soll nur ein Objekt geben
		b.setName(bibliothekView.getNameT().getText());
		Ort ort = ortService.suchenOrtById(bibliothekView.getPlzOrtCbx().getSelectedIndex());
		b.setAdresse(new Adresse(bibliothekView.getStrasseUndNrT().getText(), ort));
		b.setEmail(bibliothekView.getEmailT().getText());
		b.setTelefon(bibliothekView.getTelT().getText());
		b.setLeihfrist(Integer.parseInt(bibliothekView.getLeihfristT().getText()));
		return b;
	}

	/**
	* Bef�llt die Felder mit den Initialen Werten
	*/
	private void biblioitheksFelderFuellen() {
		Bibliothek b = bibliothekService.suchenBibliothek();
		bibliothekView.getNameT().setText(b.getName());
		bibliothekView.getStrasseUndNrT().setText(b.getAdresse().getStrasse());
		bibliothekView.getPlzOrtCbx().setSelectedIndex(b.getAdresse().getOrt().getId());
		bibliothekView.getEmailT().setText(b.getEmail());
		bibliothekView.getTelT().setText(b.getTelefon());
		bibliothekView.getLeihfristT().setText(String.valueOf(b.getLeihfrist()));
	}

	/**
	* Setzt Werte f�r die Labels, f�gt den Eingabefeldern Limiten fuer die Anzahl Zeichen zu,
	* definiert die verwendeten Buttons aus dem ButtonPanel.
	*/
	public void initialisieren() {
		PlzRenderer plzR = new PlzRenderer();
		bibliothekView.getPlzOrtCbx().setRenderer(plzR);
		bibliothekView.getPlzOrtCbx().addItem(null);
		for (Ort o : ortService.suchenAlleOrte()) {
			bibliothekView.getPlzOrtCbx().addItem(o);
		}
		bibliothekView.getPlzOrtCbx().setMaximumRowCount(10);
		bibliothekView.getPlzOrtCbx().setSelectedIndex(0);
		bibliothekView.getNameL().setText("Name der Bibliothek:*");
		bibliothekView.getStrasseUndNrL().setText("Strasse & Nr.:");
		bibliothekView.getOrtL().setText("PLZ und Ort:");
		bibliothekView.getEmailL().setText("Email:");
		bibliothekView.getTelL().setText("Tel.:");
		bibliothekView.getLeihfristL().setText("Leihfrist:*");
		TextComponentLimit.addTo(bibliothekView.getNameT(), 30);
		TextComponentLimit.addTo(bibliothekView.getStrasseUndNrT(), 50);
		TextComponentLimit.addTo(bibliothekView.getEmailT(), 50);
		TextComponentLimit.addTo(bibliothekView.getTelT(), 30);
		TextComponentLimit.addTo(bibliothekView.getLeihfristT(), 3);
		bibliothekView.getButtonPanel().getButton1().setVisible(false);
		bibliothekView.getButtonPanel().getButton2().setVisible(false);
		bibliothekView.getButtonPanel().getButton3().setText(ButtonNamen.SICHERN.getName());
		bibliothekView.getButtonPanel().getButton4().setText(ButtonNamen.ABBRECHEN.getName());
		biblioitheksFelderFuellen();
	}
}
