/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

//import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
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
public class Invoice extends _API {
	
	@Override
	String prepRequest(String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		return requestJSON;	//Request is already formatted in this case
	}
	
	@Override
	TWObject parseResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		_InvoiceLookupResp invoices = null;
		if (httpResp.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			invoices = parseInvoiceLookupResp(rawResp);
		}		
		try {
			TWObject twInvoiceLookupResp = TWObjectFactory.createObject();
			TWList corRows = null;
			TWList lookupResults = null;
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
			twInvoiceLookupResp.setPropertyValue("httpResponse", httpResponse);
			
			if ((invoices != null) && ((corRows = invoices.getTwCorrectionRows()) != null) && ((lookupResults = invoices.getTwResults()) != null)) {
				if(sopDebug) System.out.println("Invoice.lookupInvoices() Returning with [" + corRows.getArraySize() + "] number of rows.");				
				twInvoiceLookupResp.setPropertyValue("correctionRows", corRows);
				twInvoiceLookupResp.setPropertyValue("results", lookupResults);
			}
			else {
				// Return empty object but not a null object 
				if(sopDebug) System.out.println("Invoice.lookupInvoices() Returning empty response!");
				twInvoiceLookupResp.setPropertyValue("correctionRows", TWObjectFactory.createList());
				twInvoiceLookupResp.setPropertyValue("results", TWObjectFactory.createList());
			}
			return twInvoiceLookupResp;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}

	private _InvoiceLookupResp parseInvoiceLookupResp(String rawResp) {
		_InvoiceLookupResp invoicesResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			ObjectMapper jacksonMapper = new ObjectMapper();
			jacksonMapper.configure(
				    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			jacksonMapper.setDateFormat(sdf);
			
			invoicesResp = jacksonMapper.readValue(rawResp, _InvoiceLookupResp.class);
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
		return invoicesResp;
	}
}
