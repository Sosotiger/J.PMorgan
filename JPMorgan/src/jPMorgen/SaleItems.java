package jPMorgen;

/**
 * 
 * @author Shady Mohsen
 *
 */

public class SaleItems {
	
	//unique name for each product
	private String itemName;
	//number of products
	private Long quantity;
	//total value for each product
	private Double value;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
