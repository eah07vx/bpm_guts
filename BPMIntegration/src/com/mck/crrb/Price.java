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
	
	public Price() {
		super();
	}
	
	// Need to send the complete invoiceLine correction row back - so keep local copy to be used in merging the response
	private _CorrectionRowISO[] invoiceLines;
	private Map<Integer, Set<String>> correlatedResults;
	private boolean useNewRebillCustomer; //= ((String)reqHeader.getPropertyValue("correctionType")).equals(_API.ACCOUNT_SWITCH);//added for Account Switch;
	//private TWObject reqHeader;
	@Override
	String prepRequest(String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		//Read and Hold original correction rows invoice lines to be overlaid with price simulation values from the response
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//TODO: Expect API to send ISO 8601 formatted date with Pacific time zone as the base 
			//	Evaluate DateTime offset if json string contains ISO string format with Z meaning UTC timezone 
			this.invoiceLines = null;
			this.invoiceLines = jacksonMapper.readValue(requestJSON, _CorrectionRowISO[].class);
			//this.correctionType = reqHeader.getPropertyValue("correctionType");
			//this.useNewRebillCustomer = ((String)reqHeader.getPropertyValue("correctionType")).equals(_API.ACCOUNT_SWITCH);

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
			for (int i = 0; this.invoiceLines != null && i < this.invoiceLines.length; i++) {
				if (this.invoiceLines[i] != null) {
					System.out.println(this.getClass().getName() + ".prepRequest() invoiceLines[" + i + "]: " + this.invoiceLines[i].toString());
				}
			}
		}
		if(this.invoiceLines == null) {
			return "failed";
		}
		boolean useOldValues = ((Boolean)reqHeader.getPropertyValue("useOldValues")).booleanValue();
		this.useNewRebillCustomer = ((String)reqHeader.getPropertyValue("correctionType")).equals(_API.ACCOUNT_SWITCH);//added for Account Switch
		//Transform the JSON to match price simulation API request
		return prepSimulatePriceCall("priceSimulationReq", 0, _API.FETCH_SIZE, useOldValues, sopDebug);
	}
	
	/* (non-Javadoc)
	 * @see com.mck.crrb._API#parseResponse(java.lang.String, boolean)
	 */
	@Override
	TWObject parseResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception {
		//Populate and return TWObject response
		try {
			//Parse and Merge the response into original correction row invoice lines
			_SimulatePriceResp simulatePriceResp = null;
			if (httpResp.getResponseCode() == HttpsURLConnection.HTTP_OK) {
				simulatePriceResp = parseSimulatePriceResp(rawResp, sopDebug);
				if (sopDebug) {
					System.out.println("invoiceLines.length: " + this.invoiceLines.length);
				}
				if (simulatePriceResp != null) {
					mergeSimulatePriceValues(simulatePriceResp, sopDebug);				
				}
			}
			
			TWObject twSimulatePriceResp = TWObjectFactory.createObject();
			TWList twCorrectionRows = null;
			TWList lookupResults = null;
			TWObject httpResponse = TWObjectFactory.createObject();
			httpResponse.setPropertyValue("responseCode", httpResp.getResponseCode());
			httpResponse.setPropertyValue("responseMessage", httpResp.getResponseMessage());
			twSimulatePriceResp.setPropertyValue("httpResponse", httpResponse);
			
			if (this.invoiceLines.length > 0) {
				twCorrectionRows = TWObjectFactory.createList();
			}
			for (int i = 0; i < this.invoiceLines.length; i++) {
				if (this.invoiceLines[i] != null && this.invoiceLines[i].getTwCorrectionRow() != null) {
					if (sopDebug) {
						System.out.println("invoiceLines[" + i + "]: " + this.invoiceLines[i].toString());
					}
					twCorrectionRows.addArrayData(this.invoiceLines[i].getTwCorrectionRow());
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
			System.out.println("Exception caught in " + this.getClass().getName() + ".parseResponse(): " + e.getMessage());
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
	
	private String prepSimulatePriceCall(String containerName, int startIndex, int endIndex, boolean useOldValues, boolean sopDebug) {
		
		String simulatePriceReqJSON = null;
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = bucketizePriceMap(this.invoiceLines, useOldValues, this.useNewRebillCustomer, sopDebug);
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
		priceReqMap.put("endIndex", i);
		
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
	
	private TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> bucketizePriceMap(_CorrectionRowISO[] invoiceLines, boolean useOldValues, boolean useNewRebillCustomer, boolean sopDebug) {
		TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>> priceMap = new TreeMap<_SimulatePriceRowHeader, TreeMap<String, _CreditRebillMaterial>>();
		//System.out.println("TestRow[] invoiceLines.length: " + invoiceLines.length);
		
		SimpleDateFormat sdf = new SimpleDateFormat(_API.API_DATE_FORMAT);

		for (int i = 0, idx = 0; i < invoiceLines.length; i++) {
			
			if (invoiceLines[i] != null && invoiceLines[i].getCustomerId() != null) {
				//Hydrate key as SimulatePriceRowHeader
				_SimulatePriceRowHeader headerKey = hydrateSimulatePriceRowHeader(invoiceLines[i], idx++, useNewRebillCustomer); 
				if (sopDebug) {
					System.out.println(this.getClass().getName() + ".bucketizePriceMap() invoiceLines[" + i + "].headerKey has:\n customerId: " + headerKey.getCustomerId() + ", pricingDate: " + sdf.format(headerKey.getPricingDate()));
				}
				//Hydrate creditRebillMaterial
				_CreditRebillMaterial creditRebillMaterial = hydrateCreditRebillMaterial(invoiceLines[i], useOldValues);
				String materialKey = creditRebillMaterial.getRecordKey();
				
				TreeMap<String, _CreditRebillMaterial> materialList = priceMap.get(headerKey);
				
				if (materialList == null) { // Key not in TreeMap - new key found
					materialList = new TreeMap<String, _CreditRebillMaterial>();
					materialList.put(materialKey, creditRebillMaterial);	// First material in list
					if (sopDebug) {
						System.out.println(this.getClass().getName() + ".bucketizePriceMap() headerKey before put in treemap [" + i + "]:\n" + headerKey.toString()); 
					}
					priceMap.put(headerKey, materialList);
				}
				else { 	// Key already exists in TreeMap
					materialList.put(materialKey, creditRebillMaterial);
				}
				if (sopDebug) {
					System.out.println("	materialList[" + i + "] after putting in treemap:\n" + materialList.get(materialKey));
				}
			}
		}
		return priceMap;
	}

	private _SimulatePriceRowHeader hydrateSimulatePriceRowHeader(_CorrectionRowISO invoiceLine, int index, boolean useNewRebillCustomer){
		_SimulatePriceRowHeader headerKey = new _SimulatePriceRowHeader();
		headerKey.setIndex(index);
		if (useNewRebillCustomer) {
			headerKey.setCustomerId(invoiceLine.getNewRebillCust());
		}
		else {
			headerKey.setCustomerId(invoiceLine.getCustomerId());
		}
		headerKey.setPricingDate(invoiceLine.getPricingDate());
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		headerKey.setOrderType(invoiceLine.getOrderType());
		return headerKey;
	}

	private _CreditRebillMaterial hydrateCreditRebillMaterial(_CorrectionRowISO invoiceLine, boolean useOldValues) {
		_CreditRebillMaterial creditRebillMaterial = new _CreditRebillMaterial();
		creditRebillMaterial.setRecordKey(invoiceLine.getInvoiceId() + "-" + invoiceLine.getInvoiceLineItemNum());
		creditRebillMaterial.setMaterialId(invoiceLine.getMaterialId());
		creditRebillMaterial.setRebillQty(invoiceLine.getRebillQty());
		creditRebillMaterial.setUom(invoiceLine.getUom());
		creditRebillMaterial.setDc(invoiceLine.getDc());
		//Setting new (corrected historical) value fields
		creditRebillMaterial.setNewWac(invoiceLine.getNewWac());
		creditRebillMaterial.setNewBid(invoiceLine.getNewBid());
		creditRebillMaterial.setNewLead(invoiceLine.getNewLead());
		creditRebillMaterial.setNewConRef(invoiceLine.getNewConRef());
		/* 
		 * Mapping newContCogPer, newItemVarPer, newWacCogPer, newItemMkUPPer changed as per last minute emails from Anujit Sen on 2018-05-03, 2018-05-11 & 2018-05-12
		 */
		if (invoiceLine.getSalesOrg().equals(_API.DEFAULT_SALES_ORG)) {
			creditRebillMaterial.setNewContCogPer(invoiceLine.getNewContCogPer());
		} else {
			creditRebillMaterial.setNewContCogPer(invoiceLine.getCurContCogPer());
		}
		
		creditRebillMaterial.setNewItemVarPer(invoiceLine.getNewItemVarPer());
		
		if (useOldValues) {
			creditRebillMaterial.setNewWacCogPer(invoiceLine.getOldWacCogPer());
			creditRebillMaterial.setNewItemMkUpPer(invoiceLine.getOldItemMkUpPer());
			/*
			 * Removing oldNoChargeBack mapping to newNoChargeBack based on Anujit Sen's email on 5/15/2018
			 */
			//creditRebillMaterial.setNewNoChargeBack(invoiceLine.getOldNoChargeBack());
			creditRebillMaterial.setNewOverridePrice(invoiceLine.getOldOverridePrice());
			creditRebillMaterial.setNewSellCd(invoiceLine.getOldSellCd());
			creditRebillMaterial.setNewSsf(invoiceLine.getOldSsf());
			creditRebillMaterial.setNewSf(invoiceLine.getOldSf());
			creditRebillMaterial.setNewAbd(invoiceLine.getOldAbd());
			creditRebillMaterial.setNewPrice(invoiceLine.getOldPrice());
			creditRebillMaterial.setNewListPrice(invoiceLine.getOldListPrice());
			creditRebillMaterial.setNewAwp(invoiceLine.getOldAwp());
		} else {
			creditRebillMaterial.setNewWacCogPer(invoiceLine.getNewWacCogPer());
			creditRebillMaterial.setNewItemMkUpPer(invoiceLine.getNewItemMkUpPer());
			creditRebillMaterial.setNewOverridePrice(invoiceLine.getNewOverridePrice());
			creditRebillMaterial.setNewSellCd(invoiceLine.getNewSellCd());
			creditRebillMaterial.setNewSsf(invoiceLine.getNewSsf());
			creditRebillMaterial.setNewSf(invoiceLine.getNewSf());
			creditRebillMaterial.setNewAbd(invoiceLine.getNewAbd());
			creditRebillMaterial.setNewPrice(invoiceLine.getNewPrice());
			creditRebillMaterial.setNewListPrice(invoiceLine.getNewListPrice());
			creditRebillMaterial.setNewAwp(invoiceLine.getNewAwp());
		}
		
		creditRebillMaterial.setNewNoChargeBack(invoiceLine.getNewNoChargeBack());

		creditRebillMaterial.setNewActivePrice(invoiceLine.getNewActivePrice());
		creditRebillMaterial.setNewCbRef(invoiceLine.getNewCbRef());
		creditRebillMaterial.setNewChargeBack(invoiceLine.getNewChargeBack());
		//TODO: Check if these new fields are need in simulate price call?? 
		creditRebillMaterial.setNewContType(invoiceLine.getNewContType());
		creditRebillMaterial.setNewContrId(invoiceLine.getNewContrId());
		creditRebillMaterial.setNewNetBill(invoiceLine.getNewNetBill());
		creditRebillMaterial.setNewProgType(invoiceLine.getNewProgType());
		
		/* 
		 * Mapping oldAwp -> newAwp and oldListPrice -> newListPrice as per last minute email from Anujit Sen on 2018-05-03
		 */
		//creditRebillMaterial.setNewListPrice(invoiceLine.getNewListPrice());
		
		//Setting old value fields
		creditRebillMaterial.setOldAwp(invoiceLine.getOldAwp());
		creditRebillMaterial.setOldAbd(invoiceLine.getOldAbd());
		creditRebillMaterial.setOldBid(invoiceLine.getOldBid());
		creditRebillMaterial.setOldChargeBack(invoiceLine.getOldChargeBack());
		creditRebillMaterial.setOldContCogPer(invoiceLine.getOldContCogPer());
		creditRebillMaterial.setOldItemMkUpPer(invoiceLine.getOldItemMkUpPer());
		creditRebillMaterial.setOldItemVarPer(invoiceLine.getOldItemVarPer());
		creditRebillMaterial.setOldWacCogPer(invoiceLine.getOldWacCogPer());
		creditRebillMaterial.setOldLead(invoiceLine.getOldLead());
		creditRebillMaterial.setOldListPrice(invoiceLine.getOldListPrice());
		creditRebillMaterial.setOldNoChargeBack(invoiceLine.getOldNoChargeBack());
		creditRebillMaterial.setOldOverridePrice(invoiceLine.getOldOverridePrice());
		creditRebillMaterial.setOldSellCd(invoiceLine.getOldSellCd());
		creditRebillMaterial.setOldPrice(invoiceLine.getOldPrice());
		creditRebillMaterial.setOldSf(invoiceLine.getOldSf());
		creditRebillMaterial.setOldSsf(invoiceLine.getOldSsf());
		creditRebillMaterial.setOldWac(invoiceLine.getOldWac());
		//TODO: Check if these old fields should to be sent/echoed in simulate price call?
		//creditRebillMaterial.setOldActivePrice(invoiceLine.getOldActivePrice());
		//creditRebillMaterial.setOldCbRef(invoiceLine.getOldCbRef());
		//creditRebillMaterial.setoldConRef(invoiceLine.getOldConRef());

		return creditRebillMaterial;
	}

	private _CorrectionRowISO[] mergeSimulatePriceValues(_SimulatePriceResp simulatePriceResp, boolean sopDebug) {
		if (this.invoiceLines != null && this.invoiceLines.length > 0 && simulatePriceResp != null 
				&& simulatePriceResp.getPriceSimulationResp() != null && simulatePriceResp.getPriceSimulationResp().length > 0) {
			_SimulatePriceRow[] simulatePriceRows = simulatePriceResp.getPriceSimulationResp();
			if (sopDebug) {
				System.out.print("mergeSimulatedPriceValues invoiceLines[].length: " + this.invoiceLines.length + " simulatePriceRows[].length: " + simulatePriceRows.length);
			}
			for(int i = 0; i < this.invoiceLines.length; i++) {
				simPriceRows:
				for(int j = 0; j < simulatePriceRows.length; j++){
					if (sopDebug) {
						System.out.print("mergeSimulatedPriceValues loop invoiceLines["+i+"] > simulatedPriceRows["+j+"] ");
						System.out.println("> invoiceLines[i].getPricingDate() equals simulatePriceRows[j].getPricingDate()? " + this.invoiceLines[i].getPricingDate() + " == " + simulatePriceRows[j].getPricingDate());
						System.out.println("> invoiceLines[i].getCustomerId() equals simulatePriceRows[j].getCustomerId()? " + this.invoiceLines[i].getCustomerId() + " == " + simulatePriceRows[j].getCustomerId());
					}
					//System.out.println("useNewRebillCustomer:  " + this.useNewRebillCustomer);
					if (this.invoiceLines != null && this.invoiceLines[i] != null && this.invoiceLines[i].getCustomerId() != null 
							&& this.useNewRebillCustomer ? this.invoiceLines[i].getNewRebillCust().equals(simulatePriceRows[j].getCustomerId()) : this.invoiceLines[i].getCustomerId().equals(simulatePriceRows[j].getCustomerId())
							&& _Utility.compareDates(this.invoiceLines[i].getPricingDate(), simulatePriceRows[j].getPricingDate()) == 0
							&& this.invoiceLines[i].getBillType().equals(simulatePriceRows[j].getBillType()) 
							&& this.invoiceLines[i].getSalesOrg().equals(simulatePriceRows[j].getSalesOrg())) {

							//&& this.invoiceLines[i].getPricingDate().compareTo(simulatePriceRows[j].getPricingDate()) == 0) {
						if (sopDebug) {
							System.out.println("> customerId and pricingDate match: true");
						}
						_CreditRebillMaterial[] crMaterials = simulatePriceRows[j].getMaterials();
						for(int k = 0; k < crMaterials.length; k++) {
							String invoiceLineRecordKey = this.invoiceLines[i].getInvoiceId() + "-" + this.invoiceLines[i].getInvoiceLineItemNum();
							if (invoiceLineRecordKey.equals(crMaterials[k].getRecordKey())) {
								if (sopDebug) {
									System.out.println("> invoiceLineRecordKey and crMaterials record key match: " + invoiceLineRecordKey + " == " + crMaterials[k].getRecordKey());
								}
								this.invoiceLines[i].setNewPrice(crMaterials[k].getNewSellPrice());
								this.invoiceLines[i].setNewActivePrice(crMaterials[k].getNewActivePrice());
								this.invoiceLines[i].setNewAwp(crMaterials[k].getNewAwp());
								this.invoiceLines[i].setNewBid(crMaterials[k].getNewBid());
								this.invoiceLines[i].setNewCbRef(crMaterials[k].getNewCbRef());
								this.invoiceLines[i].setNewChargeBack(crMaterials[k].getNewChargeBack());
								this.invoiceLines[i].setNewConRef(crMaterials[k].getNewConRef());
								this.invoiceLines[i].setNewContCogPer(crMaterials[k].getNewContCogPer());
								this.invoiceLines[i].setNewItemMkUpPer(crMaterials[k].getNewItemMkUpPer());
								this.invoiceLines[i].setNewItemVarPer(crMaterials[k].getNewItemVarPer());
								this.invoiceLines[i].setNewWacCogPer(crMaterials[k].getNewWacCogPer());
								this.invoiceLines[i].setNewLead(crMaterials[k].getNewLead());
								this.invoiceLines[i].setNewListPrice(crMaterials[k].getNewListPrice());
								this.invoiceLines[i].setNewNoChargeBack(crMaterials[k].getNewNoChargeBack());
								this.invoiceLines[i].setNewOverridePrice(crMaterials[k].getNewOverridePrice());
								this.invoiceLines[i].setNewSellCd(crMaterials[k].getNewSellCd());
								this.invoiceLines[i].setNewSsf(crMaterials[k].getNewSsf());
								this.invoiceLines[i].setNewSf(crMaterials[k].getNewSf());
								this.invoiceLines[i].setNewWac(crMaterials[k].getNewWac());
//								TODO: Check if newAbd needs to be added to materials and add other fields
								this.invoiceLines[i].setNewAbd(crMaterials[k].getNewAbd());
								this.invoiceLines[i].setNewContType(crMaterials[k].getNewContType());
								this.invoiceLines[i].setNewContrId(crMaterials[k].getNewContrId());
								this.invoiceLines[i].setNewNetBill(crMaterials[k].getNewNetBill());
								this.invoiceLines[i].setNewProgType(crMaterials[k].getNewProgType());
								this.invoiceLines[i].setDc(crMaterials[k].getDc());
								this.invoiceLines[i].setNewCustomerName(crMaterials[k].getNewCustomerName());
								//this.invoiceLines[i].setNewRebillCust();
								this.invoiceLines[i].setNewPurchaseOrder(crMaterials[k].getNewPurchaseOrder());
								this.invoiceLines[i].setNewItemDcMarkUpPer(crMaterials[k].getNewItemDcMarkUpPer());
								break simPriceRows;
							}
						}
					}
				}
			}
		}
		return this.invoiceLines;
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
