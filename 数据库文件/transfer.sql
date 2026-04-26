create table transfer
(
    transfer_id      int auto_increment comment '调动记录序号（自增唯一）'
        primary key,
    emp_id           int          not null comment '被调动员工编号（关联员工表）',
    transfer_date    date         not null comment '调动日期',
    transfer_out_dpt int          not null comment '调出部门编号（关联部门表）',
    transfer_in_dpt  int          not null comment '调入部门编号（关联部门表）',
    transfer_reason  varchar(200) not null comment '调动原因（如岗位调整、部门合并等）',
    constraint transfer_ibfk_1
        foreign key (emp_id) references employee (emp_id),
    constraint transfer_ibfk_2
        foreign key (transfer_out_dpt) references department (dpt_id),
    constraint transfer_ibfk_3
        foreign key (transfer_in_dpt) references department (dpt_id)
)
    comment '员工调动信息记录表';

create index emp_id
    on transfer (emp_id);

create index transfer_in_dpt
    on transfer (transfer_in_dpt);

create index transfer_out_dpt
    on transfer (transfer_out_dpt);

INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (1, 3008, '2025-12-04', 5, 3, '没有理由');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (2, 1003, '2025-12-05', 3, 1, '理由');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (11, 1007, '2025-12-14', 8, 3, '3');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (12, 1007, '2025-12-14', 3, 7, '7');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (13, 1007, '2025-12-14', 7, 1, '1');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (14, 1007, '2025-12-14', 1, 6, '6');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (15, 1007, '2025-12-26', 6, 2, '无');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (18, 1007, '2025-12-26', 2, 8, 'try');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (20, 1007, '2025-12-29', 8, 8, 'Try');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (21, 1007, '2025-12-29', 8, 2, 'try');
INSERT INTO dbwork.transfer (transfer_id, emp_id, transfer_date, transfer_out_dpt, transfer_in_dpt, transfer_reason) VALUES (22, 1007, '2025-12-30', 2, 6, 'try');
