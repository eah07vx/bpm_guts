/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * @author akatre
 *
 */
public class _ConsolidatedCPRow {

	private int index;
	private String customerId;
	private Date pricingDate;
	private _CurrentPriceMaterial[] materials;
	
	public _ConsolidatedCPRow() {
		super();
	}

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

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}

	public _CurrentPriceMaterial[] getMaterials() {
		return materials;
	}

	public void setMaterials(_CurrentPriceMaterial[] materials) {
		this.materials = materials;
	}

	@Override
	public String toString() {
		String str = "";
		str += " index: " + index + ", customerId: " + customerId + ", pricingDate: " + pricingDate; 
		if (materials != null) { 
			for (int i = 0; i < materials.length; i++) {
				str += "\n" + materials[i].toString();
			}
		}
		str += "\n " + super.toString();
		return str;
	}
}
