package com.mck.crrb;

public class SimulatePriceRowHeader extends PriceRow implements Comparable<SimulatePriceRowHeader> {
	private int index; 
	private String salesOrg;
	private String billType;
	private String orderType;
	//TODO: remove recordKey from header level - keep only in material level
	//private String recordKey; // Consists of invoiceId-invoiceLineItemNum
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/*
	//TODO: remove recordKey from header level - keep only in material level
	public String getRecordKey() {
		return recordKey;
		this here
	}
	//TODO: remove recordKey from header level - keep only in material level
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}
	*/
	@Override
	public String toString() {
		String str = "";
		str = super.toString();
		str += ", index: " + index + ", salesOrg: " + salesOrg + ", billType: " + billType;
		return str;
	}
	@Override
	public int compareTo(SimulatePriceRowHeader arg0) {
		int comparison = -1;
		if ((this.getCustomerId()).equals((String)arg0.getCustomerId()) && (this.getPricingDate()).compareTo(arg0.getPricingDate()) == 0) {
			comparison = 0;
		}
		return comparison;		
	}
}
