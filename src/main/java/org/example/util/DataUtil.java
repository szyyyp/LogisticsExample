package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者： Szy
 * 日期: 2023/3/28  17:12
 */
public class DataUtil {
    static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    public static String getTime(Date dt){
        return fmt.format(dt);
    }
}
