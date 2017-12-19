/**
 * 
 */
package com.mck.crrb;

import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
class _IndexedResult {
	private int index;
	private String status;
	private String message;
	private TWObject twResult;
	
	public _IndexedResult() {
		try {
			this.twResult = TWObjectFactory.createObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
		this.twResult.setPropertyValue("index", this.index);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		this.twResult.setPropertyValue("status", this.status);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		this.twResult.setPropertyValue("message", this.message);
	}
	public TWObject getTwResult() {
		return twResult;
	}
	public void setTwResult(TWObject twResult) {
		this.twResult = twResult;
	}
	@Override
	public String toString() {
		return "index:" + index + ", status:" + status + ", message:" + message; 
	}
}
