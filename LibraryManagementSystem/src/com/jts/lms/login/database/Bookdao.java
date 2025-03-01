package com.jts.lms.login.database;

import com.jts.lms.book.BookDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bookdao { // This class is used to get and save data from database

	public BookDetail getBooksBySno(Connection conn, int srNo) throws SQLException { // using book Serial number
		String query = "Select * from books where sr_no=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, srNo);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					BookDetail book = new BookDetail();
					book.setAuthor_Name(rs.getString("author_name"));
					book.setBookName(rs.getString("name"));
					book.setBookQnty(rs.getInt("qty"));
					book.setId(rs.getInt("id"));
					book.setS_No(rs.getInt("sr_no"));

					return book;
				}
			}
		}
		return null;
	}

	public BookDetail getBooksByAuthorName(Connection conn, String authorName) throws SQLException { // using book Athur
																										// Name
		String query = "Select * from books where author_name=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, authorName);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					BookDetail book = new BookDetail();
					book.setAuthor_Name(rs.getString("author_name"));
					book.setBookName(rs.getString("name"));
					book.setBookQnty(rs.getInt("qty"));
					book.setId(rs.getInt("id"));
					book.setS_No(rs.getInt("sr_no"));

					return book;
				}
			}
		}
		return null;
	}

	public BookDetail getBooksByAuthorNameorsrNo(Connection conn, int srNo,String author_name) throws SQLException {
		String query = "Select * from books where author_name=? or sr_no=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, author_name);
			ps.setInt(2, srNo);
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					BookDetail book = new BookDetail();
					book.setAuthor_Name(rs.getString("author_name"));
					book.setBookName(rs.getString("name"));
					book.setBookQnty(rs.getInt("qty"));
					// book.setId(rs.getInt("id"));
					book.setS_No(rs.getInt("sr_no"));

					return book;
				}
			}
		}
		return null;
	}

	public void SaveBook(Connection conn, BookDetail book) throws SQLException { // Collected data are saved here
		String query = "insert into books(sr_no,name,author_name,qty)values(?,?,?,?)";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, book.getS_No());
			ps.setString(2, book.getBookName());
			ps.setString(3, book.getAuthor_Name());
			ps.setInt(4, book.getBookQnty());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Book Added Successfully!!");
				return;
			} else {
				System.out.println("Book Adding failed!!!");
			}
		}
	}

	public List<BookDetail> getAllBook(Connection conn) throws SQLException {
		String query = "Select * from books";
		List<BookDetail> books = new ArrayList<>(); // List is used to store the entire book in the Table

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BookDetail book = new BookDetail();
					book.setAuthor_Name(rs.getString("author_name"));
					book.setBookName(rs.getString("name"));
					book.setBookQnty(rs.getInt("qty"));
					book.setId(rs.getInt("id"));
					book.setS_No(rs.getInt("sr_no"));

					books.add(book);

				}

			}
		}
		return books;
	}

	public static void updateQuality(Connection conn, BookDetail book) throws SQLException {
		String query = "update books set qty=? where sr_no=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, book.getBookQnty());
			ps.setInt(2, book.getS_No());

			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Book upgraded Successfully!!");
				return;
			} else {
				System.out.println("Book ugrade Failed!!!");
			}
		}

	}
	
	public static void updateQualityReturn(Connection conn, BookDetail book) throws SQLException {
		String query = "update books set qty=? where sr_no=?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, book.getBookQnty());
			ps.setInt(2, book.getS_No());

			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Book Returned Successfully!!");
				return;
			} else {
				System.out.println("Book Return Failed!!!");
			}
		}

	}
	public static void displayFeedback(Connection conn,String suggestion,int reg_num) throws SQLException {
	    String query = "SELECT * FROM feedback ";
	    
	    try (PreparedStatement ps = conn.prepareStatement(query)){
	    		ps.setString(1, suggestion);
	    		ps.setInt(2, reg_num);
	         ResultSet rs = ps.executeQuery();

			if (rs.next());
				
			}
		}

}
