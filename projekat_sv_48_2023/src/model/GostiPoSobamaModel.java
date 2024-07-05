package model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.Gost;
import manage.RezervacijaManager;

public class GostiPoSobamaModel extends AbstractTableModel{
	private static final long serialVersionUID = 5289127995583628658L;
	private RezervacijaManager rm;
	private String[] columnNames = { "Ime", "Prezime", "Datum rođenja", "Broj pasoša", "Email", "Telefon", "Adresa", "Broj sobe"};

	public GostiPoSobamaModel (RezervacijaManager rm) {
		this.rm = rm;
	}
	
	@Override
	public int getRowCount() {
		return rm.getGostiPoSobama().values().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= rm.getGostiPoSobama().values().size()) {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return "Nema";
			case 2:
				return "gostiju";
			case 3:
				return "u";
			case 4:
				return "hotelu!";
			case 5:
				return "";
			case 6:
				return "";
			case 7:
				return "";
			default:
				return null;
			}
        }
		ArrayList<Gost> gostiList = new ArrayList<>(rm.getGostiPoSobama().values());
		ArrayList<Integer> sobe = new ArrayList<>(rm.getGostiPoSobama().keySet());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		Gost g = gostiList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return g.getIme();
		case 1:
			return g.getPrezime();
		case 2:
			return g.getDatumRodjenja().format(format);
		case 3:
			return g.getLozinka();
		case 4:
			return g.getKorisnickoIme();
		case 5:
			return g.getTelefon();
		case 6:
			return g.getAdresa();
		case 7:
			return sobe.get(rowIndex).toString();
			
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
