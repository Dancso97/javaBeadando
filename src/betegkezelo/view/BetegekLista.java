package betegkezelo.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class BetegekLista extends JDialog {
	private JTable table;
	private BetegekListaLayout layout;

	/**
	 * Launch the application.
	 * 
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { BetegekLista dialog = new
	 * BetegekLista(); dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 * dialog.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the dialog.
	 */
	public BetegekLista(JFrame f, BetegekListaLayout layoutParameter) {
		super(f, "Betegek listája", true);
		layout = layoutParameter;
		
		getContentPane().setBackground(Color.ORANGE);
		setBounds(100, 100, 569, 300);
		getContentPane().setLayout(null);

		JButton btnBezr = new JButton("Bez\u00E1r");
		btnBezr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezr.setBounds(451, 227, 89, 23);
		getContentPane().add(btnBezr);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 530, 205);
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
		TableRowSorter<BetegekListaLayout> trs = (TableRowSorter<BetegekListaLayout>) table.getRowSorter();
		trs.setSortable(0, false);
	}
}
