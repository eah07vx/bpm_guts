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
public class Z_InvoiceLookupResp extends _APIResp {
	private _CorrectionRow[] invoiceLookupResp;
	private TWList twCorrectionRows;

	/**
	 * @return the invoiceLookupResp
	 */
	public _CorrectionRow[] getInvoiceLookupResp() {
		return invoiceLookupResp;
	}

	/**
	 * @param invoiceLookupResp the invoiceLookupResp to set
	 * @throws Exception 
	 */
	public void setInvoiceLookupResp(_CorrectionRow[] invoiceLookupResp) throws Exception {
		this.invoiceLookupResp = invoiceLookupResp;
		updateTwCorrectionRows(invoiceLookupResp);
	}

	public TWList getTwCorrectionRows() {
		return twCorrectionRows;
	}

	public void setTwCorrectionRows(TWList twCorrectionRows) {
		this.twCorrectionRows = twCorrectionRows;
	}

	public void updateTwCorrectionRows(_CorrectionRow[] invoiceLookupResp) throws Exception {
		this.twCorrectionRows = TWObjectFactory.createList();
		int size = invoiceLookupResp.length;
		for (int i = 0; i < size; i++) {
			this.twCorrectionRows.addArrayData(invoiceLookupResp[i].getTwCorrectionRow());
		}
	}

	public int numberOfInvoiceLines() {
		return invoiceLookupResp.length | 0;
	}
	
	public String toString() {
		int i = 0;
		String resp = "";
		while(i < invoiceLookupResp.length) {
			resp += invoiceLookupResp[i++] + "\r\n";
		}
		resp += super.toString();
		return resp;
	}
}
