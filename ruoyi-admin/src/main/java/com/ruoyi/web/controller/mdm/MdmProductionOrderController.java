package com.ruoyi.web.controller.mdm;

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
import com.ruoyi.system.domain.MdmProductionOrder;
import com.ruoyi.system.service.IMdmProductionOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产订单Controller
 * 
 * @author ruoyi
 * @date 2025-12-22
 */
@RestController
@RequestMapping("/mdm/productionOrder")
public class MdmProductionOrderController extends BaseController
{
    @Autowired
    private IMdmProductionOrderService mdmProductionOrderService;

    /**
     * 查询生产订单列表
     */
    //@PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(MdmProductionOrder mdmProductionOrder)
    {
        startPage();
        List<MdmProductionOrder> list = mdmProductionOrderService.selectMdmProductionOrderList(mdmProductionOrder);
        return getDataTable(list);
    }

    /**
     * 导出生产订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "生产订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdmProductionOrder mdmProductionOrder)
    {
        List<MdmProductionOrder> list = mdmProductionOrderService.selectMdmProductionOrderList(mdmProductionOrder);
        ExcelUtil<MdmProductionOrder> util = new ExcelUtil<MdmProductionOrder>(MdmProductionOrder.class);
        util.exportExcel(response, list, "生产订单数据");
    }

    /**
     * 获取生产订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mdmProductionOrderService.selectMdmProductionOrderById(id));
    }

    /**
     * 新增生产订单
     */
    //@PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "生产订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdmProductionOrder mdmProductionOrder)
    {
        return toAjax(mdmProductionOrderService.insertMdmProductionOrder(mdmProductionOrder));
    }

    /**
     * 修改生产订单
     */
    //@PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "生产订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdmProductionOrder mdmProductionOrder)
    {
        return toAjax(mdmProductionOrderService.updateMdmProductionOrder(mdmProductionOrder));
    }

    /**
     * 删除生产订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "生产订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mdmProductionOrderService.deleteMdmProductionOrderByIds(ids));
    }

}
