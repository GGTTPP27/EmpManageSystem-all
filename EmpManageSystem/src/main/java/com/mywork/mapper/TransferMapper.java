package com.mywork.mapper;

import com.mywork.pojo.Transfer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransferMapper {

    @Select("select * from transfer")
    List<Transfer> getAll();

    @Select("SELECT * FROM transfer  where emp_id = #{empId}")
    List<Transfer> search1(
            @Param("empId") Integer empId);

    @Select("<script>"+
            "SELECT * "+
            "FROM transfer t"+
            "<where>"+
            "   <if test='outDptId != null'>"+
            "       AND transfer_out_dpt = #{outDptId}"+
            "   </if>"+
            "   <if test='inDptId != null'>"+
            "       AND transfer_in_dpt = #{inDptId}"+
            "   </if>"+
            "</where>"+
            "</script>")
    List<Transfer> search2(
            @Param("outDptId") Integer  outDptId,
            @Param("inDptId") Integer  inDptId  );

    @Delete("delete from transfer where transfer_id = #{transferId}")
    void delete(Integer transferId);

    @Insert("insert into transfer (emp_id,transfer_date,transfer_out_dpt,transfer_in_dpt,transfer_reason) values (#{empId},now(),#{OutDpt},#{transferInDpt},#{transferReason})")
    void add(Integer empId,Integer OutDpt, Integer transferInDpt,String transferReason);


}
