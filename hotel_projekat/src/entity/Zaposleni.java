package entity;

public class Zaposleni extends Korisnik {
	protected String strucnaSprema;
	protected int godineStaza;
	protected double plata;
	
	public Zaposleni(String ime, String prezime, String pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, String strucnaSprema, int godineStaza, double plata) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.strucnaSprema = strucnaSprema;
		this.godineStaza = godineStaza;
		this.plata = plata;
	}

	public String getStrucnaSprema() {
		return strucnaSprema;
	}

	public void setStrucnaSprema(String strucnaSprema) {
		this.strucnaSprema = strucnaSprema;
	}

	public int getGodineStaza() {
		return godineStaza;
	}

	public void setGodineStaza(int godineStaza) {
		this.godineStaza = godineStaza;
	}

	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}
	
	public double formirajPlatu() {
		double koeficijent = 0;
		switch (strucnaSprema) {
		case "I":
			koeficijent = 1.5;
			break;
		case "II":
			koeficijent = 1.3;
			break;
		case "III":
			koeficijent = 1.1;
			break;
		case "IV":
			koeficijent = 1.0;
			break;
		case "V":
			koeficijent = 0.9;
			break;
		case "VI":
			koeficijent = 0.8;
			break;
		case "VII":
			koeficijent = 0.7;
			break;
		}
		double osnovica = 25000;
		double dodatak = godineStaza * 1000;
		plata = koeficijent * osnovica + dodatak; // osnovica + (strucnaSprema * (godinaStaza preocentualno + 1) * koeficijent)
		return plata;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Stručna sprema: " + strucnaSprema + "\nGodine staža: " + godineStaza + "\nPlata: "
				+ plata + "\n";
	}

}
