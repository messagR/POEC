/**
 * test
 */
package mypackage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author PC
 *
 */
@ManagedBean(name = "manyCheckBoxBean")
@RequestScoped
public class ManyCheckBoxBean {

	private List<Person> persons;
	private List<String> selectedPersons;

	public ManyCheckBoxBean() {
		this.persons = new ArrayList<>();
		this.persons.add(new Person(1, "John"));
		this.persons.add(new Person(2, "Sophie"));
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<String> getSelectedPersons() {
		return this.selectedPersons;
	}

	public void setSelectedPersons(List<String> persons) {
		this.selectedPersons = persons;
	}


}
