package model;

import java.time.LocalDate;

import javax.swing.table.AbstractTableModel;

import entity.Sobarica;
import manage.RezervacijaManager;
import manage.SobaricaManager;

public class SobariceSobeModel extends AbstractTableModel {
	private static final long serialVersionUID = 5243783007588366891L;
	private SobaricaManager sm;
	private RezervacijaManager rm;
	private LocalDate datumPocetka;
	private LocalDate datumKraja;
	private String[] columnNames = { "Ime", "Prezime", "Pol", "Datum rođenja", "Telefon", "Adresa", "Korisničko ime", "Stručna sprema", "Godine staža", "Plata", "Spremljenih soba"};

	public SobariceSobeModel (SobaricaManager sm, LocalDate datumPocetka, LocalDate datumKraja) {
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
		return sm.getSobarice().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Sobarica s = sm.getSobarice().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return s.getIme();
		case 1:
			return s.getPrezime();
		case 2:
			return s.getPol();
		case 3:
			return s.getDatumRodjenja();
		case 4:
			return s.getTelefon();
		case 5:
			return s.getAdresa();
		case 6:
			return s.getKorisnickoIme();
		case 7:
			return s.getStrucnaSprema();
		case 8:
			return s.getGodineStaza();
		case 9:
			return s.getPlata();
		case 10:
			return rm.getBrojSredjenihSobaPoSobarici(datumPocetka, datumKraja, s.getKorisnickoIme());
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
