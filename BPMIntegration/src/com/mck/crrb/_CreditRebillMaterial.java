/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * CreditRebillMaterial represents the material on an invoice line item with pricing components, WAC, COG, contract reference, chargeback reference, etc.
 * 
 * @author akatre
 *
 */
public class _CreditRebillMaterial extends _Material {

	private String recordKey; // Consists of invoiceId-invoiceLineItemNum
	private Date pricingDate;
	private Date createdOn;
	private float rebillQty;
	private String uom;
	private String dc;
    private double newWac;
    private double newBid;
    private String newLead;
    private String newConRef;
    private String newCbRef;
    private float newContCogPer;
    private float newItemVarPer;
    private float newWacCogPer;
    private float newItemMkUpPer;
    private double newAwp;
    private double newAbd;
	private String newNoChargeBack;
	private double newChargeBack;
    private double newListPrice;
    private double newPrice; // Same as newSellPrice
    private double newOverridePrice;
    private double newSsf;
    private double newSf;
	private String newSellCd;
    private String newActivePrice;
	private String newNetBill;
	private String newProgType;
	private String newContrId;
	private String newContType;
	
    private double oldWac;
    private double oldBid;
    private String oldLead;
    private String oldConRef;
    private String oldCbRef; 
    private float oldContCogPer;
    private float oldItemVarPer;
    private float oldWacCogPer;
    private float oldItemMkUpPer;
    private double oldAwp;
	private double oldAbd;
	private String oldNoChargeBack;
	private double oldChargeBack;
    private double oldListPrice;
    private double oldPrice; // Same as oldSellPrice
    private double oldOverridePrice;
    private double oldSsf;
    private double oldSf;
	private String oldSellCd;
    private String oldActivePrice;
	private String oldNetBill;
	private String oldProgType;
	private String oldContrId;
	private String oldContType;

	private String manufacturer;
    //distribution channel
  	private String distrChan;   
	private String division;
	//PO Correction - PO numbers
	private String oldPurchaseOrder;
	private String newPurchaseOrder;
	//Account Switch - customer Ids
	private String oldCustomer;
	private String newCustomer;
	//Invoice information
	private String invoiceId;
	private String invoiceLineItemNum;
	//Original invoice information
	private String origInvoiceId;
	private String origInvoiceLineItemNum;
	private String prcGroup5;
	
