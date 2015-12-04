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
@ManagedBean(name = "dataTableBean")
@RequestScoped
public class DataTableBean {

	private List<Person> persons;

	/**
	 *
	 */
	public DataTableBean() {
		this.persons = new ArrayList<Person>();
		this.persons.add(new Person(1, "Jean", "homme.jpg"));
		this.persons.add(new Person(2, "Sophie", "femme.jpg"));
		this.persons.add(new Person(3, "Homer", "simson.jpg"));
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
