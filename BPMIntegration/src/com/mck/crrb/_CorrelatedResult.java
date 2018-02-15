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
public class _CorrelatedResult extends _IndexedResult {
	private String[] recordKeys;
	private TWList twRecordKeys;
	
	/**
	 * 
	 */
	public _CorrelatedResult() {
		super();
		try {
			this.setTwRecordKeys(TWObjectFactory.createList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param index
	 * @param status
	 * @param message
	 */
	public _CorrelatedResult(int index, String status, String message) {
		super(index, status, message);
	}

	public String[] getRecordKeys() {
		return recordKeys;
	}

	public void setRecordKeys(String[] recordKeys) throws Exception {
		this.recordKeys = recordKeys;
		updateTwRecordKeys(recordKeys);
	}

	public TWList getTwRecordKeys() {
		return twRecordKeys;
	}

	public void setTwRecordKeys(TWList twRecordKeys) {
		this.twRecordKeys = twRecordKeys;
	}

	public void updateTwRecordKeys(String[] recordKeys) throws Exception {
		if (this.twRecordKeys == null) {
			this.twRecordKeys = TWObjectFactory.createList();
		}
		for (int i = 0; i < recordKeys.length; i++) {
			 this.twRecordKeys.addArrayData(recordKeys[i]);
		}
		getTwResult().setPropertyValue("recordKeys", this.twRecordKeys);
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		for (int i = 0; i < recordKeys.length; i++) {
			str += "\n" + recordKeys[i];
		}
		return str;
	}
}
