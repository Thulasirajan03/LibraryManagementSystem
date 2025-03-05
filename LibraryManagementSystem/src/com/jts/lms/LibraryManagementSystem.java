package com.jts.lms;

import java.sql.SQLException;

import com.jts.lms.login.LoginService;

public class LibraryManagementSystem {
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		System.out.println("****************** Welcome to Library Mangement System ****************************");

		LoginService ls = new LoginService();
		ls.doLogin();
	}

}

