package betegkezelo.model;

import java.text.DateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, Date> {
	private final DateFormat dateFormat = Utils.getSdf();

	@Override
	public Date unmarshal(String xml) throws Exception {
		return dateFormat.parse(xml);
	}

	@Override public String marshal(Date object) throws Exception { return dateFormat.format(object); }
}