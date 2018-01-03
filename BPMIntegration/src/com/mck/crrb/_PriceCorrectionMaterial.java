/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class _PriceCorrectionMaterial extends _CreditRebillMaterial {
	private String poNumber;
	// Item DC Markup Percentage trifecta
	private float oldItemDcMarkUpPer;
	private float curItemDcMarkUpPer;
	private float newItemDcMarkUpPer;

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public float getOldItemDcMarkUpPer() {
		return oldItemDcMarkUpPer;
	}

	public void setOldItemDcMarkUpPer(float oldItemDcMarkUpPer) {
		this.oldItemDcMarkUpPer = oldItemDcMarkUpPer;
	}

	public float getCurItemDcMarkUpPer() {
		return curItemDcMarkUpPer;
	}

	public void setCurItemDcMarkUpPer(float curItemDcMarkUpPer) {
		this.curItemDcMarkUpPer = curItemDcMarkUpPer;
	}

	public float getNewItemDcMarkUpPer() {
		return newItemDcMarkUpPer;
	}

	public void setNewItemDcMarkUpPer(float newItemDcMarkUpPer) {
		this.newItemDcMarkUpPer = newItemDcMarkUpPer;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		s += ", oldItemDcMarkUpPer : " + oldItemDcMarkUpPer 
				+ ", curItemDcMarkUpPer: " + curItemDcMarkUpPer 
				+ ", newItemDcMarkUpPer: " + newItemDcMarkUpPer;
		return s;
	}
}
