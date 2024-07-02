package entity;

public class Osnovica {
	private double vrednost;
	private Pozicija pozicija;
	
	public Osnovica(double vrednost, Pozicija pozicija) {
		this.vrednost = vrednost;
		this.pozicija = pozicija;
	}
	
	public double getVrednost() {
		return vrednost;
	}
	
	public void setVrednost(double vrednost) {
		this.vrednost = vrednost;
	}
	
	public Pozicija getPozicija() {
		return pozicija;
	}
	
	public void setPozicija(Pozicija pozicija) {
		this.pozicija = pozicija;
	}
	
	@Override
	public String toString() {
		return pozicija + " " + vrednost;
	}
	
	public String toFile() {
		return vrednost + "," + pozicija;
	}
	
}
