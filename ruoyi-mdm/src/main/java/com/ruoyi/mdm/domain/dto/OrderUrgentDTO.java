package com.ruoyi.mdm.domain.dto;

import java.io.Serializable;

public class OrderUrgentDTO implements Serializable {

    private Long id;

    private String urgencyMode;

}


class UrgencyMode{

    public static final String AUTO = "AUTO";

    public static final String MANUAL = "MANUAL";
}