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

import teamworks.TWList;
import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class CompositeBillingAttributes extends _API {

	public CompositeBillingAttributes() {
		super();
	}
	
	// Need to send the complete invoiceLine correction row back - so keep local copy to be used in merging the response
	private _CorrectionRow[] invoiceLines;

	@Override
	String prepRequest(String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		return prepRequest(requestJSON, null, reqHeader, sopDebug);
	}
	
	/* (non-Javadoc)
	 * @see com.mck.crrb._API#prepRequest(java.lang.String, teamworks.TWObject, boolean)
	 */
	@Override
	String prepRequest(String requestJSON, String invoiceLines, TWObject reqHeader, boolean sopDebug) throws Exception {
		//Read and Hold original correction rows invoice lines to be overlaid with price simulation values from the response
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//TODO: Expect API to send ISO 8601 formatted date with Pacific time zone as the base 
			//	Evaluate DateTime offset if json string contains ISO string format with Z meaning UTC timezone
			if (invoiceLines != null) {
				this.invoiceLines = jacksonMapper.readValue(invoiceLines, _CorrectionRow[].class);
			}
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
			
			_BillingAttributeRow[] billAttRows = syncResp.getBillingAttributeResp();
			if (this.invoiceLines != null && billAttRows != null) {
				TWList twCorrectionRows = TWObjectFactory.createList();
				for (int i = 0; i < this.invoiceLines.length; i++) {
					for(int j = 0; j < billAttRows.length; j++){
						if (this.invoiceLines[i].getCustomerId().equals(billAttRows[j].getCustomerId())) {
							if (billAttRows[j].getConsolidatedPONumber() != null && !billAttRows[j].getConsolidatedPONumber().equals("")) {
                                this.invoiceLines[i].setConsolidatedPONumber(billAttRows[j].getConsolidatedPONumber());
                            }
							else {
                                this.invoiceLines[i].setConsolidatedPONumber(this.invoiceLines[i].getOldPurchaseOrder());
                            }
							this.invoiceLines[i].setEdiSuppression(billAttRows[j].getEdiSuppression());
							twCorrectionRows.addArrayData(this.invoiceLines[i].getTwCorrectionRow());
							break;
						}
					}
				}
				twBillingAttributeResp.setPropertyValue("correctionRows", twCorrectionRows);
			}

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
