package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MdmProductionOrderMapper;
import com.ruoyi.system.domain.MdmProductionOrder;
import com.ruoyi.system.service.IMdmProductionOrderService;

/**
 * 生产订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@Service
public class MdmProductionOrderServiceImpl implements IMdmProductionOrderService 
{
    @Autowired
    private MdmProductionOrderMapper mdmProductionOrderMapper;

    /**
     * 查询生产订单
     * 
     * @param id 生产订单主键
     * @return 生产订单
     */
    @Override
    public MdmProductionOrder selectMdmProductionOrderById(Long id)
    {
        return mdmProductionOrderMapper.selectMdmProductionOrderById(id);
    }

    /**
     * 查询生产订单列表
     * 
     * @param mdmProductionOrder 生产订单
     * @return 生产订单
     */
    @Override
    public List<MdmProductionOrder> selectMdmProductionOrderList(MdmProductionOrder mdmProductionOrder)
    {
        return mdmProductionOrderMapper.selectMdmProductionOrderList(mdmProductionOrder);
    }

    /**
     * 新增生产订单
     * 
     * @param mdmProductionOrder 生产订单
     * @return 结果
     */
    @Override
    public int insertMdmProductionOrder(MdmProductionOrder mdmProductionOrder)
    {
        mdmProductionOrder.setCreateTime(DateUtils.getNowDate());
        return mdmProductionOrderMapper.insertMdmProductionOrder(mdmProductionOrder);
    }

    /**
     * 修改生产订单
     * 
     * @param mdmProductionOrder 生产订单
     * @return 结果
     */
    @Override
    public int updateMdmProductionOrder(MdmProductionOrder mdmProductionOrder)
    {
        mdmProductionOrder.setUpdateTime(DateUtils.getNowDate());
        return mdmProductionOrderMapper.updateMdmProductionOrder(mdmProductionOrder);
    }

    /**
     * 批量删除生产订单
     * 
     * @param ids 需要删除的生产订单主键
     * @return 结果
     */
    @Override
    public int deleteMdmProductionOrderByIds(Long[] ids)
    {
        return mdmProductionOrderMapper.deleteMdmProductionOrderByIds(ids);
    }

    /**
     * 删除生产订单信息
     * 
     * @param id 生产订单主键
     * @return 结果
     */
    @Override
    public int deleteMdmProductionOrderById(Long id)
    {
        return mdmProductionOrderMapper.deleteMdmProductionOrderById(id);
    }
}
