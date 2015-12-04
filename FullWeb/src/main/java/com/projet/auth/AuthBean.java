/**
 * test
 */
package com.projet.auth;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author PC
 *
 */
@ManagedBean(name = "authBean")
@SessionScoped
public class AuthBean {

	private String mail;
	private String password;
	private Boolean connection = false;

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

	public Boolean getConnection() {
		return this.connection;
	}

	public void setConnection(Boolean connection) {
		this.connection = connection;
	}

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		if (this.mail.equals("test@test.com") && this.password.equals("titi")) {
			this.connection = true;
			return "category";
		} else {
			this.connection = false;
			context.addMessage(null, new FacesMessage(bundle.getString("connection.fail")));
			return "connection";
		}
	}

	public String logoff() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		this.connection = false;
		context.addMessage(null, new FacesMessage(bundle.getString("connection.off")));
		return "connection";
	}

}
