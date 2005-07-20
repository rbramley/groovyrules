package org.groovyrules.test;


/**
 * Invoice class, mirroring that in the JSR94 TCK.
 *
 * @author Rob Newsome
 */
public class Invoice {

	private String description;
	private int amount;
	private String status;
	
	/** 
	 * Create an instance of the Invoice class.
	 *
	 * @param description The description of the invoice.
	 */
	public Invoice(String description) {
		this.description = description;
		this.status = "unpaid";
	}

	/**
	 * @return Returns the amount.
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
