package thanks.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Typeproduit {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	
	public Typeproduit() {
		super();
		
	}
	public Typeproduit(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
