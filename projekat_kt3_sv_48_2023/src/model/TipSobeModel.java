package model;

import javax.swing.table.AbstractTableModel;

import entity.TipSobe;
import manage.TipSobeManager;

public class TipSobeModel extends AbstractTableModel {
	private static final long serialVersionUID = 2897552111610688796L;
	private TipSobeManager tsm;
	private String[] columnNames = { "Naziv", "Broj kreveta", "Broj osoba"};

	public TipSobeModel (TipSobeManager tsm) {
		this.tsm = tsm;
	}
	
	@Override
	public int getRowCount() {
		return tsm.getTipoviSoba().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TipSobe ts = tsm.getTipoviSoba().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return ts.getNaziv();
		case 1:
			return ts.getBrojKreveta();
		case 2:
			return ts.getBrojOsoba();
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
