package entity;

import java.util.ArrayList;

public class Soba {
	protected int brojSobe;
	protected TipSobe tipSobe;
	protected StatusSobe statusSobe;
	protected ArrayList<Oprema> opremljenostSobe;
	protected boolean pusackaSoba;
	
	public Soba(int brojSobe, TipSobe tipSobe, StatusSobe statusSobe, ArrayList<Oprema> opremljenostSobe, boolean pusackaSoba) {
		this.brojSobe = brojSobe;
		this.tipSobe = tipSobe;
		this.statusSobe = statusSobe;
		this.opremljenostSobe = opremljenostSobe;
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


}
