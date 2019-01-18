package com.leadmap.erawl.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leadmap.erawl.common.util.ChineseToPinyin;
import com.leadmap.erawl.dao.WeatherCityInfoDao;
import com.leadmap.erawl.dao.WeatherInfoDao;
import com.leadmap.erawl.dao.WeatherInfoInDayDao;
import com.leadmap.erawl.entity.weather.WeatherCityInfo;
import com.leadmap.erawl.entity.weather.WeatherInfo;
import com.leadmap.erawl.entity.weather.WeatherInfoInDay;
import com.leadmap.erawl.entity.weather.WeatherInfoInHour;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
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
 * @author: ttq
 * @Date: 2018/7/4 16:19
 */
@Component
public class ErawlWeatherCrawler {
    @Value("${weather1dUrl}")
    private String weather1dUrl;

    @Value("${weather7dUrl}")
    private String weather7dUrl;

    @Value("${weather15dUrl}")
    private String weather15dUrl;

    @Value("${airPaimingUrl}")
    private String airPaimingUrl;

    @Value("${beiJingDistrict}")
    private String beiJingDistrict;

    @Value("${beiJingPM25Url}")
    private String beiJingPM25Url;

    @Autowired
    private WeatherInfoDao weatherInfoDao;
    @Autowired
    private WeatherInfoInDayDao weatherInfoInDayDao;
    @Autowired
    private WeatherCityInfoDao weatherCityInfoDao;

    public void deleteTodayWeaterInfo(String cityId) {
        List<WeatherInfo> weatherInfoList = weatherInfoDao.findByCityId(cityId);
        if (weatherInfoList != null) {
            weatherInfoDao.delete(weatherInfoList);
        }
    }

    public WeatherInfo generateTodayWeaterInfo(String cityId) {
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
            HtmlPage rootPage = webClient.getPage(this.weather1dUrl + cityId + ".shtml");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());
//            HtmlUnitDriver driver = new HtmlUnitDriver();
//            driver.setJavascriptEnabled(true);
//            driver.get(this.weather1dUrl + cityId + ".shtml");
//            Document document = Jsoup.parseBodyFragment(driver.getPageSource());
            WeatherInfo weatherInfo = new WeatherInfo();
            WeatherCityInfo weatherCityInfo = weatherCityInfoDao.findByCityId(cityId);
            weatherInfo.setCity(weatherCityInfo.getCityName());
            weatherInfo.setCityId(cityId);
            weatherInfo.setMaxTemperature(document.select("ul[class=clearfix]").select("p[class=tem]").get(0).select("span").text()+"℃");
            weatherInfo.setMinTemperature(document.select("ul[class=clearfix]").select("p[class=tem]").get(1).select("span").text()+"℃");
            weatherInfo.setCurrentTemperature(document.select("div[class=sk]").select("div[class=tem]").select("span").text()+"℃");
            weatherInfo.setHumidity(document.select("div[class=zs h]").select("em").text());
            weatherInfo.setWindDirection(document.select("div[class=sk]").select("div[class=zs w]").select("span").text());
            weatherInfo.setWindPower(document.select("div[class=sk]").select("div[class=zs w]").select("em").text());
            weatherInfo.setPrecipitation(document.select("div[class=weatherChart]").select("p[class=rain on]").text());

//            LifeIndex lifeIndex = new LifeIndex();
            weatherInfo.setUltravioletRays(document.select("div[class=livezs]").select("ul").select("li").get(0).select("p").text());
            weatherInfo.setUltravioletRaysLevel(document.select("div[class=livezs]").select("ul").select("li").get(0).select("span").text());
            weatherInfo.setReduceWeight(document.select("div[class=livezs]").select("ul").select("li").get(1).select("p").text());
            weatherInfo.setReduceWeightLevel(document.select("div[class=livezs]").select("ul").select("li").get(1).select("em[class=star]").size() + "");
            weatherInfo.setBloodSugar(document.select("div[class=livezs]").select("ul").select("li").get(2).select("p").text());
            weatherInfo.setBloodSugarLevel(document.select("div[class=livezs]").select("ul").select("li").get(2).select("span").text());
            weatherInfo.setDress(document.select("div[class=livezs]").select("ul").select("li").get(3).select("p").text());
            weatherInfo.setDressLevel(document.select("div[class=livezs]").select("ul").select("li").get(3).select("span").text());
            weatherInfo.setCarWash(document.select("div[class=livezs]").select("ul").select("li").get(4).select("p").text());
            weatherInfo.setCarWashLevel(document.select("div[class=livezs]").select("ul").select("li").get(4).select("span").text());
            weatherInfo.setAirPollutionDispersion(document.select("div[class=livezs]").select("ul").select("li").get(5).select("p").text());
            weatherInfo.setAirPollutionDispersionLevel(document.select("div[class=livezs]").select("ul").select("li").get(5).select("span").text());
//            weatherInfo.setLifeIndex(lifeIndex);
            weatherInfo.setTime(new Date());

