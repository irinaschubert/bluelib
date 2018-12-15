package models;

import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import domain.Status;
/**
 * Verwaltet die Werte der Statusscomboboxen
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
@SuppressWarnings("serial")
public class ComboBoxModelStatus extends AbstractListModel<Status> implements ComboBoxModel<Status>{

	private List<Status> statusListe;
	Status selection = null;
	
	public ComboBoxModelStatus(List<Status> liste) {
		this.statusListe = liste;
		listeSortieren();
	}
	
	private void listeSortieren() {
		statusListe.sort(Comparator.comparing(Status::getBezeichnung));
	}
	
	@Override
	public Status getElementAt(int index) {
		return statusListe.get(index);
	}

	@Override
	public int getSize() {
		return statusListe.size();
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selection = (Status)anItem;
		
	}
	
	public int getPositionStatus(Status Status) {
		int count = 0;
		while(statusListe.get(count).getId() != Status.getId()){
			count++;
		}
		return count;
	}
	
}
