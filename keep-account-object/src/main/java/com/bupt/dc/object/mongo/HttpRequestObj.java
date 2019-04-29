package com.bupt.dc.object.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logs_request")
public class HttpRequestObj {

    @Id
    private String _id;

    private String requestURL;

    private String requestURI;

    private String lastUrl;

    private Long count;

}
