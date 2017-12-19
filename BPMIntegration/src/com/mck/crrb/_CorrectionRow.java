package com.mck.crrb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import teamworks.TWObject;
import teamworks.TWObjectFactory;

/**
 * Represents an invoice line item in the Correct Price table - 1 line per row
 * 
 * @author akatre
 *
 */
public class _CorrectionRow {
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
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	//private Date pricingDate;
	//supplier
	private String supplierId;
	private String supplierName;
	//quantity
	private float billQty;
	private float retQty;
	private float crQty;
	private float rebillQty;
	private String uom;
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	//private Date createdOn;
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
	//private String netBill;
	//Replaced with old and new NetBill fields
	private double sellPriceExt;
	private double totChbk;
	//private double gxcb;
	//Removed gxcb
	//Abd trifecta
	private String oldAbd;
	private String curAbd;
	private String newAbd;
	//net bill trifecta
	private String oldNetBill;
	private String curNetBill;
	private String newNetBill;
	//GX program type trifecta
	private String oldProgType;
	private String newProgType;
	private String curProgType;
	//control Id trifecta
	private String oldContrId;
	private String newContrId;
	private String curContrId;
	//cont type trifecta
	private String oldContType;
	private String newContType;
	private String curContType;
	//rebill customer trifecta
	private String oldRebillCust;
	private String newRebillCust;
	private String curRebillCust;
	//customer name trifecta (used for rebill)
	private String oldCustomerName;
	private String newCustomerName;
	private String curCustomerName;
	//distribution channel
	private String distrChan;
	private String division;
	private String docType;
	private String prcGroup5;
	//PO numbers
	private String oldPurchaseOrder;
	private String newPurchaseOrder;
	//manufacturer
	private String manufacturer;
	//Original invoice information
	private String origInvoiceId;
	private String origInvoiceLineItemNum;
	//IDT-Salesforce Case Number
	private String idtCaseNumber;
	//dates
	private Date pricingDate;
	private Date createdOn;

