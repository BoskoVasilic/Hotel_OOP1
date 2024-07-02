package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Oprema;
import entity.Soba;
import manage.SobaManager;

public class SobeModel extends AbstractTableModel {	
	private static final long serialVersionUID = -2139457034727152647L;
	private SobaManager sm;
	private String[] columnNames = { "Broj", "Tip", "Opremljenost", "Pusacka", "Status"};

	public SobeModel (SobaManager sm) {
		this.sm = sm;
	}
	
	@Override
	public int getRowCount() {
		return sm.getSobe().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Soba s = sm.getSobe().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return s.getBrojSobe();
		case 1:
			return s.getTipSobe().getNaziv();
		case 2:
			StringBuilder sb = new StringBuilder();
			ArrayList<Oprema> o = new ArrayList<Oprema>();
			if (s.getOpremljenostSobe().get(0) != null) {
				o = s.getOpremljenostSobe();
				for (int i = 0; i < o.size(); i++) {
					sb.append(o.get(i).getNaziv());
					if (i != o.size() - 1) {
						sb.append(", ");
					}
				}
			return sb.toString();
        } else {
        	return "nema";
        }
		case 3:
			return s.isPusackaSoba();
		case 4:
			return s.getStatusSobe();
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
