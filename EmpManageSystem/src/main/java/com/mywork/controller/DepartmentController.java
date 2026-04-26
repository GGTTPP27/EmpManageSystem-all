package com.mywork.controller;

import com.mywork.pojo.Department;
import com.mywork.pojo.Result;
import com.mywork.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/department")
@Validated
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService dptService;

    @GetMapping("/getAll")
    public Result<List<Department>> getAllDepartments() {
        List<Department> departments = dptService.getAll();
        return Result.success(departments);
    }
    @GetMapping
    public Result<Department> getDetail(Integer dptId){

        Department l= dptService.getDetail(dptId);
        return Result.success(l);
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Department.Update.class)   Department dpt){
        dpt.setUpdateTime(LocalDateTime.now());
        dptService.update(dpt);
        return Result.success();
    }

}


















