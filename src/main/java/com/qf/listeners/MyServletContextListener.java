package com.qf.listeners;

import com.qf.utils.ServletContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: MyServletContextListener
 * @Date: 2022/9/24 23:03
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ServletContextUtils.setServletContext(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
