/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
class _CurrentPriceResp extends PriceResp {
	private _CurrentPriceRow[] currentPriceResp;
	 
	//Future use
	//private int startIndex;
	//Future use
	//private int endIndex;
	public _CurrentPriceRow[] getCurrentPriceResp() {
		return currentPriceResp;
	}

	public void setCurrentPriceResp(_CurrentPriceRow[] currentPriceResp) {
		this.currentPriceResp = currentPriceResp;
	}
	
	public int numberOfResponseLines() {
		return (currentPriceResp != null) ? currentPriceResp.length : 0;
	}
}
