package betegkezelo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import betegkezelo.model.Utils;
import betegkezelo.model.BetegekModel;

public class BetegFelvetel extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tajField;
	private JTextField nevField;
	private JTextField szulidoField;
	private JTextField uvizsgaField;
	private JTextField BetegsegField;
	private BetegekModel beteg;
	private int kilep = 0;

	/**
	 * Launch the application.
	
	public static void main(String[] args) {
		try {
			BetegFelvetel dialog = new BetegFelvetel(null, 2);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 */
	/**
	 * Create the dialog.
	 */
	public BetegFelvetel(JFrame f, int maxKod) {
		super(f, true);
		setTitle("\u00DAj beteg regisztr\u00E1l\u00E1sa");
		setBounds(100, 100, 432, 385);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.ORANGE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTaj = new JLabel("Taj:");
			lblTaj.setBounds(10, 11, 46, 14);
			contentPanel.add(lblTaj);
		}
		{
			tajField = new JTextField();
			tajField.setEditable(false);
			tajField.setBounds(106, 8, 86, 20);
			contentPanel.add(tajField);
			tajField.setColumns(10);
		}
		{
			JButton btnLekr = new JButton("Lek\u00E9r");
			btnLekr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tajField.setText("" + (maxKod + 1));
				}
			});
			btnLekr.setBounds(238, 7, 89, 23);
			contentPanel.add(btnLekr);
		}
		{
			JLabel lblNewLabel = new JLabel("N\u00E9v:");
			lblNewLabel.setBounds(10, 47, 46, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblSzletsiId = new JLabel("Sz\u00FClet\u00E9si id\u0151:");
			lblSzletsiId.setBounds(10, 86, 86, 14);
			contentPanel.add(lblSzletsiId);
		}
		{
			JLabel lblUtolsVizsglat = new JLabel("Utols\u00F3 vizsg\u00E1lat:");
			lblUtolsVizsglat.setBounds(10, 130, 86, 14);
			contentPanel.add(lblUtolsVizsglat);
		}
		{
			JButton btnAktulisDtum = new JButton("Aktu\u00E1lis d\u00E1tum");
			btnAktulisDtum.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String currDate = Utils.getSdf().format(new Date());
					uvizsgaField.setText(currDate);
				}
			});
			btnAktulisDtum.setBounds(238, 126, 123, 23);
			contentPanel.add(btnAktulisDtum);
		}
		{
			JLabel lblBetegsg = new JLabel("Betegs\u00E9g:");
			lblBetegsg.setBounds(10, 185, 58, 14);
			contentPanel.add(lblBetegsg);
		}
		{
			nevField = new JTextField();
			nevField.setColumns(10);
			nevField.setBounds(105, 39, 157, 20);
			contentPanel.add(nevField);
		}
		{
			szulidoField = new JTextField();
			szulidoField.setColumns(10);
			szulidoField.setBounds(106, 83, 114, 20);
			contentPanel.add(szulidoField);
		}
		{
			JLabel lblhhnn = new JLabel("\u00E9\u00E9\u00E9\u00E9.hh.nn");
			lblhhnn.setBounds(238, 86, 69, 14);
			contentPanel.add(lblhhnn);
		}
		{
			uvizsgaField = new JTextField();
			uvizsgaField.setBounds(106, 127, 97, 20);
			contentPanel.add(uvizsgaField);
			uvizsgaField.setColumns(10);
		}
		{
			BetegsegField = new JTextField();
			BetegsegField.setBounds(106, 182, 86, 20);
			contentPanel.add(BetegsegField);
			BetegsegField.setColumns(10);
		}
		{
			JButton btnFelvesz = new JButton("Felvesz");
			btnFelvesz.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!Utils.filled(tajField))
						tajField.setText("" + (maxKod + 1));
					if (!Utils.filled(nevField))
						Utils.showMD("A Név mezõ üres!", 0);
					else if (!Utils.filled(szulidoField))
						Utils.showMD("A Születési idõ mezõ üres!", 0);
					else if (!Utils.goodDate(szulidoField))
						Utils.showMD("A Születési idõ mezõben hibás adat van!", 0);
					else if (!Utils.filled(uvizsgaField))
						Utils.showMD("Az utolsó vizsgálat mezõ üres!", 0);
					else if (!Utils.goodDate(uvizsgaField))
						Utils.showMD("A utolsó vizsgálat mezõben hibás adat van!", 0);
					else if (!Utils.filled(BetegsegField))
						Utils.showMD("A betegség mezõ üres!", 0);
					else {
						beteg = new BetegekModel(Integer.parseInt(Utils.getInputData(tajField)), Utils.getInputData(nevField),
								Utils.StoD(Utils.getInputData(szulidoField)), Utils.StoD(Utils.getInputData(uvizsgaField)),
								Utils.getInputData(BetegsegField));
						Utils.showMD("Beteg felvéve", 1);
						kilep = 1;
						dispose();
					}
				}
			});
			btnFelvesz.setBounds(139, 243, 123, 23);
			contentPanel.add(btnFelvesz);
		}

		{
			JButton btnBzar = new JButton("Bez\u00E1r");
			btnBzar.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					dispose();
				}

			});
			btnBzar.setBounds(317, 312, 89, 23);
			contentPanel.add(btnBzar);
		}
	}

	public BetegekModel getBeteg() {
		return beteg;
	}

	public int getKilep() {
		return kilep;
	}
}
