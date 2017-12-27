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
	TWObject parseResponse(String rawResp, boolean sopDebug) throws Exception {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_InvoiceLookupResp invoices = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			jacksonMapper.setDateFormat(sdf);
			
			invoices = jacksonMapper.readValue(rawResp, _InvoiceLookupResp.class);
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
		try {
			TWObject twInvoiceLookupResp = TWObjectFactory.createObject();
			TWList corRows = null;
			TWList lookupResults = null;
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
	/*
	@Deprecated
	static TWObject lookupInvoices(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception  {
		String resp = call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.lookupInvoices response: " + resp);
		_InvoiceLookupResp invoices = parseInvoiceLookupResp(resp);
		
		TWObject invoiceLookupResp = TWObjectFactory.createObject();
		TWList corRows = null;
		TWList lookupResults = null;
		if (invoices != null && (corRows = invoices.getTwCorrectionRows()) != null && (lookupResults = invoices.getTwResults()) != null) {
			if(sopDebug) System.out.println("Invoice.lookupInvoices() Returning non empty response!");
			invoiceLookupResp.setPropertyValue("correctionRows", corRows);
			invoiceLookupResp.setPropertyValue("results", lookupResults);
		}
		else {
			// Return empty object but not a null object
			if(sopDebug) System.out.println("Invoice.lookupInvoices() Returning empty response!");
			invoiceLookupResp.setPropertyValue("correctionRows", TWObjectFactory.createList());
			invoiceLookupResp.setPropertyValue("results", TWObjectFactory.createList());
		}
		return invoiceLookupResp;
	}
	*/
	/*
	 * @deprecated
	 */
	/*
	public static TWList simulatePrice(String url, String httpMethod, String sslAlias, TWList correctionRows, boolean sopDebug) {
		Date d1 = null;

		if(sopDebug) {
			System.out.println("Invoice.simulatePrice() input parameters:");
			System.out.println("> url: " + url);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> correctionRows: " + correctionRows);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\nInvoice.simulatePrice() Start prep of SimulatePrice call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		}		

		_CorrectionRowISO[] invoiceLines = new _CorrectionRowISO[correctionRows.getArraySize()];
		for (int i = 0; i < correctionRows.getArraySize(); i++) {
			if(sopDebug) System.out.println("Invoice.simulatePrice() correctionRows.getArrayData(i).getClass().getName(): " + correctionRows.getArrayData(i).getClass().getName());
			invoiceLines[i] = new _CorrectionRowISO(correctionRows.getArrayData(i));
			if (invoiceLines[i] != null) {
				System.out.println("Invoice.simulatePrice() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
			}
		}
		String requestJSON = prepSimulatePriceCall(invoiceLines, "priceSimulationReq", 1, _API.FETCH_SIZE, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePrice requestJSON" + requestJSON);
		String resp = _API.call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePrice response: " + resp);
		mergeSimulatePriceValues(invoiceLines, parseSimulatePriceResp(resp));
		TWList twCorrectionRows = null;
		try {
			twCorrectionRows = TWObjectFactory.createList();
			int size = invoiceLines.length;
			for (int i = 0; i < size; i++) {
				twCorrectionRows.addArrayData(invoiceLines[i].getTwCorrectionRow());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		return twCorrectionRows;
	}
	*/
	/*
	public static TWList simulatePriceByJSON(String url, String httpMethod, String sslAlias, String correctionRowsJSON, boolean sopDebug) {
		Date d1 = null;
		Date d2 = null;

		if(sopDebug) {
			System.out.println("Invoice.simulatePriceJSON() input parameters:");
			System.out.println("> url: " + url);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> correctionRowsJSON: " + correctionRowsJSON);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\nInvoice.simulatePriceJSON() Start prep of SimulatePrice call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		}
		
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_CorrectionRowISO[] invoiceLines = null;
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//OffsetDateTime
			
			invoiceLines = jacksonMapper.readValue(correctionRowsJSON, _CorrectionRowISO[].class);
			//System.out.println(respInFile);

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
		if (sopDebug) {
			for (int i = 0; invoiceLines != null && i < invoiceLines.length; i++) {
				if (invoiceLines[i] != null) {
					System.out.println("Invoice.simulatePriceJSON() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}
		String requestJSON = prepSimulatePriceCall(invoiceLines, "priceSimulationReq", 1, _API.FETCH_SIZE, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End prep of SimulatePrice call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("Total prep time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println("Invoice.simulatePriceJSON() requestJSON: " + requestJSON);
		}

		String resp = _API.call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePriceJSON() response: " + resp);
		mergeSimulatePriceValues(invoiceLines, parseSimulatePriceResp(resp));
		TWList twCorrectionRows = null;
		try {
			twCorrectionRows = TWObjectFactory.createList();
			int size = invoiceLines.length;
			for (int i = 0; i < size; i++) {
				twCorrectionRows.addArrayData(invoiceLines[i].getTwCorrectionRow());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		return twCorrectionRows;
	}	
	*/
	/*
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
		//TODO: Remove sopDebug statement
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
		
//		
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
			if(sopDebug) System.out.println("Invoice.submitPriceCorrectionByJSON Returning non empty response!");
			return twPriceCorrectionRows;
		}
		else {
			if(sopDebug) System.out.println("Invoice.submitPriceCorrectionByJSON Returning empty response!");
			return TWObjectFactory.createList();	// Return empty list but not a null object
		}
	}
	*/
	/*
	private static String prepSimulatePriceCall(_CorrectionRowISO[] invoiceLines, String containerName, int startIndex, int endIndex, boolean sopDebug) {
		
		//String tst = "{	\"priceSimulationReq\":[    {    	\"index\": 0,        \"customerId\":\"79387\",        \"pricingDate\":\"20170914\",        \"salesOrg\": \"8000\",        \"billType\": \"ZPF2\",        \"materials\":[			{                \"recordKey\": \"7840363909-000001\",            	\"materialId\": \"1763549\",            	\"rebillQty\": \"2.000\",                \"uom\": \"KAR\",                \"dc\": \"8110\",                \"newSellCd\": \"1\",                \"newNoChargeBack\": \"N\",                \"newActivePrice\": \"YCON\",                \"newLead\": \"0000181126\",                \"newConRef\": \"SG-WEGMANS\",                \"newCbRef\": \"SG-WEGMANS\",                \"newContCogPer\": \"-2.50\",                \"newItemVarPer\": \"3.00\",                \"newListPrice\": \"435.39\",                \"newWac\": \"435.39\",                \"newBid\": \"64.65\",                \"newItemMkUpPer\": \"1.00\",                \"newAwp\": \"608.93\",                \"newPrice\": \"120.70\"            }        ]    }]}";
		String simulatePriceReqJSON = null; 
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		
		TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = bucketizePriceMap(invoiceLines);
		List<Object> pricingRequests = new ArrayList<Object>();
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (Map.Entry<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> entry : priceMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			_SimulatePriceRowHeader simulatePriceRowHeader = entry.getKey();
			if (simulatePriceRowHeader != null) {
				pricingReq.put("index", i++);
				pricingReq.put("customerId", simulatePriceRowHeader.getCustomerId());
				pricingReq.put("pricingDate", sdf.format(simulatePriceRowHeader.getPricingDate()));
				pricingReq.put("salesOrg", simulatePriceRowHeader.getSalesOrg());
				pricingReq.put("billType", simulatePriceRowHeader.getBillType());
				pricingReq.put("orderType", simulatePriceRowHeader.getOrderType());
				
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
			simulatePriceReqJSON = jacksonMapper.writeValueAsString(priceReqMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			simulatePriceReqJSON = "failed";
		}
		return simulatePriceReqJSON;
	}
	*/
	/*
	private static String prepSubmitPriceCorrectionCall(_CorrectionRowISO[] invoiceLines, String correlationId, String containerName, int startIndex, int endIndex, boolean sopDebug) {
		
		String submitPriceCorrectionReqJSON = null; 
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		
		TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> submitMap = bucketizeSubmitMap(invoiceLines, correlationId);

		List<Object> pricingRequests = new ArrayList<Object>();
		int i = 0;
		for (Entry<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> entry : submitMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			_APIReqHeader priceCorrectionRowHeader = entry.getKey();
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
	*/
	/*
	static TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> bucketizeSubmitMap(_CorrectionRowISO[] invoiceLines, String correlationId) {
		TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>> priceMap = new TreeMap<_APIReqHeader, TreeMap<String, _PriceCorrectionMaterial>>();
		
		for (int i = 0; i < invoiceLines.length; i++) {
			//Hydrate key as SubmitPriceReqHeader
			_APIReqHeader headerKey = hydrateSubmitPriceReqHeader(invoiceLines[i], correlationId, i); 
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() invoiceLines[" + i + "].headerKey - customerId: " + headerKey.getCustomerId() + ", billType: " + headerKey.getBillType());
 			
			//Hydrate priceCorrectionMaterial
			_PriceCorrectionMaterial priceCorrectionMaterial = hydratePriceCorrectionMaterial(invoiceLines[i]);
			String materialKey = priceCorrectionMaterial.getRecordKey();
			
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() materialKey[" + i + "]: " + materialKey + ", materialId: " + priceCorrectionMaterial.getMaterialId());
			
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
	*/
	/*
	private static _InvoiceLookupResp parseInvoiceLookupResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_InvoiceLookupResp invoiceLookupResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			invoiceLookupResp = jacksonMapper.readValue(resp, _InvoiceLookupResp.class);

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
		return invoiceLookupResp;
	}
	*/
	/*
	private static _PriceCorrectionResp parseSubmitPriceCorrectionResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_PriceCorrectionResp submitPriceResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			submitPriceResp = jacksonMapper.readValue(resp, _PriceCorrectionResp.class);
			//System.out.println(respInFile);

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
	*/
	/*
	private static _APIReqHeader hydrateSubmitPriceReqHeader(_CorrectionRowISO invoiceLine, String correlationId, int index) {
		_APIReqHeader headerKey = new _APIReqHeader();
		headerKey.setIndex(index);
		headerKey.setCustomerId(invoiceLine.getCustomerId());
		headerKey.setCorrelationId(correlationId);
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		return headerKey;
	}
	*/
	/*
	private static _PriceCorrectionMaterial hydratePriceCorrectionMaterial(_CorrectionRowISO invoiceLine) {
		_PriceCorrectionMaterial priceCorrectionMaterial = new _PriceCorrectionMaterial();
		priceCorrectionMaterial.setRecordKey(invoiceLine.getInvoiceId() + "-" + invoiceLine.getInvoiceLineItemNum());
		priceCorrectionMaterial.setMaterialId(invoiceLine.getMaterialId());
		priceCorrectionMaterial.setDc(invoiceLine.getDc());
		
		priceCorrectionMaterial.setOldActivePrice(invoiceLine.getOldActivePrice());
		priceCorrectionMaterial.setOldAwp(invoiceLine.getOldAwp());
		priceCorrectionMaterial.setOldBid(invoiceLine.getOldBid());
		priceCorrectionMaterial.setOldCbRef(invoiceLine.getOldCbRef());
		priceCorrectionMaterial.setOldChargeBack(invoiceLine.getOldChargeBack());
		priceCorrectionMaterial.setOldConRef(invoiceLine.getOldConRef());
		priceCorrectionMaterial.setOldContCogPer(invoiceLine.getOldContCogPer());
		priceCorrectionMaterial.setOldItemMkUpPer(invoiceLine.getOldItemMkUpPer());
		priceCorrectionMaterial.setOldItemVarPer(invoiceLine.getOldItemVarPer());
		priceCorrectionMaterial.setOldLead(invoiceLine.getOldLead());
		priceCorrectionMaterial.setOldListPrice(invoiceLine.getOldListPrice());
		priceCorrectionMaterial.setOldNoChargeBack(invoiceLine.getOldNoChargeBack());
		priceCorrectionMaterial.setOldOverridePrice(invoiceLine.getOldOverridePrice());
		priceCorrectionMaterial.setOldSellCd(invoiceLine.getOldSellCd());
		priceCorrectionMaterial.setOldSellPrice(invoiceLine.getOldPrice());
		priceCorrectionMaterial.setOldSf(invoiceLine.getOldSf());
		priceCorrectionMaterial.setOldSsf(invoiceLine.getOldSsf());
		priceCorrectionMaterial.setOldWac(invoiceLine.getOldWac());
		priceCorrectionMaterial.setOldWacCogPer(invoiceLine.getOldWacCogPer());
		
		priceCorrectionMaterial.setNewActivePrice(invoiceLine.getNewActivePrice());
		priceCorrectionMaterial.setNewAwp(invoiceLine.getNewAwp());
		priceCorrectionMaterial.setNewBid(invoiceLine.getNewBid());
		priceCorrectionMaterial.setNewCbRef(invoiceLine.getNewCbRef());
		priceCorrectionMaterial.setNewChargeBack(invoiceLine.getNewChargeBack());
		priceCorrectionMaterial.setNewConRef(invoiceLine.getNewConRef());
		priceCorrectionMaterial.setNewContCogPer(invoiceLine.getNewContCogPer());
		priceCorrectionMaterial.setNewItemMkUpPer(invoiceLine.getNewItemMkUpPer());
		priceCorrectionMaterial.setNewItemVarPer(invoiceLine.getNewItemVarPer());
		priceCorrectionMaterial.setNewLead(invoiceLine.getNewLead());
		priceCorrectionMaterial.setNewListPrice(invoiceLine.getNewListPrice());
		priceCorrectionMaterial.setNewNoChargeBack(invoiceLine.getNewNoChargeBack());
		priceCorrectionMaterial.setNewOverridePrice(invoiceLine.getNewOverridePrice());
		priceCorrectionMaterial.setNewSellCd(invoiceLine.getNewSellCd());
		priceCorrectionMaterial.setNewSellPrice(invoiceLine.getNewPrice());
		priceCorrectionMaterial.setNewSf(invoiceLine.getNewSf());
		priceCorrectionMaterial.setNewSsf(invoiceLine.getNewSsf());
		priceCorrectionMaterial.setNewWac(invoiceLine.getNewWac());
		priceCorrectionMaterial.setNewWacCogPer(invoiceLine.getNewWacCogPer());
		
		priceCorrectionMaterial.setRebillQty(invoiceLine.getRebillQty());
		priceCorrectionMaterial.setUom(invoiceLine.getUom());
		
		return priceCorrectionMaterial;
	}
	*/

	/**
	 * @param args
	 */
	//public static void main(String[] args) {
		/*
		String simulatePriceResp = "{    \"priceSimulationResp\": [        {            \"index\": \"0\",            \"customerId\": \"\",            \"salesOrg\": \"\",            \"billType\": \"\",            \"pricingDate\": \"\",            \"materials\": [                {                    \"materialId\": \"\",					\"recordKey\": \"7840363909-000001\",                    \"rebillQty\": \"0.000\",                    \"uom\": \"\",                    \"dc\": \"\",                    \"newLead\": \"\",                    \"newConRef\": \"\",                    \"newNoChargeBack\": \"\",                    \"newCbRef\": \"\",                    \"newSellCd\": \"\",                    \"newActivePrice\": \"\",                    \"newWac\": \"435.39\",                    \"newBid\": \"64.65\",                    \"newContCogPer\": \"-2.50\",                    \"newItemVarPer\": \"3.00\",                    \"newWacCogPer\": \"0.00\",                    \"newItemMkUpPer\": \"1.00\",                    \"newAwp\": \"608.93\",                    \"newOverridePrice\": \"0.00\"                }            ]        }    ],    \"results\": [        {            \"index\": \"0\",            \"status\": \"success\"        }    ]}";
		_SimulatePriceResp spr = parseSimulatePriceResp(simulatePriceResp);
		System.out.println("Parsed output: " + spr);
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/1.0.0/crrb/pricesimulation";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String correctionRowsJSON = "[{\"invoiceId\":\"7840771398\",\"invoiceLineItemNum\":\"000046\",\"customerId\":\"112455\",\"customerName\":\"FLOYD MEDICAL CENTER PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":1,\"retQty\":0,\"crQty\":0,\"rebillQty\":1,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8148\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"938\",\"chainName\":\"VHA PHS PRIME VENDOR\",\"groupId\":\"0360\",\"groupName\":\"VHA\",\"subgroupId\":\"000136\",\"subgroupName\":\"PARTNERS COOPERATIVE\",\"reasonCode\":null,\"poNumber\":\"MK201711155     00\",\"oldPrice\":5.06,\"curPrice\":4.89,\"newPrice\":4.89,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-7.199999809265137,\"curContCogPer\":-7.199999809265137,\"newContCogPer\":-7.199999809265137,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8320FGEHAT\",\"orgVendorAccAmt\":0,\"deaNum\":\"AF1176735\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":5.06,\"totChbk\":429.94,\"gxcb\":0,\"street\":\"P.O. BOX 233\",\"city\":\"ROME\",\"region\":\"GA\",\"postalCode\":\"30162\",\"country\":\"US\",\"hin\":\"381700JF3\"},{\"invoiceId\":\"7841758403\",\"invoiceLineItemNum\":\"000005\",\"customerId\":\"837192\",\"customerName\":\"ST JOHN 23 MILE SC    PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":2,\"retQty\":0,\"crQty\":0,\"rebillQty\":2,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"PYXIS112117     00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8326FGEHDT\",\"orgVendorAccAmt\":8598.8,\"deaNum\":\"FS0770099\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":10.24,\"totChbk\":859.88,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"G4B66QXF3\"},{\"invoiceId\":\"7841539082\",\"invoiceLineItemNum\":\"000006\",\"customerId\":\"334231\",\"customerName\":\"ST JOHN ST CLAIR PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":4,\"retQty\":0,\"crQty\":0,\"rebillQty\":4,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"1120170751      00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8325FGEHDT\",\"orgVendorAccAmt\":20637.12,\"deaNum\":\"BS4050972\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":20.48,\"totChbk\":1719.76,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"D3VXLMFF2\"}]}{\"items\":[{\"invoiceId\":\"7840771398\",\"invoiceLineItemNum\":\"000046\",\"customerId\":\"112455\",\"customerName\":\"FLOYD MEDICAL CENTER PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":1,\"retQty\":0,\"crQty\":0,\"rebillQty\":1,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8148\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"938\",\"chainName\":\"VHA PHS PRIME VENDOR\",\"groupId\":\"0360\",\"groupName\":\"VHA\",\"subgroupId\":\"000136\",\"subgroupName\":\"PARTNERS COOPERATIVE\",\"reasonCode\":null,\"poNumber\":\"MK201711155     00\",\"oldPrice\":5.06,\"curPrice\":4.89,\"newPrice\":4.89,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-7.199999809265137,\"curContCogPer\":-7.199999809265137,\"newContCogPer\":-7.199999809265137,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8320FGEHAT\",\"orgVendorAccAmt\":0,\"deaNum\":\"AF1176735\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":5.06,\"totChbk\":429.94,\"gxcb\":0,\"street\":\"P.O. BOX 233\",\"city\":\"ROME\",\"region\":\"GA\",\"postalCode\":\"30162\",\"country\":\"US\",\"hin\":\"381700JF3\"},{\"invoiceId\":\"7841758403\",\"invoiceLineItemNum\":\"000005\",\"customerId\":\"837192\",\"customerName\":\"ST JOHN 23 MILE SC    PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":2,\"retQty\":0,\"crQty\":0,\"rebillQty\":2,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"PYXIS112117     00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8326FGEHDT\",\"orgVendorAccAmt\":8598.8,\"deaNum\":\"FS0770099\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":10.24,\"totChbk\":859.88,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"G4B66QXF3\"},{\"invoiceId\":\"7841539082\",\"invoiceLineItemNum\":\"000006\",\"customerId\":\"334231\",\"customerName\":\"ST JOHN ST CLAIR PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":4,\"retQty\":0,\"crQty\":0,\"rebillQty\":4,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"1120170751      00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8325FGEHDT\",\"orgVendorAccAmt\":20637.12,\"deaNum\":\"BS4050972\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":20.48,\"totChbk\":1719.76,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"D3VXLMFF2\"}]";
		
		simulatePriceByJSON(url, httpMethod, sslAlias, correctionRowsJSON, true);
		*/
		/*
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoices";
		String method = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		//String filtersJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20170101\",		\"bepTo\": \"20170102\",		\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],		\"storeNumbers\": [\"4153\"],		\"orgIdSequence\": \"05118-50-00643\",		\"invoiceIds\": [\"786599102\"],		\"materials\": [\"1101856\", \"1107689\", \"1102345\"],		\"lead\": \"0000586432\",		\"chainIds\": [\"8082\"]	}],	\"startIndex\": 0,	\"endIndex\": 999,	\"totalNumberOfRecords\": 1500}";
		 
		String filtersJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20171112\",		\"orgIdSequence\": \"00483-01-05046\",		\"contractRef\": \"A01844-3\",		\"materials\": [\"1143411\", \"1144401\", \"1324508\", \"1763549\", \"1983899\", \"2175420\"],		\"lead\": \"0000178018\",		\"bepTo\": \"20171115\"	}]}";
		String rawInvoiceLookupResp = "";
		SalesHistoryResp invoiceLookupResp = null;
		Date d1 = new Date();
		Date d2 = null;
		
		d1 = new Date();
		System.out.println("\r\nStart invoiceLookup API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		//rawInvoiceLookupResp = callInvoiceLookupAPI(url, method, sslAlias, filtersJSON);
		rawInvoiceLookupResp = callAPI(url, method, sslAlias, filtersJSON, true);
		d2 = new Date();
		System.out.println("End invoiceLookup API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
		System.out.println("rawInvoiceLookupResp length: " + rawInvoiceLookupResp.length());
		System.out.println("rawInvoiceLookupResp: " + rawInvoiceLookupResp);
		System.out.println("Total invoiceLookup API time (ms): " + (d2.getTime() - d1.getTime()));
		
		d1 = new Date();
		System.out.println("\r\nStart response parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		invoiceLookupResp = parseInvoiceLookupResp(rawInvoiceLookupResp);
		d2 = new Date();
		System.out.println("End response parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
		
		System.out.println("resp Complex object from json file with number of records: " + invoiceLookupResp.numberOfInvoiceLines());
		System.out.println("Total parsing time (ms): " + (d2.getTime() - d1.getTime()));
		*/
	//}
	
}
