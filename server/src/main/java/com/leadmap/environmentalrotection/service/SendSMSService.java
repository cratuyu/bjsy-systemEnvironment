package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.web.controller.UserController;
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
 * 短信验证码服务
 *
 * @author: ttq
 * @Date: 2018/8/18 14:44
 */
@Component
public class SendSMSService {
    private final static Logger logger = LoggerFactory.getLogger(SendSMSService.class);

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
    @Value("${smsurl}")
    private String smsurl;
    @Value("${smsapiid}")
    private String smsapiid;
    @Value("${smspassword}")
    private String smspassword;

    public String getSMSVerificationCode() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

    public boolean sendSMSVerificationCode(String mobileCode, String smsVerificationCode) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsurl);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");

        String content = new String("您的验证码是：" + smsVerificationCode + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", smsapiid), //查看用户名是登录用户中心->验证码短信->产品总览->APIID
                new NameValuePair("password", smspassword),  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
                //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
                new NameValuePair("mobile", mobileCode),
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
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

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
