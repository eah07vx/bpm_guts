/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * @author akatre
 *
 */
public class _ConsolidatedHPRow {

	private int index;
	private String customerId;
	private Date pricingDate;
	private String priority;
	private float histContCogPer;
	private String preference;
	private String histLead;
	private String classOfTrade;
	private String contractNumber;
	private String contractVersion;
	private String materialId;
	private double histBid;
	private double histWac;
	private float histItemVarPer;
	private double histPrice;
	private String contractRef;
	private String orgIdSequence;
	private String histConRef;
	private String orderType;
	private String salesOrg;
	private String distrChan;
	private String histNoChargeBack;
	private double histChargeBack;
	
	public _ConsolidatedHPRow() {
		super();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public float getHistContCogPer() {
		return histContCogPer;
	}

	public void setHistContCogPer(float histContCogPer) {
		this.histContCogPer = histContCogPer;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getHistLead() {
		return histLead;
	}

	public void setHistLead(String histLead) {
		this.histLead = histLead;
	}

	public String getClassOfTrade() {
		return classOfTrade;
	}

	public void setClassOfTrade(String classOfTrade) {
		this.classOfTrade = classOfTrade;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public double getHistBid() {
		return histBid;
	}

	public void setHistBid(double histBid) {
		this.histBid = histBid;
	}

	public double getHistWac() {
		return histWac;
	}

	public void setHistWac(double histWac) {
		this.histWac = histWac;
	}

	public float getHistItemVarPer() {
		return histItemVarPer;
	}

	public void setHistItemVarPer(float histItemVarPer) {
		this.histItemVarPer = histItemVarPer;
	}

	public double getHistPrice() {
		return histPrice;
	}

	public void setHistPrice(double histPrice) {
		this.histPrice = histPrice;
	}

	public String getContractRef() {
		return contractRef;
	}

	public void setContractRef(String contractRef) {
		this.contractRef = contractRef;
	}

	public String getOrgIdSequence() {
		return orgIdSequence;
	}

	public void setOrgIdSequence(String orgIdSequence) {
		this.orgIdSequence = orgIdSequence;
	}

	public String getHistConRef() {
		return histConRef;
	}

	public void setHistConRef(String histConRef) {
		this.histConRef = histConRef;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getDistrChan() {
		return distrChan;
	}

	public void setDistrChan(String distrChan) {
		this.distrChan = distrChan;
	}

	public String getHistNoChargeBack() {
		return histNoChargeBack;
	}

	public void setHistNoChargeBack(String histNoChargeBack) {
		this.histNoChargeBack = histNoChargeBack;
	}

	public double getHistChargeBack() {
		return histChargeBack;
	}

	public void setHistChargeBack(double histChargeBack) {
		this.histChargeBack = histChargeBack;
	}

	@Override
	public String toString() {
		String str = "";
		str += " index: " + index + ", customerId: " + customerId + ", pricingDate: " + pricingDate + 
				", priority: " + priority + ", histContCogPer: " + histContCogPer + ", preference: " + preference + 
				", histLead: " + histLead + ", classOfTrade: " + classOfTrade + ", contractNumber: " + contractNumber + 
				", contractVersion: " + contractVersion + ", materialId: " + materialId +
				", histBid: " + histBid + ", histWac: " + histWac + ", histItemVarPer: " + histItemVarPer + 
				", histPrice: " + histPrice + ", contractRef: " + contractRef + ", orgIdSequence: " + orgIdSequence + 
				", histConRef: " + histConRef + ", orderType: " + orderType + ", salesOrg: " + salesOrg + 
				", distrChan: " + distrChan + ", histNoChargeBack: " + histNoChargeBack + ", histChargeBack: " + histChargeBack; 
		return str;
	}
}
