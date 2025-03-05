package com.jts.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.jts.lms.book.BookDetail;
import com.jts.lms.book.Bookingdetails;
import com.jts.lms.login.database.Bookdao;
import com.jts.lms.login.database.Studentdao;


public class BookService {
	static Scanner sc = new Scanner(System.in);
	static int sr_no;

	public static void searchBySrNo(Connection conn) throws SQLException { /* This method helps to get the information
																			 from admin about Serial number of the
																			 book and Display it.*/
		System.out.println("Enter Serial_Number of the Book: ");
		sr_no = sc.nextInt();

		Bookdao sb = new Bookdao();
		BookDetail bd = sb.getBooksBySno(conn, sr_no);

		if (bd != null) {
			System.out.println("******Book Details****");
			System.out.println("| S_NO :" + bd.getS_No() + "   | Book_Name: " + bd.getBookName() + "   |Authur_Name: "
					+ bd.getAuthor_Name() + "|");
			System.out.println();
		} else {
			System.out.println("No information found in the " + sr_no + ". Please Enter the correct Information");
		}
	}

	public static void searchByAuthorName(Connection conn) throws SQLException { /* This method helps to get the
																					 information from admin about
																					 Author_Name and Display it.*/
		System.out.println("Enter the Author_Name of the Book: ");
		String authorName = sc.nextLine();

		Bookdao sb = new Bookdao();
		BookDetail bd = sb.getBooksByAuthorName(conn, authorName);

		if (bd != null) {
			System.out.println("******Book Details****");
			System.out.println("| S_NO :" + bd.getS_No() + "   | Book_Name: " + bd.getBookName() + "   |Authur_Name: "
					+ bd.getAuthor_Name() + "|");
			System.out.println();
		} else {
			System.out.println("No information found in the " + authorName + ". Please Enter the Correct information");
		}
	}

	public static void bookSearch(Connection conn) throws SQLException { /* Getting the option to Search the book and
																			 pass it to another method*/

		System.out.println("1. Search by Serial Number:");
		System.out.println("2. Search by Author_Name:");

		System.out.println("Enter your Choice: ");
		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {
		case 1:
			BookService.searchBySrNo(conn);
			break;
		case 2:
			BookService.searchByAuthorName(conn);
			break;
		default:
			System.out.println("Invalid Choice" + choice);
		}

	}

	public static void addBook(Connection conn) throws SQLException { // This method gets Information to add Books in the Table

		System.out.println("Enter Serial Number: ");
		int sNo = sc.nextInt();
		sc.nextLine();

		System.out.println("Enter Book Name: ");
		String bookName = sc.nextLine();

		System.out.println("Enter Athor Name: ");
		String author_name = sc.nextLine();

		System.out.println("Enter Book Quantity: ");
		int qty = sc.nextInt();

		Bookdao bd = new Bookdao();
		BookDetail book = bd.getBooksBySno(conn, sNo);

		if (book != null) {

			System.out.println("Book Already exist!!!");
			return;
		}

		BookDetail input = new BookDetail(); // Adding book Details in table
		input.setAuthor_Name(author_name);
		input.setBookName(bookName);
		input.setBookQnty(qty);
		input.setS_No(sNo);

		bd.SaveBook(conn, input); // Here our information are added through conn parameter to our table Book table
	}

	public static void getAllBooks(Connection conn) throws SQLException { // This method used for Displaying entire Book
																			// detail in the Table
		Bookdao bd = new Bookdao();
		List<BookDetail> books = bd.getAllBook(conn);

		if (books == null) { // Our table is checked here baded on Condition
			System.out.println("No books list returned from Database Query.");
		}

		else if (books.isEmpty()) {
			System.out.println("Books list is empty.");
		}

		else {
			System.out.println("+-------+-------------------------------+---------------------+-------------+");
			System.out.println("+  S_NO |          Book_Name            |     Author_Name     |   Quantity  |");
			System.out.println("+-------+-------------------------------+---------------------+-------------+");

			for (BookDetail book : books) {
				System.out.printf(" | %-4s | %-29s | %-20s | %-11s  ",book.getS_No(),book.getBookName(), book.getAuthor_Name(),book.getBookQnty());
				System.out.println();
			}
		}
	}

	public static void updateBook(Connection conn) throws SQLException { // updating book method

		System.out.println("Enter Book's serial_Number: ");
		int srNo = sc.nextInt();
		sc.nextLine();

		Bookdao bd = new Bookdao();
		BookDetail book = bd.getBooksBySno(conn, srNo);

		if (book == null) {
			System.out.println("Book not Exist!!!");
			return;
		}

		System.out.println("Enter Number of book to Update: ");
		int qty = sc.nextInt();
		BookDetail input = new BookDetail();
		input.setBookQnty(book.getBookQnty() + qty); // Value are incremented here
		input.setS_No(srNo);

		Bookdao.updateQuality(conn, input);
	}

	public static void checkOutBook(Connection conn) throws SQLException { // Book checkOut method
		Studentdao sd = new Studentdao();

		System.out.println("Enter the Register Number of the Student: ");
		int regNum = sc.nextInt();

		boolean isExist = sd.getStudentByReg(conn, regNum);

		if (!isExist) {
			System.out.println("Student are not Registered.First Register.");
			return;
		}
		getAllBooks(conn);

		System.out.println("Enter the Serial Number to Check: ");
		int sNo = sc.nextInt();

		Bookdao bd = new Bookdao();
		BookDetail book = bd.getBooksBySno(conn, sNo);

		if (book == null) {
			System.out.println("Book Not Available.");
			return;
		}

		book.setBookQnty(book.getBookQnty() - 1); // After bookcheckOut, the quantity valuue are decremented here
		int id = sd.getStudentByReg_id(conn, regNum);

		sd.SaveBookDetail(conn, id, book.getId(), 1);
		Bookdao.updateQuality(conn, book); // This method passes the date to update the value in MySql
	}

	public static void returnBook(Connection conn) throws SQLException { // CheckIn Method
		Studentdao sd = new Studentdao();

		System.out.println("Enter the Register Number: ");
		int regNum = sc.nextInt();

		boolean isExist = sd.getStudentByReg(conn, regNum);

		if (!isExist) {
			System.out.println(" No Book to Return!!!.");
			System.out.println("Get Some books");
			return;
		}

		int id = sd.getStudentByReg_id(conn, regNum);

		List<Bookingdetails> bookingDetails = sd.getAllBookingDetailId(conn, id);


		bookingDetails.stream().forEach(b -> System.out.println("| SR_NO: "+b.srNo + " |Book_Name: " + b.bookName + " |Author_Name: " + b.authorName));

		System.out.println("Enter the Serial Number to Return: ");
		int sNo = sc.nextInt();
		Bookingdetails filterDetails = bookingDetails.stream().filter(b -> b.getSrNo() == sNo).findAny().orElse(null);
		Bookdao bd = new Bookdao();
		BookDetail book = bd.getBooksBySno(conn, sNo);

		if (filterDetails == null) {
			System.out.println("No Book to Return.");
			return;
		}

		book.setBookQnty(book.getBookQnty() + 1);// After return the quantity is incremented

		Bookdao.updateQualityReturn(conn, book);
		sd.deletebookingDetail(conn, filterDetails.getId()); // After upadating the return it delete the record
	}
}
