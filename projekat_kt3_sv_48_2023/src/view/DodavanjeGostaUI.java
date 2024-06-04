package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.swing.ButtonGroup;
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

import entity.Pol;
import formater.DateLabelFormatter;
import manage.GostManager;

import javax.swing.JButton;

public class DodavanjeGostaUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private GostManager gm = new GostManager("data/gosti.csv");
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
	private JLabel lblBrojPasoa;
	private JTextField email;
	private JLabel lblBrojPasoa_1;
	private JTextField brPasosa;

	public DodavanjeGostaUI() {
		gm.ucitajGoste();
		setTitle("Hotel - Dodavanje gosta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ime = new JTextField();
		ime.setBounds(61, 69, 137, 25);
		contentPane.add(ime);
		ime.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ime:");
		lblNewLabel.setLabelFor(ime);
		lblNewLabel.setBounds(61, 49, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Dodavanje gosta:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 11, 137, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(274, 49, 69, 14);
		contentPane.add(lblPrezime);
		
		prezime = new JTextField();
		lblPrezime.setLabelFor(prezime);
		prezime.setColumns(10);
		prezime.setBounds(274, 69, 147, 25);
		contentPane.add(prezime);
		
		radioBtnPolM = new JRadioButton("Muški");
		radioBtnPolM.setBounds(61, 123, 69, 23);
		contentPane.add(radioBtnPolM);
		
		radioBtnPolZ = new JRadioButton("Ženski");
		radioBtnPolZ.setBounds(137, 123, 79, 23);
		contentPane.add(radioBtnPolZ);
		
		lblNewLabel_2 = new JLabel("Pol:");
		lblNewLabel_2.setBounds(61, 107, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtnPolM);
		bg.add(radioBtnPolZ);
		
		//date picker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(274, 123, 147, 30);
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
		contentPane.add(brTelefona);
		
		lblAdresa = new JLabel("Adresa:");
		lblAdresa.setBounds(274, 164, 69, 14);
		contentPane.add(lblAdresa);
		
		adresa = new JTextField();
		lblAdresa.setLabelFor(adresa);
		adresa.setColumns(10);
		adresa.setBounds(274, 184, 147, 25);
		contentPane.add(adresa);
		
		lblBrojPasoa = new JLabel("Email:");
		lblBrojPasoa.setBounds(61, 228, 86, 14);
		contentPane.add(lblBrojPasoa);
		
		email = new JTextField();
		lblBrojPasoa.setLabelFor(email);
		email.setColumns(10);
		email.setBounds(61, 248, 137, 25);
		contentPane.add(email);
		
		lblBrojPasoa_1 = new JLabel("Broj pasoša:");
		lblBrojPasoa_1.setBounds(274, 228, 114, 14);
		contentPane.add(lblBrojPasoa_1);
		
		brPasosa = new JTextField();
		lblBrojPasoa_1.setLabelFor(brPasosa);
		brPasosa.setColumns(10);
		brPasosa.setBounds(274, 248, 147, 25);
		contentPane.add(brPasosa);
		
		JButton dodajGostaBtn = new JButton("Dodaj");
		dodajGostaBtn.setBounds(332, 298, 89, 23);
		contentPane.add(dodajGostaBtn);
		
		dodajGostaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pol pol = radioBtnPolM.isSelected() ? Pol.M : Pol.Z;
				String datumRodjenja = datePicker.getJFormattedTextField().getText();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				String imeGosta = ime.getText();
				String prezimeGosta = prezime.getText();
				String telefon = brTelefona.getText();
				String adresaGosta = adresa.getText();
				String emailGosta = email.getText();
				String brPasosaGosta = brPasosa.getText();

				gm.dodajGosta(imeGosta, prezimeGosta, pol, LocalDate.parse(datumRodjenja, format), telefon, adresaGosta, emailGosta, brPasosaGosta);
				gm.sacuvajGoste();
				
				JOptionPane.showMessageDialog(null, "Gost je uspešno dodat!");
				
				ime.setText("");
				prezime.setText("");
				bg.clearSelection();
				datePicker.getJFormattedTextField().setText("");
				brTelefona.setText("");
				adresa.setText("");
				email.setText("");
				brPasosa.setText("");
			}
		});
		
		
	}
}
