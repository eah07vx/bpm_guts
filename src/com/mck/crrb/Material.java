/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class Material {
	
	private String materialId;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = Utility.trimLeadingChars(materialId, "0");
	}

}
