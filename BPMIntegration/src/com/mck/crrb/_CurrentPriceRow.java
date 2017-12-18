/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class _CurrentPriceRow extends PriceRow {
	private _CurrentPriceMaterial[] materials;

	public _CurrentPriceMaterial[] getMaterials() {
		return materials;
	}

	public void setMaterials(_CurrentPriceMaterial[] materials) {
		this.materials = materials;
	}
	
	public int numberOfMaterials() {
		return (materials != null) ? materials.length : 0;
	}
}
