package com.bupt.dc.control.controller;

import com.bupt.dc.object.vo.AccountSumBaseVO;
import com.bupt.dc.object.vo.AccountSumYearVO;
import com.bupt.dc.task.service.AccountSumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/sum", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountSumController {

    @Autowired
    private AccountSumService accountSumService;

    @ApiOperation(value="月日收支", notes="月日收支")
    @RequestMapping(value="/get/month/{userId}/{month}", method=RequestMethod.GET)
    public List<AccountSumBaseVO> getAccountSumListByMonth(@PathVariable Long userId, @PathVariable String month) {
        return accountSumService.getAccountSumListByMonth(userId, month);
    }

    @ApiOperation(value="年月收支", notes="年月收支")
    @RequestMapping(value="/get/year/{userId}/{year}", method=RequestMethod.GET)
    public AccountSumYearVO getAccountSumListByYear(@PathVariable Long userId, @PathVariable String year) {
        return accountSumService.getAccountSumListByYear(userId, year, null);
    }

    @ApiOperation(value="年月收支按类目", notes="年月收支按类目")
    @RequestMapping(value="/get/year/{userId}/{year}/{category}", method=RequestMethod.GET)
    public AccountSumYearVO getAccountSumListByYearAndCategory(@PathVariable Long userId, @PathVariable String year, @PathVariable String category) {
        return accountSumService.getAccountSumListByYear(userId, year, category);
    }

}
