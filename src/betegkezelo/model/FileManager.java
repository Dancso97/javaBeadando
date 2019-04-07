package betegkezelo.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import betegkezelo.view.BetegekListaLayout;

public class FileManager {

	public static void CsvReader(File fnev, BetegekListaLayout layoutParameter) {
		String taj = "", nev = "", szido = "", uvizsgalat = "", betegseg = "", s = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(fnev));
			s = in.readLine();
			s = in.readLine(); 
			while (s != null) {
				String[] st = s.split(";");
				layoutParameter.addRow(new Object[] { new Boolean(false), Utils.StoI(st[0]), st[1], st[2], st[3], st[4] });
				s = in.readLine();
			}
			in.close();
			JOptionPane.showMessageDialog(null, "Adatok beolvasva!", Utils.getMes(), 1);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "CsvReader: " + ioe.getMessage(), Utils.getMes(), 0);
		}
	}

	public static void CsvWriter(String fnev, BetegekListaLayout layoutParameter) {
		List<BetegekModel> dataFromTable = getDataFromTable(layoutParameter);
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fnev));
			out.println("Taj;Nev;Szulido;Utolso_vizsgalat;betegseg");
			for (BetegekModel beteg : dataFromTable) {
				out.println(beteg.getTaj() + ";" + beteg.getNev() + ";" + Utils.getSdf().format(beteg.getSzulido())
						+ ";" + Utils.getSdf().format(beteg.getUvizsgalat()) + ";" + beteg.getBetegseg());
			}
			out.close();
			JOptionPane.showMessageDialog(null, "csv adatok kiírva!", Utils.getMes(), 1);
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "CsvWriter: " + ioe.getMessage(), Utils.getMes(), 0);
		}
	}

	public static void jsonReader(File fnev, BetegekListaLayout layoutParameter) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(fnev));
			line = in.readLine();
			while (line != null) {
				BetegekModel beteg = gson.fromJson(line, BetegekModel.class);
				String szulido = Utils.getSdf().format(beteg.getSzulido());
				String uvizsga = Utils.getSdf().format(beteg.getUvizsgalat());
				layoutParameter.addRow(new Object[] { new Boolean(false), beteg.getTaj(), beteg.getNev(), szulido,
						uvizsga, beteg.getBetegseg() });
				line = in.readLine();
			}
			in.close();
			JOptionPane.showMessageDialog(null, "Adatok beolvasva!", Utils.getMes(), 1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "JsonReader: " + e.getMessage(), Utils.getMes(), 0);
		}
	}

	public static void jsonWriter(String fnev, BetegekListaLayout layoutParameter) {
		List<BetegekModel> dataFromTable = getDataFromTable(layoutParameter);
		Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fnev));
			for (BetegekModel beteg : dataFromTable) {
				out.println(gson.toJson(beteg));
			}
			JOptionPane.showMessageDialog(null, "json adatok kiirva!", Utils.getMes(), 1);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "JsonWriter: " + e.getMessage(), Utils.getMes(), 0);
		}
	}

	public static void xmlReader(File fnev, BetegekListaLayout layoutParameter) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(BetegekWrapper.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			BetegekWrapper betegek = (BetegekWrapper) jaxbUnmarshaller.unmarshal(new File(fnev.toString()));
			for (BetegekModel beteg : betegek.getBetegek()) {
				String szulido = Utils.getSdf().format(beteg.getSzulido());
				String uvizsga = Utils.getSdf().format(beteg.getUvizsgalat());
				layoutParameter.addRow(new Object[] { new Boolean(false), beteg.getTaj(), beteg.getNev(), szulido,
						uvizsga, beteg.getBetegseg() });
			}
			JOptionPane.showMessageDialog(null, "XML adatok beolvasva!", Utils.getMes(), 1);
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(null, "XML reader hiba: " + e.getMessage(), Utils.getMes(), 0);
		}
	}

	public static void xmlWriter(String fnev, BetegekListaLayout layoutParameter) {
		BetegekWrapper betegekWrapper = new BetegekWrapper();
		betegekWrapper.setBetegek(new ArrayList<BetegekModel>());

		List<BetegekModel> dataFromTable = getDataFromTable(layoutParameter);
		for (BetegekModel beteg : dataFromTable) {
			betegekWrapper.getBetegek().add(beteg);
		}

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(BetegekWrapper.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(betegekWrapper, new File(fnev));

			JOptionPane.showMessageDialog(null, "XML adatok kiírva!", Utils.getMes(), 1);
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(null, "XMLWriter: " + e.getMessage(), Utils.getMes(), 0);
		}
	}

	public static void dbReader(BetegekListaLayout layoutParameter) {
		Connection conn = DBUtil.GetDBConnection();
		String sqlp = "SELECT * FROM betegek";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlp);
			while (rs.next()) {
				BetegekModel beteg = new BetegekModel();
				beteg.setTaj(rs.getInt(1));
				beteg.setNev(rs.getString(2));
				String szulido = Utils.getSdf().format(rs.getDate(3));
				String uvizsga = Utils.getSdf().format(rs.getDate(4));
				beteg.setBetegseg(rs.getString(5));
				layoutParameter.addRow(new Object[] { new Boolean(false), beteg.getTaj(), beteg.getNev(), szulido,
						uvizsga, beteg.getBetegseg() });
			}
			JOptionPane.showMessageDialog(null, "Adatbázis adatok beolvasva!", Utils.getMes(), 1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Adatbázis reader hiba: " + e.getMessage(), Utils.getMes(), 0);
		}
		DBUtil.DisconnectFromDB(conn);
	}

	public static void dbWriter(BetegekListaLayout layoutParameter) {
		Connection conn = DBUtil.GetDBConnection();
		List<BetegekModel> dataFromTable = getDataFromTable(layoutParameter);
		PreparedStatement stmt = null;
		Statement check_stmt = null;

		String sqlWipeData = "DELETE FROM betegek WHERE 1";
		String sqlInsertNewBeteg = "INSERT INTO betegek VALUES (?,?,?,?,?)";
		try {
			check_stmt = conn.createStatement();
			check_stmt.executeUpdate(sqlWipeData);
			for (BetegekModel beteg : dataFromTable) {
					stmt = conn.prepareStatement(sqlInsertNewBeteg);
					stmt.setInt(1, beteg.getTaj());
					stmt.setString(2, beteg.getNev());
					stmt.setDate(3, convertStringToSqlDate(Utils.getSdf().format(beteg.getSzulido())));
					stmt.setDate(4, convertStringToSqlDate(Utils.getSdf().format(beteg.getUvizsgalat())));
					stmt.setString(5, beteg.getBetegseg());
					stmt.execute();
			}
			JOptionPane.showMessageDialog(null, "Adatbázisba kiirva az adatok!", Utils.getMes(), 1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Adatbázis writer hiba: " + e.getMessage(), Utils.getMes(), 0);
		}
		DBUtil.DisconnectFromDB(conn);
	}

	public static void pdfWriter(String fnev, BetegekListaLayout layoutParameter) {
		List<BetegekModel> dataFromTable = getDataFromTable(layoutParameter);

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fnev));
			document.open();

			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
			Font fnt = new Font(bf, 16, Font.BOLD);

			Paragraph par = new Paragraph("Beteglista", fnt);
			document.add(par);
			document.add(new Paragraph("\n \n \n \n"));

			PdfPTable table = new PdfPTable(5);

			table.setWidthPercentage(80);

			Font f1 = new Font(bf, 14, Font.BOLD);

			Font f2 = new Font(bf, 14);

			PdfPCell c = new PdfPCell(new Paragraph("Taj", f1));
			table.addCell(c);
			c = new PdfPCell(new Paragraph("Név", f1));
			table.addCell(c);
			c = new PdfPCell(new Paragraph("Születési dátum", f1));
			table.addCell(c);
			c = new PdfPCell(new Paragraph("Utolsó vizsgálat dátuma", f1));
			table.addCell(c);
			c = new PdfPCell(new Paragraph("Betegség", f1));
			table.addCell(c);
			for (BetegekModel beteg : dataFromTable) {
				c = new PdfPCell(new Paragraph(String.valueOf(beteg.getTaj()), f2));
				table.addCell(c);
				c = new PdfPCell(new Paragraph(beteg.getNev(), f2));
				table.addCell(c);
				String szulido = Utils.getSdf().format(beteg.getSzulido());
				String uvizsga = Utils.getSdf().format(beteg.getUvizsgalat());
				c = new PdfPCell(new Paragraph(szulido, f2));
				table.addCell(c);
				c = new PdfPCell(new Paragraph(uvizsga, f2));
				table.addCell(c);
				c = new PdfPCell(new Paragraph(beteg.getBetegseg(), f2));
				table.addCell(c);
			}
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			document.add(table);
			document.close();

		} catch (DocumentException de) {
			JOptionPane.showMessageDialog(null, "PDFWriter: " + de.getMessage(), Utils.getMes(), 0);

		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "PDFWriter: " + ex.getMessage(), Utils.getMes(), 0);
		}

		JOptionPane.showMessageDialog(null, "pdf adatok kiírva!", Utils.getMes(), 1);
	}

	public static List<BetegekModel> getDataFromTable(BetegekListaLayout layoutParameter) {
		List<BetegekModel> dataFromTable = new ArrayList<BetegekModel>();
		int rdb = layoutParameter.getRowCount();
		int cdb = layoutParameter.getColumnCount();

		for (int i = 0; i < rdb; i++) {
			BetegekModel beteg = new BetegekModel();
			for (int j = 1; j < cdb - 1; j++) {
				beteg.setTaj(Integer.parseInt(layoutParameter.getValueAt(i, 1).toString()));
				beteg.setNev(layoutParameter.getValueAt(i, 2).toString());
				beteg.setSzulido(Utils.StoD(layoutParameter.getValueAt(i, 3).toString()));
				beteg.setUvizsgalat(Utils.StoD(layoutParameter.getValueAt(i, 4).toString()));
			}
			beteg.setBetegseg(layoutParameter.getValueAt(i, cdb - 1).toString());
			dataFromTable.add(beteg);
		}
		return dataFromTable;
	}

	
	public static Date convertStringToSqlDate(String s) {
		java.util.Date utilDate = null;
		try {
			utilDate = Utils.getSdf().parse(s);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Date konverzió hiba: " + e.getMessage(), Utils.getMes(), 0);
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}
}
