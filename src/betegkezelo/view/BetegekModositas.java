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

public class BetegekModositas extends JDialog {
	private JTable table;
	private BetegekListaLayout layout;
	private JTextField NevModField;
	private JTextField SzulModField;
	private JTextField uvizsgaModField;
	private JTextField betegsegModField;

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
	public BetegekModositas(JFrame f,BetegekListaLayout layoutParameter) {
		super(f, "Betegek adatainak módosítása", true);
		layout = layoutParameter;

		getContentPane().setBackground(Color.ORANGE);
		setBounds(100, 100, 638, 300);
		getContentPane().setLayout(null);

		JButton btnBezr = new JButton("Bez\u00E1r");
		btnBezr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezr.setBounds(510, 227, 89, 23);
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

		JButton btnMdost = new JButton("M\u00F3dos\u00EDt");
		btnMdost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!Utils.filled(NevModField) && !Utils.filled(SzulModField)
						&& !Utils.filled(uvizsgaModField) && !Utils.filled(betegsegModField))
					Utils.showMD("Egyetlen módosító adat sincs beírva!", 0);
				else if (Utils.filled(SzulModField) && !Utils.goodDate(SzulModField))
					Utils.showMD("A születési idõ mezõben hibás adat van!", 0);
				else if (Utils.filled(uvizsgaModField) && !Utils.goodDate(uvizsgaModField))
					Utils.showMD("Az utlsó vizsgálat mezõben hibás adat van!", 0);
				else {
					int db = 0, jel = 0, x = 0;
					for (x = 0; x < layout.getRowCount(); x++)
						if ((Boolean) layout.getValueAt(x, 0)) {
							db++;
							jel = x;
						}
					if (db == 0)
						Utils.showMD("Nincs kijelölve a módosítandó rekord!", 0);
					if (db > 1)
						Utils.showMD("Több rekord van kijelölve!\nEgyszerre csak egy rekord módosítható!", 0);
					if (db == 1) {
						if (Utils.filled(NevModField))
							layout.setValueAt(Utils.getInputData(NevModField), jel, 2);
						if (Utils.filled(SzulModField))
							layout.setValueAt(Utils.getInputData(SzulModField), jel, 3);
						if (Utils.filled(uvizsgaModField))
							layout.setValueAt(Utils.getInputData(uvizsgaModField), jel, 4);
						if (Utils.filled(betegsegModField))
							layout.setValueAt(Utils.getInputData(betegsegModField), jel, 5);
						Utils.showMD("A rekord módosítva!", 1);
						Utils.setTextFieldEmpty(NevModField);
						Utils.setTextFieldEmpty(SzulModField);
						Utils.setTextFieldEmpty(uvizsgaModField);
						Utils.setTextFieldEmpty(betegsegModField);
						layout.setValueAt(new Boolean(false), jel, 0);
					}

				}
			}

		});
		btnMdost.setBounds(290, 227, 89, 23);
		getContentPane().add(btnMdost);

		NevModField = new JTextField();
		NevModField.setBounds(159, 196, 111, 20);
		getContentPane().add(NevModField);
		NevModField.setColumns(10);

		SzulModField = new JTextField();
		SzulModField.setColumns(10);
		SzulModField.setBounds(280, 196, 101, 20);
		getContentPane().add(SzulModField);

		uvizsgaModField = new JTextField();
		uvizsgaModField.setColumns(10);
		uvizsgaModField.setBounds(391, 196, 112, 20);
		getContentPane().add(uvizsgaModField);

		betegsegModField = new JTextField();
		betegsegModField.setColumns(10);
		betegsegModField.setBounds(513, 196, 86, 20);
		getContentPane().add(betegsegModField);
		TableRowSorter<BetegekListaLayout> trs = (TableRowSorter<BetegekListaLayout>) table.getRowSorter();
		trs.setSortable(0, false);
	}
}
