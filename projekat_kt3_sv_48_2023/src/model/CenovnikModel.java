package model;

import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

import entity.Cenovnik;
import manage.CenovnikManager;

public class CenovnikModel extends AbstractTableModel {
	private static final long serialVersionUID = 4124398595084479066L;
	private CenovnikManager cm;
	private String[] columnNames = { "Pocetak vazenja", "Kraj vazenja"};

	public CenovnikModel (CenovnikManager cm) {
		this.cm = cm;
	}
	
	@Override
	public int getRowCount() {
		return cm.getCenovnici().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		Cenovnik c = cm.getCenovnici().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return c.getPocetakVazenja().format(format);
		case 1:
			return c.getKrajVazenja().format(format);
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
