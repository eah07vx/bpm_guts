/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

import teamworks.TWList;
import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class _CurrentPriceRow extends _APIResp {
	
	private int index;
	private String customerId;
	private Date pricingDate;
	private _CurrentPriceMaterial[] materials;
	private TWObject twCurrentPriceRow;
	
	public _CurrentPriceRow() {
		super();
		try {
			this.setTwCurrentPriceRow(TWObjectFactory.createObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
		this.twCurrentPriceRow.setPropertyValue("index", this.index);
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
		this.twCurrentPriceRow.setPropertyValue("customerId", this.customerId);
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
		this.twCurrentPriceRow.setPropertyValue("pricingDate", this.pricingDate);
	}

	public _CurrentPriceMaterial[] getMaterials() {
		return materials;
	}

	public void setMaterials(_CurrentPriceMaterial[] materials) {
		this.materials = materials;
		TWList twMaterials;
		try {
			twMaterials = TWObjectFactory.createList();

			for (int i = 0; i < materials.length; i++) {
				twMaterials.addArrayData(materials[i].getTwCurrentPriceMaterial());
			}
			this.twCurrentPriceRow.setPropertyValue("materials", twMaterials); 
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public TWObject getTwCurrentPriceRow() {
		return twCurrentPriceRow;
	}
	
	public void setTwCurrentPriceRow(TWObject twCurrentPriceRow) {
		this.twCurrentPriceRow = twCurrentPriceRow;
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
