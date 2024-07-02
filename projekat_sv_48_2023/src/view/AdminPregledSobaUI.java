package view;

import java.awt.Font;
import java.awt.Image;

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

import entity.Soba;
import manage.SobaManager;
import model.SobeModel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;

public class AdminPregledSobaUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SobaManager sm = new SobaManager("data/sobe.csv");
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public AdminPregledSobaUI() {
		sm.ucitajSobe();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setTitle("Hotel - Pregled soba");
		setResizable(false);
		
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		
		JTable table = new JTable(new SobeModel(sm));
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
		scrollPane.setBounds(5, 31, 775, 190);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Pregled soba:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(5, 6, 96, 23);
		contentPane.add(lblNewLabel);
		
		JButton dodajBtn = new JButton("Dodaj");
		dodajBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajSobu frame = new DodajSobu(Optional.<Soba>empty());
				frame.setVisible(true);
			}
		});
		dodajBtn.setBounds(691, 227, 89, 23);
		contentPane.add(dodajBtn);
		
		JButton izmeniBtn = new JButton("Izmeni");
		izmeniBtn.setEnabled(false);
		izmeniBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					DodajSobu frame = new DodajSobu(Optional
							.of(sm.nadjiSobu(Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString()))));
					frame.setVisible(true);
				}
			}
		});
		izmeniBtn.setBounds(592, 227, 89, 23);
		contentPane.add(izmeniBtn);
		
		JButton obrisiBtn = new JButton("Obriši");
		obrisiBtn.setEnabled(false);
		obrisiBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete sobu?", "Brisanje sobe", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
					      return;
					    } else if (response == JOptionPane.YES_OPTION) {
					    	sm.obrisiSobu(Integer.parseInt(table.getModel().getValueAt(modelRed, 0).toString()));
					    	sm.sacuvajSobe();
							JOptionPane.showMessageDialog(null, "Soba uspešno obrisana.");
					    	if (table.getRowSorter() != null) {
								table.getRowSorter().modelStructureChanged();
							}
							((SobeModel) table.getModel()).fireTableDataChanged();
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					      return;
					    }
				}
			}
		});
		obrisiBtn.setBounds(493, 227, 89, 23);
		contentPane.add(obrisiBtn);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int red = table.getSelectedRow();
				int modelRed = table.convertRowIndexToModel(red);
				if (red != -1 && table.getModel().getValueAt(modelRed, 0).toString() != "") {
					izmeniBtn.setEnabled(true);
					obrisiBtn.setEnabled(true);
				} else {
					izmeniBtn.setEnabled(false);
					obrisiBtn.setEnabled(false);
				}
			}
		});
		
		JButton refreshBtn = new JButton("");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getRowSorter() != null) {
					table.getRowSorter().modelStructureChanged();
				}
				((SobeModel) table.getModel()).fireTableDataChanged();
				izmeniBtn.setEnabled(false);
				obrisiBtn.setEnabled(false);
			}
		});
		ImageIcon icon = new ImageIcon("img\\referesh.png");
		ImageIcon scaled = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		refreshBtn.setIcon(scaled);
		refreshBtn.setBounds(740, 3, 28, 28);
		contentPane.add(refreshBtn);
	}

}
