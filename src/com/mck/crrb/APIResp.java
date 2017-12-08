/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class APIResp {

	private IndexedResultStatus[] results;
	private int startIndex;
	private int endIndex;
	private int totalNumberOfRecords;

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

	public String toString() {
		String resp = "";
		resp += "META: {startIndex: " + this.startIndex + ", endIndex: " + this.endIndex + ", totalNumberOfRecords: " + this.totalNumberOfRecords + "}\r\n";
		return resp;
	}
}
