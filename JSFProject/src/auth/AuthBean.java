/**
 * test
 */
package auth;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author PC
 *
 */
@ManagedBean(name = "authBean")
@SessionScoped
public class AuthBean {

	private String mail;
	private String password;
	private String available;

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String login() {
		if (this.mail.equals("toto")) {
			return "ok";
		} else {
			return "ko";
		}
	}

	public void verify() {
		if (this.mail.equals("toto")) {
			this.available = "ok";
		} else {
			this.available = "invalide";
		}
	}

}
