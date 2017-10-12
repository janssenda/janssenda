drop database if exists slack;
create database slack;

use slack;

CREATE TABLE Channels (
    ChannelId INT PRIMARY KEY AUTO_INCREMENT,
    ChannelName VARCHAR(255) not null
);
CREATE TABLE Users (
    UserId INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(255) NOT NULL,
    UserProfile TEXT
);
CREATE TABLE Messages (
    MessageId INT PRIMARY KEY AUTO_INCREMENT,
    MessageAuthorId INT not null,
    MessageBody TEXT not null,
    MessageDateTime DATETIME not null,
    MessageChannelId INT not null
);

CREATE TABLE PrivilegesTable (
    PrivilegeId INT PRIMARY KEY AUTO_INCREMENT,
    PrivilegeDesc VARCHAR(255)
);

-- BRIDGE TABLES --

CREATE TABLE UserMessages (
    MessageId INT,
    UserId INT,
    primary key (UserId, MessageId),
    foreign key (UserId) references Users(UserId),
    foreign key (MessageId) references Messages(MessageId)
);

CREATE TABLE ChannelMessages (
    MessageId INT,
    ChannelId INT,
    primary key (ChannelId, MessageId),
    foreign key (ChannelId) references Channels(ChannelId),
    foreign key (MessageId) references Messages(MessageId)
);

CREATE TABLE UserPrivileges (
    UserId INT,
    PrivilegeId INT,
    PRIMARY KEY (UserId , PrivilegeId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId),
    FOREIGN KEY (PrivilegeId) REFERENCES PrivilegesTable (PrivilegeId)
);

