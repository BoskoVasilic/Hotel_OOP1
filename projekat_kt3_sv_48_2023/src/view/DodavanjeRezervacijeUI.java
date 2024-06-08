package view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.DodatnaUsluga;
import entity.Oprema;
import entity.TipSobe;
import formater.DateLabelFormatter;
import manage.DodatnaUslugaManager;
import manage.GostManager;
import manage.OpremaManager;
import manage.RezervacijaManager;
import manage.TipSobeManager;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class DodavanjeRezervacijeUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel_3;
	private TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");
	private RezervacijaManager rm = RezervacijaManager.getInstance();
	private JPanel panelTipSobe;
	private JPanel panelOpremljenost;
	private DodatnaUslugaManager dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
	private OpremaManager om = new OpremaManager("data/oprema.csv");
	private JLabel lblBrojLjudi;
	private GostManager gm = GostManager.getInstance();

	public DodavanjeRezervacijeUI() {
		setTitle("Hotel - Nova rezervacija");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 550);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nova rezervacija:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 11, 137, 25);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_3 = new JLabel("Datum prijave:");
		lblNewLabel_3.setBounds(61, 49, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(61, 69, 147, 30);
		contentPane.add(datePicker);
		
		UtilDateModel model1 = new UtilDateModel();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePicker1.setBounds(274, 69, 147, 30);
		contentPane.add(datePicker1);
		
		lblNewLabel_3 = new JLabel("Datum odjave:");
		lblNewLabel_3.setBounds(274, 49, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		JButton dodajRezervacijuBtn = new JButton("Dodaj");
		dodajRezervacijuBtn.setEnabled(false);
		
		dodajRezervacijuBtn.setBounds(332, 477, 89, 23);
		contentPane.add(dodajRezervacijuBtn);
		
		JLabel tipoviSobeLbl = new JLabel("Tipovi sobe:");
		tipoviSobeLbl.setEnabled(false);
		tipoviSobeLbl.setBounds(61, 313, 69, 14);
		contentPane.add(tipoviSobeLbl);
		
		panelTipSobe = new JPanel();
		panelTipSobe.setBounds(360, 292, 145, 130);
		panelTipSobe.setLayout(new BoxLayout(panelTipSobe, BoxLayout.Y_AXIS));	
		
		JScrollPane scrollPaneCheckBox = new JScrollPane(panelTipSobe);
		scrollPaneCheckBox.setEnabled(false);
		scrollPaneCheckBox.setBounds(61, 329, 145, 130);
		scrollPaneCheckBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPaneCheckBox);
		
		JLabel lblNewLabel_2_1 = new JLabel("Dodatne usluge:");
		lblNewLabel_2_1.setBounds(274, 107, 100, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JPanel panelDodatneusluge = new JPanel();
		panelDodatneusluge.setBounds(360, 292, 145, 130);
		panelDodatneusluge.setLayout(new BoxLayout(panelDodatneusluge, BoxLayout.Y_AXIS));
		
		dum.ucitajDodatneUsluge();
		for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
			JCheckBox chckbx = new JCheckBox(dum.getDodatneUsluge().get(i).getNaziv());
			panelDodatneusluge.add(chckbx);
		}
		
		JScrollPane scrollPaneCheckBox_1 = new JScrollPane(panelDodatneusluge);
		scrollPaneCheckBox_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setBounds(274, 123, 145, 130);
		contentPane.add(scrollPaneCheckBox_1);
		
		panelOpremljenost = new JPanel();
		panelOpremljenost.setBounds(360, 292, 145, 130);
		panelOpremljenost.setLayout(new BoxLayout(panelOpremljenost, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Opremljenost sobe:");
		lblNewLabel_2_1_1.setBounds(61, 107, 120, 14);
		contentPane.add(lblNewLabel_2_1_1);
		
		om.ucitajOpremu();
		for (int i = 0; i < om.getOprema().size(); i++) {
			JCheckBox chckbx = new JCheckBox(om.getOprema().get(i).getNaziv());
			panelOpremljenost.add(chckbx);
		}
		
		JScrollPane scrollPaneCheckBox_1_1 = new JScrollPane(panelOpremljenost);
		scrollPaneCheckBox_1_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1_1.setBounds(61, 123, 145, 130);
		contentPane.add(scrollPaneCheckBox_1_1);
		
		lblBrojLjudi = new JLabel("Broj ljudi:");
		lblBrojLjudi.setEnabled(false);
		lblBrojLjudi.setBounds(274, 316, 114, 14);
		contentPane.add(lblBrojLjudi);
		
		JSpinner spinner = new JSpinner();
		spinner.setEnabled(false);
		spinner.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		spinner.setBounds(338, 313, 83, 20);
		contentPane.add(spinner);
		
		JButton pretraziBtn = new JButton("Pretraži");
		pretraziBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoviSobeLbl.setEnabled(true);
				lblBrojLjudi.setEnabled(true);
				dodajRezervacijuBtn.setEnabled(true);
				scrollPaneCheckBox.setEnabled(true);
				String datumPrijave = datePicker.getJFormattedTextField().getText();
				String datumOdjave = datePicker1.getJFormattedTextField().getText();
				ArrayList<Oprema> oprema = new ArrayList<Oprema>();
		
				for (int i = 0; i < om.getOprema().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelOpremljenost.getComponent(i);
					if (chckbx.isSelected()) {
						oprema.add(om.getOprema().get(i));
					}
				}
				spinner.setEnabled(true);
				
				tsm.ucitajTipoveSoba();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				ArrayList<TipSobe> tipoviSoba = new ArrayList<TipSobe>();
				tipoviSoba = rm.postojeSobeKojeZadovoljavajuUslove(oprema, LocalDate.parse(datumPrijave, format), LocalDate.parse(datumOdjave, format));
				panelTipSobe.removeAll();
				panelTipSobe.revalidate();
				panelTipSobe.repaint();
				for (int i = 0; i < tipoviSoba.size(); i++) {
					JRadioButton radioBtn = new JRadioButton(tsm.nadjiTipSobe(tipoviSoba.get(i).getNaziv()).getNaziv());
					panelTipSobe.add(radioBtn);
				}
				panelTipSobe.revalidate();
				panelTipSobe.repaint();
				ButtonGroup bg = new ButtonGroup();
				for (int i = 0; i < tipoviSoba.size(); i++) {
					bg.add((JRadioButton) panelTipSobe.getComponent(i));
				}
			}
		});
		pretraziBtn.setBounds(332, 264, 89, 23);
		contentPane.add(pretraziBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(46, 298, 389, 4);
		contentPane.add(separator);
		
		dodajRezervacijuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String datumPrijave = datePicker.getJFormattedTextField().getText();
				String datumOdjave = datePicker1.getJFormattedTextField().getText();
				TipSobe izabraniTipSobe = null;
				ArrayList<DodatnaUsluga> dodatneUsluge = new ArrayList<DodatnaUsluga>();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				for (int i = 0; i < tsm.getTipoviSoba().size() - 1; i++) {
					JRadioButton radioBtn = (JRadioButton) panelTipSobe.getComponent(i);
					if (radioBtn.isSelected()) {
						izabraniTipSobe = tsm.nadjiTipSobe(radioBtn.getText());
						break;
					}
				}
				for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelDodatneusluge.getComponent(i);
					if (chckbx.isSelected()) {
						dodatneUsluge.add(dum.getDodatneUsluge().get(i));
					}
				}
				
				ArrayList<Oprema> oprema = new ArrayList<Oprema>();
				
				for (int i = 0; i < om.getOprema().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelOpremljenost.getComponent(i);
					if (chckbx.isSelected()) {
						oprema.add(om.getOprema().get(i));
					}
				}
				int brojLjudi = (int) spinner.getValue();
				
				int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da dodate rezervaciju?", "Dodavanje rezervacije", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.NO_OPTION) {
				      return;
				    } else if (response == JOptionPane.YES_OPTION) {
				    	if (izabraniTipSobe == null && brojLjudi != 0 && brojLjudi <= tsm.maxBrojLjudi()) {
				    		boolean uspesno = rm.dodajRezervacijuPoBrojuLjudi(gm.getUlogovaniGost(), LocalDate.parse(datumPrijave, format), LocalDate.parse(datumOdjave, format), brojLjudi, dodatneUsluge, oprema);
				    		if (uspesno) {
                    			rm.sacuvajRezervacije();
                    			dispose();
				    		} else {
				    			JOptionPane.showMessageDialog(null, "Nazalost nema slobodne sobe za uneti period i broj ljudi!", "Greška", JOptionPane.ERROR_MESSAGE);
				    		}
				    	}else if (izabraniTipSobe != null) {
							if(izabraniTipSobe.getBrojOsoba() >= brojLjudi) {
								rm.dodajRezervacijuPoTipu(gm.getUlogovaniGost(), LocalDate.parse(datumPrijave, format), LocalDate.parse(datumOdjave, format), izabraniTipSobe, brojLjudi, dodatneUsluge, oprema);
								rm.sacuvajRezervacije();
		                    	dispose();
							}else {
								JOptionPane.showMessageDialog(null, "Broj ljudi ne sme biti veći od broja osoba koje tip sobe može da primi!", "Greška", JOptionPane.ERROR_MESSAGE);
                                return;
							}
						}else {
							JOptionPane.showMessageDialog(null, "Morate izabrati tip sobe ili uneti broj ljudi!", "Greška", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
				    } else if (response == JOptionPane.CLOSED_OPTION) {
				      return;
				    }
				
				
			}
		});
	}
}
