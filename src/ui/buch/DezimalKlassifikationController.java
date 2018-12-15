package ui.buch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import domain.DezKlassifikation;
import domain.DezKlassifikationGrpe;
import models.ComboBoxModelDezKlass;
import models.ComboBoxModelDezKlassGrpe;
import services.NormdatenService;

/**
 * 
 * Controller zur Auswahl einer Dezimalklassifikation
 * 
 * @version 1.0 2018-11-20
 * @author Ueli
 *
 */

public class DezimalKlassifikationController {
	protected DezimalKlassifikationView dezimalKlassifikationView;
	private NormdatenService normdatenService;
	private BuchController buchController;
	private ComboBoxModelDezKlass comboBoxModelDezKlass;

	public DezimalKlassifikationController(DezimalKlassifikationView view, BuchController buchController) {
		dezimalKlassifikationView = view;
		this.normdatenService = new NormdatenService();
		this.comboBoxModelDezKlass = new ComboBoxModelDezKlass(normdatenService.alleDezKlassifikationen());
		this.buchController = buchController;
		initialisieren();
		control();
	}

	private void control() {
		dezimalKlassifikationView.getDezKlassifikationGrpeCbx()
				.addActionListener(dezKlassGruppenauswahlActionListener());
		dezimalKlassifikationView.getStandardButtonPanel().getButton1().addActionListener(uebernehmenActionListener());
	}

	private void initialisieren() {
		dezimalKlassifikationView.getStandardButtonPanel().getButton1().setText("Übernehmen");
		dezimalKlassifikationView.getStandardButtonPanel().getButton2().setVisible(false);
		dezimalKlassifikationView.getStandardButtonPanel().getButton3().setVisible(false);
		dezimalKlassifikationView.getStandardButtonPanel().getButton4().setVisible(false);
		dezimalKlassifikationView.getDezKlasseGrpeL().setText("Gruppe Dezimalklassifikation: ");
		dezimalKlassifikationView.getDezKlasseL().setText("Dezimalklassifikation: ");
		dezimalKlassifikationView.getDezKlassifikationGrpeCbx()
				.setModel(new ComboBoxModelDezKlassGrpe(normdatenService.alleDezKlassifikationenGruppen()));
		dezimalKlassifikationView.getDezKlassifikationCbx().setModel(comboBoxModelDezKlass);

	}

	private ActionListener uebernehmenActionListener() {
		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dezimalKlassifikationView.getDezKlassifikationCbx().getSelectedIndex() < 0) {
					JOptionPane.showMessageDialog(null, "Bitte eine Dezimalklassifikation auswählen.");
				} else {
					DezKlassifikation d = (DezKlassifikation) dezimalKlassifikationView.getDezKlassifikationCbx()
							.getModel().getSelectedItem();
					buchController.signaturSetzen(d.getDezKlasse());
					dezimalKlassifikationView.schliessen();
				}

			}

		};
		return uebernehmenButtonActionListener;
	}

	private ActionListener dezKlassGruppenauswahlActionListener() {
		ActionListener dezKlassGruppenauswahlActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dezimalKlassifikationView.getDezKlassifikationGrpeCbx().getSelectedIndex() > -1) {
					DezKlassifikationGrpe dg = (DezKlassifikationGrpe) dezimalKlassifikationView
							.getDezKlassifikationGrpeCbx().getModel().getSelectedItem();
					comboBoxModelDezKlass.filternNachGruppe(dg.getId());
					dezimalKlassifikationView.getDezKlassifikationCbx().setSelectedIndex(-1);
				}

			}

		};
		return dezKlassGruppenauswahlActionListener;
	}

}
