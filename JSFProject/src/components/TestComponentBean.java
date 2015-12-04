/**
 * test
 */
package components;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author PC
 *
 */
@ManagedBean(name = "testComponentBean")
@RequestScoped
public class TestComponentBean {

	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
