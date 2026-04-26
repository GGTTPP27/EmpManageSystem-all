create table department
(
    dpt_id      int auto_increment comment '部门编号（自增，1、2、3...）'
        primary key,
    dpt_name    varchar(50) not null comment '部门名称（如技术部、人事部）',
    create_time datetime    null,
    update_time datetime    null,
    constraint dpt_name
        unique (dpt_name)
)
    comment '部门信息表';

INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (1, '人事部门', '2025-11-05 16:02:45', '2025-11-05 23:54:03');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (2, '后勤部门', '2025-12-14 17:06:21', '2025-12-14 17:06:21');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (3, '管理部门', '2025-11-05 16:05:50', '2025-11-05 16:05:50');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (4, '法务部门', '2025-12-14 17:06:41', '2025-12-14 17:06:41');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (5, '技术部门', '2025-11-07 13:07:30', '2025-11-07 13:07:30');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (6, '市场部门', '2025-11-07 13:07:42', '2025-11-07 13:07:42');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (7, '财务部门', '2025-11-07 13:07:47', '2025-11-07 13:07:47');
INSERT INTO dbwork.department (dpt_id, dpt_name, create_time, update_time) VALUES (8, '监控部门', '2025-11-07 13:07:59', '2025-11-07 13:07:59');
