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
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import teamworks.TWList;


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
	
	public static SalesHistoryResp parseInvoiceLookupResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
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
	
	private static TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>> bucketizePriceMap(CorrectionRow[] invoices) {
		TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>> priceMap = new TreeMap<SimulatePriceRowHeader, TreeMap<String, CreditRebillMaterial>>();
		//System.out.println("TestRow[] invoices.length: " + invoices.length);
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (; i < invoices.length; i++) {
			//String key = invoices[i].getCustomerId() + "|" + sdf.format(invoices[i].getPricingDate());
			NameValuePair<String, String> key = new NameValuePair<String, String>(invoices[i].getCustomerId(), sdf.format(invoices[i].getPricingDate()));
			String value = invoices[i].getMaterialId();
			//System.out.println("NameValuePair[" + i + "]: " + key + ", materialId[" + i + "]: " + value);
			
			SimulatePriceRowHeader materialList = priceMap.get(key);
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
	
	public static SimulatePriceResp simulatePrice(String url, String httpMethod, String sslAlias, TWList correctionRows, boolean sopDebug) {
		String requestJSON = prepSimulatePriceCall(correctionRows, "priceSimulationReq", 1, 10000);
		if (sopDebug) System.out.println("Invoice.simulatePrice requestJSON" + requestJSON);
		String resp = callAPI(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) System.out.println("Invoice.simulatePrice response: " + resp);
		return parsesimulatePriceResp(resp);
	}
	
	private static String prepSimulatePriceCall(TWList correctionRows, String containerName, int startIndex, int endIndex) {
		//String tst = "{	\"priceSimulationReq\":[    {    	\"index\": 0,        \"recordKey\":\"7840363909-000001\",        \"customerId\":\"79387\",        \"pricingDate\":\"20170914\",        \"salesOrg\": \"8000\",        \"orderType\": \"ZPF2\",        \"materials\":[			{                \"materialId\": \"1763549\",            	\"rebillQty\": \"2.000\",                \"uom\": \"KAR\",                \"dc\": \"8110\",                \"newSellCd\": \"1\",                \"newNoChargeBack\": \"N\",                \"newActivePrice\": \"YCON\",                \"newLead\": \"0000181126\",                \"newConRef\": \"SG-WEGMANS\",                \"newCbRef\": \"SG-WEGMANS\",                \"newContCogPer\": \"-2.50\",                \"newItemVarPer\": \"3.00\",                \"newListPrice\": \"435.39\",                \"newWac\": \"435.39\",                \"newBid\": \"64.65\",                \"newItemMkUpPer\": \"1.00\",                \"newAwp\": \"608.93\",                \"newPrice\": \"120.70\"            }        ]    }]}";
		String simulatePriceReqJSON = null; 
		CorrectionRow[] invoices = null;
		for (int i = 0; i < correctionRows.getArraySize(); i++) {
			invoices[i] = (CorrectionRow) correctionRows.getArrayData(i);
		}
		TreeMap<NameValuePair<String, String>, TreeMap<String, String>> priceMap = bucketizePriceMap(invoices);
		
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
			simulatePriceReqJSON = jacksonMapper.writeValueAsString(priceReqMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			simulatePriceReqJSON = "failed";
		}
		return simulatePriceReqJSON;
	}
	
	private static SimulatePriceResp parsesimulatePriceResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		SimulatePriceResp simulatePriceresp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			simulatePriceresp = jacksonMapper.readValue(resp, SimulatePriceResp.class);
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
		return simulatePriceresp;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String simulatePriceResp = "{    \"priceSimulationResp\": [        {            \"index\": \"0\",            \"customerId\": \"\",            \"salesOrg\": \"\",            \"billType\": \"\",            \"pricingDate\": \"\",            \"materials\": [                {                    \"materialId\": \"\",					\"recordKey\": \"7840363909-000001\",                    \"rebillQty\": \"0.000\",                    \"uom\": \"\",                    \"dc\": \"\",                    \"newLead\": \"\",                    \"newConRef\": \"\",                    \"newNoChargeBack\": \"\",                    \"newCbRef\": \"\",                    \"newSellCd\": \"\",                    \"newActivePrice\": \"\",                    \"newWac\": \"435.39\",                    \"newBid\": \"64.65\",                    \"newContCogPer\": \"-2.50\",                    \"newItemVarPer\": \"3.00\",                    \"newWacCogPer\": \"0.00\",                    \"newItemMkUpPer\": \"1.00\",                    \"newAwp\": \"608.93\",                    \"newOverridePrice\": \"0.00\"                }            ]        }    ],    \"results\": [        {            \"index\": \"0\",            \"status\": \"success\"        }    ]}";
		SimulatePriceResp spr = parsesimulatePriceResp(simulatePriceResp);
		System.out.println("Parsed output: " + spr);
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
