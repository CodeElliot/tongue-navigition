package com.tongue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
public class ChartDTO implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
