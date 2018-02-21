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
	public static final String API_DATE_FORMAT = "yyyyMMdd";
	public static final String SHORT_DASHED_DATE_FORMAT = "yyyy-MM-dd";
	public static final String LONG_DASHED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	public static final String HTTP_NOT_OK = "HTTP_RESPONSE_NOT_OK";
	
	/* 
	 * Final template method providing unalterable boiler plate sequence of calls
	 */
	public final TWObject process(String url, String httpMethod, String sslAlias, String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		Date d1 = null;
		Date d2 = null;
		String className = this.getClass().getName();
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_DASHED_DATE_FORMAT);
		//if(sopDebug) {
			System.out.println(className + ".process() input parameters:");
			System.out.println("> url: " + url);
			System.out.println("> httpMethod: " + httpMethod);
			System.out.println("> sslAlias: " + sslAlias);
			System.out.println("> requestJSON: " + requestJSON);
			System.out.println("> sopDebug: " + sopDebug);

			d1 = new Date();
			System.out.println("\r\n" + className + ".process() Start prep of call: " + sdf.format(d1));
		//}
		//#1 Prepare request
		requestJSON = prepRequest(requestJSON, reqHeader, sopDebug);
		//if(sopDebug) {
			d2 = new Date();
			System.out.println("End prep request: " + sdf.format(d2));
			System.out.println("Total prep time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println(className + ".process() requestJSON: " + requestJSON);
		//}
		//#2 Call the API
		_HttpResponse httpResp = new _HttpResponse();
		String rawResp = call(url, httpMethod, sslAlias, requestJSON, httpResp, sopDebug);
		//if (sopDebug) {
			d1 = new Date();
			System.out.println("\r\n" + className + ".process() End call(): " + sdf.format(d1));
			System.out.println("Total call time (ms): " + (d1.getTime() - d2.getTime()));
			System.out.println(className + ".process() response: " + rawResp);
		//}
		/*
		if (rawResp.equals(HTTP_NOT_OK)) {
			TWObject errorResp = TWObjectFactory.createObject();
			errorResp.setPropertyValue("responseCode", httpResp.getResponseCode());
			errorResp.setPropertyValue("responseMessage", httpResp.getResponseMessage());
			if (sopDebug) System.out.println("_API.process(): " + HTTP_NOT_OK + " sending HTTP response code: " + (String)errorResp.getPropertyValue("responseCode"));
			return errorResp;
		} // else HTTP_OK so continue with parsing...
		*/ 
		//#3 Parse the API response
		TWObject parsedResp = parseResponse(rawResp, httpResp, sopDebug);
		//if(sopDebug) {
			d2 = new Date();
			System.out.println("End parse request: " + sdf.format(d2));
			System.out.println("Total parse time (ms): " + (d2.getTime() - d1.getTime()));
			System.out.println(className + ".process() Is parsedResp null? " + (parsedResp == null));
		//}
		return parsedResp;
	}
	
	static String call(String url, String httpMethod, String sslAlias, String requestJSON, _HttpResponse httpResp, boolean sopDebug)  {
	    String rawResp = null;
        com.ibm.websphere.ssl.JSSEHelper jsseHelper = null;
	    Properties sslProps = null;
	    HttpsURLConnection connection = null;
	    try {
	    	jsseHelper = com.ibm.websphere.ssl.JSSEHelper.getInstance();
			sslProps = jsseHelper.getProperties(sslAlias);
	        jsseHelper.setSSLPropertiesOnThread(sslProps); 
			
			URL restUrl = new URL(url);                           
			connection = (HttpsURLConnection) restUrl.openConnection();
			//if (sopDebug) { 
				System.out.println("_API.call() After restUrl.openConnection."); 
			//}
			connection.setDoOutput(true);
			connection.setRequestMethod(httpMethod);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(requestJSON); 
			writer.close();
			//if (sopDebug) { 
				System.out.println("_API.call() After writing requestJSON.");
			//}
			
			int respCode = connection.getResponseCode();
        	httpResp.setResponseCode(respCode); 
        	httpResp.setResponseMessage(connection.getResponseMessage());
        	//if (sopDebug) { 
        		System.out.println("_API.call httpResponse: "  + httpResp.toString()); 
        	//}
        	
        	/*
			switch (respCode) {
	            case HttpsURLConnection.HTTP_OK:	                
	                if (sopDebug) System.out.println("_API.call(): " + HttpsURLConnection.HTTP_OK + " httpResp: " + httpResp.toString());
	                break; // OK response - clear to proceed with processing
	            default:
	                if (sopDebug) System.out.println("_API.call(): " + HTTP_NOT_OK + " httpResp: " + httpResp.toString());
	                return HTTP_NOT_OK; // Not OK - abort mission
			}
			*/
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
			        jsonString.append(line);
			}
			if(jsonString != null) {
				rawResp = jsonString.toString();
			}
			//if (sopDebug) { 
				System.out.println("_API.call() After reading responseJSON: " + rawResp);
			//}
		}
	    catch (SSLException e) {
	        rawResp = e.getMessage();
		    System.out.println(e.getMessage());
			e.printStackTrace();
		}
		catch(IOException ioe) {
		    rawResp = ioe.getMessage();
		    System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		}
	    finally { //Future Enhancement - Once BPM supports Java 7+ and making sure that HttpsURLConnection implements AutoCloseable use try-with-resource construct instead 
			if (jsseHelper != null) { 
				jsseHelper.setSSLPropertiesOnThread(null);
			}
			if (connection != null) { 
				connection.disconnect();
			}
			//if (sopDebug) { 
				System.out.println("_API.call() resource cleanup complete.");
			//}
	    }
		return rawResp;
	}
	
	/**
	 * Transform the input request object into appropriate request JSON
	 *  
	 * @param requestJSON A JSON string representation of the request object
	 * @param correlationId TODO
	 * 
	 * @return String Transformed JSON string that is needed for this specific API call 
	 */
	abstract String prepRequest(String requestJSON,  TWObject reqHeader, boolean sopDebug) throws Exception;

	/**
	 * Parse the JSON output of the API call into TWObject representation
	 *  
	 * @param rawResp As returned by the API call @see {@link #call(String, String, String, String, boolean)}
	 * 
	 * @return TWObject An object of type TWObject ready to be mapped to a BPM Object with same attributes
	 */
	abstract TWObject parseResponse(String rawResp, _HttpResponse httpResp, boolean sopDebug) throws Exception;
	
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
	
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/crrb/invoices";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String requestJSON = "{	\"invoiceLookupReq\": [{		\"index\": 0,		\"bepFrom\": \"20170101\",		\"bepTo\": \"20170102\",		\"customers\": [\"045333\", \"066742\", \"066903\", \"067386\", \"067708\"],		\"storeNumbers\": [\"4153\"],		\"orgIdSequence\": \"05118-50-00643\",		\"invoiceIds\": [\"786599102\"],		\"materials\": [\"1101856\", \"1107689\", \"1102345\"],		\"lead\": \"0000586432\",		\"chainIds\": [\"8082\"]	}],	\"startIndex\": 0,	\"endIndex\": 999,	\"totalNumberOfRecords\": 1500}";
		
		String rawInvoiceLookupResp = _API.call(url, httpMethod, sslAlias, requestJSON, true);
		System.out.println(rawInvoiceLookupResp);
	}
	*/
}
