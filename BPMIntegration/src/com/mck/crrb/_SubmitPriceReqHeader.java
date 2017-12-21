/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class _SubmitPriceReqHeader implements Comparable<_SubmitPriceReqHeader> {
	private int index; 
	private String customerId;
	private String correlationId;
	private String salesOrg;
	private String billType;
	private String idtCaseType;
	private String idtCaseNumber;
	private String reasonCode;
	private String submittedBy;
//	TODO: Validate if orderType is needed?
//	private String orderType;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
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
//	public String getOrderType() {
//		return orderType;
//	}
//	public void setOrderType(String orderType) {
//		this.orderType = orderType;
//	}
	
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
	@Override
	public String toString() {
		return ", index: " + index + ", customerId: " + customerId + ", correlationId: " + correlationId + ", salesOrg: " + salesOrg + ", billType: " + billType 
				+ ", idtCaseType: " + idtCaseType + ", idtCaseNumber: " + idtCaseNumber + ", reasonCode: " + reasonCode + ", submittedBy: " + submittedBy;		
	}
	
	@Override
	public int compareTo(_SubmitPriceReqHeader arg0) {
		int comparison = -1;
		String compCustomerId = (String)arg0.getCustomerId();
		String compBillType = (String)arg0.getBillType();
		String compSalesOrg = (String)arg0.getSalesOrg();
		if (compCustomerId != null && compBillType != null && compSalesOrg != null 
				&& (this.getCustomerId()).equals(compCustomerId) 
				&& (this.getBillType()).equals(compBillType)
				&& (this.getSalesOrg()).equals(compSalesOrg)) {
			comparison = 0;
		}
		return comparison;
	}

}
