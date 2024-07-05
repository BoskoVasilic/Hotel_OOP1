package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.DodatnaUsluga;
import entity.Rezervacija;
import manage.RezervacijaManager;

public class DnevniOdlasciDolasciModel extends AbstractTableModel {
	private static final long serialVersionUID = 6242728386998503424L;
	private RezervacijaManager rm;
	private String[] columnNames = { "Id", "Datum prijave", "Datum odjave", "Tip sobe", "Broj ljudi", "Dodatne usluge", "Ukupna cena", "Rezevisano na"};
	private boolean odlasci;
	
	public DnevniOdlasciDolasciModel(RezervacijaManager rm, boolean odlasci) {
		this.rm = rm;
		this.odlasci = odlasci;
	}
	
	@Override
	public int getRowCount() {
		if (odlasci) {
			return rm.getDnevniOdlasciDolasci(LocalDate.now()).get("Odlasci").size();
		}else {
			return rm.getDnevniOdlasciDolasci(LocalDate.now()).get("Dolasci").size();
		}
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (odlasci) {
			if (rowIndex >= rm.getDnevniOdlasciDolasci(LocalDate.now()).get("Odlasci").size()) {
				switch (columnIndex) {
				case 0:
					return "";
				case 1:
					return "Nema";
				case 2:
					return "rezervacija";
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
				default:
					return null;
				}
	        }
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			Rezervacija r = rm.getDnevniOdlasciDolasci(LocalDate.now()).get("Odlasci").get(rowIndex);
			switch (columnIndex) {
			case 0:
				return r.getId();
			case 1:
				return r.getDatumPrijave().format(format);
			case 2:
				return r.getDatumOdjave().format(format);
			case 3:
				return r.getTipSobe().getNaziv();
			case 4:
				return r.getBrojLjudi();
			case 5:
				StringBuilder sb = new StringBuilder();
				ArrayList<DodatnaUsluga> du = new ArrayList<DodatnaUsluga>();
				if (r.getDodatneUsluge().get(0) != null || (r.getDodatneUsluge().size() > 0 && r.getDodatneUsluge().get(1) != null)) {
					du = r.getDodatneUsluge();
					for (int i = 0; i < du.size(); i++) {
						sb.append(du.get(i).getNaziv());
						if (i != du.size() - 1) {
							sb.append(", ");
						}
					}
				return sb.toString();
			} else {
				return "nema";
			}
			case 6:
				return r.getUkupnaCena();
			case 7:
				return r.getRezervisao().getKorisnickoIme();
			default:
				return null;
			}
		}else {
			if (rowIndex >= rm.getDnevniOdlasciDolasci(LocalDate.now()).get("Dolasci").size()) {
				switch (columnIndex) {
				case 0:
					return "";
				case 1:
					return "Nema";
				case 2:
					return "rezervacija";
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
				default:
					return null;
				}
	        }
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			Rezervacija r = rm.getDnevniOdlasciDolasci(LocalDate.now()).get("Dolasci").get(rowIndex);
			switch (columnIndex) {
			case 0:
				return r.getId();
			case 1:
				return r.getDatumPrijave().format(format);
			case 2:
				return r.getDatumOdjave().format(format);
			case 3:
				return r.getTipSobe().getNaziv();
			case 4:
				return r.getBrojLjudi();
			case 5:
				StringBuilder sb = new StringBuilder();
				ArrayList<DodatnaUsluga> du = new ArrayList<DodatnaUsluga>();
				if (r.getDodatneUsluge().get(0) != null || (r.getDodatneUsluge().size() > 0 && r.getDodatneUsluge().get(1) != null)) {
					du = r.getDodatneUsluge();
					for (int i = 0; i < du.size(); i++) {
						sb.append(du.get(i).getNaziv());
						if (i != du.size() - 1) {
							sb.append(", ");
						}
					}
				return sb.toString();
			} else {
				return "nema";
			}
			case 6:
				return r.getUkupnaCena();
			case 7:
				return r.getRezervisao().getKorisnickoIme();
			default:
				return null;
			}
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
