/**
 * 
 */
package com.mck.crrb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.ibm.websphere.ssl.SSLException;

import teamworks.TWObject;

/**
 * Abstract API interaction template.<br/>
 * Provides default implementation for calling the API @see {@link #call(String, String, String, String, boolean)}. 
 * Subclasses need to provide implementation for parsing the response from the API call. 
 * 
 * @author akatre
 *
 */
public abstract class _API {
	public static final int FETCH_SIZE = 10000;
	public static final String DATE_FORMAT = "yyyyMMdd";
	
	//final template method providing unalterable boiler plate sequence of calls
	public final TWObject process(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception {
		Date d1 = null;
		Date d2 = null;
		String className = this.getClass().getName();
		if(sopDebug) {
			System.out.println(className + ".process() input parameters:");
			System.out.println("> url: " + url);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> requestJSON: " + requestJSON);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\n" + className + ".process() Start prep of call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		}
		//#1 Prepare request
		requestJSON = prepRequest(requestJSON, sopDebug);
		if(sopDebug) {
			d2 = new Date();
			System.out.println("End prep of SimulatePrice call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
			System.out.println("Total prep time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println(className + ".process() requestJSON: " + requestJSON);
		}
		//#2 Call the API
		String rawResp = call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		if (sopDebug) {
			d1 = new Date();
			System.out.println("\r\n" + className + ".process() End call(): " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
			System.out.println("Total call time (ms): " + (d1.getTime() - d2.getTime()));
			System.out.println(className + ".process() response: " + rawResp);
		}
		//#3 Parse the API response
		TWObject parsedResp = parseResponse(rawResp, sopDebug);
		return parsedResp;
	}
	
	public static String call(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug)  {
	    String rawResp = null;
	    Properties sslProps = null;
	    try {
	        com.ibm.websphere.ssl.JSSEHelper jsseHelper = com.ibm.websphere.ssl.JSSEHelper.getInstance();
			sslProps = jsseHelper.getProperties(sslAlias);
	        jsseHelper.setSSLPropertiesOnThread(sslProps); 
			
			URL restUrl = new URL(url);                           
			HttpsURLConnection connection = (HttpsURLConnection) restUrl.openConnection();
			if (sopDebug) { System.out.println("_API.call() After restUrl.openConnection."); }
			connection.setDoOutput(true);
			connection.setRequestMethod(httpMethod);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(requestJSON); 
			writer.close();
			if (sopDebug) { System.out.println("_API.call() After writing requestJSON.");}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
			        jsonString.append(line);
			}
			if(jsonString != null) {
				rawResp = jsonString.toString();
			}
			jsseHelper.setSSLPropertiesOnThread(null);
			connection.disconnect();
		}
	    catch (SSLException e) {
	        rawResp = e.getMessage();
			e.printStackTrace();
		}
		catch(IOException e) {
		    rawResp = e.getMessage();
		    System.out.println(e.getMessage());
		}
		return rawResp;
	}
	
	/**
	 * Transform the input request object into appropriate request JSON
	 *  
	 * @param requestJSON A JSON string representation of the request object
	 * 
	 * @return String Transformed JSON string that is needed for this specific API call 
	 */
	abstract String prepRequest(String requestJSON, boolean sopDebug) throws Exception;

	/**
	 * Parse the JSON output of the API call into TWObject representation
	 *  
	 * @param rawResp As returned by the API call @see {@link #call(String, String, String, String, boolean)}
	 * 
	 * @return TWObject An object of type TWObject ready to be mapped to a BPM Object with same attributes
	 */
	abstract TWObject parseResponse(String rawResp, boolean sopDebug) throws Exception;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoices";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String requestJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20170101\",		\"bepTo\": \"20170102\",		\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],		\"storeNumbers\": [\"4153\"],		\"orgIdSequence\": \"05118-50-00643\",		\"invoiceIds\": [\"786599102\"],		\"materials\": [\"1101856\", \"1107689\", \"1102345\"],		\"lead\": \"0000586432\",		\"chainIds\": [\"8082\"]	}],	\"startIndex\": 0,	\"endIndex\": 999,	\"totalNumberOfRecords\": 1500}";
		
		String rawInvoiceLookupResp = _API.call(url, httpMethod, sslAlias, requestJSON, true);
		System.out.println(rawInvoiceLookupResp);
	}
	
}
