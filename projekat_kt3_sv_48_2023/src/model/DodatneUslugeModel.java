package model;

import javax.swing.table.AbstractTableModel;

import entity.DodatnaUsluga;
import manage.DodatnaUslugaManager;

public class DodatneUslugeModel extends AbstractTableModel{
	private static final long serialVersionUID = 2253394989734132677L;
	private DodatnaUslugaManager dm;
	private String[] columnNames = { "Naziv"};

	public DodatneUslugeModel (DodatnaUslugaManager dm) {
		this.dm = dm;
	}
	
	@Override
	public int getRowCount() {
		return dm.getDodatneUsluge().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DodatnaUsluga du = dm.getDodatneUsluge().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return du.getNaziv();
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
