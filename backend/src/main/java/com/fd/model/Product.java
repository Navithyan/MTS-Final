package com.fd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="myProduct") // no need to mention when class name and table name is same
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;  
	@Column(name="pname", nullable=false)
//    @NotBlank(message = "Product name must not be blank") //Rejects null + empty + spaces
	private String pname;   
	@Column(name="pprice", nullable=false)
	private double price;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(int id, String pname, double price) {
		super();
		this.id = id;
		this.pname = pname;
		this.price = price;
	}
	public int getId() {
		return id;
	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}  

	

}