            Elements elements = document.select("div[class=curve_livezs]").select("div[class=tem]").select("em");
            for (int i = 0; i < elements.size(); i++) {
                WeatherInfoInHour weatherInfoInHour = new WeatherInfoInHour();
                weatherInfoInHour.setHour(document.select("div[class=curve_livezs]").select("div[class=time]").select("em").get(i).text());
                weatherInfoInHour.setTemperature(document.select("div[class=curve_livezs]").select("div[class=tem]").select("em").get(i).text());
                weatherInfoInHour.setWindDirection(document.select("div[class=curve_livezs]").select("div[class=winf]").select("em").get(i).text());
                weatherInfoInHour.setWindPower(document.select("div[class=curve_livezs]").select("div[class=winl]").select("em").get(i).text());
                weatherInfoInHour.setWeatherStatus(document.select("div[class=curve_livezs]").select("div[class=wpic]").select("big").get(i).attr("title"));

                String title = document.select("div[class=curve_livezs]").select("div[class=wpic]").select("big").get(i).attr("title");
                String classValue = document.select("div[class=curve_livezs]").select("div[class=wpic]").select("big").get(i).attr("class");
                String url = "";
                if (title.equalsIgnoreCase("多云") || title.equalsIgnoreCase("晴天") || title.equalsIgnoreCase("阴")) {
                    if (classValue.contains("d0")) {
                        url = title + "白天" + ".png";
                    } else {
                        url = title + "夜晚" + ".png";
                    }
                } else {
                    url = title + ".png";
                }
                //汉子转化为拼音
                String imageUrl =ChineseToPinyin.ToPinyin(url);
                weatherInfoInHour.setWeatherImgUrl(imageUrl);
                weatherInfoInHour.setWeatherInfo(weatherInfo);
                weatherInfo.getWeatherInfoInHourList().add(weatherInfoInHour);
            }

