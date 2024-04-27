package manage;

import java.util.ArrayList;

import entity.Soba;

public class SobaManager {
	String sobaFile;
	ArrayList<Soba> sobe;
	
	public SobaManager(String sobaFile) {
		this.sobaFile = sobaFile;
		this.sobe = new ArrayList<Soba>();
	}
	
	public ArrayList<Soba> getSobe() {
		return sobe;
	}
	
	public Soba nadjiSobu(int brojSobe) {
		for (Soba s : sobe) {
			if (s.getBrojSobe() == brojSobe) {
				return s;
			}
		}
		return null;
	}

}
