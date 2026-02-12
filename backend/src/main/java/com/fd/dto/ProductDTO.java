package com.fd.dto;
 
import com.fd.model.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
 
public class ProductDTO {
	private int id;
    @NotBlank(message = "Product name must not be blank") //Rejects null + empty + spaces
    @Pattern(
            regexp = "^[A-Za-z ]{3,30}$",
            message = "Product name must be 3–30 characters and contain only letters"
        )
	private String pname;
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
	private double price;
	public ProductDTO() {
		
	}
	public ProductDTO(int id, String pname, double price) {
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
	 /* ---------- Entity → DTO ---------- */
    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDTO(
                product.getId(),
                product.getPname(),
                product.getPrice()
        );
    }
 
    /* ---------- DTO → Entity ---------- */
    public static Product fromDTO(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
//        product.setId(dto.getId());   // useful for update
        product.setPname(dto.getPname());
        product.setPrice(dto.getPrice());
        return product;
    }
 
 
}