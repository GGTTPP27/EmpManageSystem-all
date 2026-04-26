package com.mywork.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

//lombok在编译阶段，为实体类自动生成setter getter toString
@Data
public class Employee {
    @NotNull(groups = Update.class)
    private Integer empId;
    @NotEmpty(groups = Add.class)
    private String empName;
    @NotNull(groups = Add.class)
    private Integer empStatus;

    private LocalDate empOnboard;
    @NotEmpty(groups = Add.class)
    private String empJob;
    @NotEmpty(groups = Add.class)
    private String empTitle;
    @NotNull(groups = Add.class)
    private Integer dptId;

    //传给前端
    private String dptName;
    private LocalDateTime handleTime;


    public interface Update extends Default{

    }
    public interface Add extends Default {
    }
}