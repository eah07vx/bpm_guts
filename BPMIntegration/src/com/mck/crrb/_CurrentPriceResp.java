/**
 * 
 */
package com.mck.crrb;

import teamworks.TWList;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class _CurrentPriceResp extends _APIResp {
	
	private _CurrentPriceRow[] currentPriceResp; // Named to match response from submitPriceCorrection call 
	private TWList twCurrentPriceRows;

	public _CurrentPriceResp() {
		super();
		try {
			this.twCurrentPriceRows = TWObjectFactory.createList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public _CurrentPriceRow[] getCurrentPriceResp() {
		return currentPriceResp;
	}

	public void setCurrentPriceResp(_CurrentPriceRow[] currentPriceResp) throws Exception {
		this.currentPriceResp = currentPriceResp;
		updateTwCurrentPriceRows(currentPriceResp);
	}
	
	public int numberOfResponseLines() {
		return (currentPriceResp != null) ? currentPriceResp.length : 0;
	}

	public TWList getTwCurrentPriceRows() {
		return twCurrentPriceRows;
	}

	public void setTwCurrentPriceRows(TWList twCurrentPriceRows) {
		this.twCurrentPriceRows = twCurrentPriceRows;
	}

	public void updateTwCurrentPriceRows(_CurrentPriceRow[] currentPriceResp) throws Exception {
		if (this.twCurrentPriceRows == null) {
			this.twCurrentPriceRows = TWObjectFactory.createList();
		}
		for (int i = 0; i < currentPriceResp.length; i++) {
			this.twCurrentPriceRows.addArrayData(currentPriceResp[i].getTwCurrentPriceRow());
		}
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
