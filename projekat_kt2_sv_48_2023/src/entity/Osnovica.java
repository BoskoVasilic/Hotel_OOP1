package entity;

public enum Osnovica {
	Administarator(25000), Recepcioner(20000), Sobarica(13000);
	double osnovica;
	
	Osnovica(double osnovica) {
		this.osnovica = osnovica;
	}
	
	public double value() {
		return osnovica;
	}
}
