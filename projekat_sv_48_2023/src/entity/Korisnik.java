package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Korisnik {
    protected String ime;
    protected String prezime;
    protected Pol pol;
    protected LocalDate datumRodjenja;
    protected String telefon;
    protected String adresa;
    protected String korisnickoIme;
    protected String lozinka;
    
	public Korisnik(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka) {
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		return "Ime: " + ime + "\nPrezime: " + prezime + "\nPol: " + pol + "\nDatum rodjenja: " + datumRodjenja.format(format)
				+ "\nTelefon: " + telefon + "\nAdresa: " + adresa + "\nKorisničko ime: " + korisnickoIme;
	}
	
	public String toFile() {
		return ime + "," + prezime + "," + pol + "," + datumRodjenja + "," + telefon + "," + adresa + ","
				+ korisnickoIme + "," + lozinka;
	}
}
