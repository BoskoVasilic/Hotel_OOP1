package model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import entity.DodatnaUsluga;
import entity.Rezervacija;
import manage.RezervacijaManager;

public class RezervacijeModel extends AbstractTableModel {
	private static final long serialVersionUID = 2374373187528297758L;
	private RezervacijaManager rm;
	private String[] columnNames = { "Id", "Datum prijave", "Datum odjave", "Tip sobe", "Broj ljudi", "Dodatne usluge", "Ukupna cena"};

	public RezervacijeModel(RezervacijaManager rm) {
		this.rm = rm;
	}
	
	@Override
	public int getRowCount() {
		return rm.getRezervacijeNaCekanju().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		Rezervacija r = rm.getRezervacijeNaCekanju().get(rowIndex);
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
			if (r.getDodatneUsluge().get(0) != null) {
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
	
	public void setFilter(boolean[] tipoviSoba, boolean[] dodatneUsluge) {
		ArrayList<Rezervacija> rezervacijeNaCekanju = rm.getRezervacijeNaCekanju();
        ArrayList<Rezervacija> rezervacijeFiltrirane = new ArrayList<Rezervacija>();
        for (Rezervacija r : rezervacijeNaCekanju) {
            boolean tipSobeOk = false;
            boolean dodatneUslugeOk = false;
            if (r.getTipSobe() == null) {
                tipSobeOk = true;
            } else {
                for (int i = 0; i < tipoviSoba.length; i++) {
                    if (tipoviSoba[i] && r.getTipSobe().getNaziv().equals(rm.getTipoviSoba().get(i).getNaziv())) {
                        tipSobeOk = true;
                        break;
                    }
                }
            }
            if (r.getDodatneUsluge().get(0) == null) {
                dodatneUslugeOk = true;
            } else {
                for (int i = 0; i < dodatneUsluge.length; i++) {
                    if (dodatneUsluge[i] && r.getDodatneUsluge().get(i).getNaziv().equals(rm.getDodatneUsluge().get(i).getNaziv())) {
                        dodatneUslugeOk = true;
                        break;
                    }
                }
            }
            if (tipSobeOk && dodatneUslugeOk) {
                rezervacijeFiltrirane.add(r);
            }
        }
        rm.setRezervacijeNaCekanju(rezervacijeFiltrirane);
    }
		
	}

}
