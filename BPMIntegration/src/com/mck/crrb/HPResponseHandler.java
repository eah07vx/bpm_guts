/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;

//import javax.net.ssl.HttpsURLConnection;

//import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import teamworks.TWList;
//import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class HPResponseHandler {

	// Need to send the complete invoiceLine correction row back - so keep local copy to be used in merging the response
	private _CorrectionRow[] correctionRows;
	private _ConsolidatedHPRow[] histPriceLines;

	public TWList consolidate(String invoiceLines, String historicalPrice, boolean sopDebug) throws Exception {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//TODO: Expect API to send ISO 8601 formatted date with Pacific time zone as the base 
			//	Evaluate DateTime offset if json string contains ISO string format with Z meaning UTC timezone 
			this.correctionRows = jacksonMapper.readValue(invoiceLines, _CorrectionRow[].class);
			
			this.histPriceLines = jacksonMapper.readValue(historicalPrice, _ConsolidatedHPRow[].class);
			
			//Parse and Merge the response into original correction row invoice lines
			TWList twCorrectionRows = (this.correctionRows.length > 0) ? TWObjectFactory.createList() : null;
			
			for (int i = 0; i < this.correctionRows.length; i++) {
				if (this.correctionRows[i] != null) {
//					if (sopDebug) {
//						System.out.println("-- correctionRows[" + i + "]: " + this.correctionRows[i].toString());
//					}
					for(int j = 0; j < this.histPriceLines.length; j++){
//						if (sopDebug) {
//							System.out.println("---- currPriceLines[" + j + "]: " + this.currPriceLines[j].toString());
//						}
						if (this.histPriceLines[j] != null && 
								this.correctionRows[i].getCustomerId().equals(this.histPriceLines[j].getCustomerId()) &&  
								this.correctionRows[i].getPricingDate().compareTo(this.histPriceLines[j].getPricingDate()) == 0 && 
								this.correctionRows[i].getSalesOrg().equals(this.histPriceLines[j].getSalesOrg()) && 
								this.correctionRows[i].getOrderType().equals(this.histPriceLines[j].getOrderType()) && 
								this.correctionRows[i].getDistrChan().equals(this.histPriceLines[j].getDistrChan()) &&
								this.correctionRows[i].getMaterialId().equals(this.histPriceLines[j].getMaterialId())) {

							merge(i, this.histPriceLines[j]);
							twCorrectionRows.addArrayData(this.correctionRows[i].getTwCorrectionRow());
							break; 
						}
					}
				}
			}
			return twCorrectionRows;
				
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
		return null;
	}    
	
	void merge(int position, _ConsolidatedHPRow histPriceLine) {
		this.correctionRows[position].setNewLead(histPriceLine.getHistLead());
		this.correctionRows[position].setNewWac(histPriceLine.getHistWac());
		this.correctionRows[position].setNewPrice(histPriceLine.getHistPrice());
		this.correctionRows[position].setNewItemVarPer(histPriceLine.getHistItemVarPer()); 
		this.correctionRows[position].setNewContCogPer(histPriceLine.getHistContCogPer()); 
		this.correctionRows[position].setNewConRef(histPriceLine.getHistConRef());
		this.correctionRows[position].setNewBid(histPriceLine.getHistBid());
		this.correctionRows[position].setNewNoChargeBack(histPriceLine.getHistNoChargeBack());
		this.correctionRows[position].setNewChargeBack(histPriceLine.getHistChargeBack());
		
		// This value will be entered by application users in case of account switch correction requests - otherwise default to blank
		if (this.correctionRows[position].getNewRebillCust() == null) {
			this.correctionRows[position].setNewRebillCust(""); 
		}
		// Set default for value not returned by Historical Price API
		this.correctionRows[position].setNewAbd(0.0);
	}
}
