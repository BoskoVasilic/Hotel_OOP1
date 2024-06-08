package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import entity.DodatnaUsluga;
import entity.Rezervacija;
import entity.Soba;
import entity.StatusRezervacije;
import entity.StatusSobe;
import entity.TipSobe;
import manage.DodatnaUslugaManager;
import manage.RezervacijaManager;
import manage.SobaManager;
import manage.SobaricaManager;
import manage.TipSobeManager;
import model.CheckInRezervacijeModel;
import model.CheckOutRezervacijeModel;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

public class CheckInOutUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	protected TableRowSorter<AbstractTableModel> tableCheckOutSorter = new TableRowSorter<AbstractTableModel>();
	private RezervacijaManager rm = RezervacijaManager.getInstance();
	private TipSobeManager tsm = new TipSobeManager("data/tipoviSoba.csv");
	private DodatnaUslugaManager dum = new DodatnaUslugaManager("data/dodatneUsluge.csv");
	private SobaManager sm = new SobaManager("data/sobe.csv");
	private SobaricaManager soM = SobaricaManager.getInstance();

	public CheckInOutUI() {
		setTitle("Hotel - Check In / Out");
		tsm.ucitajTipoveSoba();
		dum.ucitajDodatneUsluge();
		sm.ucitajSobe();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JTable table = new JTable(new CheckInRezervacijeModel(rm));
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
		scrollPane.setBounds(5, 31, 974, 215);
		contentPane.add(scrollPane);
		
		JTable tableCheckOut = new JTable(new CheckOutRezervacijeModel(rm));
		tableCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tableCheckOut.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCheckOut.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer centerRenderCheckOut = new DefaultTableCellRenderer();
		centerRenderCheckOut.setHorizontalAlignment( JLabel.CENTER );
		tableCheckOut.setDefaultRenderer(String.class, centerRenderCheckOut);
		tableCheckOutSorter.setModel((AbstractTableModel) tableCheckOut.getModel());
		contentPane.setLayout(null);
		tableCheckOut.setRowSorter(tableCheckOutSorter);
		JScrollPane scrollPaneCheckOut = new JScrollPane(tableCheckOut);
		scrollPaneCheckOut.setBounds(5, 300, 974, 215);
		contentPane.add(scrollPaneCheckOut);
		
		JLabel lblNewLabel = new JLabel("CHECK IN:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(5, 6, 96, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblCheckOut = new JLabel("CHECK OUT:");
		lblCheckOut.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCheckOut.setBounds(5, 276, 96, 23);
		contentPane.add(lblCheckOut);
		
		JLabel dodeliSobuLbl = new JLabel("Dodeli sobu izabranoj rezervaciji:");
		dodeliSobuLbl.setEnabled(false);
		dodeliSobuLbl.setBounds(660, 257, 200, 14);
		contentPane.add(dodeliSobuLbl);
		
		JButton btnSobe = new JButton("SOBE");
		btnSobe.setEnabled(false);
		btnSobe.setBounds(860, 253, 100, 23);
		contentPane.add(btnSobe);
		
		btnSobe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
                if (red != -1) {
                	int modelRed = table.convertRowIndexToModel(red);
					int idRezervacije = (int) table.getValueAt(modelRed, 0);
					TipSobe tipSobe = tsm.nadjiTipSobe(table.getModel().getValueAt(modelRed, 3).toString());
                    ArrayList<String> slobodneSobe = sm.getSlobodneSobeTipa(tipSobe, rm.nadjiRezervaciju(idRezervacije).getZahtevanaOprema());
                    if(slobodneSobe.size() == 0) {
                    	JOptionPane.showMessageDialog(null, "Nema slobodnih soba tog tipa.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }else {
                    	String izbor = (String)JOptionPane.showInputDialog(null, "Izaberite sobu za rezervaciju:", "Izbor sobe", JOptionPane.PLAIN_MESSAGE, null, slobodneSobe.toArray(), slobodneSobe.get(0));
						if (izbor != null) {
							int brojSobe = Integer.parseInt(izbor);
							Rezervacija rezervacija = rm.nadjiRezervaciju(idRezervacije);
							Soba soba = sm.nadjiSobu(brojSobe);
							rezervacija.setDodeljenaSoba(soba);
							rezervacija.setStatusRezervacije(StatusRezervacije.U_TOKU);
							rm.sacuvajRezervacije();
							if (table.getRowSorter() != null) {
								table.getRowSorter().modelStructureChanged();
							}
							((CheckInRezervacijeModel) table.getModel()).fireTableDataChanged();
						}
                    }
                }
			}
		});
		
		JLabel odjaviGostaLbl = new JLabel("Odjavi gosta i završi boravak za izabranu rezervaciju:");
		odjaviGostaLbl.setEnabled(false);
		odjaviGostaLbl.setBounds(550, 531, 300, 14);
		contentPane.add(odjaviGostaLbl);
		
		JButton btnOdjava = new JButton("ODJAVA");
		btnOdjava.setEnabled(false);
		btnOdjava.setBounds(860, 527, 100, 23);
		contentPane.add(btnOdjava);
		
		btnOdjava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = tableCheckOut.getSelectedRow();
				if (red != -1) {
					int modelRed = tableCheckOut.convertRowIndexToModel(red);
					int idRezervacije = (int) tableCheckOut.getValueAt(modelRed, 0);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da odjavite gosta?", "Odjava gosta", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
					      return;
					    } else if (response == JOptionPane.YES_OPTION) {
					    	Rezervacija rezervacija = rm.nadjiRezervaciju(idRezervacije);
							rezervacija.setStatusRezervacije(StatusRezervacije.ZAVRŠENA);
							rezervacija.getDodeljenaSoba().setStatusSobe(StatusSobe.SPREMANJE);
							sm.sacuvajSobe();
							soM.dodajSobuZaSredjivanje(rezervacija.getDodeljenaSoba());
							rm.sacuvajRezervacije();
							if (tableCheckOut.getRowSorter() != null) {
								tableCheckOut.getRowSorter().modelStructureChanged();
							}
							((CheckOutRezervacijeModel) tableCheckOut.getModel()).fireTableDataChanged();
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					      return;
					    }
				}
			}
		});
		
		JLabel lblDodajDodatnuUslugu = new JLabel("Dodaj dodatnu uslugu u rezervaciju:");
		lblDodajDodatnuUslugu.setEnabled(false);
		lblDodajDodatnuUslugu.setBounds(300, 257, 220, 14);
		contentPane.add(lblDodajDodatnuUslugu);
		
		JButton btnDodaj = new JButton("+");
		btnDodaj.setEnabled(false);
		btnDodaj.setBounds(511, 253, 100, 23);
		contentPane.add(btnDodaj);
		
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int idRezervacije = (int) table.getValueAt(modelRed, 0);
					dodajDodatnuUslugu(idRezervacije);
					if (table.getRowSorter() != null) {
						table.getRowSorter().modelStructureChanged();
					}
					((CheckInRezervacijeModel) table.getModel()).fireTableDataChanged();
				}
			}
		});
		
		tableCheckOut.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = tableCheckOut.getSelectedRow();
				int modelRed = tableCheckOut.convertRowIndexToModel(red);
				if (red != -1 && tableCheckOut.getModel().getValueAt(modelRed, 0).toString() != "") {
					odjaviGostaLbl.setEnabled(true);
					btnOdjava.setEnabled(true);
				} else {
					odjaviGostaLbl.setEnabled(false);
					btnOdjava.setEnabled(false);
				}
			}
		});
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = table.getSelectedRow();
				int modelRed = table.convertRowIndexToModel(red);
				if (red != -1 && table.getModel().getValueAt(modelRed, 0).toString() != "") {
					dodeliSobuLbl.setEnabled(true);
					btnSobe.setEnabled(true);
					btnDodaj.setEnabled(true);
					lblDodajDodatnuUslugu.setEnabled(true);
				} else {
					dodeliSobuLbl.setEnabled(false);
					btnSobe.setEnabled(false);
					btnDodaj.setEnabled(false);
					lblDodajDodatnuUslugu.setEnabled(false);
				}
			}
		});
		
		JButton refreshBtn = new JButton("");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getRowSorter() != null) {
					table.getRowSorter().modelStructureChanged();
				}
				if (tableCheckOut.getRowSorter() != null) {
					tableCheckOut.getRowSorter().modelStructureChanged();
				}
				((CheckInRezervacijeModel) table.getModel()).fireTableDataChanged();
				((CheckOutRezervacijeModel) tableCheckOut.getModel()).fireTableDataChanged();
				dodeliSobuLbl.setEnabled(false);
				btnSobe.setEnabled(false);
				btnDodaj.setEnabled(false);
				lblDodajDodatnuUslugu.setEnabled(false);
				odjaviGostaLbl.setEnabled(false);
				btnOdjava.setEnabled(false);
			}
		});
		ImageIcon icon = new ImageIcon("img\\referesh.png");
		ImageIcon scaled = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		refreshBtn.setIcon(scaled);
		refreshBtn.setBounds(930, 3, 28, 28);
		contentPane.add(refreshBtn);
	}
	
	public void dodajDodatnuUslugu(int idRezervacije) {
		JDialog window = new JDialog();
        window.setSize(300, 300);

        window.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel("Izaberite dodatne usluge:");
        label.setBounds(10, 2, 200, 50);
        panel.add(label);
        
        JPanel panelDodatneUsluge = new JPanel();
		panelDodatneUsluge.setBounds(85, 50, 120, 150);
		panelDodatneUsluge.setLayout(new BoxLayout(panelDodatneUsluge, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < dum.getDodatneUsluge().size(); i++) {
			Rezervacija rezervacija = rm.nadjiRezervaciju(idRezervacije);
			if (rezervacija.getDodatneUsluge().contains(dum.getDodatneUsluge().get(i))) {
				continue;
			}
			JCheckBox chckbx = new JCheckBox(dum.getDodatneUsluge().get(i).getNaziv());
			panelDodatneUsluge.add(chckbx);
		}
			
		JScrollPane scrollPaneCheckBox_1 = new JScrollPane(panelDodatneUsluge);
		scrollPaneCheckBox_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox_1.setBounds(85, 50, 120, 150);
		contentPane.add(scrollPaneCheckBox_1);
        
        panel.add(scrollPaneCheckBox_1);
        
        JButton btnPotvrdi = new JButton("Potvrdi");
        btnPotvrdi.setBounds(40, 220, 100, 23);
		panel.add(btnPotvrdi);
		
		btnPotvrdi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rezervacija rezervacija = rm.nadjiRezervaciju(idRezervacije);
				for (int i = 0; i < panelDodatneUsluge.getComponentCount(); i++) {
					JCheckBox chckbx = (JCheckBox) panelDodatneUsluge.getComponent(i);
					if (chckbx.isSelected()) {
						if (rezervacija.getDodatneUsluge() == null)
							rezervacija.setDodatneUsluge(new ArrayList<DodatnaUsluga>());
						rezervacija.getDodatneUsluge().add(dum.nadjiDodatnuUslugu(chckbx.getText()));
					}
				}
				rezervacija.setUkupnaCena(rezervacija.izracunajUkupnuCenu());
				rm.sacuvajRezervacije();
				window.dispose();
			}
		});
		
		JButton btnOtkazi = new JButton("Otkaži");
		btnOtkazi.setBounds(150, 220, 100, 23);
		panel.add(btnOtkazi);
		
		btnOtkazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
		});
        
        window.getContentPane().add(panel);
        window.setVisible(true);
        
	}
}
