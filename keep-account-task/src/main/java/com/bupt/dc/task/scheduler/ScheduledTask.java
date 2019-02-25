package com.bupt.dc.task.scheduler;

import com.bupt.dc.dao.jpa.AccountRecordRepository;
import com.bupt.dc.dao.jpa.AccountSumRepository;
import com.bupt.dc.dao.jpa.UserRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.CategoryEnum;
import com.bupt.dc.object.dataobject.AccountRecord;
import com.bupt.dc.object.dataobject.AccountSum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
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
    @Scheduled(cron = "0 10 20 * * ?")
    public void calculateAccountSumByCategory() {
        log.info("start ScheduledTask ==================================");
        /** 1、获取所有userId **/
        List<User> users = userRepository.findAll();
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        /** 2、按userId+category+date计算 **/
        List<String> categoryList = Arrays.stream(CategoryEnum.values()).map(CategoryEnum::name).collect(Collectors.toList());
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        List<AccountSum> accountSums = new ArrayList<>();
        for (Long userId: userIds) {
            User user = new User(userId);
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
                AccountSum accountSumNew = new AccountSum();
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
}
