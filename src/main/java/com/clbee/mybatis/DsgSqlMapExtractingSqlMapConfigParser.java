package com.clbee.mybatis;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Node;

@SuppressWarnings("unchecked")
public class DsgSqlMapExtractingSqlMapConfigParser {

	private List sqlMapList = new ArrayList();

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	public List parse(InputStream inputStream) {
		try {

			return sqlMapList;
		}
		catch (Exception e) {
			throw new RuntimeException("Error occurred.  Cause: " + e, e);
		}
	}
}