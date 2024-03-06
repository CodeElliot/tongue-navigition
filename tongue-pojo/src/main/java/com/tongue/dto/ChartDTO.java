package com.tongue.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
public class ChartDTO implements Serializable {
    private LocalDate begin;
    private LocalDate end;
}
