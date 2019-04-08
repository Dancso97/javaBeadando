package betegkezelo.controller;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import betegkezelo.model.BetegekModel;
import betegkezelo.model.FileManager;
import betegkezelo.model.Kereses;
import betegkezelo.model.Utils;
import betegkezelo.view.BetegFelvetel;
import betegkezelo.view.BetegekKereses;
import betegkezelo.view.BetegekLista;
import betegkezelo.view.BetegekListaLayout;
import betegkezelo.view.BetegekModositas;
import betegkezelo.view.BetegekTorles;

public class MainWindow extends JFrame {

	private JPanel mainContentPane;
	private JTextField AdatBetoltTextField;
	private JTextField AdatKiirTextField;
	private JTextField fdb;
	private BetegekListaLayout ListaLayout;
	private BetegekListaLayout ListaLayoutK;
	private Object mezonevek[] = { "Pipa", "Taj", "Név", "Szülidõ", "Utolsó vizsgálat", "Betegségek" };
	private String elemek[] = { "Válasszon!", "MySQL DB", "Helyi .xml fájl", "Helyi .csv fájl", "Helyi .json fájl" };
	private String elemek2[] = { "Válasszon!", ">>> Forrás", "Helyi .xml fájl", "Helyi .csv fájl", "Helyi .json fájl",
			"Helyi .pdf fájl", "MySQL DB" };
	private String forras = elemek[0];
	private String cel = elemek2[0];
	private File fbe;
	private int lastTajNumber = 0;
	private String kerkif = "kod";
	private JTextField kulcs;

