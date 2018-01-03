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
	private String customerName; // Same as the newly introduced oldCustomerName field
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
	private double oldAbd;
	private double curAbd;
	private double newAbd;
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
	private String oldCustomerName; // Same as customerName
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
	//IDT-External Case 
	private String idtCaseType;
	private String idtCaseNumber;
	private String submittedBy;
	private boolean ediSuppression;
	private String consolidatedPONumber;
	//Item DC Markup trifecta
	private float oldItemDcMarkUpPer;
	private float curItemDcMarkUpPer;
	private float newItemDcMarkUpPer;
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

		this.invoiceId = (String)this.twCorrectionRow.getPropertyValue("invoiceId");
		this.invoiceLineItemNum = (String)this.twCorrectionRow.getPropertyValue("invoiceLineItemNum");
		this.customerId = (String)this.twCorrectionRow.getPropertyValue("customerId");
		this.customerName = (String)this.twCorrectionRow.getPropertyValue("customerName");
		this.materialId = (String)this.twCorrectionRow.getPropertyValue("materialId");
		this.materialName = (String)this.twCorrectionRow.getPropertyValue("materialName");
		this.supplierId = (String)this.twCorrectionRow.getPropertyValue("supplierId");
		this.supplierName = (String)this.twCorrectionRow.getPropertyValue("supplierName");
		//TODO: Better parsing logic - account for null
		this.billQty = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("billQty"));
		this.retQty = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("retQty"));
		this.crQty = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("crQty"));
		this.rebillQty = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("rebillQty"));
		this.uom = (String)this.twCorrectionRow.getPropertyValue("uom");
		this.dc = (String)this.twCorrectionRow.getPropertyValue("dc");
		this.ndcUpc = (String)this.twCorrectionRow.getPropertyValue("ndcUpc");
		this.billType = (String)this.twCorrectionRow.getPropertyValue("billType");
		this.chainId = (String)this.twCorrectionRow.getPropertyValue("chainId");
		this.chainName = (String)this.twCorrectionRow.getPropertyValue("chainName");
		this.groupId = (String)this.twCorrectionRow.getPropertyValue("groupId");
		this.groupName = (String)this.twCorrectionRow.getPropertyValue("groupName");
		this.subgroupId = (String)this.twCorrectionRow.getPropertyValue("subgroupId");
		this.subgroupName = (String)this.twCorrectionRow.getPropertyValue("subgroupName");
		this.reasonCode = (String)this.twCorrectionRow.getPropertyValue("reasonCode");
		this.poNumber = (String)this.twCorrectionRow.getPropertyValue("poNumber");
		this.oldPrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldPrice"));
		this.curPrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curPrice"));
		this.newPrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newPrice"));
		this.oldWac = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldWac"));
		this.curWac = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curWac"));
		this.newWac = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newWac"));
		this.oldBid = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldBid"));
		this.curBid = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curBid"));
		this.newBid = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newBid"));
		this.oldLead = (String)this.twCorrectionRow.getPropertyValue("oldLead");
		this.curLead = (String)this.twCorrectionRow.getPropertyValue("curLead");
		this.newLead = (String)this.twCorrectionRow.getPropertyValue("newLead");
		this.oldConRef = (String)this.twCorrectionRow.getPropertyValue("oldConRef");
		this.curConRef = (String)this.twCorrectionRow.getPropertyValue("curConRef");
		this.newConRef = (String)this.twCorrectionRow.getPropertyValue("newConRef");
		this.oldCbRef = (String)this.twCorrectionRow.getPropertyValue("oldCbRef");
		this.curCbRef = (String)this.twCorrectionRow.getPropertyValue("curCbRef");
		this.newCbRef = (String)this.twCorrectionRow.getPropertyValue("newCbRef");
		this.oldContCogPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("oldContCogPer"));
		this.curContCogPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("curContCogPer"));
		this.newContCogPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("newContCogPer"));
		this.oldItemVarPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("oldItemVarPer"));
		this.curItemVarPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("curItemVarPer"));
		this.newItemVarPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("newItemVarPer"));
		this.oldWacCogPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("oldWacCogPer"));
		this.curWacCogPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("curWacCogPer"));
		this.newWacCogPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("newWacCogPer"));
		this.oldItemMkUpPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("oldItemMkUpPer"));
		this.curItemMkUpPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("curItemMkUpPer"));
		this.newItemMkUpPer = Float.parseFloat((String)this.twCorrectionRow.getPropertyValue("newItemMkUpPer"));
		this.oldChargeBack = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldChargeBack"));
		this.curChargeBack = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curChargeBack"));
		this.newChargeBack = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newChargeBack"));
		this.oldSellCd = (String)this.twCorrectionRow.getPropertyValue("oldSellCd");
		this.curSellCd = (String)this.twCorrectionRow.getPropertyValue("curSellCd");
		this.newSellCd = (String)this.twCorrectionRow.getPropertyValue("newSellCd");
		this.oldNoChargeBack = (String)this.twCorrectionRow.getPropertyValue("oldNoChargeBack");
		this.curNoChargeBack = (String)this.twCorrectionRow.getPropertyValue("curNoChargeBack");
		this.newNoChargeBack = (String)this.twCorrectionRow.getPropertyValue("newNoChargeBack");
		this.oldActivePrice = (String)this.twCorrectionRow.getPropertyValue("oldActivePrice");
		this.curActivePrice = (String)this.twCorrectionRow.getPropertyValue("curActivePrice");
		this.newActivePrice = (String)this.twCorrectionRow.getPropertyValue("newActivePrice");
		this.oldSsf = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldSsf"));
		this.curSsf = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curSsf"));
		this.newSsf = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newSsf"));
		this.oldSf = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldSf"));
		this.curSf = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curSf"));
		this.newSf = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newSf"));
		this.oldListPrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldListPrice"));
		this.curListPrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curListPrice"));
		this.newListPrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newListPrice"));
		this.oldAwp = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldAwp"));
		this.curAwp = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curAwp"));
		this.newAwp = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newAwp"));
		this.oldOverridePrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldOverridePrice"));
		this.curOverridePrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curOverridePrice"));
		this.newOverridePrice = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newOverridePrice"));
		this.orgDbtMemoId = (String)this.twCorrectionRow.getPropertyValue("orgDbtMemoId");
		this.orgVendorAccAmt = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("orgVendorAccAmt"));
		this.deaNum = (String)this.twCorrectionRow.getPropertyValue("deaNum");
		this.hin = (String)this.twCorrectionRow.getPropertyValue("hin");
		this.street = (String)this.twCorrectionRow.getPropertyValue("street");
		this.city = (String)this.twCorrectionRow.getPropertyValue("city");
		this.region = (String)this.twCorrectionRow.getPropertyValue("region");
		this.postalCode = (String)this.twCorrectionRow.getPropertyValue("postalCode");
		this.country = (String)this.twCorrectionRow.getPropertyValue("country");
		this.salesOrg = (String)this.twCorrectionRow.getPropertyValue("salesOrg");
		this.orderType = (String)this.twCorrectionRow.getPropertyValue("orderType");
		this.sellPriceExt = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("sellPriceExt"));
		this.totChbk = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("totChbk"));
		this.oldAbd = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("oldAbd"));
		this.curAbd = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("curAbd"));
		this.newAbd = Double.parseDouble((String)this.twCorrectionRow.getPropertyValue("newAbd"));
		this.oldNetBill = (String)this.twCorrectionRow.getPropertyValue("oldNetBill");
		this.curNetBill = (String)this.twCorrectionRow.getPropertyValue("curNetBill");
		this.newNetBill = (String)this.twCorrectionRow.getPropertyValue("newNetBill");
		this.oldProgType = (String)this.twCorrectionRow.getPropertyValue("oldProgType");
		this.newProgType = (String)this.twCorrectionRow.getPropertyValue("newProgType");
		this.curProgType = (String)this.twCorrectionRow.getPropertyValue("curProgType");
		this.oldContrId = (String)this.twCorrectionRow.getPropertyValue("oldContrId");
		this.newContrId = (String)this.twCorrectionRow.getPropertyValue("newContrId");
		this.curContrId = (String)this.twCorrectionRow.getPropertyValue("curContrId");
		this.oldContType = (String)this.twCorrectionRow.getPropertyValue("oldContType");
		this.newContType = (String)this.twCorrectionRow.getPropertyValue("newContType");
		this.curContType = (String)this.twCorrectionRow.getPropertyValue("curContType");
		this.oldRebillCust = (String)this.twCorrectionRow.getPropertyValue("oldRebillCust");
		this.newRebillCust = (String)this.twCorrectionRow.getPropertyValue("newRebillCust");
		this.curRebillCust = (String)this.twCorrectionRow.getPropertyValue("curRebillCust");
		this.oldCustomerName = (String)this.twCorrectionRow.getPropertyValue("oldCustomerName");
		this.newCustomerName = (String)this.twCorrectionRow.getPropertyValue("newCustomerName");
		this.curCustomerName = (String)this.twCorrectionRow.getPropertyValue("curCustomerName");
		this.distrChan = (String)this.twCorrectionRow.getPropertyValue("distrChan");
		this.division = (String)this.twCorrectionRow.getPropertyValue("division");
		this.docType = (String)this.twCorrectionRow.getPropertyValue("docType");
		this.prcGroup5 = (String)this.twCorrectionRow.getPropertyValue("prcGroup5");
		this.submittedBy = (String)this.twCorrectionRow.getPropertyValue("submittedBy");
		this.oldPurchaseOrder = (String)this.twCorrectionRow.getPropertyValue("oldPurchaseOrder");
		this.newPurchaseOrder = (String)this.twCorrectionRow.getPropertyValue("newPurchaseOrder");
		this.origInvoiceId = (String)this.twCorrectionRow.getPropertyValue("origInvoiceId");
		this.origInvoiceLineItemNum = (String)this.twCorrectionRow.getPropertyValue("origInvoiceLineItemNum");
		
		try {
			String tmp = (String)this.twCorrectionRow.getPropertyValue("pricingDate");
			if (tmp != null) {
				this.setPricingDate(dateFormater.parse(tmp.substring(0, 10)));
			}
			tmp = (String)this.twCorrectionRow.getPropertyValue("createdOn");
			if (tmp != null) {
				this.setCreatedOn(dateFormater.parse(tmp.substring(0, 10)));
			}
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

	public void setCustomerName(String customerName) { // Set oldCustomerName and customerName to same value
		this.customerName = customerName;
		this.oldCustomerName = customerName;
		this.twCorrectionRow.setPropertyValue("customerName", this.customerName);
		this.twCorrectionRow.setPropertyValue("oldCustomerName", this.customerName);
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
	public double getOldAbd() {
		return oldAbd;
	}

	public void setOldAbd(double oldAbd) {
		this.oldAbd = oldAbd;
		this.twCorrectionRow.setPropertyValue("oldAbd", this.oldAbd);
	}

	public double getCurAbd() {
		return curAbd;
	}

	public void setCurAbd(double curAbd) {
		this.curAbd = curAbd;
		this.twCorrectionRow.setPropertyValue("curAbd", this.curAbd);
	}

	public double getNewAbd() {
		return newAbd;
	}

	public void setNewAbd(double newAbd) {
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

	public void setOldCustomerName(String oldCustomerName) { // Set oldCustomerName and customerName to same value
		this.customerName = oldCustomerName;
		this.oldCustomerName = oldCustomerName;
		this.twCorrectionRow.setPropertyValue("customerName", this.oldCustomerName);
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

	public String getIdtCaseType() {
		return idtCaseType;
	}

	public void setIdtCaseType(String idtCaseType) {
		this.idtCaseType = idtCaseType;
		this.twCorrectionRow.setPropertyValue("idtCaseType", this.idtCaseType);
	}

	public String getIdtCaseNumber() {
		return idtCaseNumber;
	}

	public void setIdtCaseNumber(String idtCaseNumber) {
		this.idtCaseNumber = idtCaseNumber;
		this.twCorrectionRow.setPropertyValue("idtCaseNumber", this.idtCaseNumber);
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
		this.twCorrectionRow.setPropertyValue("submittedBy", this.submittedBy);
	}

	public boolean isEdiSuppression() {
		return ediSuppression;
	}

	public void setEdiSuppression(boolean ediSuppression) {
		this.ediSuppression = ediSuppression;
		this.twCorrectionRow.setPropertyValue("ediSuppression", this.ediSuppression);
	}

	public String getConsolidatedPONumber() {
		return consolidatedPONumber;
	}

	public void setConsolidatedPONumber(String consolidatedPONumber) {
		this.consolidatedPONumber = consolidatedPONumber;
	}

	public float getOldItemDcMarkUpPer() {
		return oldItemDcMarkUpPer;
	}

	public void setOldItemDcMarkUpPer(float oldItemDcMarkUpPer) {
		this.oldItemDcMarkUpPer = oldItemDcMarkUpPer;
	}

	public float getCurItemDcMarkUpPer() {
		return curItemDcMarkUpPer;
	}

	public void setCurItemDcMarkUpPer(float curItemDcMarkUpPer) {
		this.curItemDcMarkUpPer = curItemDcMarkUpPer;
	}

	public float getNewItemDcMarkUpPer() {
		return newItemDcMarkUpPer;
	}

	public void setNewItemDcMarkUpPer(float newItemDcMarkUpPer) {
		this.newItemDcMarkUpPer = newItemDcMarkUpPer;
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
				", idtCaseType: " + idtCaseType + ", idtCaseNumber: " + idtCaseNumber + ", submittedBy: " + submittedBy + 
				", ediSuppression: " + ediSuppression + ", consolidatedPONumber: " + consolidatedPONumber +
				", oldItemDcMarkUpPer : " + oldItemDcMarkUpPer + ", curItemDcMarkUpPer: " + curItemDcMarkUpPer +  
				", newItemDcMarkUpPer: " + newItemDcMarkUpPer +
				", pricingDate: " + this.getPricingDate() + ", createdOn: " + this.getCreatedOn();
	}
}
