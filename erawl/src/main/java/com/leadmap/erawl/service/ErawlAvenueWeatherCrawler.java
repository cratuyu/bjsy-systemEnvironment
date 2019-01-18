package com.leadmap.erawl.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leadmap.erawl.common.util.ChineseToPinyin;
import com.leadmap.erawl.common.util.DateFormat;
import com.leadmap.erawl.common.util.DateUtil;
import com.leadmap.erawl.dao.AvenueInfoDao;
import com.leadmap.erawl.entity.weather.AvenueInfo;
import com.leadmap.erawl.entity.weather.AvenueWeatherInfo;
import com.leadmap.erawl.entity.weather.AvenueWeatherInfoInDay;
import com.leadmap.erawl.entity.weather.AvenueWeatherInfoInHour;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/18 11:01
 */
@Component
public class ErawlAvenueWeatherCrawler {

    @Value("${avenueWeather1dnUrl}")
    private String avenueWeather1dnUrl;

    @Value("${airPaimingUrl}")
    private String airPaimingUrl;

    @Value("${beiJingPM25Url}")
    private String beiJingPM25Url;

    @Value("${avenueWeather7dUrl}")
    private String avenueWeather7dUrl;

    @Value("${avenueWeather15dUrl}")
    private String avenueWeather15dUrl;

    @Autowired
    private AvenueInfoDao avenueInfoDao;

    @Autowired
    private ErawlWeatherCrawler weatherCrawler;


