/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class SimulatePriceResp extends APIResp {
	private SimulatePriceRow[] priceSimulationResp;

	public SimulatePriceRow[] getPriceSimulationResp() {
		return priceSimulationResp;
	}

	public void setPriceSimulationResp(SimulatePriceRow[] priceSimulationResp) {
		this.priceSimulationResp = priceSimulationResp;
	}
	
	public int numberOfResponseLines() {
		return (priceSimulationResp != null) ? priceSimulationResp.length : 0;
	}
	
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
