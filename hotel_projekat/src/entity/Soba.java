package entity;

import java.util.ArrayList;

public class Soba {
	protected int brojSobe;
	protected TipSobe tipSobe;
	protected StatusSobe statusSobe;
	protected ArrayList<Oprema> opremljenostSobe;
	protected boolean pusackaSoba;
	
	public Soba(int brojSobe, StatusSobe statusSobe, boolean pusackaSoba) {
		this.brojSobe = brojSobe;
		this.tipSobe = new TipSobe();
		this.statusSobe = statusSobe;
		this.opremljenostSobe = new ArrayList<Oprema>();
		this.pusackaSoba = pusackaSoba;
	}

	public int getBrojSobe() {
		return brojSobe;
	}

	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}

	public TipSobe getTipSobe() {
		return tipSobe;
	}
	
	public void setTipSobe(TipSobe tipSobe) {
		this.tipSobe = tipSobe;
	}

	public StatusSobe getStatusSobe() {
		return statusSobe;
	}

	public void setStatusSobe(StatusSobe statusSobe) {
		this.statusSobe = statusSobe;
	}
	
	public ArrayList<Oprema> getOpremljenostSobe() {
		return opremljenostSobe;
	}
	
	public void setOpremljenostSobe(ArrayList<Oprema> opremljenostSobe) {
		this.opremljenostSobe = opremljenostSobe;
	}
	
	public boolean isPusackaSoba() {
		return pusackaSoba;
	}
	
	public void setPusackaSoba(boolean pusackaSoba) {
		this.pusackaSoba = pusackaSoba;
	}
	
	public String toFile() {
		ArrayList<String> nazivOpreme = new ArrayList<String>();
		for (Oprema o : opremljenostSobe) {
			nazivOpreme.add(o.getNaziv());
		}
		return this.brojSobe + "," + tipSobe.naziv + "," + this.statusSobe + "," + nazivOpreme + "," + this.pusackaSoba;
	}


}
