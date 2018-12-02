package ui.buch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import domain.DezKlassifikation;
import domain.DezKlassifikationGrpe;
import models.ComboBoxModelDezKlass;
import models.ComboBoxModelDezKlassGrpe;
import services.NormdatenService;

public class DezimalKlassifikationController {
	protected DezimalKlassifikationView dezimalKlassifikationView;
	private List<DezKlassifikationGrpe> dezKlassGrpeLst;
	private List<DezKlassifikation> dezKlassLst;
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
		dezimalKlassifikationView.getDezKlassifikationGrpeCbx().addActionListener(dezKlassGruppenauswahlActionListener());
		dezimalKlassifikationView.getUebernehmenB().addActionListener(uebernehmenActionListener());
	}
	
	private void initialisieren() {
		dezimalKlassifikationView.getUebernehmenB().setText("�bernehmen");
		dezimalKlassifikationView.getDezKlasseGrpeL().setText("Gruppe Dezimalklassifikation: ");
		dezimalKlassifikationView.getDezKlasseL().setText("Dezimalklassifikation: ");
		dezimalKlassifikationView.getDezKlassifikationGrpeCbx().setModel(
				new ComboBoxModelDezKlassGrpe(normdatenService.alleDezKlassifikationenGruppen()));
		dezimalKlassifikationView.getDezKlassifikationCbx().setModel(comboBoxModelDezKlass);
		

	}
	
	
	
		private ActionListener uebernehmenActionListener() {
			ActionListener uebernehmenButtonActionListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (dezimalKlassifikationView.getDezKlassifikationCbx().getSelectedIndex() < 0) {
					JOptionPane.showMessageDialog(null, "Bitte eine Dezimalklassifikation ausw�hlen.");
				}
					else {
						DezKlassifikation d = (DezKlassifikation) dezimalKlassifikationView.getDezKlassifikationCbx().getModel().getSelectedItem();
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
						DezKlassifikationGrpe dg = (
								DezKlassifikationGrpe) dezimalKlassifikationView.getDezKlassifikationGrpeCbx().getModel().getSelectedItem();
						comboBoxModelDezKlass.filternNachGruppe(dg.getId());
						dezimalKlassifikationView.getDezKlassifikationCbx().setSelectedIndex(-1);
					}

				}

			};
			return dezKlassGruppenauswahlActionListener;
		}
	
	
}