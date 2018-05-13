/**
 * 
 */
package com.mck.crrb;

import java.util.Iterator;
import java.util.Set;

import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class _BillingAttributeRow extends _IndexedResult {
	
	private TWObject twBillingAttributeRow;
	//customer
	private String customerId;
	//billing attributes
	private String ediSuppression;
	private String consolidatedPONumber;

	public _BillingAttributeRow() {
		super();
		try {
			this.twBillingAttributeRow = TWObjectFactory.createObject();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = _Utility.trimLeadingChars(customerId, "0");
		this.twBillingAttributeRow.setPropertyValue("customerId", this.customerId);
	}

	public String getEdiSuppression() {
		return ediSuppression;
	}

	public void setEdiSuppression(String ediSuppression) {
		this.ediSuppression = ediSuppression;
		this.twBillingAttributeRow.setPropertyValue("ediSuppression", this.ediSuppression);
	}
	
	//TODO: Remove getEdiSupp method (and setEdiSupp)
	/* Convenience method since the API is currently returning truncated name for ediSuppression flag as ediSupp instead */
	/*
	public String getEdiSupp() {
		return ediSupp;
	}
	*/
	//TODO: Remove setEdiSupp method (and getEdiSupp)
	/* Convenience method since the API is currently returning truncated name for ediSuppression flag as ediSupp instead */
	/*public void setEdiSupp(String ediSupp) {
		this.ediSupp = ediSupp;
		this.twBillingAttributeRow.setPropertyValue("ediSupp", this.ediSupp);
	}*/
	
	public String getConsolidatedPONumber() {
		return consolidatedPONumber;
	}

	public void setConsolidatedPONumber(String consolidatedPONumber) {
		this.consolidatedPONumber = consolidatedPONumber;
		this.twBillingAttributeRow.setPropertyValue("consolidatedPONumber", this.consolidatedPONumber);
	}
	
	public TWObject getTwBillingAttributeRow() {
		TWObject res = super.getTwResult();
		Set<String> propSet = res.getPropertyNames();
		Iterator<String> props = (propSet != null ? propSet.iterator() : null);
		if (props != null) {
			if (this.twBillingAttributeRow == null) {
				try {
					this.twBillingAttributeRow = TWObjectFactory.createObject();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			while (props.hasNext()) {
				String prop = props.next();
				this.twBillingAttributeRow.setPropertyValue(prop, res.getPropertyValue(prop));
			}
		}
		return twBillingAttributeRow;
	}
	
	public void setTwBillingAttributeRow(TWObject twBillingAttributeRow) {
		this.twBillingAttributeRow = twBillingAttributeRow;
	}
}
