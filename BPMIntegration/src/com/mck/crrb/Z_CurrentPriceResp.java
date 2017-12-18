/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class Z_CurrentPriceResp extends _PriceResp {
	private Z_CurrentPriceRow[] currentPriceResp;
	 
	//Future use
	//private int startIndex;
	//Future use
	//private int endIndex;
	public Z_CurrentPriceRow[] getCurrentPriceResp() {
		return currentPriceResp;
	}

	public void setCurrentPriceResp(Z_CurrentPriceRow[] currentPriceResp) {
		this.currentPriceResp = currentPriceResp;
	}
	
	public int numberOfResponseLines() {
		return (currentPriceResp != null) ? currentPriceResp.length : 0;
	}
}
