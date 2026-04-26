package com.mywork.pojo;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Transfer {

    private Integer empId;

    private Integer transferId;

    private LocalDate transferDate;
    private Integer transferOutDpt ;
    private Integer transferInDpt ;
    private String  transferReason;

    //仅服务前端
    private String empJob;
    private String empTitle;


}
