package com.ruoyi.mdm.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.mdm.domain.dto.SaleOrderSplitApprovalDTO;
import com.ruoyi.mdm.domain.entity.ProductionOrder;
import com.ruoyi.mdm.domain.entity.SaleOrder;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mdm/flowable")
public class FlowableController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/queryAllDeployedProcesses")
    public List<JSONObject> queryAllDeployedProcesses() {
        List<JSONObject> jsonObjects = new ArrayList<>();

        // 查询所有流程定义
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey().asc() // 按流程定义的 Key 排序
                .latestVersion() // 只查询每个流程定义的最新版本
                .list();

        // 打印所有已部署的流程的 key 和 name
        for (ProcessDefinition processDefinition : processDefinitions) {
            JSONObject object = new JSONObject();
            object.put("id", processDefinition.getId());
            object.put("key", processDefinition.getKey());
            object.put("name", processDefinition.getName());
            object.put("version", processDefinition.getVersion());

            jsonObjects.add(object);
        }

        //分页查询
        // 创建查询对象
//        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
//                .latestVersion() // 只查询最新版本的流程定义
//                .orderByProcessDefinitionKey().asc(); // 按流程定义的 Key 升序排序
//
//        // 获取总条数
//        long totalCount = query.count();
//
//        // 分页查询流程定义
//        List<ProcessDefinition> processDefinitions = query.listPage((pageNum - 1) * pageSize, pageSize);

        return jsonObjects;
    }

    @GetMapping("/querySplitTasksToAudit")
    public List<SaleOrderSplitApprovalDTO> querySplitTasksToAudit() {

        String processDefinitionKey = "splitSalesOrderWithAudit";
        String taskDefinitionKey = "splitApproval";

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();

        if (processDefinition == null) {
            System.err.println("流程定义不存在：" + processDefinitionKey);
            return null;
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        UserTask userTask = (UserTask) bpmnModel.getProcessById(processDefinitionKey)
                .getFlowElement(taskDefinitionKey);

        if (userTask == null) {
            System.err.println("用户任务节点不存在：" + taskDefinitionKey);
            return null;
        }

        // 获取静态配置的assignee
        String staticAssignee = userTask.getAssignee();
        List<SaleOrderSplitApprovalDTO> res = new ArrayList<>();

        if(SecurityUtils.hasRole(staticAssignee)){
            List<Task> tasks = taskService.createTaskQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .taskDefinitionKey(taskDefinitionKey)
                    .orderByTaskCreateTime().desc()
                    .list();


            tasks.stream().forEach(task -> {
                String taskId = task.getId();
                String processInstanceId = task.getProcessInstanceId();
                Map<String, Object> variables;

                if (processInstanceId != null && !processInstanceId.isEmpty()) {
                    variables = runtimeService.getVariables(processInstanceId);

                    SaleOrder saleOrder = (SaleOrder) variables.get("saleOrder");
                    List<ProductionOrder> productionOrderList = (List<ProductionOrder>) variables.get("productionOrderList");

                    res.add(new SaleOrderSplitApprovalDTO(taskId, saleOrder, productionOrderList));
                }

            });
        };

        return res;
    }


    @PostMapping("/confirmSplit")
    public void confirmSplit(String taskId, boolean auditResult) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        HashMap<String, Object> map = new HashMap<>();
        map.put("auditResult", auditResult);
        taskService.complete(taskId, map);
    }
}
