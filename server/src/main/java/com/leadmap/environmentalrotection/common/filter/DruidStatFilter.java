package com.leadmap.environmentalrotection.common.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Company: www.leadmap.net
 * Description:
 * 配置druid监控统计功能
 * 配置Filter
 * @author: ttq
 * @Date: 2018/5/16 10:37
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        }
)

public class DruidStatFilter extends WebStatFilter {

}
