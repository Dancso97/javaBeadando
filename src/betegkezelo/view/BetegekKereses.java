package betegkezelo.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import betegkezelo.model.Utils;

public class BetegekKereses extends JDialog {
	private JTable table;
	private BetegekListaLayout layout;
	private JTextField NevField;
	private JTextField SzulField;
	private JTextField uvizsgaField;
	private JTextField betegsegField;
	private JTextField TajField;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BetegekListaMod dialog = new BetegekListaMod(null);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 */
	/**
	 * Create the dialog. , BetegekListaLayout layoutParameter
	 */
	public BetegekKereses(JFrame f,BetegekListaLayout layoutParameter, String mezo, String kulcs,Object mezonevek[]) {
		super(f, "Betegek adatainak módosítása", true);
		layout = layoutParameter;

		getContentPane().setBackground(Color.ORANGE);
		setBounds(100, 100, 638, 300);
		getContentPane().setLayout(null);

		TajField.setVisible(false); NevField.setVisible(false);
		SzulField.setVisible(false); uvizsgaField.setVisible(false);
		betegsegField.setVisible(false);
		
		if (mezo.equals("kod")) TajField.setVisible(true);
		if (mezo.equals("nev")) NevField.setVisible(true);
		if (mezo.equals("szul")) SzulField.setVisible(true);
		if (mezo.equals("uvizsga")) uvizsgaField.setVisible(true);
		if (mezo.equals("betegseg")) betegsegField.setVisible(true);
		
		JButton btnBezr = new JButton("Bez\u00E1r");
		btnBezr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezr.setBounds(290, 227, 89, 23);
		getContentPane().add(btnBezr);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 589, 174);
		getContentPane().add(scrollPane);
		table = new JTable(layout);
		scrollPane.setViewportView(table);

		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
			tc = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 1 || i == 5)
				tc.setPreferredWidth(30);
			else {
				tc.setPreferredWidth(100);
			}
		}

		table.setAutoCreateRowSorter(true);

		NevField = new JTextField(kulcs);
		NevField.setBounds(159, 196, 111, 20);
		getContentPane().add(NevField);
		NevField.setColumns(10);

		SzulField = new JTextField(kulcs);
		SzulField.setColumns(10);
		SzulField.setBounds(280, 196, 101, 20);
		getContentPane().add(SzulField);

		uvizsgaField = new JTextField(kulcs);
		uvizsgaField.setColumns(10);
		uvizsgaField.setBounds(391, 196, 112, 20);
		getContentPane().add(uvizsgaField);

		betegsegField = new JTextField(kulcs);
		betegsegField.setColumns(10);
		betegsegField.setBounds(513, 196, 86, 20);
		getContentPane().add(betegsegField);
		
		TajField = new JTextField(kulcs);
		TajField.setColumns(10);
		TajField.setBounds(74, 196, 81, 20);
		getContentPane().add(TajField);
		TableRowSorter<BetegekListaLayout> trs = (TableRowSorter<BetegekListaLayout>) table.getRowSorter();
		trs.setSortable(0, false);
	}
}
