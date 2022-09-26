package com.qf.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据库工具类：将连接数据库的一些操作，封装成工具方法，方便连接数据库的时候使用。
 * 
 * 1：提取了一些公用的方法
 * 
 * 2：将url user  pwd 信息 都使用配置文件管理。
 */
public class DBUtil {
	//定义德鲁伊的连接池数据源对象。
	private static DataSource dataSource;

	//提供对外的访问的接口。
	public static DataSource getDateSource(){
		return dataSource;
	}
	
	//读取配置文件的内容
	static{
		try {
			//容器
			Properties prop = new Properties();
			// prop.load(DBUtil.class.getResourceAsStream("/druid.prop"));
			// 加载配置文件
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
			//通过配置文件的容器去创建连接池数据源对象。
			dataSource = DruidDataSourceFactory.createDataSource(prop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//构造方法私有化
	private DBUtil() {}

	
}
