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
public class HistoricalPrice extends _API {

	public HistoricalPrice() {
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
		_Result asyncResp = null;
		int respCode = httpResp.getResponseCode();
		if (respCode == HttpsURLConnection.HTTP_ACCEPTED || respCode == HttpsURLConnection.HTTP_OK) {
			asyncResp = parseAsyncResp(rawResp);
		}
		// Prepare TWObject for return
		try {
			TWObject twResponse = TWObjectFactory.createObject();
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
			twResponse.setPropertyValue("httpResponse", httpResponse);
			twResponse.setPropertyValue("results", (asyncResp != null ? asyncResp.getTwResult() : null));

			return twResponse;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}

	private _Result parseAsyncResp(String rawResp) {
		_Result result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			ObjectMapper jacksonMapper = new ObjectMapper();
			jacksonMapper.configure(
				    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jacksonMapper.setDateFormat(sdf);
			
			result = jacksonMapper.readValue(rawResp, _Result.class);
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
		return result;
	}
	
}
