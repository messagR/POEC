package composite;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "userData")
@RequestScoped
public class UserData {

	private String name;
	private String password;

	public String getName() {
		return this.name;
	}

	public void setName(String login) {
		this.name = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		return "result";
	}

}
