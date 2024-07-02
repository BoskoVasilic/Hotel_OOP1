package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import formater.DateLabelFormatter;
import manage.RezervacijaManager;

public class PotvrdjeneRezervacijeUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RezervacijaManager rm = RezervacijaManager.getInstance();

	public PotvrdjeneRezervacijeUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Potvrdjene rezervacije");
		
		JLabel lblNewLabel_3 = new JLabel("Pocetak perioda:");
		lblNewLabel_3.setBounds(36, 52, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(36, 72, 147, 30);
		contentPane.add(datePicker);
		
		UtilDateModel model1 = new UtilDateModel();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePicker1.setBounds(249, 72, 147, 30);
		contentPane.add(datePicker1);
		
		lblNewLabel_3 = new JLabel("Kraj perioda:");
		lblNewLabel_3.setBounds(249, 52, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("BROJ POTVRĐENIH REZERVACIJA:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 299, 23);
		contentPane.add(lblNewLabel);
		
		JButton prikaziBtn = new JButton("Prikaži");
		prikaziBtn.setBounds(335, 124, 89, 23);
		contentPane.add(prikaziBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 169, 414, 2);
		contentPane.add(separator);
		
		JLabel brPotRezLbl = new JLabel("Broj potvrđenih rezervacija: ");
		brPotRezLbl.setBounds(20, 182, 262, 14);
		contentPane.add(brPotRezLbl);
		
		prikaziBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validno = true;
				if (datePicker.getJFormattedTextField().getText().isEmpty() || datePicker1.getJFormattedTextField().getText().isEmpty()) {
					validno = false;
				}
				if (!validno) {
					JOptionPane.showMessageDialog(null, "Morate uneti datume za opseg!", "Greška",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				int brojPotvrdjenihRezervacija = rm.getBrojPotvrdjenihRezervacija(LocalDate.parse(datePicker.getJFormattedTextField().getText(), format), LocalDate.parse(datePicker1.getJFormattedTextField().getText(), format));
				
				brPotRezLbl.setText("Broj potvrđenih rezervacija: " + brojPotvrdjenihRezervacija);

			}
		});
		
	}

}
