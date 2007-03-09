package org.groovyrules.test;

import javax.rules.ObjectFilter;

/**
 * Filter that allows through only Invoices
 * 
 * @author Rob Newsome
 */
public class InvoiceObjectFilter implements ObjectFilter {

	public Object filter(Object obj) {
		return (obj instanceof Invoice) ? obj : null;
	};

	public void reset() {
	};
};