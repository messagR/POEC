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
@ManagedBean(name = "selectOneMenuBean")
@RequestScoped
public class SelectOneMenuBean {

	private List<Person> persons;
	private int selectedPersonsId;

	public SelectOneMenuBean() {
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

	public int getSelectedPersonsId() {
		return this.selectedPersonsId;
	}

	public void setSelectedPersonsId(int idPerson) {
		this.selectedPersonsId = idPerson;
	}

}
