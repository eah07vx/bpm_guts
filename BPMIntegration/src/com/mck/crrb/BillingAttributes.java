/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class BillingAttributes extends _API {

	public BillingAttributes() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.mck.crrb._API#prepRequest(java.lang.String, teamworks.TWObject, boolean)
	 */
	@Override
	String prepRequest(String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		return requestJSON;	//Request is already formatted 
	}

	/* (non-Javadoc)
	 * @see com.mck.crrb._API#parseResponse(java.lang.String, com.mck.crrb._HttpResponse, boolean)
	 */
	@Override
	TWObject parseResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		_BillingAttributeResp syncResp = null;
		int respCode = httpResp.getResponseCode();
		if (sopDebug) { 
			System.out.println(this.getClass().getName()+ ".parseResponse >> \n    respCode: " + respCode);
			System.out.println("    rawResp: " + rawResp);
		}
		// Prepare TWObject for return
		try {
			TWObject twBillingAttributeResp = TWObjectFactory.createObject();
			TWObject httpResponse = TWObjectFactory.createObject();
			
			String respMsg = httpResp.getResponseMessage();
			httpResponse.setPropertyValue("responseCode", respCode);
			if (respMsg == null || respMsg.equals("")) {
            	respMsg = rawResp;
            }
			if (respCode == HttpsURLConnection.HTTP_OK) {
				syncResp = parseBillingAttributeResp(rawResp);
				twBillingAttributeResp.setPropertyValue("billingAttributeResp", syncResp.getTwBillingAttributeRows());
			}
			else {                
				respMsg = _API.HTTP_NOT_OK + ": " + respMsg; // Not OK - abort mission
			}
			httpResponse.setPropertyValue("responseMessage", respMsg);
			twBillingAttributeResp.setPropertyValue("httpResponse", httpResponse);
			twBillingAttributeResp.setPropertyValue("results", (syncResp != null ? syncResp.getTwResults() : null));

			return twBillingAttributeResp;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}
	
	private _BillingAttributeResp parseBillingAttributeResp(String rawResp) {
		_BillingAttributeResp billingAttributeResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			ObjectMapper jacksonMapper = new ObjectMapper();
			jacksonMapper.configure(
				    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jacksonMapper.setDateFormat(sdf);
			
			billingAttributeResp = jacksonMapper.readValue(rawResp, _BillingAttributeResp.class);
		} catch (JsonParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return billingAttributeResp;
	}
}
