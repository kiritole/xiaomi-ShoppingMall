package com.qf.utils;

import javax.servlet.ServletContext;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: ServletContextUtils
 * @Date: 2022/9/24 21:16
 */
public class ServletContextUtils {
    public static ServletContext servletContext;

    public static void setServletContext(ServletContext context){
        servletContext = context;
    }

    public static ServletContext getServletContext(){
        return servletContext;
    }
}
