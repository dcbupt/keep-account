package com.bupt.dc.object;

import com.bupt.dc.object.error.ErrorInfo;
import com.bupt.dc.object.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -7674123515635396707L;

    protected boolean success = true;
    protected T model;
    protected Integer msgCode;
    protected int httpStatusCode = 200;
    protected String msgInfo;
    protected Map<String, String> headers = new HashMap();
    protected Map bizExtMap;

    private Result(T model) {
        this.model = model;
    }

    private Result(Integer msgCode, String msgInfo) {
        this.success = false;
        this.msgCode = msgCode;
        this.msgInfo = msgInfo;
    }

    public static <T> Result<T> ok(T model) {return new Result(model);}

    public static Result fail(ErrorInfo errorInfo) {
        return new Result(errorInfo.getCode(), errorInfo.getMessage());
    }

    public static Result fail(Integer msgCode, String msgInfo) {
        return new Result(msgCode, msgInfo);
    }

    public void set302Jump(String url) {
        this.httpStatusCode = 302;
        this.headers.put("Location", url);
    }

    public void setCacheControl(int maxAge, Date lastModified, String cacheKey) {
        this.headers.put("Cache-Control", "max-age=" + maxAge);
        this.setLastModified(lastModified);
        this.setCacheKey(cacheKey);
    }

    public void setCachedHttpStatusCode() {
        this.httpStatusCode = 304;
    }

    public void setLimitHttpStatusCode() {
        this.httpStatusCode = 420;
    }

    public void setCacheKey(String cacheKey) {
        if (cacheKey != null) {
            this.headers.put("CacheKey", cacheKey);
        }

    }

    public void setLastModified(Date lastModified) {
        Date d = null;
        if (lastModified != null) {
            d = lastModified;
        } else {
            d = new Date();
        }

        this.headers.put("Last-Modified", DateUtil.getGmtTime(d));
    }

    public void setCacheControl(String cacheControl, Date lastModified, String cacheKey) {
        this.headers.put("Cache-Control", cacheControl);
        this.setLastModified(lastModified);
        this.setCacheKey(cacheKey);
    }

    public void isCached(boolean isCached) {
        this.headers.put("isCached", isCached + "");
    }

    public void isCached(boolean isCached, int mtopCachedMaxAge) {
        this.headers.put("isCached", isCached + "");
        this.headers.put("mtopCachedMaxAge", mtopCachedMaxAge + "");
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

}
