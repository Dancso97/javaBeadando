package betegkezelo.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="Betegek")
@XmlAccessorType(XmlAccessType.FIELD)

public class BetegekModel implements Serializable {
	private int taj;
	private String nev;
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Date szulido;
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Date uvizsgalat;
	private String betegseg;

	public BetegekModel(int taj, String nev, Date szulido, Date uvizsgalat, String betegseg) {
		super();
		this.taj = taj;
		this.nev = nev;
		this.szulido = szulido;
		this.uvizsgalat = uvizsgalat;
		this.betegseg = betegseg;
	}

	public BetegekModel() {
	}


	public int getTaj() {
		return taj;
	}
	
	public void setTaj(int taj) {
		this.taj = taj;
	}

	public String getNev() {
		return nev;
	}


	public void setNev(String nev) {
		this.nev = nev;
	}

	public Date getSzulido() {
		return szulido;
	}


	public void setSzulido(Date szulido) {
		this.szulido = szulido;
	}

	
	public Date getUvizsgalat() {
		return uvizsgalat;
	}
	

	public void setUvizsgalat(Date uvizsgalat) {
		this.uvizsgalat = uvizsgalat;
	}

	public String getBetegseg() {
		return betegseg;
	}

	
	public void setBetegseg(String betegseg) {
		this.betegseg = betegseg;
	}

	
	@Override
	public String toString() {
		return "BetegekModel [taj=" + taj + ", nev=" + nev + ", szulido=" + Utils.getSdf().format(szulido) + ", uvizsgalat=" + Utils.getSdf().format(uvizsgalat)+ ", betegseg=" + betegseg + "]";
	}

	
}
