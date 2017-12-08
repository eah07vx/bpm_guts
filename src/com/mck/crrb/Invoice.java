/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
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
		return parseInvoiceLookupResp(resp);
	}
	
	//@VisibleForTesting
	public static TWList lookupInvoices(String filterJSON) throws IOException, JsonMappingException, JsonParseException {
		ObjectMapper jacksonMapper = new ObjectMapper();
		try {
			String jsonTestObject = "{ \"invoiceLookupResp\": [  {   \"oldChargeBack\": 3.3,   \"deaNum\": \"BA2050538\",   \"chainId\": \"184\",   \"billQty\": 1,   \"oldWacCogPer\": 0,   \"oldWac\": 22,   \"sellPriceExt\": 17.5,   \"totChbk\": 3.3,   \"retQty\": 0,   \"ndcUpc\": \"N67457042612\",   \"oldLead\": \"00586432\",   \"oldItemMkUpPer\": 0,   \"invoiceId\": \"7788900280\",   \"groupId\": \"0360\",   \"oldAwp\": 36,   \"uom\": \"KAR\",   \"netBill\": \"\",   \"salesOrg\": \"8000\",   \"createdOn\": \"20170123\",   \"customerId\": \"0000040769\",   \"oldPrice\": 17.5,   \"oldBid\": 18.7,   \"poNumber\": \"AHIP-65300-0123100\",   \"subgroupId\": \"\",   \"oldSellCd\": \"2\",   \"oldContCogPer\": -6.4,   \"pricingDate\": \"20170123\",   \"billType\": \"ZPF2\",   \"chainName\": \"VOLUNTARY HOSP OF AMER\",   \"groupName\": \"VHA\",   \"orderType\": \"ZSOR\",   \"gxcb\": 0,   \"oldCbRef\": \"PHARMP\",   \"invoiceLineItemNum\": \"000018\",   \"oldNoChargeBack\": \"\",   \"orgDbtMemoId\": \"7024SMIIEV\",   \"materialName\": \"HALOP LAC SDV 5MG/ML MYL  25\",   \"materialId\": \"000000000002579258\",   \"orgVendorAccAmt\": 19.8,   \"oldSf\": 0,   \"crQty\": 0,   \"oldActivePrice\": \"YCON\",   \"oldSsf\": 0,   \"customerName\": \"ALLENMORE HOSP PHCY\",   \"dc\": \"8128\",   \"oldConRef\": \"PHARMP\",   \"rebillQty\": 1,   \"oldListPrice\": 24.26,   \"supplierId\": \"\",   \"supplierName\": \"\"  },  {   \"oldChargeBack\": 3.3,   \"deaNum\": \"BB6894251\",   \"chainId\": \"045\",   \"billQty\": 1,   \"oldWacCogPer\": 0,   \"oldWac\": 22,   \"sellPriceExt\": 17.39,   \"totChbk\": 3.3,   \"retQty\": 0,   \"ndcUpc\": \"N67457042612\",   \"oldLead\": \"00586432\",   \"oldItemMkUpPer\": 0,   \"invoiceId\": \"7789371577\",   \"groupId\": \"0230\",   \"oldAwp\": 36,   \"uom\": \"KAR\",   \"netBill\": \"\",   \"salesOrg\": \"8000\",   \"createdOn\": \"20170125\",   \"customerId\": \"0000242616\",   \"oldPrice\": 17.39,   \"oldBid\": 18.7,   \"poNumber\": \"012517HEART  00\",   \"subgroupId\": \"000008\",   \"oldSellCd\": \"2\",   \"oldContCogPer\": -7.03,   \"pricingDate\": \"20170125\",   \"billType\": \"ZPF2\",   \"chainName\": \"BANNER HEALTH\",   \"groupName\": \"PREMIER\",   \"orderType\": \"ZSOR\",   \"gxcb\": 0,   \"oldCbRef\": \"PHARMP\",   \"invoiceLineItemNum\": \"000001\",   \"oldNoChargeBack\": \"\",   \"orgDbtMemoId\": \"7026SMIIPX\",   \"materialName\": \"HALOP LAC SDV 5MG/ML MYL  25\",   \"materialId\": \"000000000002579258\",   \"orgVendorAccAmt\": 23.1,   \"oldSf\": 0,   \"crQty\": 0,   \"oldActivePrice\": \"YCON\",   \"oldSsf\": 0,   \"customerName\": \"BANNER HEART HOSPITAL\",   \"subgroupName\": \"BANNER HEALTH\",   \"dc\": \"8170\",   \"oldConRef\": \"PHARMP\",   \"rebillQty\": 1,   \"oldListPrice\": 24.26,   \"supplierId\": \"\",   \"supplierName\": \"\"  },  {   \"oldChargeBack\": 0,   \"deaNum\": \"AJ4147357\",   \"chainId\": \"830\",   \"billQty\": 6,   \"oldWacCogPer\": -5.86,   \"oldWac\": 25,   \"sellPriceExt\": 141.24,   \"totChbk\": 0,   \"retQty\": 0,   \"ndcUpc\": \"N25021078104\",   \"oldLead\": \"\",   \"oldItemMkUpPer\": 0,   \"invoiceId\": \"7806123021\",   \"groupId\": \"0230\",   \"oldAwp\": 30,   \"uom\": \"EA\",   \"netBill\": \"\",   \"salesOrg\": \"8000\",   \"createdOn\": \"20170502\",   \"customerId\": \"0000233303\",   \"oldPrice\": 23.54,   \"oldBid\": 0,   \"poNumber\": \"57999315100  00\",   \"subgroupId\": \"000086\",   \"oldSellCd\": \"N\",   \"oldContCogPer\": 0,   \"pricingDate\": \"20170502\",   \"billType\": \"ZPF2\",   \"chainName\": \"NOBILANT\",   \"groupName\": \"PREMIER\",   \"orderType\": \"ZSOR\",   \"gxcb\": 0,   \"oldCbRef\": \"\",   \"invoiceLineItemNum\": \"000008\",   \"oldNoChargeBack\": \"X\",   \"materialName\": \"GRANIS MDV 1MG/ML   SAG 4ML\",   \"materialId\": \"000000000001750843\",   \"oldSf\": 0,   \"crQty\": 0,   \"oldActivePrice\": \"YCOS\",   \"oldSsf\": 0,   \"customerName\": \"JOHNS HOPKINS PEDS GPO\",   \"subgroupName\": \"HMPG\",   \"dc\": \"8120\",   \"oldConRef\": \"\",   \"rebillQty\": 6,   \"oldListPrice\": 27.56,   \"supplierId\": \"\",   \"supplierName\": \"\"  }   ], \"results\": [{  \"index\": \"1\",  \"status\": \"success\"} ], \"startIndex\": 0, \"endIndex\": 2, \"totalNumberOfRecords\": 3}";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);

			Date d1 = new Date();
			System.out.println("\r\nStart file parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
			
			//BWHanaResponse respInFile = jacksonMapper.readValue(new File("C:\\Users\\akatre\\eclipse-workspace\\BPMIntegrationTest\\src\\com\\mck\\crrb\\bwHanaResponse.json"), BWHanaResponse.class);
			//SalesHistoryResp respInFile = jacksonMapper.readValue(new File("C:\\Users\\akatre\\eclipse-workspace\\BPMIntegrationTest\\src\\com\\mck\\crrb\\crrbInvoicesResponse.json"), SalesHistoryResp.class);
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
		}
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
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
		System.out.println("rawInvoiceLookupResp length:" + rawInvoiceLookupResp.length()); 
		System.out.println("Total invoiceLookup API time (ms): " + (d2.getTime() - d1.getTime()));
		
		d1 = new Date();
		System.out.println("\r\nStart response parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		invoiceLookupResp = parseInvoiceLookupResp(rawInvoiceLookupResp);
		d2 = new Date();
		System.out.println("End response parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
		
		System.out.println("resp Complex object from json file with number of records: " + invoiceLookupResp.numberOfInvoiceLines());
		System.out.println("Total parsing time (ms): " + (d2.getTime() - d1.getTime()));
	}
	
}
