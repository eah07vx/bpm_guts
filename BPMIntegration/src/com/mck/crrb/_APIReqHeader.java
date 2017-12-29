/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

import teamworks.TWObject;

/**
 * @author akatre
 *
 */
public class _APIReqHeader implements Comparable<_APIReqHeader> {
	private int index; 
	private String correlationId;
	private String customerId;
	private Date pricingDate;
	private String orderType;
	private String salesOrg;
	private String billType;
	private String idtCaseType;
	private String idtCaseNumber;
	private String reasonCode;
	private String submittedBy;
	private boolean ediSuppression;
	private String consolidatedPONumber;

	public _APIReqHeader (TWObject reqHeader) {
		if (reqHeader != null) { 
			this.setCorrelationId((String)reqHeader.getPropertyValue("correlationId"));
			this.setIdtCaseType((String)reqHeader.getPropertyValue("idtCaseType"));
			this.setIdtCaseNumber((String)reqHeader.getPropertyValue("idtCaseNumber"));
			this.setReasonCode((String)reqHeader.getPropertyValue("reasonCode"));
			this.setSubmittedBy((String)reqHeader.getPropertyValue("submittedBy"));
			/*
			//Currently EDI Suppression flag and Consolidated PO Number do NOT come through reqHeader - the are in correction row a.k.a. invoice line
			if ((Boolean)reqHeader.getPropertyValue("ediSuppression") != null) {
				this.setEdiSuppression(((Boolean)reqHeader.getPropertyValue("ediSuppression")).booleanValue());
			}
			if ((String)reqHeader.getPropertyValue("consolidatedPONumber") != null) {
				this.setConsolidatedPONumber((String)reqHeader.getPropertyValue("consolidatedPONumber"));
			}
			*/
		}
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Date getPricingDate() {
		return pricingDate;
	}
	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSalesOrg() {
		return salesOrg;
	}
	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getIdtCaseType() {
		return idtCaseType;
	}
	public void setIdtCaseType(String idtCaseType) {
		this.idtCaseType = idtCaseType;
	}
	public String getIdtCaseNumber() {
		return idtCaseNumber;
	}
	public void setIdtCaseNumber(String idtCaseNumber) {
		this.idtCaseNumber = idtCaseNumber;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public boolean isEdiSuppression() {
		return ediSuppression;
	}
	public void setEdiSuppression(boolean ediSuppression) {
		this.ediSuppression = ediSuppression;
	}
	public String getConsolidatedPONumber() {
		return consolidatedPONumber;
	}
	public void setConsolidatedPONumber(String consolidatedPONumber) {
		this.consolidatedPONumber = consolidatedPONumber;
	}
	@Override
	public String toString() {
		return ", index: " + index + ", correlationId: " + correlationId + ", customerId: " + customerId
				+ ", pricindDate: " + pricingDate.toString() + ", orderType: " + orderType
				+ ", salesOrg: " + salesOrg + ", billType: " + billType 
				+ ", idtCaseType: " + idtCaseType + ", idtCaseNumber: " + idtCaseNumber + ", reasonCode: " + reasonCode + ", submittedBy: " + submittedBy
				+ ", ediSuppression: " + ediSuppression + ", consolidatedPONumber: " + consolidatedPONumber;		
	}
	
	@Override
	public int compareTo(_APIReqHeader arg0) {
		int comparison = -1;
		if (arg0 == null) { // As the method is called on this object - argument being null means its unequal
			return comparison;
		}
		System.out.println(this.getClass().getName() + ".compareTo: " + 
				"\nthis.customerId: " + this.customerId + " equals " + (arg0.getCustomerId()) +
				" ?\nthis.salesOrg: " + this.salesOrg + " equals " + (arg0.getSalesOrg()) +
				" ?\nthis.consolidatedPONumber: " + this.consolidatedPONumber + " equals " + (arg0.getConsolidatedPONumber()) +
				" ?\nthis.ediSuppression: " + this.ediSuppression + " == " + arg0.isEdiSuppression() + 
				" ?\n>>  Comparison: " + 
				((this.customerId.equals(arg0.getCustomerId())) 
				&& (this.salesOrg.equals(arg0.getSalesOrg()))
				&& (this.consolidatedPONumber.equals(arg0.getConsolidatedPONumber()))
				&& (this.ediSuppression == arg0.isEdiSuppression())));
		
		if ((this.customerId.equals(arg0.getCustomerId())) 
				&& (this.salesOrg.equals(arg0.getSalesOrg()))
				&& (this.consolidatedPONumber.equals(arg0.getConsolidatedPONumber()))
				&& (this.ediSuppression == arg0.isEdiSuppression())) {
			comparison = 0;
		}
		return comparison;
	}
}
