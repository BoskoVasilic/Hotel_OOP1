package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import formater.DateLabelFormatter;
import manage.SobaManager;
import model.PrikazSobaModel;
import javax.swing.JSeparator;

public class PrikazSobaUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SobaManager sm = new SobaManager("data/sobe.csv");
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public PrikazSobaUI() {
		sm.ucitajSobe();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Prikaz soba");
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLabel lblNewLabel = new JLabel("PRIKAZ SOBA:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 5, 241, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Pocetak perioda:");
		lblNewLabel_3.setBounds(241, 35, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(241, 55, 147, 30);
		contentPane.add(datePicker);
		
		UtilDateModel model1 = new UtilDateModel();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePicker1.setBounds(454, 55, 147, 30);
		contentPane.add(datePicker1);
		
		lblNewLabel_3 = new JLabel("Kraj perioda:");
		lblNewLabel_3.setBounds(454, 35, 114, 14);
		contentPane.add(lblNewLabel_3);
		
		JTable table = new JTable(new PrikazSobaModel(sm, null, null));
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
		scrollPane.setBounds(5, 135, 774, 215);
		contentPane.add(scrollPane);
		
		JButton prikaziBtn = new JButton("Prikaži");
		prikaziBtn.setBounds(674, 90, 89, 23);
		contentPane.add(prikaziBtn);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 122, 753, 2);
		contentPane.add(separator);
		
		prikaziBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validno = true;
				if (datePicker.getJFormattedTextField().getText().isEmpty() || datePicker1.getJFormattedTextField().getText().isEmpty()) {
					validno = false;
				}
				if (!validno) {
					JOptionPane.showMessageDialog(null, "Morate uneti datume za opseg!", "Greška",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

				
				if (table.getRowSorter() == null) {
					table.setRowSorter(tableSorter);
				}
				((PrikazSobaModel) table.getModel())
						.setDatumPocetka(LocalDate.parse(datePicker.getJFormattedTextField().getText(), format));
				((PrikazSobaModel) table.getModel())
						.setDatumKraja(LocalDate.parse(datePicker1.getJFormattedTextField().getText(), format));
				((PrikazSobaModel) table.getModel()).fireTableDataChanged();
	
			}
		});

	}

}
