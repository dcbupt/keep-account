package com.bupt.dc.task.scheduler;

import com.bupt.dc.dao.jpa.AccountRecordRepository;
import com.bupt.dc.dao.jpa.AccountSumRepository;
import com.bupt.dc.dao.jpa.UserRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.CategoryEnum;
import com.bupt.dc.object.constant.KeepAccountConstant;
import com.bupt.dc.object.dataobject.AccountRecord;
import com.bupt.dc.object.dataobject.AccountSum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScheduledTask {

    @Resource
    private UserRepository userRepository;

    @Resource
    private AccountRecordRepository accountRecordRepository;

    @Resource
    private AccountSumRepository accountSumRepository;

    /**
     * 每日0点执行任务：
     * 计算每个用户每天在某个类目下的流水金额之和
     */
    @Scheduled(cron = "0 07 19 * * ?")
    public void calculateAccountSumByCategoryDaily() {
        log.info("start calculateAccountSumByCategoryDaily task ==================================");
        /** 1、获取所有userId **/
        List<User> users = userRepository.findAll();
        //List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        /** 2、按userId+category+date计算 **/
        List<String> categoryList = Arrays.stream(CategoryEnum.values()).map(CategoryEnum::name).collect(Collectors.toList());
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        List<AccountSum> accountSums = new ArrayList<>();
        for (User user : users) {
            //User user = new User(userId);
            List<AccountRecord> accountRecords = accountRecordRepository.findByUserAndDate(user, yesterday);
            accountRecords.sort(Comparator.comparing(AccountRecord::getCategory));

            for (AccountRecord accountRecord : accountRecords) {
                boolean found = false;
                for (AccountSum accountSum : accountSums) {
                    if (accountRecord.getCategory().equals(accountSum.getCategory())) {
                        accountSum.setSum(accountSum.getSum()+accountRecord.getAmount());
                        found = true;
                    }
                }
                if (found) {
                    continue;
                }
                AccountSum accountSumNew = new AccountSum(KeepAccountConstant.DURATION_DAILY);
                accountSumNew.setSum(accountRecord.getAmount());
                accountSumNew.setCategory(accountRecord.getCategory());
                accountSumNew.setDate(yesterday);
                accountSumNew.setType(accountRecord.getType());
                accountSumNew.setUser(user);
                accountSums.add(accountSumNew);
            }
        }

        /** 3、持久化到sum表 **/
        accountSumRepository.saveAll(accountSums);

    }


    /**
     * 每月1日0点执行任务：
     * 计算每个用户上月在类目下的流水金额之和
     */
    @Scheduled(cron = "0 08 19 * * ?")
    public void calculateAccountSumByCategoryMonthly() {
        log.info("start calculateAccountSumByCategoryMonthly task");
        LocalDate date = LocalDate.now().minusDays(1L);
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());

        /** 1、获取所有userId **/
        List<User> users = userRepository.findAll();
        //List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        List<AccountSum> result = new ArrayList<>();

        /** 2、计算每个类目下，用户上个月的流水金额之和 **/
        List<String> categoryList = Arrays.stream(CategoryEnum.values()).map(CategoryEnum::name).collect(Collectors.toList());
        for (User user: users) {
            for (String category : categoryList) {
                List<AccountSum> accountSums = accountSumRepository.findAccountSumsByDurationAndUserAndCategoryAndDateBetween(KeepAccountConstant.DURATION_DAILY,
                                    user, category, start, end);
                if (!CollectionUtils.isEmpty(accountSums)) {
                    AccountSum month = new AccountSum(KeepAccountConstant.DURATION_MONTHLY);
                    month.setUser(user);
                    month.setType(accountSums.get(0).getType());
                    month.setDate(start);
                    month.setCategory(category);
                    month.setSum(accountSums.stream().map(AccountSum::getSum).collect(Collectors.summingLong(value -> value)).longValue());
                    result.add(month);
                }
            }
        }

        /** 3、持久化 **/
        accountSumRepository.saveAll(result);
    }
}
