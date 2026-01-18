package com.ruoyi.mdm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售订单对象 mdm_sale_order
 * 
 * @author ruoyi
 * @date 2026-01-18
 */
public class SaleOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售订单序号（主键） */
    private Long id;

    /** 下单日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;

    /** 是否加急生产：Y=是，N=否 */
    @Excel(name = "是否加急生产：Y=是，N=否")
    private String isUrgent;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 产品规格 */
    @Excel(name = "产品规格")
    private String specificationModel;

    /** 订单总重量（KG） */
    @Excel(name = "订单总重量", readConverterExp = "K=G")
    private BigDecimal totalWeightKg;

    /** 总交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "总交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date totalDeliveryDate;

    /** 订单金额（元） */
    @Excel(name = "订单金额", readConverterExp = "元=")
    private BigDecimal amount;

    /** 是否已排产：Y=是，N=否 */
    @Excel(name = "是否已排产：Y=是，N=否")
    private String isScheduled;

    /** 产品类别 */
    @Excel(name = "产品类别")
    private String productCategory;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOrderDate(Date orderDate) 
    {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() 
    {
        return orderDate;
    }

    public void setIsUrgent(String isUrgent) 
    {
        this.isUrgent = isUrgent;
    }

    public String getIsUrgent() 
    {
        return isUrgent;
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

    public void setTotalWeightKg(BigDecimal totalWeightKg) 
    {
        this.totalWeightKg = totalWeightKg;
    }

    public BigDecimal getTotalWeightKg() 
    {
        return totalWeightKg;
    }

    public void setTotalDeliveryDate(Date totalDeliveryDate) 
    {
        this.totalDeliveryDate = totalDeliveryDate;
    }

    public Date getTotalDeliveryDate() 
    {
        return totalDeliveryDate;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    public void setIsScheduled(String isScheduled) 
    {
        this.isScheduled = isScheduled;
    }

    public String getIsScheduled() 
    {
        return isScheduled;
    }

    public void setProductCategory(String productCategory) 
    {
        this.productCategory = productCategory;
    }

    public String getProductCategory() 
    {
        return productCategory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderDate", getOrderDate())
            .append("isUrgent", getIsUrgent())
            .append("customerName", getCustomerName())
            .append("specificationModel", getSpecificationModel())
            .append("totalWeightKg", getTotalWeightKg())
            .append("totalDeliveryDate", getTotalDeliveryDate())
            .append("amount", getAmount())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isScheduled", getIsScheduled())
            .append("productCategory", getProductCategory())
            .toString();
    }
}
