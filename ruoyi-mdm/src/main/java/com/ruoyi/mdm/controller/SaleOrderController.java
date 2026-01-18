package com.ruoyi.mdm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mdm.domain.SaleOrder;
import com.ruoyi.mdm.service.ISaleOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 * 
 * @author ruoyi
 * @date 2026-01-18
 */
@RestController
@RequestMapping("/mdm/saleOrder")
public class SaleOrderController extends BaseController
{
    @Autowired
    private ISaleOrderService saleOrderService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('mdm:saleOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(SaleOrder saleOrder)
    {
        startPage();
        List<SaleOrder> list = saleOrderService.selectSaleOrderList(saleOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('mdm:saleOrder:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SaleOrder saleOrder)
    {
        List<SaleOrder> list = saleOrderService.selectSaleOrderList(saleOrder);
        ExcelUtil<SaleOrder> util = new ExcelUtil<SaleOrder>(SaleOrder.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('mdm:saleOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(saleOrderService.selectSaleOrderById(id));
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('mdm:saleOrder:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SaleOrder saleOrder)
    {
        return toAjax(saleOrderService.insertSaleOrder(saleOrder));
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('mdm:saleOrder:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SaleOrder saleOrder)
    {
        return toAjax(saleOrderService.updateSaleOrder(saleOrder));
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('mdm:saleOrder:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(saleOrderService.deleteSaleOrderByIds(ids));
    }
}
