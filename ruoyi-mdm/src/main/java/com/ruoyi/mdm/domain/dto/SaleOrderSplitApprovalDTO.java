package com.ruoyi.mdm.domain.dto;

import com.ruoyi.mdm.domain.entity.ProductionOrder;
import com.ruoyi.mdm.domain.entity.SaleOrder;

import java.io.Serializable;
import java.util.List;

public class SaleOrderSplitApprovalDTO implements Serializable {

    private String taskId;

    private SaleOrder saleOrder;

    private List<ProductionOrder> productionOrderList;


    public SaleOrderSplitApprovalDTO(String taskId, SaleOrder saleOrder, List<ProductionOrder> productionOrderList) {
        this.taskId = taskId;
        this.saleOrder = saleOrder;
        this.productionOrderList = productionOrderList;
    }

    public List<ProductionOrder> getProductionOrderList() {
        return productionOrderList;
    }

    public void setProductionOrderList(List<ProductionOrder> productionOrderList) {
        this.productionOrderList = productionOrderList;
    }

    public SaleOrder getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
