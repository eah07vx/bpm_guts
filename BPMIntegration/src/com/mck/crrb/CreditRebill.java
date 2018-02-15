/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class CreditRebill extends _API {
	
	/* (non-Javadoc)
	 * @see com.mck.crrb._API#prepRequest(java.lang.String, boolean)
	 */
	@Override
	String prepRequest(String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_CorrectionRowISO[] invoiceLines = null;
		try {
			invoiceLines = jacksonMapper.readValue(requestJSON, _CorrectionRowISO[].class);
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
		//TODO: Remove sopDebug statement
		if (sopDebug) {
			for (int i = 0; invoiceLines != null && i < invoiceLines.length; i++) {
				if (invoiceLines[i] != null) {
					System.out.println("CreditRebill.prepRequest() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}
		if(invoiceLines == null) {
			return "failed";
		}
		return prepSubmitPriceCorrectionCall(invoiceLines, reqHeader, "priceCorrectionReq", 0, _API.FETCH_SIZE, sopDebug);	
	}

	/* (non-Javadoc)
	 * @see com.mck.crrb._API#parseResponse(java.lang.String, boolean)
	 */
	@Override
	TWObject parseResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		_PriceCorrectionResp priceCorrectionResp = null;
		if (httpResp.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			priceCorrectionResp = parseSubmitPriceCorrectionResp(rawResp);
		}
		try {
			TWObject twPriceCorrectionResp = TWObjectFactory.createObject();
			TWList twPriceCorrectionRows = null;
			//TWList lookupResults = null;
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
			twPriceCorrectionResp.setPropertyValue("httpResponse", httpResponse);
			if (priceCorrectionResp != null && (twPriceCorrectionRows = priceCorrectionResp.getTwPriceCorrectionRows()) != null) {
				if(sopDebug) System.out.println("CreditRebill.parseResponse() Returning non empty response!");
				twPriceCorrectionResp.setPropertyValue("priceCorrectionRows", twPriceCorrectionRows);
				//twPriceCorrectionResp.setPropertyValue("results", lookupResults);
			}
			else {
				// Return empty object but not a null object
				if(sopDebug) System.out.println("CreditRebill.parseResponse() Returning empty response!");
				twPriceCorrectionResp.setPropertyValue("priceCorrectionRows", TWObjectFactory.createList());
				//twPriceCorrectionResp.setPropertyValue("results", TWObjectFactory.createList());
			}
			return twPriceCorrectionResp;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}

	private static String prepSubmitPriceCorrectionCall(_CorrectionRowISO[] invoiceLines, TWObject reqHeader, String containerName, int startIndex, int endIndex, boolean sopDebug) {
		
		String submitPriceCorrectionReqJSON = null; 
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
		
		TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> submitMap = bucketizeSubmitMap(invoiceLines, reqHeader);

		List<Object> pricingRequests = new ArrayList<Object>();
		int i = 0;
		for (Entry<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> entry : submitMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			_APIReqHeader priceCorrectionRowHeader = entry.getKey();
			if (priceCorrectionRowHeader != null) {
				pricingReq.put("index", i++);
				pricingReq.put("correlationId", priceCorrectionRowHeader.getCorrelationId());
				pricingReq.put("customerId", priceCorrectionRowHeader.getCustomerId());
				pricingReq.put("pricingDate", sdf.format(priceCorrectionRowHeader.getPricingDate()));
				pricingReq.put("salesOrg", priceCorrectionRowHeader.getSalesOrg());
				pricingReq.put("billType", priceCorrectionRowHeader.getBillType());
				pricingReq.put("idtCaseType", priceCorrectionRowHeader.getIdtCaseType());
				pricingReq.put("idtCaseNumber", priceCorrectionRowHeader.getIdtCaseNumber());
				pricingReq.put("reasonCode", priceCorrectionRowHeader.getReasonCode());
				pricingReq.put("submittedBy", priceCorrectionRowHeader.getSubmittedBy());
				pricingReq.put("ediSuppression", priceCorrectionRowHeader.getEdiSuppression());
				pricingReq.put("consolidatedPONumber", priceCorrectionRowHeader.getConsolidatedPONumber());
				
				if (entry != null && entry.getValue() != null && entry.getValue().values() != null) {
					pricingReq.put("materials", entry.getValue().values());		// value in the priceMap entry has materials
				}
				pricingRequests.add(pricingReq);
			}
		}
		priceReqMap.put(containerName, pricingRequests.toArray());
		priceReqMap.put("startIndex", startIndex);
		priceReqMap.put("endIndex", endIndex-1);
		
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.setDateFormat(new SimpleDateFormat(_API.API_DATE_FORMAT));
		jacksonMapper.setSerializationInclusion(Include.NON_NULL);
		
		try {
			submitPriceCorrectionReqJSON = jacksonMapper.writeValueAsString(priceReqMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			submitPriceCorrectionReqJSON = "failed";
		}
		return submitPriceCorrectionReqJSON;
	}
	static TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> bucketizeSubmitMap(_CorrectionRowISO[] invoiceLines, TWObject reqHeader) {
		TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> priceMap = new TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>>();
		
		for (int i = 0; i < invoiceLines.length; i++) {
			//Hydrate key as SubmitPriceReqHeader
			_APIReqHeader headerKey = hydrateSubmitPriceReqHeader(invoiceLines[i], reqHeader, i); 
			//TODO: Remove SOP debug statement below
			System.out.println("CreditRebill.bucketizePriceMap() invoiceLines[" + i + "].headerKey - customerId: " + headerKey.getCustomerId() + ", billType: " + headerKey.getBillType());
 			
			//Hydrate priceCorrectionMaterial
			_PriceCorrectionMaterial priceCorrectionMaterial = hydratePriceCorrectionMaterial(invoiceLines[i]);
			String materialKey = priceCorrectionMaterial.getRecordKey();
			
			//TODO: Remove SOP debug statement below
			System.out.println("CreditRebill.bucketizePriceMap() materialKey[" + i + "]: " + materialKey + ", materialId: " + priceCorrectionMaterial.getMaterialId());
			
			TreeMap<String, _PriceCorrectionMaterial> materialList = priceMap.get(headerKey);
			//TODO: Remove SOP debug
			System.out.print("materialList[" + i + "] before: " + (materialList != null ? materialList.get(materialKey) : materialList));
			
			if (materialList == null) { // Key not in TreeMap - new key found
				materialList = new TreeMap<String, _PriceCorrectionMaterial>();
				materialList.put(materialKey, priceCorrectionMaterial);	// First material in list
				priceMap.put(headerKey, materialList);
			}
			else { 	// Key already exists in TreeMap
				materialList.put(materialKey, priceCorrectionMaterial);
			}
			//TODO: Remove SOP debug
			System.out.println("  after: " + materialList.get(materialKey) + "\n");
		}
		return priceMap;
	}
	
	private static _PriceCorrectionResp parseSubmitPriceCorrectionResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_PriceCorrectionResp submitPriceResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			jacksonMapper.setDateFormat(sdf);
			
			submitPriceResp = jacksonMapper.readValue(resp, _PriceCorrectionResp.class);

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
		return submitPriceResp;
	}
	
	private static _APIReqHeader hydrateSubmitPriceReqHeader(_CorrectionRowISO invoiceLine, TWObject reqHeader, int index) {
		_APIReqHeader headerKey = new _APIReqHeader(reqHeader);
		headerKey.setIndex(index);
		headerKey.setCustomerId(invoiceLine.getCustomerId());
		headerKey.setPricingDate(invoiceLine.getPricingDate());
		headerKey.setOrderType(invoiceLine.getOrderType());
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		headerKey.setEdiSuppression(invoiceLine.getEdiSuppression());		
		//TODO: Remove logic to default consolidatedPONumber to poNumber
		// Use consolidated PO number if not null otherwise default to PO Number from invoice 
		//headerKey.setConsolidatedPONumber(invoiceLine.getConsolidatedPONumber() != null ? invoiceLine.getConsolidatedPONumber() : invoiceLine.getPoNumber());
		headerKey.setConsolidatedPONumber(invoiceLine.getConsolidatedPONumber());
		/*
		//Currently EDI Suppression flag and Consolidated PO Number do NOT come through reqHeader - the are in correction row a.k.a. invoice line
		if ((String)reqHeader.getPropertyValue("ediSuppression") == null) {
			headerKey.setEdiSuppression(invoiceLine.getEdiSuppression());  
		}
		if ((String)reqHeader.getPropertyValue("consolidatedPONumber") == null) {
			headerKey.setConsolidatedPONumber(invoiceLine.getPoNumber());
		}
		*/
		return headerKey;
	}
	
	private static _PriceCorrectionMaterial hydratePriceCorrectionMaterial(_CorrectionRowISO invoiceLine) {
		_PriceCorrectionMaterial priceCorrectionMaterial = new _PriceCorrectionMaterial();
		priceCorrectionMaterial.setRecordKey(invoiceLine.getInvoiceId() + "-" + invoiceLine.getInvoiceLineItemNum());
		priceCorrectionMaterial.setMaterialId(invoiceLine.getMaterialId());
		priceCorrectionMaterial.setCreatedOn(invoiceLine.getCreatedOn());
		// Quantity fields
		priceCorrectionMaterial.setRebillQty(invoiceLine.getRebillQty());
		priceCorrectionMaterial.setUom(invoiceLine.getUom());
		priceCorrectionMaterial.setDc(invoiceLine.getDc());
		// Original invoice line pricing related fields
		priceCorrectionMaterial.setOldWac(invoiceLine.getOldWac());
		priceCorrectionMaterial.setOldBid(invoiceLine.getOldBid());
		priceCorrectionMaterial.setOldLead(invoiceLine.getOldLead());
		priceCorrectionMaterial.setOldConRef(invoiceLine.getOldConRef());
		priceCorrectionMaterial.setOldContCogPer(invoiceLine.getOldContCogPer());
		priceCorrectionMaterial.setOldItemVarPer(invoiceLine.getOldItemVarPer());
		priceCorrectionMaterial.setOldWacCogPer(invoiceLine.getOldWacCogPer());
		priceCorrectionMaterial.setOldItemMkUpPer(invoiceLine.getOldItemMkUpPer());
		priceCorrectionMaterial.setOldAwp(invoiceLine.getOldAwp());
		priceCorrectionMaterial.setOldNoChargeBack(invoiceLine.getOldNoChargeBack());
		priceCorrectionMaterial.setOldOverridePrice(invoiceLine.getOldOverridePrice());
		priceCorrectionMaterial.setOldSellPrice(invoiceLine.getOldPrice()); // oldSellPrice === oldPrice
		priceCorrectionMaterial.setOldCbRef(invoiceLine.getOldCbRef());
		priceCorrectionMaterial.setOldSellCd(invoiceLine.getOldSellCd());
		priceCorrectionMaterial.setOldActivePrice(invoiceLine.getOldActivePrice());
		priceCorrectionMaterial.setOldChargeBack(invoiceLine.getOldChargeBack());
		priceCorrectionMaterial.setOldSsf(invoiceLine.getOldSsf());
		priceCorrectionMaterial.setOldSf(invoiceLine.getOldSf());
		priceCorrectionMaterial.setOldListPrice(invoiceLine.getOldListPrice());
		priceCorrectionMaterial.setOldAbd(invoiceLine.getOldAbd());
		priceCorrectionMaterial.setOldNetBill(invoiceLine.getOldNetBill());
		priceCorrectionMaterial.setOldProgType(invoiceLine.getOldProgType());
		priceCorrectionMaterial.setOldContrId(invoiceLine.getOldContrId());
		priceCorrectionMaterial.setOldContType(invoiceLine.getOldContType());
		// New price related fields
		priceCorrectionMaterial.setNewWac(invoiceLine.getNewWac());
		priceCorrectionMaterial.setNewBid(invoiceLine.getNewBid());
		priceCorrectionMaterial.setNewLead(invoiceLine.getNewLead());
		priceCorrectionMaterial.setNewConRef(invoiceLine.getNewConRef());
		priceCorrectionMaterial.setNewContCogPer(invoiceLine.getNewContCogPer());
		priceCorrectionMaterial.setNewItemVarPer(invoiceLine.getNewItemVarPer());
		priceCorrectionMaterial.setNewWacCogPer(invoiceLine.getNewWacCogPer());
		priceCorrectionMaterial.setNewItemMkUpPer(invoiceLine.getNewItemMkUpPer());
		priceCorrectionMaterial.setNewAwp(invoiceLine.getNewAwp());
		priceCorrectionMaterial.setNewNoChargeBack(invoiceLine.getNewNoChargeBack());
		priceCorrectionMaterial.setNewOverridePrice(invoiceLine.getNewOverridePrice());
		priceCorrectionMaterial.setNewSellPrice(invoiceLine.getNewPrice()); // newSellPrice === newPrice
		priceCorrectionMaterial.setNewCbRef(invoiceLine.getNewCbRef());
		priceCorrectionMaterial.setNewSellCd(invoiceLine.getNewSellCd());
		priceCorrectionMaterial.setNewActivePrice(invoiceLine.getNewActivePrice());
		priceCorrectionMaterial.setNewChargeBack(invoiceLine.getNewChargeBack());
		priceCorrectionMaterial.setNewSsf(invoiceLine.getNewSsf());
		priceCorrectionMaterial.setNewSf(invoiceLine.getNewSf());
		priceCorrectionMaterial.setNewListPrice(invoiceLine.getNewListPrice());
		priceCorrectionMaterial.setNewAbd(invoiceLine.getNewAbd());
		priceCorrectionMaterial.setNewNetBill(invoiceLine.getNewNetBill());
		priceCorrectionMaterial.setNewProgType(invoiceLine.getNewProgType());
		priceCorrectionMaterial.setNewContrId(invoiceLine.getNewContrId());
		priceCorrectionMaterial.setNewContType(invoiceLine.getNewContType());
		// Additional pass-through fields
		priceCorrectionMaterial.setManufacturer(invoiceLine.getManufacturer());
		priceCorrectionMaterial.setDistrChan(invoiceLine.getDistrChan());
		priceCorrectionMaterial.setDivision(invoiceLine.getDivision());
		// Original poNumber
		priceCorrectionMaterial.setPoNumber(invoiceLine.getPoNumber());
		// PO Correction
		priceCorrectionMaterial.setOldPurchaseOrder(invoiceLine.getOldPurchaseOrder());
		priceCorrectionMaterial.setNewPurchaseOrder(invoiceLine.getNewPurchaseOrder());
		// Account Switch - customer Ids
		priceCorrectionMaterial.setOldCustomer(invoiceLine.getCustomerId());
		priceCorrectionMaterial.setNewCustomer(invoiceLine.getNewRebillCust());
		// Invoice line identifiers
		priceCorrectionMaterial.setInvoiceId(invoiceLine.getInvoiceId());
		priceCorrectionMaterial.setInvoiceLineItemNum(invoiceLine.getInvoiceLineItemNum());
		priceCorrectionMaterial.setOrigInvoiceId(invoiceLine.getOrigInvoiceId());
		priceCorrectionMaterial.setOrigInvoiceLineItemNum(invoiceLine.getOrigInvoiceLineItemNum());
		priceCorrectionMaterial.setPrcGroup5(invoiceLine.getPrcGroup5());
		// 	Item DC Markup percentages
		priceCorrectionMaterial.setOldItemDcMarkUpPer(invoiceLine.getOldItemDcMarkUpPer());
		priceCorrectionMaterial.setCurItemDcMarkUpPer(invoiceLine.getCurItemDcMarkUpPer());
		priceCorrectionMaterial.setNewItemDcMarkUpPer(invoiceLine.getNewItemDcMarkUpPer());

		return priceCorrectionMaterial;
	}
	/*
	static TWList submitPriceCorrectionByJSON(String url, String httpMethod, String sslAlias, String correctionRowsJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		Date d1 = null;
		Date d2 = null;

		if(sopDebug) {
			System.out.println("CreditRebill.submitPriceCorrection() input parameters:");
			System.out.println("> url: " + url);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> correctionRowsJSON: " + correctionRowsJSON);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\nCreditRebill.submitPriceCorrection() Start prep of submitPriceCorrection call: " + new SimpleDateFormat(_API.LONG_DASHED_DATE_FORMAT).format(d1));
		}		

		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_CorrectionRowISO[] invoiceLines = null;
		try {
			invoiceLines = jacksonMapper.readValue(correctionRowsJSON, _CorrectionRowISO[].class);
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
		//TODO: Remove sopDebug statements
		if (sopDebug) {
			for (int i = 0; invoiceLines != null && i < invoiceLines.length; i++) {
				if (invoiceLines[i] != null) {
					System.out.println("CreditRebill.submitPriceCorrection() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}

		String requestJSON = prepSubmitPriceCorrectionCall(invoiceLines, reqHeader, "priceCorrectionReq", 1, _API.FETCH_SIZE, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End prep of submitPriceCorrection call: " + new SimpleDateFormat(_API.LONG_DASHED_DATE_FORMAT).format(d2));
			System.out.println("Total prep time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println("CreditRebill.submitPriceCorrection() requestJSON: " + requestJSON);
		}

		String resp = _API.call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("CreditRebill.submitPriceCorrection() response: " + resp);
		_PriceCorrectionResp priceCorrectionResp = parseSubmitPriceCorrectionResp(resp);
		
		
//		try {
//			twPriceCorrectionRows = TWObjectFactory.createList();
//			int size = priceCorrectionResp.length;
//			for (int i = 0; i < size; i++) {
//				twPriceCorrectionRows.addArrayData(priceCorrectionResp[i].getTwCorrectionRow());
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		
		TWList twPriceCorrectionRows = null;
		if (priceCorrectionResp != null && (twPriceCorrectionRows = priceCorrectionResp.getTwPriceCorrectionRows()) != null) {
			if(sopDebug) System.out.println("CreditRebill.submitPriceCorrectionByJSON Returning non empty response!");
			return twPriceCorrectionRows;
		}
		else {
			if(sopDebug) System.out.println("CreditRebill.submitPriceCorrectionByJSON Returning empty response!");
			return TWObjectFactory.createList();	// Return empty list but not a null object
		}
	}
	*/	
}