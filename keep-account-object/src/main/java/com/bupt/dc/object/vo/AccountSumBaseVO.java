package com.bupt.dc.object.vo;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class AccountSumBaseVO {

    @NonNull
    private Long sum;
    @NonNull
    private Integer type;
    @NonNull
    private LocalDate date;
    @NonNull
    private Long userId;
    private String category;

    public AccountSumBaseVO(@NonNull Integer type, @NonNull Long userId, @NonNull LocalDate localDate) {
        this.sum = 0L;
        this.type = type;
        this.userId = userId;
        this.date = localDate;
    }
}
