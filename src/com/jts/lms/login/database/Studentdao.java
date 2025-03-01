package com.jts.lms.login.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jts.lms.book.Bookingdetails;


public class Studentdao {

	public boolean getStudentByReg(Connection conn, int regNum) throws SQLException { /* to Check student avialable i
																						 the table*/
		String query = "Select * from students where reg_num=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, regNum);

			try (ResultSet rs = ps.executeQuery()) {

				return rs.next();
			}
		}

	}

	public int getStudentByReg_id(Connection conn, int regNum) throws SQLException { /* used to get the id to check-out
																						 books*/
		String query = "Select * from students where reg_num=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, regNum);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return rs.getInt(1);
				}

			}
		}
		return 0;

	}

	public void SaveStudent(Connection conn, String StudentName, int regNum, String dept) throws SQLException { // Here student detail are inserted
		String query = "insert into students(std_name,reg_num,dept)values(?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, StudentName);
			ps.setInt(2, regNum);
			ps.setString(3, dept);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Student Added Successfully!!");
				return;
			} else {
				System.out.println("Student Adding failed!!!");
			}
		}
	}
	
	public static void saveFeedback(Connection conn, int regNum, String suggestion) throws SQLException {
	    String query = "INSERT INTO feedback (reg_num, suggestion) VALUES (?, ?)";
	    
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, regNum);
	        ps.setString(2, suggestion);
	        ps.executeUpdate();
   }
	}

            
	public void getAllStudent(Connection conn) throws SQLException { // Added Student are Displayed here
		String query = "Select * from students";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			try (ResultSet rs = ps.executeQuery()) {

				System.out.println("*****************Student Details*******************");
				System.out.println("+-------+-------------------------+-----------------+-------------+");
				System.out.println("+  S_ID |        Student_Name     | Register_Number |  Department |");
				System.out.println("+-------+-------------------------+-----------------+-------------+");
				while (rs.next()) {
					System.out.printf("| %-5s | %-23s | %-16s | %-11s",rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
					System.out.println();

				}

			}
		}

	}

	public void SaveBookDetail(Connection conn, int std_id, int book_id, int qnty) throws SQLException { /* check-out
																											 information
																											 are added
																											 in the
																											 table*/
		String query = "insert into booking_details(std_id,book_id,qty)values(?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, std_id);
			ps.setInt(2, book_id);
			ps.setInt(3, qnty);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Booking details Added Successfully!!");
				return;
			} else {
				System.out.println("Booking details Adding Failed!!!");
			}
		}
	}

	public List<Bookingdetails> getAllBookingDetailId(Connection conn, int stdId)throws SQLException { /* Here the information of booking_detail table are combined with the book table */
		String query = "SELECT * FROM booking_details bd " + "INNER JOIN books b ON b.id = bd.book_id " + // joint are
																											// implemented
				"WHERE bd.std_id = ?";

		List<Bookingdetails> bookingDetails = new ArrayList<>();

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, stdId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Bookingdetails bookingDetail = new Bookingdetails();

				bookingDetail.setAuthorName(rs.getString("author_name"));
				bookingDetail.setBookName(rs.getString("name"));
				bookingDetail.setId(rs.getInt("std_id"));
				bookingDetail.setBookId(rs.getInt("qty"));
				bookingDetail.setSrNo(rs.getInt("sr_no"));

				bookingDetails.add(bookingDetail);
			}
		}

		return bookingDetails;
	}
	
	

	public void deletebookingDetail(Connection conn, int id) throws SQLException { // Deletion operation performed here
		String query = "delete from booking_details where id=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.executeUpdate(); // after completion data are updated here
		}
	}
}
