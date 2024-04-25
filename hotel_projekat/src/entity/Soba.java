package entity;

public class Soba {
	protected int brojSobe;
	protected int brojKreveta;
	protected StatusSobe statusSobe;
	protected double cena;
	
	public Soba(int brojSobe, int brojKreveta, StatusSobe statusSobe, double cena) {
		this.brojSobe = brojSobe;
		this.brojKreveta = brojKreveta;
		this.statusSobe = statusSobe;
		this.cena = cena;
	}

	public int getBrojSobe() {
		return brojSobe;
	}

	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public StatusSobe getStatusSobe() {
		return statusSobe;
	}

	public void setStatusSobe(StatusSobe statusSobe) {
		this.statusSobe = statusSobe;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	

}
