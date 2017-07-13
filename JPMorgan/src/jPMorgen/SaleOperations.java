package jPMorgen;

/**
 * 
 * @author Shady Mohsen
 *
 */

public class SaleOperations {
	
	//unique name for each adjustment type
	private String operationType;
	//affected product name
	private String productName;
	//previous quantity of the product before adjustment
	private Long previousQuantity;
	//amount of adjustment
	private Double amount;
	
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getPreviousQuantity() {
		return previousQuantity;
	}
	public void setPreviousQuantity(Long previousQuantity) {
		this.previousQuantity = previousQuantity;
	}
}
