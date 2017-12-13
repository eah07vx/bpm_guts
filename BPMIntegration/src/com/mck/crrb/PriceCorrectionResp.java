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
public class PriceCorrectionResp extends APIResp {
	
	private PriceCorrectionRow[] priceCorrectionResp; // Named to match response from submitPriceCorrection call 
	private TWList twPriceCorrectionRows;

	public PriceCorrectionRow[] getPriceCorrectionResp() {
		return priceCorrectionResp;
	}

	public void setPriceCorrectionResp(PriceCorrectionRow[] priceCorrectionResp) throws Exception {
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

	public void updateTwPriceCorrectionRows(PriceCorrectionRow[] priceCorrectionResp) throws Exception {
		this.twPriceCorrectionRows = TWObjectFactory.createList();
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