            generateAirQuality(weatherInfo);
            return weatherInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void generateAirQuality(WeatherInfo weatherInfo) {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage rootPage = webClient.getPage(this.airPaimingUrl);
//            HtmlUnitDriver driver = new HtmlUnitDriver();
//            driver.setJavascriptEnabled(true);
//            driver.get("http://www.86pm25.com/paiming.htm");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());
            weatherInfo.setAQI(document.select("a[href=/city/beijing.html]").parents().get(0).parent().select("td").get(3).text());
            weatherInfo.setEnvironmentalAssessment(getEnvironmentalAssessmentByAQI(weatherInfo.getAQI()));
            weatherInfo.setCityRankings(document.select("a[href=/city/beijing.html").parents().get(0).parent().select("td").get(0).text());
            if (weatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("优")) {
                weatherInfo.setHealthGuidelines("健康影响:空气质量令人满意,基本无空气污染,对健康没有危害;建议措施:各类人群可多参加户外活动,多呼吸一下清新的空气");
            } else if (weatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("良")) {
                weatherInfo.setHealthGuidelines("健康影响:除少数对某些污染物特别敏感的人群外,不会对人体健康产生危害;建议措施:除少数对某些污染物特别容易过敏的人群外,其他人群可以正常进行室外活动");
            } else if (weatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("轻度污染")) {
                weatherInfo.setHealthGuidelines("健康影响:敏感人群症状会有轻度加剧,对健康人群没有明显影响;建议措施:儿童、老年人及心脏病、呼吸系统疾病患者应尽量减少体力消耗大的户外活动");
            } else if (weatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("中度污染")) {
                weatherInfo.setHealthGuidelines("健康影响:敏感人群症状进一步加剧，可能对健康人群的心脏、呼吸系统有影响;建议措施:儿童、老年人及心脏病、呼吸系统疾病患者应尽量减少外出，停留在室内，一般人群应适量减少户外运动");
            } else if (weatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("重度污染")) {
                weatherInfo.setHealthGuidelines("健康影响:此时心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状;建议措施:儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动");
            } else if (weatherInfo.getEnvironmentalAssessment().trim().equalsIgnoreCase("严重污染")) {
                weatherInfo.setHealthGuidelines("健康影响:空气状况极差，所有人的健康都会受到严重危害;建议措施:儿童、老年人和病人应停留在室内，避免体力消耗，除有特殊需要的人群外，一般人群尽量不要停留在室外");
            }

            WeatherCityInfo weatherCityInfo = weatherCityInfoDao.findByCityId(weatherInfo.getCityId());
            if (weatherCityInfo.getMonitoringSite() != null && !weatherCityInfo.getMonitoringSite().equalsIgnoreCase("")) {
                webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getOptions().setCssEnabled(false);
                webClient.setAjaxController(new NicelyResynchronizingAjaxController());
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                rootPage = webClient.getPage(this.beiJingPM25Url);
                document = Jsoup.parseBodyFragment(rootPage.asXml());
                weatherInfo.setAQI(document.select("td:contains(" + weatherCityInfo.getMonitoringSite() + ")").parents().get(0).select("td").get(1).text());
                weatherInfo.setEnvironmentalAssessment(getEnvironmentalAssessmentByAQI(weatherInfo.getAQI()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getEnvironmentalAssessmentByAQI(String aqi) {
        try {
            int i = Integer.parseInt(aqi);
            if (i <= 50) {
                return "优";
            } else if (i > 50 && i <= 100) {
                return "良";
            } else if (i > 100 && i <= 150) {
                return "轻度污染";
            } else if (i > 150 && i <= 200) {
                return "中度污染";
            } else if (i > 200 && i <= 300) {
                return "重度污染";
            } else {
                return "严重污染";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    public void deleteDayWeaterInfo(String cityId) {
        List<WeatherInfoInDay> weatherInfoInDayList = weatherInfoInDayDao.findByCityId(cityId);
        if (weatherInfoInDayList != null && weatherInfoInDayList.size() > 0) {
            weatherInfoInDayDao.delete(weatherInfoInDayList);
        }
    }

    public List<WeatherInfoInDay> generate7DayWeaterInfo(String cityId) {
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
            HtmlPage rootPage = webClient.getPage(this.weather7dUrl + cityId + ".shtml");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());

            List<WeatherInfoInDay> weatherInfoInDayList = new ArrayList<>();
//            HtmlUnitDriver driver = new HtmlUnitDriver();
//            driver.setJavascriptEnabled(true);
//            driver.get(this.weather7dUrl + cityId + ".shtml");
//            Document document = Jsoup.parseBodyFragment(driver.getPageSource());

            for (int i = 0; i < 7; i++) {
                WeatherInfoInDay weatherInfoInDay = new WeatherInfoInDay();
                String day = document.select("ul[class=t clearfix").select("li").get(i).select("h1").text();
                if (day.contains("（")) {
                    day = day.split("（")[0];
                }

                Format f = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DAY_OF_MONTH, i);
                Date tomorrow = c.getTime();
                weatherInfoInDay.setDay(f.format(tomorrow));

                weatherInfoInDay.setWeatherStatus(document.select("ul[class=t clearfix").select("li").get(i).select("p").get(0).text());
                String max = document.select("ul[class=t clearfix").select("li").get(i).select("p[class=tem]").select("span").text();
                if (!max.contains("℃")) {
                    max += "℃";
                }
                weatherInfoInDay.setMaxTemperature(max);
                String min = document.select("ul[class=t clearfix").select("li").get(i).select("p[class=tem]").select("i").text();
                if (!min.contains("℃")) {
                    min += "℃";
                }
                weatherInfoInDay.setMinTemperature(min);

                Elements elements = document.select("ul[class=t clearfix").select("li").get(i).select("p[class=win]").select("span");
                if (elements != null && elements.size() == 2) {
                    String windDirection = "";
                    windDirection += elements.get(0).attr("title") + "转" + elements.get(1).attr("title");
                    weatherInfoInDay.setWindDirection(windDirection);
                }
                weatherInfoInDay.setWindPower(document.select("ul[class=t clearfix").select("li").get(i).select("p[class=win]").select("i").text());
                weatherInfoInDay.setCityId(cityId);
                weatherInfoInDayList.add(weatherInfoInDay);
//            weatherInfoInDayDao.save(weatherInfoInDay);
            }
            return weatherInfoInDayList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<WeatherInfoInDay> generate15DayWeaterInfo(String cityId) {

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
            HtmlPage rootPage = webClient.getPage(this.weather15dUrl + cityId + ".shtml");
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());

//            HtmlUnitDriver driver = new HtmlUnitDriver();
//            driver.setJavascriptEnabled(true);
//            driver.get(this.weather15dUrl + cityId + ".shtml");
//            Document document = Jsoup.parseBodyFragment(driver.getPageSource());

            List<WeatherInfoInDay> weatherInfoInDayList = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                WeatherInfoInDay weatherInfoInDay = new WeatherInfoInDay();
                String day = document.select("ul[class=t clearfix").select("li").get(i).select("span").get(0).text();
                if (day.contains("（")) {
                    day = day.split("（")[0];
                }

                Format f = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.DAY_OF_MONTH, i + 7);
                Date tomorrow = c.getTime();
                weatherInfoInDay.setDay(f.format(tomorrow));

//                weatherInfoInDay.setDay(day);
                weatherInfoInDay.setWeatherStatus(document.select("ul[class=t clearfix").select("li").get(i).select("span").get(1).text());
                String temp = document.select("ul[class=t clearfix").select("li").get(i).select("span[class=tem]").text();
                String[] array = temp.split("/");
                if (array != null && array.length == 2) {
                    if (!array[0].contains("℃")) {
                        array[0] += "℃";
                    }
                    weatherInfoInDay.setMaxTemperature(array[0]);
                    if (!array[1].contains("℃")) {
                        array[1] += "℃";
                    }
                    weatherInfoInDay.setMinTemperature(array[1]);
                }

                Elements elements = document.select("ul[class=t clearfix").select("li").get(i).select("p[class=win]").select("span");
                weatherInfoInDay.setWindDirection(document.select("ul[class=t clearfix").select("li").get(i).select("span").get(3).text());
                weatherInfoInDay.setWindPower(document.select("ul[class=t clearfix").select("li").get(i).select("span").get(4).text());
                weatherInfoInDay.setCityId(cityId);
                weatherInfoInDayList.add(weatherInfoInDay);
//                weatherInfoInDayDao.save(weatherInfoInDay);
            }
            return weatherInfoInDayList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获取北京市所属的区县信息
     */
    public void generateCityInfo() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        driver.setJavascriptEnabled(true);
        driver.get(this.beiJingDistrict);
        Document document = Jsoup.parseBodyFragment(driver.getPageSource());
        Elements elements = document.select("city");
        if (elements != null && elements.size() > 0) {
            for (int i = 0; i < elements.size(); i++) {
                WeatherCityInfo weatherCityInfo = new WeatherCityInfo();
                weatherCityInfo.setCityName(elements.get(i).attr("cityname"));
                weatherCityInfo.setCityId(elements.get(i).attr("url"));
                weatherCityInfoDao.save(weatherCityInfo);
            }
        }
    }

//    public void generateAirQuality() {
//        try {
//            HtmlUnitDriver driver = new HtmlUnitDriver();
//            driver.setJavascriptEnabled(true);
//            driver.get("http://zx.bjmemc.com.cn/getAqiList.shtml?timestamp=1526448442458");
//            Document document = Jsoup.parseBodyFragment(driver.getPageSource());
//            Elements e = document.getElementsByTag("script").eq(6);
//            /*循环遍历script下面的JS变量*/
//            for (Element element : e) {
//                /*取得JS变量数组*/
//                String[] data = element.data().toString().split("var");
//                /*取得单个JS变量*/
//                for (String variable : data) {
//                    /*过滤variable为空的数据*/
//                    if (variable.contains("=")) {
//
//                        /*取到满足条件的JS变量*/
//                        if (variable.contains("option") || variable.contains("config")
//                                || variable.contains("color") || variable.contains("innerColor")) {
//
//                            String[] kvp = variable.split("=");
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
