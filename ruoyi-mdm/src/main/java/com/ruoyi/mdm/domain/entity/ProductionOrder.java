package com.ruoyi.mdm.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生产订单管理对象 mdm_production_order
 *
 * @author Frank Feng
 * @date 2025-12-28
 */
public class ProductionOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    @Excel(name = "销售订单序号")
    private Long saleId;

    /** 下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;

    /** 产品类别 */
    @Excel(name = "产品类别")
    private String productCategory;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specificationModel;

    /** 重量(KG) */
    @Excel(name = "重量(KG)")
    private BigDecimal weightKg;

    /** 交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryDate;

    /** 是否优先生产 */
    @Excel(name = "是否优先生产")
    private String isPriority;

    /** 是否加急 */
    @Excel(name = "是否加急")
    private String isUrgent;

    /** 是否已生产 */
    @Excel(name = "是否已生产")
    private String isProduced;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSaleId(Long saleId)
    {
        this.saleId = saleId;
    }

    public Long getSaleId()
    {
        return saleId;
    }

    public void setOrderDate(Date orderDate) 
    {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() 
    {
        return orderDate;
    }

    public void setProductCategory(String productCategory) 
    {
        this.productCategory = productCategory;
    }

    public String getProductCategory() 
    {
        return productCategory;
    }

    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }

    public void setSpecificationModel(String specificationModel) 
    {
        this.specificationModel = specificationModel;
    }

    public String getSpecificationModel() 
    {
        return specificationModel;
    }

    public void setWeightKg(BigDecimal weightKg) 
    {
        this.weightKg = weightKg;
    }

    public BigDecimal getWeightKg() 
    {
        return weightKg;
    }

    public void setDeliveryDate(Date deliveryDate) 
    {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() 
    {
        return deliveryDate;
    }

    public void setIsPriority(String isPriority) 
    {
        this.isPriority = isPriority;
    }

    public String getIsPriority() 
    {
        return isPriority;
    }

    public void setIsUrgent(String isUrgent) 
    {
        this.isUrgent = isUrgent;
    }

    public String getIsUrgent() 
    {
        return isUrgent;
    }

    public void setIsProduced(String isProduced) 
    {
        this.isProduced = isProduced;
    }

    public String getIsProduced() 
    {
        return isProduced;
    }

    public void setProductionDate(Date productionDate) 
    {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() 
    {
        return productionDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("saleId", getSaleId())
            .append("orderDate", getOrderDate())
            .append("productCategory", getProductCategory())
            .append("customerName", getCustomerName())
            .append("specificationModel", getSpecificationModel())
            .append("weightKg", getWeightKg())
            .append("deliveryDate", getDeliveryDate())
            .append("isPriority", getIsPriority())
            .append("isUrgent", getIsUrgent())
            .append("isProduced", getIsProduced())
            .append("productionDate", getProductionDate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
