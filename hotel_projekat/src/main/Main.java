package main;

import entity.Osnovica;
import entity.Pol;
import entity.StrucnaSprema;
import entity.Zaposleni;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		Zaposleni z = new Zaposleni("Pera", "Peric", Pol.M, "01.01.1990", "123456", "Adresa 1", "pera", "pera", StrucnaSprema.II, 5, Osnovica.Sobarica);
		System.out.println(z);
	}

}
