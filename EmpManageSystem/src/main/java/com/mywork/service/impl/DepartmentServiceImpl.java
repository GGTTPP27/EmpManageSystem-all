package com.mywork.service.impl;

import com.mywork.mapper.DepartmentMapper;
import com.mywork.pojo.Department;
import com.mywork.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAll() {
        return departmentMapper.getAll();
    }




    @Override
    public Department findByDptName(String dptName) {
        Department dpt=departmentMapper.findByDptName(dptName);
        return dpt;
    }
    @Override
    public void add(String dptName) {
        departmentMapper.add(dptName);
    }
    @Override
    public Department getDetail(Integer dptId){
        return departmentMapper.getDetail(dptId);
    }
    @Override
    public void update(Department dpt){
        departmentMapper.update(dpt);
    }
    @Override
    public void delete(Integer dptId){
        departmentMapper.delete(dptId);
    }
}
