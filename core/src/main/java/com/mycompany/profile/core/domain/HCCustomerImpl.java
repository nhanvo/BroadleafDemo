package com.mycompany.profile.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.broadleafcommerce.profile.core.domain.CustomerImpl;

@Entity
@Table(name = "HC_CUSTOMER")
@PrimaryKeyJoinColumn(name = "CUSTOMER_ID")
public class HCCustomerImpl extends CustomerImpl implements HCCustomer {
	
	private static final long serialVersionUID = 6545097668293683751L;
	
	@Column(name = "NUM_SAUCES_BOUGHT")
    protected Integer numSaucesBought = 0;

    @Column(name = "TOTAL_HEAT_RATING")
    protected Integer totalHeatRating = 0;
    
    @Override
    public Integer getAverageHeatRatingBought() {
        if (numSaucesBought == null || numSaucesBought == 0) {
            return 0;
        }
        return totalHeatRating / numSaucesBought;
    }

    @Override
    public Integer getNumSaucesBought() {
        return numSaucesBought;
    }

    @Override
    public void setNumSaucesBought(Integer numSaucesBought) {
        this.numSaucesBought = numSaucesBought;
    }

    @Override
    public Integer getTotalHeatRating() {
        return totalHeatRating;
    }

    @Override
    public void setTotalHeatRating(Integer totalHeatRating) {
        this.totalHeatRating = totalHeatRating;
    }
	
}
