package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import domain.DezKlassifikation;

/**
 * 
 * Enthält die Gruppenbegriffe der Dezimalklassifikation
 * 
 * @author Schmutz
 * @version 1.0 2018-11-17
 *
 */

public class ComboBoxModelDezKlass extends AbstractListModel implements ComboBoxModel{

	private List<DezKlassifikation> dezKlassListeUngefiltert;
	private List<DezKlassifikation> dezKlassListe;
	DezKlassifikation selection = null;
	
	public ComboBoxModelDezKlass(List<DezKlassifikation> liste) {
		this.dezKlassListeUngefiltert = liste;
		this.dezKlassListe = new ArrayList<>();
	
	}
	
	private void listeSortieren() {
		dezKlassListe.sort(Comparator.comparing(DezKlassifikation::getDezKlasse)
				.thenComparing(Comparator.comparing(DezKlassifikation::getBezeichnung)));
	}
	
	@Override
	public Object getElementAt(int index) {
		return dezKlassListe.get(index);
	}

	@Override
	public int getSize() {
		return dezKlassListe.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (DezKlassifikation)anItem;
		
	}
	
	public void filternNachGruppe(int id) {
		dezKlassListe.clear();
		for (DezKlassifikation d : dezKlassListeUngefiltert) {
			if (d.getGruppeId() == id) {
				dezKlassListe.add(d);
			}
		listeSortieren();
		fireContentsChanged(this,0, getSize());
		
		}
		
	}
	
	


}
