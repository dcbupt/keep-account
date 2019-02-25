package com.bupt.dc.dao.jpa;

import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.dataobject.AccountRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountRecordRepository extends JpaRepository<AccountRecord, Long> {

    List<AccountRecord> findByUserAndDate(User user, LocalDate localDate);

}
