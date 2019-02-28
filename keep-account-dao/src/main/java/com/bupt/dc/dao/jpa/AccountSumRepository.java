package com.bupt.dc.dao.jpa;

import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.dataobject.AccountSum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountSumRepository extends JpaRepository<AccountSum, Long> {

    List<AccountSum> findAccountSumsByDurationAndUserAndDateBetweenOrderByDate(String duration, User user, LocalDate start, LocalDate end);

    List<AccountSum> findAccountSumsByDurationAndUserAndCategoryAndDateBetween(String duration, User user, String category, LocalDate start, LocalDate end);

    List<AccountSum> findAccountSumsByDurationAndDateAndUserAndType(String duration, LocalDate date, User user, Integer type);

    List<AccountSum> findAccountSumsByDurationAndDateAndUserAndTypeAndCategory(String duration, LocalDate date, User user, Integer type, String category);
}
