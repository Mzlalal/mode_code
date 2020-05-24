package com.mzlalalal.util;

import cn.hutool.core.util.StrUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.mzlalalal.common.DRobotParam;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * @description: 钉钉机器人使用工具类
 * @author: 赵凤明
 * @date: 2020/5/14 17:59
 */
public class DRobotKit {

    /**
     * 目标地址
     */
    private static String url = "{}&sign={}&timestamp={}";

    /**
     * 获取签证
     *
     * @param targetUrl 目标地址 https://oapi.dingtalk.com/robot/send?access_token=XXXXXX
     * @param secret    SEC73deb1c4d6df3eb9daxd7f58f96175ca5c5b03fe96afe9665a63f01e32b1efxa
     * @return String : https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
     * @throws Exception
     */
    public static String buildUrl(String targetUrl, String secret) throws Exception {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return StrUtil.format(url, targetUrl, sign, timestamp);
    }

    /**
     * 发送消息
     *
     * @param param 参数
     * @throws Exception
     */
    public static OapiRobotSendResponse send(DRobotParam param) throws Exception {
        // 参数校验
        param.check();

        // 拼接 最终发送的 URL
        String url = buildUrl(param.getTargetUrl(), param.getSecret());
        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(param.getMsgType().toString().toLowerCase());

        // 分类
        if (DRobotParam.DRobotMsgType.TEXT.equals(param.getMsgType())) {

            createText(request, param);

            // 创建 判断是否需要 @
            createAt(request, param);

        } else if (DRobotParam.DRobotMsgType.LINK.equals(param.getMsgType())) {

            createLink(request, param);

        } else if (DRobotParam.DRobotMsgType.MARKDOWN.equals(param.getMsgType())) {
            createMarkdown(request, param);

            // 创建 判断是否需要 @
            createAt(request, param);
        }


        return client.execute(request);
    }

    /**
     * 创建文本请求
     *
     * @param request
     * @param param
     */
    public static void createText(OapiRobotSendRequest request, DRobotParam param) {
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(param.getContent());
        request.setText(text);
    }

    /**
     * 创建 链接 语法内容请求
     *
     * @param request
     * @param param
     */
    public static void createLink(OapiRobotSendRequest request, DRobotParam param) {
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setText(param.getContent());
        link.setTitle(param.getTitle());
        link.setMessageUrl(param.getMessageUrl());
        link.setPicUrl(param.getPicUrl());
        request.setLink(link);
    }

    /**
     * 创建 markdown 语法内容请求
     *
     * @param request
     * @param param
     */
    public static void createMarkdown(OapiRobotSendRequest request, DRobotParam param) {
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setText(param.getContent());
        markdown.setTitle(param.getTitle());
        request.setMarkdown(markdown);
    }

    /**
     * 创建 需要被 @ 的人
     *
     * @param request
     * @param param
     */
    public static void createAt(OapiRobotSendRequest request, DRobotParam param) {
        if (param.getAtMobiles() == null || param.getAtMobiles().isEmpty()) {
            return;
        }
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(param.getAtMobiles());
        at.setIsAtAll(String.valueOf(param.getIsAtAll()));
        request.setAt(at);
    }

    public static void main(String[] args) {
        DRobotParam param = new DRobotParam();
        param.setTargetUrl("https://oapi.dingtalk.com/robot/send?access_token=5dfdd83e04ceab824862c38987406ea361169469e73413027671d8852cc07d4f");
        param.setSecret("SEC73deb1c4d6df3eb9daad7f58f96175ca5c5b03fe96afe9665a63f01e32b1ef9a");
        param.setMsgType(DRobotParam.DRobotMsgType.MARKDOWN);
        param.setContent("");
        param.setTitle("");

        try {
            DRobotKit.send(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
