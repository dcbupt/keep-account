package com.bupt.dc.object.auth;

import com.bupt.dc.object.dataobject.AccountRecord;
import com.bupt.dc.object.dataobject.AccountSum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {

    public User(Long id) { this.id = id; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /** 默认懒加载 **/
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JsonIgnoreProperties(value = {"userList"})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"user"})
    private List<AccountRecord> accountRecords;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"user"})
    private List<AccountSum> accountSums;

    public static void main(String[] args) {
        //T t = new T();
        //t.setAmount(1L);
        //t.setPoolId(100226300010420L);
        //t.setSecurityCode("sdsd");
        //t.setSubBizType(930004002);
        //t.setUserId(3694440423L);
        //
        //T t2 = new T();
        //BeanUtils.copyProperties(t, t2);
        //
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("AFTER_DIAGNOSE", JSONObject.toJSONString(t));
        ////jsonObject.put("2019-02", JSONObject.toJSONString(t2));
        //
        //System.out.println(JSONObject.toJSONString(jsonObject));

        //JSONObject jsonObject = JSONObject.parseObject(s);
        //T t = JSONObject.parseObject((String)jsonObject.get("2019-022"), T.class);
        //System.out.println(JSONObject.toJSONString(t));

        StringBuilder stringBuilder = new StringBuilder("123|");

        System.out.println(stringBuilder.substring(0,stringBuilder.length()-1));
        System.out.println(Boolean.TRUE.equals(null));
    }

    @Data
    @NoArgsConstructor
    static class T {
        private Long amount;
        private Long poolId;
        private Integer subBizType;
        private Long userId;
        private String securityCode;
    }

    public static String s = "{\"2019-02\":\"{\\\"amount\\\":100,\\\"poolId\\\":123,\\\"securityCode\\\":\\\"abc\\\","
        + "\\\"subBizType\\\":24,\\\"userId\\\":141905}\",\"2019-01\":\"{\\\"amount\\\":100,\\\"poolId\\\":123,"
        + "\\\"securityCode\\\":\\\"abc\\\",\\\"subBizType\\\":24,\\\"userId\\\":141905}\"}\n";
}
