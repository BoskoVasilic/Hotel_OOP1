package view;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import formater.DateLabelFormatter;
import manage.ZaposleniManager;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import entity.StrucnaSprema;
import entity.Zaposleni;

import javax.swing.JSpinner;

import entity.Pol;
import entity.Pozicija;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DodajZaposlenogUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ime;
	private JTextField prezime;
	private JRadioButton radioBtnPolM;
	private JRadioButton radioBtnPolZ;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblBrojTelefona;
	private JTextField brTelefona;
	private JLabel lblAdresa;
	private JTextField adresa;
	private JLabel lblKorisnickoIme;
	private JTextField korisnickoIme;
	private JLabel lblLozinka;
	private JTextField lozinka;
	private JLabel lblGodineStaza;
	private JSpinner staz;
	private JComboBox<Pozicija> radnoMesto;
	private ZaposleniManager zm = new ZaposleniManager();

	public DodajZaposlenogUI(Optional<Zaposleni> zaposleni) {
		setTitle("Hotel - Dodaj zaposlenog");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ime = new JTextField();
		ime.setBounds(61, 69, 137, 25);
		if (zaposleni.isPresent()) {
			ime.setText(zaposleni.get().getIme());
		}
		contentPane.add(ime);
		ime.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ime:");
		lblNewLabel.setLabelFor(ime);
		lblNewLabel.setBounds(61, 49, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Dodavanje zaposlenog:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 11, 188, 25);
		if (zaposleni.isPresent()) {
			lblNewLabel_1.setText("Izmena zaposlenog:");
		}
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(274, 49, 69, 14);
		contentPane.add(lblPrezime);
		
		prezime = new JTextField();
		lblPrezime.setLabelFor(prezime);
		prezime.setColumns(10);
		prezime.setBounds(274, 69, 147, 25);
		if (zaposleni.isPresent()) {
			prezime.setText(zaposleni.get().getPrezime());
		}
		contentPane.add(prezime);
		
		radioBtnPolM = new JRadioButton("Muški");
		radioBtnPolM.setBounds(61, 123, 69, 23);
		contentPane.add(radioBtnPolM);
		
		radioBtnPolZ = new JRadioButton("Ženski");
		radioBtnPolZ.setBounds(137, 123, 79, 23);
		
		
		lblNewLabel_2 = new JLabel("Pol:");
		lblNewLabel_2.setBounds(61, 107, 46, 14);
		
		if (zaposleni.isPresent()) {
			if (zaposleni.get().getPol().equals(Pol.M)) {
				radioBtnPolM.setSelected(true);
			} else {
				radioBtnPolZ.setSelected(true);
			}
		}
		
		contentPane.add(radioBtnPolZ);
		contentPane.add(lblNewLabel_2);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtnPolM);
		bg.add(radioBtnPolZ);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(274, 123, 147, 30);
		if (zaposleni.isPresent()) {
			datePicker.getJFormattedTextField()
					.setText(zaposleni.get().getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
		}
		contentPane.add(datePicker);
		
		lblNewLabel_3 = new JLabel("Datum rođenja:");
		lblNewLabel_3.setBounds(274, 107, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		lblBrojTelefona = new JLabel("Broj telefona:");
		lblBrojTelefona.setBounds(61, 164, 86, 14);
		contentPane.add(lblBrojTelefona);
		
		brTelefona = new JTextField();
		lblBrojTelefona.setLabelFor(brTelefona);
		brTelefona.setColumns(10);
		brTelefona.setBounds(61, 184, 137, 25);
		if (zaposleni.isPresent()) {
			brTelefona.setText(zaposleni.get().getTelefon());
		}
		contentPane.add(brTelefona);
		
		brTelefona.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') && brTelefona.getText().length() < 12) || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					brTelefona.setEditable(true);
				} else {
					brTelefona.setEditable(false);
				}
			}
		});
		
		lblAdresa = new JLabel("Adresa:");
		lblAdresa.setBounds(274, 164, 69, 14);
		contentPane.add(lblAdresa);
		
		adresa = new JTextField();
		lblAdresa.setLabelFor(adresa);
		adresa.setColumns(10);
		adresa.setBounds(274, 184, 147, 25);
		if (zaposleni.isPresent()) {
			adresa.setText(zaposleni.get().getAdresa());
		}
		contentPane.add(adresa);
		
		lblKorisnickoIme = new JLabel("Korisničko ime:");
		lblKorisnickoIme.setBounds(61, 228, 86, 14);
		contentPane.add(lblKorisnickoIme);
		
		korisnickoIme = new JTextField();
		lblKorisnickoIme.setLabelFor(korisnickoIme);
		korisnickoIme.setColumns(10);
		korisnickoIme.setBounds(61, 248, 137, 25);
		if (zaposleni.isPresent()) {
			korisnickoIme.setText(zaposleni.get().getKorisnickoIme());
		}
		contentPane.add(korisnickoIme);
		
		lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setBounds(274, 228, 114, 14);
		contentPane.add(lblLozinka);
		
		lozinka = new JTextField();
		lblLozinka.setLabelFor(lozinka);
		lozinka.setColumns(10);
		lozinka.setBounds(274, 248, 147, 25);
		if (zaposleni.isPresent()) {
			lozinka.setText(zaposleni.get().getLozinka());
		}
		contentPane.add(lozinka);
		
		JButton dodajZaposlenogBtn = new JButton("Dodaj");
		
		dodajZaposlenogBtn.setBounds(332, 377, 89, 23);
		if (zaposleni.isPresent()) {
			dodajZaposlenogBtn.setText("Izmeni");
		}
		contentPane.add(dodajZaposlenogBtn);
		
		JLabel lblStrucnaSprema = new JLabel("Stručna sprema:");
		lblStrucnaSprema.setBounds(61, 293, 86, 14);
		contentPane.add(lblStrucnaSprema);
		
		JLabel lblPolozaj = new JLabel("Radno mesto:");
		lblPolozaj.setBounds(274, 293, 114, 14);
		contentPane.add(lblPolozaj);
		
		JComboBox<StrucnaSprema> strucnaSprema = new JComboBox<StrucnaSprema>();
		strucnaSprema.setModel(new DefaultComboBoxModel<StrucnaSprema>(StrucnaSprema.values()));
		strucnaSprema.setBounds(61, 314, 137, 22);
		if (zaposleni.isPresent()) {
			strucnaSprema.setSelectedItem(zaposleni.get().getStrucnaSprema());
		} else {
			strucnaSprema.setSelectedIndex(-1);
		}
		contentPane.add(strucnaSprema);
		
		lblGodineStaza = new JLabel("Godine staža:");
		lblGodineStaza.setBounds(61, 350, 114, 14);
		contentPane.add(lblGodineStaza);
		
		staz = new JSpinner();
		staz.setBounds(150, 347, 46, 20);
		if (zaposleni.isPresent()) {
			staz.setValue(zaposleni.get().getGodineStaza());
		}
		contentPane.add(staz);
		
		radnoMesto = new JComboBox<Pozicija>();
		radnoMesto.setModel(new DefaultComboBoxModel<Pozicija>(Pozicija.values()));
		radnoMesto.setBounds(274, 314, 137, 22);
		if (zaposleni.isPresent()) {
			radnoMesto.setSelectedItem(zaposleni.get().getOsnovica().getPozicija());
		} else {
			radnoMesto.setSelectedIndex(-1);
		}
		contentPane.add(radnoMesto);
		
		dodajZaposlenogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validno = true;
				Pol pol = null;
				String imeZaposlenog = "";
				String prezimeZaposlenog = "";
				String datumRodjenja = "";
				String telefon = "";
				String adresaZaposlenog = "";
				String korisnickoImeZaposlenog = "";
				String lozinkaZaposlenog = "";
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				if (radioBtnPolM.isSelected() == false && radioBtnPolZ.isSelected() == false) {
					validno = false;
				}else {
					pol = radioBtnPolM.isSelected() ? Pol.M : Pol.Z;
				}
				if (ime.getText().equals("") || prezime.getText().equals("")
						|| datePicker.getJFormattedTextField().getText().equals("") || brTelefona.getText().equals("")
						|| adresa.getText().equals("") || korisnickoIme.getText().equals("") || lozinka.getText().equals("") || strucnaSprema.getSelectedIndex() == -1 || radnoMesto.getSelectedIndex() == -1) {
					validno = false;
				}else {
					datumRodjenja = datePicker.getJFormattedTextField().getText();
					imeZaposlenog = ime.getText();
					prezimeZaposlenog = prezime.getText();
					telefon = brTelefona.getText();
					adresaZaposlenog = adresa.getText();
					korisnickoImeZaposlenog = korisnickoIme.getText();
					lozinkaZaposlenog = lozinka.getText();
				}
				
				if (validno == false) {
					JOptionPane.showMessageDialog(null, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					if(zaposleni.isPresent()) {
						zm.izmeniZaposlenog(imeZaposlenog, prezimeZaposlenog, pol, LocalDate.parse(datumRodjenja, format), telefon, adresaZaposlenog, korisnickoImeZaposlenog, lozinkaZaposlenog, (StrucnaSprema)strucnaSprema.getSelectedItem(), (int)staz.getValue(), (Pozicija)radnoMesto.getSelectedItem());
						JOptionPane.showMessageDialog(null, "Zaposleni je uspešno izmenjen!");
						dispose();
						return;
					}else {
						Zaposleni z = zm.kreirajZaposlenog(imeZaposlenog, prezimeZaposlenog, pol, LocalDate.parse(datumRodjenja, format), telefon, adresaZaposlenog, korisnickoImeZaposlenog, lozinkaZaposlenog, (StrucnaSprema)strucnaSprema.getSelectedItem(), (int)staz.getValue(), (Pozicija)radnoMesto.getSelectedItem());
						zm.dodajZaposlenog(z);
					}
					
					JOptionPane.showMessageDialog(null, "Zaposleni je uspešno dodat!");
					
					ime.setText("");
					prezime.setText("");
					bg.clearSelection();
					datePicker.getJFormattedTextField().setText("");
					brTelefona.setText("");
					adresa.setText("");
					korisnickoIme.setText("");
					lozinka.setText("");
					strucnaSprema.setSelectedIndex(-1);
					staz.setValue(0);
					radnoMesto.setSelectedIndex(-1);
				}
			}
		});
	}
}
