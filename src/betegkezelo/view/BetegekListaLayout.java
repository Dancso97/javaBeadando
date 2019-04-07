package betegkezelo.view;

import javax.swing.table.DefaultTableModel;

public class BetegekListaLayout extends DefaultTableModel {

	public BetegekListaLayout(Object[] fieldNames, int rows) {
		super(fieldNames, rows);
	}

	public boolean isCellEditable(int row, int column) {
		// 0.oszlop szerkezthetõ, többi nem.
		if (column == 0)
			return true;
		return false;
	}

	public Class<?> getColumnClass(int index) {
		//0.oszlop logikai,1és5 int, többi string tipusú.
		if (index == 0)
			return (Boolean.class);
		else if (index == 1 || index == 5)
			return (Integer.class);
		return (String.class);
	}

}
