package com.dm.library.dao;

import com.dm.library.model.Author;
import com.dm.library.model.Book;
import com.dm.library.model.Publisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.dm.library.dao.SQLQuery.*;

public class LibraryDaoJdbcImpl implements LibraryDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addAuthor(Author author) {
        jdbcTemplate.update(SQL_INSERT_AUTHOR,
                author.getFirstName(),
                author.getLastName(),
                author.getStreet(),
                author.getCity(),
                author.getState(),
                author.getZip(),
                author.getPhone());

        int authorId =
                jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        author.setAuthorId(authorId);
    }


    @Override
    public void deleteAuthor(int authorId) {
        jdbcTemplate.update(SQL_DELETE_AUTHOR, authorId);
    }

    @Override
    public void updateAuthor(Author author) {
        jdbcTemplate.update(SQL_UPDATE_AUTHOR,
                author.getFirstName(),
                author.getLastName(),
                author.getStreet(),
                author.getCity(),
                author.getState(),
                author.getZip(),
                author.getPhone(),
                author.getAuthorId());
    }

    @Override
    public Author getAuthorById(int authorId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_AUTHOR,
                    new AuthorMapper(), authorId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    @Override
    public List<Author> getAllAuthors() {
        return jdbcTemplate.query(SQL_SELECT_ALL_AUTHORS, new AuthorMapper());
    }


    private void insertBooksAuthors(Book book) {
        final int bookId = book.getBookId();
        final List<Author> authors = book.getAuthors();

        // Update the books_authors bridge table with an entry for
        // each author of this book
        for (Author currentAuthor : authors) {
            jdbcTemplate.update(SQL_INSERT_BOOKS_AUTHORS, bookId, currentAuthor.getAuthorId());
        }
    }

    private List<Author> findAuthorsForBook(Book book) {
        return jdbcTemplate.query(SQL_SELECT_AUTHORS_BY_BOOK_ID,
                new AuthorMapper(),
                book.getBookId());
    }

    private Publisher findPublisherForBook(Book book) {
        return jdbcTemplate.queryForObject(SQL_SELECT_PUBLISHER_BY_BOOK_ID,
                new PublisherMapper(),
                book.getBookId());
    }

    private List<Book>associatePublisherAndAuthorsWithBooks(List<Book> bookList) {
        // set the complete list of author ids for each book
        for (Book currentBook : bookList) {
            // add Authors to current book
            currentBook.setAuthors(findAuthorsForBook(currentBook));
            // add the Publisher to current book
            currentBook.setPublisher(findPublisherForBook(currentBook));
        }
        return bookList;
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addBook(Book book) {
        // first insert into books table and get newly generated book_id
        jdbcTemplate.update(SQL_INSERT_BOOK,
                book.getIsbn(),
                book.getTitle(),
                book.getPublisher().getPublisherId(),
                book.getPrice(),
                book.getPublishDate().toString());
        book.setBookId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        // now update the books_authors table
        insertBooksAuthors(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteBook(int bookId) {
        // delete books_authors relationship for this book
        jdbcTemplate.update(SQL_DELETE_BOOKS_AUTHORS, bookId);
        // delete book
        jdbcTemplate.update(SQL_DELETE_BOOK, bookId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBook(Book book) {
        // update books table
        jdbcTemplate.update(SQL_UPDATE_BOOK,
                book.getIsbn(),
                book.getTitle(),
                book.getPublisher().getPublisherId(),
                book.getPrice(),
                book.getPublishDate().toString(),
                book.getBookId());
        // delete books_authors relationships and then reset them
        jdbcTemplate.update(SQL_DELETE_BOOKS_AUTHORS, book.getBookId());
        insertBooksAuthors(book);
    }

    @Override
    public Book getBookById(int id) {
        try {
            // get the properties from the books table
            Book book = jdbcTemplate.queryForObject(SQL_SELECT_BOOK,
                    new BookMapper(),id);
            // get the Authors for this book and set list on the book
            book.setAuthors(findAuthorsForBook(book));
            // get the Publisher for this book
            book.setPublisher(findPublisherForBook(book));
            return book;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Book> getBooksByAuthorId(int authorId) {
        // get the books written/cowritten by this author
        List<Book> bookList =
                jdbcTemplate.query(SQL_SELECT_BOOKS_BY_AUTHOR_ID,
                        new BookMapper(),
                        authorId);
        // set the publisher and list of Authors for each book
        return associatePublisherAndAuthorsWithBooks(bookList);
    }

    @Override
    public List<Book> getBooksByPublisherId(int publisherId) {
        // get the books published by this publishers
        List<Book> bookList =
                jdbcTemplate.query(SQL_SELECT_BOOKS_BY_PUBLISHER_ID,
                        new BookMapper(),
                        publisherId);
        // set the publisher and list of Authors for each book
        return associatePublisherAndAuthorsWithBooks(bookList);
    }

    @Override
    public List<Book> getAllBooks() {
        // get all the books
        List<Book> bookList = jdbcTemplate.query(SQL_SELECT_ALL_BOOKS,
                new BookMapper());
        // set the publisher and list of Authors for each book
        return associatePublisherAndAuthorsWithBooks(bookList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPublisher(Publisher publisher) {
        jdbcTemplate.update(SQL_INSERT_PUBLISHER,
                publisher.getName(),
                publisher.getStreet(),
                publisher.getCity(),
                publisher.getState(),
                publisher.getZip(),
                publisher.getPhone());

        int publisherId =
                jdbcTemplate.queryForObject("select LAST_INSERT_ID()",Integer.class);

        publisher.setPublisherId(publisherId);
    }

    @Override
    public void deletePublisher(int publisherId) {
        jdbcTemplate.update(SQL_DELETE_PUBLISHER, publisherId);
    }

    @Override
    public void updatePublisher(Publisher publisher) {
        jdbcTemplate.update(SQL_UPDATE_PUBLISHER,
                publisher.getName(),
                publisher.getStreet(),
                publisher.getCity(),
                publisher.getState(),
                publisher.getZip(),
                publisher.getPhone(),
                publisher.getPublisherId());
    }

    @Override
    public Publisher getPublisherById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_PUBLISHER,
                    new PublisherMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_PUBLISHERS, new PublisherMapper());
    }


    private static final class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            Author au = new Author();
            au.setFirstName(rs.getString("first_name"));
            au.setLastName(rs.getString("last_name"));
            au.setStreet(rs.getString("street"));
            au.setCity(rs.getString("city"));
            au.setState(rs.getString("state"));
            au.setZip(rs.getString("zip"));
            au.setPhone(rs.getString("phone"));
            au.setAuthorId(rs.getInt("author_id"));
            return au;
        }
    }

    private static final class PublisherMapper implements RowMapper<Publisher> {

        @Override
        public Publisher mapRow(ResultSet rs, int i) throws SQLException {
            Publisher pub = new Publisher();
            pub.setPublisherId(rs.getInt("publisher_id"));
            pub.setName(rs.getString("name"));
            pub.setStreet(rs.getString("street"));
            pub.setCity(rs.getString("city"));
            pub.setState(rs.getString("state"));
            pub.setZip(rs.getString("zip"));
            pub.setPhone(rs.getString("phone"));
            return pub;
        }
    }

    private static final class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            Book b = new Book();
            b.setBookId(rs.getInt("book_id"));
            b.setIsbn(rs.getString("isbn"));
            b.setTitle(rs.getString("title"));
            b.setPrice(rs.getBigDecimal("price"));
            b.setPublishDate(rs.getTimestamp("publish_date").
                    toLocalDateTime().toLocalDate());
            return b;
        }
    }


}
