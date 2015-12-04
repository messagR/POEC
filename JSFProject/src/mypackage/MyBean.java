/**
 * test
 */
package mypackage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * @author PC
 *
 */
// les anotations sont remplacés par le managed-bean du faces-config.xml
// si on ne met pas de name dans managedBean il sera initialisé avec le nom de
// la classe tout en minuscule (mybean)
@ManagedBean(name = "myBean")
@SessionScoped
public class MyBean {

	@ManagedProperty(value = "une autre valeur")
	private String property;

	// pour fonctionner il lui faut quoiqu'il arrive un constructeur punlic sans
	// paramètres (par défaut)

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
