package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.Gost;
import entity.Pol;
import manage.GostManager;

public class GostManagerTest {
	private GostManager gostManager;

	@Before
	public void setUp() {
		gostManager = GostManager.getInstance();
	}

	@Test
    public void testNadjiGosta() {
        gostManager.dodajGosta("ime", "prezime", Pol.M, LocalDate.of(1990, 1, 1), "0612345678", "adresa", "email", "brojPasosa");

        Gost gost = gostManager.nadjiGosta("email");
        assertNotNull(gost);
        assertEquals("ime", gost.getIme());

        Gost nePostojeciGost = gostManager.nadjiGosta("nepostojeci");
        assertNull(nePostojeciGost);
    }

    @Test
    public void testDodajGosta() {
    	int gostiSize = gostManager.getGosti().size();
        boolean rezultat = gostManager.dodajGosta("Milos", "Milosevic", Pol.M, LocalDate.of(1990, 1, 1), "0612345678", "adresa", "email", "brojPasosa");
        assertTrue(rezultat);

        ArrayList<Gost> gosti = gostManager.getGosti();
        
        assertEquals(gostiSize + 1, gosti.size());
        assertEquals("Milos", gosti.get(gostiSize).getIme());

        boolean duplikat = gostManager.dodajGosta("Milos", "Milosevic", Pol.M, LocalDate.of(1990, 1, 1), "0612345678", "adresa", "email", "brojPasosa");
        assertFalse(duplikat);
    }

    @Test
    public void testIzmeniGosta() {
        gostManager.dodajGosta("Milos", "Milosevic", Pol.M, LocalDate.of(1990, 1, 1), "0612345678", "adresa", "email", "brojPasosa");

        boolean rezultat = gostManager.izmeniGosta("email", "novoIme", "novoPrezime", Pol.Z, LocalDate.of(1991, 2, 2), "0612345679", "novaAdresa", "novaLozinka");
        assertTrue(rezultat);

        Gost izmenjeniGost = gostManager.nadjiGosta("email");
        assertNotNull(izmenjeniGost);
        assertEquals("novoIme", izmenjeniGost.getIme());
        assertEquals("novoPrezime", izmenjeniGost.getPrezime());
        assertEquals(Pol.Z, izmenjeniGost.getPol());
        assertEquals(LocalDate.of(1991, 2, 2), izmenjeniGost.getDatumRodjenja());

        boolean nepostojeci = gostManager.izmeniGosta("nepostojeci", "Milos", "Milosevic", Pol.M, LocalDate.of(1990, 1, 1), "0612345678", "adresa", "lozinka");
        assertFalse(nepostojeci);
    }

    @Test
    public void testObrisiGosta() {
        gostManager.dodajGosta("Milos", "Milosevic", Pol.M, LocalDate.of(1990, 1, 1), "0612345678", "adresa", "email", "brojPasosa");

        boolean rezultat = gostManager.obrisiGosta("email");
        assertTrue(rezultat);

        Gost obrisanGost = gostManager.nadjiGosta("email");
        assertNull(obrisanGost);

        boolean nepostojeci = gostManager.obrisiGosta("nepostojeci");
        assertFalse(nepostojeci);
    }

}
