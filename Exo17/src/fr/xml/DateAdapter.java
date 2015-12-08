/**
 * test
 */
package fr.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author PC
 *
 */
public class DateAdapter extends XmlAdapter<String, Date> {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Date unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}
		return this.sdf.parse(v);
	}

	@Override
	public String marshal(Date v) throws Exception {
		if (v == null) {
			return null;
		}
		return this.sdf.format(v);
	}

}
