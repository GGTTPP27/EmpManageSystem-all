package com.mywork.service;

import com.mywork.pojo.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAll();



    Department findByDptName(String dptName);

    void add(String dptName);

    Department getDetail(Integer dptId);

    void update(Department dpt);

    void delete(Integer dptId);
}
