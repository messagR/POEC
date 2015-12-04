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
@ManagedBean(name = "selectManyListBoxBean")
@RequestScoped
public class SelectManyListBoxBean {

	private List<Person> persons;
	private List<String> selectedPersons;

	public SelectManyListBoxBean() {
		this.persons = new ArrayList<>();
		this.persons.add(new Person(1, "Jean"));
		this.persons.add(new Person(2, "Laura"));
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
