package com.wangdg.lcs.common;

/**
 * LCS分词系统异常
 *
 * @author wangdg
 */
public class LCSRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LCSRuntimeException(String msg) {
        super(msg);
    }
}
