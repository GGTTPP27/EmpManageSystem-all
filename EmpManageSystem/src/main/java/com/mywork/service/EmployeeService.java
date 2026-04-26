package com.mywork.service;

import com.mywork.pojo.Employee;

import java.util.List;

public interface EmployeeService {

    //Integer getD(Integer empId);

    //Employee findByEmpId(String empId);

    void add(Employee employee);

    //Integer getEmpNum(Integer dptId);

    List<Employee> getAll();

/*    List<Employee> getDetail(Integer empId);

    List<Employee> getByName(String empName);

    List<Employee> getBydptId(Integer dptId);*/

    /* void update(Employee employee); */

    void delete(Integer empId);

    List<Employee> getSearch(Integer empId, String empName, Integer dptId);

    void retired(Employee employee);

    Integer makeEmpId(Integer dptId);

    void UpdateForTransfer(Integer empId,String empJob, String empTitle, Integer dptId,String transferReason);

    String generateEmployeeBackupSql();

    Integer getStat(String empId);
}
