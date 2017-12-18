/**
 * 
 */
package com.mck.crrb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import teamworks.TWObject;

/**
 * Uses the date format returned by Date.prototype.toJSON() which defaults to Date.prototype.toISOString() with format 'yyyy-MM-ddTHH:mm:ss.SSSSZ'
 * 
 * @author akatre
 *
 */
public class CorrectionRowISO extends CorrectionRow {

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private Date pricingDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private Date createdOn;

		public CorrectionRowISO() {
			super();
		} 

		public CorrectionRowISO(Object twCorrectionRow) {			
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

		@Override
		public String toString() {
			String s = super.toString();
			return s + "pricingDate: " + this.getPricingDate() +  ", createdOn: " + this.getCreatedOn();
		}
		
		@Override
		public Date getPricingDate() {
			return pricingDate;
		}

		@Override
		public void setPricingDate(Date pricingDate) {
			this.pricingDate = pricingDate;
			super.getTwCorrectionRow().setPropertyValue("pricingDate", this.pricingDate);
		}

		@Override
		public Date getCreatedOn() {
			return createdOn;
		}

		@Override
		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
			super.getTwCorrectionRow().setPropertyValue("createdOn", this.createdOn);
		}
}
