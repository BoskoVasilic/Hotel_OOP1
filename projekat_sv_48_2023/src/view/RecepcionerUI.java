package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class RecepcionerUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecepcionerUI frame = new RecepcionerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RecepcionerUI() {
		setTitle("Hotel - Recepcioner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dobrodosli recepcioner");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(110, 34, 277, 37);
		contentPane.add(lblNewLabel);
		
		JButton rezervacije_btn = new JButton("");
		rezervacije_btn.setIcon(new ImageIcon("img\\reservation.png"));
		rezervacije_btn.setBounds(90, 137, 104, 87);
		contentPane.add(rezervacije_btn);
		
		rezervacije_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PotvrdaRezervacijaUI frame = new PotvrdaRezervacijaUI();
				frame.setVisible(true);
			}
		});
		
		JButton chekInOut_btn = new JButton("");
		chekInOut_btn.setIcon(new ImageIcon("img\\check_in_out.png"));
		chekInOut_btn.setBounds(306, 137, 104, 87);
		contentPane.add(chekInOut_btn);
		
		chekInOut_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckInOutUI checkInOutFrame = new CheckInOutUI();
				checkInOutFrame.setVisible(true);
			}
		});
		
		JButton sobe_btn = new JButton("");
		sobe_btn.setIcon(new ImageIcon("img\\rooms.png"));
		sobe_btn.setBounds(90, 316, 104, 87);
		contentPane.add(sobe_btn);
		
		sobe_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PregledSobaUI pregledSobaFrame = new PregledSobaUI();
				pregledSobaFrame.setVisible(true);
			}
		});
		
		JButton dodajGosta_btn = new JButton("");
		dodajGosta_btn.setIcon(new ImageIcon("img\\add_guest.png"));
		dodajGosta_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		dodajGosta_btn.setBounds(306, 316, 104, 87);
		contentPane.add(dodajGosta_btn);
		
		dodajGosta_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodavanjeGostaUI dodavanjeGostaFrame = new DodavanjeGostaUI();
				dodavanjeGostaFrame.setVisible(true);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("REZERVACIJE");
		lblNewLabel_1.setLabelFor(rezervacije_btn);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(98, 226, 90, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("CHECK IN/CHECK OUT");
		lblNewLabel_1_1.setLabelFor(chekInOut_btn);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(290, 220, 134, 27);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("SOBE");
		lblNewLabel_1_2.setLabelFor(sobe_btn);
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(98, 403, 90, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("DODAJ GOSTA");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2_1.setBounds(315, 403, 90, 14);
		contentPane.add(lblNewLabel_1_2_1);
		
		JButton logoutBtn = new JButton("");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginUI frame = new LoginUI();
				frame.setVisible(true);
				dispose();
			}
		});
		ImageIcon icon = new ImageIcon("img\\logout.png");
		ImageIcon scaled = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		logoutBtn.setIcon(scaled);
		logoutBtn.setBounds(446, 11, 28, 28);
		contentPane.add(logoutBtn);
	}
}
