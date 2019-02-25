package com.bupt.dc.control.controller;

import com.bupt.dc.object.dataobject.AccountRecord;
import com.bupt.dc.task.service.AccountRecordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/record", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountRecordController {

    @Autowired
    private AccountRecordService accountRecordService;

    @ApiOperation(value="流水录入", notes="流水录入")
    @ApiImplicitParam(name = "accountRecord", value = "账单记录实体accountRecord", required = true, dataType = "AccountRecord")
    @RequestMapping(value="/create", method=RequestMethod.POST)
    public AccountRecord postAccountRecord(@RequestBody AccountRecord accountRecord) {
        return accountRecordService.save(accountRecord);
    }


    @ApiOperation(value="流水更新", notes="流水更新")
    @ApiImplicitParam(name = "accountRecord", value = "账单记录实体accountRecord", required = true, dataType = "AccountRecord")
    @RequestMapping(value="/update", method=RequestMethod.PUT)
    public AccountRecord updateAccountRecord(@RequestBody AccountRecord accountRecord) {
        return accountRecordService.update(accountRecord);
    }

    @ApiOperation(value="日流水", notes="日流水")
    @RequestMapping(value="/get/{userId}/{date}", method=RequestMethod.GET)
    public List<AccountRecord> getAccountRecordList(@PathVariable Long userId, @PathVariable String date) {
        return accountRecordService.getUserAccoundRecordsByDate(userId, date);
    }


    @ApiOperation(value="流水删除", notes="流水删除")
    @ApiImplicitParam(name = "accountRecord", value = "账单记录实体accountRecord", required = true, dataType = "AccountRecord")
    @RequestMapping(value="/del/{id}", method=RequestMethod.DELETE)
    public void delAccountRecord(@PathVariable Long id) {
        accountRecordService.del(id);
    }
}
