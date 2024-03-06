package com.tongue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long id;
    private String openId;
    private String uname;
    private String uimg;
    private Integer gender;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