    /**
     * 获取街道天气信息
     *
     * @param evenueId
     * @return
     */
    public AvenueWeatherInfo getTodayAvenueWeaterInfo(String evenueId ,String updateTime) {
        try {
            //设置浏览器的User-Agent
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            //启用JS
            webClient.getOptions().setJavaScriptEnabled(true);
            //是否启用CSS
            webClient.getOptions().setCssEnabled(false);
            //设置支持AJAX
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            HtmlPage rootPage = webClient.getPage(this.avenueWeather1dnUrl + evenueId + ".shtml");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());

            AvenueWeatherInfo avenueWeatherInfo = new AvenueWeatherInfo();
            AvenueInfo avenueInfo = avenueInfoDao.findByAvenueId(evenueId);
            avenueWeatherInfo.setAvenue(avenueInfo.getAvenueName());
            avenueWeatherInfo.setAvenueId(evenueId);
            avenueWeatherInfo.setMaxTemperature(document.select("div[class=minMax]").select("span").get(0).text());
            avenueWeatherInfo.setMinTemperature(document.select("div[class=minMax]").select("span").get(1).text());
            avenueWeatherInfo.setCurrentTemperature(document.select("div[class=tempDiv]").select("span[class=temp]").text() + "℃");
            avenueWeatherInfo.setHumidity(document.select("div[class=todayLeft]").select("p").select("span").get(1).text());
            String win = document.select("div[class=todayLeft]").select("p").select("span").get(0).text();
            String[] str = win.split(" ");
            avenueWeatherInfo.setWindDirection(str[0]);
            avenueWeatherInfo.setWindPower(str[1]);

            avenueWeatherInfo.setUltravioletRays(document.select("div[class=lv]").select("dl").get(0).select("dd").text());
            avenueWeatherInfo.setUltravioletRaysLevel(document.select("div[class=lv]").select("dl").get(0).select("dt").select("em").text());
            avenueWeatherInfo.setCold(document.select("div[class=lv]").select("dl").get(1).select("dd").text());
            avenueWeatherInfo.setColdLevel(document.select("div[class=lv]").select("dl").get(1).select("dt").select("em").text());
            avenueWeatherInfo.setDress(document.select("div[class=lv]").select("dl").get(2).select("dd").text());
            avenueWeatherInfo.setDressLevel(document.select("div[class=lv]").select("dl").get(2).select("dt").select("em").text());
            avenueWeatherInfo.setCarWash(document.select("div[class=lv]").select("dl").get(3).select("dd").text());
            avenueWeatherInfo.setCarWashLevel(document.select("div[class=lv]").select("dl").get(3).select("dt").select("em").text());
            avenueWeatherInfo.setMotion(document.select("div[class=lv]").select("dl").get(4).select("dd").text());
            avenueWeatherInfo.setMotionLevel(document.select("div[class=lv]").select("dl").get(4).select("dt").select("em").text());
            avenueWeatherInfo.setAirPollutionDispersion(document.select("div[class=lv]").select("dl").get(5).select("dd").text());
            avenueWeatherInfo.setAirPollutionDispersionLevel(document.select("div[class=lv]").select("dl").get(5).select("dt").select("em").text());

            avenueWeatherInfo.setTime(DateUtil.string2Date(updateTime+":00",DateFormat.DateFull));

            Elements elements = document.select("#weatherALL").select("div[class=time]");

            for (int i = 0; i < elements.size(); i++) {
                AvenueWeatherInfoInHour avenueWeatherInfoInHour = new AvenueWeatherInfoInHour();
                avenueWeatherInfoInHour.setHour(document.select("#weatherALL").select("div[class=time]").get(i).text());
                avenueWeatherInfoInHour.setTemperature(document.select("tspan[dy=0]").get(i).text());
                avenueWeatherInfoInHour.setWindDirection(document.select("#weatherALL").select("div[class=wind]").get(i).text());
                avenueWeatherInfoInHour.setWindPower(document.select("#weatherALL").select("div[class=windL]").get(i).text());
                avenueWeatherInfoInHour.setWeatherStatus(document.select("#weatherALL").select("i").get(i).attr("title"));
                String titles = document.select("#weatherALL").select("i").get(i).attr("title");
                String classValue = document.select("#weatherALL").select("i").get(i).attr("class");
                String url = "";
                if (titles.equalsIgnoreCase("多云") || titles.equalsIgnoreCase("晴") || titles.equalsIgnoreCase("阴")) {
                    if (classValue.contains("d00")) {
                        url = titles + "白天" + ".png";
                    } else {
                        url = titles + "夜晚" + ".png";
                    }
                } else {
                    url = titles + ".png";
                }
                //汉子转化为拼音
                String imageUrl = ChineseToPinyin.ToPinyin(url);
                avenueWeatherInfoInHour.setWeatherImgUrl(imageUrl);
                avenueWeatherInfoInHour.setAvenueWeatherInfo(avenueWeatherInfo);
                avenueWeatherInfo.getAvenueWeatherInfoInHourList().add(avenueWeatherInfoInHour);
            }
            getAvenueAirQuality(avenueWeatherInfo);
            return avenueWeatherInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private void getAvenueAirQuality(AvenueWeatherInfo avenueWeatherInfo) {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage rootPage = webClient.getPage(this.airPaimingUrl);
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());
            avenueWeatherInfo.setAQI(document.select("a[href=/city/beijing.html]").parents().get(0).parent().select("td").get(3).text());
            avenueWeatherInfo.setEnvironmentalAssessment(weatherCrawler.getEnvironmentalAssessmentByAQI(avenueWeatherInfo.getAQI()));
            avenueWeatherInfo.setCityRankings(document.select("a[href=/city/beijing.html").parents().get(0).parent().select("td").get(0).text());
            if (avenueWeatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("优")) {
                avenueWeatherInfo.setHealthGuidelines("健康影响:空气质量令人满意,基本无空气污染,对健康没有危害;建议措施:各类人群可多参加户外活动,多呼吸一下清新的空气");
            } else if (avenueWeatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("良")) {
                avenueWeatherInfo.setHealthGuidelines("健康影响:除少数对某些污染物特别敏感的人群外,不会对人体健康产生危害;建议措施:除少数对某些污染物特别容易过敏的人群外,其他人群可以正常进行室外活动");
            } else if (avenueWeatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("轻度污染")) {
                avenueWeatherInfo.setHealthGuidelines("健康影响:敏感人群症状会有轻度加剧,对健康人群没有明显影响;建议措施:儿童、老年人及心脏病、呼吸系统疾病患者应尽量减少体力消耗大的户外活动");
            } else if (avenueWeatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("中度污染")) {
                avenueWeatherInfo.setHealthGuidelines("健康影响:敏感人群症状进一步加剧，可能对健康人群的心脏、呼吸系统有影响;建议措施:儿童、老年人及心脏病、呼吸系统疾病患者应尽量减少外出，停留在室内，一般人群应适量减少户外运动");
            } else if (avenueWeatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("重度污染")) {
                avenueWeatherInfo.setHealthGuidelines("健康影响:此时心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状;建议措施:儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动");
            } else if (avenueWeatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("严重污染")) {
                avenueWeatherInfo.setHealthGuidelines("健康影响:空气状况极差，所有人的健康都会受到严重危害;建议措施:儿童、老年人和病人应停留在室内，避免体力消耗，除有特殊需要的人群外，一般人群尽量不要停留在室外");
            }

            webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            rootPage = webClient.getPage(this.beiJingPM25Url);

            document = Jsoup.parseBodyFragment(rootPage.asXml());
            avenueWeatherInfo.setAQI(document.select("td:contains(" + "顺义" + ")").parents().get(0).select("td").get(1).text());
            avenueWeatherInfo.setEnvironmentalAssessment(weatherCrawler.getEnvironmentalAssessmentByAQI(avenueWeatherInfo.getAQI()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * 获取街道7天天气
     *
     * @param avenueId
     * @return
     */
    public List<AvenueWeatherInfoInDay> get7DayAvenueWeatherInfo(String avenueId) {
        try {
            //设置浏览器的User-Agent
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            //启用JS
            webClient.getOptions().setJavaScriptEnabled(true);
            //是否启用CSS
            webClient.getOptions().setCssEnabled(false);
            //设置支持AJAX
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage rootPage = webClient.getPage(this.avenueWeather7dUrl + avenueId + ".shtml");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());

            List<AvenueWeatherInfoInDay> avenueWeatherInfoInDayList = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                AvenueWeatherInfoInDay avenueWeatherInfoInDay = new AvenueWeatherInfoInDay();

                Format f = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = c.getTime();
                avenueWeatherInfoInDay.setDay(f.format(tomorrow));
                avenueWeatherInfoInDay.setWeatherStatus(document.select("ul[class=blue-container backccc").select("p[class=weather-info").get(i).text());

                Elements e = document.getElementsByTag("script").eq(6);
                for (Element element : e) {
                    String maxTemperature1 = element.data().split(";")[1].split("\\[")[1].split("\\]")[0];
                    String maxTemperature = maxTemperature1.split(",")[i].replace("\"", "");
                    String minTemperature1 = element.data().split(";")[2].split("\\[")[1].split("\\]")[0];
                    String minTemperature = minTemperature1.split(",")[i].replace("\"", "");
                    avenueWeatherInfoInDay.setMaxTemperature(maxTemperature +"℃");
                    avenueWeatherInfoInDay.setMinTemperature(minTemperature +"℃");
                }


                Elements elements = document.select("ul[class=blue-container backccc").select("div[class=wind-container]").get(i + 1).select("i");
                if (elements != null && elements.size() == 2) {
                    String windDirection = "";
                    windDirection += elements.get(0).attr("title") + "转" + elements.get(1).attr("title");
                    avenueWeatherInfoInDay.setWindDirection(windDirection);
                }

                avenueWeatherInfoInDay.setWindPower(document.select("ul[class=blue-container backccc").select("p[class=wind-info").get(i).select("p").text());
                avenueWeatherInfoInDay.setAvenueId(avenueId);
                avenueWeatherInfoInDayList.add(avenueWeatherInfoInDay);

            }
            return avenueWeatherInfoInDayList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获取街道15天天气
     *
     * @param avenueId
     * @return
     */
    public List<AvenueWeatherInfoInDay> get15DayAvenueWeatherInfo(String avenueId) {
        try {
            //设置浏览器的User-Agent
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            //启用JS
            webClient.getOptions().setJavaScriptEnabled(true);
            //是否启用CSS
            webClient.getOptions().setCssEnabled(false);
            //设置支持AJAX
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage rootPage = webClient.getPage(this.avenueWeather15dUrl + avenueId + ".shtml");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());

            List<AvenueWeatherInfoInDay> avenueWeatherInfoInDayList = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                AvenueWeatherInfoInDay avenueWeatherInfoInDay = new AvenueWeatherInfoInDay();

                Format f = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DAY_OF_MONTH, i + 7);
                Date tomorrow = c.getTime();
                avenueWeatherInfoInDay.setDay(f.format(tomorrow));
                avenueWeatherInfoInDay.setWeatherStatus(document.select("ul[class=blue-container backccc").select("p[class=weather-info").get(i).text());


                Elements el = document.getElementsByTag("script").eq(5);

                for (Element element : el) {
                    String max1 = element.data().split(";")[0].split("=")[1].split("\\[")[1].split("\\]")[0];
                    String max = max1.split(",")[i].replace("\"", "");
                    String min1 = element.data().split(";")[1].split("\\[")[1].split("\\]")[0];
                    String min = min1.split(",")[i].replace("\"", "");
                    avenueWeatherInfoInDay.setMaxTemperature(max +"℃");
                    avenueWeatherInfoInDay.setMinTemperature(min +"℃");
                }



                Elements elements = document.select("ul[class=blue-container backccc").select("div[class=wind-container]").get(i).select("i");
                if (elements != null && elements.size() == 2) {
                    String windDirection = "";
                    windDirection += elements.get(0).attr("title") + "转" + elements.get(1).attr("title");
                    avenueWeatherInfoInDay.setWindDirection(windDirection);
                }

                avenueWeatherInfoInDay.setWindPower(document.select("ul[class=blue-container backccc").select("p[class=wind-info").get(i).select("p").text());
                avenueWeatherInfoInDay.setAvenueId(avenueId);
                avenueWeatherInfoInDayList.add(avenueWeatherInfoInDay);

            }
            return avenueWeatherInfoInDayList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
