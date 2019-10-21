package com.bupt.dc.task.scheduler;

import com.bupt.dc.dao.jpa.AccountRecordRepository;
import com.bupt.dc.dao.jpa.AccountSumRepository;
import com.bupt.dc.dao.jpa.UserRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.CategoryEnum;
import com.bupt.dc.object.constant.KeepAccountConstant;
import com.bupt.dc.object.dataobject.AccountRecord;
import com.bupt.dc.object.dataobject.AccountSum;
import com.bupt.dc.object.factory.AbstractFactory;
import com.bupt.dc.object.factory.FactoryMaker;
import com.bupt.dc.object.processor.annotation.BizProcessChooser;
import com.bupt.dc.object.processor.annotation.BizTypeEnum;
import com.bupt.dc.object.processor.linked.DefaultProcessorManager;
import com.bupt.dc.object.processor.ProcessorRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Resource
    private DefaultProcessorManager defaultProcessorManager;

    @Resource
    private FactoryMaker factoryMaker;

    /**
     * 每日0点执行任务：
     * 计算每个用户每天在某个类目下的流水金额之和
     */
    @Scheduled(cron = "0 25 17 * * ?")
    public void calculateAccountSumByCategoryDaily() {
        log.info("start calculateAccountSumByCategoryDaily task ==================================");

        /** 1、获取所有userId **/
        List<User> users = userRepository.findAll();

        /** 2、计算每个用户昨天在某个类目下的收入/支出总和 **/
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        List<AccountSum> accountSums = new ArrayList<>();
        users.forEach(user -> {
            accountRecordRepository.findByUserAndDate(user, yesterday).stream().collect(Collectors.groupingBy(AccountRecord::getCategory)).
                entrySet().forEach(entry -> {
                    accountSums.add(new AccountSum(user, entry.getKey(), entry.getValue().get(0).getType(),
                        entry.getValue().stream().mapToLong(AccountRecord::getAmount).summaryStatistics().getSum(),
                        yesterday, KeepAccountConstant.DURATION_DAILY));
            });
        });

        /** 3、持久化到sum表 **/
        accountSumRepository.saveAll(accountSums);

    }


    /**
     * 每月1日0点执行任务：
     * 计算每个用户上月在类目下的流水金额之和
     */
    @Scheduled(cron = "30 25 17 * * ?")
    public void calculateAccountSumByCategoryMonthly() {
        log.info("start calculateAccountSumByCategoryMonthly task");
        LocalDate date = LocalDate.now().minusDays(1L);
        LocalDate start = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = date.with(TemporalAdjusters.lastDayOfMonth());
        List<AccountSum> result = new ArrayList<>();

        /** 1、获取所有userId **/
        /** 2、计算每个类目下，用户上个月的流水金额之和 **/
        userRepository.findAll().forEach(user -> {
            Arrays.stream(CategoryEnum.values()).map(CategoryEnum::name).forEach(category -> {
                Map<Integer, Long> map = accountSumRepository.findAccountSumsByDurationAndUserAndCategoryAndDateBetween(KeepAccountConstant.DURATION_DAILY,
                    user, category, start, end).stream().collect(Collectors.groupingBy(AccountSum::getType, Collectors.summingLong(AccountSum::getSum)));
                if (MapUtils.isEmpty(map)) {
                    return;
                }
                AccountSum month = new AccountSum(KeepAccountConstant.DURATION_MONTHLY);
                month.setUser(user);
                month.setType(map.keySet().iterator().next());
                month.setDate(start);
                month.setCategory(category);
                month.setSum(map.values().iterator().next());
                result.add(month);

            });
        });

        /** 3、持久化 **/
        accountSumRepository.saveAll(result);
    }

    @Scheduled(cron = "00 39 15 * * ?")
    @Retryable(value = Exception.class, listeners = {"commonRetryListener"}, backoff = @Backoff(delay = 1000, maxDelay = 3000, random = true))
    public void testRetry() {
        log.info("start testRetry");
        int i = 1/1;
    }

    @Recover
    public void testRetryRecovery(ArithmeticException exception) {
        log.info("enter recovery");
    }

    @Scheduled(cron = "00 17 15 * * ?")
    public void testProcessor() {
        log.info("start testProcessor");
        ProcessorRequest request = new ProcessorRequest();
        request.setBizCode("test");
        request.setRequest(new Object());
        defaultProcessorManager.processor(request);
    }

    @Scheduled(cron = "00 54 14 * * ?")
    public void testFactory() {
        log.info("start testFactory");
        AbstractFactory factory = factoryMaker.makeFactory();
        factory.createDefaultComponent();
    }

    @Scheduled(cron = "00 56 16 * * ?")
    public void testAnnotationProcess() {
        log.info("start testAnnotationProcess");
        Optional.ofNullable(BizProcessChooser.getBizProcessorMap().get(BizTypeEnum.DEFAULT.name())).
            ifPresent(bizProcessors -> {
                bizProcessors.forEach(bizProcessor -> {
                    ProcessorRequest request = new ProcessorRequest();
                    bizProcessor.process(request);
                });
            });
    }
}
