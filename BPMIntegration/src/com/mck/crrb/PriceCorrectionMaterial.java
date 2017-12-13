/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * @author akatre
 *
 */
public class PriceCorrectionMaterial extends CreditRebillMaterial {
	private Date pricingDate;
	private double oldWac;
	private double oldBid;
	private String oldLead;
	private String oldConRef;
	private float oldContCogPer;
	private float oldItemVarPer;
	private float oldWacCogPer;
	private float oldItemMkUpPer;
	private double oldAwp;
	private String oldNoChargeBack;
	private double oldOverridePrice;
	private double oldSellPrice;
	private String oldCbRef;
	private String oldSellCd;
	private String oldActivePrice;
	private double oldChargeBack;
	private double oldSsf;
	private double oldSf;
	private double oldListPrice;
	private String orderType;
	
	public Date getPricingDate() {
		return pricingDate;
	}
	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}
	public double getOldWac() {
		return oldWac;
	}
	public void setOldWac(double oldWac) {
		this.oldWac = oldWac;
	}
	public double getOldBid() {
		return oldBid;
	}
	public void setOldBid(double oldBid) {
		this.oldBid = oldBid;
	}
	public String getOldLead() {
		return oldLead;
	}
	public void setOldLead(String oldLead) {
		this.oldLead = oldLead;
	}
	public String getOldConRef() {
		return oldConRef;
	}
	public void setOldConRef(String oldConRef) {
		this.oldConRef = oldConRef;
	}
	public float getOldContCogPer() {
		return oldContCogPer;
	}
	public void setOldContCogPer(float oldContCogPer) {
		this.oldContCogPer = oldContCogPer;
	}
	public float getOldItemVarPer() {
		return oldItemVarPer;
	}
	public void setOldItemVarPer(float oldItemVarPer) {
		this.oldItemVarPer = oldItemVarPer;
	}
	public float getOldWacCogPer() {
		return oldWacCogPer;
	}
	public void setOldWacCogPer(float oldWacCogPer) {
		this.oldWacCogPer = oldWacCogPer;
	}
	public float getOldItemMkUpPer() {
		return oldItemMkUpPer;
	}
	public void setOldItemMkUpPer(float oldItemMkUpPer) {
		this.oldItemMkUpPer = oldItemMkUpPer;
	}
	public double getOldAwp() {
		return oldAwp;
	}
	public void setOldAwp(double oldAwp) {
		this.oldAwp = oldAwp;
	}
	public String getOldNoChargeBack() {
		return oldNoChargeBack;
	}
	public void setOldNoChargeBack(String oldNoChargeBack) {
		this.oldNoChargeBack = oldNoChargeBack;
	}
	public double getOldOverridePrice() {
		return oldOverridePrice;
	}
	public void setOldOverridePrice(double oldOverridePrice) {
		this.oldOverridePrice = oldOverridePrice;
	}
	public double getOldSellPrice() {
		return oldSellPrice;
	}
	public void setOldSellPrice(double oldSellPrice) {
		this.oldSellPrice = oldSellPrice;
	}
	public String getOldCbRef() {
		return oldCbRef;
	}
	public void setOldCbRef(String oldCbRef) {
		this.oldCbRef = oldCbRef;
	}
	public String getOldSellCd() {
		return oldSellCd;
	}
	public void setOldSellCd(String oldSellCd) {
		this.oldSellCd = oldSellCd;
	}
	public String getOldActivePrice() {
		return oldActivePrice;
	}
	public void setOldActivePrice(String oldActivePrice) {
		this.oldActivePrice = oldActivePrice;
	}
	public double getOldChargeBack() {
		return oldChargeBack;
	}
	public void setOldChargeBack(double oldChargeBack) {
		this.oldChargeBack = oldChargeBack;
	}
	public double getOldSsf() {
		return oldSsf;
	}
	public void setOldSsf(double oldSsf) {
		this.oldSsf = oldSsf;
	}
	public double getOldSf() {
		return oldSf;
	}
	public void setOldSf(double oldSf) {
		this.oldSf = oldSf;
	}
	public double getOldListPrice() {
		return oldListPrice;
	}
	public void setOldListPrice(double oldListPrice) {
		this.oldListPrice = oldListPrice;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		s += ", pricingDate : " + pricingDate  +  ", oldWac : " + oldWac  +  ", oldBid : " + oldBid  +  ", oldLead : " + oldLead  
				+  ", oldConRef : " + oldConRef  +  ", oldContCogPer : " + oldContCogPer  +  ", oldItemVarPer : " + oldItemVarPer  
				+  ", oldWacCogPer : " + oldWacCogPer  +  ", oldItemMkUpPer : " + oldItemMkUpPer  +  ", oldAwp : " + oldAwp  
				+  ", oldNoChargeBack : " + oldNoChargeBack  +  ", oldOverridePrice : " + oldOverridePrice  +  ", oldSellPrice : " 
				+ oldSellPrice  +  ", oldCbRef : " + oldCbRef  +  ", oldSellCd: " + oldSellCd +  ", oldActivePrice : " + oldActivePrice  
				+  ", oldChargeBack : " + oldChargeBack  +  ", oldSsf : " + oldSsf  +  ", oldSf : " + oldSf  +  ", oldListPrice : " + oldListPrice  
				+  ", orderType: " + orderType;
		return s;
	}	 
}
