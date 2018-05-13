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
public class _BillingAttributeResp extends _APIResp {
	
	private _BillingAttributeRow[] billingAttributeResp; // Named to match response from submitPriceCorrection call 
	private TWList twBillingAttributeRows;

	public _BillingAttributeResp() {
		super();
		try {
			this.twBillingAttributeRows = TWObjectFactory.createList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public _BillingAttributeRow[] getBillingAttributeResp() {
		return billingAttributeResp;
	}

	public void setBillingAttributeResp(_BillingAttributeRow[] billingAttributeResp) throws Exception {
		this.billingAttributeResp = billingAttributeResp;
		updateTwBillingAttributeRows(billingAttributeResp);
	}
	
	public int numberOfResponseLines() {
		return (billingAttributeResp != null) ? billingAttributeResp.length : 0;
	}

	public TWList getTwBillingAttributeRows() {
		return twBillingAttributeRows;
	}

	public void setTwBillingAttributeRows(TWList twPriceCorrectionRows) {
		this.twBillingAttributeRows = twPriceCorrectionRows;
	}

	public void updateTwBillingAttributeRows(_BillingAttributeRow[] billingAttributeResp) throws Exception {
		if (this.twBillingAttributeRows == null) {
			this.twBillingAttributeRows = TWObjectFactory.createList();
		}
		for (int i = 0; i < billingAttributeResp.length; i++) {
			this.twBillingAttributeRows.addArrayData(billingAttributeResp[i].getTwBillingAttributeRow());
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < billingAttributeResp.length; i++) {
			str += "\n" + billingAttributeResp[i].toString();
		}
		str += "\n " +  super.toString();
		return str;
	}
}
