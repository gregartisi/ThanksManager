package thanks.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Produit {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String image;
	private double price;
	private long typeProduit;
	private int quantity;
	
	
	
	
	
	public Produit() {
		super();
		
	}
	
	
	public Produit(String name, String image, double price,long typeProduit,int quantity) {
		super();
		
		this.name = name;
		this.image = image;
		this.price = price;
		this.typeProduit = typeProduit;
		this.quantity = quantity;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}


	public long getTypeProduit() {
		return typeProduit;
	}


	public void setTypeProduit(long typeProduit) {
		this.typeProduit = typeProduit;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
