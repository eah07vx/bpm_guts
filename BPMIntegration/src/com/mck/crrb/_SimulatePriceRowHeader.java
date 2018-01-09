package com.mck.crrb;


/**
 * Header information of each record in the simulate price API response. Subclass can have detail attributes.
 * 
 * @author akatre
 *
 */
public class _SimulatePriceRowHeader extends _PriceRow implements Comparable<_SimulatePriceRowHeader> {
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
	public int compareTo(_SimulatePriceRowHeader arg0) {
		if (arg0 == null) {
			throw new NullPointerException(this.getClass().getName() + ".compareTo() null argument");
		}
		return ((this.getCustomerId()).compareTo((String)arg0.getCustomerId()) | (this.getPricingDate()).compareTo(arg0.getPricingDate())); // bitwise or
	}
}
