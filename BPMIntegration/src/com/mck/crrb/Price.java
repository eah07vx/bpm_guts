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
public class Price extends _API {
	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * @see _API#process(String, String, String, String, boolean) 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 * @throws Exception 
	 *  
	 */
	@Deprecated
	public TWObject simulateByTWList(String url, String httpMethod, String sslAlias, TWList correctionRows, boolean sopDebug) throws Exception {
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
		String requestJSON = prepSimulatePriceCall(invoiceLines, "priceSimulationReq", 1, _Utility.FETCH_SIZE, sopDebug);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
//		return twCorrectionRows;
		return super.process(url, httpMethod, sslAlias, requestJSON, sopDebug);
	}
	
	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * @see _API#process(String, String, String, String, boolean) 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 * @throws Exception 
	 *  
	 */
	public TWObject simulate(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception {
		return super.process(url, httpMethod, sslAlias, requestJSON, sopDebug);
	}

	/* (non-Javadoc)
	 * @see com.mck.crrb._API#parseResponse(java.lang.String, boolean)
	 */
	@Override
	TWObject parseResponse(String rawResp, boolean sopDebug) throws Exception {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_InvoiceLookupResp invoices = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.DATE_FORMAT);
			jacksonMapper.setDateFormat(sdf);
			
			invoices = jacksonMapper.readValue(rawResp, _InvoiceLookupResp.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TWObject twInvoiceLookupResp = TWObjectFactory.createObject();
			TWList corRows = null;
			TWList lookupResults = null;
			if (invoices != null && (corRows = invoices.getTwCorrectionRows()) != null && (lookupResults = invoices.getTwResults()) != null) {
				if(sopDebug) System.out.println("Invoice.lookupInvoices() Returning non empty response!");
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
	
	private static _SimulatePriceResp parseSimulatePriceResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_SimulatePriceResp simulatePriceResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			simulatePriceResp = jacksonMapper.readValue(resp, _SimulatePriceResp.class);
			//System.out.println(respInFile);

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return simulatePriceResp;
	}
	
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sopDebug) {
			for (int i = 0; invoiceLines != null && i < invoiceLines.length; i++) {
				if (invoiceLines[i] != null) {
					System.out.println("Invoice.simulatePriceJSON() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}
		String requestJSON = prepSimulatePriceCall(invoiceLines, "priceSimulationReq", 1, _Utility.FETCH_SIZE, sopDebug);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return twCorrectionRows;
	}	

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
	
	static TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> bucketizePriceMap(_CorrectionRowISO[] invoiceLines) {
		TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = new TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>>();
		//System.out.println("TestRow[] invoiceLines.length: " + invoiceLines.length);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < invoiceLines.length; i++) {
			//NameValuePair<String, String> key = new NameValuePair<String, String>(invoiceLines[i].getCustomerId(), sdf.format(invoiceLines[i].getPricingDate()));

			//Hydrate key as SimulatePriceRowHeader
			_SimulatePriceRowHeader headerKey = hydrateSimulatePriceRowHeader(invoiceLines[i], i); //new SimulatePriceRowHeader(invoiceLines[i].getCustomerId(), sdf.format(invoiceLines[i].getPricingDate()), ...);
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() invoiceLines[" + i + "].headerKey - customerId: " + headerKey.getCustomerId() + ", pricingDate: " + sdf.format(headerKey.getPricingDate()));
 			
			//Hydrate creditRebillMaterial
			_CreditRebillMaterial creditRebillMaterial = hydrateCreditRebillMaterial(invoiceLines[i]);
			String materialKey = creditRebillMaterial.getRecordKey();
			
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() materialKey[" + i + "]: " + materialKey + ", materialId: " + creditRebillMaterial.getMaterialId());
			
			TreeMap<String, _CreditRebillMaterial> materialList = priceMap.get(headerKey);
			//TODO: Remove SOP debug
			System.out.print("materialList[" + i + "] before: " + (materialList != null ? materialList.get(materialKey) : materialList));
			
			if (materialList == null) { // Key not in TreeMap - new key found
				materialList = new TreeMap<String, _CreditRebillMaterial>();
				materialList.put(materialKey, creditRebillMaterial);	// First material in list
				priceMap.put(headerKey, materialList);
			}
			else { 	// Key already exists in TreeMap
				materialList.put(materialKey, creditRebillMaterial);
			}
			//TODO: Remove SOP debug
			System.out.println("  after: " + materialList.get(materialKey) + "\n");
		}
		return priceMap;
	}

	private static _SimulatePriceRowHeader hydrateSimulatePriceRowHeader(_CorrectionRowISO invoiceLine, int index){
		_SimulatePriceRowHeader headerKey = new _SimulatePriceRowHeader();
		headerKey.setIndex(index);
		headerKey.setCustomerId(invoiceLine.getCustomerId());
		headerKey.setPricingDate(invoiceLine.getPricingDate());
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		headerKey.setOrderType(invoiceLine.getOrderType());
		return headerKey;
	}

	private static _CreditRebillMaterial hydrateCreditRebillMaterial(_CorrectionRowISO invoiceLine) {
		_CreditRebillMaterial creditRebillMaterial = new _CreditRebillMaterial();
		creditRebillMaterial.setRecordKey(invoiceLine.getInvoiceId() + "-" + invoiceLine.getInvoiceLineItemNum());
		creditRebillMaterial.setMaterialId(invoiceLine.getMaterialId());
		creditRebillMaterial.setDc(invoiceLine.getDc());
		creditRebillMaterial.setNewActivePrice(invoiceLine.getNewActivePrice());
		creditRebillMaterial.setNewAwp(invoiceLine.getNewAwp());
		creditRebillMaterial.setNewBid(invoiceLine.getNewBid());
		creditRebillMaterial.setNewCbRef(invoiceLine.getNewCbRef());
		creditRebillMaterial.setNewChargeBack(invoiceLine.getNewChargeBack());
		creditRebillMaterial.setNewConRef(invoiceLine.getNewConRef());
		creditRebillMaterial.setNewContCogPer(invoiceLine.getNewContCogPer());
		creditRebillMaterial.setNewItemMkUpPer(invoiceLine.getNewItemMkUpPer());
		creditRebillMaterial.setNewItemVarPer(invoiceLine.getNewItemVarPer());
		creditRebillMaterial.setNewLead(invoiceLine.getNewLead());
		creditRebillMaterial.setNewListPrice(invoiceLine.getNewListPrice());
		creditRebillMaterial.setNewNoChargeBack(invoiceLine.getNewNoChargeBack());
		creditRebillMaterial.setNewOverridePrice(invoiceLine.getNewOverridePrice());
		creditRebillMaterial.setNewSellCd(invoiceLine.getNewSellCd());
		creditRebillMaterial.setNewSellPrice(invoiceLine.getNewPrice());
		creditRebillMaterial.setNewSf(invoiceLine.getNewSf());
		creditRebillMaterial.setNewSsf(invoiceLine.getNewSsf());
		creditRebillMaterial.setNewWac(invoiceLine.getNewWac());
		creditRebillMaterial.setNewWacCogPer(invoiceLine.getNewWacCogPer());
		creditRebillMaterial.setRebillQty(invoiceLine.getRebillQty());
		creditRebillMaterial.setUom(invoiceLine.getUom());
		
		return creditRebillMaterial;
	}

	private static _CorrectionRowISO[] mergeSimulatePriceValues(_CorrectionRowISO[] invoiceLines, _SimulatePriceResp simulatePriceResp) {
		if (invoiceLines != null && invoiceLines.length > 0 && simulatePriceResp != null 
				&& simulatePriceResp.getPriceSimulationResp() != null && simulatePriceResp.getPriceSimulationResp().length > 0) {
			_SimulatePriceRow[] simulatePriceRows = simulatePriceResp.getPriceSimulationResp();
			for(int i = 0; i < invoiceLines.length; i++) {
				for(int j = 0; j < simulatePriceRows.length; j++){
					if (invoiceLines[i].getCustomerId() != null && invoiceLines[i].getCustomerId().equals(simulatePriceRows[j].getCustomerId())
							&& invoiceLines[i].getPricingDate().compareTo(simulatePriceRows[j].getPricingDate()) == 0) {
						_CreditRebillMaterial[] crMaterials = simulatePriceRows[j].getMaterials();
						for(int k = 0; k < crMaterials.length; k++) {
							String invoiceLineRecordKey = invoiceLines[i].getInvoiceId() + "-" + invoiceLines[i].getInvoiceLineItemNum();
							if (invoiceLineRecordKey.equals(crMaterials[k].getRecordKey())) {
								invoiceLines[i].setNewPrice(crMaterials[k].getNewSellPrice());
								invoiceLines[i].setNewActivePrice(crMaterials[k].getNewActivePrice());
								invoiceLines[i].setNewAwp(crMaterials[k].getNewAwp());
								invoiceLines[i].setNewBid(crMaterials[k].getNewBid());
								invoiceLines[i].setNewCbRef(crMaterials[k].getNewCbRef());
								invoiceLines[i].setNewChargeBack(crMaterials[k].getNewChargeBack());
								invoiceLines[i].setNewConRef(crMaterials[k].getNewConRef());
								invoiceLines[i].setNewContCogPer(crMaterials[k].getNewContCogPer());
								invoiceLines[i].setNewItemMkUpPer(crMaterials[k].getNewItemMkUpPer());
								invoiceLines[i].setNewItemVarPer(crMaterials[k].getNewItemVarPer());
								invoiceLines[i].setNewLead(crMaterials[k].getNewLead());
								invoiceLines[i].setNewListPrice(crMaterials[k].getNewListPrice());
								invoiceLines[i].setNewNoChargeBack(crMaterials[k].getNewNoChargeBack());
								invoiceLines[i].setNewOverridePrice(crMaterials[k].getNewOverridePrice());
								invoiceLines[i].setNewSellCd(crMaterials[k].getNewSellCd());
								invoiceLines[i].setNewSf(crMaterials[k].getNewSf());
								invoiceLines[i].setNewSsf(crMaterials[k].getNewSsf());
								invoiceLines[i].setNewWac(crMaterials[k].getNewWac());
								invoiceLines[i].setNewWacCogPer(crMaterials[k].getNewWacCogPer());
//								TODO: Check if newAbd needs to be added to materials and add other fields
//								invoiceLines[i].setNewAbd(crMaterials[k].getNew??);
							}
						}
					}
				}
			}
		}
		return invoiceLines;
	}
}
