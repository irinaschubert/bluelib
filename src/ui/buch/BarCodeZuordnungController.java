package ui.buch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import services.MedienhandlingService;
import services.Verifikation;

/**
 * 
 * Controller für die View zur Überprüfung und Zuordnung der Barcodes
 * 
 * @author Ueli
 * @version 1.0 2018-11-28
 *
 */

public class BarCodeZuordnungController {
	protected BarCodeZuordnungView barCodeZuordnungView;
	private BuchController buchController;

	public BarCodeZuordnungController(BarCodeZuordnungView view, BuchController buchController) {
		barCodeZuordnungView = view;
		this.buchController = buchController;
		initialisieren();
		control();
	}

	private void control() {
		barCodeZuordnungView.getBarCodeT().addKeyListener(barcodeScanningKeyAdapter());
		barCodeZuordnungView.getUebernehmenB().addActionListener(uebernehmenActionListener());
	}

	private void initialisieren() {
		barCodeZuordnungView.getBarCodeL().setText("Barcode: ");
		barCodeZuordnungView.getUebernehmenB().setText("Übernehmen");

	}

	private ActionListener uebernehmenActionListener() {
		ActionListener uebernehmenButtonActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				barCodeUeberpruefenUndUebergeben();
			}

		};
		return uebernehmenButtonActionListener;
	}

	private KeyAdapter barcodeScanningKeyAdapter() {

		KeyAdapter barcodeScanningKeyListener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					barCodeUeberpruefenUndUebergeben();

				}
			}

		};
		return barcodeScanningKeyListener;
	}

	private Boolean barcodeUeberpruefen() {
		Boolean r = true;
		Verifikation v = new Verifikation();
		MedienhandlingService medienhandlingService = new MedienhandlingService();
		v = medienhandlingService.istBarcode(barCodeZuordnungView.getBarCodeT().getText());
		if (!v.isAktionErfolgreich()) {
			JOptionPane.showMessageDialog(null, v.getNachricht());
			r = false;
		} else {
			int barCode = Integer.parseInt(barCodeZuordnungView.getBarCodeT().getText());
			v = medienhandlingService.barcodeZugeordnet(barCode);
			if (v.isAktionErfolgreich()) {
				JOptionPane.showMessageDialog(null, v.getNachricht());
				r = false;
			}
		}

		return r;
	}

	private void barCodeUeberpruefenUndUebergeben() {
		if (barcodeUeberpruefen()) {
			buchController.barCodeSetzen(barCodeZuordnungView.getBarCodeT().getText());
			barCodeZuordnungView.schliessen();
		}
	}

}
