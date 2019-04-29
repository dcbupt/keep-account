package com.bupt.dc.object.dataobject;

import com.bupt.dc.object.auth.User;
import com.bupt.dc.object.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class AccountRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"accountRecords"})
    private User user;

    @NonNull
    @Column(nullable = false)
    private String category;

    @NonNull
    @Column(nullable = false)
    private Integer type;

    @NonNull
    @Column(nullable = false)
    private Long amount;

    @Column
    private String remark;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public static void main(String[] args) {
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setAmount(1L);
        accountRecord.setCategory("EAT");
        accountRecord.setDate(LocalDate.now());
        accountRecord.setRemark("test");
        User user = new User();
        user.setId(1L);
        accountRecord.setUser(user);
        System.out.println(JacksonUtil.encode(accountRecord));
    }
}
