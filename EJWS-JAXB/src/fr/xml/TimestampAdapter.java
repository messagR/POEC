/**
 * test
 */
package fr.xml;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author PC
 *
 */
public class TimestampAdapter extends XmlAdapter<String, Timestamp> {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Timestamp unmarshal(String v) throws Exception {
		if (v == null) {
			return null;
		}
		return (Timestamp) this.sdf.parse(v);
	}

	@Override
	public String marshal(Timestamp v) throws Exception {
		if (v == null) {
			return null;
		}
		return this.sdf.format(v);
	}

}
