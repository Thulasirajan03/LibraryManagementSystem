package com.jts.lms.book;

public class BookDetail { // This class is used to set and get the data.
	public int id;
	public int S_No;
	public String bookName;
	public String Author_Name;
	public int bookQnty;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getS_No() {
		return S_No;
	}

	public void setS_No(int s_No) {
		S_No = s_No;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor_Name() {
		return Author_Name;
	}

	public void setAuthor_Name(String author_Name) {
		Author_Name = author_Name;
	}

	public int getBookQnty() {
		return bookQnty;
	}

	public void setBookQnty(int bookQnty) {
		this.bookQnty = bookQnty;
	}
}
