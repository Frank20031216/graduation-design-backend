package com.ruoyi.mdm.event;

import org.springframework.context.ApplicationEvent;
import com.ruoyi.mdm.domain.entity.ProductionOrder;

/**
 * 生产订单变更事件
 */
public class ProductionOrderChangeEvent extends ApplicationEvent {

    // 变更类型：INSERT/UPDATE/DELETE
    private String changeType;

    public ProductionOrderChangeEvent(ProductionOrder source, String changeType) {
        super(source);
        this.changeType = changeType;
    }

    // getter/setter
    public ProductionOrder getProductionOrder() {
        return (ProductionOrder) super.getSource();
    }

    public String getChangeType() {
        return changeType;
    }
}