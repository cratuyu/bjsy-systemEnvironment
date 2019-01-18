package com.leadmap.erawl.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.leadmap.erawl.dao.CountryMonitorAirCompanyInfoDao;
import com.leadmap.erawl.entity.company.CountryMonitorAirCompanyInfo;
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
 * @Date: 2018/7/18 15:23
 */
@Component
public class ErawlCountryMonitorAirCompanyInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlCountryMonitorAirCompanyInfoService.class);

    @Autowired
    private CountryMonitorAirCompanyInfoDao countryMonitorAirCompanyInfoDao;

    public void generateDataFromSite() {
        try {
            WebClient webclient = new WebClient();
            webclient.getOptions().setCssEnabled(false);
            webclient.getOptions().setJavaScriptEnabled(false);
            HtmlPage htmlpage = webclient.getPage("http://58.30.229.115/PublicGKDayDataWebSite/index.aspx");
            HtmlForm form = htmlpage.getFormByName("form1");

            List<HtmlInput> radios = form.getInputsByName("OutPutType");
            if (radios != null && radios.size() > 0) {
                radios.get(1).click();
            }

            List<HtmlInput> inputs = form.getInputsByName("btnExcute");
            if (inputs != null && inputs.size() > 0) {
                HtmlPage htmlPage = inputs.get(0).click();
                Document document = Jsoup.parseBodyFragment(htmlPage.asXml());
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
                        HashMap<String, CountryMonitorAirCompanyInfo> hashMap = new HashMap();
                        for (int i = 0; i < finalTrList.size(); i++) {
                            List<Element> tr = finalTrList.get(i);
                            CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo = new CountryMonitorAirCompanyInfo();
                            countryMonitorAirCompanyInfo.setCityName(tr.get(0).text());
                            countryMonitorAirCompanyInfo.setCompanyName(tr.get(2).text());
                            countryMonitorAirCompanyInfo.setCompanyAddress(tr.get(3).text());
                            countryMonitorAirCompanyInfo.setMonitorName(tr.get(4).text());
                            countryMonitorAirCompanyInfo.setMonitorDate(tr.get(5).text());
                            countryMonitorAirCompanyInfo.setDayCumulativeFlow(tr.get(6).text());
                            countryMonitorAirCompanyInfo.setGasFlow(tr.get(7).text());

                            String key = tr.get(0).text() + "-" + tr.get(2).text() + "-" + tr.get(3).text() + "-" + tr.get(4).text();
                            if (!hashMap.containsKey(key)) {
                                hashMap.put(key, countryMonitorAirCompanyInfo);
                            }
                        }

                        for (int i = 0; i < finalTrList.size(); i++) {
                            List<Element> tr = finalTrList.get(i);
                            String key = tr.get(0).text() + "-" + tr.get(2).text() + "-" + tr.get(3).text() + "-" + tr.get(4).text();
                            CountryMonitorAirCompanyInfo countryMonitorAirCompanyInfo = hashMap.get(key);
                            Float x = 116.523942f;
                            Float y = 39.883738f;
                            Random random = new Random();
                            if (countryMonitorAirCompanyInfo != null) {
                                if (tr.get(8).text().equalsIgnoreCase("二氧化硫")) {
                                    countryMonitorAirCompanyInfo.setSo2DayMeanValue(tr.get(9).text());
                                    countryMonitorAirCompanyInfo.setSo2Standard(tr.get(10).text());
                                    countryMonitorAirCompanyInfo.setSo2LastAuditDate(tr.get(11).text());
                                    countryMonitorAirCompanyInfo.setSo2lastAuditStatus(tr.get(12).text());
                                    countryMonitorAirCompanyInfo.setSo2Mark(tr.get(13).text());
                                    countryMonitorAirCompanyInfo.setX(((Float) (x - random.nextFloat())).toString());
                                    countryMonitorAirCompanyInfo.setY(((Float) (y - random.nextFloat())).toString());
                                } else {
                                    countryMonitorAirCompanyInfo.setNoDayMeanValue(tr.get(9).text());
                                    countryMonitorAirCompanyInfo.setNoStandard(tr.get(10).text());
                                    countryMonitorAirCompanyInfo.setNoLastAuditDate(tr.get(11).text());
                                    countryMonitorAirCompanyInfo.setNoLastAuditStatus(tr.get(12).text());
                                    countryMonitorAirCompanyInfo.setNoMark(tr.get(13).text());
                                    countryMonitorAirCompanyInfo.setX(((Float) (x - random.nextFloat())).toString());
                                    countryMonitorAirCompanyInfo.setY(((Float) (y - random.nextFloat())).toString());
                                }
                                countryMonitorAirCompanyInfo.setCreateTime(new Date());
                            }
                        }

                        Format f = new SimpleDateFormat("yyyy-MM-dd");
                        Date today = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(today);
                        c.add(Calendar.DAY_OF_MONTH, -1);
                        Date yesterday = c.getTime();
                        List<CountryMonitorAirCompanyInfo> countryMonitorAirCompanyInfos = countryMonitorAirCompanyInfoDao.findByMonitorDate(f.format(yesterday));
                        if (countryMonitorAirCompanyInfos.size() == 0) {
                            countryMonitorAirCompanyInfoDao.save(hashMap.values());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
