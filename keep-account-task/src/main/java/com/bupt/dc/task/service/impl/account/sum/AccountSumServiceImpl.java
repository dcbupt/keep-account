package com.bupt.dc.task.service.impl.account.sum;

import com.bupt.dc.dao.jpa.AccountSumRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.KeepAccountConstant;
import com.bupt.dc.object.dataobject.AccountSum;
import com.bupt.dc.object.util.DateUtil;
import com.bupt.dc.object.vo.AccountSumBaseVO;
import com.bupt.dc.object.vo.AccountSumYearVO;
import com.bupt.dc.task.service.AccountSumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountSumServiceImpl implements AccountSumService {

    private final List<String> months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

    @Resource
    private AccountSumRepository accountSumRepository;

    @Override
    public List<AccountSumBaseVO> getAccountSumListByMonth(Long userId, String month) {
        LocalDate start = DateUtil.getFirstDayOfMonth(month);
        LocalDate end = DateUtil.getLastDayOfMonth(month);
        User user = new User(userId);
        List<AccountSum> accountSums = accountSumRepository.findAccountSumsByDurationAndUserAndDateBetweenOrderByDate(
                                                                KeepAccountConstant.DURATION_DAILY, user, start, end);
        return transformToBaseVO(userId, accountSums, start, end);
    }

    private List<AccountSumBaseVO> transformToBaseVO(Long userId, List<AccountSum> accountSums, LocalDate start, LocalDate end) {
        /** 计算当月每天收支 **/
        LocalDate current = start;
        List<AccountSumBaseVO> ret = new ArrayList<>();
        List<AccountSum> filterList = null;
        while (!current.isAfter(end)) {
            final LocalDate compare = current;
            AccountSumBaseVO income = new AccountSumBaseVO(KeepAccountConstant.TYPE_INCOME, userId, current);
            AccountSumBaseVO expense = new AccountSumBaseVO(KeepAccountConstant.TYPE_EXPENSE, userId, current);
            filterList = accountSums.stream().filter(accountSum -> accountSum.getDate().isEqual(compare)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(filterList)) {
                for (AccountSum accountSum : filterList) {
                    if (accountSum.getType().equals(KeepAccountConstant.TYPE_INCOME)) {
                        income.setSum(income.getSum() + accountSum.getSum());
                    }

                    if (accountSum.getType() == KeepAccountConstant.TYPE_EXPENSE) {
                        expense.setSum(expense.getSum() + accountSum.getSum());
                    }
                }
                ret.add(income);
                ret.add(expense);
            }
            current = current.plusDays(1L);
        }
        return ret;
    }

    @Override
    public AccountSumYearVO getAccountSumListByYear(Long userId, String year, String category) {
        User user = new User(userId);
        AccountSumYearVO accountSumYearVO = new AccountSumYearVO();
        List<AccountSumBaseVO> income = new ArrayList<>();
        List<AccountSumBaseVO> expense = new ArrayList<>();
        accountSumYearVO.setIncomeOfYear(income);
        accountSumYearVO.setExpenseOfYear(expense);

        for (String m : months) {
            String month = year + "-" + m;
            LocalDate date = DateUtil.getFirstDayOfMonth(month);
            /** 获取这个月的收入 **/
            List<AccountSum> incomeSum = null;
            if (StringUtils.isEmpty(category)) {
                incomeSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndType(
                    KeepAccountConstant.DURATION_MONTHLY, date, user, KeepAccountConstant.TYPE_INCOME);
            } else {
                incomeSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndTypeAndCategory(
                    KeepAccountConstant.DURATION_MONTHLY, date, user, KeepAccountConstant.TYPE_INCOME, category);
            }
            AccountSumBaseVO monthIncome = new AccountSumBaseVO(KeepAccountConstant.TYPE_INCOME, userId, date);
            monthIncome.setCategory(category);
            if (!CollectionUtils.isEmpty(incomeSum)) {
                monthIncome.setSum(incomeSum.stream().map(AccountSum::getSum).collect(Collectors.summingLong(value -> value)).longValue());
            }
            income.add(monthIncome);
            /** 获取这个月的支出 **/
            List<AccountSum> expenseSum = null;
            if (StringUtils.isEmpty(category)) {
                expenseSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndType(
                    KeepAccountConstant.DURATION_MONTHLY, date, user, KeepAccountConstant.TYPE_EXPENSE);
            } else {
                expenseSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndTypeAndCategory(
                    KeepAccountConstant.DURATION_MONTHLY, date, user, KeepAccountConstant.TYPE_EXPENSE, category);
            }
            AccountSumBaseVO monthExpense = new AccountSumBaseVO(KeepAccountConstant.TYPE_EXPENSE, userId, date);
            monthExpense.setCategory(category);
            if (!CollectionUtils.isEmpty(expenseSum)) {
                monthExpense.setSum(expenseSum.stream().map(AccountSum::getSum).collect(Collectors.summingLong(value -> value)).longValue());
            }
            expense.add(monthExpense);
        }

        return accountSumYearVO;
    }
}
