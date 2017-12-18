/**
 * 
 */
package com.mck.crrb;

import teamworks.TWObject;

/**
 * @author akatre
 *
 */
public class _PriceCorrectionRow extends _IndexedResult {
	private TWObject twPriceCorrectionRow;
	
	private String customerId;
	private String correlationid;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
		this.twPriceCorrectionRow.setPropertyValue("customerId", this.customerId);
	}

	public String getCorrelationid() {
		return correlationid;
	}
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
		this.twPriceCorrectionRow.setPropertyValue("correlationid", this.correlationid);
	}

	public TWObject getTwPriceCorrectionRow() {
		return twPriceCorrectionRow;
	}
	public void setTwPriceCorrectionRow(TWObject twPriceCorrectionRow) {
		this.twPriceCorrectionRow = twPriceCorrectionRow;
	}
}
