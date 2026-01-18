package com.ruoyi.mdm.service;

import java.util.List;
import com.ruoyi.mdm.domain.SaleOrder;

/**
 * 销售订单Service接口
 * 
 * @author ruoyi
 * @date 2026-01-18
 */
public interface ISaleOrderService 
{
    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    public SaleOrder selectSaleOrderById(Long id);

    /**
     * 查询销售订单列表
     * 
     * @param saleOrder 销售订单
     * @return 销售订单集合
     */
    public List<SaleOrder> selectSaleOrderList(SaleOrder saleOrder);

    /**
     * 新增销售订单
     * 
     * @param saleOrder 销售订单
     * @return 结果
     */
    public int insertSaleOrder(SaleOrder saleOrder);

    /**
     * 修改销售订单
     * 
     * @param saleOrder 销售订单
     * @return 结果
     */
    public int updateSaleOrder(SaleOrder saleOrder);

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的销售订单主键集合
     * @return 结果
     */
    public int deleteSaleOrderByIds(Long[] ids);

    /**
     * 删除销售订单信息
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    public int deleteSaleOrderById(Long id);
}
