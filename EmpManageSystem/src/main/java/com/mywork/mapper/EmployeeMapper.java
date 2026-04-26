package com.mywork.mapper;

import com.mywork.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {


    @Select("select emp_status from employee where emp_id = #{empId}")
    Integer getStat(String empId);
/*
    @Select("select * from employee where emp_id= #{empId}")
    Employee findByEmpId(String empId);
*/
    @Insert("insert into employee (emp_id,emp_name,emp_status,emp_onboard,emp_job,emp_title,dpt_id) values (#{empId},#{empName},#{empStatus},#{empOnboard},#{empJob},#{empTitle},#{dptId})")
    void add(Employee employee);
/*
    @Select("SELECT count(*) from employee where dpt_id=#{dptId}")
    Integer getEmpNum(Integer dptId);
*/
    @Select("select e.*, d.dpt_name as dptName from employee e left join department d on e.dpt_id = d.dpt_id")
    List<Employee> getAll();

/*    // 按部门ID查询员工并关联部门名称
    @Select("select e.*, d.dpt_name as dptName from employee e left join department d on e.dpt_id = d.dpt_id where e.dpt_id=#{dptId}")
    List<Employee> getBydptId(Integer dptId);

    //按员工ID查询详情并关联部门名称
    @Select("select e.*, d.dpt_name as dptName from employee e left join department d on e.dpt_id = d.dpt_id where e.emp_id=#{empId}")
    List<Employee> getDetail(Integer empId);


    @Select("select e.*, d.dpt_name as dptName from employee e left join department d on e.dpt_id = d.dpt_id where e.emp_name=#{empName}")
    List<Employee> getByName(String empName);
*/
    @Update("<script>" +
            "update employee " +
            "<set>" +
            "<if test='empName != null and empName != \"\"'>emp_name = #{empName},</if>" +
            "<if test='empStatus != null'>emp_status = #{empStatus},</if>" +
            "<if test='empJob != null and empJob != \"\"'>emp_job = #{empJob},</if>" +
            "<if test='empTitle != null'>emp_title = #{empTitle},</if>" +
            "<if test='dptId != null'>dpt_id = #{dptId},</if>" +
            "</set>" +
            "where emp_id = #{empId}" +
            "</script>")
    void update(Employee employee);

    @Delete("delete from employee where emp_id = #{empId}")
    void delete(Integer empId);
/*
    @Update("update employee set dpt_id = #{newDptId} where emp_id = #{empId}")
    void updateDptId(@Param("empId") Integer empId, @Param("newDptId") Integer newDptId);
*/

    @Select("<script>"+
            "SELECT e.*, d.dpt_name as dptName "+
            "FROM employee e "+
            "LEFT JOIN department d ON e.dpt_id = d.dpt_id "+
            "<where>"+
            "   <if test='empId != null'>"+
            "       AND e.emp_id = #{empId}"+
            "   </if>"+
            "   <if test='empName != null and empName != \"\"'>"+
            "       AND e.emp_name = #{empName}"+
            "   </if>"+
            "   <if test='dptId != null'>"+
            "       AND e.dpt_id = #{dptId}"+
            "   </if>"+
            "</where>"+
            "</script>")
    List<Employee> getSearch(
            @Param("empId") Integer empId,
            @Param("empName") String empName,
            @Param("dptId") Integer dptId);


    @Update("update employee set emp_status = #{empStatus} , handle_time=#{handleTime} where emp_id = #{empId}")
    void retired(Employee employee);

    /**
     * 校验员工编号是否存在
     */
    @Select("SELECT COUNT(1) FROM employee WHERE emp_id = #{empId}")
    int existsByEmpId(Integer empId);

    /*
     * 查询指定部门下最大的员工编号

    @Select("SELECT MAX(emp_id) FROM employee WHERE dpt_id = #{dptId}")
    Integer findMaxEmpIdByDptId(Integer dptId);
*/

    @Update("update employee set emp_job=#{empJob} ,emp_title=#{empTitle} ,dpt_id=#{dptId} ,transfer_reason_temp =#{transferReason} where emp_id=#{empId}")
    void updatefortransfer(Integer empId,  String empJob, String empTitle, Integer dptId, String transferReason);

    /*
    @Select("select dpt_id from employee where emp_id =#{empId}")
    Integer getD(Integer empId);*/

    // 1. 查询所有员工数据（备份用）
    @Select("SELECT * FROM employee")
    List<Employee> selectAllForBackup();

    // 2. 获取employee表的建表语句（MySQL专用，用于备份）
    @Select("SHOW CREATE TABLE employee")
    Map<String, String> getEmployeeTableCreateSql();
}