    public String getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	public Date getPricingDate() {
		return pricingDate;
	}
	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
	}
	
	public float getRebillQty() {
		return rebillQty;
	}
	public void setRebillQty(float rebillQty) {
		this.rebillQty = rebillQty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public double getNewWac() {
		return newWac;
	}
	public void setNewWac(double newWac) {
		this.newWac = newWac;
	}
	public double getNewBid() {
		return newBid;
	}
	public void setNewBid(double newBid) {
		this.newBid = newBid;
	}
	public String getNewLead() {
		return newLead;
	}
	public void setNewLead(String newLead) {
		this.newLead = newLead;
	}
	public String getNewConRef() {
		return newConRef;
	}
	public void setNewConRef(String newConRef) {
		this.newConRef = newConRef;
	}
	public String getNewCbRef() {
		return newCbRef;
	}
	public void setNewCbRef(String newCbRef) {
		this.newCbRef = newCbRef;
	}
	public float getNewContCogPer() {
		return newContCogPer;
	}
	public void setNewContCogPer(float newContCogPer) {
		this.newContCogPer = newContCogPer;
	}
	public float getNewItemVarPer() {
		return newItemVarPer;
	}
	public void setNewItemVarPer(float newItemVarPer) {
		this.newItemVarPer = newItemVarPer;
	}
	public float getNewWacCogPer() {
		return newWacCogPer;
	}
	public void setNewWacCogPer(float newWacCogPer) {
		this.newWacCogPer = newWacCogPer;
	}
	public float getNewItemMkUpPer() {
		return newItemMkUpPer;
	}
	public void setNewItemMkUpPer(float newItemMkUpPer) {
		this.newItemMkUpPer = newItemMkUpPer;
	}
	public double getNewAwp() {
		return newAwp;
	}
	public void setNewAwp(double newAwp) {
		this.newAwp = newAwp;
	}
	public double getNewAbd() {
		return newAbd;
	}
	public void setNewAbd(double newAbd) {
		this.newAbd = newAbd;
	}
	public String getNewNoChargeBack() {
		return newNoChargeBack;
	}
	public void setNewNoChargeBack(String newNoChargeBack) {
		this.newNoChargeBack = newNoChargeBack;
	}
	public double getNewSellPrice() {
		return newPrice; // Same as newSellPrice
	}
	public void setNewSellPrice(double newSellPrice) {
		this.newPrice = newSellPrice; 
	}
	public double getNewChargeBack() {
		return newChargeBack;
	}
	public void setNewChargeBack(double newChargeBack) {
		this.newChargeBack = newChargeBack;
	}
	public double getNewListPrice() {
		return newListPrice;
	}
	public void setNewListPrice(double newListPrice) {
		this.newListPrice = newListPrice;
	}
	public double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}
	public double getNewOverridePrice() {
		return newOverridePrice;
	}
	public void setNewOverridePrice(double newOverridePrice) {
		this.newOverridePrice = newOverridePrice;
	}
	public double getNewSsf() {
		return newSsf;
	}
	public void setNewSsf(double newSsf) {
		this.newSsf = newSsf;
	}
	public double getNewSf() {
		return newSf;
	}
	public void setNewSf(double newSf) {
		this.newSf = newSf;
	}
	public String getNewSellCd() {
		return newSellCd;
	}
	public void setNewSellCd(String newSellCd) {
		this.newSellCd = newSellCd;
	}
	public String getNewActivePrice() {
		return newActivePrice;
	}
	public void setNewActivePrice(String newActivePrice) {
		this.newActivePrice = newActivePrice;
	}
	public String getNewNetBill() {
		return newNetBill;
	}
	public void setNewNetBill(String newNetBill) {
		this.newNetBill = newNetBill;
	}
	public String getNewProgType() {
		return newProgType;
	}
	public void setNewProgType(String newProgType) {
		this.newProgType = newProgType;
	}
	public String getNewContrId() {
		return newContrId;
	}
	public void setNewContrId(String newContrId) {
		this.newContrId = newContrId;
	}
	public String getNewContType() {
		return newContType;
	}
	public void setNewContType(String newContType) {
		this.newContType = newContType;
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
	public String getOldCbRef() {
		return oldCbRef;
	}
	public void setOldCbRef(String oldCbRef) {
		this.oldCbRef = oldCbRef;
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
	public double getOldAbd() {
		return oldAbd;
	}
	public void setOldAbd(double oldAbd) {
		this.oldAbd = oldAbd;
	}
	public String getOldNoChargeBack() {
		return oldNoChargeBack;
	}
	public void setOldNoChargeBack(String oldNoChargeBack) {
		this.oldNoChargeBack = oldNoChargeBack;
	}
	public double getOldChargeBack() {
		return oldChargeBack;
	}
	public void setOldChargeBack(double oldChargeBack) {
		this.oldChargeBack = oldChargeBack;
	}
	public double getOldListPrice() {
		return oldListPrice;
	}
	public void setOldListPrice(double oldListPrice) {
		this.oldListPrice = oldListPrice;
	}
	public double getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}
	public double getOldSellPrice() {
		return oldPrice;
	}
	public void setOldSellPrice(double oldSellPrice) {
		this.oldPrice = oldSellPrice;
	}
	public double getOldOverridePrice() {
		return oldOverridePrice;
	}
	public void setOldOverridePrice(double oldOverridePrice) {
		this.oldOverridePrice = oldOverridePrice;
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
	public String getOldNetBill() {
		return oldNetBill;
	}
	public void setOldNetBill(String oldNetBill) {
		this.oldNetBill = oldNetBill;
	}
	public String getOldProgType() {
		return oldProgType;
	}
	public void setOldProgType(String oldProgType) {
		this.oldProgType = oldProgType;
	}
	public String getOldContrId() {
		return oldContrId;
	}
	public void setOldContrId(String oldContrId) {
		this.oldContrId = oldContrId;
	}
	public String getOldContType() {
		return oldContType;
	}
	public void setOldContType(String oldContType) {
		this.oldContType = oldContType;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDistrChan() {
		return distrChan;
	}
	public void setDistrChan(String distrChan) {
		this.distrChan = distrChan;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getOldPurchaseOrder() {
		return oldPurchaseOrder;
	}
	public void setOldPurchaseOrder(String oldPurchaseOrder) {
		this.oldPurchaseOrder = oldPurchaseOrder;
	}
	public String getNewPurchaseOrder() {
		return newPurchaseOrder;
	}
	public void setNewPurchaseOrder(String newPurchaseOrder) {
		this.newPurchaseOrder = newPurchaseOrder;
	}
	public String getOldCustomer() {
		return oldCustomer;
	}
	public void setOldCustomer(String oldCustomer) {
		this.oldCustomer = oldCustomer;
	}
	public String getNewCustomer() {
		return newCustomer;
	}
	public void setNewCustomer(String newCustomer) {
		this.newCustomer = newCustomer;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceLineItemNum() {
		return invoiceLineItemNum;
	}
	public void setInvoiceLineItemNum(String invoiceLineItemNum) {
		this.invoiceLineItemNum = invoiceLineItemNum;
	}
	public String getOrigInvoiceId() {
		return origInvoiceId;
	}
	public void setOrigInvoiceId(String origInvoiceId) {
		this.origInvoiceId = origInvoiceId;
	}
	public String getOrigInvoiceLineItemNum() {
		return origInvoiceLineItemNum;
	}
	public void setOrigInvoiceLineItemNum(String origInvoiceLineItemNum) {
		this.origInvoiceLineItemNum = origInvoiceLineItemNum;
	}
	public String getPrcGroup5() {
		return prcGroup5;
	}
	public void setPrcGroup5(String prcGroup5) {
		this.prcGroup5 = prcGroup5;
	}
	@Override
	public String toString() {
		String str = "  recordKey: " + recordKey 
				+ ", rebillQty: " + rebillQty
				+ ", uom: " + uom
				+ ", dc: " + dc
				+ ", newWac: " + newWac
				+ ", newBid: " + newBid
				+ ", newLead: " + newLead
				+ ", newConRef: " + newConRef
				+ ", newCbRef: " + newCbRef
				+ ", newContCogPer: " + newContCogPer
				+ ", newItemVarPer: " + newItemVarPer
				+ ", newWacCogPer: " + newWacCogPer
				+ ", newItemMkUpPer: " + newItemMkUpPer
				+ ", newAwp: " + newAwp
				+ ", newAbd: " + newAbd
				+ ", newNoChargeBack: " + newNoChargeBack
				+ ", newChargeBack: " + newChargeBack
				+ ", newListPrice: " + newListPrice
				+ ", newPrice: " + newPrice
				+ ", newOverridePrice: " + newOverridePrice
				+ ", newSsf: " + newSsf
				+ ", newSf: " + newSf
				+ ", newSellCd: " + newSellCd
				+ ", newActivePrice: " + newActivePrice
				+ ", newNetBill: " + newNetBill
				+ ", newProgType: " + newProgType
				+ ", newContrId: " + newContrId
				+ ", newContType: " + newContType
				+ ", oldWac: " + oldWac
				+ ", oldBid: " + oldBid
				+ ", oldLead: " + oldLead
				+ ", oldConRef: " + oldConRef
				+ ", oldCbRef: " + oldCbRef
				+ ", oldContCogPer: " + oldContCogPer
				+ ", oldItemVarPer: " + oldItemVarPer
				+ ", oldWacCogPer: " + oldWacCogPer
				+ ", oldItemMkUpPer: " + oldItemMkUpPer
				+ ", oldAwp: " + oldAwp
				+ ", oldAbd: " + oldAbd
				+ ", oldNoChargeBack: " + oldNoChargeBack
				+ ", oldChargeBack: " + oldChargeBack
				+ ", oldListPrice: " + oldListPrice
				+ ", oldPrice: " + oldPrice
				+ ", oldOverridePrice: " + oldOverridePrice
				+ ", oldSsf: " + oldSsf
				+ ", oldSf: " + oldSf
				+ ", oldSellCd: " + oldSellCd
				+ ", oldActivePrice: " + oldActivePrice
				+ ", oldNetBill: " + oldNetBill
				+ ", oldProgType: " + oldProgType
				+ ", oldContrId: " + oldContrId
				+ ", oldContType: " + oldContType
				+ ", manufacturer: " + manufacturer
				+ ", distrChan: " + distrChan
				+ ", division: " + division
				+ ", oldPurchaseOrder: " + oldPurchaseOrder
				+ ", newPurchaseOrder: " + newPurchaseOrder
				+ ", oldCustomer: " + oldCustomer
				+ ", newCustomer: " + newCustomer
				+ ", invoiceId: " + invoiceId
				+ ", invoiceLineItemNum: " + invoiceLineItemNum
				+ ", origInvoiceId: " + origInvoiceId
				+ ", origInvoiceLineItemNum: " + origInvoiceLineItemNum
				+ ", prcGroup5: " + prcGroup5; 
		return str;
	}
}
