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
import java.util.Set;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

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
	// Need to send the complete invoiceLine correction row back - so keep local copy to be used in merging the response
	_CorrectionRowISO[] invoiceLines;
	Map<Integer, Set<String>> correlatedResults;
	
	@Override
	String prepRequest(String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		//Read and Hold original correction rows invoice lines to be overlaid with price simulation values from the response
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//TODO: Evaluate DateTime offset if json string contains ISO string format with Z meaning UTC timezone 
			
			this.invoiceLines = jacksonMapper.readValue(requestJSON, _CorrectionRowISO[].class);

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
					System.out.println("Invoice.simulatePriceJSON() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
				}
			}
		}
		if(this.invoiceLines == null) {
			return "failed";
		}
		//Transform the JSON to match price simulation API request
		return prepSimulatePriceCall("priceSimulationReq", 0, _API.FETCH_SIZE, sopDebug);
	}
	
	/* (non-Javadoc)
	 * @see com.mck.crrb._API#parseResponse(java.lang.String, boolean)
	 */
	@Override
	TWObject parseResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		//Parse and Merge the response into original correction row invoice lines
		_SimulatePriceResp simulatePriceResp = null;
		if (httpResp.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			simulatePriceResp = parseSimulatePriceResp(rawResp, sopDebug);
			if (sopDebug) {
				System.out.println("invoiceLines.length: " + invoiceLines.length);
				System.out.println("simulatePriceResp: " + simulatePriceResp);
			}
			if (simulatePriceResp != null) {
				mergeSimulatePriceValues(this.invoiceLines, simulatePriceResp);				
			}
		}
		//Populate and return TWObject response
		try {
			TWObject twSimulatePriceResp = TWObjectFactory.createObject();
			TWList twCorrectionRows = null;
			TWList lookupResults = null;
			TWObject httpResponse = TWObjectFactory.createObject();
			httpResponse.setPropertyValue("responseCode", httpResp.getResponseCode());
			httpResponse.setPropertyValue("responseMessage", httpResp.getResponseMessage());
			twSimulatePriceResp.setPropertyValue("httpResponse", httpResponse);
			
			twCorrectionRows = TWObjectFactory.createList();
			int size = invoiceLines.length;
			for (int i = 0; i < size; i++) {
				if (invoiceLines[i] != null && invoiceLines[i].getTwCorrectionRow() != null) {
					twCorrectionRows.addArrayData(invoiceLines[i].getTwCorrectionRow());
				}
			}
			if ((twCorrectionRows != null) && (simulatePriceResp != null) 
					&& (lookupResults = mergeCorrelatedResults(this.correlatedResults, simulatePriceResp.getResults())) != null) {
				if(sopDebug) System.out.println("Price.parseResponse() Returning non empty response!");
				twSimulatePriceResp.setPropertyValue("correctionRows", twCorrectionRows);
				twSimulatePriceResp.setPropertyValue("results", lookupResults);
			}
			else {
				// Return empty object but not a null object
				if(sopDebug) System.out.println("Price.parseResponse() Returning empty response!");
				twSimulatePriceResp.setPropertyValue("correctionRows", TWObjectFactory.createList());
				twSimulatePriceResp.setPropertyValue("results", TWObjectFactory.createList());
			}
			return twSimulatePriceResp;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
	}

	private _SimulatePriceResp parseSimulatePriceResp(String rawResp, boolean sopDebug) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_SimulatePriceResp simulatePriceResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
			jacksonMapper.setDateFormat(sdf);
			
			simulatePriceResp = jacksonMapper.readValue(rawResp, _SimulatePriceResp.class);
			if (sopDebug) {
				System.out.println("simulatePriceResp: " + simulatePriceResp);
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
		return simulatePriceResp;
	}
	
	private String prepSimulatePriceCall(String containerName, int startIndex, int endIndex, boolean sopDebug) {
		
		String simulatePriceReqJSON = null;
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = bucketizePriceMap(this.invoiceLines, sopDebug);
		List<Object> pricingRequests = new ArrayList<Object>();
		this.correlatedResults = new TreeMap<Integer, Set<String>>();
		
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);
		
		for (Map.Entry<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> entry : priceMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			_SimulatePriceRowHeader simulatePriceRowHeader = entry.getKey();
			TreeMap<String, _CreditRebillMaterial> simulatePriceRowDetail = entry.getValue();
			if (simulatePriceRowHeader != null && simulatePriceRowDetail != null && simulatePriceRowDetail.values() != null) {
				pricingReq.put("index", i);
				pricingReq.put("customerId", simulatePriceRowHeader.getCustomerId());
				pricingReq.put("pricingDate", sdf.format(simulatePriceRowHeader.getPricingDate()));
				pricingReq.put("salesOrg", simulatePriceRowHeader.getSalesOrg());
				pricingReq.put("billType", simulatePriceRowHeader.getBillType());
				pricingReq.put("orderType", simulatePriceRowHeader.getOrderType());
				if (sopDebug) {
					System.out.println(this.getClass().getName() + ".prepSimulatePriceCall() - simulatePriceRowDetail.keySet[" + i + "]: " + simulatePriceRowDetail.keySet().toString());
				}
				this.correlatedResults.put(i, simulatePriceRowDetail.keySet()); // has recordKeys
				pricingReq.put("materials", simulatePriceRowDetail.values());	// has material records
				pricingRequests.add(pricingReq);
				i++;
			}
		}
		priceReqMap.put(containerName, pricingRequests.toArray());
		priceReqMap.put("startIndex", startIndex);
		priceReqMap.put("endIndex", endIndex - 1);
		
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.setDateFormat(new SimpleDateFormat(_API.API_DATE_FORMAT));
		jacksonMapper.setSerializationInclusion(Include.NON_NULL);
		
		try {
			simulatePriceReqJSON = jacksonMapper.writeValueAsString(priceReqMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			simulatePriceReqJSON = "failed";
		}
		return simulatePriceReqJSON;
	}
	
	private TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> bucketizePriceMap(_CorrectionRowISO[] invoiceLines, boolean sopDebug) {
		TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = new TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>>();
		//System.out.println("TestRow[] invoiceLines.length: " + invoiceLines.length);
		
		SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);

		for (int i = 0, idx = 0; i < invoiceLines.length; i++) {
			//NameValuePair<String, String> key = new NameValuePair<String, String>(invoiceLines[i].getCustomerId(), sdf.format(invoiceLines[i].getPricingDate()));
			/*if (sopDebug) {
				System.out.println("Price.bucketizePriceMap() invoiceLines[i] null?: " + (invoiceLines[i] != null));
				System.out.println("Price.bucketizePriceMap() invoiceLines[i]: " + invoiceLines[i]);
			}*/
			if (invoiceLines[i] != null && invoiceLines[i].getCustomerId() != null) {
				//Hydrate key as SimulatePriceRowHeader
				_SimulatePriceRowHeader headerKey = hydrateSimulatePriceRowHeader(invoiceLines[i], idx++); 
				if (sopDebug) {
					System.out.println((i+1) + ". \n" + this.getClass().getName() + ".bucketizePriceMap() invoiceLines[" + i + "].headerKey - customerId: " + headerKey.getCustomerId() + ", pricingDate: " + sdf.format(headerKey.getPricingDate()));
				}
				//Hydrate creditRebillMaterial
				_CreditRebillMaterial creditRebillMaterial = hydrateCreditRebillMaterial(invoiceLines[i]);
				String materialKey = creditRebillMaterial.getRecordKey();
				
				//TODO: Remove SOP debug statement below
				//System.out.println("Price.bucketizePriceMap() materialKey[" + i + "]: " + materialKey + ", materialId: " + creditRebillMaterial.getMaterialId());
				
				TreeMap<String, _CreditRebillMaterial> materialList = priceMap.get(headerKey);
				//TODO: Remove SOP debug
				//System.out.print("materialList[" + i + "] before: " + (materialList != null ? materialList.get(materialKey) : materialList));
				
				if (materialList == null) { // Key not in TreeMap - new key found
					materialList = new TreeMap<String, _CreditRebillMaterial>();
					materialList.put(materialKey, creditRebillMaterial);	// First material in list
					if (sopDebug) {
						System.out.println(this.getClass().getName() + ".bucketizePriceMap() headerKey before put in treemap [" + i + "] \n" + headerKey.toString()); 
					}
					priceMap.put(headerKey, materialList);
				}
				else { 	// Key already exists in TreeMap
					materialList.put(materialKey, creditRebillMaterial);
				}
				if (sopDebug) {
					System.out.println("	materialList[" + i + "] after putting in treemap: " + materialList.get(materialKey) + "\n");
				}
			}
		}
		return priceMap;
	}

	private _SimulatePriceRowHeader hydrateSimulatePriceRowHeader(_CorrectionRowISO invoiceLine, int index){
		_SimulatePriceRowHeader headerKey = new _SimulatePriceRowHeader();
		headerKey.setIndex(index);
		headerKey.setCustomerId(invoiceLine.getCustomerId());
		headerKey.setPricingDate(invoiceLine.getPricingDate());
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		headerKey.setOrderType(invoiceLine.getOrderType());
		return headerKey;
	}

	private _CreditRebillMaterial hydrateCreditRebillMaterial(_CorrectionRowISO invoiceLine) {
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
		//TODO: Check if these new fields need to be echoed in simulate price call?? 
		creditRebillMaterial.setNewContType(invoiceLine.getNewContType());
		creditRebillMaterial.setNewContrId(invoiceLine.getNewContrId());
		creditRebillMaterial.setNewNetBill(invoiceLine.getNewNetBill());
		creditRebillMaterial.setNewProgType(invoiceLine.getNewProgType());

		return creditRebillMaterial;
	}

	private _CorrectionRowISO[] mergeSimulatePriceValues(_CorrectionRowISO[] invoiceLines, _SimulatePriceResp simulatePriceResp) {
		if (invoiceLines != null && invoiceLines.length > 0 && simulatePriceResp != null 
				&& simulatePriceResp.getPriceSimulationResp() != null && simulatePriceResp.getPriceSimulationResp().length > 0) {
			_SimulatePriceRow[] simulatePriceRows = simulatePriceResp.getPriceSimulationResp();
			for(int i = 0; i < invoiceLines.length; i++) {
				for(int j = 0; j < simulatePriceRows.length; j++){
					if (invoiceLines != null && invoiceLines[i] != null && invoiceLines[i].getCustomerId() != null && invoiceLines[i].getCustomerId().equals(simulatePriceRows[j].getCustomerId())
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
								invoiceLines[i].setNewSsf(crMaterials[k].getNewSsf());
								invoiceLines[i].setNewSf(crMaterials[k].getNewSf());
								invoiceLines[i].setNewWac(crMaterials[k].getNewWac());
								invoiceLines[i].setNewWacCogPer(crMaterials[k].getNewWacCogPer());
//								TODO: Check if newAbd needs to be added to materials and add other fields
//								invoiceLines[i].setNewAbd(crMaterials[k].getNewAbd());
								invoiceLines[i].setNewContType(crMaterials[k].getNewContType());
								invoiceLines[i].setNewContrId(crMaterials[k].getNewContrId());
								invoiceLines[i].setNewNetBill(crMaterials[k].getNewNetBill());
								invoiceLines[i].setNewProgType(crMaterials[k].getNewProgType());
								invoiceLines[i].setDc(crMaterials[k].getDc());
							}
						}
					}
				}
			}
		}
		return invoiceLines;
	}
	
	private TWList mergeCorrelatedResults(Map<Integer, Set<String>> correlatedResults, _IndexedResult[] indexedResults) throws Exception {
		TWList twResults = TWObjectFactory.createList();
		if (indexedResults != null) {
			for(int i = 0; i < indexedResults.length; i++) {
				//this.twResult.setPropertyValue("index", this.index);
				TWObject twResult = TWObjectFactory.createObject();
				int listIndex = indexedResults[i].getIndex();
				twResult.setPropertyValue("index", listIndex);
				twResult.setPropertyValue("status", indexedResults[i].getStatus());
				twResult.setPropertyValue("message", indexedResults[i].getMessage());
				TWList twRecordKeys = TWObjectFactory.createList();
				Set<String> recordKeys = correlatedResults.get(listIndex);
				if (recordKeys != null) {
					for (String recordKey : recordKeys) {
						twRecordKeys.addArrayData(recordKey);
					}
				}
				twResult.setPropertyValue("recordKeys", twRecordKeys);
				twResults.addArrayData(twResult);
			}
		}
		return twResults;
	}
}
