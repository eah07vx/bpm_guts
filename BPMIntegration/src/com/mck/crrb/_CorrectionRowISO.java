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
public class _CorrectionRowISO extends _CorrectionRow {

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private Date pricingDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private Date createdOn;

		public _CorrectionRowISO() {
			super();
		} 

		public _CorrectionRowISO(Object twCorrectionRow) {			
			super((TWObject)twCorrectionRow);
			TWObject twCorrRow = super.getTwCorrectionRow();
			SimpleDateFormat dateFormater = new SimpleDateFormat(_API.SHORT_DASHED_DATE_FORMAT);

			try {
				//TODO: Remove SOP debug
				System.out.println("CorrectionRow.pricingDate input string: " + twCorrRow.getPropertyValue("pricingDate").toString().substring(0, 10));
				this.setPricingDate(dateFormater.parse(twCorrRow.getPropertyValue("pricingDate").toString().substring(0, 10)));
				//TODO: Remove SOP debug
				System.out.println("CorrectionRow.createdOn input string: " + twCorrRow.getPropertyValue("createdOn").toString().substring(0, 10));
				this.setCreatedOn(dateFormater.parse(twCorrRow.getPropertyValue("createdOn").toString().substring(0, 10)));
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		@Override
		public Date getPricingDate() {
			return pricingDate;
		}

		public void setPricingDate(String pricingDate) {
			SimpleDateFormat dateFormater = new SimpleDateFormat(_API.SHORT_DASHED_DATE_FORMAT);
			try {
				this.pricingDate = (pricingDate != null ? (dateFormater.parse(pricingDate.substring(0, 10))) : null);
				super.getTwCorrectionRow().setPropertyValue("pricingDate", this.pricingDate);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}			
		}

		@Override
		public Date getCreatedOn() {
			return createdOn;
		}

		public void setCreatedOn(String createdOn) {
			SimpleDateFormat dateFormater = new SimpleDateFormat(_API.SHORT_DASHED_DATE_FORMAT);
			try {
				this.createdOn = (createdOn != null ? (dateFormater.parse(createdOn.substring(0, 10))) : null);
				super.getTwCorrectionRow().setPropertyValue("createdOn", this.createdOn);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
}
