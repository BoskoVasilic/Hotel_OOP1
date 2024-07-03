package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import manage.RezervacijaManager;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;


public class AdminUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RezervacijaManager rm = RezervacijaManager.getInstance();
	
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
		JMenuItem sobariceSobe = new JMenuItem("Sobarice i sobe");
		JMenuItem potvrdjeneRezervacije = new JMenuItem("Potvrđene rezervacije");
		JMenuItem obradjeneRezervacije = new JMenuItem("Obrađene rezervacije");
		JMenuItem prikazSoba = new JMenuItem("Prikaz soba");
		izvestaji.add(prihodiRashodi);
		izvestaji.add(sobariceSobe);
		izvestaji.add(potvrdjeneRezervacije);
		izvestaji.add(obradjeneRezervacije);
		izvestaji.add(prikazSoba);
		menuBar.add(izvestaji);
		JMenu grafikoni = new JMenu("Grafikoni");
		JMenuItem prihodiUPrethodnojGodini = new JMenuItem("Prihodi u prethodnih 12 meseci");
		grafikoni.add(prihodiUPrethodnojGodini);
		menuBar.add(grafikoni);
		
		this.setJMenuBar(menuBar);
		
		prihodiRashodi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrihodiRashodiUI frame = new PrihodiRashodiUI();
				frame.setVisible(true);
			}
		});
		
		sobariceSobe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SobariceSobeUI frame = new SobariceSobeUI();
				frame.setVisible(true);
			}
		});
		
		potvrdjeneRezervacije.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PotvrdjeneRezervacijeUI frame = new PotvrdjeneRezervacijeUI();
				frame.setVisible(true);
			}
		});
		
		obradjeneRezervacije.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObradjeneRezervacijeUI frame = new ObradjeneRezervacijeUI();
				frame.setVisible(true);
			}
		});
		
		prikazSoba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrikazSobaUI frame = new PrikazSobaUI();
				frame.setVisible(true);
			}
		});
		
		prihodiUPrethodnojGodini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate pocetak = LocalDate.now().minusMonths(12);
		        LocalDate kraj = LocalDate.now();
		        
		        HashMap<String, Double> data = rm.getPrihodiPoTipuSobe(pocetak, kraj);

		        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Prihodi po tipu sobe").xAxisTitle("Tip sobe").yAxisTitle("Prihod").build();
		        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
	            chart.getStyler().setPlotGridLinesVisible(true);
	            chart.getStyler().setChartTitleVisible(true);
	            chart.getStyler().setChartTitleFont(new Font("Arial", Font.PLAIN, 18));
	            chart.getStyler().setAxisTitleFont(new Font("Arial", Font.PLAIN, 14));
	            chart.getStyler().setAxisTickLabelsFont(new Font("Arial", Font.PLAIN, 12));
		        
		        chart.addSeries("Prihodi", new ArrayList<>(data.keySet()), new ArrayList<>(data.values()));

		        Thread t = new Thread(new Runnable() {
		            @Override
		            public void run() {
		            	new SwingWrapper<>(chart).displayChart();
		            }
		        });
		        t.start();
			}
		});
		
		JLabel lblNewLabel = new JLabel("Dobrodosli administrator");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(110, 34, 277, 37);
		contentPane.add(lblNewLabel);
		
		JButton zaposleniBtn = new JButton("");
		zaposleniBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZaposleniUI frame = new ZaposleniUI();
				frame.setVisible(true);
			}
		});
		zaposleniBtn.setIcon(new ImageIcon("img\\add_employee.png"));
		zaposleniBtn.setBounds(26, 104, 104, 87);
		contentPane.add(zaposleniBtn);
		
		JLabel lblNewLabel_1 = new JLabel("ZAPOSLENI");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(34, 193, 90, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton cenovniciBtn = new JButton("");
		cenovniciBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PregledCenovnikaUI frame = new PregledCenovnikaUI();
				frame.setVisible(true);
			}
		});
		cenovniciBtn.setIcon(new ImageIcon("img\\price_list.png"));
		cenovniciBtn.setBounds(193, 104, 104, 87);
		contentPane.add(cenovniciBtn);
		
		JLabel lblNewLabel_1_1 = new JLabel("CENOVNICI");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(201, 193, 90, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JButton dodatneUslugeBtn = new JButton("");
		dodatneUslugeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PregledDodatnihUslugaUI frame = new PregledDodatnihUslugaUI();
				frame.setVisible(true);
			}
		});
		dodatneUslugeBtn.setIcon(new ImageIcon("img\\additionalServices.png"));
		dodatneUslugeBtn.setBounds(358, 104, 104, 87);
		contentPane.add(dodatneUslugeBtn);
		
		JLabel lblNewLabel_1_2 = new JLabel("DODATNE USLUGE");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(352, 193, 116, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JButton tipoviSobaBtn = new JButton("");
		tipoviSobaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PregledTipovaSobaUI frame = new PregledTipovaSobaUI();
				frame.setVisible(true);
			}
		});
		tipoviSobaBtn.setIcon(new ImageIcon("img\\room_type.png"));
		tipoviSobaBtn.setBounds(26, 227, 104, 87);
		contentPane.add(tipoviSobaBtn);
		
		JLabel lblNewLabel_1_3 = new JLabel("TIPOVI SOBA");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3.setBounds(34, 316, 90, 14);
		contentPane.add(lblNewLabel_1_3);
		
		JButton sobeBtn = new JButton("");
		sobeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminPregledSobaUI frame = new AdminPregledSobaUI();
				frame.setVisible(true);
			}
		});
		sobeBtn.setIcon(new ImageIcon("img\\rooms.png"));
		sobeBtn.setBounds(193, 227, 104, 87);
		contentPane.add(sobeBtn);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("SOBE");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1_1.setBounds(201, 316, 90, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton opremaBtn = new JButton("");
		opremaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PregledOpremeUI frame = new PregledOpremeUI();
				frame.setVisible(true);
			}
		});
		opremaBtn.setIcon(new ImageIcon("img\\equimpment.png"));
		opremaBtn.setBounds(358, 227, 104, 87);
		contentPane.add(opremaBtn);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("OPREMA");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2_1.setBounds(366, 316, 90, 14);
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
		logoutBtn.setBounds(446, 2, 28, 28);
		contentPane.add(logoutBtn);

	}
}
