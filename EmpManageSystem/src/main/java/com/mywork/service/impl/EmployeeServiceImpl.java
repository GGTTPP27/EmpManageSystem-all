package com.mywork.service.impl;

import com.mywork.mapper.EmployeeMapper;
import com.mywork.pojo.Employee;
import com.mywork.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeMapper employeeMapper;


    @Override
    public void add(Employee employee) {
        employeeMapper.add(employee);
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.getAll();
    }
    /*
    @Override
    public List<Employee> getDetail(Integer empId){
        return employeeMapper.getDetail(empId);
    }
    @Override
    public  List<Employee> getByName(String empName){
        return employeeMapper.getByName(empName);
    }
    @Override
    public List<Employee> getBydptId(Integer dptId) {
        return employeeMapper.getBydptId(dptId);
    }
    */
    /*
    @Override
    public Employee findByEmpId(String empId){
        return employeeMapper.findByEmpId(empId);
    }

    @Override
    public Integer getEmpNum(Integer dptId){
        return employeeMapper.getEmpNum(dptId);
    }
  */
    @Override
    public List<Employee> getSearch(Integer empId, String empName, Integer dptId) {
        // 处理姓名空字符串（避免查询条件为""）
        if (empName != null) {
            empName = empName.trim();
            if (empName.isEmpty()) {
                empName = null;
            }
        }
        return employeeMapper.getSearch(empId, empName, dptId);
    }
    @Override
    public void delete(Integer empId) {
        employeeMapper.delete(empId);
    }
    @Override
    public Integer makeEmpId(Integer dptId) {

        int Num1=dptId*1000;
        /*
        int NumMax=employeeMapper.findMaxEmpIdByDptId(dptId)+1;//最大号
        int NumT=Num1+employeeMapper.getEmpNum(dptId)+1;//假设没有中间空的号，目前有多少个号
        if(NumT==NumMax){
            return NumT;
        }*/
        int resNum=Num1+1;
        while(employeeMapper.existsByEmpId(resNum)>0){
            resNum++;//找到没有被利用的号
        }
        return resNum;
    }
    //退休//////////////////////////////////////////////////////////////////////////
    @Override
    public void retired(Employee employee) {
        employeeMapper.retired(employee);
    }
    /*
    @Override
    public Integer getD(Integer empId){
        return employeeMapper.getD(empId);
    }*/


    //xx////////////
    @Override
    public void UpdateForTransfer(Integer empId, String empJob, String empTitle, Integer dptId, String transferReason){
        employeeMapper.updatefortransfer(empId,empJob,empTitle, dptId,transferReason);
    }

    @Override
    public String generateEmployeeBackupSql() {
        StringBuilder sqlBuilder = new StringBuilder();
        // 替换SimpleDateFormat为DateTimeFormatter（适配LocalDate/LocalDateTime）
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 备份生成时间（用当前时间）
        String now = java.time.LocalDateTime.now().format(dateTimeFormatter);

        // 拼接建表语句
        Map<String, String> createTableMap = employeeMapper.getEmployeeTableCreateSql();
        String createTableSql = createTableMap.get("Create Table");
        sqlBuilder.append("-- 员工表（employee）备份SQL\n");
        sqlBuilder.append("-- 生成时间：").append(now).append("\n\n");
        createTableSql = createTableSql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
        sqlBuilder.append(createTableSql).append(";\n\n");

        // 可选清空表语句
        sqlBuilder.append("-- 【可选】导入前清空员工表（保留数据请注释此行）\n");
        sqlBuilder.append("TRUNCATE TABLE employee;\n\n");

        // 拼接插入数据
        List<Employee> empList = employeeMapper.selectAllForBackup();
        if (!empList.isEmpty()) {
            sqlBuilder.append("-- 批量插入员工数据\n");
            sqlBuilder.append("INSERT INTO employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time) VALUES\n");

            for (int i = 0; i < empList.size(); i++) {
                Employee emp = empList.get(i);
                sqlBuilder.append("(")
                        .append(emp.getEmpId()).append(",")
                        .append("'").append(emp.getEmpName()).append("',")
                        .append(emp.getEmpStatus()).append(",")
                        // 修复：LocalDate类型格式化
                        .append("'").append(emp.getEmpOnboard().format(dateFormatter)).append("',")
                        .append("'").append(emp.getEmpJob()).append("',")
                        .append(emp.getEmpTitle() == null ? "NULL" : "'" + emp.getEmpTitle() + "'").append(",")
                        .append(emp.getDptId()).append(",")
                        // 修复：LocalDateTime类型格式化（兼容null）
                        .append(emp.getHandleTime() == null ? "NULL" : "'" + emp.getHandleTime().format(dateTimeFormatter) + "'")
                        .append(i == empList.size() - 1 ? ");\n" : "),\n");
            }
        } else {
            sqlBuilder.append("-- 员工表暂无数据\n");
        }
        return sqlBuilder.toString();
    }

    @Override
    public Integer getStat(String empId){
        return employeeMapper.getStat(empId);
    }
}