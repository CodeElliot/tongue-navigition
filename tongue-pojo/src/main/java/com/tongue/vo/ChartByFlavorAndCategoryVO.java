package com.tongue.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ChartByFlavorAndCategoryVO implements Serializable {
    private List<Map<String,Object>> data;
}
