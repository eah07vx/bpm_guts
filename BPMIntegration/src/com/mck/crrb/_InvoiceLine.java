/**
 * 
 */
package com.mck.crrb;
/*
import java.text.ParseException;
import java.text.SimpleDateFormat;
*/
import java.util.Date;

//import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author akatre
 *
 */
public class _InvoiceLine {
	private String oldChargeBack;
	private String deaNum;
	private String chainId;
	private String billQty;
	private String oldWacCogPer;
	private String oldWac;
	private String sellPriceExt;
	private String totChbk;
	private String retQty;
	private String ndcUpc;
	private String oldLead;
	private String oldItemMkUpPer;
	private String invoiceId;
	private String groupId;
	private String oldAwp;
	private String uom;
	private String netBill;
	private String salesOrg;
	private Date createdOn;
	private String customerId;
	private String oldPrice;
	private String oldBid;
	private String poNumber;
	private String subgroupId;
	private String oldSellCd;
	private String oldContCogPer;
	private Date pricingDate;
	private String billType;
	private String chainName;
	private String groupName;
	private String orderType;
	private String gxcb;
	private String oldCbRef;
	private String invoiceLineItemNum;
	private String oldNoChargeBack;
	private String orgDbtMemoId;
	private String materialName;
	private String materialId;
	private String orgVendorAccAmt;
	private String oldSf;
	private String crQty;
	private String oldActivePrice;
	private String oldSsf;
	private String customerName;
	private String subgroupName;
	private String dc;
	private String oldConRef;
	private String rebillQty;
	private String oldListPrice;
	private String supplierId;
	private String supNm;

	public String getOldChargeBack() {
		return oldChargeBack;
	}

	public void setOldChargeBack(String oldChargeBack) {
		this.oldChargeBack = oldChargeBack;
	}

	public String getDeaNum() {
		return deaNum;
	}

	public void setDeaNum(String deaNum) {
		this.deaNum = deaNum;
	}

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	public String getBillQty() {
		return billQty;
	}

	public void setBillQty(String billQty) {
		this.billQty = billQty;
	}

	public String getOldWacCogPer() {
		return oldWacCogPer;
	}

	public void setOldWacCogPer(String oldWacCogPer) {
		this.oldWacCogPer = oldWacCogPer;
	}

	public String getOldWac() {
		return oldWac;
	}

	public void setOldWac(String oldWac) {
		this.oldWac = oldWac;
	}

	public String getSellPriceExt() {
		return sellPriceExt;
	}

	public void setSellPriceExt(String sellPriceExt) {
		this.sellPriceExt = sellPriceExt;
	}

	public String getTotChbk() {
		return totChbk;
	}

	public void setTotChbk(String totChbk) {
		this.totChbk = totChbk;
	}

	public String getRetQty() {
		return retQty;
	}

	public void setRetQty(String retQty) {
		this.retQty = retQty;
	}

	public String getNdcUpc() {
		return ndcUpc;
	}

	public void setNdcUpc(String ndcUpc) {
		this.ndcUpc = ndcUpc;
	}

	public String getOldLead() {
		return oldLead;
	}

	public void setOldLead(String oldLead) {
		this.oldLead = oldLead;
	}

	public String getOldItemMkUpPer() {
		return oldItemMkUpPer;
	}

