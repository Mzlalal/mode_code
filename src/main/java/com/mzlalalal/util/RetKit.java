package com.mzlalalal.util;


import com.mzlalalal.model.Ret;

public class RetKit {
    public static Ret buildFail(String failMsg) {
        return Ret.fail("msg", failMsg);
    }

    public static Ret buildFail(String failMsgFormat, Object... args) {
        String failMsg = String.format(failMsgFormat, args);
        return buildFail(failMsg);
    }

    public static Ret buildOk() {
        return Ret.ok("msg", "操作成功");
    }

    public static Ret buildOk(String msg) {
        return Ret.ok("msg", msg);
    }

    public static Ret buildOk(Object data) {
        return buildOk("操作成功", data);
    }

    public static Ret buildOk(String msg, Object data) {
        return Ret.ok("msg", msg).set("data", data);
    }
}
