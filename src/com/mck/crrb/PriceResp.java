/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 * This class is currently not extending from APIResp since the current price API does not return startIndex, endIndex and totalNumberOfRecords.
 * If that API returns those values in the future then this class can either be replaced with APIResp or parameters unique but common across all price API responses can be added here.
 */
public class PriceResp {
	
	private IndexedResultStatus[] results;
	
	/**
	 * @return the results
	 */
	public IndexedResultStatus[] getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(IndexedResultStatus[] results) {
		this.results = results;
	}
}
