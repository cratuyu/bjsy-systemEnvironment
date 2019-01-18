package com.leadmap.environmentalrotection.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Company: www.leadmap.net
 * Description:极光短信接口
 *
 * @author: yxm
 * @Date: 2018/9/27 17:05
 */
@Component
public class JSMSExample {

    protected static final Logger LOG = LoggerFactory.getLogger(JSMSExample.class);

    private static final String appkey = "e735e0319cb2ef470b89abb6";
    private static final String masterSecret = "c8b186aa63caf1cb640723dd";


    public static void main(String[] args) {
    	testSendSMSCode("13227063953");
//        testSendValidSMSCode();
//        testSendVoiceSMSCode();
//        testSendTemplateSMS();
    }

    //发送短信验证码
    public static String testSendSMSCode(String phone) {
        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phone)
                .setTempId(1)
                .build();
        String result = null;
        try {
            SendSMSResult res = client.sendSMSCode(payload);
//            result = res.toString();
            result = res.getMessageId();
            LOG.info(res.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
        //返回的是json{msg_id:"4346777"}
        return result;
    }


    //验证码校验
    public static boolean testSendValidSMSCode(String msg_id,String valid) {
        SMSClient client = new SMSClient(masterSecret, appkey);
        boolean valids ;
        try {
            ValidSMSResult res = client.sendValidSMSCode(msg_id ,valid);
            valids = res.getIsValid();
            System.out.println(res.toString());
            LOG.info(res.toString());
            return valids;
        } catch (APIConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
//            e.printStackTrace();
            if (e.getErrorCode() == 50010) {
                // do something
            }
//            System.out.println(e.getStatus() + " errorCode: " + e.getErrorCode() + " " + e.getErrorMessage());
//            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Message: " + e.getMessage());
        }
        return false;
    }


    /**
     *  The default value of ttl is 60 seconds.
     *  语言验证码
     */
    public static String testSendVoiceSMSCode(String phone) {
        SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phone)
                .setTTL(90)
                .build();
        String result = null;
        try {
            SendSMSResult res = client.sendVoiceSMSCode(payload);
            result = res.getMessageId();
            LOG.info(res.toString());
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        }
        return result;
    }

}
