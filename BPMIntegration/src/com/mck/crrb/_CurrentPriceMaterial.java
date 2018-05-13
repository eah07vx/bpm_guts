/**
 * 
 */
package com.mck.crrb;

import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * @author akatre
 *
 */
public class _CurrentPriceMaterial extends _Material {
	private TWObject twCurrentPriceMaterial;
	
	private String curSellCd;
	private String curNoChargeBack;
	private String curActivePrice;
	private String curLead;
	private String curConRef;
	private String curCbRef;
    private float curContCogPer;
	private float curItemDcMarkUpPer;
    private float curItemMkUpPer;
    private float curItemVarPer;
	private float curWacCogPer;
    private double curListPrice;
    private double curWac;
    private double curBid;
	private double curChargeBack;
	private double curAbd;
	private String curNetBill;
	private double curSsf;
	private double curSf;
    private double curAwp;
    private double curPrice;
	private String curContType;
	private String curContrId;
	private String curProgType;
    private String curProgramTypeCd;
	private double curOverridePrice;
	private String curRebillCust;
	private String curCustomerName;
	
	public _CurrentPriceMaterial() {
    	super();
		try {
			this.setTwCurrentPriceMaterial(TWObjectFactory.createObject());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }

    public TWObject getTwCurrentPriceMaterial() {
		return twCurrentPriceMaterial;
	}

	public void setTwCurrentPriceMaterial(TWObject twCurrentPriceMaterial) {
		this.twCurrentPriceMaterial = twCurrentPriceMaterial;
	}
	
	@Override
	public void setMaterialId(String materialId) {
		super.setMaterialId(materialId);
		this.twCurrentPriceMaterial.setPropertyValue("materialId", this.materialId);
	}

    public String getCurSellCd() {
		return curSellCd;
	}
	public void setCurSellCd(String curSellCd) {
		this.curSellCd = curSellCd;
		this.twCurrentPriceMaterial.setPropertyValue("curSellCd", this.curSellCd);
	}
	
	public String getCurNoChargeBack() {
		return curNoChargeBack;
	}
	public void setCurNoChargeBack(String curNoChargeBack) {
		this.curNoChargeBack = curNoChargeBack;
		this.twCurrentPriceMaterial.setPropertyValue("curNoChargeBack", this.curNoChargeBack);
	}
	public String getCurActivePrice() {
		return curActivePrice;
	}
	public void setCurActivePrice(String curActivePrice) {
		this.curActivePrice = curActivePrice;
		this.twCurrentPriceMaterial.setPropertyValue("curActivePrice", this.curActivePrice);
	}
	public String getCurLead() {
		return curLead;
	}
	public void setCurLead(String curLead) {
		this.curLead = curLead;
		this.twCurrentPriceMaterial.setPropertyValue("curLead", this.curLead);
	}
	public String getCurConRef() {
		return curConRef;
	}
	public void setCurConRef(String curConRef) {
		this.curConRef = curConRef;
		this.twCurrentPriceMaterial.setPropertyValue("curConRef", this.curConRef);
	}
	public String getCurCbRef() {
		return curCbRef;
	}
	public void setCurCbRef(String curCbRef) {
		this.curCbRef = curCbRef;
		this.twCurrentPriceMaterial.setPropertyValue("curCbRef", this.curCbRef);
	}
	public float getCurContCogPer() {
		return curContCogPer;
	}
	public void setCurContCogPer(float curContCogPer) {
		this.curContCogPer = curContCogPer;
		this.twCurrentPriceMaterial.setPropertyValue("curContCogPer", this.curContCogPer);
	}
	public float getCurItemVarPer() {
		return curItemVarPer;
	}
	public void setCurItemVarPer(float curItemVarPer) {
		this.curItemVarPer = curItemVarPer;
		this.twCurrentPriceMaterial.setPropertyValue("curItemVarPer", this.curItemVarPer);
	}
	public double getCurListPrice() {
		return curListPrice;
	}
	public void setCurListPrice(double curListPrice) {
		this.curListPrice = curListPrice;
		this.twCurrentPriceMaterial.setPropertyValue("curListPrice", this.curListPrice);
	}
	public double getCurWac() {
		return curWac;
	}
	public void setCurWac(double curWac) {
		this.curWac = curWac;
		this.twCurrentPriceMaterial.setPropertyValue("curWac", this.curWac);
	}
	public double getCurBid() {
		return curBid;
	}
	public void setCurBid(double curBid) {
		this.curBid = curBid;
		this.twCurrentPriceMaterial.setPropertyValue("curBid", this.curBid);
	}
	public float getCurItemMkUpPer() {
		return curItemMkUpPer;
	}
	public void setCurItemMkUpPer(float curItemMkUpPer) {
		this.curItemMkUpPer = curItemMkUpPer;
		this.twCurrentPriceMaterial.setPropertyValue("curItemMkUpPer", this.curItemMkUpPer);
	}
	public double getCurAwp() {
		return curAwp;
	}
	public void setCurAwp(double curAwp) {
		this.curAwp = curAwp;
		this.twCurrentPriceMaterial.setPropertyValue("curAwp", this.curAwp);
	}
	public double getCurPrice() {
		return curPrice;
	}
	public void setCurPrice(double curPrice) {
		this.curPrice = curPrice;
		this.twCurrentPriceMaterial.setPropertyValue("curPrice", this.curPrice);
	}
	public String getCurProgramTypeCd() {
		return curProgramTypeCd;
	}
	public void setCurProgramTypeCd(String curProgramTypeCd) {
		this.curProgramTypeCd = curProgramTypeCd;
		this.twCurrentPriceMaterial.setPropertyValue("curProgramTypeCd", this.curProgramTypeCd);
	}
	public float getCurWacCogPer() {
		return curWacCogPer;
	}
	public void setCurWacCogPer(float curWacCogPer) {
		this.curWacCogPer = curWacCogPer;
		this.twCurrentPriceMaterial.setPropertyValue("curWacCogPer", this.curWacCogPer);
	}
	public double getCurChargeBack() {
		return curChargeBack;
	}
	public void setCurChargeBack(double curChargeBack) {
		this.curChargeBack = curChargeBack;
		this.twCurrentPriceMaterial.setPropertyValue("curChargeBack", this.curChargeBack);
	}
	public double getCurSsf() {
		return curSsf;
	}
	public void setCurSsf(double curSsf) {
		this.curSsf = curSsf;
		this.twCurrentPriceMaterial.setPropertyValue("curSsf", this.curSsf);
	}
	public double getCurSf() {
		return curSf;
	}
	public void setCurSf(double curSf) {
		this.curSf = curSf;
		this.twCurrentPriceMaterial.setPropertyValue("curSf", this.curSf);
	}
	public double getCurOverridePrice() {
		return curOverridePrice;
	}
	public void setCurOverridePrice(double curOverridePrice) {
		this.curOverridePrice = curOverridePrice;
		this.twCurrentPriceMaterial.setPropertyValue("curOverridePrice", this.curOverridePrice);
	}
	public double getCurAbd() {
		return curAbd;
	}
	public void setCurAbd(double curAbd) {
		this.curAbd = curAbd;
		this.twCurrentPriceMaterial.setPropertyValue("curAbd", this.curAbd);
	}
	public String getCurNetBill() {
		return curNetBill;
	}
	public void setCurNetBill(String curNetBill) {
		this.curNetBill = curNetBill;
		this.twCurrentPriceMaterial.setPropertyValue("curNetBill", this.curNetBill);
	}
	public String getCurProgType() {
		return curProgType;
	}
	public void setCurProgType(String curProgType) {
		this.curProgType = curProgType;
		this.twCurrentPriceMaterial.setPropertyValue("curProgType", this.curProgType);
	}
	public String getCurContrId() {
		return curContrId;
	}
	public void setCurContrId(String curContrId) {
		this.curContrId = curContrId;
		this.twCurrentPriceMaterial.setPropertyValue("curContrId", this.curContrId);
	}
	public String getCurContType() {
		return curContType;
	}
	public void setCurContType(String curContType) {
		this.curContType = curContType;
		this.twCurrentPriceMaterial.setPropertyValue("curContType", this.curContType);
	}
	public String getCurRebillCust() {
		return curRebillCust;
	}
	public void setCurRebillCust(String curRebillCust) {
		this.curRebillCust = curRebillCust;
		this.twCurrentPriceMaterial.setPropertyValue("curRebillCust", this.curRebillCust);
	}
	public String getCurCustomerName() {
		return curCustomerName;
	}
	public void setCurCustomerName(String curCustomerName) {
		this.curCustomerName = curCustomerName;
		this.twCurrentPriceMaterial.setPropertyValue("curCustomerName", this.curCustomerName);
	}
	public float getCurItemDcMarkUpPer() {
		return curItemDcMarkUpPer;
	}
	public void setCurItemDcMarkUpPer(float curItemDcMarkUpPer) {
		this.curItemDcMarkUpPer = curItemDcMarkUpPer;
		this.twCurrentPriceMaterial.setPropertyValue("curItemDcMarkUpPer", this.curItemDcMarkUpPer);
	}

	@Override
	public String toString() {
		String str = "\n " + super.toString() + "\n";

		str += " curSellCd: " + curSellCd + ", curNoChargeBack: " + curNoChargeBack + ", curActivePrice: " + curActivePrice +
				" curLead: " + curLead + ", curConRef: " + curConRef + ", curCbRef: " + curCbRef +
				" curContCogPer: " + curContCogPer + ", curItemDcMarkUpPer: " + curItemDcMarkUpPer + ", curItemMkUpPer: " + curItemMkUpPer +
				" curItemVarPer: " + curItemVarPer + ", curWacCogPer: " + curWacCogPer + ", curListPrice: " + curListPrice +
				" curWac: " + curWac + ", curBid: " + curBid + ", curChargeBack: " + curChargeBack +
				" curAbd: " + curAbd + ", curNetBill: " + curNetBill + ", curSsf: " + curSsf +
				" curSf: " + curSf + ", curAwp: " + curAwp + ", curPrice: " + curPrice +
				" curContType: " + curContType + ", curContrId: " + curContrId + ", curProgType: " + curProgType +
				" curProgramTypeCd: " + curProgramTypeCd + ", curOverridePrice: " + curOverridePrice + ", curRebillCust: " + curRebillCust +
				" curCustomerName: " + curCustomerName;
		
		return str;
	}
}
