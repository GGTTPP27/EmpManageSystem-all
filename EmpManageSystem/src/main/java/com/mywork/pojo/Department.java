package com.mywork.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Department {
    @NotNull(groups = Update.class)
    private Integer dptId;
    @NotEmpty
    @Pattern(regexp = "^\\S{2,10}$")
    private String dptName;

    private Integer empNum;
    private Integer empNum1 ; //传给前端
    private Integer empNum2 ;//传给前端

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public interface Add extends Default{
    }
    public interface Update extends Default {
    }
}
