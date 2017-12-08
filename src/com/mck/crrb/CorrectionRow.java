/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

import teamworks.TWObject;
import teamworks.TWObjectFactory;

//import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akatre
 *
 */
class CorrectionRow {
	private TWObject twCorrectionRow;
	//invoice
	private String invoiceId;
	private String invoiceLineItemNum;
	//customer
	private String customerId;
	private String customerName;
	//material
	private String materialId;
	private String materialName;
	private Date pricingDate;
	//supplier
	private String supplierId;
	private String supplierName;
	//quantity
	private double billQty;
	private double retQty;
	private double crQty;
	private double rebillQty;
	private String uom;
	private Date createdOn;
	private String dc;
	private String ndcUpc;
	private String billType;
	//chain
	private String chainId;
	private String chainName;
	//group; sub-group
	private String groupId;
	private String groupName;
	private String subgroupId;
	private String subgroupName;
	private String reasonCode;
	private String poNumber;
	//price trifecta
	private double oldPrice;
	private double curPrice;
	private double newPrice;
	//wac trifecta
	private double oldWac;
	private double curWac;
	private double newWac;
	//bid trifecta
	private double oldBid;
	private double curBid;
	private double newBid;
	//lead trifecta
	private String oldLead;
	private String curLead;
	private String newLead;
	//contract reference trifecta
	private String oldConRef;
	private String curConRef;
	private String newConRef;
	//chargeback reference trifecta
	private String oldCbRef;
	private String curCbRef;
	private String newCbRef;
	//contract COG percentage trifecta
	private float oldContCogPer;
	private float curContCogPer;
	private float newContCogPer;
	//item var percentage trifecta
	private float oldItemVarPer;
	private float curItemVarPer;
	private float newItemVarPer;
	//WAC COG percentage trifecta
	private float oldWacCogPer;
	private float curWacCogPer;
	private float newWacCogPer;
	//item markup/down percentage trifecta
	private float oldItemMkUpPer;
	private float curItemMkUpPer;
	private float newItemMkUpPer;
	//chargeback amount trifecta
	private double oldChargeBack;
	private double curChargeBack;
	private double newChargeBack;
	//sell code trifecta
	private String oldSellCd;
	private String curSellCd;
	private String newSellCd;
	//no chargeback indicator trifecta
	private String oldNoChargeBack;
	private String curNoChargeBack;
	private String newNoChargeBack;
	//active price indicator (e.g. YCON, YCOS, YFIX, ZDSC) trifecta
	private String oldActivePrice;
	private String curActivePrice;
	private String newActivePrice;
	//SSF and SF trifectas
	private double oldSsf;
	private double curSsf;
	private double newSsf;
	private double oldSf;
	private double curSf;
	private double newSf;
	//list price trifecta
	private double oldListPrice;
	private double curListPrice;
	private double newListPrice;
	//average warehouse price trifecta
	private double oldAwp;
	private double curAwp;
	private double newAwp;
	//override price trifecta
	private double oldOverridePrice;
	private double curOverridePrice;
	private double newOverridePrice;
	private String orgDbtMemoId;
	private double orgVendorAccAmt;
	//additional customer identification 
	private String deaNum;
	private String hin;
	//customer address
	private String street;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	//miscellaneous attributes
	private String salesOrg;
	private String orderType;
	private String netBill;
	private double sellPriceExt;
	private double totChbk;
	private double gxcb;
	//TODO: remove this field once Archit makes the change in Mulesoft API
	private String address; 
	
