package com.mck.crrb;

import java.util.Date;

/**
 * @author akatre
 *
 */
public class CorrectionRowNonISO extends CorrectionRow {

		private Date pricingDate;
		private Date createdOn;

		public CorrectionRowNonISO() {
			super();
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
