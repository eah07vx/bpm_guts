/**
 * 
 */
package com.mck.crrb;

/**
 * Each record in the simulate price API response. Has materials list.
 * 
 * @author akatre
 *
 */
public class _SimulatePriceRow extends _SimulatePriceRowHeader {
	private _CreditRebillMaterial[] materials;
	
	public _CreditRebillMaterial[] getMaterials() {
		return materials;
	}
	public void setMaterials(_CreditRebillMaterial[] materials) {
		this.materials = materials;
	}
	
	public String toString() {
		String str = "";
		str = super.toString();
		str += ", materials[]: ";
		if (materials != null) {
			for (int i = 0; i < materials.length; i++) {
				str += "\n" + materials[i].toString();
			}
		}
		else 
			str += materials;
		return str;
	}
}
