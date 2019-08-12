package com.clbee.mybatis;

public interface RefreshableSqlSessionFactoryBean {
	void refresh() throws Exception;

	void setCheckInterval(int ms);
}
