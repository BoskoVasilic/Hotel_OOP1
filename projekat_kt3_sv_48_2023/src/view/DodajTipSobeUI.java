package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manage.TipSobeManager;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DodajTipSobeUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField naziv;
	private TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");

	public DodajTipSobeUI() {
		tsm.ucitajTipoveSoba();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 210, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Hotel - Dodaj tip sobe");
		
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Novi tip sobe:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 117, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Naziv tipa:");
		lblNewLabel_1.setBounds(20, 44, 80, 14);
		contentPane.add(lblNewLabel_1);
		
		naziv = new JTextField();
		naziv.setBounds(20, 60, 153, 20);
		contentPane.add(naziv);
		naziv.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Broj kreveta:");
		lblNewLabel_1_1.setBounds(20, 91, 80, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Broj ljudi:");
		lblNewLabel_1_2.setBounds(20, 142, 80, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JSpinner brojKreveta = new JSpinner();
		brojKreveta.setModel(new SpinnerNumberModel(0, 0, 20, 1));
		brojKreveta.setBounds(20, 107, 153, 20);
		contentPane.add(brojKreveta);
		
		JSpinner brojLjudi = new JSpinner();
		brojLjudi.setModel(new SpinnerNumberModel(0, 0, 20, 1));
		brojLjudi.setBounds(20, 158, 153, 20);
		contentPane.add(brojLjudi);
		
		JButton dodajBtn = new JButton("Dodaj");
		dodajBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nazivTipa = naziv.getText();
				int brKreveta = (int) brojKreveta.getValue();
				int brLjudi = (int) brojLjudi.getValue();
				if (nazivTipa.isEmpty() || brKreveta == 0 || brLjudi == 0) {
					JOptionPane.showMessageDialog(null, "Morate popuniti sve vrednosti.", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}
				boolean uspesno = tsm.dodajTipSobe(nazivTipa, brKreveta, brLjudi);
				if (uspesno) {
					tsm.sacuvajTipoveSoba();
					JOptionPane.showMessageDialog(null, "Tip sobe uspešno dodat.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Uneti naziv tipa sobe vec postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
		});
		dodajBtn.setBounds(95, 227, 89, 23);
		contentPane.add(dodajBtn);
	}
}
