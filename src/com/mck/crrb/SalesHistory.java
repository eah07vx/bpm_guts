/**
 * 
 */
package com.mck.crrb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import teamworks.TWList;

/**
 * @author akatre
 *
 */
public class SalesHistory {
	private SalesHistoryResp salesHistory;
	
	public TWList getSalesHistory(String invoiceURL, String curPriceURL, String histPriceURL, 
			String httpMethod, String sslAlias, String filtersJSON, boolean isCurrentCorrection, boolean sopDebug) throws Exception {

		Date d1 = null;
		Date d2 = null;

		// Invoice Lookup
		if(sopDebug) {
			System.out.println("SalesHistory.getSalesHistory() input parameters:");
			System.out.println("> invoiceURL: " + invoiceURL);
			System.out.println("> curPriceURL: " + curPriceURL);
			System.out.println("> histPriceURL: " + histPriceURL);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> filtersJSON: " + filtersJSON);
			System.out.println("> isCurrentCorrection: " + isCurrentCorrection);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\nSalesHistory.getSalesHistory() Start invoiceLookup API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		}
		this.salesHistory = Invoice.lookupInvoices(invoiceURL, httpMethod, sslAlias, filtersJSON, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End invoiceLookup API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("Total invoiceLookup API time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println("resp Complex object from json file with number of records: " + (this.salesHistory != null ? this.salesHistory.numberOfInvoiceLines() : 0));
		}

		// Current Price
		String currentPriceReqJSON = prepCurrentPriceReq(salesHistory, 0, 100000);
		if(sopDebug) {
			System.out.println("SalesHistory.getSalesHistory() currentPriceReqJSON:" + currentPriceReqJSON);
			d1 = new Date();
			System.out.println("SalesHistory.getSalesHistory() Start currentPrice API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		}
		CurrentPriceResp currentPriceLookupResp = CurrentPrice.getCurrentPrices(curPriceURL, httpMethod, sslAlias, currentPriceReqJSON, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End currentPrice API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("Total currentPrice API time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println("SalesHistory.getSalesHistory() currentPriceLookupResp no. of rows:" + currentPriceLookupResp.getCurrentPriceResp().length);
		}
		if (currentPriceLookupResp != null && currentPriceLookupResp.getCurrentPriceResp() != null && currentPriceLookupResp.getCurrentPriceResp().length > 0) {
			mergeResponse(currentPriceLookupResp, isCurrentCorrection, sopDebug);
		}
		if(sopDebug) {
			CorrectionRow[] debugRows = salesHistory.getInvoiceLookupResp();
			System.out.println("SalesHistory.getSalesHistory() response merged: " + (debugRows != null ? debugRows.length : -1));
			TWList twDebugRows = salesHistory.getTwCorrectionRows();
			System.out.println("SalesHistory.getSalesHistory().getTwCorrectionRows(): " + (twDebugRows != null ? twDebugRows.getArraySize() : -1));
		}
		return salesHistory.getTwCorrectionRows();
	}
	
	private void mergeResponse(CurrentPriceResp currentPriceLookupResp, boolean isCurrentCorrection, boolean sopDebug) throws Exception {
		CorrectionRow[] corrRows = salesHistory.getInvoiceLookupResp();
		CurrentPriceRow[] cpRows = currentPriceLookupResp.getCurrentPriceResp();
		boolean corrRowHydrated = false;
		if (cpRows != null && cpRows.length > 0) {
			int i, j, k;
			for (i = 0; i < corrRows.length; i++) {
				corrRowHydrated = false;
				for (j = 0; j < cpRows.length; j++) {
					CurrentPriceMaterial[] materials = cpRows[j].getMaterials();
					for (k = 0; materials != null && k < materials.length; k++) {
						/*System.out.println("COMPARISON: " + ((corrRows[i].getCustomerId().equals(cpRows[j].getCustomerId())) && (corrRows[i].getPricingDate().compareTo(cpRows[j].getPricingDate()) == 0) && (corrRows[i].getMaterialId().equals(materials[k].getMaterialId()))) + 
								"corrRows["+i+"].getCustomerId(): " + corrRows[i].getCustomerId() + 
								"; cpRows["+j+"].getCustomerId(): " + cpRows[j].getCustomerId() + 
								"; corrRows["+i+"].getPricingDate(): " + corrRows[i].getPricingDate() + 
								"; cpRows["+j+"].getPricingDate(): " + cpRows[j].getPricingDate() + 
								"; corrRows["+i+"].getMaterialId(): " + corrRows[i].getMaterialId() + 
								"; materials["+k+"].getMaterialId(): " + materials[k].getMaterialId() + 
								"; compareDates:" + (corrRows[i].getPricingDate().compareTo(cpRows[j].getPricingDate()) == 0));*/
						if ((corrRows[i].getCustomerId().equals(cpRows[j].getCustomerId()))
								&& (corrRows[i].getPricingDate().compareTo(cpRows[j].getPricingDate()) == 0)
								&& (corrRows[i].getMaterialId().equals(materials[k].getMaterialId()))) {
							if (sopDebug) System.out.println("SalesHistory.mergeResponse - hydrateCurrentValuesOfCorrectionRow: (" + corrRows[i] + ", " + materials[k] + "," + isCurrentCorrection + ")");
							hydrateCurrentValuesOfCorrectionRow(corrRows[i], materials[k], isCurrentCorrection);
							corrRowHydrated = true;
							break;
						}
					}
					if (corrRowHydrated) break;
				}
			}	
		}
		salesHistory.setInvoiceLookupResp(corrRows);
	}
	
	private void hydrateCurrentValuesOfCorrectionRow(CorrectionRow correctionRow, CurrentPriceMaterial cpMaterial, boolean isCurrentCorrection) {
		correctionRow.setCurSellCd(cpMaterial.getCurSellCd());
		correctionRow.setCurNoChargeBack(cpMaterial.getCurNoChargeBack());
	    correctionRow.setCurActivePrice(cpMaterial.getCurActivePrice());
	    correctionRow.setCurLead(cpMaterial.getCurLead());
	    correctionRow.setCurConRef(cpMaterial.getCurConRef());
	    correctionRow.setCurCbRef(cpMaterial.getCurCbRef());
	    correctionRow.setCurContCogPer(cpMaterial.getCurContCogPer());
	    correctionRow.setCurItemVarPer(cpMaterial.getCurItemVarPer());
	    correctionRow.setCurListPrice(cpMaterial.getCurListPrice());
	    correctionRow.setCurWac(cpMaterial.getCurWac());
	    correctionRow.setCurBid(cpMaterial.getCurBid());
	    correctionRow.setCurWacCogPer(cpMaterial.getCurWacCogPer());
	    correctionRow.setCurItemMkUpPer(cpMaterial.getCurItemMkUpPer());
	    correctionRow.setCurAwp(cpMaterial.getCurAwp());
	    correctionRow.setCurPrice(cpMaterial.getCurPrice());
		if (isCurrentCorrection == true) {	// If correction is to be based on current values then new values will be same as current values
			correctionRow.setNewSellCd(cpMaterial.getCurSellCd());
			correctionRow.setNewNoChargeBack(cpMaterial.getCurNoChargeBack());
		    correctionRow.setNewActivePrice(cpMaterial.getCurActivePrice());
		    correctionRow.setNewLead(cpMaterial.getCurLead());
		    correctionRow.setNewConRef(cpMaterial.getCurConRef());
		    correctionRow.setNewCbRef(cpMaterial.getCurCbRef());
		    correctionRow.setNewContCogPer(cpMaterial.getCurContCogPer());
		    correctionRow.setNewItemVarPer(cpMaterial.getCurItemVarPer());
		    correctionRow.setNewListPrice(cpMaterial.getCurListPrice());
		    correctionRow.setNewWac(cpMaterial.getCurWac());
		    correctionRow.setNewBid(cpMaterial.getCurBid());
		    correctionRow.setNewWacCogPer(cpMaterial.getCurWacCogPer());
		    correctionRow.setNewItemMkUpPer(cpMaterial.getCurItemMkUpPer());
		    correctionRow.setNewAwp(cpMaterial.getCurAwp());
		    correctionRow.setNewPrice(cpMaterial.getCurPrice());
		}
	}

	private static TreeMap<NameValuePair<String, String>, TreeMap<String, String>> bucketizePriceMap(CorrectionRow[] invoices) {
		TreeMap<NameValuePair<String, String>, TreeMap<String, String>> priceMap = new TreeMap<NameValuePair<String, String>, TreeMap<String, String>>();
		//System.out.println("TestRow[] invoices.length: " + invoices.length);
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (; i < invoices.length; i++) {
			//String key = invoices[i].getCustomerId() + "|" + sdf.format(invoices[i].getPricingDate());
			NameValuePair<String, String> key = new NameValuePair<String, String>(invoices[i].getCustomerId(), sdf.format(invoices[i].getPricingDate()));
			String value = invoices[i].getMaterialId();
			//System.out.println("NameValuePair[" + i + "]: " + key + ", materialId[" + i + "]: " + value);
			
			TreeMap<String, String> materialList = priceMap.get(key);
			//System.out.print("materialList[" + i + "] before: " + materialList);
			
			if (materialList == null) { // Key not in TreeMap - new key found
				materialList = new TreeMap<String, String>();
				materialList.put(value, value);	// First material in list
				priceMap.put(key, materialList);
			}
			else { 	// Key already exists in TreeMap
				materialList.put(value, value);
			}
			//System.out.println("  after: " + materialList);
			////System.out.println("");
		}
		return priceMap;
	}
	
	static String prepCurrentPriceReq(SalesHistoryResp salesHistoryResp, int startIndex, int endIndex) {
		return prepPriceReq(salesHistoryResp, "currentPriceReq", startIndex, endIndex); 
		//TODO: Get with Archit to use camelCase for container name - change CurrentPriceReq to currentPriceReq
	}
	//TODO: Uncomment when implementing historical price lookup
	static String prepHistoricalPriceReq(SalesHistoryResp salesHistoryResp, int startIndex, int endIndex) {
		return prepPriceReq(salesHistoryResp, "historicalPriceReq", startIndex, endIndex);
	}


	private static String prepPriceReq(SalesHistoryResp salesHistoryResp, String containerName, int startIndex, int endIndex) {
		String priceReqJSON = null; 
		//String priceReqJSON = "{\"CurrentPriceReq\":[";
		TreeMap<NameValuePair<String, String>, TreeMap<String, String>> priceMap = bucketizePriceMap(salesHistoryResp.getInvoiceLookupResp());
		
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		List<Object> pricingRequests = new ArrayList<Object>();
		int i = 0;
		for (Map.Entry<NameValuePair<String, String>, TreeMap<String, String>> entry : priceMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			NameValuePair<String, String> custPricingDatePair = entry.getKey();
			if (custPricingDatePair != null && custPricingDatePair.getKey() != null && custPricingDatePair.getValue() != null) {
				pricingReq.put("index", i++);
				pricingReq.put("customerId", custPricingDatePair.getKey());
				pricingReq.put("pricingDate", custPricingDatePair.getValue());
				if (entry != null && entry.getValue() != null && entry.getValue().values() != null) {
					pricingReq.put("materialIds", entry.getValue().values());		// value in the priceMap entry has materials
				}
				pricingRequests.add(pricingReq);
			}
		}
		priceReqMap.put(containerName, pricingRequests.toArray());
		priceReqMap.put("startIndex", startIndex);
		priceReqMap.put("endIndex", endIndex);
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.setDateFormat(new SimpleDateFormat("yyyyMMdd"));
		
		try {
			priceReqJSON = jacksonMapper.writeValueAsString(priceReqMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			priceReqJSON = "failed";
		}
		return priceReqJSON;
	}
	
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		
		String invoiceURL = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoices";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String filtersJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20170101\",		\"bepTo\": \"20170102\",		\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],		\"storeNumbers\": [\"4153\"],		\"orgIdSequence\": \"05118-50-00643\",		\"invoiceIds\": [\"786599102\"],		\"materials\": [\"1101856\", \"1107689\", \"1102345\"],		\"lead\": \"0000586432\",		\"chainIds\": [\"8082\"]	}],	\"startIndex\": 0,	\"endIndex\": 999,	\"totalNumberOfRecords\": 1500}";
		SalesHistory sh = new SalesHistory();
		try {
			TWList salesHistoryResp = sh.getSalesHistory(invoiceURL, null, null, httpMethod, sslAlias, filtersJSON, true, true);
			System.out.println(salesHistoryResp.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//Test
		prepHistoricalPriceReq(sh.salesHistory, 0, 100000);
	}
	*/
}