	// Main window
	public MainWindow() {	
		setTitle("Betegkezel\u0151");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 297);
		mainContentPane = new JPanel();
		mainContentPane.setBackground(Color.ORANGE);
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainContentPane);
		mainContentPane.setLayout(null);

		JButton btnBetlts = new JButton("Bet\u00F6lt\u00E9s");

		btnBetlts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (forras.equals("Válasszon!"))
					JOptionPane.showMessageDialog(null, "Elõször válassza ki a Forrás-t!", Utils.getMes(), 0);
				FileDialog fd = new FileDialog(new Frame(), " ", FileDialog.LOAD);
				switch (forras) {
				case "Helyi .xml fájl":
					fd.setFile("*.xml");
					fd.setVisible(true);
					if (fd.getFile() != null) {
						fbe = new File(fd.getDirectory(), fd.getFile());
						// System.out.println(fd.getDirectory() + "" + fd.getFile());
						String befnev = fd.getDirectory() + "" + fd.getFile();
						AdatBetoltTextField.setText(befnev);
						FileManager.xmlReader(fbe, ListaLayout);
					}
					fdb.setText("" + ListaLayout.getRowCount());
					break;

				case "Helyi .csv fájl":
					fd.setFile("*.csv");
					fd.setVisible(true);
					if (fd.getFile() != null) {
						fbe = new File(fd.getDirectory(), fd.getFile());
						// System.out.println(fd.getDirectory() + "" + fd.getFile());
						String befnev = fd.getDirectory() + "" + fd.getFile();
						AdatBetoltTextField.setText(befnev);
						FileManager.CsvReader(fbe, ListaLayout);
					}
					fdb.setText("" + ListaLayout.getRowCount());
					break;

				case "Helyi .json fájl":
					fd.setFile("*.json");
					fd.setVisible(true);
					if (fd.getFile() != null) {
						fbe = new File(fd.getDirectory(), fd.getFile());
						String befnev = fd.getDirectory() + "" + fd.getFile();
						AdatBetoltTextField.setText(befnev);
						FileManager.jsonReader(fbe, ListaLayout);
					}
					fdb.setText("" + ListaLayout.getRowCount());
					break;

				case "MySQL DB":
					FileManager.dbReader(ListaLayout);
					fdb.setText("" + ListaLayout.getRowCount());
					break;
				}
			}
		});

		btnBetlts.setBounds(10, 11, 123, 23);
		mainContentPane.add(btnBetlts);
		// Táblázat mezõnevek
		ListaLayout = new BetegekListaLayout(mezonevek, 0);

		JButton btnLista = new JButton("Lista");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BetegekLista lista = new BetegekLista(MainWindow.this, ListaLayout);
				lista.setVisible(true);
			}
		});
		btnLista.setBounds(10, 53, 123, 23);
		mainContentPane.add(btnLista);

		JButton btnhNewAdat = new JButton("\u00DAj adat");
		btnhNewAdat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ListaLayout.getRowCount() == 0)
					;
				else
					lastTajNumber = (int) ListaLayout.getValueAt(ListaLayout.getRowCount() - 1, 1);

				BetegFelvetel ujbeteg = new BetegFelvetel(MainWindow.this, lastTajNumber);
				ujbeteg.setVisible(true);
				if (ujbeteg.getKilep() == 1) {
					BetegekModel ub = ujbeteg.getBeteg();
					String szulido = Utils.getSdf().format(ub.getSzulido());
					String uvizsga = Utils.getSdf().format(ub.getUvizsgalat());
					ListaLayout.addRow(new Object[] { new Boolean(false), ub.getTaj(), ub.getNev(), szulido, uvizsga,
							ub.getBetegseg() });
					fdb.setText("" + ListaLayout.getRowCount());
				}

			}
		});
		btnhNewAdat.setBounds(10, 87, 123, 23);
		mainContentPane.add(btnhNewAdat);

		JButton btnMdosts = new JButton("M\u00F3dos\u00EDt\u00E1s");
		btnMdosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ListaLayout.getRowCount() == 0)
					Utils.showMD("Nincs módosítható adat!", 0);
				else {
					BetegekModositas bm = new BetegekModositas(MainWindow.this, ListaLayout);
					bm.setVisible(true);
				}

			}
		});
		btnMdosts.setBounds(10, 121, 123, 23);
		mainContentPane.add(btnMdosts);

		JButton btnTrl = new JButton("T\u00F6rl\u00E9s");
		btnTrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ListaLayout.getRowCount() == 0)
					Utils.showMD("Nincs törölhetõ adat!", 0);
				else {
					BetegekTorles bt = new BetegekTorles(MainWindow.this, ListaLayout);
					bt.setVisible(true);
					fdb.setText("" + ListaLayout.getRowCount());
				}

			}
		});
		btnTrl.setBounds(10, 155, 123, 23);
		mainContentPane.add(btnTrl);

		JButton btnKiir = new JButton("Ki\u00EDr\u00E1s");
		btnKiir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cel.equals("Válasszon!"))
					Utils.SMD("Elõször válassza ki a Cél-t!");
				else if (ListaLayout.getRowCount() == 0)
					Utils.SMD("Nincs kiírható adat");

				else if (AdatKiirTextField.getText().length() == 0 && cel.equals("MySQL DB")) {
					AdatKiirTextField.setText("MySQL DB");
					FileManager.dbWriter(ListaLayout);
				}

				else if (AdatKiirTextField.getText().length() == 0)
					Utils.SMD("Nincs megadva a cél fájl neve!");

				else if (cel.equals(">>> Forrás")) {
					String kiirfnev = AdatBetoltTextField.getText();
					AdatKiirTextField.setText(kiirfnev);
					switch (forras) {
					case "Helyi .xml fájl":
						FileManager.xmlWriter(kiirfnev, ListaLayout);
						break;
					case "Helyi .csv fájl":
						FileManager.CsvWriter(kiirfnev, ListaLayout);
						break;
					case "Helyi .json fájl":
						FileManager.jsonWriter(kiirfnev, ListaLayout);
						break;
					case "Helyi .pdf fájl":
						FileManager.pdfWriter(kiirfnev, ListaLayout);
						break;
					case "MySQL DB":
						FileManager.dbWriter(ListaLayout);
						break;
					}
				} else {
					String kiirfnev = AdatKiirTextField.getText().toString();
					AdatKiirTextField.setText(kiirfnev);
					switch (cel) {
					case "Helyi .xml fájl":
						FileManager.xmlWriter(kiirfnev, ListaLayout);
						break;
					case "Helyi .csv fájl":
						FileManager.CsvWriter(kiirfnev, ListaLayout);
						break;
					case "Helyi .json fájl":
						FileManager.jsonWriter(kiirfnev, ListaLayout);
						break;
					case "Helyi .pdf fájl":
						FileManager.pdfWriter(kiirfnev, ListaLayout);
						break;
					}
				}
			}
		});
		btnKiir.setBounds(10, 214, 123, 23);
		mainContentPane.add(btnKiir);

		JButton mainClose = new JButton("Bez\u00E1r");
		mainClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mainClose.setBounds(636, 216, 89, 23);
		mainContentPane.add(mainClose);

		JLabel lbljAdatokBetltse = new JLabel("\u00DAj adatok bet\u00F6lt\u00E9se:");
		lbljAdatokBetltse.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lbljAdatokBetltse.setBounds(183, 14, 123, 14);
		mainContentPane.add(lbljAdatokBetltse);

		JComboBox AdatBetoltComboBox = new JComboBox();
		for (String s : elemek) {
			AdatBetoltComboBox.addItem(s);
		}
		AdatBetoltComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forras = (String) AdatBetoltComboBox.getSelectedItem();
				AdatBetoltTextField.setText(forras);
			}
		});

		AdatBetoltComboBox.setBounds(336, 12, 146, 20);
		mainContentPane.add(AdatBetoltComboBox);

		AdatBetoltTextField = new JTextField();
		AdatBetoltTextField.setBounds(520, 12, 146, 20);
		mainContentPane.add(AdatBetoltTextField);
		AdatBetoltTextField.setColumns(10);

		JLabel lblAdatokKirsa = new JLabel("Adatok ki\u00EDr\u00E1sa:");
		lblAdatokKirsa.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblAdatokKirsa.setBounds(143, 217, 123, 14);
		mainContentPane.add(lblAdatokKirsa);

		JComboBox AdatKiirComboBox = new JComboBox();

		for (String s : elemek2) {
			AdatKiirComboBox.addItem(s);
		}

		AdatKiirComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cel = (String) AdatKiirComboBox.getSelectedItem();
				if (cel.equals(">>> Forrás") && AdatBetoltTextField.getText().equals(""))
					Utils.SMD("Nincs megadva a Forrás!");
				if (cel.equals(">>> Forrás") && !AdatBetoltTextField.getText().equals(""))
					AdatKiirTextField.setText(AdatBetoltTextField.getText());
			}

		});
		AdatKiirComboBox.setBounds(308, 215, 146, 20);
		mainContentPane.add(AdatKiirComboBox);

		AdatKiirTextField = new JTextField();
		AdatKiirTextField.setColumns(10);
		AdatKiirTextField.setBounds(464, 215, 146, 20);
		mainContentPane.add(AdatKiirTextField);

		JLabel ListaLabel = new JLabel("Adatok sz\u00E1ma:");
		ListaLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		ListaLabel.setBounds(143, 56, 123, 14);
		mainContentPane.add(ListaLabel);

		fdb = new JTextField();
		fdb.setText("0");
		fdb.setHorizontalAlignment(SwingConstants.RIGHT);
		fdb.setEditable(false);
		fdb.setBounds(245, 54, 50, 20);
		mainContentPane.add(fdb);
		fdb.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(308, 53, 407, 147);
		mainContentPane.add(panel);
		panel.setLayout(null);
		
		JRadioButton jrbTaj = new JRadioButton("Taj");
		jrbTaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jrbTaj.isSelected()) kerkif="taj";
			}
		});
		jrbTaj.setBounds(6, 7, 109, 23);
		panel.add(jrbTaj);
		
		JRadioButton jrbNev = new JRadioButton("N\u00E9v");
		jrbNev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbNev.isSelected()) kerkif="nev";
			}
		});
		jrbNev.setBounds(6, 29, 109, 23);
		panel.add(jrbNev);
		
		JRadioButton jrbSzul = new JRadioButton("Sz\u00FClet\u00E9si id\u0151");
		jrbSzul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbSzul.isSelected()) kerkif="szul";
			}
		});
		jrbSzul.setBounds(6, 58, 109, 23);
		panel.add(jrbSzul);
		
		JRadioButton jrbUvizsga = new JRadioButton("Utols\u00F3 vizsg\u00E1lat:");
		jrbUvizsga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbUvizsga.isSelected()) kerkif="uvizsga";
			}
		});
		jrbUvizsga.setBounds(6, 87, 109, 23);
		panel.add(jrbUvizsga);
		
		JRadioButton jrbBeteg = new JRadioButton("Betegs\u00E9g");
		jrbBeteg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jrbBeteg.isSelected()) kerkif="beteg";
			}
		});
		jrbBeteg.setBounds(6, 117, 109, 23);
		panel.add(jrbBeteg);
		
		JLabel lblKeress = new JLabel("Keres\u00E9s:");
		lblKeress.setBounds(153, 91, 46, 14);
		mainContentPane.add(lblKeress);
		
		JButton btnKeres = new JButton("Keres\u00E9s");
		btnKeres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Utils.RF(fdb).equals("0")) Utils.showMD("Nincs betöltött adat!", 0);
				else if (!Utils.filled(kulcs)) Utils.showMD("A keresõkulcs (X=) nincs megadva!", 0);
				else if (!Kereses.KeyCheck(kerkif, Utils.RF(kulcs))) Utils.showMD("A keresõkulcs hibásan van megadva!", 0);
				else {
				ListaLayoutK = Kereses.Select(ListaLayout, kerkif, Utils.RF(kulcs), mezonevek);
				BetegekKereses ek = new BetegekKereses(MainWindow.this, ListaLayoutK, kerkif,
				Utils.RF(kulcs), mezonevek);
				ek.setVisible(true);
				}

			}
		});
		btnKeres.setBounds(177, 183, 89, 23);
		mainContentPane.add(btnKeres);
		
		kulcs = new JTextField();
		kulcs.setBounds(143, 156, 123, 20);
		mainContentPane.add(kulcs);
		kulcs.setColumns(10);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrbTaj);
		bg.add(jrbNev);
		bg.add(jrbSzul);
		bg.add(jrbUvizsga);
		bg.add(jrbBeteg);
	}

	public int getLastTajNumber() {
		return lastTajNumber;
	}

	public void setLastTajNumber(int lastTajNumber) {
		this.lastTajNumber = lastTajNumber;
	}
}
