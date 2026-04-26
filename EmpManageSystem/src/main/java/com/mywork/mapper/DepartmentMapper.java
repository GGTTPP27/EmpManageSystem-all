package com.mywork.mapper;

import com.mywork.pojo.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("select d.dpt_id, d.dpt_name, " +
            "SUM(CASE WHEN e.emp_status IN (1,2,3,5) THEN 1 ELSE 0 END) emp_num1, " +  // 在职人数
            "SUM(CASE WHEN e.emp_status IN (4,6) THEN 1 ELSE 0 END) emp_num2 " +  // 退休/离职人数
            "from department d " +
            "left join employee e on d.dpt_id = e.dpt_id " +
            "group by d.dpt_id, d.dpt_name " +
            "order by d.dpt_id asc")
    List<Department> getAll();


    @Select("select * from department where dpt_name=#{dptName}")
    Department findByDptName(String dptName);

    @Insert("INSERT INTO department (dpt_name,create_time,update_time) values (#{dptName},now(),now())")
    void add(String dptName);

    @Select("select * from department where dpt_id=#{dptId}")
    Department getDetail(Integer dptId);

    @Update("update department set dpt_name =#{dptName},update_time=#{updateTime} where dpt_id=#{dptId}")
    void update(Department dpt);

    @Delete("delete from department where dpt_id=#{dptId}")
    void delete(Integer dptId);
}
