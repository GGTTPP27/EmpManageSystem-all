package com.mywork.controller;

import com.mywork.pojo.Transfer;
import com.mywork.pojo.Result;

import com.mywork.service.EmployeeService;
import com.mywork.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@CrossOrigin
public class TransferController {

    @Autowired
    private TransferService transferservice;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAll")
    public Result<List<Transfer>> getAll() {

        List<Transfer> es = transferservice.getAll();
        return Result.success(es);
    }

    @GetMapping("/search1")
    public Result<List<Transfer>> search1(
            @RequestParam(required = false) Integer empId){

        List<Transfer> es=transferservice.search1(empId);
        return Result.success(es);

    }
    @GetMapping("/search2")
    public Result<List<Transfer>> search2(
            @RequestParam(required = false) Integer transferOutDpt,
            @RequestParam(required = false) Integer transferInDpt){
        List<Transfer> es=transferservice.search2(transferOutDpt,transferInDpt);
        return Result.success(es);

    }
    @DeleteMapping("/delete")
    public Result delete(Integer transferId) {
        transferservice.delete(transferId);
        return Result.success();
    }

    @PostMapping
    public Result add(String empId,Integer transferInDpt,String empJob,String empTitle,String transferReason) {

        Integer emppId =Integer.parseInt(empId);
        //Integer OutDpt = employeeService.getD(emppId);

        if(transferservice.search1(emppId).size()==0){
            return Result.error("不存在该员工");
        }
        Integer Stat = employeeService.getStat(empId);
        if(Stat==4||Stat==6){
            return Result.error("该员工已离职或退休！调度错误！");
        }

        employeeService.UpdateForTransfer(emppId,empJob,empTitle,transferInDpt,transferReason);

        //transferservice.add(emppId,OutDpt,transferInDpt,transferReason);
        return Result.success();
    }
}
