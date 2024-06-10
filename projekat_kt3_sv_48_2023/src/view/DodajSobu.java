package view;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import entity.Oprema;
import entity.Soba;
import entity.StatusSobe;
import entity.TipSobe;
import manage.OpremaManager;
import manage.SobaManager;
import manage.TipSobeManager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class DodajSobu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");
	private JPanel panelTipSobe;
	private OpremaManager om = new OpremaManager("data/oprema.csv");
	private SobaManager sm = new SobaManager("data/sobe.csv");

	public DodajSobu(Optional<Soba> soba) {
		sm.ucitajSobe();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setResizable(false);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nova soba:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 85, 22);
		if (soba.isPresent()) {
			lblNewLabel.setText("Izmena sobe:");
		}
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Broj sobe:");
		lblNewLabel_1.setBounds(20, 44, 63, 14);
		contentPane.add(lblNewLabel_1);
		
		JSpinner brojSobe = new JSpinner();
		brojSobe.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		brojSobe.setBounds(96, 41, 55, 20);
		if (soba.isPresent()) {
			brojSobe.setValue(soba.get().getBrojSobe());
			brojSobe.setEnabled(false);
		}
		contentPane.add(brojSobe);
		
		JLabel lblNewLabel_2 = new JLabel("Tipovi sobe:");
		lblNewLabel_2.setBounds(20, 69, 69, 14);
		contentPane.add(lblNewLabel_2);
		
		panelTipSobe = new JPanel();
		panelTipSobe.setBounds(20, 50, 145, 130);
		panelTipSobe.setLayout(new BoxLayout(panelTipSobe, BoxLayout.Y_AXIS));
		
		tsm.ucitajTipoveSoba();
		for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
			JRadioButton radioBtn = new JRadioButton(tsm.nadjiTipSobe(tsm.getTipoviSoba().get(i).getNaziv()).getNaziv());
			if (soba.isPresent()
					&& tsm.nadjiTipSobe(tsm.getTipoviSoba().get(i).getNaziv()).equals(soba.get().getTipSobe())) {
				radioBtn.setSelected(true);
			}
			panelTipSobe.add(radioBtn);
		}

		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
			bg.add((JRadioButton) panelTipSobe.getComponent(i));
		}
		
		JScrollPane scrollPaneCheckBox = new JScrollPane(panelTipSobe);
		scrollPaneCheckBox.setBounds(20, 91, 145, 130);
		scrollPaneCheckBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPaneCheckBox);
		
		JLabel lblNewLabel_2_1 = new JLabel("Oprema:");
		lblNewLabel_2_1.setBounds(269, 69, 100, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JPanel panelOprema = new JPanel();
		panelOprema.setBounds(360, 292, 145, 130);
		panelOprema.setLayout(new BoxLayout(panelOprema, BoxLayout.Y_AXIS));
		
		om.ucitajOpremu();
		for (int i = 0; i <	om.getOprema().size(); i++) {
			JCheckBox chckbx = new JCheckBox(om.getOprema().get(i).getNaziv());
			if (soba.isPresent() && soba.get().getOpremljenostSobe().contains(om.getOprema().get(i))) {
				chckbx.setSelected(true);
			}
			panelOprema.add(chckbx);
		}
		
		
		JScrollPane scrollPaneCheckBox_1 = new JScrollPane(panelOprema);
		scrollPaneCheckBox_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneCheckBox_1.setBounds(269, 91, 145, 130);
		contentPane.add(scrollPaneCheckBox_1);
		
		JCheckBox pusackaChckbx = new JCheckBox("Pušačka");
		pusackaChckbx.setBounds(269, 40, 97, 23);
		if (soba.isPresent() && soba.get().isPusackaSoba()) {
			pusackaChckbx.setSelected(true);
		}
		contentPane.add(pusackaChckbx);
		
		JButton dodajBtn = new JButton("Dodaj");
		if (soba.isPresent()) {
			dodajBtn.setText("Izmeni");
		}
		dodajBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Oprema> oprema = new ArrayList<Oprema>();
				TipSobe tipSobe = null;
				int brojSobeInt = -1;
				for (int i = 0; i < panelTipSobe.getComponentCount(); i++) {
                    JRadioButton radioBtn = (JRadioButton) panelTipSobe.getComponent(i);
                    if (radioBtn.isSelected()) {
                        tipSobe = tsm.nadjiTipSobe(radioBtn.getText());
                        break;
                    }
                }
				if (tipSobe == null) {
					JOptionPane.showMessageDialog(null, "Morate izabrati tip sobe.", "Greška",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				brojSobeInt = (int) brojSobe.getValue();
				for (Soba s : sm.getSobe()) {
					if (s.getBrojSobe() == brojSobeInt && !soba.isPresent()) {
						JOptionPane.showMessageDialog(null, "Soba sa unetim brojem već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				
				for (int i = 0; i < panelOprema.getComponentCount(); i++) {
					JCheckBox chckbx = (JCheckBox) panelOprema.getComponent(i);
					if (chckbx.isSelected()) {
						oprema.add(om.nadjiOpremu(chckbx.getText()));
					}
				}
				boolean pusacka = pusackaChckbx.isSelected();
				if (soba.isPresent() && soba.get().getStatusSobe() != StatusSobe.ZAUZETA) {
					sm.izmeniSobu(brojSobeInt, tipSobe, StatusSobe.SLOBODNA, oprema, pusacka);
					sm.sacuvajSobe();
					JOptionPane.showMessageDialog(null, "Soba uspešno izmenjena.");
					dispose();
					return;
				}else if (soba.isPresent() && soba.get().getStatusSobe() == StatusSobe.ZAUZETA) {
					JOptionPane.showMessageDialog(null, "Soba je zauzeta i ne može se menjati.", "Greška",
							JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					sm.dodajSobu(brojSobeInt, tipSobe, StatusSobe.SLOBODNA, oprema, pusacka);
					sm.sacuvajSobe();
					JOptionPane.showMessageDialog(null, "Soba uspešno dodata.");
					dispose();
				}
			}
		});
		dodajBtn.setBounds(325, 232, 89, 23);
		contentPane.add(dodajBtn);
	}
}
