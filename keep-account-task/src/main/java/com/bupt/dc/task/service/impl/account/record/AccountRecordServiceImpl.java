package com.bupt.dc.task.service.impl.account.record;

import com.bupt.dc.dao.jpa.AccountRecordRepository;
import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.dataobject.AccountRecord;
import com.bupt.dc.object.util.DateUtil;
import com.bupt.dc.task.service.AccountRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AccountRecordServiceImpl implements AccountRecordService {

    @Autowired
    private AccountRecordRepository accountRecordRepository;

    @Override
    public List<AccountRecord> getUserAccoundRecordsByDate(Long userId, String date) {
        LocalDate localDate = DateUtil.formatDate(date);
        User user = new User(userId);
        List<AccountRecord> accountRecords = accountRecordRepository.findByUserAndDate(user, localDate);
        return accountRecords;
    }

    @Override
    public AccountRecord save(AccountRecord accountRecord) {
        return accountRecordRepository.save(accountRecord);
    }

    @Override
    public AccountRecord update(AccountRecord accountRecord) {
        AccountRecord update = accountRecordRepository.findById(accountRecord.getId()).get();
        update.setAmount(accountRecord.getAmount());
        return accountRecordRepository.save(update);
    }

    @Override
    public void del(Long id) {
        accountRecordRepository.deleteById(id);
    }
}
