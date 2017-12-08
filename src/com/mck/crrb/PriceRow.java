/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * @author akatre
 *
 */
public class PriceRow {
	private String customerId;
	private Date pricingDate;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = Utility.trimLeadingChars(customerId, "0");
	}
	public Date getPricingDate() {
		return pricingDate;
	}
	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}
}
