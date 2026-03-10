package com.ruoyi.mdm.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.mdm.domain.dto.ProductionOrderQueryDTO;
import com.ruoyi.mdm.domain.entity.SaleOrder;
import com.ruoyi.mdm.service.ISaleOrderService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
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
import com.ruoyi.mdm.domain.entity.ProductionOrder;
import com.ruoyi.mdm.service.IProductionOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产订单管理Controller
 *
 * @author Frank Feng
 * @date 2025-12-28
 */
@RestController
@RequestMapping("/mdm/productionOrder")
public class ProductionOrderController extends BaseController {

    @Autowired
    private IProductionOrderService productionOrderService;

    @Autowired
    private ISaleOrderService saleOrderService;

    private final Logger log = LoggerFactory.getLogger(ProductionOrderController.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 查询生产订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody ProductionOrderQueryDTO productionOrder) {
        startPage();
        List<ProductionOrder> list = productionOrderService.selectProductionOrderList(productionOrder, productionOrder.getPageNum(), productionOrder.getPageSize());
        return getDataTable(list);
    }

    /**
     * 导出生产订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:export')")
    @Log(title = "生产订单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductionOrderQueryDTO productionOrder) {
        List<ProductionOrder> list = productionOrderService.selectProductionOrderList(productionOrder, 1, Integer.MAX_VALUE);
        ExcelUtil<ProductionOrder> util = new ExcelUtil<ProductionOrder>(ProductionOrder.class);
        util.exportExcel(response, list, "生产订单管理数据");
    }

    /**
     * 新增：导入生产订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:import')") // 原先为add，修改为import用于单独的数据权限控制
    @Log(title = "生产订单管理", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult excelImport(MultipartFile file) throws Exception {
        ExcelUtil<ProductionOrder> util = new ExcelUtil<ProductionOrder>(ProductionOrder.class);
        List<ProductionOrder> productionOrderList = util.importExcel(file.getInputStream());
        return toAjax(productionOrderService.insertProductionOrders(productionOrderList));
    }

    /**
     * 获取生产订单管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(productionOrderService.selectProductionOrderById(id));
    }

    /**
     * 新增生产订单管理
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:add')")
    @Log(title = "生产订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductionOrder productionOrder) {
        return toAjax(productionOrderService.insertProductionOrder(productionOrder));
    }

    /**
     * 批量新增生产订单管理
     */
    //@PreAuthorize("@ss.hasPermi('mdm:productionOrder:add')")
    @Log(title = "生产订单管理", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody List<ProductionOrder> productionOrderList) {

        HashMap<String,Object> map = new HashMap<>();
        if (CollectionUtils.isEmpty(productionOrderList)) {
            throw new ServiceException("生产订单列表不能为空，请至少填写一条订单数据");
        }
        Long saleOrderId = productionOrderList.get(0).getSaleId();
        SaleOrder saleOrder = saleOrderService.selectSaleOrderById(saleOrderId);

        map.put("saleOrder",saleOrder);
        map.put("productionOrderList",productionOrderList);

        ProcessInstance processInstance =
                        runtimeService.startProcessInstanceByKey("splitSalesOrderWithAudit", map);
        String processInstanceId = processInstance.getId();
        log.info("{}\t流程实例ID:{}",processInstance.getProcessDefinitionName(),processInstanceId);
//        Task task = taskService.createTaskQuery()
//                .processInstanceId(processInstanceId)
//                .active()
//                .singleResult();
//        taskService.complete(task.getId());

        return success(processInstanceId);
        //return toAjax(productionOrderService.insertProductionOrders(productionOrderList));
    }

    /**
     * 修改生产订单管理
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:edit')")
    @Log(title = "生产订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductionOrder productionOrder) {
        return toAjax(productionOrderService.updateProductionOrder(productionOrder));
    }

    /**
     * 删除生产订单管理
     */
    @PreAuthorize("@ss.hasPermi('mdm:productionOrder:remove')")
    @Log(title = "生产订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productionOrderService.deleteProductionOrderByIds(ids));
    }
}
