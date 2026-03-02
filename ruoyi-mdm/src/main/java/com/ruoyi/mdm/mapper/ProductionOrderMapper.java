package com.ruoyi.mdm.mapper;

import java.util.List;

import com.ruoyi.mdm.domain.dto.ProductionOrderQueryDTO;
import com.ruoyi.mdm.domain.entity.ProductionOrder;

/**
 * 生产订单管理Mapper接口
 *
 * @author Frank Feng
 * @date 2025-12-28
 */
public interface ProductionOrderMapper 
{
    /**
     * 查询生产订单管理
     * 
     * @param id 生产订单管理主键
     * @return 生产订单管理
     */
    public ProductionOrder selectProductionOrderById(Long id);

    /**
     * 查询生产订单管理列表
     * 
     * @param productionOrder
     * @return 生产订单管理集合
     */
    public List<ProductionOrder> selectProductionOrderList(ProductionOrderQueryDTO productionOrder);

    /**
     * 新增生产订单管理
     * 
     * @param productionOrder 生产订单管理
     * @return 结果
     */
    public int insertProductionOrder(ProductionOrder productionOrder);

    /**
     * 修改生产订单管理
     * 
     * @param productionOrder 生产订单管理
     * @return 结果
     */
    public int updateProductionOrder(ProductionOrder productionOrder);

    /**
     * 删除生产订单管理
     * 
     * @param id 生产订单管理主键
     * @return 结果
     */
    public int deleteProductionOrderById(Long id);

    /**
     * 批量删除生产订单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductionOrderByIds(Long[] ids);

    /**
     * 新增：批量插入生产订单管理
     *
     * @param productionOrderList
     * @return 结果
     */
    public int insertProductionOrders(List<ProductionOrder> productionOrderList);
}
