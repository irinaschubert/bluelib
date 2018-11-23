package models;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import domain.Schlagwort;

public class ComboBoxModelSchlagwort extends AbstractListModel implements ComboBoxModel{

	private List<Schlagwort> schlagwortListe;
	Schlagwort selection = null;
	
	public ComboBoxModelSchlagwort(List<Schlagwort> liste) {
		this.schlagwortListe = liste;
		listeSortieren();
	
	}
	
	private void listeSortieren() {
		schlagwortListe.sort(Comparator.comparing(Schlagwort::getSchlagwort));
	}
	
	@Override
	public Object getElementAt(int index) {
		return schlagwortListe.get(index);
	}

	@Override
	public int getSize() {
		return schlagwortListe.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (Schlagwort)anItem;
		
	}
	
	public void geloeschteEntfernen() {
		for (Iterator<Schlagwort> iter = schlagwortListe.iterator(); iter.hasNext(); ) {
			  Schlagwort element = iter.next();
			  if (element.getGeloescht()) {
			    iter.remove();
			  }
			}
	}

	


}
