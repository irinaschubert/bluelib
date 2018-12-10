package models;

import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import domain.Autor;

/**
 * Verwaltet die Werte der Auoren-Bomboboxen
 * 
 * @version 1.0 2018-11-28
 * @author Ueli
 *
 */

public class ComboBoxModelAutor extends AbstractListModel implements ComboBoxModel{

	private List<Autor> autorenListe;
	Autor selection = null;
	
	public ComboBoxModelAutor(List<Autor> liste) {
		this.autorenListe = liste;
		listeSortieren();
	
	}
	
	private void listeSortieren() {
		autorenListe.sort(Comparator.comparing(Autor::getName)
				.thenComparing(Comparator.comparing(Autor::getVorname)));
	}
	
	@Override
	public Object getElementAt(int index) {
		return autorenListe.get(index);
	}

	@Override
	public int getSize() {
		return autorenListe.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (Autor)anItem;
		
	}
	
	public void leerenEintragErstellen() {
		Autor a = new Autor();
		a.setName("-- Kein Autor ausgewählt --");
		a.setVorname("");
		a.setId(-1);
		autorenListe.add(0, a);
	}
	


}
