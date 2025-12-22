package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.MdmProductionOrder;

/**
 * 生产订单Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
public interface MdmProductionOrderMapper 
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
     * 删除生产订单
     * 
     * @param id 生产订单主键
     * @return 结果
     */
    public int deleteMdmProductionOrderById(Long id);

    /**
     * 批量删除生产订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMdmProductionOrderByIds(Long[] ids);
}
