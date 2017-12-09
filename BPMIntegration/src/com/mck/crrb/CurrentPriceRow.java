/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class CurrentPriceRow extends PriceRow {
	private CurrentPriceMaterial[] materials;

	public CurrentPriceMaterial[] getMaterials() {
		return materials;
	}

	public void setMaterials(CurrentPriceMaterial[] materials) {
		this.materials = materials;
	}
	
	public int numberOfMaterials() {
		return (materials != null) ? materials.length : 0;
	}
}
