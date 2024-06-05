package view;


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
import manage.TipSobeManager;
import manage.DodatnaUslugaManager;
import model.RezervacijeModel;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
		
		JLabel lblNewLabel = new JLabel("Postavite status izabrane rezervacije:");
		lblNewLabel.setBounds(10, 242, 242, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("POTVRĐENA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(48, 270, 106, 23);
		contentPane.add(btnNewButton);
		
		JButton btnOdbijena = new JButton("ODBIJENA");
		btnOdbijena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOdbijena.setBounds(48, 304, 106, 23);
		contentPane.add(btnOdbijena);
		
		JLabel lblNewLabel_1 = new JLabel("Filteri:");
		lblNewLabel_1.setBounds(349, 242, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Tipovi sobe:");
		lblNewLabel_2.setBounds(359, 265, 69, 14);
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
		scrollPaneCheckBox.setBounds(360, 292, 145, 130);
		scrollPaneCheckBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneCheckBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPaneCheckBox);
		
		JLabel lblNewLabel_2_1 = new JLabel("Dodatne usluge:");
		lblNewLabel_2_1.setBounds(557, 265, 100, 14);
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
		scrollPaneCheckBox_1.setBounds(558, 292, 145, 130);
		contentPane.add(scrollPaneCheckBox_1);
		
		JButton btnNewButton_1 = new JButton("Primeni");
		btnNewButton_1.setBounds(614, 433, 89, 23);
		contentPane.add(btnNewButton_1);
		
	    
	}
}
