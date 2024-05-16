package com.tongue.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
public class PageResult<T> implements Serializable {
    private Long total;//总数据量
    private List<T> items;//数据对象集合
}
