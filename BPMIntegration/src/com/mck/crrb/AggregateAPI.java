/**
 * 
 */
package com.mck.crrb;

import teamworks.TWObject;

/**
 * @author akatre
 *
 */
public class AggregateAPI {
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
	public TWObject submitCreditRebill(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug) throws Exception {
		return new CreditRebill().process(url, httpMethod, sslAlias, requestJSON, null, sopDebug);
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
	public TWObject simulatePrice(String url, String httpMethod, String sslAlias, String requestJSON, String correlationId, boolean sopDebug) throws Exception {
		return new Price().process(url, httpMethod, sslAlias, requestJSON, correlationId, sopDebug);
	}
}
