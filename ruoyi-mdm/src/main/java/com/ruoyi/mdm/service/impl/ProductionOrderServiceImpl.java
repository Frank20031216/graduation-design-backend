package com.ruoyi.mdm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mdm.mapper.ProductionOrderMapper;
import com.ruoyi.mdm.domain.ProductionOrder;
import com.ruoyi.mdm.service.IProductionOrderService;
import org.springframework.util.CollectionUtils;

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
        productionOrder.setCreateBy(SecurityUtils.getUsername());
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
        productionOrder.setUpdateBy(SecurityUtils.getUsername());
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
        // 1. 空列表校验（基础校验）
        if (CollectionUtils.isEmpty(productionOrderList)) {
            throw new ServiceException("生产订单列表不能为空，请至少填写一条订单数据");
        }

        // 2. 遍历校验每个订单的业务规则（核心）
        // 获取今天的日期（截断时分秒，仅保留年月日）
        Date today = DateUtils.parseDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, new Date()));
        // 交期最小值：今天+2天
        Date minDeliveryDate = DateUtils.addDays(today, 2);

        for (int i = 0; i < productionOrderList.size(); i++) {
            ProductionOrder order = productionOrderList.get(i);
            String rowTip = "第" + (i + 1) + "行生产订单：";

            // 必填项校验（交期+生产日期）
            Date deliveryDate = order.getDeliveryDate();
            Date productionDate = order.getProductionDate();

            if (deliveryDate == null) {
                throw new ServiceException(rowTip + "交期为必填项，请选择具体日期");
            }
            if (productionDate == null) {
                throw new ServiceException(rowTip + "生产日期为必填项，请选择具体日期");
            }

            // 交期规则校验：不能早于今天+2天
            if (deliveryDate.compareTo(minDeliveryDate) < 0) {
                throw new ServiceException(rowTip + "交期不能早于" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, minDeliveryDate) + "（今天+2天）");
            }

            // 生产日期规则校验：必须在今天 ~ 交期之间
            if (productionDate.compareTo(today) < 0) {
                throw new ServiceException(rowTip + "生产日期不能早于今天（" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, today) + "）");
            }
            if (productionDate.compareTo(deliveryDate) > 0) {
                throw new ServiceException(rowTip + "生产日期不能晚于交期（" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, deliveryDate) + "）");
            }

            // 重量非空校验
            BigDecimal weightKg = order.getWeightKg();
            if (weightKg == null || weightKg.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ServiceException(rowTip + "重量必须大于0，请填写有效数值");
            }
        }

        // 3. 所有校验通过，执行批量插入
        int insertCount = productionOrderMapper.insertProductionOrders(productionOrderList);

        // 可选：校验插入结果
        if (insertCount != productionOrderList.size()) {
            throw new ServiceException("批量新增生产订单失败，部分数据插入异常");
        }

        return insertCount;
    }

}
