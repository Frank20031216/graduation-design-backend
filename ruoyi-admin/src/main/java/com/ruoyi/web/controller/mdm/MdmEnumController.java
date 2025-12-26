package com.ruoyi.web.controller.mdm;

import com.ruoyi.common.enums.ProductCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mdm/enum")
public class MdmEnumController {

    @GetMapping("/product-category")
    public List<Map<String, Object>> getType() {
        return ProductCategory.toList();
    }

    @GetMapping("specification-model")
    public List<Map<String, Object>> getSpecificationModel() {
        return new ArrayList<>();
    }
}
