package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import manage.RezervacijaManager;

public class PrihodiUPrethodnojGodiniUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RezervacijaManager rm = RezervacijaManager.getInstance();

	public PrihodiUPrethodnojGodiniUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Prihodi u prethodnih 12 meseci");
		
		

	}

}
