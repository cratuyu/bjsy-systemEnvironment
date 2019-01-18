package com.leadmap.environmentalrotection.service;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.alibaba.fastjson.JSONArray;
import com.leadmap.environmentalrotection.common.descrypt.DesCrypt;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.AirQualityInfoDao;
import com.leadmap.environmentalrotection.entity.weather.AQI24hInfo;
import com.leadmap.environmentalrotection.entity.weather.AirQualityInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.script.*;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Company: www.leadmap.net
 * Description:
 * 获取北京市空气质量信息
 *
 * @author: ttq
 * @Date: 2018/7/6 16:26
 */
public class AirQualityCrawler extends BreadthCrawler {
    private List<AirQualityInfo> airQualityInfoList;

    private List<AQI24hInfo> aqi24hInfoList;

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public AirQualityCrawler(String crawlPath, boolean autoParse, String url) {
        super(crawlPath, autoParse);
        this.addSeed(url);
    }

    @Override
    public void visit(Page page, Links nextLinks) {
        Document doc = page.getDoc();
        Elements e = doc.getElementsByTag("script").eq(19);
        HashMap<String, String> map = new HashMap<>();
        for (Element element : e) {
            String[] data = element.data().toString().split("'");
            if (data != null && data.length > 1) {
                try {
                    DesCrypt desCrypt = new DesCrypt();
                    String result = desCrypt.decode(data[1], data[3]);
                    List<AirQualityInfo> airQualityInfoList = new ArrayList<AirQualityInfo>();
                    airQualityInfoList = JSONArray.parseArray(result, AirQualityInfo.class);
                    if (airQualityInfoList != null && airQualityInfoList.size() > 0) {
                        for (AirQualityInfo airQualityInfo : airQualityInfoList) {
                            airQualityInfo.setDate(new Date());
                        }
                    }
                    this.airQualityInfoList = airQualityInfoList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        e = doc.getElementsByTag("script").eq(19);
        map = new HashMap<>();
        for (Element element : e) {
            String[] data = element.data().toString().split("'");
            if (data != null && data.length > 1) {
                try {
                    DesCrypt desCrypt = new DesCrypt();
                    String result = desCrypt.decode(data[5], data[7]);
                    List<AQI24hInfo> airQualityInfoList = new ArrayList<AQI24hInfo>();
                    airQualityInfoList = JSONArray.parseArray(result, AQI24hInfo.class);
                    this.aqi24hInfoList = airQualityInfoList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public List<AirQualityInfo> getAirQualityInfoList() {
        return airQualityInfoList;
    }

    public void setAirQualityInfoList(List<AirQualityInfo> airQualityInfoList) {
        this.airQualityInfoList = airQualityInfoList;
    }

    public List<AQI24hInfo> getAqi24hInfoList() {
        return aqi24hInfoList;
    }

    public void setAqi24hInfoList(List<AQI24hInfo> aqi24hInfoList) {
        this.aqi24hInfoList = aqi24hInfoList;
    }
}
