package model;

import javax.swing.table.AbstractTableModel;

import entity.Oprema;
import manage.OpremaManager;

public class OpremaModel extends AbstractTableModel {
	private static final long serialVersionUID = -3737207584942651928L;
	private OpremaManager om;
	private String[] columnNames = { "Naziv"};

	public OpremaModel (OpremaManager om) {
		this.om = om;
	}
	
	@Override
	public int getRowCount() {
		return om.getOprema().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Oprema o = om.getOprema().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return o.getNaziv();
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
