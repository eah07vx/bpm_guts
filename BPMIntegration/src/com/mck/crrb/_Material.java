/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class _Material {
	
	private String materialId;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = _Utility.trimLeadingChars(materialId, "0");
	}

}
