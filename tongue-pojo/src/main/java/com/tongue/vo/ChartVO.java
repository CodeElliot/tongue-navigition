package com.tongue.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
//返回图表数据
public class ChartVO implements Serializable {
    private List<LocalDate> dateList;
    private List<Integer> newDataList;
}
