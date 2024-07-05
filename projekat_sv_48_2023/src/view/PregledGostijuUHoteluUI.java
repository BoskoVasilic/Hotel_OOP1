package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import manage.RezervacijaManager;
import model.GostiPoSobamaModel;

public class PregledGostijuUHoteluUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	private RezervacijaManager rm = RezervacijaManager.getInstance();

	public PregledGostijuUHoteluUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Hotel - Pregled gostiju u hotelu");
		
		JTable table = new JTable(new GostiPoSobamaModel(rm));
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
		scrollPane.setBounds(5, 31, 874, 275);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Pregled gostiju u hotelu:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(5, 6, 229, 23);
		contentPane.add(lblNewLabel);
		
	}

}
