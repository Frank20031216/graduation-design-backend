package com.ruoyi.mdm.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mdm.mapper.ProductionOrderMapper;
import com.ruoyi.mdm.domain.ProductionOrder;
import com.ruoyi.mdm.service.IProductionOrderService;

/**
 * 生产订单管理Service业务层处理
 *
 * @author Frank Feng
 * @date 2025-12-28
 */
@Service
public class ProductionOrderServiceImpl implements IProductionOrderService {
    @Autowired
    private ProductionOrderMapper productionOrderMapper;

    /**
     * 查询生产订单管理
     *
     * @param id 生产订单管理主键
     * @return 生产订单管理
     */
    @Override
    public ProductionOrder selectProductionOrderById(Long id) {
        return productionOrderMapper.selectProductionOrderById(id);
    }

    /**
     * 查询生产订单管理列表
     *
     * @param productionOrder 生产订单管理
     * @return 生产订单管理
     */
    @Override
    public List<ProductionOrder> selectProductionOrderList(ProductionOrder productionOrder) {
        return productionOrderMapper.selectProductionOrderList(productionOrder);
    }

    /**
     * 新增生产订单管理
     *
     * @param productionOrder 生产订单管理
     * @return 结果
     */
    @Override
    public int insertProductionOrder(ProductionOrder productionOrder) {
        productionOrder.setCreateTime(DateUtils.getNowDate());
        return productionOrderMapper.insertProductionOrder(productionOrder);
    }

    /**
     * 修改生产订单管理
     *
     * @param productionOrder 生产订单管理
     * @return 结果
     */
    @Override
    public int updateProductionOrder(ProductionOrder productionOrder) {
        productionOrder.setUpdateTime(DateUtils.getNowDate());
        return productionOrderMapper.updateProductionOrder(productionOrder);
    }

    /**
     * 批量删除生产订单管理
     *
     * @param ids 需要删除的生产订单管理主键
     * @return 结果
     */
    @Override
    public int deleteProductionOrderByIds(Long[] ids) {
        return productionOrderMapper.deleteProductionOrderByIds(ids);
    }

    /**
     * 删除生产订单管理信息
     *
     * @param id 生产订单管理主键
     * @return 结果
     */
    @Override
    public int deleteProductionOrderById(Long id) {
        return productionOrderMapper.deleteProductionOrderById(id);
    }

    /**
     * 新增：批量插入生产订单管理
     *
     * @param productionOrderList
     * @return 结果
     */
    @Override
    public int insertProductionOrders(List<ProductionOrder> productionOrderList) {
        return productionOrderMapper.insertProductionOrders(productionOrderList);
    }
}
