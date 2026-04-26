create table user
(
    id          int auto_increment
        primary key,
    username    varchar(10) not null,
    password    varchar(16) not null,
    create_time datetime    null,
    update_time datetime    null
);

INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (1, 'GTP', '111111', '2025-10-31 14:12:41', '2025-12-30 11:09:17');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (2, 'Ja', '123123', '2025-11-03 16:21:33', '2025-11-03 16:47:45');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (3, 'JJJ', '131313', '2025-12-11 14:36:42', '2025-12-11 14:36:42');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (4, 'Wells', '123456', '2025-12-11 15:17:14', '2025-12-11 15:17:14');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (5, 'GG', 'GGGGGG', '2025-12-11 15:19:07', '2025-12-11 15:19:07');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (6, 'GGG', 'gggggg', '2025-12-11 15:19:48', '2025-12-11 15:19:48');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (7, 'TT', '111111', '2025-12-11 15:20:04', '2025-12-28 17:58:00');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (8, 'Coward', '232323', '2025-12-11 15:44:00', '2025-12-11 15:44:00');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (9, 'New', '111111', '2025-12-11 15:58:39', '2025-12-11 15:58:39');
INSERT INTO dbwork.user (id, username, password, create_time, update_time) VALUES (10, 'NewP', '123456', '2025-12-28 23:21:42', '2025-12-28 23:22:33');
