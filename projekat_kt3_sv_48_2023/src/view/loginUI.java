package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import manage.AdministratorManager;
import manage.GostManager;
import manage.RecepcionerManager;
import manage.SobaricaManager;



public class loginUI extends JFrame {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginUI frame = new loginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField korisnickoIme;
	private JTextField lozinka;
	private AdministratorManager am = new AdministratorManager("data/administratori.csv");
	private GostManager gm = GostManager.getInstance();
	private RecepcionerManager rm = new RecepcionerManager("data/recepcioneri.csv");
	private SobaricaManager sm = new SobaricaManager("data/sobarice.csv");

	public loginUI() {
		setTitle("Hotel - Prijava");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DOBRODOŠLI");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(114, 11, 192, 53);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PRIJAVA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(165, 54, 87, 36);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Korisničko ime:");
		lblNewLabel_2.setBounds(114, 115, 110, 14);
		contentPane.add(lblNewLabel_2);
		
		korisnickoIme = new JTextField();
		lblNewLabel_2.setLabelFor(korisnickoIme);
		korisnickoIme.setBounds(114, 132, 192, 20);
		contentPane.add(korisnickoIme);
		korisnickoIme.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Lozinka:");
		lblNewLabel_2_1.setBounds(114, 163, 110, 14);
		contentPane.add(lblNewLabel_2_1);
		
		lozinka = new JTextField();
		lblNewLabel_2_1.setLabelFor(lozinka);
		lozinka.setColumns(10);
		lozinka.setBounds(114, 180, 192, 20);
		contentPane.add(lozinka);
		
		JButton prijaviSeBtn = new JButton("PRIJAVI SE");
		prijaviSeBtn.setBounds(155, 218, 102, 23);
		contentPane.add(prijaviSeBtn);
		
		am.ucitajAdministratore();
		rm.ucitajRecepcionere();
		sm.ucitajSobarice();

		prijaviSeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = korisnickoIme.getText();
				String password = lozinka.getText();
				for (int i = 0; i < am.getAdministratori().size(); i++) {
					if (am.getAdministratori().get(i).getKorisnickoIme().equals(username)
							&& am.getAdministratori().get(i).getLozinka().equals(password)) {
						AdminUI adminUI = new AdminUI();
						adminUI.setVisible(true);
						dispose();
						return;
					}
				}
				for (int i = 0; i < rm.getRecepcioneri().size(); i++) {
					if (rm.getRecepcioneri().get(i).getKorisnickoIme().equals(username)
							&& rm.getRecepcioneri().get(i).getLozinka().equals(password)) {
						RecepcionerUI recepcionerUI = new RecepcionerUI();
						recepcionerUI.setVisible(true);
						dispose();
						return;
					}
				}
				for (int i = 0; i < sm.getSobarice().size(); i++) {
					if (sm.getSobarice().get(i).getKorisnickoIme().equals(username)
							&& sm.getSobarice().get(i).getLozinka().equals(password)) {
						SobaricaUI sobaricaUI = new SobaricaUI();
						sobaricaUI.setVisible(true);
						dispose();
						return;
					}
				}
				for (int i = 0; i < gm.getGosti().size(); i++) {
					if (gm.getGosti().get(i).getKorisnickoIme().equals(username)
							&& gm.getGosti().get(i).getLozinka().equals(password)) {
						GostUI gostUI = new GostUI();
						gostUI.setVisible(true);
						dispose();
						gm.setUlogovaniGost(gm.getGosti().get(i));
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "Pogrešno korisničko ime ili lozinka!", "Greška", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
