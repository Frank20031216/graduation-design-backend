package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.MdmProductionOrder;

/**
 * 生产订单Service接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public interface IMdmProductionOrderService 
{
    /**
     * 查询生产订单
     * 
     * @param id 生产订单主键
     * @return 生产订单
     */
    public MdmProductionOrder selectMdmProductionOrderById(Long id);

    /**
     * 查询生产订单列表
     * 
     * @param mdmProductionOrder 生产订单
     * @return 生产订单集合
     */
    public List<MdmProductionOrder> selectMdmProductionOrderList(MdmProductionOrder mdmProductionOrder);

    /**
     * 新增生产订单
     * 
     * @param mdmProductionOrder 生产订单
     * @return 结果
     */
    public int insertMdmProductionOrder(MdmProductionOrder mdmProductionOrder);

    /**
     * 修改生产订单
     * 
     * @param mdmProductionOrder 生产订单
     * @return 结果
     */
    public int updateMdmProductionOrder(MdmProductionOrder mdmProductionOrder);

    /**
     * 批量删除生产订单
     * 
     * @param ids 需要删除的生产订单主键集合
     * @return 结果
     */
    public int deleteMdmProductionOrderByIds(Long[] ids);

    /**
     * 删除生产订单信息
     * 
     * @param id 生产订单主键
     * @return 结果
     */
    public int deleteMdmProductionOrderById(Long id);
}
