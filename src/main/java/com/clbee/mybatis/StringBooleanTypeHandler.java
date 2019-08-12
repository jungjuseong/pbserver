package com.clbee.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public abstract class StringBooleanTypeHandler implements TypeHandler<Boolean> {

	
	public Boolean getResult(ResultSet arg0, String arg1) throws SQLException {
		return arg0.getBoolean(arg1);
	}

	
	public Boolean getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		return arg0.getBoolean(arg1);
	}

	
	public void setParameter(PreparedStatement arg0, int arg1, Boolean arg2,
			JdbcType arg3) throws SQLException {
		arg0.setBoolean(arg1, arg2);		
	}

}


