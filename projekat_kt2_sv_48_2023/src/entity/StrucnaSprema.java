package entity;

public enum StrucnaSprema {
	I(3.45), II(3.50), III(3.60), IV(3.80), V(4.0), VI(4.4), VII(5.4), VIII(7.0);
	double koeficijent;
	
	StrucnaSprema(double koeficijent) {
		this.koeficijent = koeficijent;
	}
	
	public double value() {
		return koeficijent;
	}
}
