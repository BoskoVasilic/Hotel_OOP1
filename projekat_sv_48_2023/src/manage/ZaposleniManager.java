package manage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import entity.Osnovica;
import entity.Pol;
import entity.Pozicija;
import entity.Sobarica;
import entity.StrucnaSprema;
import entity.Zaposleni;

public class ZaposleniManager {
	private AdministratorManager am;
	private RecepcionerManager rm;
	private SobaricaManager sm;
	private OsnovicaManager om = OsnovicaManager.getInstance();
	
	public ZaposleniManager() {
		this.am = new AdministratorManager("data/administratori.csv");
		this.rm = new RecepcionerManager("data/recepcioneri.csv");
		this.sm = SobaricaManager.getInstance();
		am.ucitajAdministratore();
		rm.ucitajRecepcionere();
	}
	
	public ArrayList<Zaposleni> getZaposleni() {
		ArrayList<Zaposleni> zaposleni = new ArrayList<Zaposleni>();
		zaposleni.addAll(am.getAdministratori());
		zaposleni.addAll(rm.getRecepcioneri());
		zaposleni.addAll(sm.getSobarice());
		return zaposleni;
	}
	
	public void prikaziSveZaposlene() {
		System.out.println("------------------------------");
		System.out.println("Administratori: ");
		System.out.println("------------------------------");
		for (Zaposleni z : am.getAdministratori()) {
			System.out.println(z);
		}
		System.out.println("------------------------------");
		System.out.println("Recepcioneri: ");
		System.out.println("------------------------------");
		for (Zaposleni z : rm.getRecepcioneri()) {
			System.out.println(z);
		}
		System.out.println("------------------------------");
		System.out.println("Sobarice: ");
		System.out.println("------------------------------");
		for (Sobarica s : sm.getSobarice()) {
			System.out.println(s);
		}
		System.out.println("------------------------------");
	}
	
	public Zaposleni kreirajZaposlenog(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon,
			String adresa, String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza,
			Pozicija pozicija) {
		Zaposleni z = new Zaposleni(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme,
				lozinka, strucnaSprema, godineStaza,
				new Osnovica(om.nadjiOsnovicu(pozicija).getVrednost(), pozicija));
		return z;
	}
	
	public void dodajZaposlenog(Zaposleni z) {
		if(z.getOsnovica().getPozicija() == Pozicija.Administrator) {
			am.dodajAdministratora(z.getIme(), z.getPrezime(), z.getPol(), z.getDatumRodjenja(), z.getTelefon(),
					z.getAdresa(), z.getKorisnickoIme(), z.getLozinka(), z.getStrucnaSprema(), z.getGodineStaza());
			am.sacuvajAdministratore();
		} else if (z.getOsnovica().getPozicija() == Pozicija.Recepcioner) {
			rm.dodajRecepcionera(z.getIme(), z.getPrezime(), z.getPol(), z.getDatumRodjenja(), z.getTelefon(),
					z.getAdresa(), z.getKorisnickoIme(), z.getLozinka(), z.getStrucnaSprema(), z.getGodineStaza());
			rm.sacuvajRecepcionere();
		} else if (z.getOsnovica().getPozicija() == Pozicija.Sobarica) {
			sm.dodajSobaricu(z.getIme(), z.getPrezime(), z.getPol(), z.getDatumRodjenja(), z.getTelefon(),
					z.getAdresa(), z.getKorisnickoIme(), z.getLozinka(), z.getStrucnaSprema(), z.getGodineStaza());
			sm.sacuvajSobarice();
		}
	}
	
	public void obrisiZaposlenog(String korisnickoIme, Pozicija pozicija) {
		if (pozicija == Pozicija.Administrator) {
			am.obrisiAdministratora(korisnickoIme);
			am.sacuvajAdministratore();
		} else if (pozicija == Pozicija.Recepcioner) {
			rm.obrisiRecepcionera(korisnickoIme);
			rm.sacuvajRecepcionere();
		} else if (pozicija == Pozicija.Sobarica) {
			sm.obrisiSobaricu(korisnickoIme);
			sm.sacuvajSobarice();
		}
	}
	
	public void izmeniZaposlenog(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon,
			String adresa, String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza,
			Pozicija pozicija) {
		if (pozicija == Pozicija.Administrator) {
			am.izmeniAdministratora(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka,
					strucnaSprema, godineStaza);
			am.sacuvajAdministratore();
		} else if (pozicija == Pozicija.Recepcioner) {
			rm.izmeniRecepcionera(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka,
					strucnaSprema, godineStaza);
			rm.sacuvajRecepcionere();
		} else if (pozicija == Pozicija.Sobarica) {
			sm.izmeniSobaricu(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka, strucnaSprema,
					godineStaza);
			sm.sacuvajSobarice();
		}
	}
	
	public Zaposleni nadjiZaposlenog(String korisnickoIme, Pozicija pozicija) {
		if (pozicija == Pozicija.Administrator) {
			return am.nadjiAdministratora(korisnickoIme);
		} else if (pozicija == Pozicija.Recepcioner) {
			return rm.nadjiRecepcionera(korisnickoIme);
		} else if (pozicija == Pozicija.Sobarica) {
			return sm.nadjiSobaricu(korisnickoIme);
		}
		return null;
	}
	
	public double getRashodi(LocalDate pocetakPerioda, LocalDate krajPerioda) {
		double rashodi = 0;
		for (Zaposleni z : getZaposleni()) {
			rashodi += z.getPlata();
		}
		long vremenski_period = ChronoUnit.DAYS.between(pocetakPerioda, krajPerioda);
		if (vremenski_period < 30) {
            rashodi *= vremenski_period / 30;
        }		
		
		return rashodi;
	}
}
