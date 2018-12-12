package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import domain.DezKlassifikationGrpe;

/**
 * Verwaltet die Gruppen der Dezimalklassifikationen
 *
 * @author Ueli
 * @version 1.0 2018-11-17
 *
 */

public class ComboBoxModelDezKlassGrpe extends AbstractListModel implements ComboBoxModel{

	private List<DezKlassifikationGrpe> dezKlassGrpeListe;
	DezKlassifikationGrpe selection = null;
	
	public ComboBoxModelDezKlassGrpe(List<DezKlassifikationGrpe> liste) {
		this.dezKlassGrpeListe = liste;
		listeSortieren();
	
	}
	
	private void listeSortieren() {
		dezKlassGrpeListe.sort(Comparator.comparing(DezKlassifikationGrpe::getGruppe)
				.thenComparing(Comparator.comparing(DezKlassifikationGrpe::getBezeichnung)));
	}
	
	@Override
	public Object getElementAt(int index) {
		return dezKlassGrpeListe.get(index);
	}

	@Override
	public int getSize() {
		return dezKlassGrpeListe.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (DezKlassifikationGrpe)anItem;
		
	}
	
	


}
