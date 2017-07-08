package com.jyoffice.actflow;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;

public class GenCode {
	static String driver = "org.gjt.mm.mysql.Driver";
	static String url = "jdbc:mysql://127.0.0.1/cbj";
	static String userName = "root";
	static String password = "root";

	public static void main(String[] args) {
		ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, "cbj", userName,
				password);
		DBStyle mysql = new MySqlStyle();
		// sql语句放在classpagth的/sql 目录下
		SQLLoader loader = new ClasspathLoader("sql");
		// 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的
		UnderlinedNameConversion nc = new UnderlinedNameConversion();
		// 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
		SQLManager sqlManager = new SQLManager(mysql, loader, source, nc,
				new Interceptor[] { new DebugInterceptor() });
		
		try {
			/*sqlManager.genPojoCode("sys_auto", "com.jyoffice.sys.model");
			sqlManager.genPojoCode("sys_menu", "com.jyoffice.sys.model");
			sqlManager.genPojoCode("sys_role", "com.jyoffice.sys.model");
			sqlManager.genPojoCode("sys_role_auto", "com.jyoffice.sys.model");
			sqlManager.genPojoCode("sys_role_user", "com.jyoffice.sys.model");
			sqlManager.genPojoCode("sys_user", "com.jyoffice.sys.model");
			
			sqlManager.genSQLFile("sys_auto");
			sqlManager.genSQLFile("sys_menu");
			sqlManager.genSQLFile("sys_role");
			sqlManager.genSQLFile("sys_role_auto");
			sqlManager.genSQLFile("sys_role_user");
			sqlManager.genSQLFile("sys_user");*/
			sqlManager.genPojoCode("act_run_businfo", "com.jyoffice.actflow.model");
			sqlManager.genSQLFile("act_run_businfo");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
