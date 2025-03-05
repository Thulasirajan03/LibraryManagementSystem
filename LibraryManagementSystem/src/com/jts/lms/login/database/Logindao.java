package com.jts.lms.login.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Logindao {
	String query;
	
	public String doLogin(Connection conn,String username,String password) throws SQLException {
		String query= "Select * from login where user_name=? and password=?";
		try(PreparedStatement ps= conn.prepareStatement(query)){
			ps.setString(1,username);
			ps.setString(2,password);
		
		try(ResultSet rs=ps.executeQuery()){
			if(rs.next()) {
				return rs.getString("user_type");
			}
		}
		
		}
	return null;
}
}
