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
	private _PriceCorrectionMaterial[] materials;
	
	public _PriceCorrectionMaterial[] getMaterials() {
		return materials;
	}
	public void setMaterials(_PriceCorrectionMaterial[] materials) {
		this.materials = materials;
	}
	
	public String toString() {
		String delim = "";
		String str = "";
		str = super.toString();
		str += ", materials[]: ";
		if (materials != null) {
			for (int i = 0; i < materials.length; i++) {
				str += delim + " {" + materials[i].toString() + "}";
				delim = ",";
			}
		}
		else 
			str += materials;
		return str;
	}
}
