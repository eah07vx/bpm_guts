/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	String prepRequest(String requestJSON, String correlationId, boolean sopDebug) throws Exception {
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
		//TODO: Remove sopDebug statements
		if (sopDebug) {
			for (int i = 0; invoiceLines != null && i < invoiceLines.length; i++) {
				if (invoiceLines[i] != null) {
					System.out.println("Invoice.submitPriceCorrection() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}

		return prepSubmitPriceCorrectionCall(invoiceLines, correlationId, "priceCorrectionReq", 1, _API.FETCH_SIZE, sopDebug);
		
	}

	/* (non-Javadoc)
	 * @see com.mck.crrb._API#parseResponse(java.lang.String, boolean)
	 */
	@Override
	TWObject parseResponse(String rawResp, boolean sopDebug) throws Exception {
		_PriceCorrectionResp priceCorrectionResp = parseSubmitPriceCorrectionResp(rawResp);
		
		try {
			TWObject twPriceCorrectionResp = TWObjectFactory.createObject();
			TWList twPriceCorrectionRows = null;
			//TWList lookupResults = null;
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

	static TWList submitPriceCorrectionByJSON(String url, String httpMethod, String sslAlias, String correctionRowsJSON, String correlationId, boolean sopDebug) throws Exception {
		Date d1 = null;
		Date d2 = null;

		if(sopDebug) {
			System.out.println("Invoice.submitPriceCorrection() input parameters:");
			System.out.println("> url: " + url);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> correctionRowsJSON: " + correctionRowsJSON);
			System.out.println("> correlationId: " + correlationId);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\nInvoice.submitPriceCorrection() Start prep of submitPriceCorrection call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
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
					System.out.println("Invoice.submitPriceCorrection() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}

		String requestJSON = prepSubmitPriceCorrectionCall(invoiceLines, correlationId, "priceCorrectionReq", 1, _API.FETCH_SIZE, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End prep of submitPriceCorrection call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("Total prep time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println("Invoice.submitPriceCorrection() requestJSON: " + requestJSON);
		}

		String resp = _API.call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.submitPriceCorrection() response: " + resp);
		_PriceCorrectionResp priceCorrectionResp = parseSubmitPriceCorrectionResp(resp);
		
		/*
		try {
			twPriceCorrectionRows = TWObjectFactory.createList();
			int size = priceCorrectionResp.length;
			for (int i = 0; i < size; i++) {
				twPriceCorrectionRows.addArrayData(priceCorrectionResp[i].getTwCorrectionRow());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		*/
		TWList twPriceCorrectionRows = null;
		if (priceCorrectionResp != null && (twPriceCorrectionRows = priceCorrectionResp.getTwPriceCorrectionRows()) != null) {
			if(sopDebug) System.out.println("Invoice.submitPriceCorrectionByJSON Returning non empty response!");
			return twPriceCorrectionRows;
		}
		else {
			if(sopDebug) System.out.println("Invoice.submitPriceCorrectionByJSON Returning empty response!");
			return TWObjectFactory.createList();	// Return empty list but not a null object
		}
	}
	private static String prepSubmitPriceCorrectionCall(_CorrectionRowISO[] invoiceLines, String correlationId, String containerName, int startIndex, int endIndex, boolean sopDebug) {
		
		String submitPriceCorrectionReqJSON = null; 
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		
		TreeMap<_SubmitPriceReqHeader, TreeMap<String, _CreditRebillMaterial>> submitMap = bucketizeSubmitMap(invoiceLines, correlationId);

		List<Object> pricingRequests = new ArrayList<Object>();
		int i = 0;
		for (Entry<_SubmitPriceReqHeader, TreeMap<String, _CreditRebillMaterial>> entry : submitMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			_SubmitPriceReqHeader priceCorrectionRowHeader = entry.getKey();
			if (priceCorrectionRowHeader != null) {
				pricingReq.put("index", i++);
				pricingReq.put("customerId", priceCorrectionRowHeader.getCustomerId());
				pricingReq.put("correlationId", priceCorrectionRowHeader.getCorrelationId());
				pricingReq.put("salesOrg", priceCorrectionRowHeader.getSalesOrg());
				pricingReq.put("billType", priceCorrectionRowHeader.getBillType());
				
				if (entry != null && entry.getValue() != null && entry.getValue().values() != null) {
					pricingReq.put("materials", entry.getValue().values());		// value in the priceMap entry has materials
				}
				pricingRequests.add(pricingReq);
			}
		}
		priceReqMap.put(containerName, pricingRequests.toArray());
		priceReqMap.put("startIndex", startIndex);
		priceReqMap.put("endIndex", endIndex);
		
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.setDateFormat(new SimpleDateFormat("yyyyMMdd"));
		jacksonMapper.setSerializationInclusion(Include.NON_EMPTY);
		
		try {
			submitPriceCorrectionReqJSON = jacksonMapper.writeValueAsString(priceReqMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			submitPriceCorrectionReqJSON = "failed";
		}
		return submitPriceCorrectionReqJSON;
	}
	static TreeMap<_SubmitPriceReqHeader, TreeMap<String, _CreditRebillMaterial>> bucketizeSubmitMap(_CorrectionRowISO[] invoiceLines, String correlationId) {
		TreeMap<_SubmitPriceReqHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = new TreeMap<_SubmitPriceReqHeader, TreeMap<String, _CreditRebillMaterial>>();
		
		for (int i = 0; i < invoiceLines.length; i++) {
			//Hydrate key as SubmitPriceReqHeader
			_SubmitPriceReqHeader headerKey = hydrateSubmitPriceReqHeader(invoiceLines[i], correlationId, i); 
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() invoiceLines[" + i + "].headerKey - customerId: " + headerKey.getCustomerId() + ", billType: " + headerKey.getBillType());
 			
			//Hydrate priceCorrectionMaterial
			_CreditRebillMaterial priceCorrectionMaterial = hydratePriceCorrectionMaterial(invoiceLines[i]);
			String materialKey = priceCorrectionMaterial.getRecordKey();
			
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() materialKey[" + i + "]: " + materialKey + ", materialId: " + priceCorrectionMaterial.getMaterialId());
			
			TreeMap<String, _CreditRebillMaterial> materialList = priceMap.get(headerKey);
			//TODO: Remove SOP debug
			System.out.print("materialList[" + i + "] before: " + (materialList != null ? materialList.get(materialKey) : materialList));
			
			if (materialList == null) { // Key not in TreeMap - new key found
				materialList = new TreeMap<String, _CreditRebillMaterial>();
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
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
	private static _SubmitPriceReqHeader hydrateSubmitPriceReqHeader(_CorrectionRowISO invoiceLine, String correlationId, int index) {
		_SubmitPriceReqHeader headerKey = new _SubmitPriceReqHeader();
		headerKey.setIndex(index);
		headerKey.setCustomerId(invoiceLine.getCustomerId());
		headerKey.setCorrelationId(correlationId);
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		return headerKey;
	}
	private static _CreditRebillMaterial hydratePriceCorrectionMaterial(_CorrectionRowISO invoiceLine) {
		_CreditRebillMaterial priceCorrectionMaterial = new _CreditRebillMaterial();
		priceCorrectionMaterial.setRecordKey(invoiceLine.getInvoiceId() + "-" + invoiceLine.getInvoiceLineItemNum());
		priceCorrectionMaterial.setMaterialId(invoiceLine.getMaterialId());
		
		priceCorrectionMaterial.setRebillQty(invoiceLine.getRebillQty());
		priceCorrectionMaterial.setUom(invoiceLine.getUom());
		priceCorrectionMaterial.setDc(invoiceLine.getDc());
		//Original invoice line pricing related fields
		priceCorrectionMaterial.setOldActivePrice(invoiceLine.getOldActivePrice());
		priceCorrectionMaterial.setOldAwp(invoiceLine.getOldAwp());
		priceCorrectionMaterial.setOldAbd(invoiceLine.getOldAbd());
		priceCorrectionMaterial.setOldBid(invoiceLine.getOldBid());
		priceCorrectionMaterial.setOldCbRef(invoiceLine.getOldCbRef());
		priceCorrectionMaterial.setOldChargeBack(invoiceLine.getOldChargeBack());
		priceCorrectionMaterial.setOldConRef(invoiceLine.getOldConRef());
		priceCorrectionMaterial.setOldContCogPer(invoiceLine.getOldContCogPer());
		priceCorrectionMaterial.setOldWacCogPer(invoiceLine.getOldWacCogPer());
		priceCorrectionMaterial.setOldItemMkUpPer(invoiceLine.getOldItemMkUpPer());
		priceCorrectionMaterial.setOldItemVarPer(invoiceLine.getOldItemVarPer());
		priceCorrectionMaterial.setOldLead(invoiceLine.getOldLead());
		priceCorrectionMaterial.setOldListPrice(invoiceLine.getOldListPrice());
		priceCorrectionMaterial.setOldNoChargeBack(invoiceLine.getOldNoChargeBack());
		priceCorrectionMaterial.setOldOverridePrice(invoiceLine.getOldOverridePrice());
		priceCorrectionMaterial.setOldSellCd(invoiceLine.getOldSellCd());
		priceCorrectionMaterial.setOldSellPrice(invoiceLine.getOldPrice()); // oldSellPrice === oldPrice
		priceCorrectionMaterial.setOldSf(invoiceLine.getOldSf());
		priceCorrectionMaterial.setOldSsf(invoiceLine.getOldSsf());
		priceCorrectionMaterial.setOldWac(invoiceLine.getOldWac());
		priceCorrectionMaterial.setOldNetBill(invoiceLine.getOldNetBill());
		priceCorrectionMaterial.setOldProgType(invoiceLine.getOldProgType());
		priceCorrectionMaterial.setOldContrId(invoiceLine.getOldContrId());
		priceCorrectionMaterial.setOldContType(invoiceLine.getOldContType());
		//New price related fields
		priceCorrectionMaterial.setNewActivePrice(invoiceLine.getNewActivePrice());
		priceCorrectionMaterial.setNewAwp(invoiceLine.getNewAwp());
		priceCorrectionMaterial.setNewAbd(invoiceLine.getNewAbd());
		priceCorrectionMaterial.setNewBid(invoiceLine.getNewBid());
		priceCorrectionMaterial.setNewCbRef(invoiceLine.getNewCbRef());
		priceCorrectionMaterial.setNewChargeBack(invoiceLine.getNewChargeBack());
		priceCorrectionMaterial.setNewConRef(invoiceLine.getNewConRef());
		priceCorrectionMaterial.setNewContCogPer(invoiceLine.getNewContCogPer());
		priceCorrectionMaterial.setNewWacCogPer(invoiceLine.getNewWacCogPer());
		priceCorrectionMaterial.setNewItemMkUpPer(invoiceLine.getNewItemMkUpPer());
		priceCorrectionMaterial.setNewItemVarPer(invoiceLine.getNewItemVarPer());
		priceCorrectionMaterial.setNewLead(invoiceLine.getNewLead());
		priceCorrectionMaterial.setNewListPrice(invoiceLine.getNewListPrice());
		priceCorrectionMaterial.setNewNoChargeBack(invoiceLine.getNewNoChargeBack());
		priceCorrectionMaterial.setNewOverridePrice(invoiceLine.getNewOverridePrice());
		priceCorrectionMaterial.setNewSellCd(invoiceLine.getNewSellCd());
		priceCorrectionMaterial.setNewSellPrice(invoiceLine.getNewPrice()); // newSellPrice === newPrice
		priceCorrectionMaterial.setNewSf(invoiceLine.getNewSf());
		priceCorrectionMaterial.setNewSsf(invoiceLine.getNewSsf());
		priceCorrectionMaterial.setNewWac(invoiceLine.getNewWac());
		priceCorrectionMaterial.setNewNetBill(invoiceLine.getNewNetBill());
		priceCorrectionMaterial.setNewProgType(invoiceLine.getNewProgType());
		priceCorrectionMaterial.setNewContrId(invoiceLine.getNewContrId());
		priceCorrectionMaterial.setNewContType(invoiceLine.getNewContType());
		
		priceCorrectionMaterial.setManufacturer(invoiceLine.getManufacturer());
		priceCorrectionMaterial.setDistrChan(invoiceLine.getDistrChan());
		priceCorrectionMaterial.setDivision(invoiceLine.getDivision());
		// PO Correction
		priceCorrectionMaterial.setOldPurchaseOrder(invoiceLine.getOldPurchaseOrder());
		priceCorrectionMaterial.setNewPurchaseOrder(invoiceLine.getNewPurchaseOrder());
		// Account Switch - customer Ids
		priceCorrectionMaterial.setOldCustomer(invoiceLine.getCustomerId());
		priceCorrectionMaterial.setNewCustomer(invoiceLine.getNewRebillCust());
		//Invoice line identifiers
		priceCorrectionMaterial.setInvoiceId(invoiceLine.getInvoiceId()); 
		priceCorrectionMaterial.setInvoiceLineItemNum(invoiceLine.getInvoiceLineItemNum());  
		priceCorrectionMaterial.setOrigInvoiceId(invoiceLine.getOrigInvoiceId());
		priceCorrectionMaterial.setOrigInvoiceLineItemNum(invoiceLine.getOrigInvoiceLineItemNum());  
		priceCorrectionMaterial.setPrcGroup5(invoiceLine.getPrcGroup5());  

		return priceCorrectionMaterial;
	}
	
}
