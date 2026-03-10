package com.ruoyi.mdm.process;

import com.ruoyi.mdm.domain.entity.SaleOrder;
import com.ruoyi.mdm.service.ISaleOrderService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusRollback implements JavaDelegate {

    @Autowired
    private ISaleOrderService saleOrderService;

    @Override
    public void execute(DelegateExecution execution) {

        SaleOrder saleOrder = execution.getVariable("saleOrder", SaleOrder.class);
        boolean auditResult = execution.getVariable("auditResult",Boolean.class);

        if (auditResult) {
            saleOrder.setIsScheduled("Y");
        } else {
            saleOrder.setIsScheduled("N");
        }


        saleOrderService.updateSaleOrder(saleOrder);
    }

}
