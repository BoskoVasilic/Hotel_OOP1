package model;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Oprema;
import entity.Soba;
import manage.RezervacijaManager;
import manage.SobaManager;

public class PrikazSobaModel extends AbstractTableModel{
	private static final long serialVersionUID = -1717267938542531036L;
	private SobaManager sm;
	private RezervacijaManager rm;
	private LocalDate datumPocetka;
	private LocalDate datumKraja;
	private String[] columnNames = { "Broj", "Tip", "Opremljenost", "Pusacka", "Trenutni status", "Broj noćenja", "Prihod"};

	public PrikazSobaModel (SobaManager sm, LocalDate datumPocetka, LocalDate datumKraja) {
		this.sm = sm;
		this.rm = RezervacijaManager.getInstance();
	}
	
	public void setDatumPocetka(LocalDate datumPocetka) {
		this.datumPocetka = datumPocetka;
	}
	
	public void setDatumKraja(LocalDate datumKraja) {
		this.datumKraja = datumKraja;
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
		case 5:
			return rm.getBrojNocenjaPoSobi(datumPocetka, datumKraja, s.getBrojSobe());
		case 6:
			return rm.getPrihodPoSobi(datumPocetka, datumKraja, s.getBrojSobe());
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