	public CorrectionRow() {
		try {
			this.twCorrectionRow = TWObjectFactory.createObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public String toString() {
		return this.getBillQty() + ", " + this.getBillType() + ", " + this.getChainId() + ", " + this.getChainName() + ", " + this.getCreatedOn() + ", " + this.getCrQty() + ", " + this.getCustomerId() + ", " + this.getCustomerName() + ", " + this.getDc() + ", " + this.getDeaNum() + ", " + this.getGroupId() + ", " + this.getGroupName() + ", " + this.getGxcb() + ", " + this.getInvoiceId() + ", " + this.getInvoiceLineItemNum() + ", " + this.getMaterialId() + ", " + this.getMaterialName() + ", " + this.getNdcUpc() + ", " + this.getNetBill() + ", " + this.getOldActivePrice() + ", " + this.getOldAwp() + ", " + this.getOldBid() + ", " + this.getOldCbRef() + ", " + this.getOldChargeBack() + ", " + this.getOldConRef() + ", " + this.getOldContCogPer() + ", " + this.getOldItemMkUpPer() + ", " + this.getOldLead() + ", " + this.getOldListPrice() + ", " + this.getReasonCode() + ", " + this.getOldNoChargeBack() + ", " + this.getOldPrice() + ", " + this.getOldSellCd() + ", " + this.getOldSf() + ", " + this.getOldSsf() + ", " + this.getOldWac() + ", " + this.getOldWacCogPer() + ", " + this.getOrderType() + ", " + this.getOrgDbtMemoId() + ", " + this.getOrgVendorAccAmt() + ", " + this.getPoNumber() + ", " + this.getPricingDate() + ", " + this.getRebillQty() + ", " + this.getRetQty() + ", " + this.getSalesOrg() + ", " + this.getSellPriceExt() + ", " + this.getSubgroupId() + ", " + this.getSubgroupName() + ", " + this.getSupplierId() + ", " + this.supplierName + ", " + this.getTotChbk() + ", " + this.getUom();
	}

	public TWObject getTwCorrectionRow() {
		return twCorrectionRow;
	}

	public void setTwCorrectionRow(TWObject twCorrectionRow) {
		this.twCorrectionRow = twCorrectionRow;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
		this.twCorrectionRow.setPropertyValue("invoiceId", this.invoiceId);
	}

	public String getInvoiceLineItemNum() {
		return invoiceLineItemNum;
	}

	public void setInvoiceLineItemNum(String invoiceLineItemNum) {
		this.invoiceLineItemNum = invoiceLineItemNum;
		this.twCorrectionRow.setPropertyValue("invoiceLineItemNum", this.invoiceLineItemNum);
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = Utility.trimLeadingChars(customerId, "0");
		this.twCorrectionRow.setPropertyValue("customerId", this.customerId);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
		this.twCorrectionRow.setPropertyValue("customerName", this.customerName);
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = Utility.trimLeadingChars(materialId, "0");
		this.twCorrectionRow.setPropertyValue("materialId", this.materialId);
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
		this.twCorrectionRow.setPropertyValue("materialName", this.materialName);
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
		this.twCorrectionRow.setPropertyValue("pricingDate", this.pricingDate);
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = Utility.trimLeadingChars(supplierId, "0");
		this.twCorrectionRow.setPropertyValue("supplierId", this.supplierId);
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
		this.twCorrectionRow.setPropertyValue("supplierName", this.supplierName);
	}

	public double getBillQty() {
		return billQty;
	}

	public void setBillQty(double billQty) {
		this.billQty = billQty;
		this.twCorrectionRow.setPropertyValue("billQty", this.billQty);
	}

	public double getRetQty() {
		return retQty;
	}

	public void setRetQty(double retQty) {
		this.retQty = retQty;
		this.twCorrectionRow.setPropertyValue("retQty", this.retQty);
	}

	public double getCrQty() {
		return crQty;
	}

	public void setCrQty(double crQty) {
		this.crQty = crQty;
		this.twCorrectionRow.setPropertyValue("crQty", this.crQty);
	}

	public double getRebillQty() {
		return rebillQty;
	}

	public void setRebillQty(double rebillQty) {
		this.rebillQty = rebillQty;
		this.twCorrectionRow.setPropertyValue("rebillQty", this.rebillQty);
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
		this.twCorrectionRow.setPropertyValue("uom", this.uom);
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		this.twCorrectionRow.setPropertyValue("createdOn", this.createdOn);
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
		this.twCorrectionRow.setPropertyValue("dc", this.dc);
	}

	public String getNdcUpc() {
		return ndcUpc;
	}

	public void setNdcUpc(String ndcUpc) {
		this.ndcUpc = ndcUpc;
		this.twCorrectionRow.setPropertyValue("ndcUpc", this.ndcUpc);
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
		this.twCorrectionRow.setPropertyValue("billType", this.billType);
	}

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
		this.twCorrectionRow.setPropertyValue("chainId", this.chainId);
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
		this.twCorrectionRow.setPropertyValue("chainName", this.chainName);
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
		this.twCorrectionRow.setPropertyValue("groupId", this.groupId);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
		this.twCorrectionRow.setPropertyValue("groupName", this.groupName);
	}

	public String getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(String subgroupId) {
		this.subgroupId = subgroupId;
		this.twCorrectionRow.setPropertyValue("subgroupId", this.subgroupId);
	}

	public String getSubgroupName() {
		return subgroupName;
	}

	public void setSubgroupName(String subgroupName) {
		this.subgroupName = subgroupName;
		this.twCorrectionRow.setPropertyValue("subgroupName", this.subgroupName);
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
		this.twCorrectionRow.setPropertyValue("reasonCode", this.reasonCode);
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
		this.twCorrectionRow.setPropertyValue("poNumber", this.poNumber);
	}

	public double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
		this.twCorrectionRow.setPropertyValue("oldPrice", this.oldPrice);
	}

