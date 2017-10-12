
use slack;

INSERT INTO Channels (ChannelName) values('Public');
INSERT INTO Channels (ChannelName) values('JavaOOP17');
INSERT INTO Channels (ChannelName) values('Political');
INSERT INTO Channels (ChannelName) values('MS.NET');
INSERT INTO Channels (ChannelName) values('PrivateMessage#1');
INSERT INTO Channels (ChannelName) values('PrivateMessage#2');

INSERT INTO Users (UserName, UserProfile) values ('Danimaetrix', 'DMProfile');
INSERT INTO Users (UserName, UserProfile) values ('Vitaly', 'Vitaly''s Profile');
INSERT INTO Users (UserName, UserProfile) values ('Corbin', 'Corndog');
INSERT INTO Users (UserName, UserProfile) values ('DonaldTrump5', ':( :( :(');
INSERT INTO Users (UserName, UserProfile) values ('Charlie', 'Candy Mountain');
INSERT INTO Users (UserName, UserProfile) values ('Barney88', 'Please');

-- 'YYYY-MM-DD HH:MM:SS' --
INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (1, 1, '2017-09-27 07:34:23', 'The first message ever');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (1, 2, '2017-09-27 23:15:23', 'Here''s a message!');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (3, 2, '2017-09-27 07:34:23', 'Ugh, mornings are terrible...');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (1, 1, '2017-09-27 04:34:23', 'lol');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (1, 1, '2017-09-27 11:34:23', 'Can someone post the link?');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (3, 1, '2017-09-27 13:34:23', 'This is the last thing I needed today');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (2, 4, '2017-09-27 12:15:23', 'Time for lunch!');

INSERT INTO Messages (MessageAuthorId, MessageChannelId, MessageDateTime, MessageBody)
values (2, 2, '2017-09-27 09:34:23', 'LOL omg..');

INSERT INTO PrivilegesTable (PrivilegeDesc) values('Normal');
INSERT INTO PrivilegesTable (PrivilegeDesc) values('Administrator');

INSERT INTO UserPrivileges (UserId, PrivilegeId) values(1,1);
INSERT INTO UserPrivileges (UserId, PrivilegeId) values(2,2);
INSERT INTO UserPrivileges (UserId, PrivilegeId) values(3,2);
INSERT INTO UserPrivileges (UserId, PrivilegeId) values(4,1);
INSERT INTO UserPrivileges (UserId, PrivilegeId) values(5,1);
INSERT INTO UserPrivileges (UserId, PrivilegeId) values(6,1);

INSERT INTO UserMessages(MessageId, UserId) values(1, 1);
INSERT INTO UserMessages(MessageId, UserId) values(2, 1);
INSERT INTO UserMessages(MessageId, UserId) values(3, 3);
INSERT INTO UserMessages(MessageId, UserId) values(4, 1);
INSERT INTO UserMessages(MessageId, UserId) values(5, 1);
INSERT INTO UserMessages(MessageId, UserId) values(6, 3);
INSERT INTO UserMessages(MessageId, UserId) values(7, 2);
INSERT INTO UserMessages(MessageId, UserId) values(8, 2);

INSERT INTO ChannelMessages(MessageId, ChannelId) values(1, 1);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(2, 2);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(3, 2);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(4, 1);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(5, 1);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(6, 1);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(7, 4);
INSERT INTO ChannelMessages(MessageId, ChannelId) values(8, 2);