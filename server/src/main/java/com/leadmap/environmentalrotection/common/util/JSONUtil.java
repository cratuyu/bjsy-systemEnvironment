package com.leadmap.environmentalrotection.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Company: www.leadmap.net
 * Description:json工具类
 *
 * @author: yxm
 * @Date: 2018/9/25 10:18
 */
public class JSONUtil {

    private static Logger logger = Logger.getLogger(JSONUtil.class);

    private static ObjectMapper om = new ObjectMapper();

    public static String writeValueAsString(Object entity) {
        om.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value,JsonGenerator jg,SerializerProvider sp) throws IOException, JsonProcessingException {
                jg.writeString("-");
            }
        });
        try {
            String result = om.writeValueAsString(entity);
            //结果打印在控制台
            logger.debug(result);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
