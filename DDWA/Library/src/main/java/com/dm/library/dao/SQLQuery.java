package com.dm.library.dao;

public class SQLQuery {

    public static final String SQL_INSERT_AUTHOR
            = "insert into authors (first_name, last_name, street, city, "
            + "state, zip, phone) values (?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_DELETE_AUTHOR
            = "delete from authors where author_id = ?";

    public static final String SQL_UPDATE_AUTHOR
            = "update authors set first_name = ?, last_name = ?, street = ?, "
            + "city = ?, state = ?, zip = ?, phone = ? where author_id =  ?";

    public static final String SQL_SELECT_AUTHOR
            = "select * from authors where author_id = ?";

    public static final String SQL_SELECT_AUTHORS_BY_BOOK_ID
            = "select au.author_id, au.first_name, au.last_name, au.street, "
            + "au.city, au.state, au.zip, au.phone from authors au "
            + "join books_authors ba on au.author_id = ba.author_id where "
            + "ba.book_id = ?";

    public static final String SQL_SELECT_ALL_AUTHORS
            = "select * from authors";


    // BOOKS AND BOOKS_AUTHORS
    public static final String SQL_INSERT_BOOK
            = "insert into books "
            + "(isbn, title, publisher_id, price, publish_date) "
            + "values (?, ?, ?, ?, ?)";

    public static final String SQL_INSERT_BOOKS_AUTHORS
            = "insert into books_authors (book_id, author_id) values(?, ?)";

    public static final String SQL_DELETE_BOOK
            = "delete from books where book_id = ?";

    public static final String SQL_DELETE_BOOKS_AUTHORS
            = "delete from books_authors where book_id = ?";

    public static final String SQL_UPDATE_BOOK
            = "update books set isbn = ?, title = ?, publisher_id = ?, "
            + "price = ?, publish_date = ? "
            + "where book_id = ?";

    public static final String SQL_SELECT_BOOK
            = "select * from books where book_id = ?";

    public static final String
            SQL_SELECT_BOOKS_AUTHORS_AUTHOR_ID_BY_BOOK_ID
            = "select author_id from books_authors where book_id = ?";

    public static final String SQL_SELECT_ALL_BOOKS
            = "select * from books";

    public static final String SQL_SELECT_BOOKS_BY_AUTHOR_ID
            = "select b.book_id, b.isbn, b.title, b.publisher_id, b.price, "
            + "b.publish_date from books b join books_authors ba on author_id "
            + "where b.book_id = ba.book_id "
            + "and ba.author_id  =  ?;";

    public static final String SQL_SELECT_BOOKS_BY_PUBLISHER_ID
            = "select * from books where publisher_id = ?";

    public static final String SQL_INSERT_PUBLISHER
            = "insert into publishers (name, street, city, state, zip, phone) "
            + "values (?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_PUBLISHER
            = "delete from publishers where publisher_id = ?";
    public static final String SQL_UPDATE_PUBLISHER
            = "update publishers set name = ?, street = ?, city = ?, "
            + "state = ?, zip = ?, phone = ? where publisher_id  =  ?";
    public static final String SQL_SELECT_PUBLISHER
            = "select * from publishers where publisher_id = ?";
    public static final String SQL_SELECT_PUBLISHER_BY_BOOK_ID
            = "select pub.publisher_id, pub.name, pub.street, pub.city, "
            + "pub.state, pub.zip, pub.phone from publishers pub "
            + "join books on pub.publisher_id = books.publisher_id where "
            + "books.book_id = ?";
    public static final String SQL_SELECT_ALL_PUBLISHERS
            = "select * from publishers";



}
