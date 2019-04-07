package betegkezelo.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import betegkezelo.model.Utils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class BetegekTorles extends JDialog {
	private JTable table;
	private BetegekListaLayout layout;
	private boolean multipleDelete = false;

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
	public BetegekTorles(JFrame f, BetegekListaLayout layoutParameter) {
		super(f, "Betegek törlése", true);
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

		JButton btnTrl = new JButton("T\u00F6r\u00F6l");
		btnTrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < layout.getRowCount(); x++)
					if ((Boolean) layout.getValueAt(x, 0)) {
						db++;
						jel = x;
					}
				if (db == 0)
					Utils.showMD("Nincs kijelölve a törlendõ rekord!", 0);
				if (!multipleDelete) {
					if (db > 1)
						Utils.showMD("Több rekord van kijelölve!\nEgyszerre csak egy rekord törölhetõ!", 0);
					if (db == 1) {
						layout.removeRow(jel);
						Utils.showMD("A rekord törölve!", 1);
					}
				} else {
					for (int i = 0; i < layout.getRowCount(); i++)
						if ((Boolean) layout.getValueAt(i, 0)) {
							layout.removeRow(i);
							i--;
						}
					Utils.showMD("Rekord(ok) törölve!", 1);
				}
			}
		});
		btnTrl.setBounds(300, 227, 89, 23);
		getContentPane().add(btnTrl);

		JCheckBox tobbDel = new JCheckBox("T\u00F6bb mez\u0151 t\u00F6rl\u00E9se");
		tobbDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multipleDelete = tobbDel.isSelected();
			}
		});
		tobbDel.setBounds(20, 227, 145, 23);
		getContentPane().add(tobbDel);
		TableRowSorter<BetegekListaLayout> trs = (TableRowSorter<BetegekListaLayout>) table.getRowSorter();
		trs.setSortable(0, false);
	}
}
