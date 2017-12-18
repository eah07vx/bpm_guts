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
	
	@Override
	public String toString() {
		return ", index: " + index + ", customerId: " + customerId + ", correlationId: " + correlationId + ", salesOrg: " + salesOrg + ", billType: " + billType;		
	}
	
	@Override
	public int compareTo(_SubmitPriceReqHeader arg0) {
		int comparison = -1;
		if ((this.getCustomerId()).equals((String)arg0.getCustomerId()) && (this.getBillType()).compareTo(arg0.getBillType()) == 0) {
			comparison = 0;
		}
		return comparison;
	}

}
