package com.mywork.controller;


import com.mywork.pojo.Employee;
import com.mywork.pojo.Result;

import com.mywork.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
/*    //根据Id查询
    @GetMapping("/getById")
    public Result<List<Employee>> getDetail(Integer empId) {
        List<Employee> es = employeeService.getDetail(empId);
        return Result.success(es);
    }
    //根据员工姓名查询
    @GetMapping("/getByName")
    public Result<List<Employee>> getDetail(String empName) {
        List<Employee> es = employeeService.getByName(empName);
        return Result.success(es);
    }

    //根据部门查询
    @GetMapping("/getByDptId")
    public Result<List<Employee>> getBydptId(Integer dptId) {
        List<Employee> es = employeeService.getBydptId(dptId);
        return Result.success(es);
    }
*/
    //搜索三合一
    @GetMapping("/Search")
    public Result<List<Employee>> getEmployees(
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) String empName,
            @RequestParam(required = false) Integer dptId) {

        List<Employee> employees = employeeService.getSearch(empId, empName, dptId);
        return Result.success(employees);
    }
    //进入网页，调出所有数据
    @GetMapping("/getAll")
    public Result<List<Employee>> getAll() {
        List<Employee> es = employeeService.getAll();
        return Result.success(es);
    }

    //删除员工数据
    @DeleteMapping
    public Result delete(Integer empId) {
        employeeService.delete(empId);
        return Result.success();
    }
    //员工退休处理
    @PutMapping("/retired")
    public Result retired(@RequestBody Employee employee) {
        employee.setHandleTime(LocalDateTime.now()); //数据库还没有加上
        employeeService.retired(employee);
        return Result.success();
    }
    //新增员工信息
    @PostMapping
    public Result add(@RequestBody @Validated(Employee.Add.class) Employee employee) {
        employee.setEmpOnboard(LocalDate.now());
        //3001 dptId+empNum
        Integer dptId = employee.getDptId();

        Integer empId = employeeService.makeEmpId(dptId);
        employee.setEmpId(empId);


        employeeService.add(employee);
        return Result.success();
    }
    /*
    @GetMapping("/backup/sql")
    public void downloadBackupSql(HttpServletResponse response) {
        try {
            // 1. 生成备份SQL
            String backupSql = employeeService.generateEmployeeBackupSql();

            // 2. 设置响应头（指定下载文件，避免乱码）
            response.setContentType("application/octet-stream");
            String fileName = "employee_backup_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".sql";
            // 解决文件名中文乱码
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
            response.setCharacterEncoding("UTF-8");

            // 3. 写入SQL并触发下载
            PrintWriter writer = response.getWriter();
            writer.write(backupSql);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("员工表备份失败：" + e.getMessage());
        }
    }*/
    @GetMapping("/backup/sql")
    public void downloadBackupSql(HttpServletResponse response) {
        try {
            String backupSql = employeeService.generateEmployeeBackupSql();

            response.setContentType("application/octet-stream");
            String fileName = "employee_backup_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".sql";
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.write(backupSql);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("员工表备份失败：" + e.getMessage());
        }
    }
}
