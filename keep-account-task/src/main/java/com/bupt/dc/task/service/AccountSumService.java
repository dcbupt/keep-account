package com.bupt.dc.task.service;

import com.bupt.dc.object.vo.AccountSumBaseVO;
import com.bupt.dc.object.vo.AccountSumYearVO;

import java.util.List;

public interface AccountSumService {

    List<AccountSumBaseVO> getAccountSumListByMonth(Long userId, String month);

    AccountSumYearVO getAccountSumListByYear(Long userId, String year, String category);
}
