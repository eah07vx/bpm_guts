/**
 * 
 */
package com.mck.crrb;

import teamworks.TWList;
import teamworks.TWObject;

/**
 * @author akatre
 *
 */
public class AggregateAPI {
	
	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWObject invokeAsync(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception  {
		return new AsyncAPI().process(url, httpMethod, sslAlias, requestJSON, null, sopDebug);
	}
	
	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWObject lookupInvoice(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception  {
		return new Invoice().process(url, httpMethod, sslAlias, requestJSON, null, sopDebug);
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
	public TWObject simulatePrice(String url, String httpMethod, String sslAlias, String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		return new Price().process(url, httpMethod, sslAlias, requestJSON, reqHeader, sopDebug);
	}
	
	/**
	 * Convenience method that does the same job as process method inherited from _API. 
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
	public TWObject submitCreditRebill(String url, String httpMethod, String sslAlias, String requestJSON, TWObject reqHeader, boolean sopDebug) throws Exception {
		return new CreditRebill().process(url, httpMethod, sslAlias, requestJSON, reqHeader, sopDebug);
	}

	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWObject getCurrentPrice(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception  {
		return new CurrentPrice().process(url, httpMethod, sslAlias, requestJSON, null, sopDebug);
	}
	
	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWObject getHistoricalPrice(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception  {
		return new HistoricalPrice().process(url, httpMethod, sslAlias, requestJSON, null, sopDebug);
	}
	
	/**
	 * Convenience method that does the same job as process method inherited from API. 
	 * 
	 * @param url
	 * @param httpMethod
	 * @param sslAlias
	 * @param requestJSON
	 * @param sopDebug
	 * @return teamworks.TWObject to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWObject getBillingAttributes(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception  {
		return new BillingAttributes().process(url, httpMethod, sslAlias, requestJSON, null, sopDebug);
	}	
	
	/**
	 * Convenience method that does the same job as consolidate. 
	 * 
	 * @param invoiceLines
	 * @param currentPrice
	 * @param sopDebug
	 * @return teamworks.TWList to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWList consolidateCPResponse(String invoiceLines, String currentPrice, boolean sopDebug) throws Exception {
		return new CPResponseHandler().consolidate(invoiceLines, currentPrice, sopDebug);
	}

	/**
	 * Convenience method that does the same job as consolidate. 
	 * 
	 * @param invoiceLines
	 * @param historicalPrice
	 * @param sopDebug
	 * @return teamworks.TWList to be mapped to BPM BO with same properties/parameters 
	 *  
	 */
	public TWList consolidateHPResponse(String invoiceLines, String historicalPrice, boolean sopDebug) throws Exception {
		return new HPResponseHandler().consolidate(invoiceLines, historicalPrice, sopDebug);
	}
}
