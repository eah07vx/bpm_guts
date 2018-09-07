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
	private String correctionType;
	private String customerId;
	private Date pricingDate;
	private String orderType;
	private String salesOrg;
	private String billType;
	private String idtCaseType;
	private String idtCaseNumber;
	private String reasonCode;
	private String submittedBy;
	private String ediSuppression;
	private String consolidatedPONumber;
	private String newRebillCust;

	public _APIReqHeader (TWObject reqHeader) {
		if (reqHeader != null) { 
			this.setCorrelationId((String)reqHeader.getPropertyValue("correlationId"));
			this.setCorrectionType((String)reqHeader.getPropertyValue("correctionType"));
			this.setIdtCaseType((String)reqHeader.getPropertyValue("idtCaseType"));
			this.setIdtCaseNumber((String)reqHeader.getPropertyValue("idtCaseNumber"));
			this.setReasonCode((String)reqHeader.getPropertyValue("reasonCode"));
			this.setSubmittedBy((String)reqHeader.getPropertyValue("submittedBy"));
			this.setNewRebillCust((String)reqHeader.getPropertyValue("newRebillCust"));
		}
	}
	
	public _APIReqHeader (_CorrectionRowISO invoiceLine, TWObject reqHeader, int index) {
        this.setIndex(index);
        if (reqHeader != null) {
            this.setCorrelationId((String)reqHeader.getPropertyValue("correlationId"));
            this.setCorrectionType((String)reqHeader.getPropertyValue("correctionType"));
            this.setIdtCaseType((String)reqHeader.getPropertyValue("idtCaseType"));
            this.setIdtCaseNumber((String)reqHeader.getPropertyValue("idtCaseNumber"));
            this.setReasonCode((String)reqHeader.getPropertyValue("reasonCode"));
            this.setSubmittedBy((String)reqHeader.getPropertyValue("submittedBy"));
        }
        if (invoiceLine != null) {
            this.setCustomerId(invoiceLine.getCustomerId());
            this.setNewRebillCust(invoiceLine.getNewRebillCust());
            // Moving pricingDate into materials array
            //this.setPricingDate(invoiceLine.getPricingDate());
            this.setOrderType(invoiceLine.getOrderType());
            this.setSalesOrg(invoiceLine.getSalesOrg());
            this.setBillType(invoiceLine.getBillType());
            this.setEdiSuppression(invoiceLine.getEdiSuppression());
            this.setConsolidatedPONumber(invoiceLine.getConsolidatedPONumber());
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
	public String getCorrectionType() {
		return correctionType;
	}

	public void setCorrectionType(String correctionType) {
		this.correctionType = correctionType;
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
	public String getEdiSuppression() {
		return ediSuppression;
	}
	public void setEdiSuppression(String ediSuppression) {
		this.ediSuppression = ediSuppression;
	}
	public String getConsolidatedPONumber() {
		return consolidatedPONumber;
	}
	public void setConsolidatedPONumber(String consolidatedPONumber) {
		this.consolidatedPONumber = consolidatedPONumber;
	}
	public String getNewRebillCust() {
		return newRebillCust;
	}
	public void setNewRebillCust(String newRebillCust) {
		this.newRebillCust = newRebillCust; 
	}
	@Override
	public String toString() {
		return ", index: " + index + ", correlationId: " + correlationId + ", customerId: " + customerId
				+ ", pricingDate: " + (pricingDate != null ? pricingDate.toString() : null) + ", orderType: " + orderType
				+ ", salesOrg: " + salesOrg + ", billType: " + billType 
				+ ", idtCaseType: " + idtCaseType + ", idtCaseNumber: " + idtCaseNumber + ", reasonCode: " + reasonCode + ", submittedBy: " + submittedBy
				+ ", ediSuppression: " + ediSuppression + ", consolidatedPONumber: " + consolidatedPONumber + ", newRebillCust: " + newRebillCust;		
	}
	/**
	 * used for consolidation logic to group header
	 */
	@Override
	public int compareTo(_APIReqHeader arg0) {
		int comparison = -1;
		if (arg0 == null) { // As the method is called on this object - argument being null means its unequal
			throw new NullPointerException(this.getClass().getName() + ".compareTo() null argument");
		}
		//TODO: Remove sop
		/*
		 System.out.println(this.getClass().getName() + ".compareTo: " + 
		 		"\nthis.customerId: |" + this.customerId + "| equals |" + (arg0.getCustomerId()) +
				"|?\nthis.salesOrg: |" + this.salesOrg + "| equals |" + (arg0.getSalesOrg()) +
				"|?\nthis.consolidatedPONumber: |" + this.consolidatedPONumber + "| equals |" + (arg0.getConsolidatedPONumber()) +
				"|?\nthis.ediSuppression: |" + this.ediSuppression + "| equals |" + arg0.getEdiSuppression());
		*/
		if (this.customerId.equals(arg0.getCustomerId()) 
				&& this.salesOrg.equals(arg0.getSalesOrg())	
				&& this.newRebillCust.equals(arg0.getNewRebillCust())
				&& ((this.consolidatedPONumber == null && arg0.getConsolidatedPONumber() == null) || this.consolidatedPONumber.equals(arg0.getConsolidatedPONumber()))
				&& ((this.ediSuppression == null && arg0.getEdiSuppression() == null) || this.ediSuppression.equals(arg0.getEdiSuppression()))) {
			comparison = 0;
		}
		System.out.println("\n    comparison: " + comparison);
		return comparison;
	}
}
