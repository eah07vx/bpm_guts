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

		public String toString() {
			String s = super.toString();
			return s + "pricingDate: " + this.getPricingDate() +  ", createdOn: " + this.getCreatedOn();
		}

		public Date getPricingDate() {
			return pricingDate;
		}

		public void setPricingDate(Date pricingDate) {
			this.pricingDate = pricingDate;
		}

		public Date getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
		}
}
