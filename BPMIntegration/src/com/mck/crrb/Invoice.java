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
import teamworks.TWObjectFactory;


/**
 * @author akatre
 *
 */
public class Invoice extends SAPIntegration {

	public static SalesHistoryResp lookupInvoices(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug)  {
		String resp = callAPI(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.lookupInvoices response: " + resp);
		return parseInvoiceLookupResp(resp);
	}

	public static TWList simulatePrice(String url, String httpMethod, String sslAlias, TWList correctionRows, boolean sopDebug) {
		Date d1 = null;
		Date d2 = null;

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
		

		CorrectionRowISO[] invoiceLines = new CorrectionRowISO[correctionRows.getArraySize()];
		for (int i = 0; i < correctionRows.getArraySize(); i++) {
			if(sopDebug) System.out.println("Invoice.simulatePrice() correctionRows.getArrayData(i).getClass().getName(): " + correctionRows.getArrayData(i).getClass().getName());
			invoiceLines[i] = new CorrectionRowISO(correctionRows.getArrayData(i));
			if (invoiceLines[i] != null) {
				System.out.println("Invoice.simulatePrice() invoiceLines[" + i + "]: " + invoiceLines[i].toString());
			}
		}
		String requestJSON = prepSimulatePriceCall(invoiceLines, "priceSimulationReq", 1, Utility.FETCH_SIZE, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePrice requestJSON" + requestJSON);
		String resp = callAPI(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePrice response: " + resp);
		overrideSimulatePriceValues(invoiceLines, parseSimulatePriceResp(resp));
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
	
	public static TWList simulatePriceJSON(String url, String httpMethod, String sslAlias, String correctionRowsJSON, boolean sopDebug) {
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
		CorrectionRowISO[] invoiceLines = null;
		try {
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");			
			//jacksonMapper.setDateFormat(sdf);
			//OffsetDateTime
			
			invoiceLines = jacksonMapper.readValue(correctionRowsJSON, CorrectionRowISO[].class);
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
		String requestJSON = prepSimulatePriceCall(invoiceLines, "priceSimulationReq", 1, Utility.FETCH_SIZE, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End prep of SimulatePrice call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("Total prep time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println("Invoice.simulatePriceJSON() requestJSON: " + requestJSON);
		}

		String resp = callAPI(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePriceJSON() response: " + resp);
		overrideSimulatePriceValues(invoiceLines, parseSimulatePriceResp(resp));
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
	
	public static SalesHistoryResp parseInvoiceLookupResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SalesHistoryResp invoiceLookupResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			invoiceLookupResp = jacksonMapper.readValue(resp, SalesHistoryResp.class);
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
		return invoiceLookupResp;
	}
	
	private static String prepSimulatePriceCall(CorrectionRowISO[] invoiceLines, String containerName, int startIndex, int endIndex, boolean sopDebug) {
		
		//String tst = "{	\"priceSimulationReq\":[    {    	\"index\": 0,        \"customerId\":\"79387\",        \"pricingDate\":\"20170914\",        \"salesOrg\": \"8000\",        \"billType\": \"ZPF2\",        \"materials\":[			{                \"recordKey\": \"7840363909-000001\",            	\"materialId\": \"1763549\",            	\"rebillQty\": \"2.000\",                \"uom\": \"KAR\",                \"dc\": \"8110\",                \"newSellCd\": \"1\",                \"newNoChargeBack\": \"N\",                \"newActivePrice\": \"YCON\",                \"newLead\": \"0000181126\",                \"newConRef\": \"SG-WEGMANS\",                \"newCbRef\": \"SG-WEGMANS\",                \"newContCogPer\": \"-2.50\",                \"newItemVarPer\": \"3.00\",                \"newListPrice\": \"435.39\",                \"newWac\": \"435.39\",                \"newBid\": \"64.65\",                \"newItemMkUpPer\": \"1.00\",                \"newAwp\": \"608.93\",                \"newPrice\": \"120.70\"            }        ]    }]}";
		String simulatePriceReqJSON = null; 
		Map<String, Object> priceReqMap = new HashMap<String, Object>();
		
		TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>> priceMap = bucketizePriceMap(invoiceLines);
		List<Object> pricingRequests = new ArrayList<Object>();
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (Map.Entry<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>> entry : priceMap.entrySet()) {
			Map<String, Object> pricingReq = new HashMap<String, Object>();
			SimulatePriceRowHeader simulatePriceRowHeader = entry.getKey();
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
	
	static TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>> bucketizePriceMap(CorrectionRowISO[] invoiceLines) {
		TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>> priceMap = new TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>>();
		//System.out.println("TestRow[] invoiceLines.length: " + invoiceLines.length);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < invoiceLines.length; i++) {
			//NameValuePair<String, String> key = new NameValuePair<String, String>(invoiceLines[i].getCustomerId(), sdf.format(invoiceLines[i].getPricingDate()));

			//Hydrate key as SimulatePriceRowHeader
			SimulatePriceRowHeader headerKey = hydrateSimulatePriceRowHeader(invoiceLines[i], i); //new SimulatePriceRowHeader(invoiceLines[i].getCustomerId(), sdf.format(invoiceLines[i].getPricingDate()), ...);
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() invoiceLines[" + i + "].headerKey - customerId: " + headerKey.getCustomerId() + ", pricingDate: " + sdf.format(headerKey.getPricingDate()));
 			
			//Hydrate creditRebillMaterial
			CreditRebillMaterial creditRebillMaterial = hydrateCreditRebillMaterial(invoiceLines[i]);
			String materialKey = creditRebillMaterial.getRecordKey();
			
			//TODO: Remove SOP debug statement below
			System.out.println("Invoice.bucketizePriceMap() materialKey[" + i + "]: " + materialKey + ", materialId: " + creditRebillMaterial.getMaterialId());
			
			TreeMap<String, CreditRebillMaterial> materialList = priceMap.get(headerKey);
			//TODO: Remove SOP debug
			System.out.print("materialList[" + i + "] before: " + (materialList != null ? materialList.get(materialKey) : materialList));
			
			if (materialList == null) { // Key not in TreeMap - new key found
				materialList = new TreeMap<String, CreditRebillMaterial>();
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
	
	private static SimulatePriceRowHeader hydrateSimulatePriceRowHeader(CorrectionRowISO invoiceLine, int index){
		SimulatePriceRowHeader headerKey = new SimulatePriceRowHeader();
		headerKey.setIndex(index);
		headerKey.setCustomerId(invoiceLine.getCustomerId());
		headerKey.setPricingDate(invoiceLine.getPricingDate());
		headerKey.setSalesOrg(invoiceLine.getSalesOrg());
		headerKey.setBillType(invoiceLine.getBillType());
		headerKey.setOrderType(invoiceLine.getOrderType());
		return headerKey;
	}
	
	private static CreditRebillMaterial hydrateCreditRebillMaterial(CorrectionRowISO invoiceLine) {
		CreditRebillMaterial creditRebillMaterial = new CreditRebillMaterial();
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
	
	private static SimulatePriceResp parseSimulatePriceResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimulatePriceResp simulatePriceResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			simulatePriceResp = jacksonMapper.readValue(resp, SimulatePriceResp.class);
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
	
	private static CorrectionRowISO[] overrideSimulatePriceValues(CorrectionRowISO[] invoiceLines, SimulatePriceResp simulatePriceResp) {
		if (invoiceLines != null && invoiceLines.length > 0 && simulatePriceResp != null 
				&& simulatePriceResp.getPriceSimulationResp() != null && simulatePriceResp.getPriceSimulationResp().length > 0) {
			SimulatePriceRow[] simulatePriceRows = simulatePriceResp.getPriceSimulationResp();
			for(int i = 0; i < invoiceLines.length; i++) {
				
				for(int j = 0; j < simulatePriceRows.length; j++){
					CreditRebillMaterial[] crMaterials = simulatePriceRows[j].getMaterials();
//					invoiceLines[i].setNewPrice(crMaterials[]);
				}
			}
		}
		return invoiceLines;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String simulatePriceResp = "{    \"priceSimulationResp\": [        {            \"index\": \"0\",            \"customerId\": \"\",            \"salesOrg\": \"\",            \"billType\": \"\",            \"pricingDate\": \"\",            \"materials\": [                {                    \"materialId\": \"\",					\"recordKey\": \"7840363909-000001\",                    \"rebillQty\": \"0.000\",                    \"uom\": \"\",                    \"dc\": \"\",                    \"newLead\": \"\",                    \"newConRef\": \"\",                    \"newNoChargeBack\": \"\",                    \"newCbRef\": \"\",                    \"newSellCd\": \"\",                    \"newActivePrice\": \"\",                    \"newWac\": \"435.39\",                    \"newBid\": \"64.65\",                    \"newContCogPer\": \"-2.50\",                    \"newItemVarPer\": \"3.00\",                    \"newWacCogPer\": \"0.00\",                    \"newItemMkUpPer\": \"1.00\",                    \"newAwp\": \"608.93\",                    \"newOverridePrice\": \"0.00\"                }            ]        }    ],    \"results\": [        {            \"index\": \"0\",            \"status\": \"success\"        }    ]}";
		SimulatePriceResp spr = parseSimulatePriceResp(simulatePriceResp);
		System.out.println("Parsed output: " + spr);
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/1.0.0/crrb/pricesimulation";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String correctionRowsJSON = "[{\"invoiceId\":\"7840771398\",\"invoiceLineItemNum\":\"000046\",\"customerId\":\"112455\",\"customerName\":\"FLOYD MEDICAL CENTER PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":1,\"retQty\":0,\"crQty\":0,\"rebillQty\":1,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8148\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"938\",\"chainName\":\"VHA PHS PRIME VENDOR\",\"groupId\":\"0360\",\"groupName\":\"VHA\",\"subgroupId\":\"000136\",\"subgroupName\":\"PARTNERS COOPERATIVE\",\"reasonCode\":null,\"poNumber\":\"MK201711155     00\",\"oldPrice\":5.06,\"curPrice\":4.89,\"newPrice\":4.89,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-7.199999809265137,\"curContCogPer\":-7.199999809265137,\"newContCogPer\":-7.199999809265137,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8320FGEHAT\",\"orgVendorAccAmt\":0,\"deaNum\":\"AF1176735\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":5.06,\"totChbk\":429.94,\"gxcb\":0,\"street\":\"P.O. BOX 233\",\"city\":\"ROME\",\"region\":\"GA\",\"postalCode\":\"30162\",\"country\":\"US\",\"hin\":\"381700JF3\"},{\"invoiceId\":\"7841758403\",\"invoiceLineItemNum\":\"000005\",\"customerId\":\"837192\",\"customerName\":\"ST JOHN 23 MILE SC    PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":2,\"retQty\":0,\"crQty\":0,\"rebillQty\":2,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"PYXIS112117     00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8326FGEHDT\",\"orgVendorAccAmt\":8598.8,\"deaNum\":\"FS0770099\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":10.24,\"totChbk\":859.88,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"G4B66QXF3\"},{\"invoiceId\":\"7841539082\",\"invoiceLineItemNum\":\"000006\",\"customerId\":\"334231\",\"customerName\":\"ST JOHN ST CLAIR PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":4,\"retQty\":0,\"crQty\":0,\"rebillQty\":4,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"1120170751      00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8325FGEHDT\",\"orgVendorAccAmt\":20637.12,\"deaNum\":\"BS4050972\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":20.48,\"totChbk\":1719.76,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"D3VXLMFF2\"}]}{\"items\":[{\"invoiceId\":\"7840771398\",\"invoiceLineItemNum\":\"000046\",\"customerId\":\"112455\",\"customerName\":\"FLOYD MEDICAL CENTER PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":1,\"retQty\":0,\"crQty\":0,\"rebillQty\":1,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8148\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"938\",\"chainName\":\"VHA PHS PRIME VENDOR\",\"groupId\":\"0360\",\"groupName\":\"VHA\",\"subgroupId\":\"000136\",\"subgroupName\":\"PARTNERS COOPERATIVE\",\"reasonCode\":null,\"poNumber\":\"MK201711155     00\",\"oldPrice\":5.06,\"curPrice\":4.89,\"newPrice\":4.89,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-7.199999809265137,\"curContCogPer\":-7.199999809265137,\"newContCogPer\":-7.199999809265137,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8320FGEHAT\",\"orgVendorAccAmt\":0,\"deaNum\":\"AF1176735\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":5.06,\"totChbk\":429.94,\"gxcb\":0,\"street\":\"P.O. BOX 233\",\"city\":\"ROME\",\"region\":\"GA\",\"postalCode\":\"30162\",\"country\":\"US\",\"hin\":\"381700JF3\"},{\"invoiceId\":\"7841758403\",\"invoiceLineItemNum\":\"000005\",\"customerId\":\"837192\",\"customerName\":\"ST JOHN 23 MILE SC    PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":2,\"retQty\":0,\"crQty\":0,\"rebillQty\":2,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"PYXIS112117     00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8326FGEHDT\",\"orgVendorAccAmt\":8598.8,\"deaNum\":\"FS0770099\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":10.24,\"totChbk\":859.88,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"G4B66QXF3\"},{\"invoiceId\":\"7841539082\",\"invoiceLineItemNum\":\"000006\",\"customerId\":\"334231\",\"customerName\":\"ST JOHN ST CLAIR PHS\",\"materialId\":\"1763549\",\"materialName\":\"OMNIPAQUE VIAL 240MG 10ML   10\",\"pricingDate\":\"2016-11-01T00:00:00Z\",\"supplierId\":\"32836\",\"supplierName\":\"GE HEALTHCARE\",\"billQty\":4,\"retQty\":0,\"crQty\":0,\"rebillQty\":4,\"uom\":\"KAR\",\"createdOn\":\"2016-11-01T00:00:00Z\",\"dc\":\"8132\",\"ndcUpc\":\"N00407141210\",\"billType\":\"ZPF2\",\"chainId\":\"734\",\"chainName\":\"ASCENSION PRIME VENDOR\",\"groupId\":\"0577\",\"groupName\":\"ASCENSION HEALTH\",\"subgroupId\":\"000024\",\"subgroupName\":\"DETJOH\",\"reasonCode\":null,\"poNumber\":\"1120170751      00\",\"oldPrice\":5.12,\"curPrice\":4.95,\"newPrice\":4.95,\"oldWac\":435.39,\"curWac\":435.39,\"newWac\":435.39,\"oldBid\":5.45,\"curBid\":5.27,\"newBid\":5.27,\"oldLead\":\"178018\",\"curLead\":\"178018\",\"newLead\":\"178018\",\"oldConRef\":\"A01844-3\",\"curConRef\":\"A01844-3\",\"newConRef\":\"A01844-3\",\"oldCbRef\":\"A01844-3\",\"curCbRef\":\"A01844-3\",\"newCbRef\":\"A01844-3\",\"oldContCogPer\":-6,\"curContCogPer\":-6,\"newContCogPer\":-6,\"oldItemVarPer\":0,\"curItemVarPer\":0,\"newItemVarPer\":0,\"oldWacCogPer\":0,\"curWacCogPer\":0,\"newWacCogPer\":0,\"oldItemMkUpPer\":0,\"curItemMkUpPer\":0,\"newItemMkUpPer\":0,\"oldChargeBack\":429.94,\"curChargeBack\":430.12,\"newChargeBack\":430.12,\"oldSellCd\":\"2\",\"curSellCd\":\"2\",\"newSellCd\":\"2\",\"oldNoChargeBack\":\"\",\"curNoChargeBack\":\"\",\"newNoChargeBack\":\"\",\"oldActivePrice\":\"YCON\",\"curActivePrice\":\"YCON\",\"newActivePrice\":\"YCON\",\"oldSsf\":0,\"curSsf\":0,\"newSsf\":0,\"oldSf\":0,\"curSf\":0,\"newSf\":0,\"oldListPrice\":435.39,\"curListPrice\":435.39,\"newListPrice\":435.39,\"oldAwp\":608.93,\"curAwp\":608.93,\"newAwp\":608.93,\"oldOverridePrice\":0,\"curOverridePrice\":0,\"newOverridePrice\":0,\"orgDbtMemoId\":\"8325FGEHDT\",\"orgVendorAccAmt\":20637.12,\"deaNum\":\"BS4050972\",\"salesOrg\":\"8000\",\"orderType\":\"ZSOR\",\"netBill\":\"\",\"sellPriceExt\":20.48,\"totChbk\":1719.76,\"gxcb\":0,\"street\":\"PO BOX 33902\",\"city\":\"INDIANAPOLIS\",\"region\":\"IN\",\"postalCode\":\"46203\",\"country\":\"US\",\"hin\":\"D3VXLMFF2\"}]";
		
		simulatePriceJSON(url, httpMethod, sslAlias, correctionRowsJSON, true);
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
	}
	
	//@VisibleForTesting
	public static TWList testLookupInvoices(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws IOException, JsonMappingException, JsonParseException {
		String resp = callAPI(url, httpMethod, sslAlias, requestJSON, sopDebug);
		//System.out.println("testLookupInvoices() response: " + resp);
		return parseInvoiceLookupResp(resp).getTwCorrectionRows();
		/*ObjectMapper jacksonMapper = new ObjectMapper();
		//jacksonMapper.configure(
		//	    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			//String jsonTestObject = "{ \"invoiceLookupResp\": [  {   \"oldChargeBack\": 3.3,   \"deaNum\": \"BA2050538\",   \"chainId\": \"184\",   \"billQty\": 1,   \"oldWacCogPer\": 0,   \"oldWac\": 22,   \"sellPriceExt\": 17.5,   \"totChbk\": 3.3,   \"retQty\": 0,   \"ndcUpc\": \"N67457042612\",   \"oldLead\": \"00586432\",   \"oldItemMkUpPer\": 0,   \"invoiceId\": \"7788900280\",   \"groupId\": \"0360\",   \"oldAwp\": 36,   \"uom\": \"KAR\",   \"netBill\": \"\",   \"salesOrg\": \"8000\",   \"createdOn\": \"20170123\",   \"customerId\": \"0000040769\",   \"oldPrice\": 17.5,   \"oldBid\": 18.7,   \"poNumber\": \"AHIP-65300-0123100\",   \"subgroupId\": \"\",   \"oldSellCd\": \"2\",   \"oldContCogPer\": -6.4,   \"pricingDate\": \"20170123\",   \"billType\": \"ZPF2\",   \"chainName\": \"VOLUNTARY HOSP OF AMER\",   \"groupName\": \"VHA\",   \"orderType\": \"ZSOR\",   \"gxcb\": 0,   \"oldCbRef\": \"PHARMP\",   \"invoiceLineItemNum\": \"000018\",   \"oldNoChargeBack\": \"\",   \"orgDbtMemoId\": \"7024SMIIEV\",   \"materialName\": \"HALOP LAC SDV 5MG/ML MYL  25\",   \"materialId\": \"000000000002579258\",   \"orgVendorAccAmt\": 19.8,   \"oldSf\": 0,   \"crQty\": 0,   \"oldActivePrice\": \"YCON\",   \"oldSsf\": 0,   \"customerName\": \"ALLENMORE HOSP PHCY\",   \"dc\": \"8128\",   \"oldConRef\": \"PHARMP\",   \"rebillQty\": 1,   \"oldListPrice\": 24.26,   \"supplierId\": \"\",   \"supplierName\": \"\"  },  {   \"oldChargeBack\": 3.3,   \"deaNum\": \"BB6894251\",   \"chainId\": \"045\",   \"billQty\": 1,   \"oldWacCogPer\": 0,   \"oldWac\": 22,   \"sellPriceExt\": 17.39,   \"totChbk\": 3.3,   \"retQty\": 0,   \"ndcUpc\": \"N67457042612\",   \"oldLead\": \"00586432\",   \"oldItemMkUpPer\": 0,   \"invoiceId\": \"7789371577\",   \"groupId\": \"0230\",   \"oldAwp\": 36,   \"uom\": \"KAR\",   \"netBill\": \"\",   \"salesOrg\": \"8000\",   \"createdOn\": \"20170125\",   \"customerId\": \"0000242616\",   \"oldPrice\": 17.39,   \"oldBid\": 18.7,   \"poNumber\": \"012517HEART  00\",   \"subgroupId\": \"000008\",   \"oldSellCd\": \"2\",   \"oldContCogPer\": -7.03,   \"pricingDate\": \"20170125\",   \"billType\": \"ZPF2\",   \"chainName\": \"BANNER HEALTH\",   \"groupName\": \"PREMIER\",   \"orderType\": \"ZSOR\",   \"gxcb\": 0,   \"oldCbRef\": \"PHARMP\",   \"invoiceLineItemNum\": \"000001\",   \"oldNoChargeBack\": \"\",   \"orgDbtMemoId\": \"7026SMIIPX\",   \"materialName\": \"HALOP LAC SDV 5MG/ML MYL  25\",   \"materialId\": \"000000000002579258\",   \"orgVendorAccAmt\": 23.1,   \"oldSf\": 0,   \"crQty\": 0,   \"oldActivePrice\": \"YCON\",   \"oldSsf\": 0,   \"customerName\": \"BANNER HEART HOSPITAL\",   \"subgroupName\": \"BANNER HEALTH\",   \"dc\": \"8170\",   \"oldConRef\": \"PHARMP\",   \"rebillQty\": 1,   \"oldListPrice\": 24.26,   \"supplierId\": \"\",   \"supplierName\": \"\"  },  {   \"oldChargeBack\": 0,   \"deaNum\": \"AJ4147357\",   \"chainId\": \"830\",   \"billQty\": 6,   \"oldWacCogPer\": -5.86,   \"oldWac\": 25,   \"sellPriceExt\": 141.24,   \"totChbk\": 0,   \"retQty\": 0,   \"ndcUpc\": \"N25021078104\",   \"oldLead\": \"\",   \"oldItemMkUpPer\": 0,   \"invoiceId\": \"7806123021\",   \"groupId\": \"0230\",   \"oldAwp\": 30,   \"uom\": \"EA\",   \"netBill\": \"\",   \"salesOrg\": \"8000\",   \"createdOn\": \"20170502\",   \"customerId\": \"0000233303\",   \"oldPrice\": 23.54,   \"oldBid\": 0,   \"poNumber\": \"57999315100  00\",   \"subgroupId\": \"000086\",   \"oldSellCd\": \"N\",   \"oldContCogPer\": 0,   \"pricingDate\": \"20170502\",   \"billType\": \"ZPF2\",   \"chainName\": \"NOBILANT\",   \"groupName\": \"PREMIER\",   \"orderType\": \"ZSOR\",   \"gxcb\": 0,   \"oldCbRef\": \"\",   \"invoiceLineItemNum\": \"000008\",   \"oldNoChargeBack\": \"X\",   \"materialName\": \"GRANIS MDV 1MG/ML   SAG 4ML\",   \"materialId\": \"000000000001750843\",   \"oldSf\": 0,   \"crQty\": 0,   \"oldActivePrice\": \"YCOS\",   \"oldSsf\": 0,   \"customerName\": \"JOHNS HOPKINS PEDS GPO\",   \"subgroupName\": \"HMPG\",   \"dc\": \"8120\",   \"oldConRef\": \"\",   \"rebillQty\": 6,   \"oldListPrice\": 27.56,   \"supplierId\": \"\",   \"supplierName\": \"\"  }   ], \"results\": [{  \"index\": \"1\",  \"status\": \"success\"} ], \"startIndex\": 0, \"endIndex\": 2, \"totalNumberOfRecords\": 3}";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);

			Date d1 = new Date();
			System.out.println("\r\nStart file parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
			
			//BWHanaResponse respInFile = jacksonMapper.readValue(new File("C:\\Users\\akatre\\eclipse-workspace\\BPMIntegrationTest\\src\\com\\mck\\crrb\\bwHanaResponse.json"), BWHanaResponse.class);
			//SalesHistoryResp respInFile = jacksonMapper.readValue(new File("C:\\Users\\akatre\\eclipse-workspace\\BPMIntegrationTest\\src\\com\\mck\\crrb\\crrbInvoicesResponse.json"), SalesHistoryResp.class);
			//SalesHistoryResp resp = jacksonMapper.readValue(jsonTestObject, SalesHistoryResp.class);
			SalesHistoryResp resp = jacksonMapper.readValue(jsonTestObject, SalesHistoryResp.class);
			Date d2 = new Date();
			System.out.println("End file parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("resp Complex object from json file with number of records: " + resp.numberOfInvoiceLines());
			System.out.println("Total parsing time (ms): " + (d2.getTime() - d1.getTime()));
			
			return resp.getTwCorrectionRows();

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}*/
	}
	
	
	/*
	public static String callInvoiceLookupAPI(String url, String method, String sslAlias, String filtersJSON)  {
	    String rawInvoiceLookupResp = null;
	    Properties sslProps = null;
	    //OLD: url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoicelookup/hana";
	    //url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoices";
	    //method = "POST";
	    //sslAlias = "CellDefaultSSLSettings";
	    //OLD: filtersJSON = "{\"INVOICELOOKUPREQ\": [{\"orgIdSequence\": \"05118-50-00643\",\"lead\": \"0000586432\",\"bepFrom\": \"20170101\",\"bepTo\": \"20170120\",\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],\"materials\": [\"1101856\", \"1107689\", \"1102345\"]}]}";
	    //filtersJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20170101\",		\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],		\"storeNumbers\": [\"4153\"],		\"orgIdSequence\": \"05118-50-00643\",		\"invoiceIds\": [\"786599102\"],		\"materials\": [\"1101856\", \"1107689\", \"1102345\"],		\"lead\": \"0000586432\",		\"bepTo\": \"20170102\",		\"chainIds\": [\"8082\"]	}],	\"startIndex\": 0,	\"endIndex\": 999,	\"totalNumberOfRecords\": 1500}";
	    try {
	        System.out.println("Inside Try");
			com.ibm.websphere.ssl.JSSEHelper jsseHelper = com.ibm.websphere.ssl.JSSEHelper.getInstance();
			try {
	            sslProps = jsseHelper.getProperties(sslAlias);
	            System.out.println("PROPERTIES IN TRY, AFTER GET API CALL: " + sslProps);
			} 
			catch (SSLException e) {
                e.printStackTrace();
			}
			System.out.println("PROPERTIES: " + sslProps);
			System.out.println("PROPERTIES ON THREAD: " + jsseHelper.getSSLPropertiesOnThread());
			System.out.println(sslProps.toString());
			jsseHelper.setSSLPropertiesOnThread(sslProps); 
			
			URL restUrl = new URL(url);                           
			HttpsURLConnection connection = (HttpsURLConnection) restUrl.openConnection();
			System.out.println("AFTER OPEN CONNECTION");
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(filtersJSON); 
			writer.close();
			System.out.println("AFTER WRITER.CLOSE");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
			        jsonString.append(line);
			}
			if(jsonString != null) {
				rawInvoiceLookupResp = jsonString.toString();
			}
			jsseHelper.setSSLPropertiesOnThread(null);
			connection.disconnect();
		}
		catch(IOException e) {
		    rawInvoiceLookupResp = e.getMessage();
		    System.out.println(e.getMessage());
		}
		return rawInvoiceLookupResp;
	}
	*/

}
