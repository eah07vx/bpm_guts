/**
 * 
 */
package com.mck.crrb;

import teamworks.TWList;

/**
 * @author akatre
 *
 */
public class _SimulatePriceResp extends _APIResp {
	private _SimulatePriceRow[] priceSimulationResp;
	private TWList twCorrectionRows;

	public _SimulatePriceRow[] getPriceSimulationResp() {
		return priceSimulationResp;
	}

	public void setPriceSimulationResp(_SimulatePriceRow[] priceSimulationResp) {
		this.priceSimulationResp = priceSimulationResp;
		//updateTwCorrectionRows(priceSimulationResp);
	}
	
	public TWList getTwCorrectionRows() {
		return twCorrectionRows;
	}

	public void setTwCorrectionRows(TWList twCorrectionRows) {
		this.twCorrectionRows = twCorrectionRows;
	}

	public int numberOfResponseLines() {
		return (priceSimulationResp != null) ? priceSimulationResp.length : 0;
	}
/*
	public void updateTwCorrectionRows(_SimulatePriceRow[] priceSimulationResp) throws Exception {
		this.twCorrectionRows = TWObjectFactory.createList();
		int size = priceSimulationResp.length;
		for (int i = 0; i < size; i++) {
			this.twCorrectionRows.addArrayData(priceSimulationResp[i].getTwCorrectionRow());
		}
	}
*/
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < priceSimulationResp.length; i++) {
			str += "\n" + priceSimulationResp[i].toString();
		}
		str += "\n " +  super.toString();
		return str;
	}
}
