package org.crane.flink.util;

import java.util.Properties;

/**
 * Module:User Defined Util
 * CLassName: DefinedUtil
 * Description:
 * Author: Crane
 * date: 2020/12/3 下午11:15
 */
public class DefinedUtil {
    public static Properties pro;

    static {
        pro = System.getProperties();
    }

    /***
     * @Author:Crane
     * @Description: Get OS Name information
     * @Date: 2020/12/5 上午12:20
     * @Param: []
     * @Return: java.lang.String
     */
    public static String os() {
        //Linux  Windows
        return pro.getProperty("os.name");
    }

}
