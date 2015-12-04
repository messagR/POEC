/**
 * test
 */
package mypackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 * @author PC
 *
 */
@ManagedBean(name = "welcomeBean")
@RequestScoped
public class WelcomeBean {

	@ManagedProperty(value = "#{authBean.mail}")
	private String mail;

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
