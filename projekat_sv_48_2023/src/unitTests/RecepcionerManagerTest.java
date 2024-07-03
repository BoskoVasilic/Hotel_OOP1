package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Pol;
import entity.StrucnaSprema;
import entity.Zaposleni;
import manage.RecepcionerManager;

public class RecepcionerManagerTest {
	private RecepcionerManager recepcionerManager;
	private File recepcionerFile;

	@Before
	public void setUp() {
		recepcionerFile = new File("data/test/recepcioneri.csv");
		recepcionerManager = new RecepcionerManager(recepcionerFile.getAbsolutePath());
	}

	@After
	public void tearDown() {
		recepcionerFile.delete();
	}

	@Test
    public void testNadjiRecepcionera() {
        recepcionerManager.dodajRecepcionera("Ana", "Anić", Pol.Z, LocalDate.of(1995, 3, 10), "0649876543", "Treća Ulica 3", "aanic", "lozinka789", StrucnaSprema.V, 3);

        Zaposleni r = recepcionerManager.nadjiRecepcionera("aanic");
        assertNotNull(r);
        assertEquals("aanic", r.getKorisnickoIme());

        Zaposleni r2 = recepcionerManager.nadjiRecepcionera("nepostoji");
        assertNull(r2);
    }

    @Test
    public void testDodajRecepcionera() {
        boolean rezultat = recepcionerManager.dodajRecepcionera("Marko", "Marković", Pol.M, LocalDate.of(1988, 12, 30), "063223344", "Četvrta Ulica 4", "mmarkovic", "lozinka321", StrucnaSprema.IV, 8);
        assertTrue(rezultat);

        Zaposleni r = recepcionerManager.nadjiRecepcionera("mmarkovic");
        assertNotNull(r);
        assertEquals("Marko", r.getIme());
    }

    @Test
    public void testIzmeniRecepcionera() {
        recepcionerManager.dodajRecepcionera("Jovan", "Jovanović", Pol.M, LocalDate.of(1985, 1, 1), "065555555", "Peta Ulica 5", "jjovanovic", "staraLozinka", StrucnaSprema.IV, 15);

        boolean rezultat = recepcionerManager.izmeniRecepcionera("Jovan", "Jovanović", Pol.M, LocalDate.of(1985, 1, 1), "065555555", "Nova Ulica 5", "jjovanovic", "novaLozinka", StrucnaSprema.IV, 15);
        assertTrue(rezultat);

        Zaposleni r = recepcionerManager.nadjiRecepcionera("jjovanovic");
        assertEquals("Nova Ulica 5", r.getAdresa());
        assertEquals("novaLozinka", r.getLozinka());
    }

    @Test
    public void testObrisiRecepcionera() {
        recepcionerManager.dodajRecepcionera("Svetlana", "Svetlanić", Pol.Z, LocalDate.of(1982, 11, 11), "066777888", "Šesta Ulica 6", "ssvetlanic", "lozinka654", StrucnaSprema.IV, 12);

        boolean rezultat = recepcionerManager.obrisiRecepcionera("ssvetlanic");
        assertTrue(rezultat);

        Zaposleni r = recepcionerManager.nadjiRecepcionera("ssvetlanic");
        assertNull(r);
    }

}
