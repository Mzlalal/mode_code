package com.mzlalalal.common;

import cn.hutool.core.lang.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc: 钉钉 机器人所需要的参数
 * 详细参考 : https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq
 * example:
 * TEXT类型:
 * {
 *     "msgtype": "text",
 *     "text": {
 *         "content": "我就是我, 是不一样的烟火@156xxxx8827"
 *     },
 *     "at": {
 *         "atMobiles": [
 *             "156xxxx8827",
 *             "189xxxx8325"
 *         ],
 *         "isAtAll": false
 *     }
 * }
 * msgtype content 必须
 *
 * link 类型:
 * {
 *     "msgtype": "link",
 *     "link": {
 *         "text": "这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林",
 *         "title": "时代的火车向前开",
 *         "picUrl": "",
 *         "messageUrl": "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI"
 *     }
 * }
 * msgtype title text messageUrl 必须
 *
 * markdown 类型:
 * {
 *      "msgtype": "markdown",
 *      "markdown": {
 *          "title":"杭州天气",
 *          "text": "#### 杭州天气 @150XXXXXXXX \n> 9度，西北风1级，空气良89，相对温度73%\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n> ###### 10点20分发布 [天气](https://www.dingalk.com) \n"
 *      },
 *       "at": {
 *           "atMobiles": [
 *               "150XXXXXXXX"
 *           ],
 *           "isAtAll": false
 *       }
 *  }
 * msgtype title text 必须
 * @Author: Mzlalalal
 * @Date: 2020/5/14 22:47
 **/
public class DRobotParam implements Serializable {
    private static final long serialVersionUID = 5102378680197340008L;
    /**
     * 目标 webhook 地址
     */
    private String targetUrl;
    /**
     * 私匙
     */
    private String secret;
    /**
     * 消息类型 link text markdown等扩展
     */
    private Enum msgType;
    /**
     * TEXT LINK MARKDOWN
     * 消息内容
     */
    private String content;
    /**
     * TEXT  MARKDOWN
     * 被 @ 人员的手机号码
     */
    private List<String> atMobiles;
    /**
     * LINK MARKDOWN
     * 消息标题
     */
    private String title;
    /**
     * TEXT  MARKDOWN
     * 是否 @ 所有人
     */
    private boolean isAtAll = false;
    /**
     * LINK
     * 点击消息跳转的URL
     */
    private String messageUrl;
    /**
     * LINK
     * 图片URL
     */
    private String picUrl;

    /**
     * 消息类型
     */
    public enum DRobotMsgType {
        /**
         * 文本类型
         */
        TEXT,
        /**
         * 链接类型
         */
        LINK,
        /**
         * markdown 语法
         */
        MARKDOWN
    }

    public DRobotParam () {
    }

    public DRobotParam (String targetUrl, String secret) {
        this.targetUrl = targetUrl;
        this.secret = secret;
    }

    public void textPara (String content) {
        this.msgType = DRobotMsgType.TEXT;
        this.content = content;
    }

    public void textPara (String targetUrl, String secret, String content) {
        this.msgType = DRobotMsgType.TEXT;
        this.targetUrl = targetUrl;
        this.secret = secret;
        this.content = content;
    }

    public void linkPara (String title, String content, String messageUrl) {
        this.msgType = DRobotMsgType.LINK;
        this.title = title;
        this.content = content;
        this.messageUrl = messageUrl;
    }

    public void linkPara (String targetUrl, String secret, String title, String content, String messageUrl) {
        this.msgType = DRobotMsgType.LINK;
        this.targetUrl = targetUrl;
        this.secret = secret;
        this.title = title;
        this.content = content;
        this.messageUrl = messageUrl;
    }

    public void markdownPara (String targetUrl, String secret, String title, String content) {
        this.msgType = DRobotMsgType.MARKDOWN;
        this.targetUrl = targetUrl;
        this.secret = secret;
        this.title = title;
        this.content = content;
    }

    public void markdownPara (String title, String content) {
        this.msgType = DRobotMsgType.MARKDOWN;
        this.title = title;
        this.content = content;
    }

    /**
     * 参数校验
     */
    public void check() {
        Assert.notBlank(targetUrl, "目标 webhook 地址不能为空");
        Assert.notBlank(secret, "私匙不能为空");
        Assert.notBlank(content, "消息内容不能为空");

        // 验证链接类型
        if (msgType.equals(DRobotMsgType.LINK)) {
            Assert.notBlank(title, "消息标题不能为空");
            Assert.notBlank(messageUrl, "点击消息跳转的URL不能为空");
        }
        // 验证 markdown 类型
        if (msgType.equals(DRobotMsgType.MARKDOWN)) {
            Assert.notBlank(title, "消息标题不能为空");
        }
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }

    public Enum getMsgType() {
        return msgType;
    }

    public void setMsgType(Enum msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsAtAll() {
        return isAtAll;
    }

    public void setIsAtAll(boolean isAtAll) {
        isAtAll = isAtAll;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "DRobotParam{" +
                "targetUrl='" + targetUrl + '\'' +
                ", secret='" + secret + '\'' +
                ", content='" + content + '\'' +
                ", atMobiles=" + atMobiles +
                ", msgType=" + msgType +
                ", title='" + title + '\'' +
                ", isAtAll=" + isAtAll +
                ", messageUrl='" + messageUrl + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
