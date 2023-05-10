package cn.edu.ntu.job.quartz.tutorials.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Format date to string.
 *
 * @author zack <br>
 * @create 2020-12-27 <br>
 * @project quartz <br>
 */
public class DFUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return sdf.format(date);
    }
}
