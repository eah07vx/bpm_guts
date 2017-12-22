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
public class _PriceCorrectionRow extends _IndexedResult {
	private TWObject twPriceCorrectionRow;
	
	private String correlationId;
	private String customerId;
	private String salesOrg;
	private boolean ediSuppression;
	private String consolidatedPONumber;
	
	public _PriceCorrectionRow() {
		super();
		try {
			this.twPriceCorrectionRow = TWObjectFactory.createObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public _PriceCorrectionRow(int index, String status, String message, String correlationId, String customerId, String salesOrg, boolean ediSuppression, String consolidatedPONumber) {
		super(index, status, message);
		try {
			this.twPriceCorrectionRow = TWObjectFactory.createObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setCorrelationId(correlationId);
		this.setCustomerId(customerId);
		this.setSalesOrg(salesOrg);
		this.setEdiSuppression(ediSuppression);
		this.setConsolidatedPONumber(consolidatedPONumber);
	} 

	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
		this.twPriceCorrectionRow.setPropertyValue("correlationId", this.correlationId);
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
		this.twPriceCorrectionRow.setPropertyValue("customerId", this.customerId);
	}

	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
		this.twPriceCorrectionRow.setPropertyValue("salesOrg", this.salesOrg);
	}
	
	public boolean isEdiSuppression() {
		return ediSuppression;
	}
	public void setEdiSuppression(boolean ediSuppression) {
		this.ediSuppression = ediSuppression;
		this.twPriceCorrectionRow.setPropertyValue("ediSuppression", this.ediSuppression);
	}
	
	public String getConsolidatedPONumber() {
		return consolidatedPONumber;
	}
	public void setConsolidatedPONumber(String consolidatedPONumber) {
		this.consolidatedPONumber = consolidatedPONumber;
		this.twPriceCorrectionRow.setPropertyValue("consolidatedPONumber", this.consolidatedPONumber);
	}
	
	public TWObject getTwPriceCorrectionRow() {
		TWObject res = super.getTwResult();
		Set<String> propSet = res.getPropertyNames();
		Iterator<String> props = (propSet != null ? propSet.iterator() : null);
		if (props != null) {
			if (this.twPriceCorrectionRow == null) {
				try {
					this.twPriceCorrectionRow = TWObjectFactory.createObject();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			while (props.hasNext()) {
				String prop = props.next();
				this.twPriceCorrectionRow.setPropertyValue(prop, res.getPropertyValue(prop));
			}
		}
		/*
		this.twPriceCorrectionRow.setPropertyValue("index", super.getIndex());
		this.twPriceCorrectionRow.setPropertyValue("status", super.getStatus());
		this.twPriceCorrectionRow.setPropertyValue("message", super.getMessage());
		*/
		return twPriceCorrectionRow;
	}
	public void setTwPriceCorrectionRow(TWObject twPriceCorrectionRow) {
		this.twPriceCorrectionRow = twPriceCorrectionRow;
	}
}
