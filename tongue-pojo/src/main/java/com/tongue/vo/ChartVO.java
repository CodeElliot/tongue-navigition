package com.tongue.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
//返回图表数据
public class ChartVO implements Serializable {
    private String begin;
    private String end;
}
