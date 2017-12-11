package com.mck.crrb;

public class SimulatePriceRowHeader extends PriceRow {
	private int index; 
	private String salesOrg;
	private String billType;
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
	/*
	//TODO: remove recordKey from header level - keep only in material level
	public String getRecordKey() {
		return recordKey;
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
}
