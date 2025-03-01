package com.jts.lms.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.jts.lms.login.database.Studentdao;

public class StudentService {
	static Scanner sc= new Scanner(System.in);
	
	
	
	public static void addStudent(Connection conn) throws SQLException {
		
		System.out.println("Please enter the Student Name: ");
		String studentName=sc.nextLine();
		 
		
		System.out.println("Please enter the Register Number: ");
		int regNum=sc.nextInt();
		sc.nextLine();
		
		System.out.println("Please enter the Department: ");
		String dept=sc.nextLine();
		

		Studentdao bd=new Studentdao();
		boolean studentExist=bd.getStudentByReg(conn,regNum);
		
		
		if(studentExist) {
			
			System.out.println("Student Already exist!!!");
			return;
		}
		
		bd.SaveStudent(conn,studentName,regNum,dept);
	}
	
	public static void feedback(Connection conn) throws SQLException {
		System.out.print("Enter Registration Number: ");
        int regNumber = sc.nextInt();
        sc.nextLine();  
        
        System.out.print("Enter Suggestion: ");
        String suggest = sc.nextLine();
        
        
        Studentdao.saveFeedback(conn, regNumber, suggest);
        
        System.out.println("Feedback saved successfully!");
	}
	
	
	public static void getAllStudents(Connection conn) throws SQLException {
	    Studentdao sd=new Studentdao();
	    sd.getAllStudent(conn);
	}
	public static void displayFeedback(Connection conn) throws SQLException {
	    String query = "SELECT * FROM feedback";
	    
	    try (PreparedStatement ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	         
	    	System.out.println("+---------+-------------------------------+---------------------+-------------+");
			System.out.println("+  REG_NO |          Book_Name                                                |");
			System.out.println("+---------+-------------------------------+---------------------+-------------+");
	        while (rs.next()) {
	            int regNum = rs.getInt("reg_num");
	            String suggestion = rs.getString("suggestion");
	            
	            System.out.printf( "| %-7s | %-29s  " , regNum,suggestion);
	            System.out.println();
	        }
	    }
	}
}
