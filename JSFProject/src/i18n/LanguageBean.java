/**
 * test
 * i18n = internationalization
 */
package i18n;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author PC
 *
 */
@ManagedBean(name = "languageBean")
@RequestScoped
public class LanguageBean {

	private String locale;
	// private static Map<String, Locale> locales;
	private Map<String, Locale> locales;

	// static {
	// // linkedHashMap force le tri dans l'ordre des puts
	// // hashMap fait le tri qu'il veut
	// LanguageBean.locales = new LinkedHashMap<String, Locale>();
	// LanguageBean.locales.put("en", Locale.ENGLISH);
	// LanguageBean.locales.put("fr", Locale.FRENCH);
	// LanguageBean.locales.put("it", Locale.ITALIAN);
	// }

	/**
	 *
	 */
	public LanguageBean() {
		// linkedHashMap force le tri dans l'ordre des puts
		// hashMap fait le tri qu'il veut
		this.locales = new LinkedHashMap<String, Locale>();
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		this.locales.put(bundle.getString("language.en"), Locale.ENGLISH);
		this.locales.put(bundle.getString("language.fr"), Locale.FRENCH);
		this.locales.put(bundle.getString("language.it"), Locale.ITALIAN);
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	// public static Map<String, Locale> getLocales() {
	// return LanguageBean.locales;
	// }
	//
	// public static void setLocales(Map<String, Locale> localesInMap) {
	// LanguageBean.locales = localesInMap;
	// }

	public Map<String, Locale> getLocales() {
		return this.locales;
	}

	public void setLocales(Map<String, Locale> localesInMap) {
		this.locales = localesInMap;
	}

	public Map<String, Locale> getLocalesInMap() {
		return this.getLocales();
	}

	public void changeLocale() {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locales.get(this.locale));
	}

}
