package com.leadmap.management.mapper;

import com.leadmap.management.model.Resources;
import com.leadmap.management.util.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface ResourcesMapper extends MyMapper<Resources> {

    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String, Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);
}