package com.mycompany.order.service.workflow.add;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductAttribute;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;

public class ValidateHeatRangeRestrictionActivity extends BaseActivity {
	private static final Log LOG = LogFactory.getLog(ValidateHeatRangeRestrictionActivity.class);

    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;

    @Override
    public ProcessContext execute(ProcessContext context) throws Exception {
        // Get our seed data
        CartOperationRequest request = (CartOperationRequest) context.getSeedData();
        Long skuId = request.getItemRequest().getSkuId();
        Order cart = request.getOrder();

        Sku sku = catalogService.findSkuById(skuId);
        Product product = sku.getProduct();
        Integer heatRange = getHeatRange(product);

        // Heat Range will be null for non-hot sauce products
        if (heatRange != null && heatRange == 1) {
            for (DiscreteOrderItem doi : cart.getDiscreteOrderItems()) {
                Integer doiHeatRange = getHeatRange(doi.getProduct());
                if (doiHeatRange != null && doiHeatRange == 5) {
                    throw new InvalidSauceHeatRangeException("Trying to add heat range 1 when heat range 5 in cart");
                }
            }
        }

        return context;
    }

    /**
     * @return the heatRange attribute of a product if it exists -- null otherwise
     */
    protected Integer getHeatRange(Product product) {
        ProductAttribute heatRangeAttr = product.getProductAttributes().get("heatRange");

        // Heat Range will be null for non-hot sauce products
        if (heatRangeAttr != null) {
            try {
                return Integer.parseInt(heatRangeAttr.getValue());
            } catch (Exception e) { 
                // All The Exceptions!! (We'll return null, do nothing)
            }
        }

        return null;
    }
}
