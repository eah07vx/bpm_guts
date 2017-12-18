/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class Z_CurrentPriceRow extends _PriceRow {
	private Z_CurrentPriceMaterial[] materials;

	public Z_CurrentPriceMaterial[] getMaterials() {
		return materials;
	}

	public void setMaterials(Z_CurrentPriceMaterial[] materials) {
		this.materials = materials;
	}
	
	public int numberOfMaterials() {
		return (materials != null) ? materials.length : 0;
	}
}
