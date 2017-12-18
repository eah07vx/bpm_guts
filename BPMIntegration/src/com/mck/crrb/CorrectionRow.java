package com.mck.crrb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import teamworks.TWObject;

/**
 * Represents an invoice line item in the Correct Price table - 1 line per row
 * 
 * @author akatre
 *
 */
public class CorrectionRow extends _CorrectionRowNonDate {

		private Date pricingDate;
		private Date createdOn;

		public CorrectionRow() {
			super();
		} 
		
		public CorrectionRow(Object twCorrectionRow) {			
			super((TWObject)twCorrectionRow);
			TWObject twCorrRow = super.getTwCorrectionRow();
			String dateFormat = "yyyy-MM-dd";
			SimpleDateFormat dateFormater = new SimpleDateFormat(dateFormat);

			//TODO: Remove SOP debug
			try {
				System.out.println("CorrectionRow.pricingDate input string: " + twCorrRow.getPropertyValue("pricingDate").toString().substring(0, 9));
				this.setPricingDate(dateFormater.parse(twCorrRow.getPropertyValue("pricingDate").toString().substring(0, 9)));
				//TODO: Remove SOP debug
				System.out.println("CorrectionRow.createdOn input string: " + twCorrRow.getPropertyValue("createdOn").toString().substring(0, 9));
				this.setCreatedOn(dateFormater.parse(twCorrRow.getPropertyValue("createdOn").toString().substring(0, 9)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public Date getPricingDate() {
			return pricingDate;
		}

		public void setPricingDate(Date pricingDate) {
			this.pricingDate = pricingDate;
			super.getTwCorrectionRow().setPropertyValue("pricingDate", this.pricingDate);
		}

		public Date getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
			super.getTwCorrectionRow().setPropertyValue("createdOn", this.createdOn);
		}

		public String toString() {
			String s = super.toString();
			return s + ", pricingDate: " + this.getPricingDate() +  ", createdOn: " + this.getCreatedOn();
		}
}
