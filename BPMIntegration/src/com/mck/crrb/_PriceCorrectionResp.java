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
public class _PriceCorrectionResp extends _APIResp {
	
	private _PriceCorrectionRow[] priceCorrectionResp; // Named to match response from submitPriceCorrection call 
	private TWList twPriceCorrectionRows;

	public _PriceCorrectionResp() {
		super();
		try {
			this.twPriceCorrectionRows = TWObjectFactory.createList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public _PriceCorrectionRow[] getPriceCorrectionResp() {
		return priceCorrectionResp;
	}

	public void setPriceCorrectionResp(_PriceCorrectionRow[] priceCorrectionResp) throws Exception {
		this.priceCorrectionResp = priceCorrectionResp;
		updateTwPriceCorrectionRows(priceCorrectionResp);
	}
	
	public int numberOfResponseLines() {
		return (priceCorrectionResp != null) ? priceCorrectionResp.length : 0;
	}

	public TWList getTwPriceCorrectionRows() {
		return twPriceCorrectionRows;
	}

	public void setTwPriceCorrectionRows(TWList twPriceCorrectionRows) {
		this.twPriceCorrectionRows = twPriceCorrectionRows;
	}

	public void updateTwPriceCorrectionRows(_PriceCorrectionRow[] priceCorrectionResp) throws Exception {
		if (this.twPriceCorrectionRows == null) {
			this.twPriceCorrectionRows = TWObjectFactory.createList();
		}
		int size = priceCorrectionResp.length;
		for (int i = 0; i < size; i++) {
			this.twPriceCorrectionRows.addArrayData(priceCorrectionResp[i].getTwPriceCorrectionRow());
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < priceCorrectionResp.length; i++) {
			str += "\n" + priceCorrectionResp[i].toString();
		}
		str += "\n " +  super.toString();
		return str;
	}
}