	public void setOldItemMkUpPer(String oldItemMkUpPer) {
		this.oldItemMkUpPer = oldItemMkUpPer;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOldAwp() {
		return oldAwp;
	}

	public void setOldAwp(String oldAwp) {
		this.oldAwp = oldAwp;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getNetBill() {
		return netBill;
	}

	public void setNetBill(String netBill) {
		this.netBill = netBill;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getOldBid() {
		return oldBid;
	}

	public void setOldBid(String oldBid) {
		this.oldBid = oldBid;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(String subgroupId) {
		this.subgroupId = subgroupId;
	}

	public String getOldSellCd() {
		return oldSellCd;
	}

	public void setOldSellCd(String oldSellCd) {
		this.oldSellCd = oldSellCd;
	}

	public String getOldContCogPer() {
		return oldContCogPer;
	}

	public void setOldContCogPer(String oldContCogPer) {
		this.oldContCogPer = oldContCogPer;
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			this.pricingDate = sdf.parse(pricingDate);
			//System.out.println("Input: " + pricingDate + "; Output: " + this.pricingDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getGxcb() {
		return gxcb;
	}

	public void setGxcb(String gxcb) {
		this.gxcb = gxcb;
	}

	public String getOldCbRef() {
		return oldCbRef;
	}

	public void setOldCbRef(String oldCbRef) {
		this.oldCbRef = oldCbRef;
	}

	public String getInvoiceLineItemNum() {
		return invoiceLineItemNum;
	}

	public void setInvoiceLineItemNum(String invoiceLineItemNum) {
		this.invoiceLineItemNum = invoiceLineItemNum;
	}

	public String getOldNoChargeBack() {
		return oldNoChargeBack;
	}

	public void setOldNoChargeBack(String oldNoChargeBack) {
		this.oldNoChargeBack = oldNoChargeBack;
	}

	public String getOrgDbtMemoId() {
		return orgDbtMemoId;
	}

	public void setOrgDbtMemoId(String orgDbtMemoId) {
		this.orgDbtMemoId = orgDbtMemoId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getOrgVendorAccAmt() {
		return orgVendorAccAmt;
	}

	public void setOrgVendorAccAmt(String orgVendorAccAmt) {
		this.orgVendorAccAmt = orgVendorAccAmt;
	}

	public String getOldSf() {
		return oldSf;
	}

	public void setOldSf(String oldSf) {
		this.oldSf = oldSf;
	}

	public String getCrQty() {
		return crQty;
	}

	public void setCrQty(String crQty) {
		this.crQty = crQty;
	}

	public String getOldActivePrice() {
		return oldActivePrice;
	}

	public void setOldActivePrice(String oldActivePrice) {
		this.oldActivePrice = oldActivePrice;
	}

	public String getOldSsf() {
		return oldSsf;
	}

	public void setOldSsf(String oldSsf) {
		this.oldSsf = oldSsf;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSubgroupName() {
		return subgroupName;
	}

	public void setSubgroupName(String subgroupName) {
		this.subgroupName = subgroupName;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getOldConRef() {
		return oldConRef;
	}

	public void setOldConRef(String oldConRef) {
		this.oldConRef = oldConRef;
	}

	public String getRebillQty() {
		return rebillQty;
	}

	public void setRebillQty(String rebillQty) {
		this.rebillQty = rebillQty;
	}

	public String getOldListPrice() {
		return oldListPrice;
	}

	public void setOldListPrice(String oldListPrice) {
		this.oldListPrice = oldListPrice;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	//@JsonProperty("supplierName")
	public String getSupNm() {
		return supNm;
	}

	public void setSupplierName(String sn) {
		this.supNm = sn;
	}
	
	public String toString() {
		return this.billQty + ", " + this.billType + ", " + this.chainId + ", " + this.chainName + ", " + this.createdOn + ", " + this.crQty + ", " + this.customerId + ", " + this.customerName + ", " + this.dc + ", " + this.deaNum + ", " + this.groupId + ", " + this.groupName + ", " + this.gxcb + ", " + this.invoiceId + ", " + this.invoiceLineItemNum + ", " + this.materialId + ", " + this.materialName + ", " + this.ndcUpc + ", " + this.netBill + ", " + this.oldActivePrice + ", " + this.oldAwp + ", " + this.oldBid + ", " + this.oldCbRef + ", " + this.oldChargeBack + ", " + this.oldConRef + ", " + this.oldContCogPer + ", " + this.oldItemMkUpPer + ", " + this.oldLead + ", " + this.oldListPrice + ", " + this.oldNoChargeBack + ", " + this.oldPrice + ", " + this.oldSellCd + ", " + this.oldSf + ", " + this.oldSsf + ", " + this.oldWac + ", " + this.oldWacCogPer + ", " + this.orderType + ", " + this.orgDbtMemoId + ", " + this.orgVendorAccAmt + ", " + this.poNumber + ", " + this.pricingDate + ", " + this.rebillQty + ", " + this.retQty + ", " + this.salesOrg + ", " + this.sellPriceExt + ", " + this.subgroupId + ", " + this.subgroupName + ", " + this.supplierId + ", " + this.supNm + ", " + this.totChbk + ", " + this.uom;
	}
}
