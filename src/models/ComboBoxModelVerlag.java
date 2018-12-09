package models;

import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import domain.Verlag;
/**
 * @version 0.1 16.10.2018
 * @author irina
 */
@SuppressWarnings("serial")
public class ComboBoxModelVerlag extends AbstractListModel<Verlag> implements ComboBoxModel<Verlag>{

	private List<Verlag> verlagListe;
	Verlag selection = null;
	
	public ComboBoxModelVerlag(List<Verlag> liste) {
		this.verlagListe = liste;
		listeSortieren();
	}
	
	private void listeSortieren() {
		verlagListe.sort(Comparator.comparing(Verlag::getName));
	}
	
	@Override
	public Verlag getElementAt(int index) {
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
	
	public void leerenEintragErstellen() {
		Verlag v = new Verlag();
		v.setName("-- Kein Verlag ausgewählt --");
		v.setId(-1);
		verlagListe.add(0, v);
	}
	
	public int getPositionVerlag(Verlag verlag) {
		int count = 0;
		while(verlagListe.get(count).getId() != verlag.getId()){
			count++;
		}
		return count;
	}
	
}
