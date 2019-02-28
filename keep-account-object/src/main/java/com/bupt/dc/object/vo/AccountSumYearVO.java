package com.bupt.dc.object.vo;

import lombok.Data;

import java.util.List;

@Data
public class AccountSumYearVO {

    private List<AccountSumBaseVO> incomeOfYear;

    private List<AccountSumBaseVO> expenseOfYear;

}
