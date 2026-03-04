package com.ruoyi.mdm.process;

import com.ruoyi.mdm.domain.entity.ProductionOrder;
import com.ruoyi.mdm.service.IProductionOrderService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchCreateProductionOrders implements JavaDelegate {

    @Autowired
    private IProductionOrderService productionOrderService;

    private final Logger log = LoggerFactory.getLogger(BatchCreateProductionOrders.class);

    @Override
    public void execute(DelegateExecution execution) {

        List<ProductionOrder> orders = execution.getVariable("productionOrderList", List.class);

        log.info("获取流程变量: {}", orders);

        // 调用服务层方法批量创建生产订单
        productionOrderService.insertProductionOrders(orders);
    }
}
