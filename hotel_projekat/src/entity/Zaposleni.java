package entity;

public class Zaposleni extends Korisnik {
	protected StrucnaSprema strucnaSprema;
	protected int godineStaza;
	protected double plata;
	protected Osnovica osnovica;
	
	public Zaposleni(String ime, String prezime, Pol pol, String datumRodjenja, String telefon, String adresa,
			String korisnickoIme, String lozinka, StrucnaSprema strucnaSprema, int godineStaza, Osnovica osnovica) {
		super(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
		this.strucnaSprema = strucnaSprema;
		this.godineStaza = godineStaza;
		this.osnovica = osnovica;
		this.plata = this.formirajPlatu();
	}

	public StrucnaSprema getStrucnaSprema() {
		return strucnaSprema;
	}

	public void setStrucnaSprema(StrucnaSprema strucnaSprema) {
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
	
	public Osnovica getOsnovica() {
		return osnovica;
	}
	
	public void setOsnovica(Osnovica osnovica) {
		this.osnovica = osnovica;
	}
	
	protected double formirajPlatu() {
        double koeficijent = strucnaSprema.value();
		double osnovica = this.osnovica.value();
		double dodatak = godineStaza * 50 / 100;
		plata = koeficijent * osnovica * ((100 + dodatak) / 100);
		return plata;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Stručna sprema: " + strucnaSprema + "\nGodine staža: " + godineStaza + "\nPlata: "
				+ Math.round(plata) + "\n";
	}

}
