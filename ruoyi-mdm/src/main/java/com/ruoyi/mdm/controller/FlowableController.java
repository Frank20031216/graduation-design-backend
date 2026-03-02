package com.ruoyi.mdm.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mdm/flowable")
public class FlowableController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;

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

}
