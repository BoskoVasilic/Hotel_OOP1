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

import entity.Pozicija;
import entity.Zaposleni;
import manage.ZaposleniManager;
import model.ZaposleniModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class ZaposleniUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	ZaposleniManager zm = new ZaposleniManager();

	public ZaposleniUI() {
		setTitle("Hotel - Pregled zaposlenih");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1430, 350);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JTable table = new JTable(new ZaposleniModel(zm));
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
		scrollPane.setBounds(5, 30, 1400, 215);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Pregled zaposlenih:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(5, 5, 155, 22);
		contentPane.add(lblNewLabel);
		
		JButton dodajZaposlenogBtn = new JButton("+");
		dodajZaposlenogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajZaposlenogUI frame = new DodajZaposlenogUI(Optional.<Zaposleni>empty());
				frame.setVisible(true);
				if (table.getRowSorter() != null) {
					table.getRowSorter().modelStructureChanged();
				}
				((ZaposleniModel) table.getModel()).fireTableDataChanged();
			}
		});
		dodajZaposlenogBtn.setBounds(1316, 269, 89, 23);
		contentPane.add(dodajZaposlenogBtn);
		
		JButton izmenaBtn = new JButton("Izmena");
		izmenaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					Zaposleni zaposleni = zm.nadjiZaposlenog(table.getModel().getValueAt(modelRed, 6).toString(),
							Pozicija.valueOf(table.getModel().getValueAt(modelRed, 10).toString()));
					DodajZaposlenogUI frame = new DodajZaposlenogUI(Optional.of(zaposleni));
					frame.setVisible(true);
					if (table.getRowSorter() != null) {
						table.getRowSorter().modelStructureChanged();
					}
					((ZaposleniModel) table.getModel()).fireTableDataChanged();
				}
			}
		});
		izmenaBtn.setEnabled(false);
		izmenaBtn.setBounds(1217, 269, 89, 23);
		contentPane.add(izmenaBtn);
		
		JButton brisanjeBtn = new JButton("Brisanje");
		brisanjeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete zaposlenog?", "Brisanje zaposlenog", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
					      return;
					    } else if (response == JOptionPane.YES_OPTION) {
					    	zm.obrisiZaposlenog(table.getModel().getValueAt(modelRed, 6).toString(), Pozicija.valueOf(table.getModel().getValueAt(modelRed, 10).toString()));
							JOptionPane.showMessageDialog(null, "Zaposleni uspešno obrisan.");
					    	if (table.getRowSorter() != null) {
								table.getRowSorter().modelStructureChanged();
							}
							((ZaposleniModel) table.getModel()).fireTableDataChanged();
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					      return;
					    }
				}
			}
		});
		brisanjeBtn.setEnabled(false);
		brisanjeBtn.setBounds(1118, 269, 89, 23);
		contentPane.add(brisanjeBtn);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = table.getSelectedRow();
				int modelRed = table.convertRowIndexToModel(red);
				if (red != -1 && table.getModel().getValueAt(modelRed, 0).toString() != "") {
					izmenaBtn.setEnabled(true);
					brisanjeBtn.setEnabled(true);
				} else {
					izmenaBtn.setEnabled(false);
					brisanjeBtn.setEnabled(false);
				}
			}
		});
		
	}

}
