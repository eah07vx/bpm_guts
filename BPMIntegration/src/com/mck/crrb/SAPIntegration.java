/**
 * 
 */
package com.mck.crrb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.ibm.websphere.ssl.SSLException;

/**
 * @author akatre
 *
 */
public class SAPIntegration {

	public static String callAPI(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug)  {
	    String rawInvoiceLookupResp = null;
	    Properties sslProps = null;
	    try {
	        com.ibm.websphere.ssl.JSSEHelper jsseHelper = com.ibm.websphere.ssl.JSSEHelper.getInstance();
			sslProps = jsseHelper.getProperties(sslAlias);
	        jsseHelper.setSSLPropertiesOnThread(sslProps); 
			
			URL restUrl = new URL(url);                           
			HttpsURLConnection connection = (HttpsURLConnection) restUrl.openConnection();
			if (sopDebug) { System.out.println("SAPIntegration.callAPI() After restUrl.openConnection."); }
			connection.setDoOutput(true);
			connection.setRequestMethod(httpMethod);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(requestJSON); 
			writer.close();
			if (sopDebug) { System.out.println("SAPIntegration.callAPI() After writing requestJSON.");}
			
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
	    catch (SSLException e) {
	        rawInvoiceLookupResp = e.getMessage();
			e.printStackTrace();
		}
		catch(IOException e) {
		    rawInvoiceLookupResp = e.getMessage();
		    System.out.println(e.getMessage());
		}
		return rawInvoiceLookupResp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoices";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String requestJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20170101\",		\"bepTo\": \"20170102\",		\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],		\"storeNumbers\": [\"4153\"],		\"orgIdSequence\": \"05118-50-00643\",		\"invoiceIds\": [\"786599102\"],		\"materials\": [\"1101856\", \"1107689\", \"1102345\"],		\"lead\": \"0000586432\",		\"chainIds\": [\"8082\"]	}],	\"startIndex\": 0,	\"endIndex\": 999,	\"totalNumberOfRecords\": 1500}";
		
		String salesHistoryResp = callAPI(url, httpMethod, sslAlias, requestJSON, true);
		System.out.println(salesHistoryResp);
	}
	
}
