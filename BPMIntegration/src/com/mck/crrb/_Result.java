/**
 * 
 */
package com.mck.crrb;

import teamworks.TWList;
import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
class _Result {

	private String status;
	private String messageId;
	private String message;
	private String errorCode;
	private String responseCode;
	private String groupMessageId;
	private String[] subgroupMessageIds;
	private TWObject twResult;
	
	public _Result() {
		try {
			this.twResult = TWObjectFactory.createObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public _Result(int index, String status, String message) {
		try {
			this.twResult = TWObjectFactory.createObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setStatus(status);
		this.setMessage(message);
	} 

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		this.twResult.setPropertyValue("status", this.status);
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
		this.twResult.setPropertyValue("messageId", this.messageId);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		this.twResult.setPropertyValue("message", this.message);
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		this.twResult.setPropertyValue("errorCode", this.errorCode);
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
		this.twResult.setPropertyValue("responseCode", this.responseCode);
	}

	public String getGroupMessageId() {
		return groupMessageId;
	}

	public void setGroupMessageId(String groupMessageId) {
		this.groupMessageId = groupMessageId;
		this.twResult.setPropertyValue("groupMessageId", this.groupMessageId);
	}

	public String[] getSubgroupMessageIds() {
		return subgroupMessageIds;
	}

	public void setSubgroupMessageIds(String[] subgroupMessageIds) throws Exception {
		this.subgroupMessageIds = subgroupMessageIds;
		TWList twSubgroupMessageIds = TWObjectFactory.createList(); 
		for (int i = 0; i < subgroupMessageIds.length; i++) {
			twSubgroupMessageIds.addArrayData(subgroupMessageIds[i]);
		}
		this.twResult.setPropertyValue("subgroupMessageIds", twSubgroupMessageIds);
	}

	public TWObject getTwResult() {
		return twResult;
	}
	public void setTwResult(TWObject twResult) {
		this.twResult = twResult;
	}
	@Override
	public String toString() {
		return "status:" + status + ", messageId:" + messageId + ", message:" + message + ", errorCode:" + errorCode + ", responseCode:" + responseCode; 
	}
}
