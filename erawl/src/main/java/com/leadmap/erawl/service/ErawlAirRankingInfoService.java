package com.leadmap.erawl.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leadmap.erawl.common.util.Util;
import com.leadmap.erawl.dao.AirRankingInfoDao;
import com.leadmap.erawl.entity.air.AirRankingInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/13 10:45
 */
@Component
public class ErawlAirRankingInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlAirRankingInfoService.class);

    @Value("${airPaimingUrl}")
    private String airPaimingUrl;

    @Autowired
    private AirRankingInfoDao airRankingInfoDao;

    public void generateData() {
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage rootPage = webClient.getPage(this.airPaimingUrl);
            Document document = Jsoup.parseBodyFragment(rootPage.asXml());
            String updateTime = document.select("div[class=weilai]").select("div").get(2).text().replace("更新：", "");

            List<AirRankingInfo> airRankingInfoList = airRankingInfoDao.findByUpdateTime(updateTime);
            if (airRankingInfoList.size() == 0) {
                Elements elements = document.select("table[id=goodtable]").select("tbody").select("tr");
                if (elements.size() > 0) {
//                    airRankingInfoDao.delete(airRankingInfoDao.findAll());
                    List<AirRankingInfo> airRankingInfoList1 = new ArrayList<>();
                    for (int i = 0; i < elements.size(); i++) {
                        AirRankingInfo airRankingInfoDTO = new AirRankingInfo();
                        airRankingInfoDTO.setIndexValue(elements.get(i).select("td").get(0).select("div").text().trim());
                        airRankingInfoDTO.setCity(elements.get(i).select("td").get(1).select("a").text());
                        airRankingInfoDTO.setProvince(elements.get(i).select("td").get(2).text());
                        airRankingInfoDTO.setAqi(elements.get(i).select("td").get(3).text());
                        airRankingInfoDTO.setLevel(elements.get(i).select("td").get(4).select("div").select("font").text());
                        airRankingInfoDTO.setPm25(elements.get(i).select("td").get(5).text());
                        airRankingInfoDTO.setUpdateTime(updateTime);
                        airRankingInfoDTO.setCreateTime(new Date());

                        airRankingInfoList1.add(airRankingInfoDTO);
                    }
                    airRankingInfoDao.delete(airRankingInfoDao.findAll(Util.getDaySpecification(new Date())));
                    airRankingInfoDao.save(airRankingInfoList1);
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
