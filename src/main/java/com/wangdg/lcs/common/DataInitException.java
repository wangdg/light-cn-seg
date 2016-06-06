package com.wangdg.lcs.common;

/**
 * 数据初始化异常
 * 
 * @author wangdg
 */
public class DataInitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataInitException(String msg) {
        super(msg);
    }
}
