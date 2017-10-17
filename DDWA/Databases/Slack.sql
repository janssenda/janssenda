drop database if exists slack;
create database slack;

use slack;

CREATE TABLE Channels (
    ChannelId INT PRIMARY KEY AUTO_INCREMENT,
    ChannelName VARCHAR(255) not null
);
CREATE TABLE Users (
    UserId INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(255) not null,
    UserProfile TEXT
);
CREATE TABLE Messages (
    MessageId INT PRIMARY KEY AUTO_INCREMENT,
    MessageAuthorId INT not null,
    MessageBody TEXT not null,
    MessageDateTime DATETIME not null,
    MessageChannelId INT not null
);

