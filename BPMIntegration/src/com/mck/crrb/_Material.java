/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class _Material {
	
	protected String materialId;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = _Utility.trimLeadingChars(materialId, "0");
	}
	
	@Override
	public String toString() {
		String str = "\n materialId: " + materialId;
		return str;
	}
}
