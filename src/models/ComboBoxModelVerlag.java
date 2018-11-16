package models;

import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import domain.Autor;
import domain.Verlag;

public class ComboBoxModelVerlag extends AbstractListModel implements ComboBoxModel{

	private List<Verlag> verlagListe;
	Verlag selection = null;
	
	public ComboBoxModelVerlag(List<Verlag> liste) {
		this.verlagListe = liste;
		listeSortieren();
		Verlag v = new Verlag();
		v.setName("-- Kein Verlag ausgewählt --");
		v.setId(-1);
		verlagListe.add(0, v);
	}
	
	private void listeSortieren() {
		verlagListe.sort(Comparator.comparing(Verlag::getName));
	}
	
	@Override
	public Object getElementAt(int index) {
		return verlagListe.get(index);
	}

	@Override
	public int getSize() {
		return verlagListe.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (Verlag)anItem;
		
	}

}
