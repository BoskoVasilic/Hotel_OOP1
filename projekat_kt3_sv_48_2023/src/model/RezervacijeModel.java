package model;

import javax.swing.table.AbstractTableModel;

import entity.Rezervacija;
import manage.RezervacijaManager;

public class RezervacijeModel extends AbstractTableModel {
	private static final long serialVersionUID = 2374373187528297758L;
	private RezervacijaManager rm;
	private String[] columnNames = { "Id", "Datum prijave", "Datum odjave", "Tip sobe", "Broj ljudi", "Dodatne usluge", "Ukupna cena"};

	public RezervacijeModel(RezervacijaManager rm) {
		this.rm = rm;
	}
	
	@Override
	public int getRowCount() {
		return rm.getRezervacije().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Rezervacija r = rm.getRezervacije().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return r.getId();

		default:
			return null;
		}

	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.getValueAt(0, columnIndex).getClass();
	}

}
