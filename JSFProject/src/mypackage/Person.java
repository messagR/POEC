/**
 * test
 */
package mypackage;

/**
 * @author PC
 *
 */
public class Person {

	private int id;
	private String name;
	private String image;
	private Integer age;
	private String music = "";

	public Person() {
		super();
	}

	public Person(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Person(int id, String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
	}

	public Person(int id, String name, String image, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.age = age;
	}

	public Person(int id, String name, String image, Integer age, String music) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.age = age;
		this.music = music;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getAge() {
		return this.age;
	}

	public String getMusic() {
		return this.music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
