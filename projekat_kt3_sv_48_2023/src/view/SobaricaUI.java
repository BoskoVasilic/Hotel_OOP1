package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import entity.StatusSobe;
import manage.SobaManager;
import manage.SobaricaManager;
import model.DodeljeneSobeSobariciModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SobaricaUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SobaricaManager sm = SobaricaManager.getInstance();
	private SobaManager sobaManager = new SobaManager("data/sobe.csv");
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public SobaricaUI() {
		setTitle("Hotel - Sobarica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dodeljene sobe:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 160, 22);
		contentPane.add(lblNewLabel);
		
		JTable table = new JTable(new DodeljeneSobeSobariciModel(sm));
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
		scrollPane.setBounds(5, 40, 775, 215);
		contentPane.add(scrollPane);
		
		JButton sredjivanjeZavrsenoBtn = new JButton("Sređivanje završeno");
		sredjivanjeZavrsenoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da označite da je spremanje završeno?", "Završeno spremanje", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
					      return;
					    } else if (response == JOptionPane.YES_OPTION) {
					    	sobaManager.ucitajSobe();
							int modelRed = table.convertRowIndexToModel(red);
							int brojSobe = Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString());
							sobaManager.nadjiSobu(brojSobe).setStatusSobe(StatusSobe.SLOBODNA);
							sobaManager.sacuvajSobe();
							sm.ukloniSobuZaSredjivanje(sobaManager.nadjiSobu(brojSobe));
							sm.sacuvajSobarice();
							if (table.getRowSorter() != null) {
								table.getRowSorter().modelStructureChanged();
							}
							((DodeljeneSobeSobariciModel) table.getModel()).fireTableDataChanged();
							sredjivanjeZavrsenoBtn.setEnabled(false);
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					      return;
					    }
				}
			}
		});
		sredjivanjeZavrsenoBtn.setEnabled(false);
		sredjivanjeZavrsenoBtn.setBounds(620, 266, 150, 23);
		contentPane.add(sredjivanjeZavrsenoBtn);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = table.getSelectedRow();
				int modelRed = table.convertRowIndexToModel(red);
				if (red != -1 && table.getModel().getValueAt(modelRed, 0).toString() != "") {
					sredjivanjeZavrsenoBtn.setEnabled(true);
                } else {
                    sredjivanjeZavrsenoBtn.setEnabled(false);
                }
			}
		});
	}

}
