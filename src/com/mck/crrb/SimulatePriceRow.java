/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class SimulatePriceRow extends SimulatePriceRowHeader {
	
	private CreditRebillMaterial[] materials;
	
	public CreditRebillMaterial[] getMaterials() {
		return materials;
	}
	public void setMaterials(CreditRebillMaterial[] materials) {
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
