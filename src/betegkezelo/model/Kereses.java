package betegkezelo.model;

import betegkezelo.view.BetegekListaLayout;

public class Kereses {
	public static boolean KeyCheck(String mezo, String key) {
		boolean vi = false;
		Character fc = ' ';
		String fs = "";
		if (mezo.equals("taj")) {
			if (key.substring(0, 1).equals("="))
				key = key.substring(1, key.length());
			vi = goodStoInt(key);
		}
		if (mezo.equals("nev")) {
			fs = key.substring(0, 1);
			if (Character.isLetter(key.charAt(0)) || fs.equals(" "))
				vi = true;
		}
		if (mezo.equals("beteg")) {
			if (Character.isLetter(key.charAt(0)))
				vi = true;
		}
		
		if (mezo.equals("szul")) {
			fs = key.substring(0, 1);
			fc = key.charAt(0);
			if (fs.equals("<") || fs.equals(">") || fs.equals("=")) {
				if (key.length() > 1 && goodStoInt(key.substring(1, key.length())))
					vi = true;
				else
					vi = false;
			}
		
			if (Character.isDigit(fc) && key.indexOf("..") > 0) {
				int x = key.indexOf("..");
				String k1 = key.substring(0, x);
				String k2 = key.substring(x + 2, key.length());
				if (goodStoInt(k1) && goodStoInt(k2))
					vi = true;
				else
					vi = false;
			}
		}
		
		if (mezo.equals("uvizsga")) {
			fs = key.substring(0, 1);
			fc = key.charAt(0);
			if (fs.equals("<") || fs.equals(">") || fs.equals("=")) {
				if (key.length() > 1 && goodStoInt(key.substring(1, key.length())))
					vi = true;
				else
					vi = false;
			}
		
			if (Character.isDigit(fc) && key.indexOf("..") > 0) {
				int x = key.indexOf("..");
				String k1 = key.substring(0, x);
				String k2 = key.substring(x + 2, key.length());
				if (goodStoInt(k1) && goodStoInt(k2))
					vi = true;
				else
					vi = false;
			}
		}
		
		return vi;
	}
	
	public static BetegekListaLayout Select(BetegekListaLayout eBetegekListaLayout, String mezo, String key ,Object fejlecek[]) {
		BetegekListaLayout kerBetegekListaLayout = new BetegekListaLayout(fejlecek, 0);
		String k = "", k1 = "", k2 = "";
		int x = 0;
		if (mezo.equals("taj") && key.substring(0, 1).equals("="))
			key = key.substring(1, key.length());
		if (mezo.equals("szul"))
			k = key.substring(1, key.length());
		if (mezo.equals("szul") && key.indexOf("..") > 0) {
			x = key.indexOf("..");
			k1 = key.substring(0, x);
			k2 = key.substring(x + 2, key.length());
		}
		
		if (mezo.equals("uvizsga"))
			k = key.substring(1, key.length());
		if (mezo.equals("uvizsga") && key.indexOf("..") > 0) {
			x = key.indexOf("..");
			k1 = key.substring(0, x);
			k2 = key.substring(x + 2, key.length());
		}
		
		for (int i = 0; i < eBetegekListaLayout.getRowCount(); i++) {
			if (mezo.equals("taj") && key.equals(eBetegekListaLayout.getValueAt(i, 1).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("nev") && eBetegekListaLayout.getValueAt(i, 2).toString().indexOf(key) >= 0)
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("betegseg") && key.equals(eBetegekListaLayout.getValueAt(i, 5).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("szul") && key.substring(0, 1).equals("=") && k.equals(eBetegekListaLayout.getValueAt(i, 3).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("szul") && key.substring(0, 1).equals(">")
					&& Utils.StoI(k) < Utils.StoI(eBetegekListaLayout.getValueAt(i, 3).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("szul") && key.substring(0, 1).equals("<")
					&& Utils.StoI(k) > Utils.StoI(eBetegekListaLayout.getValueAt(i, 3).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("szul") && key.indexOf("..") > 0) {
				String s = eBetegekListaLayout.getValueAt(i, 3).toString();
				if (Utils.StoI(k1) < Utils.StoI(s) && Utils.StoI(k2) > Utils.StoI(s))
					Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			}
			if (mezo.equals("uvizsga") && key.substring(0, 1).equals("=") && k.equals(eBetegekListaLayout.getValueAt(i, 4).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("uvizsga") && key.substring(0, 1).equals(">")
					&& Utils.StoI(k) < Utils.StoI(eBetegekListaLayout.getValueAt(i, 4).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("uvizsga") && key.substring(0, 1).equals("<")
					&& Utils.StoI(k) > Utils.StoI(eBetegekListaLayout.getValueAt(i, 4).toString()))
				Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			if (mezo.equals("uvizsga") && key.indexOf("..") > 0) {
				String s = eBetegekListaLayout.getValueAt(i, 4).toString();
				if (Utils.StoI(k1) < Utils.StoI(s) && Utils.StoI(k2) > Utils.StoI(s))
					Pack(eBetegekListaLayout, kerBetegekListaLayout, i);
			}
		}
		return kerBetegekListaLayout;
	}

	public static void Pack(BetegekListaLayout eBetegekListaLayout, BetegekListaLayout kerBetegekListaLayout, int row) {
		kerBetegekListaLayout.addRow(new Object[] { new Boolean(false), Utils.StoI(eBetegekListaLayout.getValueAt(row, 1).toString()),
				eBetegekListaLayout.getValueAt(row, 2).toString(), eBetegekListaLayout.getValueAt(row, 3).toString(),
				Utils.StoI(eBetegekListaLayout.getValueAt(row, 4).toString()), eBetegekListaLayout.getValueAt(row, 5).toString() });
	}

	public static boolean goodStoInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
} 
