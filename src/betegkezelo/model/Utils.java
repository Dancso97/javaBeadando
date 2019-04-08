package betegkezelo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Utils {

	private static String mes = "Üzenet!";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

	public static String getInputData(JTextField input) {
		return input.getText().toString();
	}

	public static boolean filled(JTextField input) {
		String test = getInputData(input);
		if (test.length() > 0)
			return true;
		else
			return false;
	}

	public static boolean goodDate(JTextField input) {
		String test = getInputData(input);
		Date testDate = null;
		try {
			testDate = sdf.parse(test);
		} catch (ParseException e) {
			return false;
		}
		if (sdf.format(testDate).equals(test))
			return true;
		else
			return false;
	}

	public static void showMD(String s, int i) {
		JOptionPane.showMessageDialog(null, s, mes, i);
	}

	public static Date StoD(String s) {
		Date testDate = null, vid = null;
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {
			return vid;
		}
		if (!sdf.format(testDate).equals(s)) {
			return vid;
		}
		return testDate;
	}

	public static void setTextFieldEmpty(JTextField a) {
		a.setText("");
	}

	public static String getMes() {
		return mes;
	}


	public static SimpleDateFormat getSdf() {
		return sdf;
	}
	
	public static void SMD(String s) {
		JOptionPane.showMessageDialog(null, s, Utils.getMes(), 0);
	}
	
	public static int StoI(String s) {
		int x = -55;
		x = Integer.parseInt(s);
		return x;
	}
	
	public static boolean goodStoInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean goodStoD(String s) {
		Date testDate = null, vid = null;
		try {
			testDate = sdf.parse(s);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	public static String RF(JTextField a) {
		return a.getText().toString();
	}

}
