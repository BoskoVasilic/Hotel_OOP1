package view;

import java.awt.Font;

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

import entity.StatusRezervacije;
import manage.RezervacijaManager;
import model.GostRezervacijeModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GostUI extends JFrame {

	private static final long serialVersionUID = 1L;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private RezervacijaManager rm = RezervacijaManager.getInstance();
	private JPanel contentPane;

	public GostUI() {
		setTitle("Hotel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JTable table = new JTable(new GostRezervacijeModel(rm));
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
		scrollPane.setBounds(5, 40, 974, 215);
		contentPane.add(scrollPane);
		
		if (table.getRowSorter() != null) {
            table.getRowSorter().modelStructureChanged();
        }
		
		JLabel lblNewLabel = new JLabel("Moje rezervacije:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(5, 11, 151, 18);
		contentPane.add(lblNewLabel);
		
		JLabel otkaziLbl = new JLabel("Otkaži izabranu rezervaciju:");
		
		JButton otkaziBtn = new JButton("Otkaži");
		otkaziBtn.setEnabled(false);
		otkaziBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da otkažete rezervaciju?", "Otkazivanje rezervacije", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
					      return;
					    } else if (response == JOptionPane.YES_OPTION) {
							int id = Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString());
							if (rm.nadjiRezervaciju(id).getStatusRezervacije() == StatusRezervacije.NA_ČEKANJU) {
						    	rm.promeniStatusRezervacije(id, StatusRezervacije.OTKAZANA);
						    	rm.nadjiRezervaciju(id).setUkupnaCena(0.0);
						    	rm.sacuvajRezervacije();
								((GostRezervacijeModel) table.getModel()).fireTableDataChanged();
								otkaziBtn.setEnabled(false);
								otkaziLbl.setEnabled(false);
							}else {
								rm.promeniStatusRezervacije(id, StatusRezervacije.OTKAZANA);
								rm.sacuvajRezervacije();
								((GostRezervacijeModel) table.getModel()).fireTableDataChanged();
								otkaziBtn.setEnabled(false);
								otkaziLbl.setEnabled(false);
							}
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					      return;
					    }
					
				}
			}
		});
		otkaziBtn.setBounds(700, 275, 89, 23);
		contentPane.add(otkaziBtn);
		
		JButton dodajRezervacijuBtn = new JButton("Nova rezervacija");
		dodajRezervacijuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodavanjeRezervacijeUI dr = new DodavanjeRezervacijeUI();
				dr.setVisible(true);
				if (table.getRowSorter() != null) {
		            table.getRowSorter().modelStructureChanged();
		        }
				((GostRezervacijeModel) table.getModel()).fireTableDataChanged();
			}
		});
		dodajRezervacijuBtn.setBounds(840, 275, 130, 23);
		contentPane.add(dodajRezervacijuBtn);
		
		otkaziLbl.setLabelFor(otkaziBtn);
		otkaziLbl.setEnabled(false);
		otkaziLbl.setBounds(532, 279, 200, 14);
		contentPane.add(otkaziLbl);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = table.getSelectedRow();
				int modelRed = table.convertRowIndexToModel(red);
				if (red != -1 && table.getModel().getValueAt(modelRed, 0).toString() != "" && (rm.nadjiRezervaciju(Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString())).getStatusRezervacije() == StatusRezervacije.POTVRĐENA || rm.nadjiRezervaciju(Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString())).getStatusRezervacije() == StatusRezervacije.NA_ČEKANJU)) {
					otkaziBtn.setEnabled(true);
					otkaziLbl.setEnabled(true);
				} else {
					otkaziBtn.setEnabled(false);
					otkaziLbl.setEnabled(false);
				}
			}
		});
	}
}
