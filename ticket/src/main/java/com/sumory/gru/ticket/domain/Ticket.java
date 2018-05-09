package com.sumory.gru.ticket.domain;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 用户请求返回的结果实体
 * 
 * @author sumory.wu
 * @date 2015年3月11日 下午4:17:43
 */
public class Ticket {
    private boolean success;//是否成功
    private int errorCode;//错误状态码
    private String msg;//描述信息

    private Map<String, Object> data;//返回数据

    public Ticket(boolean success, int errorCode, String msg, Map<String, Object> data) {
        super();
        this.success = success;
        this.errorCode = errorCode;
        this.msg = (msg == null ? "" : msg);
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
