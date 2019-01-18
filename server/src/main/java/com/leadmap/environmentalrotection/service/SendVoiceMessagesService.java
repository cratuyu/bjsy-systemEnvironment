package com.leadmap.environmentalrotection.service;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/4 18:06
 */
@Component
public class SendVoiceMessagesService {

    private final static Logger logger = LoggerFactory.getLogger(SendVoiceMessagesService.class);

    private static String Url = "http://api.voice.ihuyi.com/webservice/voice.php?method=Submit";
    @Value("${voiceSmsurl}")
    private String voiceSmsurl;
    @Value("${voiceSmsapiid}")
    private String voiceSmsapiid;
    @Value("${voiceSmspassword}")
    private String voiceSmspassword;

    public String getVoiceMessages() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

    public boolean sendVoiceMessages(String mobileCode , String content) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(voiceSmsurl);

        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", voiceSmsapiid),//查看用户名 登录用户中心->语音验证码>产品总览->API接口信息->APIID
                new NameValuePair("password", voiceSmspassword),//查看密码 登录用户中心->语音验证码>产品总览->API接口信息->APIKEY
                new NameValuePair("mobile", mobileCode),//手机号码
                new NameValuePair("content", content),
        };

        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult = method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String voiceid = root.elementText("voiceid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(voiceid);

            return true;

        } catch (HttpException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

}
