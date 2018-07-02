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
public class CPResponseHandler {

	// Need to send the complete invoiceLine correction row back - so keep local copy to be used in merging the response
	private _CorrectionRow[] correctionRows;
	private _ConsolidatedCPRow[] currPriceLines;

	public TWList consolidate(String invoiceLines, String currentPrice, boolean sopDebug) throws Exception {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//TODO: Expect API to send ISO 8601 formatted date with Pacific time zone as the base 
			//	Evaluate DateTime offset if json string contains ISO string format with Z meaning UTC timezone 
			this.correctionRows = jacksonMapper.readValue(invoiceLines, _CorrectionRow[].class);
			
			this.currPriceLines = jacksonMapper.readValue(currentPrice, _ConsolidatedCPRow[].class);
			
			//Parse and Merge the response into original correction row invoice lines
			TWList twCorrectionRows = (this.correctionRows.length > 0) ? TWObjectFactory.createList() : null;
			
			for (int i = 0; i < this.correctionRows.length; i++) {
				if (this.correctionRows[i] != null) {
					if (sopDebug) {
						System.out.println("-- correctionRows[" + i + "]: " + this.correctionRows[i].toString());
					}
					currentPriceLoop:
					for(int j = 0; j < this.currPriceLines.length; j++){
						if (sopDebug) {
							System.out.println("---- currPriceLines[" + j + "]: " + this.currPriceLines[j].toString());
						}
						if (this.currPriceLines[j] != null && 
								this.correctionRows[i].getCustomerId().equals(this.currPriceLines[j].getCustomerId()) &&
								_Utility.compareDates(this.correctionRows[i].getPricingDate(), this.currPriceLines[j].getPricingDate()) == 0) {
								
							_CurrentPriceMaterial[] materials = this.currPriceLines[j].getMaterials();
							if (materials != null) {
								for(int k = 0; k < materials.length; k++) {
									if (sopDebug) {
										System.out.println("------ materials[" + k + "]: " + materials[k].toString());
									}
									if (this.correctionRows[i].getMaterialId().equals(materials[k].getMaterialId())) {
										if (sopDebug) {
											System.out.println("-------- materialsIds matched[" + k + "]: " + this.correctionRows[i].getMaterialId());
										}
										merge(i, materials[k], sopDebug);
										break currentPriceLoop; 
									}
								}
							}
						}
					}
					if (sopDebug) {
						System.out.println("Returning twCorrectionRows[" + i + "].isCustInactive: " + this.correctionRows[i].getTwCorrectionRow().getPropertyValue("isCustInactive"));
					}
					twCorrectionRows.addArrayData(this.correctionRows[i].getTwCorrectionRow());
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
	
	void merge(int position, _CurrentPriceMaterial material, boolean sopDebug) {
		if (sopDebug) {
			System.out.println("CP Response Handler merge _CorrectionRow.setCustInactive - isCustInactive: " + this.correctionRows[position].isCustInactive() + ", this.correctionRows[i].getPropertyValue - " + this.correctionRows[position].getTwCorrectionRow().getPropertyValue("isCustInactive"));
		}
		this.correctionRows[position].setCurSellCd(material.getCurSellCd());
		this.correctionRows[position].setCurPrice(material.getCurPrice());
		this.correctionRows[position].setCurActivePrice(material.getCurActivePrice());
		this.correctionRows[position].setCurLead(material.getCurLead());
		this.correctionRows[position].setCurConRef(material.getCurConRef());
		this.correctionRows[position].setCurCbRef(material.getCurCbRef());
		this.correctionRows[position].setCurContCogPer(material.getCurContCogPer());
		this.correctionRows[position].setCurItemDcMarkUpPer(material.getCurItemDcMarkUpPer());
		this.correctionRows[position].setCurItemMkUpPer(material.getCurItemMkUpPer());
		this.correctionRows[position].setCurItemVarPer(material.getCurItemVarPer());
		this.correctionRows[position].setCurWacCogPer(material.getCurWacCogPer());
		this.correctionRows[position].setCurListPrice(material.getCurListPrice());
		this.correctionRows[position].setCurWac(material.getCurWac());
		this.correctionRows[position].setCurBid(material.getCurBid());
		this.correctionRows[position].setCurChargeBack(material.getCurChargeBack());
		this.correctionRows[position].setCurNoChargeBack(material.getCurNoChargeBack());
		//TODO: Check if newAbd needs to be added to materials and add other fields
		this.correctionRows[position].setCurAbd(material.getCurAbd());
		this.correctionRows[position].setCurNetBill(material.getCurNetBill());
		this.correctionRows[position].setCurSsf(material.getCurSsf());
		this.correctionRows[position].setCurSf(material.getCurSf());
		this.correctionRows[position].setCurAwp(material.getCurAwp());
		this.correctionRows[position].setCurContType(material.getCurContType());
		this.correctionRows[position].setCurContrId(material.getCurContrId());
		this.correctionRows[position].setCurProgType(material.getCurProgType());
//			TODO: Check if curProgType and curProgramTypeCd are the same or not; if so why 2 params and if not do we need them in IDT?
//			this.correctionRows[position].setCurProgramTypeCd(material.getCurProgramTypeCd());
		this.correctionRows[position].setCurOverridePrice(material.getCurOverridePrice());
		this.correctionRows[position].setCurRebillCust(material.getCurRebillCust());
		this.correctionRows[position].setCurCustomerName(material.getCurCustomerName());
	}
}
