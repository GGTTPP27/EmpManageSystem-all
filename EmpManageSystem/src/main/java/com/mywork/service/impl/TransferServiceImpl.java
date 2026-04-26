package com.mywork.service.impl;

import com.mywork.mapper.EmployeeMapper;
import com.mywork.mapper.TransferMapper;
import com.mywork.pojo.Transfer;
import com.mywork.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    TransferMapper transferMapper;
    EmployeeMapper employeeMapper;

    @Override
    public List<Transfer> getAll(){
        return transferMapper.getAll();
    }

    @Override
    public List<Transfer> search1(Integer empId){
        return transferMapper.search1(empId);
    }
    @Override
    public List<Transfer> search2(Integer outDptId, Integer inDptId){
        return transferMapper.search2(outDptId,inDptId);
    }
    @Override
    public void delete(Integer transferId){
        transferMapper.delete(transferId);
    }
    @Override
    public void add(Integer empId,Integer OutDpt,Integer transferInDpt,String transferReason){
        transferMapper.add(empId,OutDpt,transferInDpt,transferReason);
    }




}
