/**
 * 
 */
package com.mck.crrb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import teamworks.TWObject;

/**
 * @author akatre
 *
 */
abstract class Z_CurrentPrice extends _API {

	public static Z_CurrentPriceResp getCurrentPrices(String url, String httpMethod, String sslAlias, String requestJSON, boolean sopDebug)  {
		String resp = _API.call(url, httpMethod, sslAlias, requestJSON, sopDebug);
		//TODO remove this test code
		//@Test - set specific resp object as callAPI doesn't return anything
		if (sopDebug) { System.out.println("CurrentPrice.getCurrentPrices() raw response: " + resp); }
		if (parseCurrentPriceLookupResp(resp).numberOfResponseLines() < 1) {
			if (sopDebug) { System.out.println("CurrentPrice.getCurrentPrices() number of response lines < 1 :("); }
			resp = "{    \"currentPriceResp\": [        {            \"pricingDate\": \"20170104\",			\"customerId\": \"0000040053\",			\"materials\": [                {                    \"materialId\": \"000000000002476489\",                    \"curSellCd\": \"2\",                    \"curNoChargeBack\": \"\",                    \"curActivePrice\": \"YCON\",                    \"curLead\": \"00001664\",                    \"curConRef\": \"I0151322\",                    \"curCbRef\": \"I0151322\",                    \"curContCogPer\": -2.99,                    \"curItemVarPer\": 0,                    \"curListPrice\": 82.37,                    \"curWac\": 103,                    \"curBid\": 44.45,                    \"curWacCogPer\": 45.64,                    \"curItemMkUpPer\": -5.6,                    \"curAwp\": 999.5,                    \"curPrice\": 62.1                },				{                    \"materialId\": \"000000000002056497\",                    \"curSellCd\": \"2\",                    \"curNoChargeBack\": \"\",                    \"curActivePrice\": \"YCON\",                    \"curLead\": \"00001664\",                    \"curConRef\": \"I0151322\",                    \"curCbRef\": \"I0151322\",                    \"curContCogPer\": -2.99,                    \"curItemVarPer\": 0,                    \"curListPrice\": 100.37,                    \"curWac\": 102,                    \"curBid\": 65.45,                    \"curWacCogPer\": 96.64,                    \"curItemMkUpPer\": -6.6,                    \"curAwp\": 1030.5,                    \"curPrice\": 63.5                },                {                    \"materialId\": \"000000000002476489\",                    \"curSellCd\": \"9\",                    \"curNoChargeBack\": \"\",                    \"curActivePrice\": \"YCOS\",                    \"curLead\": \"00001664\",                    \"curConRef\": \"I0151322\",                    \"curCbRef\": \"I0151322\",                    \"curContCogPer\": -2.99,                    \"curItemVarPer\": 0,                    \"curListPrice\": 142.75,                    \"curWac\": 130.7,                    \"curWacCogPer\": 0,                    \"curItemMkUpPer\": 0,                    \"curAwp\": 142.75,                    \"curPrice\": 126.31                }            ]        },        {				\"pricingDate\": \"20170103\",				\"customerId\": \"0000105789\",				\"materials\": [                {                    \"materialId\": \"000000000003565306\",                    \"curSellCd\": \"9\",                    \"curNoChargeBack\": \"\",                    \"curActivePrice\": \"YCOS\",                    \"curLead\": \"\",                    \"curConRef\": \"\",                    \"curCbRef\": \"\",                    \"curContCogPer\": 0,                    \"curItemVarPer\": 0,                    \"curListPrice\": 142.75,                    \"curWac\": 130.7,                    \"curWacCogPer\": 0,                    \"curItemMkUpPer\": 0,                    \"curAwp\": 142.75,                    \"curPrice\": 126.31                }            ]        }    ],    \"results\": [        {            \"index\": \"0\",            \"status\": \"success\"        },        {            \"index\": \"1\",            \"status\": \"success\"        }    ]}";
		}
		return parseCurrentPriceLookupResp(resp);
	}
	
	public static Z_CurrentPriceResp parseCurrentPriceLookupResp(String resp) {
		ObjectMapper jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Z_CurrentPriceResp currentPriceLookupResp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jacksonMapper.setDateFormat(sdf);
			
			currentPriceLookupResp = jacksonMapper.readValue(resp, Z_CurrentPriceResp.class);
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
		return currentPriceLookupResp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String url = "https://esswsqdpz01.mckesson.com/MckWebServices/muleservices/sapphr/currentprice/request";
		String httpMethod = "POST";
		String sslAlias = "CellDefaultSSLSettings";
		String requestJSON = "{\"CurrentPriceReq\":[		{			\"customerId\":\"80853\",			\"pricingDate\":\"20170505\",			\"materials\":[\"1361955\",\"1116854\",\"1100452\"]		},		{			\"customerId\":\"80853\",			\"pricingDate\":\"20171119\",			\"materials\":[\"1361955\"]		}	],	\"startIndex\" : 0,	\"endIndex\" : 0}";
		String rawResp = "";
		Z_CurrentPriceResp currentPriceLookupResp = null;
		Date d1 = new Date();
		Date d2 = null;
		
		d1 = new Date();
		System.out.println("\r\nStart invoiceLookup API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		rawResp = _API.call(url, httpMethod, sslAlias, requestJSON, true);
		d2 = new Date();
		System.out.println("End invoiceLookup API call: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
		System.out.println("rawInvoiceLookupResp length:" + rawResp.length()); 
		System.out.println("Total invoiceLookup API time (ms): " + (d2.getTime() - d1.getTime()));
		
		d1 = new Date();
		System.out.println("\r\nStart response parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d1));
		currentPriceLookupResp = parseCurrentPriceLookupResp(rawResp);
		d2 = new Date();
		System.out.println("End response parsing: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(d2));
		
		System.out.println("resp Complex object from json file with number of records: " + currentPriceLookupResp.numberOfResponseLines());
		System.out.println("Total parsing time (ms): " + (d2.getTime() - d1.getTime()));
	}

	@Override
	public TWObject parseResponse(String rawResp, boolean sopDebug) {
		// TODO Auto-generated method stub
		return null;
	}

}
