package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import manage.CenovnikManager;
import model.CenovnikModel;

public class PregledCenovnikaUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CenovnikManager cm = new CenovnikManager("data/cenovnik.csv");
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public PregledCenovnikaUI() {
		cm.ucitajCenovnike();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setTitle("Hotel - Pregled cenovnika");
		setResizable(false);
		setLocationRelativeTo(null);
		
		JTable table = new JTable(new CenovnikModel(cm));
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
		scrollPane.setBounds(5, 31, 425, 190);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Pregled cenovnika:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(5, 6, 207, 23);
		contentPane.add(lblNewLabel);
		
		JButton dodajBtn = new JButton("Dodaj");
		dodajBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajCenovnikUI dc = new DodajCenovnikUI();
				dc.setVisible(true);
				
			}
		});
		dodajBtn.setBounds(335, 227, 89, 23);
		contentPane.add(dodajBtn);
		
		JButton izmeniBtn = new JButton("Izmeni");
		izmeniBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		izmeniBtn.setEnabled(false);
		izmeniBtn.setBounds(236, 227, 89, 23);
		contentPane.add(izmeniBtn);
		
		JButton obrisiBtn = new JButton("Obriši");
		obrisiBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				int red = table.getSelectedRow();
				if (red != -1) {
					int modelRed = table.convertRowIndexToModel(red);
					int response = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete cenovnik?", "Brisanje cenovnika", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.NO_OPTION) {
					      return;
					    } else if (response == JOptionPane.YES_OPTION) {
					    	cm.obrisiCenovnik(LocalDate.parse((table.getModel().getValueAt(modelRed, 0).toString()), format), LocalDate.parse((table.getModel().getValueAt(modelRed, 1).toString()), format));
							JOptionPane.showMessageDialog(null, "Cenovnik uspešno obrisana.");
							cm.sacuvajCenovnike();
					    	if (table.getRowSorter() != null) {
								table.getRowSorter().modelStructureChanged();
							}
							((CenovnikModel) table.getModel()).fireTableDataChanged();
					    } else if (response == JOptionPane.CLOSED_OPTION) {
					      return;
					    }
				}
			}
		});
		obrisiBtn.setEnabled(false);
		obrisiBtn.setBounds(137, 227, 89, 23);
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
				((CenovnikModel) table.getModel()).fireTableDataChanged();
				izmeniBtn.setEnabled(false);
				obrisiBtn.setEnabled(false);
			}
		});
		ImageIcon icon = new ImageIcon("img\\referesh.png");
		ImageIcon scaled = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		refreshBtn.setIcon(scaled);
		refreshBtn.setBounds(396, 1, 28, 28);
		contentPane.add(refreshBtn);
	}

}
