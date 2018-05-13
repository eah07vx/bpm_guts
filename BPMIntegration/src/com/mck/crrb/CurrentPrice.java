/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import teamworks.TWList;
import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class CurrentPrice extends _API {
	
	public CurrentPrice() {
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
		TWObject currentPrices = null;
		int respCode = httpResp.getResponseCode();
		if (respCode == HttpsURLConnection.HTTP_OK) {
			currentPrices = parseSyncResponse(rawResp, httpResp, sopDebug);
		}
		else if (respCode == HttpsURLConnection.HTTP_ACCEPTED) {
			currentPrices = parseAsyncResponse(rawResp, httpResp, sopDebug);
		}
		return currentPrices;
	}
	
	TWObject parseSyncResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		
		_CurrentPriceResp cpResp = parseCurrentPriceResp(rawResp);
		if (sopDebug) {
			System.out.println(this.getClass().getName() + ".parseSyncResponse() _CurrentPriceResp: " + cpResp);
		}
		// Prepare TWObject response
		try {
			TWObject twCurrentPriceResp = TWObjectFactory.createObject();
			TWList cpRows = null;
//			TWList results = null;
			TWObject httpResponse = TWObjectFactory.createObject();
			
			int respCode = httpResp.getResponseCode();
			String respMsg = httpResp.getResponseMessage();
			httpResponse.setPropertyValue("responseCode", respCode);
			if (respMsg == null || respMsg.equals("")) {
            	respMsg = rawResp;
            }
			if (respCode != HttpsURLConnection.HTTP_OK) {	                
				respMsg = _API.HTTP_NOT_OK + ": " + respMsg; // Not OK - abort mission
			}
			httpResponse.setPropertyValue("responseMessage", respMsg);
			twCurrentPriceResp.setPropertyValue("httpResponse", httpResponse);

			if ((cpResp != null) && ((cpRows = cpResp.getTwCurrentPriceRows()) != null)) { // && ((results = cpResp.getTwResults()) != null)) {
				if(sopDebug) System.out.println(this.getClass() + ".parseSyncResponse() Returning with [" + cpRows.getArraySize() + "] number of rows.");				
				twCurrentPriceResp.setPropertyValue("currentPriceResp", cpRows);
//				twCurrentPriceResp.setPropertyValue("results", results);
			}
			else {
				// Return empty object but not a null object 
				if(sopDebug) System.out.println(this.getClass() + ".parseSyncResponse() Returning empty response!");
				twCurrentPriceResp.setPropertyValue("currentPriceResp", TWObjectFactory.createList());
//				twCurrentPriceResp.setPropertyValue("results", TWObjectFactory.createList());
			}
			return twCurrentPriceResp;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}

	private _CurrentPriceResp parseCurrentPriceResp(String rawResp) {
		_CurrentPriceResp cpResp = null;
		try {
			ObjectMapper jacksonMapper = new ObjectMapper();

			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			jacksonMapper.setDateFormat(sdf);
			jacksonMapper.configure(
				    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			cpResp = jacksonMapper.readValue(rawResp, _CurrentPriceResp.class);
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
		return cpResp;
	}

	TWObject parseAsyncResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		int respCode = httpResp.getResponseCode();
		// Prepare TWObject for return
		try {
			TWObject twCurrentPriceResp = TWObjectFactory.createObject();
			TWObject httpResponse = TWObjectFactory.createObject();
			
			String respMsg = httpResp.getResponseMessage();
			httpResponse.setPropertyValue("responseCode", respCode);
			if (respMsg == null || respMsg.equals("")) {
            	respMsg = rawResp;
            }
			if (respCode != HttpsURLConnection.HTTP_ACCEPTED && respCode != HttpsURLConnection.HTTP_OK) {                
				respMsg = _API.HTTP_NOT_OK + ": " + respMsg; // Not OK - abort mission
			}
			httpResponse.setPropertyValue("responseMessage", respMsg);
			twCurrentPriceResp.setPropertyValue("httpResponse", httpResponse);

			return twCurrentPriceResp;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}
}
