package com.leadmap.management.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.mapper.OpinionFeedbackMapper;
import com.leadmap.management.model.OpinionFeedback;
import com.leadmap.management.service.OpinionFeedbackService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@Service("opinionFeedbackService")
public class OpinionFeedbackServiceImpl extends BaseService<OpinionFeedback> implements OpinionFeedbackService {

    @Resource
    private OpinionFeedbackMapper opinionFeedbackMapper;

    @Override
    public PageInfo<OpinionFeedback> selectByPage(OpinionFeedback opinionFeedback, int start, int length) {
        int page = start/length+1;
        Example example = new Example(OpinionFeedback.class);
        //分页查询
        PageHelper.startPage(page, length);
        List<OpinionFeedback> opinionFeedbackList = opinionFeedbackMapper.selectByOpinionFeedback(opinionFeedback);
        return new PageInfo<>(opinionFeedbackList);
    }

}
