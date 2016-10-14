package com.mycompany.checkout.service.workflow;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.core.CustomerState;

import com.mycompany.profile.core.domain.HCCustomer;

import org.broadleafcommerce.core.catalog.domain.ProductAttribute;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.Order;

public class RecordHeatRangeActivity extends BaseActivity {
	private static final Log LOG = LogFactory.getLog(RecordHeatRangeActivity.class);

    @Resource(name = "blCustomerService")
    protected CustomerService customerService;

    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
        CheckoutSeed seed = (CheckoutSeed) context.getSeedData();
        Order order = seed.getOrder();
        int orderTotalHeatRating = 0;
        int productCount = 0;

        for (DiscreteOrderItem doi : order.getDiscreteOrderItems()) {
            ProductAttribute heatRating = doi.getProduct().getProductAttributes().get("heatRange");
            try {
                orderTotalHeatRating += (doi.getQuantity() * Integer.parseInt(heatRating.getValue()));
                productCount += doi.getQuantity();
            } catch (Exception e) {
                LOG.warn("Unable to parse the heat range. Product id: " + doi.getProduct().getId());
            }
        }

        HCCustomer customer = (HCCustomer) CustomerState.getCustomer();
        customer.setNumSaucesBought(customer.getNumSaucesBought() + productCount);
        customer.setTotalHeatRating(customer.getTotalHeatRating() + orderTotalHeatRating);
        customer = (HCCustomer) customerService.saveCustomer(customer);
        CustomerState.setCustomer(customer);

        return context;
    }
}
