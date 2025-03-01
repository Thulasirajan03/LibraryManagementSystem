package com.jts.lms.login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.jts.lms.login.database.DatabaseService;
import com.jts.lms.login.database.Logindao;
import com.jts.lms.service.BookService;
import com.jts.lms.service.StudentService;

public class LoginService { // Displaying the Menu for Admin and Students
	Scanner sc = new Scanner(System.in);
	String username, password;

	public void doLogin() throws ClassNotFoundException, SQLException {
		System.out.println("Please Enter the Username: ");
		username = sc.nextLine();

		System.out.println("Please Enter the Password: ");
		password = sc.nextLine();

		try (Connection conn = DatabaseService.getConnection()) {
			Logindao ld = new Logindao();
			String userType = ld.doLogin(conn, username, password);
			if (userType == null) {
				System.out.println("Invalid User");
				return;
				
			} else {
				System.out.println("Welcome!!!!!!!! ");
			}

			System.out.println("Login Successfull. You Login as " + userType);
			System.out.println("Please Select the given Options: ");

			if (userType.equals("admin")) {
				// librain or Admin Details
				displayAdminMenu(conn);
			} else {
				// Student Details
				displayStudentMenu(conn);
			}
		}
	}

	public void displayAdminMenu(Connection conn) throws SQLException { // Admin Menu
		int choice;

		do {
			System.out.println("************************************************");
			System.out.println("1. View the Available Books :");
			System.out.println("2. Add new Book :");
			System.out.println("3. Search the book Details :");
			System.out.println("4. Update the boook Quantity:");
			System.out.println("5. Register Students:");
			System.out.println("6. View Student Details :");
			System.out.println("7. View Student Feedback or Suggestion :");
			System.out.println("8. Exit from the Application: ");
			System.out.println("************************************************");

			System.out.println("Enter your Choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				BookService.getAllBooks(conn); // methods
				break;
			case 2:
				BookService.addBook(conn);
				break;
			case 3:
				BookService.bookSearch(conn);
				break;
			case 4:
				BookService.updateBook(conn);
				break;
			case 5:
				StudentService.addStudent(conn);
				break;
			case 6:
				StudentService.getAllStudents(conn);
				break;
			case 7:
				StudentService. displayFeedback(conn);
				break;
			case 8:
				System.out.println("Thank you for using Library Management System");
				break;
			default:
				System.out.println(" Invalid choice. Choose Again!!!");

			}
		} while (choice != 8);

	}

	public void displayStudentMenu(Connection conn) throws SQLException { // Student Menu
		int choice;

		// BookService bs = new BookService();
		// StudentService ss = new StudentService();

		do {
			System.out.println("************************************************");
			System.out.println("1. View the Available Books :");
			System.out.println("2. Get a Book :");
			System.out.println("3. Return Book:");
			System.out.println("4. Search the Book Details :");
			System.out.println("5. Give a Suggestion :");
			System.out.println("6. Exit from the Application");
			System.out.println("************************************************");

			System.out.println("Enter your Choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				BookService.getAllBooks(conn); // methods
				break;
			case 2:
				BookService.checkOutBook(conn);
				break;
			case 3:
				BookService.returnBook(conn);
				break;
			case 4:
				BookService.bookSearch(conn);
				break;
			case 5:
				StudentService.feedback(conn);
				break;
			case 6:
				System.out.println("Thank you for using Library Management System");
				break;

			default:
				System.out.println(" Invalid choice. Choose again!!!");

			}
		} while (choice != 6);

	}

}
