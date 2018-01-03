/**
 * 
 */
package com.mck.crrb;

/**
 * @author akatre
 *
 */
public class _HttpResponse {
	private int responseCode;
	private String responseMessage;
	
	//Default constructor
	public _HttpResponse(){
		
	}
	//Shortcut constructor to set attributes
	public _HttpResponse(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	@Override
	public String toString() {
		return "responseCode: " + responseCode + ", responseMessage: " + responseMessage;
	}
}
