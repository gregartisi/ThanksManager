package thanks.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String adress;
	private String tel;
	private String email;
	private String contact;
	private int pays;
	
	
	public Customer() {
		super();
		
	}
	
	public Customer(String name, String adress, String tel, String email, String contact,int pays) {
		super();
		this.name = name;
		this.adress = adress;
		this.tel = tel;
		this.email = email;
		this.contact = contact;
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
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getPays() {
		return pays;
	}

	public void setPays(int pays) {
		this.pays = pays;
	}
}
