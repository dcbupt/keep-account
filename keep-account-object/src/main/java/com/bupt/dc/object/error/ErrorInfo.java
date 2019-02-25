package com.bupt.dc.object.error;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorInfo {

    @NonNull
    private Integer code;

    @NonNull
    private String message;

    private String url;

}