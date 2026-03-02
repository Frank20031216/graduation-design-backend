package com.ruoyi.mdm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.mdm.domain.entity.ProductionOrder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductionOrderQueryDTO implements Serializable {

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

    /** 搜索值 */
    @JsonIgnore
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    @Excel(name = "开始生产时间")
    private Date startDate;

    @Excel(name = "结束生产时间")
    private Date endDate;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer offset;

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

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public String getSearchValue()
    {
        return searchValue;
    }
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

     public Integer getOffset() {
        return offset;
    }
}
