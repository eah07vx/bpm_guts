/**
 * 
 */
package com.mck.crrb;

import java.util.Date;

/**
 * @author akatre
 *
 * CreditRebillMaterial represents the material on an invoice line item with pricing components, WAC, COG, contract reference, chargeback reference, etc.
 */
public class CreditRebillMaterial extends Material {

	private String recordKey; // Consists of invoiceId-invoiceLineItemNum
	//TODO: Check why privingDate is needed at the material level if it is already at the header level?
	private Date pricingDate;
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
	private String newNoChargeBack;
	private double newSellPrice;
	private double newChargeBack;
    private double newListPrice;
    private String manufacturer;
    //TODO: Remove these fields if not expected in response. START:
    private double newOverridePrice;
    private double newSsf;
    private double newSf;
	private String newSellCd;
    private String newActivePrice;
    //TODO: END Remove these fields if not expected in response.
    
	public String getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
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
	public String getNewNoChargeBack() {
		return newNoChargeBack;
	}
	public void setNewNoChargeBack(String newNoChargeBack) {
		this.newNoChargeBack = newNoChargeBack;
	}
	public double getNewSellPrice() {
		return newSellPrice;
	}
	public void setNewSellPrice(double newSellPrice) {
		this.newSellPrice = newSellPrice;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
	@Override
	public String toString() {
		String str = "";
		str += "recordKey: " + recordKey 
			+ ", rebillQty: " + rebillQty 
			+ ", uom: " + uom 
			+ ", dc: " + dc 
			+ ", newWac: " + newWac 
			+ ", newBid: " + newBid 
			+ ", newLead: " + newLead 
			+ ", newConRef: " + newConRef 
			+ ", newCbRef : " + newCbRef  
			+ ", newContCogPer: " + newContCogPer 
			+ ", newItemVarPer: " + newItemVarPer 
			+ ", newWacCogPer: " + newWacCogPer 
			+ ", newItemMkUpPer: " + newItemMkUpPer 
			+ ", newAwp: " + newAwp 
			+ ", newNoChargeBack: " + newNoChargeBack 
			+ ", newSellPrice: " + newSellPrice 
			+ ", newChargeBack: " + newChargeBack 
			+ ", newListPrice: " + newListPrice 
			+ ", manufacturer: " + manufacturer;
		return str;
	}
}
