/**
 * test
 */
package mypackage;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * @author PC
 *
 */
@ManagedBean(name = "personBean")
@RequestScoped
public class PersonBean {

	private Person person = new Person();
	private boolean likesMusic = false;

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person persons) {
		this.person = persons;
	}

	public boolean isLikesMusic() {
		return this.likesMusic;
	}

	public void setLikesMusic(boolean likesMusic) {
		System.out.println(this.likesMusic);
		this.likesMusic = likesMusic;
		System.out.println(this.likesMusic);
	}

	public void validateLikesMusic(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String music = arg2.toString();
		UIInput checkbox = (UIInput) arg1.findComponent("musicCheckBox");
		boolean likesMusic = (boolean) checkbox.getValue();
		if (likesMusic && (!music.contains("musique"))) {
			FacesMessage message = new FacesMessage(
					"Comme vous aimez la musique, merci de spécifier un titre ou un auteur de musique classique");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

}
