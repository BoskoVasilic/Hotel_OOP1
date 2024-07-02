package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import entity.Cenovnik;
import formater.DateLabelFormatter;
import manage.CenovnikManager;
import manage.DodatnaUslugaManager;
import manage.TipSobeManager;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class DodajCenovnikUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");
	private JPanel panelTipSobe;
	private DodatnaUslugaManager dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
	CenovnikManager cm = new CenovnikManager("data/cenovnik.csv");

	public DodajCenovnikUI(Optional<Cenovnik> cenovnik) {
		cm.ucitajCenovnike();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Hotel - Dodaj cenovnik");
		if (cenovnik.isPresent()) {
			setTitle("Hotel - Izmeni cenovnik");
		}
		
		JLabel lblNewLabel_2 = new JLabel("Tipovi soba:");
		lblNewLabel_2.setBounds(20, 127, 69, 14);
		contentPane.add(lblNewLabel_2);
		
		panelTipSobe = new JPanel();
		panelTipSobe.setBounds(360, 292, 145, 130);
		panelTipSobe.setLayout(new BoxLayout(panelTipSobe, BoxLayout.Y_AXIS));
		
		tsm.ucitajTipoveSoba();
		for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
			JLabel label = new JLabel(tsm.getTipoviSoba().get(i).getNaziv());
			panelTipSobe.add(label);
			if (cenovnik.isPresent()) {
				HashMap<String, Double> cene = cenovnik.get().getCene().get("TipoviSoba");
				JTextField textField = new JTextField();
				if (cene.containsKey(tsm.getTipoviSoba().get(i).getNaziv())) {
					textField.setText(cene.get(tsm.getTipoviSoba().get(i).getNaziv()).toString());
				}
				panelTipSobe.add(textField);
			} else {
				panelTipSobe.add(new JTextField());
			}
		}
		
		JLabel lblNewLabel_3 = new JLabel("Pocetak važenja:");
		lblNewLabel_3.setBounds(20, 49, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(20, 69, 147, 30);
		if (cenovnik.isPresent()) {
			datePicker.getJFormattedTextField()
					.setText(cenovnik.get().getPocetakVazenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
			datePicker.getJFormattedTextField().setEnabled(false);
            datePicker.getComponent(1).setEnabled(false);
		}
		contentPane.add(datePicker);
		
		UtilDateModel model1 = new UtilDateModel();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePicker1.setBounds(243, 69, 147, 30);
		if (cenovnik.isPresent()) {
			datePicker1.getJFormattedTextField()
					.setText(cenovnik.get().getKrajVazenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
			datePicker1.getJFormattedTextField().setEnabled(false);
            datePicker1.getComponent(1).setEnabled(false);
		}
		contentPane.add(datePicker1);
		
		lblNewLabel_3 = new JLabel("Kraj važenja:");
		lblNewLabel_3.setBounds(243, 49, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		JScrollPane scrollPaneCheckBox = new JScrollPane(panelTipSobe);
		scrollPaneCheckBox.setBounds(20, 152, 145, 130);
		scrollPaneCheckBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPaneCheckBox);
		
		JLabel lblNewLabel_2_1 = new JLabel("Dodatne usluge:");
		lblNewLabel_2_1.setBounds(244, 127, 100, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JPanel panelDodatneusluge = new JPanel();
		panelDodatneusluge.setBounds(360, 292, 145, 130);
		panelDodatneusluge.setLayout(new BoxLayout(panelDodatneusluge, BoxLayout.Y_AXIS));
		
		dum.ucitajDodatneUsluge();
		for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
			JLabel lbl = new JLabel(dum.getDodatneUsluge().get(i).getNaziv());
			panelDodatneusluge.add(lbl);
			if (cenovnik.isPresent()) {
				HashMap<String, Double> cene = cenovnik.get().getCene().get("DodatneUsluge");
				JTextField textField = new JTextField();
				if (cene.containsKey(dum.getDodatneUsluge().get(i).getNaziv())) {
					textField.setText(cene.get(dum.getDodatneUsluge().get(i).getNaziv()).toString());
				}
				panelDodatneusluge.add(textField);
			} else {
				panelDodatneusluge.add(new JTextField());
			}
		}
		
		
		JScrollPane scrollPaneCheckBox_1 = new JScrollPane(panelDodatneusluge);
		scrollPaneCheckBox_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneCheckBox_1.setBounds(243, 152, 145, 130);
		contentPane.add(scrollPaneCheckBox_1);
		
		JLabel lblNewLabel = new JLabel("Novi cenovnik:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 161, 22);
		if (cenovnik.isPresent()) {
			lblNewLabel.setText("Izmeni cenovnik:");
		}
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Dodaj");
		if (cenovnik.isPresent()) {
			btnNewButton.setText("Izmeni");
		}
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, HashMap<String, Double>> cene = new HashMap<String, HashMap<String, Double>>();
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				boolean valid = true;
				cene.put("TipoviSoba", new HashMap<String, Double>());
				for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
					String cena = ((JTextField) panelTipSobe.getComponent(i * 2 + 1)).getText();
					if (cena.isEmpty()) {
						valid = false;
					}else {
						try {
                            Double.parseDouble(cena);
                        } catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "Cena mora biti broj!", "Greška",
									JOptionPane.ERROR_MESSAGE);
							return;
                        }
						cene.get("TipoviSoba").put(tsm.getTipoviSoba().get(i).getNaziv(), Double.parseDouble(cena));
					}
				}
				cene.put("DodatneUsluge", new HashMap<String, Double>());
				for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
					String cena = ((JTextField) panelDodatneusluge.getComponent(i * 2 + 1)).getText();
					if (cena.isEmpty()) {
						valid = false;
					} else {
						cene.get("DodatneUsluge").put(dum.getDodatneUsluge().get(i).getNaziv(),
								Double.parseDouble(cena));
					}
				}
				if (datePicker.getJFormattedTextField().getText().isEmpty()
						|| datePicker1.getJFormattedTextField().getText().isEmpty()) {
					valid = false;
				}
				if (valid && !cenovnik.isPresent()) {
					boolean uspesno = cm.dodajCenovnikGui(LocalDate.parse(datePicker.getJFormattedTextField().getText(), format), LocalDate.parse(datePicker1.getJFormattedTextField().getText(), format), cene);
					if (uspesno) {
						cm.sacuvajCenovnike();
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Cenovnik sa datim datumom vec postoji.", "Greška",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}else if(valid && cenovnik.isPresent()){
					cm.izmeniCenovnikKompletGUI(LocalDate.parse(datePicker.getJFormattedTextField().getText(), format), LocalDate.parse(datePicker1.getJFormattedTextField().getText(), format), cene);
                    cm.sacuvajCenovnike();
                    dispose();
				}else {
                    JOptionPane.showMessageDialog(null, "Morate popuniti sve vrednosti.", "Greška",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
			}
		});
		btnNewButton.setBounds(321, 327, 89, 23);
		contentPane.add(btnNewButton);
		
		
	}

}
