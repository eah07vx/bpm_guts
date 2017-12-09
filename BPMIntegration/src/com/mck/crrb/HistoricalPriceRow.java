/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class HistoricalPriceRow extends PriceRow {
	private HistoricalPriceMaterial[] materials;

	public HistoricalPriceMaterial[] getMaterials() {
		return materials;
	}

	public void setMaterials(HistoricalPriceMaterial[] materials) {
		this.materials = materials;
	}
	
}
