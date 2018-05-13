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
class _APIResp {

	private _IndexedResult[] results;
	private int startIndex;
	private int endIndex;
	private int totalNumberOfRecords;
	private TWList twResults;

	public _APIResp() {
		super();
		try {
			this.twResults = TWObjectFactory.createList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the results
	 */
	public _IndexedResult[] getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(_IndexedResult[] results) {
		this.results = results;
		try {
			updateTwResults(results);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return the totalNumberOfRecords
	 */
	public int getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}

	/**
	 * @param totalNumberOfRecords the totalNumberOfRecords to set
	 */
	public void setTotalNumberOfRecords(int totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}

	public TWList getTwResults() {
		return twResults;
	}

	public void setTwResults(TWList twResults) {
		this.twResults = twResults;
	}

	public void updateTwResults(_IndexedResult[] results) throws Exception {
		if (this.twResults == null) {
			this.twResults = TWObjectFactory.createList();
		}
		int size = results.length;
		for (int i = 0; i < size; i++) {
			this.twResults.addArrayData(results[i].getTwResult());
		}
	}

	public int numberOfResults() {
		return (results != null) ? results.length : 0;
	}
	
	public String toString() {
		String str = "startIndex: " + this.startIndex + ", endIndex: " + this.endIndex + ", totalNumberOfRecords: " + this.totalNumberOfRecords + 
				", results.length: " + (results != null ? results.length : -1) + ", results: ";
		if (results != null) {
			for (int i = 0; i < results.length; i++) {
				str += "\r\n  results[" + i + "]: " + results[i].toString();
			}
		}
		else {
			str += "none";
		}
		return str;
	}
}
