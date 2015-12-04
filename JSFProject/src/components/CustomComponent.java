/**
 * test
 */
package components;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent(value = "components.customComponent", createTag = true)
public class CustomComponent extends UIComponentBase {

	@Override
	public String getFamily() {
		return "my.custom.component";
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		String value = (String) this.getAttributes().get("value");
		if (value != null) {
			ResponseWriter writer = context.getResponseWriter();
			writer.write(value.toUpperCase());
		}
	}

}
