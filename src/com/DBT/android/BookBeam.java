package com.DBT.android;

public class BookBeam {
	public String getBook_URL() {
		return Book_URL;
	}
	public void setBook_URL(String book_URL) {
		Book_URL = book_URL;
	}
	private String Book_URL;
	private String ISBN;
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getPhoto_ID() {
		return Photo_ID;
	}
	public void setPhoto_ID(String photo_ID) {
		Photo_ID = photo_ID;
	}
	
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	public String getEdition() {
		return Edition;
	}
	public void setEdition(String edition) {
		Edition = edition;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getAuthors() {
		return Authors;
	}
	public void setAuthors(String authors) {
		Authors = authors;
	}
	public String getPublichser() {
		return publichser;
	}
	public void setPublichser(String publichser) {
		this.publichser = publichser;
	}
	private String Photo_ID;
	private String Name;
	private String Details;
	private String Edition;
	private String Title;
	private String subtitle;
	private String Authors;
	private String publichser;
}
