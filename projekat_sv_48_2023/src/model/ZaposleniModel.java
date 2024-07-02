package model;

import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

import entity.Zaposleni;
import manage.ZaposleniManager;

public class ZaposleniModel extends AbstractTableModel {
	private static final long serialVersionUID = -7818705306842688231L;
	ZaposleniManager zm;
	private String[] columnNames = { "Ime", "Prezime", "Pol", "Datum rođenja", "Telefon", "Adresa", "Korisničko ime", "Stručna sprema", "Godine staža", "Plata", "Pozicija"};

	public ZaposleniModel(ZaposleniManager zm) {
		this.zm = zm;
	}
	
	@Override
	public int getRowCount() {
		return zm.getZaposleni().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= zm.getZaposleni().size()) {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return "Nema";
			case 2:
				return "zaposlenih";
			case 3:
				return "za";
			case 4:
				return "prikaz!";
			case 5:
				return "";
			case 6:
				return "";
			case 7:
				return "";
			case 8:
				return "";
			case 9:
				return "";
			case 10:
				return "";
			default:
				return null;
			}
        }
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		Zaposleni z = zm.getZaposleni().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return z.getIme();
		case 1:
			return z.getPrezime();
		case 2:
			return z.getPol().toString();
		case 3:
			return z.getDatumRodjenja().format(format);
		case 4:
			return z.getTelefon();
		case 5:
			return z.getAdresa();
		case 6:
			return z.getKorisnickoIme();
		case 7:
			return z.getStrucnaSprema().toString();
		case 8:
			return String.valueOf(z.getGodineStaza());
		case 9:
			return String.valueOf((int) z.getPlata());
		case 10:
			return z.getOsnovica().getPozicija().toString();
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
