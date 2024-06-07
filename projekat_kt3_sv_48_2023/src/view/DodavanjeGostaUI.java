package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	private GostManager gm = GostManager.getInstance();
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
	private JLabel lblEmail;
	private JTextField email;
	private JLabel lblBrojPasosa;
	private JTextField brPasosa;

	public DodavanjeGostaUI() {
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
		contentPane.add(adresa);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(61, 228, 86, 14);
		contentPane.add(lblEmail);
		
		email = new JTextField();
		lblEmail.setLabelFor(email);
		email.setColumns(10);
		email.setBounds(61, 248, 137, 25);
		contentPane.add(email);
		
		lblBrojPasosa = new JLabel("Broj pasoša:");
		lblBrojPasosa.setBounds(274, 228, 114, 14);
		contentPane.add(lblBrojPasosa);
		
		brPasosa = new JTextField();
		lblBrojPasosa.setLabelFor(brPasosa);
		brPasosa.setColumns(10);
		brPasosa.setBounds(274, 248, 147, 25);
		contentPane.add(brPasosa);
		
		brPasosa.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') && brPasosa.getText().length() < 9) || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					brPasosa.setEditable(true);
				} else {
					brPasosa.setEditable(false);
				}
			}
		});
		
		JButton dodajGostaBtn = new JButton("Dodaj");
		dodajGostaBtn.setBounds(332, 298, 89, 23);
		contentPane.add(dodajGostaBtn);
		
		dodajGostaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validno = true;
				Pol pol = null;
				String imeGosta = "";
				String prezimeGosta = "";
				String datumRodjenja = "";
				String telefon = "";
				String adresaGosta = "";
				String emailGosta = "";
				String brPasosaGosta = "";
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				if (radioBtnPolM.isSelected() == false && radioBtnPolZ.isSelected() == false) {
					validno = false;
				}else {
					pol = radioBtnPolM.isSelected() ? Pol.M : Pol.Z;
				}
				if (ime.getText().equals("") || prezime.getText().equals("")
						|| datePicker.getJFormattedTextField().getText().equals("") || brTelefona.getText().equals("")
						|| adresa.getText().equals("") || email.getText().equals("") || brPasosa.getText().equals("")) {
					validno = false;
				}else {
					datumRodjenja = datePicker.getJFormattedTextField().getText();
					imeGosta = ime.getText();
					prezimeGosta = prezime.getText();
					telefon = brTelefona.getText();
					adresaGosta = adresa.getText();
					emailGosta = email.getText();
					brPasosaGosta = brPasosa.getText();
				}
				
				if (validno == false) {
					JOptionPane.showMessageDialog(null, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
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
			}
		});
		
		
	}
}
