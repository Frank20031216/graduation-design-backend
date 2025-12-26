package com.ruoyi.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProductCategory {

    LIGHT_TUBE(1, "光管"),
    COPPER_PLATE(2, "铜板"),
    COPPER_STRIP(3, "铜带");

    // 枚举编码（数据库存储用）
    private final Integer code;
    // 枚举名称（前端展示用）
    private final String name;

    // 构造方法（枚举的构造方法默认private，无需显式声明）
    ProductCategory(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // GETTER方法（枚举属性需提供getter，无setter）
    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ProductCategory getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ProductCategory item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new IllegalArgumentException("无效的物料类型编码：" + code);
    }

    // 【常用工具方法】根据编码获取名称（直接返回展示名）
    public static String getNameByCode(Integer code) {
        ProductCategory item = getByCode(code);
        return item == null ? "" : item.getName();
    }

    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductCategory item : ProductCategory.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", item.getCode());
            map.put("name", item.getName());
            list.add(map);
        }
        return list;
    }

}
