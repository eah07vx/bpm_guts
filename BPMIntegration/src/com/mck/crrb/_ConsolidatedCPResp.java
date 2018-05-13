/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class _ConsolidatedCPResp {
	
	private _ConsolidatedCPRow[] currentPriceResp; // Named to match response from submitPriceCorrection call 

	public _ConsolidatedCPResp() {
		super();
	}
	
	public _ConsolidatedCPRow[] getCurrentPriceResp() {
		return currentPriceResp;
	}

	public void setCurrentPriceResp(_ConsolidatedCPRow[] currentPriceResp) throws Exception {
		this.currentPriceResp = currentPriceResp;
	}
	
	public int numberOfResponseLines() {
		return (currentPriceResp != null) ? currentPriceResp.length : 0;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < currentPriceResp.length; i++) {
			str += "\n" + currentPriceResp[i].toString();
		}
		str += "\n " +  super.toString();
		return str;
	}
}
