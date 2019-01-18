package com.leadmap.environmentalrotection.common.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/28 10:15
 */
public class FormatUtil {
    public static Map getImageContentType() {
        return imageContentType;
    }

    public static void setImageContentType(Map imageContentType) {
        FormatUtil.imageContentType = imageContentType;
    }

    private static Map imageContentType = new HashMap<>();

    static {

        imageContentType.put("jpg", "image/jpeg");

        imageContentType.put("jpeg", "image/jpeg");

        imageContentType.put("png", "image/png");

        imageContentType.put("tif", "image/tiff");

        imageContentType.put("tiff", "image/tiff");

        imageContentType.put("ico", "image/x-icon");

        imageContentType.put("bmp", "image/bmp");

        imageContentType.put("gif", "image/gif");

    }

}
