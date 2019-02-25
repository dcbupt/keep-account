package com.bupt.dc.task.scheduler;

import com.bupt.dc.dao.jpa.UserRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.constant.CategoryEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScheduledTask {

    @Resource
    private UserRepository userRepository;


    /**
     * 每日0点执行任务：
     * 计算每个用户每天在某个类目下的流水金额之和
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void reportCurrentTime() {
        /** 1、获取所有userId **/
        List<User> users = userRepository.findAll();
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        /** 2、按userId+category+date计算 **/
        List<String> categoryList = Arrays.stream(CategoryEnum.values()).map(CategoryEnum::name).collect(Collectors.toList());

        /** 3、持久化到sum表 **/

    }
}
