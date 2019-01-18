package com.leadmap.erawl.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leadmap.erawl.dao.CountryMonitorWaterCompanyInfoDao;
import com.leadmap.erawl.entity.company.CountryMonitorWaterCompanyInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 15:17
 */
@Component
public class ErawlCountryMonitorWaterCompanyInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlCountryMonitorWaterCompanyInfoService.class);

    @Autowired
    private CountryMonitorWaterCompanyInfoDao countryMonitorWaterCompanyInfoDao;

    public void generateDataFromSite() {
        try {
            WebClient webclient = new WebClient();
            webclient.getOptions().setCssEnabled(false);
            webclient.getOptions().setJavaScriptEnabled(false);
            HtmlPage htmlpage = webclient.getPage("http://58.30.229.115/PublicGKDayDataWebSite/index.aspx");
            HtmlForm form = htmlpage.getFormByName("form1");

            List<HtmlInput> radios = form.getInputsByName("OutPutType");
            if (radios != null && radios.size() > 0) {
                radios.get(0).click();
            }

            List<HtmlInput> inputs = form.getInputsByName("btnExcute");
            if (inputs != null && inputs.size() > 0) {
                HtmlPage htmlPage = inputs.get(0).click();
                Document document = Jsoup.parseBodyFragment(htmlPage.asXml());
                if (document != null) {
                    Elements trs = document.select("tr");
                    if (trs != null && trs.size() > 0) {
                        List<List<Element>> finalTrList = new ArrayList<>();
                        Element preTr = null;
                        List<Element> preTds = null;
                        for (int i = 2; i < trs.size(); i++) {
                            if (preTr == null) {
                                preTr = trs.get(i);
                                finalTrList.add(preTr.select("td"));
                                continue;
                            } else {
                                preTds = finalTrList.get(finalTrList.size() - 1);
                                Element currentTr = trs.get(i);
                                Elements currentTds = currentTr.select("td");
                                if (preTds.size() > currentTds.size()) {
                                    for (int j = 0; j < (preTds.size() - currentTr.select("td").size()); j++) {
                                        currentTds.add(j, preTds.get(j));
                                    }
                                }

                                finalTrList.add(currentTds);
                                preTr = currentTr;
                            }
                        }

                        if (finalTrList != null) {
                            HashMap<String, CountryMonitorWaterCompanyInfo> hashMap = new HashMap();
                            for (int i = 0; i < finalTrList.size(); i++) {
                                List<Element> tr = finalTrList.get(i);
                                CountryMonitorWaterCompanyInfo countryMonitorWaterCompanyInfo = new CountryMonitorWaterCompanyInfo();
                                countryMonitorWaterCompanyInfo.setCityName(tr.get(0).text());
                                countryMonitorWaterCompanyInfo.setCompanyName(tr.get(2).text());
                                countryMonitorWaterCompanyInfo.setCompanyAddress(tr.get(3).text());
                                countryMonitorWaterCompanyInfo.setMonitorName(tr.get(4).text());
                                countryMonitorWaterCompanyInfo.setMonitorDate(tr.get(5).text());
                                countryMonitorWaterCompanyInfo.setDayCumulativeFlow(tr.get(6).text());

                                String key = tr.get(0).text() + "-" + tr.get(2).text() + "-" + tr.get(3).text() + "-" + tr.get(4).text();
                                if (!hashMap.containsKey(key)) {
                                    hashMap.put(key, countryMonitorWaterCompanyInfo);
                                }
                            }

                            for (int i = 0; i < finalTrList.size(); i++) {
                                List<Element> tr = finalTrList.get(i);
                                String key = tr.get(0).text() + "-" + tr.get(2).text() + "-" + tr.get(3).text() + "-" + tr.get(4).text();
                                CountryMonitorWaterCompanyInfo countryMonitorWaterCompanyInfo = hashMap.get(key);
                                Float x = 116.523942f;
                                Float y = 39.883738f;
                                Random random = new Random();
                                if (tr.get(7).text().equalsIgnoreCase("化学需氧量(COD)")) {
                                    countryMonitorWaterCompanyInfo.setCodDayMeanValue(tr.get(8).text());
                                    countryMonitorWaterCompanyInfo.setCodStandard(tr.get(9).text());
                                    countryMonitorWaterCompanyInfo.setCodLastAuditDate(tr.get(10).text());
                                    countryMonitorWaterCompanyInfo.setCodLastAuditStatus(tr.get(11).text());
                                    countryMonitorWaterCompanyInfo.setCodMark(tr.get(12).text());
                                    countryMonitorWaterCompanyInfo.setX(((Float) (x - random.nextFloat())).toString());
                                    countryMonitorWaterCompanyInfo.setY(((Float) (y - random.nextFloat())).toString());
                                } else {
                                    countryMonitorWaterCompanyInfo.setNh4DayMeanValue(tr.get(8).text());
                                    countryMonitorWaterCompanyInfo.setNh4Standard(tr.get(9).text());
                                    countryMonitorWaterCompanyInfo.setNh4LastAuditDate(tr.get(10).text());
                                    countryMonitorWaterCompanyInfo.setNh4LastAuditStatus(tr.get(11).text());
                                    countryMonitorWaterCompanyInfo.setNh4Mark(tr.get(12).text());
                                    countryMonitorWaterCompanyInfo.setX(((Float) (x - random.nextFloat())).toString());
                                    countryMonitorWaterCompanyInfo.setY(((Float) (y - random.nextFloat())).toString());
                                }
                                countryMonitorWaterCompanyInfo.setCreateTime(new Date());
                            }

                            Format f = new SimpleDateFormat("yyyy-MM-dd");
                            Date today = new Date();
                            Calendar c = Calendar.getInstance();
                            c.setTime(today);
                            c.add(Calendar.DAY_OF_MONTH, -1);
                            Date yesterday = c.getTime();
                            if (countryMonitorWaterCompanyInfoDao.findByMonitorDate(f.format(yesterday)).size() == 0) {
                                countryMonitorWaterCompanyInfoDao.save(hashMap.values());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
