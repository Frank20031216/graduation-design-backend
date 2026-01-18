package com.ruoyi.mdm.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mdm.mapper.SaleOrderMapper;
import com.ruoyi.mdm.domain.SaleOrder;
import com.ruoyi.mdm.service.ISaleOrderService;

/**
 * 销售订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-18
 */
@Service
public class SaleOrderServiceImpl implements ISaleOrderService 
{
    @Autowired
    private SaleOrderMapper saleOrderMapper;

    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    @Override
    public SaleOrder selectSaleOrderById(Long id)
    {
        return saleOrderMapper.selectSaleOrderById(id);
    }

    /**
     * 查询销售订单列表
     * 
     * @param saleOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<SaleOrder> selectSaleOrderList(SaleOrder saleOrder)
    {
        return saleOrderMapper.selectSaleOrderList(saleOrder);
    }

    /**
     * 新增销售订单
     * 
     * @param saleOrder 销售订单
     * @return 结果
     */
    @Override
    public int insertSaleOrder(SaleOrder saleOrder)
    {
        saleOrder.setCreateTime(DateUtils.getNowDate());
        return saleOrderMapper.insertSaleOrder(saleOrder);
    }

    /**
     * 修改销售订单
     * 
     * @param saleOrder 销售订单
     * @return 结果
     */
    @Override
    public int updateSaleOrder(SaleOrder saleOrder)
    {
        saleOrder.setUpdateTime(DateUtils.getNowDate());
        return saleOrderMapper.updateSaleOrder(saleOrder);
    }

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteSaleOrderByIds(Long[] ids)
    {
        return saleOrderMapper.deleteSaleOrderByIds(ids);
    }

    /**
     * 删除销售订单信息
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteSaleOrderById(Long id)
    {
        return saleOrderMapper.deleteSaleOrderById(id);
    }
}
