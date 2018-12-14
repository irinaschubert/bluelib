package ui.Bibliothek;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domain.Adresse;
import domain.Autor;
import domain.Bibliothek;
import domain.Ort;
import domain.Verlag;
import hilfsklassen.ButtonNamen;
import hilfsklassen.DateConverter;
import hilfsklassen.TextComponentLimit;
import models.TableModelVerlag;
import services.NormdatenService;
import services.OrtService;
import services.Verifikation;
import ui.HauptController;
import ui.renderer.PlzRenderer;

/**
 * 
 * Controller für die die Stammdaten der Bibliothek
 * 
 * @version 2.0 31.10.2018
 * @author mike
 *
 */

public class BibliothekController {
	private BibliothekView bibliothekView;
	private NormdatenService normdatenService;
	private OrtService ortService;
	private HauptController hauptController;

	public BibliothekController(BibliothekView view, HauptController hauptController) {
		bibliothekView = view;
		this.hauptController = hauptController;
		normdatenService = new NormdatenService();
		ortService = new OrtService();
		initialisieren();
		control();
	}

	private void control() {
		ActionListener sichernBibliothekActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Bibliothek b = new Bibliothek();
				if (inputValidierungSpeichern()) {
					b = feldwertezuObjektSpeichern();
					JOptionPane.showMessageDialog(null, "Bibliotheksstammdaten erfasst");
					// Prüfung, ob ein neuer Autor erfasst wurde oder ein Autor aktialisiert wird
					normdatenService.aktualisierenBibliothek(b);
				}
			}
		};
		bibliothekView.getButtonPanel().getButton3().addActionListener(sichernBibliothekActionListener);

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

	private boolean inputValidierungSpeichern() {
		boolean keinInputFehler = true;
		if (bibliothekView.getNameT().getText().isEmpty() || (bibliothekView.getLeihfristT().getText().isEmpty())) {
			JOptionPane.showMessageDialog(null, "Bitte alle Pflichtfelder erfassen");
			keinInputFehler = false;
		}
		try {
			Integer.parseInt(bibliothekView.getLeihfristT().getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Üngültige Leihfrist. Bitte eine Zahl zwischen 0 und 365 eingeben");
			// e.printStackTrace();
			keinInputFehler = false;
			return keinInputFehler;
		} finally {

		}
		if (Integer.parseInt(bibliothekView.getLeihfristT().getText()) < 0
				|| Integer.parseInt(bibliothekView.getLeihfristT().getText()) > 365) {
			// System.out.println(Integer.parseInt(bibliothekView.getLeihfristT().getText())
			// >= 0);
			JOptionPane.showMessageDialog(null, "Üngültige Leihfrist. Die Leihfrist kann von 0 bis 365 Tagen sein");
			keinInputFehler = false;
		}
		return keinInputFehler;
	}

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

	private void biblioitheksFelderFuellen() {
		Bibliothek b = normdatenService.anzeigenBibliothek(); // Bibliothek bestücken
		bibliothekView.getNameT().setText(b.getName());
		bibliothekView.getStrasseUndNrT().setText(b.getAdresse().getStrasse());
		bibliothekView.getPlzOrtCbx().setSelectedIndex(b.getAdresse().getOrt().getId());
		bibliothekView.getEmailT().setText(b.getEmail());
		bibliothekView.getTelT().setText(b.getTelefon());
		bibliothekView.getLeihfristT().setText(String.valueOf(b.getLeihfrist()));

	}

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
