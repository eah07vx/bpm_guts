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

public class InvoiceLookupResp extends APIResp {
	private CorrectionRow[] invoiceLookupResp;  // Named to match response from invoiceLookup call 
	private TWList twCorrectionRows;

	/**
	 * @return the invoiceLookupResp
	 */
	public CorrectionRow[] getInvoiceLookupResp() {
		return invoiceLookupResp;
	}

	/**
	 * @param invoiceLookupResp the invoiceLookupResp to set
	 * @throws Exception 
	 */
	public void setInvoiceLookupResp(CorrectionRow[] invoiceLookupResp) throws Exception {
		this.invoiceLookupResp = invoiceLookupResp;
		updateTwCorrectionRows(invoiceLookupResp);
	}

	public TWList getTwCorrectionRows() {
		return twCorrectionRows;
	}

	public void setTwCorrectionRows(TWList twCorrectionRows) {
		this.twCorrectionRows = twCorrectionRows;
	}

	public void updateTwCorrectionRows(CorrectionRow[] invoiceLookupResp) throws Exception {
		this.twCorrectionRows = TWObjectFactory.createList();
		int size = invoiceLookupResp.length;
		for (int i = 0; i < size; i++) {
			this.twCorrectionRows.addArrayData(invoiceLookupResp[i].getTwCorrectionRow());
		}
	}

	public int numberOfInvoiceLines() {
		return (invoiceLookupResp != null) ? invoiceLookupResp.length : 0;
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
/*
	@Override
	public Set<String> getPropertyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPropertyValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeProperty(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyValue(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTWClassName() throws TeamWorksException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
