DROP DATABASE IF EXISTS library;

CREATE DATABASE library;
USE library;

CREATE TABLE IF NOT EXISTS `authors` (
 `author_id` int(11) NOT NULL AUTO_INCREMENT,
 `first_name` varchar(20) NOT NULL,
 `last_name` varchar(20) NOT NULL,
 `street` varchar(20) NOT NULL,
 `city` varchar(20) NOT NULL,
 `state` varchar(20) NOT NULL,
 `zip` varchar(20) NOT NULL,
 `phone` varchar(20) NOT NULL,
 PRIMARY KEY (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `books` (
 `book_id` int(11) NOT NULL AUTO_INCREMENT,
 `isbn` varchar(20) NOT NULL,
 `title` varchar(50) NOT NULL,
 `publisher_id` int(11) NOT NULL,
 `price` decimal(5,2) NOT NULL,
 `publish_date` datetime NOT NULL,
 PRIMARY KEY (`book_id`),
 KEY `publisher_id` (`publisher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `books_authors` (
 `book_id` int(11) NOT NULL,
 `author_id` int(11) NOT NULL,
 KEY `book_id` (`book_id`),
 KEY `author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `publishers` (
 `publisher_id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(20) NOT NULL,
 `street` varchar(20) NOT NULL,
 `city` varchar(20) NOT NULL,
 `state` varchar(20) NOT NULL,
 `zip` varchar(20) NOT NULL,
 `phone` varchar(20) NOT NULL,
 PRIMARY KEY (`publisher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for table `books`
--
ALTER TABLE `books`
 ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`publisher_id`) REFERENCES `publishers`
(`publisher_id`) ON DELETE NO ACTION;
--
-- Constraints for table `books_authors`
--
ALTER TABLE `books_authors`
 ADD CONSTRAINT `books_authors_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books`
(`book_id`) ON DELETE NO ACTION,
 ADD CONSTRAINT `books_authors_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `authors`
(`author_id`) ON DELETE NO ACTION;