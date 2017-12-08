/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class CurrentPriceResp extends PriceResp {
	private CurrentPriceRow[] currentPriceResp;
	 
	//Future use
	//private int startIndex;
	//Future use
	//private int endIndex;
	public CurrentPriceRow[] getCurrentPriceResp() {
		return currentPriceResp;
	}

	public void setCurrentPriceResp(CurrentPriceRow[] currentPriceResp) {
		this.currentPriceResp = currentPriceResp;
	}
	
	public int numberOfResponseLines() {
		return (currentPriceResp != null) ? currentPriceResp.length : 0;
	}
}
