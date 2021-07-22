package com.smarladu.qmserver.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/7/22
 */

@Data
@AllArgsConstructor
@Slf4j
public class ApiResult {
    public enum Result {
        SUCCESS, FAIL
    }
    private Result result;
    private String msg;
    private Object data;

    public static ApiResult create(Result result, String msg, Object data) {
        return new ApiResult(result, msg, data);
    }

    public static ApiResult success(String msg, Object data) {
        return create(Result.SUCCESS, msg, data);
    }

    public static ApiResult success(Object data) {
        return create(Result.SUCCESS, "", data);
    }

    public static ApiResult fail(String msg) {
        return create(Result.FAIL, msg, null);
    }
}
