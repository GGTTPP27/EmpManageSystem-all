package com.mywork.service;

import com.mywork.pojo.Transfer;

import java.util.List;

public interface TransferService {

    List<Transfer> getAll();

    List<Transfer> search1(Integer empId);
    
    List<Transfer> search2(Integer outDptId, Integer inDptId);


    void delete(Integer transferId);

    void add(Integer empId,Integer OutDpt,Integer transferInDpt,String transferReason );
}
