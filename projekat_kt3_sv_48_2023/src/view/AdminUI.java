package view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;


public class AdminUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUI frame = new AdminUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminUI() {
		setTitle("Hotel - Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu izvestaji = new JMenu("Izveštaji");
		JMenuItem prihodiRashodi = new JMenuItem("Prihodi/Rashodi");
		izvestaji.add(prihodiRashodi);
		menuBar.add(izvestaji);
		
		this.setJMenuBar(menuBar);
		
		
		JLabel lblNewLabel = new JLabel("Dobrodosli administrator");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(110, 34, 277, 37);
		contentPane.add(lblNewLabel);
		
		JButton rezervacije_btn = new JButton("");
		rezervacije_btn.setIcon(new ImageIcon("img\\add_employee.png"));
		rezervacije_btn.setBounds(26, 104, 104, 87);
		contentPane.add(rezervacije_btn);
		
		JLabel lblNewLabel_1 = new JLabel("ZAPOSLENI");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(34, 193, 90, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton rezervacije_btn_1 = new JButton("");
		rezervacije_btn_1.setIcon(new ImageIcon("img\\price_list.png"));
		rezervacije_btn_1.setBounds(193, 104, 104, 87);
		contentPane.add(rezervacije_btn_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("CENOVNICI");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(201, 193, 90, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JButton rezervacije_btn_2 = new JButton("");
		rezervacije_btn_2.setIcon(new ImageIcon("img\\additionalServices.png"));
		rezervacije_btn_2.setBounds(358, 104, 104, 87);
		contentPane.add(rezervacije_btn_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("DODATNE USLUGE");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(352, 193, 116, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JButton rezervacije_btn_3 = new JButton("");
		rezervacije_btn_3.setIcon(new ImageIcon("img\\room_type.png"));
		rezervacije_btn_3.setBounds(26, 227, 104, 87);
		contentPane.add(rezervacije_btn_3);
		
		JLabel lblNewLabel_1_3 = new JLabel("TIPOVI SOBA");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3.setBounds(34, 316, 90, 14);
		contentPane.add(lblNewLabel_1_3);
		
		JButton rezervacije_btn_1_1 = new JButton("");
		rezervacije_btn_1_1.setIcon(new ImageIcon("img\\rooms.png"));
		rezervacije_btn_1_1.setBounds(193, 227, 104, 87);
		contentPane.add(rezervacije_btn_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("SOBE");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1.setBounds(201, 316, 90, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton rezervacije_btn_2_1 = new JButton("");
		rezervacije_btn_2_1.setIcon(new ImageIcon("img\\equimpment.png"));
		rezervacije_btn_2_1.setBounds(358, 227, 104, 87);
		contentPane.add(rezervacije_btn_2_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("OPREMA");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2_1.setBounds(366, 316, 90, 14);
		contentPane.add(lblNewLabel_1_2_1);

	}
}
