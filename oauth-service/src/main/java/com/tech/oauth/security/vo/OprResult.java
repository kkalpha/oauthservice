package com.tech.oauth.security.vo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author YK
 * @version 2018-1-27
 */

public class OprResult {

    private static final Logger logger = LoggerFactory.getLogger(OprResult.class);

    boolean success = true;
    String msg = "Action successfully done.";
    Long total = null;
    Object data = null;
    Integer statusCode = 0;
    String exceptionId;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCsrfToken() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .map(req -> req.getAttribute(CsrfToken.class.getName(), RequestAttributes.SCOPE_REQUEST))
            .map(token -> ((CsrfToken)token).getToken()).orElse(null);
    }

//    public static OprResult invalid(String msg) {
//        OprResult oprResult = new OprResult();
//        oprResult.setSuccess(false);
//        oprResult.setMsg(StringUtils.isNotEmpty(msg) ? msg : "Action failed.");
//        return oprResult;
//    }

//    public static OprResult error(String msg, Throwable ex) {
//        return OprResult.error(HttpStatus.SERVICE_UNAVAILABLE.value(), msg, ex);
//    }

    public static OprResult error(Integer statusCode, String msg, Throwable ex) {
        HttpServletResponse response
            = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        return OprResult.error(response, statusCode, msg, ex);
    }

    public static OprResult error(HttpServletResponse response, Integer statusCode, String msg, Throwable ex) {

        UUID uuid = UUID.randomUUID();
        logger.error(uuid + " - " + msg, ex);

        OprResult oprResult = new OprResult();
        oprResult.setSuccess(false);
        oprResult.setStatusCode(statusCode);
        oprResult.setMsg(StringUtils.isNotEmpty(msg) ? msg : "Action failed.");
        if (ex != null) {
            oprResult.setData(ex.getMessage());
        }
        oprResult.setExceptionId(uuid.toString());
        return oprResult;
    }

    public static OprResult info(String msg) {
        OprResult oprResult = new OprResult();
        oprResult.setMsg(msg);
        return oprResult;
    }

    public static OprResult info(Integer code, String msg) {
        OprResult oprResult = new OprResult();
        oprResult.setStatusCode(code);
        oprResult.setMsg(msg);
        return oprResult;
    }

    public static OprResult success() {
        return new OprResult();
    }

    public static OprResult unSuccess(Object data) {
        OprResult result = new OprResult();
        result.setSuccess(false);
        result.setData(data);
        return result;
    }

    public static OprResult success(Object data) {
        OprResult result = new OprResult();
        result.setData(data);
        try {
            result.setTotal((long)CollectionUtils.size(data));
        } catch (IllegalArgumentException ex) {
            logger.trace(data + " is not an instance of collection");
        }
        return result;
    }

//    public static OprResult successPaging(PagingResult data) {
//        OprResult result = new OprResult();
//        if (null != data) {
//            result.setData(data.getDetails());
//            result.setTotal(data.getCount());
//        }
//        return result;
//    }

//    public static OprResult successWithIdGuard(Object data, Long total, String salt) {
//
//        if (Collection.class.isAssignableFrom(data.getClass())) {
//            Collection objects = (Collection)data;
//            for (Object object : objects) {
//                IdGuarder.guardId(object, salt);
//            }
//        } else {
//            IdGuarder.guardId(data, salt);
//        }
//
//        return OprResult.success(data, total, null);
//    }

    public static OprResult success(Object data, Long total) {
        return OprResult.success(data, total, null);
    }

    public static OprResult success(Object data, Long total, String msg) {
        OprResult oprResult = new OprResult();
        oprResult.setData(data);
        oprResult.setTotal(total);
        if (msg != null) {
            oprResult.setMsg(msg);
        }
        return oprResult;
    }

}
