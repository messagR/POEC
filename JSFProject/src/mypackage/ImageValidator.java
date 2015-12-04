/**
 * test
 */
package mypackage;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author PC
 *
 */
@FacesValidator("imageValidator")
public class ImageValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String input = arg2.toString();
		if (!input.endsWith(".jpg")) {
			FacesMessage message = new FacesMessage(
					arg0.getApplication().getResourceBundle(arg0, "msg").getString("imagevalidator.message"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

}