	public _CorrectionRow() {
		try {
			this.twCorrectionRow = TWObjectFactory.createObject();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	} 

	public _CorrectionRow(Object twCorrectionRow) {
		this.twCorrectionRow = (TWObject)twCorrectionRow;
		SimpleDateFormat dateFormater = new SimpleDateFormat(_API.SHORT_DASHED_DATE_FORMAT);

		this.invoiceId = this.twCorrectionRow.getPropertyValue("invoiceId").toString();
		this.invoiceId = this.twCorrectionRow.getPropertyValue("invoiceId").toString();
		this.invoiceLineItemNum = this.twCorrectionRow.getPropertyValue("invoiceLineItemNum").toString();
		this.customerId = this.twCorrectionRow.getPropertyValue("customerId").toString();
		this.customerName = this.twCorrectionRow.getPropertyValue("customerName").toString();
		this.materialId = this.twCorrectionRow.getPropertyValue("materialId").toString();
		this.materialName = this.twCorrectionRow.getPropertyValue("materialName").toString();
		this.supplierId = this.twCorrectionRow.getPropertyValue("supplierId").toString();
		this.supplierName = this.twCorrectionRow.getPropertyValue("supplierName").toString();
		this.billQty = Float.parseFloat(this.twCorrectionRow.getPropertyValue("billQty").toString());
		this.retQty = Float.parseFloat(this.twCorrectionRow.getPropertyValue("retQty").toString());
		this.crQty = Float.parseFloat(this.twCorrectionRow.getPropertyValue("crQty").toString());
		this.rebillQty = Float.parseFloat(this.twCorrectionRow.getPropertyValue("rebillQty").toString());
		this.uom = this.twCorrectionRow.getPropertyValue("uom").toString();
		this.dc = this.twCorrectionRow.getPropertyValue("dc").toString();
		this.ndcUpc = this.twCorrectionRow.getPropertyValue("ndcUpc").toString();
		this.billType = this.twCorrectionRow.getPropertyValue("billType").toString();
		this.chainId = this.twCorrectionRow.getPropertyValue("chainId").toString();
		this.chainName = this.twCorrectionRow.getPropertyValue("chainName").toString();
		this.groupId = this.twCorrectionRow.getPropertyValue("groupId").toString();
		this.groupName = this.twCorrectionRow.getPropertyValue("groupName").toString();
		this.subgroupId = this.twCorrectionRow.getPropertyValue("subgroupId").toString();
		this.subgroupName = this.twCorrectionRow.getPropertyValue("subgroupName").toString();
		this.reasonCode = this.twCorrectionRow.getPropertyValue("reasonCode").toString();
		this.poNumber = this.twCorrectionRow.getPropertyValue("poNumber").toString();
		this.oldPrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldPrice").toString());
		this.curPrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curPrice").toString());
		this.newPrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newPrice").toString());
		this.oldWac = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldWac").toString());
		this.curWac = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curWac").toString());
		this.newWac = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newWac").toString());
		this.oldBid = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldBid").toString());
		this.curBid = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curBid").toString());
		this.newBid = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newBid").toString());
		this.oldLead = this.twCorrectionRow.getPropertyValue("oldLead").toString();
		this.curLead = this.twCorrectionRow.getPropertyValue("curLead").toString();
		this.newLead = this.twCorrectionRow.getPropertyValue("newLead").toString();
		this.oldConRef = this.twCorrectionRow.getPropertyValue("oldConRef").toString();
		this.curConRef = this.twCorrectionRow.getPropertyValue("curConRef").toString();
		this.newConRef = this.twCorrectionRow.getPropertyValue("newConRef").toString();
		this.oldCbRef = this.twCorrectionRow.getPropertyValue("oldCbRef").toString();
		this.curCbRef = this.twCorrectionRow.getPropertyValue("curCbRef").toString();
		this.newCbRef = this.twCorrectionRow.getPropertyValue("newCbRef").toString();
		this.oldContCogPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("oldContCogPer").toString());
		this.curContCogPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("curContCogPer").toString());
		this.newContCogPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("newContCogPer").toString());
		this.oldItemVarPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("oldItemVarPer").toString());
		this.curItemVarPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("curItemVarPer").toString());
		this.newItemVarPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("newItemVarPer").toString());
		this.oldWacCogPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("oldWacCogPer").toString());
		this.curWacCogPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("curWacCogPer").toString());
		this.newWacCogPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("newWacCogPer").toString());
		this.oldItemMkUpPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("oldItemMkUpPer").toString());
		this.curItemMkUpPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("curItemMkUpPer").toString());
		this.newItemMkUpPer = Float.parseFloat(this.twCorrectionRow.getPropertyValue("newItemMkUpPer").toString());
		this.oldChargeBack = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldChargeBack").toString());
		this.curChargeBack = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curChargeBack").toString());
		this.newChargeBack = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newChargeBack").toString());
		this.oldSellCd = this.twCorrectionRow.getPropertyValue("oldSellCd").toString();
		this.curSellCd = this.twCorrectionRow.getPropertyValue("curSellCd").toString();
		this.newSellCd = this.twCorrectionRow.getPropertyValue("newSellCd").toString();
		this.oldNoChargeBack = this.twCorrectionRow.getPropertyValue("oldNoChargeBack").toString();
		this.curNoChargeBack = this.twCorrectionRow.getPropertyValue("curNoChargeBack").toString();
		this.newNoChargeBack = this.twCorrectionRow.getPropertyValue("newNoChargeBack").toString();
		this.oldActivePrice = this.twCorrectionRow.getPropertyValue("oldActivePrice").toString();
		this.curActivePrice = this.twCorrectionRow.getPropertyValue("curActivePrice").toString();
		this.newActivePrice = this.twCorrectionRow.getPropertyValue("newActivePrice").toString();
		this.oldSsf = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldSsf").toString());
		this.curSsf = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curSsf").toString());
		this.newSsf = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newSsf").toString());
		this.oldSf = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldSf").toString());
		this.curSf = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curSf").toString());
		this.newSf = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newSf").toString());
		this.oldListPrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldListPrice").toString());
		this.curListPrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curListPrice").toString());
		this.newListPrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newListPrice").toString());
		this.oldAwp = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldAwp").toString());
		this.curAwp = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curAwp").toString());
		this.newAwp = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newAwp").toString());
		this.oldOverridePrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("oldOverridePrice").toString());
		this.curOverridePrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("curOverridePrice").toString());
		this.newOverridePrice = Double.parseDouble(this.twCorrectionRow.getPropertyValue("newOverridePrice").toString());
		this.orgDbtMemoId = this.twCorrectionRow.getPropertyValue("orgDbtMemoId").toString();
		this.orgVendorAccAmt = Double.parseDouble(this.twCorrectionRow.getPropertyValue("orgVendorAccAmt").toString());
		this.deaNum = this.twCorrectionRow.getPropertyValue("deaNum").toString();
		this.hin = this.twCorrectionRow.getPropertyValue("hin").toString();
		this.street = this.twCorrectionRow.getPropertyValue("street").toString();
		this.city = this.twCorrectionRow.getPropertyValue("city").toString();
		this.region = this.twCorrectionRow.getPropertyValue("region").toString();
		this.postalCode = this.twCorrectionRow.getPropertyValue("postalCode").toString();
		this.country = this.twCorrectionRow.getPropertyValue("country").toString();
		this.salesOrg = this.twCorrectionRow.getPropertyValue("salesOrg").toString();
		this.orderType = this.twCorrectionRow.getPropertyValue("orderType").toString();
		this.sellPriceExt = Double.parseDouble(this.twCorrectionRow.getPropertyValue("sellPriceExt").toString());
		this.totChbk = Double.parseDouble(this.twCorrectionRow.getPropertyValue("totChbk").toString());
		this.oldAbd = this.twCorrectionRow.getPropertyValue("oldAbd").toString();
		this.curAbd = this.twCorrectionRow.getPropertyValue("curAbd").toString();
		this.newAbd = this.twCorrectionRow.getPropertyValue("newAbd").toString();
		this.oldNetBill = this.twCorrectionRow.getPropertyValue("oldNetBill").toString();
		this.curNetBill = this.twCorrectionRow.getPropertyValue("curNetBill").toString();
		this.newNetBill = this.twCorrectionRow.getPropertyValue("newNetBill").toString();
		this.oldProgType = this.twCorrectionRow.getPropertyValue("oldProgType").toString();
		this.newProgType = this.twCorrectionRow.getPropertyValue("newProgType").toString();
		this.curProgType = this.twCorrectionRow.getPropertyValue("curProgType").toString();
		this.oldContrId = this.twCorrectionRow.getPropertyValue("oldContrId").toString();
		this.newContrId = this.twCorrectionRow.getPropertyValue("newContrId").toString();
		this.curContrId = this.twCorrectionRow.getPropertyValue("curContrId").toString();
		this.oldContType = this.twCorrectionRow.getPropertyValue("oldContType").toString();
		this.newContType = this.twCorrectionRow.getPropertyValue("newContType").toString();
		this.curContType = this.twCorrectionRow.getPropertyValue("curContType").toString();
		this.oldRebillCust = this.twCorrectionRow.getPropertyValue("oldRebillCust").toString();
		this.newRebillCust = this.twCorrectionRow.getPropertyValue("newRebillCust").toString();
		this.curRebillCust = this.twCorrectionRow.getPropertyValue("curRebillCust").toString();
		this.oldCustomerName = this.twCorrectionRow.getPropertyValue("oldCustomerName").toString();
		this.newCustomerName = this.twCorrectionRow.getPropertyValue("newCustomerName").toString();
		this.curCustomerName = this.twCorrectionRow.getPropertyValue("curCustomerName").toString();
		this.distrChan = this.twCorrectionRow.getPropertyValue("distrChan").toString();
		this.division = this.twCorrectionRow.getPropertyValue("division").toString();
		this.docType = this.twCorrectionRow.getPropertyValue("docType").toString();
		this.prcGroup5 = this.twCorrectionRow.getPropertyValue("prcGroup5").toString();
		this.oldPurchaseOrder = this.twCorrectionRow.getPropertyValue("oldPurchaseOrder").toString();
		this.newPurchaseOrder = this.twCorrectionRow.getPropertyValue("newPurchaseOrder").toString();
		try {
			this.setPricingDate(dateFormater.parse(this.twCorrectionRow.getPropertyValue("pricingDate").toString().substring(0, 9)));
			this.setCreatedOn(dateFormater.parse(this.twCorrectionRow.getPropertyValue("createdOn").toString().substring(0, 9)));
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
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
		this.customerId = _Utility.trimLeadingChars(customerId, "0");
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
		this.materialId = _Utility.trimLeadingChars(materialId, "0");
		this.twCorrectionRow.setPropertyValue("materialId", this.materialId);
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
		this.twCorrectionRow.setPropertyValue("materialName", this.materialName);
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = _Utility.trimLeadingChars(supplierId, "0");
		this.twCorrectionRow.setPropertyValue("supplierId", this.supplierId);
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
		this.twCorrectionRow.setPropertyValue("supplierName", this.supplierName);
	}

	public float getBillQty() {
		return billQty;
	}

	public void setBillQty(float billQty) {
		this.billQty = billQty;
		this.twCorrectionRow.setPropertyValue("billQty", this.billQty);
	}

	public float getRetQty() {
		return retQty;
	}

	public void setRetQty(float retQty) {
		this.retQty = retQty;
		this.twCorrectionRow.setPropertyValue("retQty", this.retQty);
	}

	public float getCrQty() {
		return crQty;
	}

	public void setCrQty(float crQty) {
		this.crQty = crQty;
		this.twCorrectionRow.setPropertyValue("crQty", this.crQty);
	}

	public float getRebillQty() {
		return rebillQty;
	}

	public void setRebillQty(float rebillQty) {
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
		this.oldLead = _Utility.trimLeadingChars(oldLead, "0");
		this.twCorrectionRow.setPropertyValue("oldLead", this.oldLead);
	}

	public String getCurLead() {
		return curLead;
	}

	public void setCurLead(String curLead) {
		this.curLead = _Utility.trimLeadingChars(curLead, "0");
		this.twCorrectionRow.setPropertyValue("curLead", this.curLead);
	}

	public String getNewLead() {
		return newLead;
	}

	public void setNewLead(String newLead) {
		this.newLead = _Utility.trimLeadingChars(newLead, "0");
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
		this.postalCode = _Utility.trimLeadingChars(postalCode, "0");
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
/*
 * 20171214 akatre: Replaced with oldNetBill and newNetBill
 * 
	public String getNetBill() {
		return netBill;
	}

	public void setNetBill(String netBill) {
		this.netBill = netBill;
		this.twCorrectionRow.setPropertyValue("netBill", this.netBill);
	}
*/
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
/*
 *  20171214 akatre: Removed gxcb as per new mapping sheet
	public double getGxcb() {
		return gxcb;
	}

	public void setGxcb(double gxcb) {
		this.gxcb = gxcb;
		this.twCorrectionRow.setPropertyValue("gxcb", this.gxcb);
	}
*/
	public String getOldAbd() {
		return oldAbd;
	}

	public void setOldAbd(String oldAbd) {
		this.oldAbd = oldAbd;
		this.twCorrectionRow.setPropertyValue("oldAbd", this.oldAbd);
	}

	public String getCurAbd() {
		return curAbd;
	}

	public void setCurAbd(String curAbd) {
		this.curAbd = curAbd;
		this.twCorrectionRow.setPropertyValue("curAbd", this.curAbd);
	}

	public String getNewAbd() {
		return newAbd;
	}

	public void setNewAbd(String newAbd) {
		this.newAbd = newAbd;
		this.twCorrectionRow.setPropertyValue("newAbd", this.newAbd);
	}

	public String getOldNetBill() {
		return oldNetBill;
	}

	public void setOldNetBill(String oldNetBill) {
		this.oldNetBill = oldNetBill;
		this.twCorrectionRow.setPropertyValue("oldNetBill", this.oldNetBill);
	}

	public String getCurNetBill() {
		return curNetBill;
	}

	public void setCurNetBill(String curNetBill) {
		this.curNetBill = curNetBill;
		this.twCorrectionRow.setPropertyValue("curNetBill", this.curNetBill);
	}

	public String getNewNetBill() {
		return newNetBill;
	}

	public void setNewNetBill(String newNetBill) {
		this.newNetBill = newNetBill;
		this.twCorrectionRow.setPropertyValue("newNetBill", this.newNetBill);
	}

	public String getOldProgType() {
		return oldProgType;
	}

	public void setOldProgType(String oldProgType) {
		this.oldProgType = oldProgType;
		this.twCorrectionRow.setPropertyValue("oldProgType", this.oldProgType);
	}

	public String getNewProgType() {
		return newProgType;
	}

	public void setNewProgType(String newProgType) {
		this.newProgType = newProgType;
		this.twCorrectionRow.setPropertyValue("newProgType", this.newProgType);
	}

	public String getCurProgType() {
		return curProgType;
	}

	public void setCurProgType(String curProgType) {
		this.curProgType = curProgType;
		this.twCorrectionRow.setPropertyValue("curProgType", this.curProgType);
	}

	public String getOldContrId() {
		return oldContrId;
	}

	public void setOldContrId(String oldContrId) {
		this.oldContrId = oldContrId;
		this.twCorrectionRow.setPropertyValue("oldContrId", this.oldContrId);
	}

	public String getNewContrId() {
		return newContrId;
	}

	public void setNewContrId(String newContrId) {
		this.newContrId = newContrId;
		this.twCorrectionRow.setPropertyValue("newContrId", this.newContrId);
	}

	public String getCurContrId() {
		return curContrId;
	}

	public void setCurContrId(String curContrId) {
		this.curContrId = curContrId;
		this.twCorrectionRow.setPropertyValue("curContrId", this.curContrId);
	}

	public String getOldContType() {
		return oldContType;
	}

	public void setOldContType(String oldContType) {
		this.oldContType = oldContType;
		this.twCorrectionRow.setPropertyValue("oldContType", this.oldContType);
	}

	public String getNewContType() {
		return newContType;
	}

	public void setNewContType(String newContType) {
		this.newContType = newContType;
		this.twCorrectionRow.setPropertyValue("newContType", this.newContType);
	}

	public String getCurContType() {
		return curContType;
	}

	public void setCurContType(String curContType) {
		this.curContType = curContType;
		this.twCorrectionRow.setPropertyValue("curContType", this.curContType);
	}

	public String getOldRebillCust() {
		return oldRebillCust;
	}

	public void setOldRebillCust(String oldRebillCust) {
		this.oldRebillCust = oldRebillCust;
		this.twCorrectionRow.setPropertyValue("oldRebillCust", this.oldRebillCust);
	}

	public String getNewRebillCust() {
		return newRebillCust;
	}

	public void setNewRebillCust(String newRebillCust) {
		this.newRebillCust = newRebillCust;
		this.twCorrectionRow.setPropertyValue("newRebillCust", this.newRebillCust);
	}

	public String getCurRebillCust() {
		return curRebillCust;
	}

	public void setCurRebillCust(String curRebillCust) {
		this.curRebillCust = curRebillCust;
		this.twCorrectionRow.setPropertyValue("curRebillCust", this.curRebillCust);
	}

	public String getOldCustomerName() {
		return oldCustomerName;
	}

	public void setOldCustomerName(String oldCustomerName) {
		this.oldCustomerName = oldCustomerName;
		this.twCorrectionRow.setPropertyValue("oldCustomerName", this.oldCustomerName);
	}

	public String getNewCustomerName() {
		return newCustomerName;
	}

	public void setNewCustomerName(String newCustomerName) {
		this.newCustomerName = newCustomerName;
		this.twCorrectionRow.setPropertyValue("newCustomerName", this.newCustomerName);
	}

	public String getCurCustomerName() {
		return curCustomerName;
	}

	public void setCurCustomerName(String curCustomerName) {
		this.curCustomerName = curCustomerName;
		this.twCorrectionRow.setPropertyValue("curCustomerName", this.curCustomerName);
	}

	public String getDistrChan() {
		return distrChan;
	}

	public void setDistrChan(String distrChan) {
		this.distrChan = distrChan;
		this.twCorrectionRow.setPropertyValue("distrChan", this.distrChan);
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
		this.twCorrectionRow.setPropertyValue("division", this.division);
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
		this.twCorrectionRow.setPropertyValue("docType", this.docType);
	}

	public String getPrcGroup5() {
		return prcGroup5;
	}

	public void setPrcGroup5(String prcGroup5) {
		this.prcGroup5 = prcGroup5;
		this.twCorrectionRow.setPropertyValue("prcGroup5", this.prcGroup5);
	}

	public String getOldPurchaseOrder() {
		return oldPurchaseOrder;
	}

	public void setOldPurchaseOrder(String oldPurchaseOrder) {
		this.oldPurchaseOrder = oldPurchaseOrder;
		this.twCorrectionRow.setPropertyValue("oldPurchaseOrder", this.oldPurchaseOrder);
	}

	public String getNewPurchaseOrder() {
		return newPurchaseOrder;
	}

	public void setNewPurchaseOrder(String newPurchaseOrder) {
		this.newPurchaseOrder = newPurchaseOrder;
		this.twCorrectionRow.setPropertyValue("newPurchaseOrder", this.newPurchaseOrder);
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
		this.twCorrectionRow.setPropertyValue("manufacturer", this.manufacturer);
	}

	public String getOrigInvoiceId() {
		return origInvoiceId;
	}

	public void setOrigInvoiceId(String origInvoiceId) {
		this.origInvoiceId = origInvoiceId;
		this.twCorrectionRow.setPropertyValue("origInvoiceId", this.origInvoiceId);
	}

	public String getOrigInvoiceLineItemNum() {
		return origInvoiceLineItemNum;
	}

	public void setOrigInvoiceLineItemNum(String origInvoiceLineItemNum) {
		this.origInvoiceLineItemNum = origInvoiceLineItemNum;
		this.twCorrectionRow.setPropertyValue("origInvoiceLineItemNum", this.origInvoiceLineItemNum);
	}

	public String getIdtCaseNumber() {
		return idtCaseNumber;
	}

	public void setIdtCaseNumber(String idtCaseNumber) {
		this.idtCaseNumber = idtCaseNumber;
		this.twCorrectionRow.setPropertyValue("idtCaseNumber", this.idtCaseNumber);
	}

	public Date getPricingDate() {
		return pricingDate;
	}

	public void setPricingDate(Date pricingDate) {
		this.pricingDate = pricingDate;
		this.twCorrectionRow.setPropertyValue("pricingDate", this.pricingDate);
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		this.twCorrectionRow.setPropertyValue("createdOn", this.createdOn);
	}

	public String toString() {
		return "twCorrectionRow.size: " + this.twCorrectionRow.getPropertyNames().size() + 
				", invoiceId: " + this.invoiceId +  ", invoiceLineItemNum: " + this.invoiceLineItemNum +
				", customerId: " + this.customerId +  ", customerName: " + this.customerName +  
				", materialId: " + this.materialId +  ", materialName: " + this.materialName +
				", supplierId: " + this.supplierId +  ", supplierName: " + this.supplierName +  
				", billQty: " + this.billQty +  ", retQty: " + this.retQty +  ", crQty: " + this.crQty +  ", rebillQty: " + this.rebillQty + 
				", uom: " + this.uom + ", dc: " + this.dc +  ", ndcUpc: " + this.ndcUpc +
				", billType: " + this.billType +  ", chainId: " + this.chainId +  ", chainName: " + this.chainName +  
				", groupId: " + this.groupId +  ", groupName: " + this.groupName +  ", subgroupId: " + this.subgroupId +  ", subgroupName: " + this.subgroupName +  
				", reasonCode: " + this.reasonCode +  ", poNumber: " + this.poNumber +
				", oldPrice: " + this.oldPrice +  ", curPrice: " + this.curPrice +  ", newPrice: " + this.newPrice +  
				", oldWac: " + this.oldWac +  ", curWac: " + this.curWac +  ", newWac: " + this.newWac +  
				", oldBid: " + this.oldBid +  ", curBid: " + this.curBid +  
				", newBid: " + this.newBid +  ", oldLead: " + this.oldLead +  
				", curLead: " + this.curLead +  ", newLead: " + this.newLead +  ", oldConRef: " + this.oldConRef +  
				", curConRef: " + this.curConRef +  ", newConRef: " + this.newConRef +  
				", oldCbRef: " + this.oldCbRef +  ", curCbRef: " + this.curCbRef +  ", newCbRef: " + this.newCbRef +  
				", oldContCogPer: " + this.oldContCogPer +  ", curContCogPer: " + this.curContCogPer +  
				", newContCogPer: " + this.newContCogPer +  ", oldItemVarPer: " + this.oldItemVarPer +  
				", curItemVarPer: " + this.curItemVarPer +  ", newItemVarPer: " + this.newItemVarPer +  
				", oldWacCogPer: " + this.oldWacCogPer +  ", curWacCogPer: " + this.curWacCogPer + 
				", newWacCogPer: " + this.newWacCogPer +  ", oldItemMkUpPer: " + this.oldItemMkUpPer +  
				", curItemMkUpPer: " + this.curItemMkUpPer +  ", newItemMkUpPer: " + this.newItemMkUpPer +  
				", oldChargeBack: " + this.oldChargeBack +  ", curChargeBack: " + this.curChargeBack +  
				", newChargeBack: " + this.newChargeBack +  ", oldSellCd: " + this.oldSellCd +  
				", curSellCd: " + this.curSellCd +  ", newSellCd: " + this.newSellCd +  ", oldNoChargeBack: " + this.oldNoChargeBack +  
				", curNoChargeBack: " + this.curNoChargeBack +  
				", newNoChargeBack: " + this.newNoChargeBack +  ", oldActivePrice: " + this.oldActivePrice +  
				", curActivePrice: " + this.curActivePrice +  
				", newActivePrice: " + this.newActivePrice +  ", oldSsf: " + this.oldSsf +  
				", curSsf: " + this.curSsf +  ", newSsf: " + this.newSsf +  ", oldSf: " + this.oldSf +  
				", curSf: " + this.curSf +  ", newSf: " + this.newSf +  ", oldListPrice: " + this.oldListPrice +  
				", curListPrice: " + this.curListPrice +  
				", newListPrice: " + this.newListPrice +  ", oldAwp: " + this.oldAwp +  ", curAwp: " + this.curAwp +  ", newAwp: " + this.newAwp +  
				", oldOverridePrice: " + this.oldOverridePrice +  ", curOverridePrice: " + this.curOverridePrice +  ", newOverridePrice: " + this.newOverridePrice +  
				", orgDbtMemoId: " + this.orgDbtMemoId +  ", orgVendorAccAmt: " + this.orgVendorAccAmt +  
				", deaNum: " + this.deaNum +  ", hin: " + this.hin + 
				", street: " + this.street +  ", city: " + this.city +  ", region: " + this.region +  ", postalCode: " + this.postalCode +  ", country: " + this.country +  
				", salesOrg: " + this.salesOrg +  ", orderType: " + this.orderType +  ", sellPriceExt: " + this.sellPriceExt +  
				", totChbk: " + this.totChbk +  ", oldAbd: " + this.oldAbd +  ", curAbd: " + this.curAbd +  ", newAbd: " + this.newAbd +  
				", oldNetBill: " + this.oldNetBill +  ", newNetBill: " + this.newNetBill +  
				", oldProgType: " + this.oldProgType +  ", newProgType: " + this.newProgType +  ", curProgType: " + this.curProgType +
				", oldContrId: " + this.oldContrId +  ", newContrId: " + this.newContrId +  ", curContrId: " + this.curContrId + 
				", oldContType: " + this.oldContType +  ", newContType: " + this.newContType +  ", curContType: " + this.curContType + 
				", oldRebillCust: " + this.oldRebillCust +  ", newRebillCust: " + this.newRebillCust +  ", curRebillCust: " + this.curRebillCust +
				", oldCustomerName: " + this.oldCustomerName +  ", newCustomerName: " + this.newCustomerName +  ", curCustomerName: " + this.curCustomerName + 
				", distrChan: " + this.distrChan +  ", division: " + this.division +  ", docType: " + this.docType + 
				", prcGroup5: " + this.prcGroup5 + ", oldPurchaseOrder: " + this.oldPurchaseOrder + ", newPurchaseOrder: " + this.newPurchaseOrder +
				", manufacturer: " + this.manufacturer + ", origInvoiceId: " + origInvoiceId + ", origInvoiceLineItemNum: " + origInvoiceLineItemNum +
				", idtCaseNumber: " + idtCaseNumber + 
				", pricingDate: " + this.getPricingDate() + ", createdOn: " + this.getCreatedOn();
	}
}