	public double getCurPrice() {
		return curPrice;
	}

	public void setCurPrice(double curPrice) {
		this.curPrice = curPrice;
		this.twCorrectionRow.setPropertyValue("curPrice", this.curPrice);
	}

	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
		this.twCorrectionRow.setPropertyValue("newPrice", this.newPrice);
	}

	public double getOldWac() {
		return oldWac;
	}

	public void setOldWac(double oldWac) {
		this.oldWac = oldWac;
		this.twCorrectionRow.setPropertyValue("oldWac", this.oldWac);
	}

	public double getCurWac() {
		return curWac;
	}

	public void setCurWac(double curWac) {
		this.curWac = curWac;
		this.twCorrectionRow.setPropertyValue("curWac", this.curWac);
	}

	public double getNewWac() {
		return newWac;
	}

	public void setNewWac(double newWac) {
		this.newWac = newWac;
		this.twCorrectionRow.setPropertyValue("newWac", this.newWac);
	}

	public double getOldBid() {
		return oldBid;
	}

	public void setOldBid(double oldBid) {
		this.oldBid = oldBid;
		this.twCorrectionRow.setPropertyValue("oldBid", this.oldBid);
	}

	public double getCurBid() {
		return curBid;
	}

	public void setCurBid(double curBid) {
		this.curBid = curBid;
		this.twCorrectionRow.setPropertyValue("curBid", this.curBid);
	}

	public double getNewBid() {
		return newBid;
	}

	public void setNewBid(double newBid) {
		this.newBid = newBid;
		this.twCorrectionRow.setPropertyValue("newBid", this.newBid);
	}

	public String getOldLead() {
		return oldLead;
	}

	public void setOldLead(String oldLead) {
		this.oldLead = Utility.trimLeadingChars(oldLead, "0");
		this.twCorrectionRow.setPropertyValue("oldLead", this.oldLead);
	}

	public String getCurLead() {
		return curLead;
	}

	public void setCurLead(String curLead) {
		this.curLead = Utility.trimLeadingChars(curLead, "0");
		this.twCorrectionRow.setPropertyValue("curLead", this.curLead);
	}

	public String getNewLead() {
		return newLead;
	}

	public void setNewLead(String newLead) {
		this.newLead = Utility.trimLeadingChars(newLead, "0");
		this.twCorrectionRow.setPropertyValue("newLead", this.newLead);
	}

	public String getOldConRef() {
		return oldConRef;
	}

	public void setOldConRef(String oldConRef) {
		this.oldConRef = oldConRef;
		this.twCorrectionRow.setPropertyValue("oldConRef", this.oldConRef);
	}

	public String getCurConRef() {
		return curConRef;
	}

	public void setCurConRef(String curConRef) {
		this.curConRef = curConRef;
		this.twCorrectionRow.setPropertyValue("curConRef", this.curConRef);
	}

	public String getNewConRef() {
		return newConRef;
	}

	public void setNewConRef(String newConRef) {
		this.newConRef = newConRef;
		this.twCorrectionRow.setPropertyValue("newConRef", this.newConRef);
	}

	public String getOldCbRef() {
		return oldCbRef;
	}

	public void setOldCbRef(String oldCbRef) {
		this.oldCbRef = oldCbRef;
		this.twCorrectionRow.setPropertyValue("oldCbRef", this.oldCbRef);
	}

	public String getCurCbRef() {
		return curCbRef;
	}

	public void setCurCbRef(String curCbRef) {
		this.curCbRef = curCbRef;
		this.twCorrectionRow.setPropertyValue("curCbRef", this.curCbRef);
	}

	public String getNewCbRef() {
		return newCbRef;
	}

	public void setNewCbRef(String newCbRef) {
		this.newCbRef = newCbRef;
		this.twCorrectionRow.setPropertyValue("newCbRef", this.newCbRef);
	}

	public float getOldContCogPer() {
		return oldContCogPer;
	}

	public void setOldContCogPer(float oldContCogPer) {
		this.oldContCogPer = oldContCogPer;
		this.twCorrectionRow.setPropertyValue("oldContCogPer", this.oldContCogPer);
	}

	public float getCurContCogPer() {
		return curContCogPer;
	}

	public void setCurContCogPer(float curContCogPer) {
		this.curContCogPer = curContCogPer;
		this.twCorrectionRow.setPropertyValue("curContCogPer", this.curContCogPer);
	}

	public float getNewContCogPer() {
		return newContCogPer;
	}

	public void setNewContCogPer(float newContCogPer) {
		this.newContCogPer = newContCogPer;
		this.twCorrectionRow.setPropertyValue("newContCogPer", this.newContCogPer);
	}

	public float getOldItemVarPer() {
		return oldItemVarPer;
	}

	public void setOldItemVarPer(float oldItemVarPer) {
		this.oldItemVarPer = oldItemVarPer;
		this.twCorrectionRow.setPropertyValue("oldItemVarPer", this.oldItemVarPer);
	}

	public float getCurItemVarPer() {
		return curItemVarPer;
	}

	public void setCurItemVarPer(float curItemVarPer) {
		this.curItemVarPer = curItemVarPer;
		this.twCorrectionRow.setPropertyValue("curItemVarPer", this.curItemVarPer);
	}

	public float getNewItemVarPer() {
		return newItemVarPer;
	}

	public void setNewItemVarPer(float newItemVarPer) {
		this.newItemVarPer = newItemVarPer;
		this.twCorrectionRow.setPropertyValue("newItemVarPer", this.newItemVarPer);
	}

	public float getOldWacCogPer() {
		return oldWacCogPer;
	}

	public void setOldWacCogPer(float oldWacCogPer) {
		this.oldWacCogPer = oldWacCogPer;
		this.twCorrectionRow.setPropertyValue("oldWacCogPer", this.oldWacCogPer);
	}

	public float getCurWacCogPer() {
		return curWacCogPer;
	}

	public void setCurWacCogPer(float curWacCogPer) {
		this.curWacCogPer = curWacCogPer;
		this.twCorrectionRow.setPropertyValue("curWacCogPer", this.curWacCogPer);
	}

	public float getNewWacCogPer() {
		return newWacCogPer;
	}

	public void setNewWacCogPer(float newWacCogPer) {
		this.newWacCogPer = newWacCogPer;
		this.twCorrectionRow.setPropertyValue("newWacCogPer", this.newWacCogPer);
	}

	public float getOldItemMkUpPer() {
		return oldItemMkUpPer;
	}

	public void setOldItemMkUpPer(float oldItemMkUpPer) {
		this.oldItemMkUpPer = oldItemMkUpPer;
		this.twCorrectionRow.setPropertyValue("oldItemMkUpPer", this.oldItemMkUpPer);
	}

	public float getCurItemMkUpPer() {
		return curItemMkUpPer;
	}

	public void setCurItemMkUpPer(float curItemMkUpPer) {
		this.curItemMkUpPer = curItemMkUpPer;
		this.twCorrectionRow.setPropertyValue("curItemMkUpPer", this.curItemMkUpPer);
	}

	public float getNewItemMkUpPer() {
		return newItemMkUpPer;
	}

	public void setNewItemMkUpPer(float newItemMkUpPer) {
		this.newItemMkUpPer = newItemMkUpPer;
		this.twCorrectionRow.setPropertyValue("newItemMkUpPer", this.newItemMkUpPer);
	}

	public double getOldChargeBack() {
		return oldChargeBack;
	}

	public void setOldChargeBack(double oldChargeBack) {
		this.oldChargeBack = oldChargeBack;
		this.twCorrectionRow.setPropertyValue("oldChargeBack", this.oldChargeBack);
	}

	public double getCurChargeBack() {
		return curChargeBack;
	}

	public void setCurChargeBack(double curChargeBack) {
		this.curChargeBack = curChargeBack;
		this.twCorrectionRow.setPropertyValue("curChargeBack", this.curChargeBack);
	}

	public double getNewChargeBack() {
		return newChargeBack;
	}

	public void setNewChargeBack(double newChargeBack) {
		this.newChargeBack = newChargeBack;
		this.twCorrectionRow.setPropertyValue("newChargeBack", this.newChargeBack);
	}

	public String getOldSellCd() {
		return oldSellCd;
	}

	public void setOldSellCd(String oldSellCd) {
		this.oldSellCd = oldSellCd;
		this.twCorrectionRow.setPropertyValue("oldSellCd", this.oldSellCd);
	}

	public String getCurSellCd() {
		return curSellCd;
	}

	public void setCurSellCd(String curSellCd) {
		this.curSellCd = curSellCd;
		this.twCorrectionRow.setPropertyValue("curSellCd", this.curSellCd);
	}

	public String getNewSellCd() {
		return newSellCd;
	}

	public void setNewSellCd(String newSellCd) {
		this.newSellCd = newSellCd;
		this.twCorrectionRow.setPropertyValue("newSellCd", this.newSellCd);
	}

	public String getOldNoChargeBack() {
		return oldNoChargeBack;
	}

	public void setOldNoChargeBack(String oldNoChargeBack) {
		this.oldNoChargeBack = oldNoChargeBack;
		this.twCorrectionRow.setPropertyValue("oldNoChargeBack", this.oldNoChargeBack);
	}

	public String getCurNoChargeBack() {
		return curNoChargeBack;
	}

	public void setCurNoChargeBack(String curNoChargeBack) {
		this.curNoChargeBack = curNoChargeBack;
		this.twCorrectionRow.setPropertyValue("curNoChargeBack", this.curNoChargeBack);
	}

	public String getNewNoChargeBack() {
		return newNoChargeBack;
	}

	public void setNewNoChargeBack(String newNoChargeBack) {
		this.newNoChargeBack = newNoChargeBack;
		this.twCorrectionRow.setPropertyValue("newNoChargeBack", this.newNoChargeBack);
	}

	public String getOldActivePrice() {
		return oldActivePrice;
	}

	public void setOldActivePrice(String oldActivePrice) {
		this.oldActivePrice = oldActivePrice;
		this.twCorrectionRow.setPropertyValue("oldActivePrice", this.oldActivePrice);
	}

	public String getCurActivePrice() {
		return curActivePrice;
	}

	public void setCurActivePrice(String curActivePrice) {
		this.curActivePrice = curActivePrice;
		this.twCorrectionRow.setPropertyValue("curActivePrice", this.curActivePrice);
	}

	public String getNewActivePrice() {
		return newActivePrice;
	}

	public void setNewActivePrice(String newActivePrice) {
		this.newActivePrice = newActivePrice;
		this.twCorrectionRow.setPropertyValue("newActivePrice", this.newActivePrice);
	}

	public double getOldSsf() {
		return oldSsf;
	}

	public void setOldSsf(double oldSsf) {
		this.oldSsf = oldSsf;
		this.twCorrectionRow.setPropertyValue("oldSsf", this.oldSsf);
	}

	public double getCurSsf() {
		return curSsf;
	}

	public void setCurSsf(double curSsf) {
		this.curSsf = curSsf;
		this.twCorrectionRow.setPropertyValue("curSsf", this.curSsf);
	}

	public double getNewSsf() {
		return newSsf;
	}

	public void setNewSsf(double newSsf) {
		this.newSsf = newSsf;
		this.twCorrectionRow.setPropertyValue("newSsf", this.newSsf);
	}

	public double getOldSf() {
		return oldSf;
	}

	public void setOldSf(double oldSf) {
		this.oldSf = oldSf;
		this.twCorrectionRow.setPropertyValue("oldSf", this.oldSf);
	}

	public double getCurSf() {
		return curSf;
	}

	public void setCurSf(double curSf) {
		this.curSf = curSf;
		this.twCorrectionRow.setPropertyValue("curSf", this.curSf);
	}

	public double getNewSf() {
		return newSf;
	}

	public void setNewSf(double newSf) {
		this.newSf = newSf;
		this.twCorrectionRow.setPropertyValue("newSf", this.newSf);
	}

	public double getOldListPrice() {
		return oldListPrice;
	}

	public void setOldListPrice(double oldListPrice) {
		this.oldListPrice = oldListPrice;
		this.twCorrectionRow.setPropertyValue("oldListPrice", this.oldListPrice);
	}

	public double getCurListPrice() {
		return curListPrice;
	}

	public void setCurListPrice(double curListPrice) {
		this.curListPrice = curListPrice;
		this.twCorrectionRow.setPropertyValue("curListPrice", this.curListPrice);
	}

	public double getNewListPrice() {
		return newListPrice;
	}

	public void setNewListPrice(double newListPrice) {
		this.newListPrice = newListPrice;
		this.twCorrectionRow.setPropertyValue("newListPrice", this.newListPrice);
	}

	public double getOldAwp() {
		return oldAwp;
	}

	public void setOldAwp(double oldAwp) {
		this.oldAwp = oldAwp;
		this.twCorrectionRow.setPropertyValue("oldAwp", this.oldAwp);
	}

	public double getCurAwp() {
		return curAwp;
	}

	public void setCurAwp(double curAwp) {
		this.curAwp = curAwp;
		this.twCorrectionRow.setPropertyValue("curAwp", this.curAwp);
	}

	public double getNewAwp() {
		return newAwp;
	}

	public void setNewAwp(double newAwp) {
		this.newAwp = newAwp;
		this.twCorrectionRow.setPropertyValue("newAwp", this.newAwp);
	}

	public double getOldOverridePrice() {
		return oldOverridePrice;
	}

	public void setOldOverridePrice(double oldOverridePrice) {
		this.oldOverridePrice = oldOverridePrice;
		this.twCorrectionRow.setPropertyValue("oldOverridePrice", this.oldOverridePrice);
	}

	public double getCurOverridePrice() {
		return curOverridePrice;
	}

	public void setCurOverridePrice(double curOverridePrice) {
		this.curOverridePrice = curOverridePrice;
		this.twCorrectionRow.setPropertyValue("curOverridePrice", this.curOverridePrice);
	}

	public double getNewOverridePrice() {
		return newOverridePrice;
	}

	public void setNewOverridePrice(double newOverridePrice) {
		this.newOverridePrice = newOverridePrice;
		this.twCorrectionRow.setPropertyValue("newOverridePrice", this.newOverridePrice);
	}

	public String getOrgDbtMemoId() {
		return orgDbtMemoId;
	}

	public void setOrgDbtMemoId(String orgDbtMemoId) {
		this.orgDbtMemoId = orgDbtMemoId;
		this.twCorrectionRow.setPropertyValue("orgDbtMemoId", this.orgDbtMemoId);
	}

	public double getOrgVendorAccAmt() {
		return orgVendorAccAmt;
	}

	public void setOrgVendorAccAmt(double orgVendorAccAmt) {
		this.orgVendorAccAmt = orgVendorAccAmt;
		this.twCorrectionRow.setPropertyValue("orgVendorAccAmt", this.orgVendorAccAmt);
	}

	public String getDeaNum() {
		return deaNum;
	}

	public void setDeaNum(String deaNum) {
		this.deaNum = deaNum;
		this.twCorrectionRow.setPropertyValue("deaNum", this.deaNum);
	}

	public String getHin() {
		return hin;
	}

	public void setHin(String hin) {
		this.hin = hin;
		this.twCorrectionRow.setPropertyValue("hin", this.hin);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
		this.twCorrectionRow.setPropertyValue("street", this.street);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		this.twCorrectionRow.setPropertyValue("city", this.city);
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
		this.twCorrectionRow.setPropertyValue("region", this.region);
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = Utility.trimLeadingChars(postalCode, "0");
		this.twCorrectionRow.setPropertyValue("postalCode", this.postalCode);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
		this.twCorrectionRow.setPropertyValue("country", this.country);
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
		this.twCorrectionRow.setPropertyValue("salesOrg", this.salesOrg);
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
		this.twCorrectionRow.setPropertyValue("orderType", this.orderType);
	}

	public String getNetBill() {
		return netBill;
	}

	public void setNetBill(String netBill) {
		this.netBill = netBill;
		this.twCorrectionRow.setPropertyValue("netBill", this.netBill);
	}

	public double getSellPriceExt() {
		return sellPriceExt;
	}

	public void setSellPriceExt(double sellPriceExt) {
		this.sellPriceExt = sellPriceExt;
		this.twCorrectionRow.setPropertyValue("sellPriceExt", this.sellPriceExt);
	}

	public double getTotChbk() {
		return totChbk;
	}

	public void setTotChbk(double totChbk) {
		this.totChbk = totChbk;
		this.twCorrectionRow.setPropertyValue("totChbk", this.totChbk);
	}

	public double getGxcb() {
		return gxcb;
	}

	public void setGxcb(double gxcb) {
		this.gxcb = gxcb;
		this.twCorrectionRow.setPropertyValue("gxcb", this.gxcb);
	}

	//TODO: Remove when API is updated
	public String getAddress() {
		return address;
	}

	//TODO: Remove when API is updated
	public void setAddress(String address) {
		this.address = address;
	}

}
