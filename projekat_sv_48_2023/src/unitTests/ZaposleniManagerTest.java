package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import entity.Pol;
import entity.Pozicija;
import entity.StrucnaSprema;
import entity.Zaposleni;
import manage.ZaposleniManager;

public class ZaposleniManagerTest {
	private ZaposleniManager zm;

	@Before
	public void setUp() {
		zm = new ZaposleniManager();
	}

	@Test
    public void testKreirajZaposlenog() {
        Zaposleni z = zm.kreirajZaposlenog("Pera", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "sifra", StrucnaSprema.IV, 5, Pozicija.Administrator);
        assertNotNull( z);
        assertEquals( "Pera", z.getIme());
        assertEquals(Pozicija.Administrator, z.getOsnovica().getPozicija());
    }

    @Test
    public void testDodajZaposlenog() {
        Zaposleni z = zm.kreirajZaposlenog("Pera", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "sifra", StrucnaSprema.IV, 5, Pozicija.Administrator);
        boolean rezultat = zm.dodajZaposlenog(z);
        assertTrue( rezultat);
    }

    @Test
    public void testObrisiZaposlenog() {
        Zaposleni z = zm.kreirajZaposlenog("Pera", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "sifra", StrucnaSprema.IV, 5, Pozicija.Administrator);
        zm.dodajZaposlenog(z);
        boolean rezultat = zm.obrisiZaposlenog("pera", Pozicija.Administrator);
        assertTrue(rezultat);
    }

    @Test
    public void testIzmeniZaposlenog() {
        Zaposleni z = zm.kreirajZaposlenog("Pera", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "sifra", StrucnaSprema.IV, 5, Pozicija.Administrator);
        zm.dodajZaposlenog(z);
        boolean rezultat = zm.izmeniZaposlenog("Petar", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "nova_sifra", StrucnaSprema.VI, 6, Pozicija.Administrator);
        assertTrue(rezultat);
        Zaposleni izmenjeniZaposleni = zm.nadjiZaposlenog("pera", Pozicija.Administrator);
        assertEquals( "Petar", izmenjeniZaposleni.getIme());
        assertEquals(StrucnaSprema.VI, izmenjeniZaposleni.getStrucnaSprema());
    }

    @Test
    public void testNadjiZaposlenog() {
        Zaposleni z = zm.kreirajZaposlenog("Pera", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "sifra", StrucnaSprema.IV, 5, Pozicija.Administrator);
        zm.dodajZaposlenog(z);
        Zaposleni nadjeniZaposleni = zm.nadjiZaposlenog("pera", Pozicija.Administrator);
        assertNotNull( nadjeniZaposleni);
        assertEquals("Pera", nadjeniZaposleni.getIme());
    }

    @Test
    public void testGetRashodi() {
        Zaposleni z = zm.kreirajZaposlenog("Pera", "Perić", Pol.M, LocalDate.of(1990, 1, 1), "0123456789",
                "Ulica 1", "pera", "sifra", StrucnaSprema.IV, 5, Pozicija.Administrator);
        zm.dodajZaposlenog(z);
        LocalDate pocetakPerioda = LocalDate.of(2024, 1, 1);
        LocalDate krajPerioda = LocalDate.of(2024, 1, 31);
        double rashodi = zm.getRashodi(pocetakPerioda, krajPerioda);
        assertTrue(rashodi > 0);
    }
}
