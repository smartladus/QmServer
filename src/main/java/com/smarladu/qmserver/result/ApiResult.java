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
    public static class Code {
        public static final int SUCCESS = 0;
        public static final int UPLOAD_ERROR = 1;
        public static final int INVALID_PARAM = 2;
        public static final int RECORD_NOT_FOUND = 3;
        public static final int FILE_NOT_EXIST = 4;

    }
    private int code;
    private String message;
    private Object result;

    public static ApiResult create(int code, String message, Object result) {
//        return new ApiResult(result, msg, JsonUtils.toSnakeJsonObj(data));
        return new ApiResult(code, message, result);
    }

    public static ApiResult success(String message, Object result) {
        return create(Code.SUCCESS, message, result);
    }

    public static ApiResult success(Object result) {
        return create(Code.SUCCESS, "success", result);
    }

    public static ApiResult fail(int code, String message) {
        return create(code, message, null);
    }
}
