package com.bupt.dc.task.service.impl.account.sum;

import com.bupt.dc.dao.jpa.AccountSumRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.CategoryEnum;
import com.bupt.dc.object.constant.KeepAccountConstant;
import com.bupt.dc.object.dataobject.AccountSum;
import com.bupt.dc.object.util.DateUtil;
import com.bupt.dc.object.vo.AccountSumBaseVO;
import com.bupt.dc.object.vo.AccountSumYearVO;
import com.bupt.dc.task.service.AccountSumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountSumServiceImpl implements AccountSumService {

    @Resource
    private AccountSumRepository accountSumRepository;

    /**
     * 计算当月用户的日收支
     * @param userId
     * @param month
     * @return
     */
    @Override
    public List<AccountSumBaseVO> getAccountSumListByMonth(Long userId, String month) {
        LocalDate start = DateUtil.getFirstDayOfMonth(month);
        LocalDate end = DateUtil.getLastDayOfMonth(month);
        User user = new User(userId);
        /** 查询用户时间段内的日 **/
        List<AccountSum> accountSums = accountSumRepository.findAccountSumsByDurationAndUserAndDateBetweenOrderByDate(
                                                                KeepAccountConstant.DURATION_DAILY, user, start, end);
        return transformToBaseVO(userId, accountSums, start, end);
    }

    private List<AccountSumBaseVO> transformToBaseVO(Long userId, List<AccountSum> accountSums, LocalDate start, LocalDate end) {
        List<AccountSumBaseVO> ret = new ArrayList<>();
        /** 按天聚合每天的总收支 **/
        accountSums.stream().filter(accountSum -> accountSum.getType().equals(KeepAccountConstant.TYPE_INCOME)).collect(
            Collectors.groupingBy(AccountSum::getDate, Collectors.summingLong(AccountSum::getSum))).entrySet().stream().forEach(
                entry -> {
                    ret.add(new AccountSumBaseVO((Long)entry.getValue(), KeepAccountConstant.TYPE_INCOME, entry.getKey(), userId));
                }
        );
        accountSums.stream().filter(accountSum -> accountSum.getType().equals(KeepAccountConstant.TYPE_EXPENSE)).collect(
            Collectors.groupingBy(AccountSum::getDate, Collectors.summingLong(AccountSum::getSum))).entrySet().stream().forEach(
                entry -> {
                    ret.add(new AccountSumBaseVO(entry.getValue(), KeepAccountConstant.TYPE_EXPENSE, entry.getKey(), userId));
                }
        );
        ret.sort(Comparator.comparing(AccountSumBaseVO::getDate));
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

        if (KeepAccountConstant.TYPE_INCOME.equals(CategoryEnum.getTypeByCategory(category))) {
            DateUtil.getMonths(year).stream().map(DateUtil::getFirstDayOfMonth).forEach(localDate -> {
                Long monthSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndTypeAndCategory(
                        KeepAccountConstant.DURATION_MONTHLY, localDate, user, KeepAccountConstant.TYPE_INCOME, category)
                        .stream().map(AccountSum::getSum).
                        collect(Collectors.summingLong(value -> value)).longValue();
                income.add(new AccountSumBaseVO(monthSum, KeepAccountConstant.TYPE_INCOME, localDate, userId));
            });
        } else if (KeepAccountConstant.TYPE_EXPENSE.equals(CategoryEnum.getTypeByCategory(category))) {
            DateUtil.getMonths(year).stream().map(DateUtil::getFirstDayOfMonth).forEach(localDate -> {
                Long monthSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndTypeAndCategory(
                    KeepAccountConstant.DURATION_MONTHLY, localDate, user, KeepAccountConstant.TYPE_EXPENSE, category)
                    .stream().map(AccountSum::getSum).
                        collect(Collectors.summingLong(value -> value)).longValue();
                expense.add(new AccountSumBaseVO(monthSum, KeepAccountConstant.TYPE_EXPENSE, localDate, userId));
            });
        } else {
            DateUtil.getMonths(year).stream().map(DateUtil::getFirstDayOfMonth).forEach(localDate -> {
                Long monthSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndType(
                    KeepAccountConstant.DURATION_MONTHLY, localDate, user, KeepAccountConstant.TYPE_INCOME)
                    .stream().map(AccountSum::getSum).
                        collect(Collectors.summingLong(value -> value)).longValue();
                income.add(new AccountSumBaseVO(monthSum, KeepAccountConstant.TYPE_INCOME, localDate, userId));
            });
            DateUtil.getMonths(year).stream().map(DateUtil::getFirstDayOfMonth).forEach(localDate -> {
                Long monthSum = accountSumRepository.findAccountSumsByDurationAndDateAndUserAndType(
                    KeepAccountConstant.DURATION_MONTHLY, localDate, user, KeepAccountConstant.TYPE_EXPENSE)
                    .stream().map(AccountSum::getSum).
                        collect(Collectors.summingLong(value -> value)).longValue();
                income.add(new AccountSumBaseVO(monthSum, KeepAccountConstant.TYPE_EXPENSE, localDate, userId));
            });
        }

        return accountSumYearVO;
    }
}
