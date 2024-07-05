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
import model.DnevniOdlasciDolasciModel;

public class DnevniOdlasciDolasciUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	protected TableRowSorter<AbstractTableModel> tableCheckOutSorter = new TableRowSorter<AbstractTableModel>();
	private RezervacijaManager rm = RezervacijaManager.getInstance();

	public DnevniOdlasciDolasciUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Dnevni odlasci i dolasci");
		setResizable(false);
		
		JTable table = new JTable(new DnevniOdlasciDolasciModel(rm, false));
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
		
		JTable tableCheckOut = new JTable(new DnevniOdlasciDolasciModel(rm, true));
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
		
		JLabel lblNewLabel = new JLabel("DOLASCI:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(5, 6, 96, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblCheckOut = new JLabel("ODLASCI:");
		lblCheckOut.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCheckOut.setBounds(5, 276, 96, 23);
		contentPane.add(lblCheckOut);
	}

}
