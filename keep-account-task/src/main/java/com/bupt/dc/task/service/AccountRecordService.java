package com.bupt.dc.task.service;

import com.bupt.dc.object.dataobject.AccountRecord;

import java.util.List;

public interface AccountRecordService {

    List<AccountRecord> getUserAccoundRecordsByDate(Long userId, String date);

    AccountRecord save(AccountRecord accountRecord);

    AccountRecord update(AccountRecord accountRecord);

    void del(Long id);
}
