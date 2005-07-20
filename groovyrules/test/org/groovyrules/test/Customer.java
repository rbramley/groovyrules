package org.groovyrules.test;

/**
 * Customer class, mirroring that in the JSR94 TCK.
 * * @author Rob Newsome
 */
public class Customer {

	private String name;
	private int creditLimit;
	
	public Customer(String name) {
		this.name = name;
	}
	
	/**
	 * @return Returns the creditLimit.
	 */
	public int getCreditLimit() {
		return creditLimit;
	}
	/**
	 * @param creditLimit The creditLimit to set.
	 */
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
