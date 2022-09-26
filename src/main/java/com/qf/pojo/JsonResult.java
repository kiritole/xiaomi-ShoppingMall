package com.qf.pojo;

/**
 * 封装返回给前端的统一接口
 * 将来把所有内容都封装到这个类当中, 再经过序列化成json字符串,
 * 前端拿到之后,直接可以转换为对象, 方便前端业务逻辑处理
 */
public class JsonResult {
    /**
     * 业务状态码
     * 0, 表示业务处理成功
     * 非0, 表示业务处理失败
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 给前端发送发数据
     */
    private Object data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    private JsonResult() {

    }

    private JsonResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 请求成功返回给前端对象「fastJson2/jackson转换为json串」
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static JsonResult ok(int code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult ok(String msg, Object data) {
        return new JsonResult(0, msg, data);
    }

    public static JsonResult ok(int code, String msg) {
        return new JsonResult(code, msg, null);
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(0, "操作成功", data);
    }

    /**
     * 业务处理失败会调用此方法fastJson2/jackson转换为json串」
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static JsonResult error(int code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult error(String msg, Object data) {
        return new JsonResult(-1, msg, data);
    }

    public static JsonResult error(Object data) {
        return new JsonResult(-1, "操作失败", data);
    }

    public static JsonResult error(int code, String msg) {
        return new JsonResult(code, msg, null);
    }

}
