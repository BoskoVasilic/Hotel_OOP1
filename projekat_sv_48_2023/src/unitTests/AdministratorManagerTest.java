package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Pol;
import entity.StrucnaSprema;
import entity.Zaposleni;
import manage.AdministratorManager;

public class AdministratorManagerTest {
	private AdministratorManager adminManager;
	private File adminFile;

	@Before
	public void setUp() {
		adminFile = new File("data/test/administratori.csv");
		adminManager = new AdministratorManager(adminFile.getAbsolutePath());
	}

	@After
	public void tearDown() {
		adminFile.delete();
	}

	@Test
    public void testUcitajAdministratore() throws IOException {
        String data = "Marko,Marković,M,1985-05-15,0612345678,Adresa,marko.m,lozinka,VII,10";
        Files.write(adminFile.toPath(), data.getBytes());
        
        boolean rezultat = adminManager.ucitajAdministratore();
        assertTrue(rezultat);
        
        ArrayList<Zaposleni> administratori = adminManager.getAdministratori();
        assertEquals(1, administratori.size());
        
        Zaposleni admin = administratori.get(0);
        assertEquals("Marko", admin.getIme());
        assertEquals("Marković", admin.getPrezime());
        assertEquals(Pol.M, admin.getPol());
        assertEquals(LocalDate.of(1985, 5, 15), admin.getDatumRodjenja());
        assertEquals("0612345678", admin.getTelefon());
        assertEquals("Adresa", admin.getAdresa());
        assertEquals("marko.m", admin.getKorisnickoIme());
        assertEquals("lozinka", admin.getLozinka());
        assertEquals(StrucnaSprema.VII, admin.getStrucnaSprema());
        assertEquals(10, admin.getGodineStaza());
    }
    
    @Test
    public void testSacuvajAdministratore() throws IOException {
        adminManager.dodajAdministratora("Marko", "Marković", Pol.M, LocalDate.of(1985, 5, 15), "0612345678", "Adresa", "marko.m", "lozinka", StrucnaSprema.VII, 10);
        
        boolean rezultat = adminManager.sacuvajAdministratore();
        assertTrue(rezultat);
        
        String sadrzaj = new String(Files.readAllBytes(adminFile.toPath()));
        assertTrue(sadrzaj.contains("Marko,Marković,M,1985-05-15,0612345678,Adresa,marko.m,lozinka,VII,10"));
    }
    
    @Test
    public void testNadjiAdministratora() {
        adminManager.dodajAdministratora("Marko", "Marković", Pol.M, LocalDate.of(1985, 5, 15), "0612345678", "Adresa", "marko.m", "lozinka", StrucnaSprema.VII, 10);
        
        Zaposleni admin = adminManager.nadjiAdministratora("marko.m");
        assertNotNull(admin);
        assertEquals("marko.m", admin.getKorisnickoIme());
        
        Zaposleni nePostojeciAdmin = adminManager.nadjiAdministratora("nepostojeci.m");
        assertNull(nePostojeciAdmin);
    }
    
    @Test
    public void testDodajAdministratora() {
        adminManager.dodajAdministratora("Marko", "Marković", Pol.M, LocalDate.of(1985, 5, 15), "0612345678", "Adresa", "marko.m", "lozinka", StrucnaSprema.VII, 10);
        
        ArrayList<Zaposleni> administratori = adminManager.getAdministratori();
        assertEquals(1, administratori.size());
        
        Zaposleni admin = administratori.get(0);
        assertEquals("Marko", admin.getIme());
        assertEquals("Marković", admin.getPrezime());
    }
    
    @Test
    public void testIzmeniAdministratora() {
        adminManager.dodajAdministratora("Marko", "Marković", Pol.M, LocalDate.of(1985, 5, 15), "0612345678", "Adresa", "marko.m", "lozinka", StrucnaSprema.VII, 10);
        
        adminManager.izmeniAdministratora("Milos", "Milić", Pol.Z, LocalDate.of(1990, 10, 10), "0698765432", "Nova Adresa", "marko.m", "novalozinka", StrucnaSprema.VIII, 5);
        
        Zaposleni admin = adminManager.nadjiAdministratora("marko.m");
        assertEquals("Milos", admin.getIme());
        assertEquals("Milić", admin.getPrezime());
        assertEquals(Pol.Z, admin.getPol());
        assertEquals(LocalDate.of(1990, 10, 10), admin.getDatumRodjenja());
        assertEquals("0698765432", admin.getTelefon());
        assertEquals("Nova Adresa", admin.getAdresa());
        assertEquals("novalozinka", admin.getLozinka());
        assertEquals(StrucnaSprema.VIII, admin.getStrucnaSprema());
        assertEquals(5, admin.getGodineStaza());
    }
    
    @Test
    public void testObrisiAdministratora() {
        adminManager.dodajAdministratora("Marko", "Marković", Pol.M, LocalDate.of(1985, 5, 15), "0612345678", "Adresa", "marko.m", "lozinka", StrucnaSprema.VII, 10);
        
        adminManager.obrisiAdministratora("marko.m");
        
        ArrayList<Zaposleni> administratori = adminManager.getAdministratori();
        assertEquals(0, administratori.size());
    }

}
