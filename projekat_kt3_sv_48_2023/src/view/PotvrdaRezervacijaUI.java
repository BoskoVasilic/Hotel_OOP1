package view;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import entity.DodatnaUsluga;
import entity.StatusRezervacije;
import entity.TipSobe;
import manage.RezervacijaManager;
import manage.TipSobeManager;
import manage.DodatnaUslugaManager;
import model.RezervacijeModel;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;

public class PotvrdaRezervacijaUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RezervacijaManager rm;
	private TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private JPanel panelTipSobe;
	private DodatnaUslugaManager dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");

	public PotvrdaRezervacijaUI() {
		setTitle("Hotel - Pristigle rezervacije");
		rm = new RezervacijaManager("data/rezervacije.csv");
		rm.ucitajRezervacije();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JTable table = new JTable(new RezervacijeModel(rm));
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);
		tableSorter.setModel((AbstractTableModel) table.getModel());
		contentPane.setLayout(null);
		table.setRowSorter(tableSorter);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 5, 974, 215);
		contentPane.add(scrollPane);
		
		JLabel postaviStatusLbl = new JLabel("Postavite status izabrane rezervacije:");
		postaviStatusLbl.setEnabled(false);
		postaviStatusLbl.setBounds(650, 259, 242, 14);
		contentPane.add(postaviStatusLbl);
		
		
		JButton potvrdiRezervacijuBtn = new JButton("POTVRĐENA");
		potvrdiRezervacijuBtn.setEnabled(false);
		potvrdiRezervacijuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da potvrdite ovu rezervaciju?", "Potvrda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				    if (response == JOptionPane.NO_OPTION) {
				      return;
				    } else if (response == JOptionPane.YES_OPTION) {
				    	int id = Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString());
				    	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				    	LocalDate pocetak = LocalDate.parse(table.getModel().getValueAt(modelRed, 1).toString(), format);
				    	LocalDate kraj = LocalDate.parse(table.getModel().getValueAt(modelRed, 2).toString(), format);
				    	TipSobe tipSobe = tsm.nadjiTipSobe(table.getModel().getValueAt(modelRed, 3).toString());
						if (rm.isSlobodnaSobaZaPeriod(pocetak, kraj, tipSobe) == false) {
							JOptionPane.showMessageDialog(null, "Nema slobodnih soba zahtevanog tipa za izabrani period.", "Greška",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						rm.promeniStatusRezervacije(id, StatusRezervacije.POTVRĐENA);
						rm.sacuvajRezervacije();
						((RezervacijeModel) table.getModel()).fireTableDataChanged();
				    } else if (response == JOptionPane.CLOSED_OPTION) {
				      return;
				    }
				}
			}
		});
		
		potvrdiRezervacijuBtn.setBounds(700, 290, 106, 23);
		contentPane.add(potvrdiRezervacijuBtn);
		
		JButton odbijRezervacijuBtn = new JButton("ODBIJENA");
		odbijRezervacijuBtn.setEnabled(false);
		odbijRezervacijuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da odbijete ovu rezervaciju?", "Potvrda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				    if (response == JOptionPane.NO_OPTION) {
				      return;
				    } else if (response == JOptionPane.YES_OPTION) {
				    	int id = Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString());
						rm.promeniStatusRezervacije(id, StatusRezervacije.ODBIJENA);
						rm.sacuvajRezervacije();
						((RezervacijeModel) table.getModel()).fireTableDataChanged();
				    } else if (response == JOptionPane.CLOSED_OPTION) {
				      return;
				    }
				}
			}
		});
		odbijRezervacijuBtn.setBounds(700, 320, 106, 23);
		contentPane.add(odbijRezervacijuBtn);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = table.getSelectedRow();
				int modelRed = table.convertRowIndexToModel(red);
				if (red != -1 && table.getModel().getValueAt(modelRed, 0).toString() != "") {
					postaviStatusLbl.setEnabled(true);
					potvrdiRezervacijuBtn.setEnabled(true);
					odbijRezervacijuBtn.setEnabled(true);
				} else {
					postaviStatusLbl.setEnabled(false);
					potvrdiRezervacijuBtn.setEnabled(false);
					odbijRezervacijuBtn.setEnabled(false);
				}
			}
		});
		
		
		JLabel lblNewLabel_1 = new JLabel("Filteri:");
		lblNewLabel_1.setBounds(15, 236, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Tipovi sobe:");
		lblNewLabel_2.setBounds(25, 259, 69, 14);
		contentPane.add(lblNewLabel_2);
		
		panelTipSobe = new JPanel();
		panelTipSobe.setBounds(360, 292, 145, 130);
		panelTipSobe.setLayout(new BoxLayout(panelTipSobe, BoxLayout.Y_AXIS));
		
		tsm.ucitajTipoveSoba();
		for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
			JCheckBox chckbx = new JCheckBox(tsm.getTipoviSoba().get(i).getNaziv());
			//chckbx.setBounds(360, 286 + (i * 23), 138, 23);
			panelTipSobe.add(chckbx);
		}
		
		JScrollPane scrollPaneCheckBox = new JScrollPane(panelTipSobe);
		scrollPaneCheckBox.setBounds(26, 286, 145, 130);
		scrollPaneCheckBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPaneCheckBox);
		
		JLabel lblNewLabel_2_1 = new JLabel("Dodatne usluge:");
		lblNewLabel_2_1.setBounds(223, 259, 100, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JPanel panelDodatneusluge = new JPanel();
		panelDodatneusluge.setBounds(360, 292, 145, 130);
		panelDodatneusluge.setLayout(new BoxLayout(panelDodatneusluge, BoxLayout.Y_AXIS));
		
		dum.ucitajDodatneUsluge();
		for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
			JCheckBox chckbx = new JCheckBox(dum.getDodatneUsluge().get(i).getNaziv());
			// chckbx.setBounds(360, 286 + (i * 23), 138, 23);
			panelDodatneusluge.add(chckbx);
		}
		
		
		JScrollPane scrollPaneCheckBox_1 = new JScrollPane(panelDodatneusluge);
		scrollPaneCheckBox_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneCheckBox_1.setBounds(224, 286, 145, 130);
		contentPane.add(scrollPaneCheckBox_1);
		
		JButton primeniFiltereBtn = new JButton("Primeni");
		primeniFiltereBtn.setBounds(280, 427, 89, 23);
		contentPane.add(primeniFiltereBtn);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(181, 427, 89, 23);
		contentPane.add(btnReset);
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelTipSobe.getComponent(i);
					chckbx.setSelected(false);
				}
				for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelDodatneusluge.getComponent(i);
					chckbx.setSelected(false);
				}
				rm.setRezervacijeNaCekanju();
				if (table.getRowSorter() != null) {
					table.getRowSorter().modelStructureChanged();
				}
				((RezervacijeModel) table.getModel()).fireTableDataChanged();
				postaviStatusLbl.setEnabled(false);
				potvrdiRezervacijuBtn.setEnabled(false);
				odbijRezervacijuBtn.setEnabled(false);
			}
		});
		
		primeniFiltereBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<TipSobe> tipoviSoba = new ArrayList<TipSobe>();
				ArrayList<DodatnaUsluga> dodatneUsluge = new ArrayList<DodatnaUsluga>();
				for (int i = 0; i < tsm.getTipoviSoba().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelTipSobe.getComponent(i);
					if (chckbx.isSelected()) {
						tipoviSoba.add(tsm.getTipoviSoba().get(i));
					}
				}
				for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
					JCheckBox chckbx = (JCheckBox) panelDodatneusluge.getComponent(i);
					if (chckbx.isSelected()) {
						dodatneUsluge.add(dum.getDodatneUsluge().get(i));
					}
				}
				if (tipoviSoba.size() == 0 && dodatneUsluge.size() == 0) {
					rm.setRezervacijeNaCekanju();
				}else {
					rm.setFilter(tipoviSoba, dodatneUsluge);
				}
				if (table.getRowSorter() != null) {
		            table.getRowSorter().modelStructureChanged();
		        }
				((RezervacijeModel) table.getModel()).fireTableDataChanged();
			}
		});
		
	    
	}
}
