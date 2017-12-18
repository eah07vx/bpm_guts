/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * Top level price row will have attributes from this POJO at a minimum
 * 
 * @author akatre
 *
 */
class _PriceRow {
	private String customerId;
	private Date pricingDate;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = _Utility.trimLeadingChars(customerId, "0");
	}
	public Date getPricingDate() {
		return pricingDate;
	}
	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}
	@Override
	public String toString() {
		String str = "";
		str += "customerId: " + customerId + ", pricingDate: " + pricingDate;
		return str;
	}
}
