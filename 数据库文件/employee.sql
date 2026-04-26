create table employee
(
    emp_id               int                     not null comment '员工编号（格式：部门号+3位序号，如1001表示部门1的第1个员工）'
        primary key,
    emp_name             varchar(50)             not null comment '员工姓名',
    emp_status           int                     not null comment '员工状态（1-在职，2-兼职，3-试用，4-离职，5-返聘，6-退休）',
    emp_onboard          date                    not null comment '到岗日期',
    emp_job              varchar(50)             not null comment '工作岗位（如软件工程师、行政专员）',
    emp_title            varchar(50)             null comment '职务（如主管、经理，可为空）',
    dpt_id               int                     not null comment '当前所在部门编号（关联部门表）',
    handle_time          datetime                null comment '处理时间（如状态变更/人事审批/异动处理等时间）',
    transfer_reason_temp varchar(200) default '' not null comment '临时存储调动原因，触发器使用后由后端清空',
    constraint employee_ibfk_1
        foreign key (dpt_id) references department (dpt_id)
)
    comment '员工基本信息表';

create index dpt_id
    on employee (dpt_id);

INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1001, '张明', 4, '2020-03-15', '软件工程师', '技术主管', 1, '2025-12-29 13:03:32', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1002, '李婷', 2, '2021-07-22', '前端开发工程师', '前端开发专员', 1, '2025-12-28 13:50:25', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1003, '王浩', 1, '2023-01-10', '后端开发工程师', '后端开发专员', 1, '2025-12-14 13:32:03', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1004, '赵静', 2, '2019-09-05', '测试工程师', '测试组长', 1, '2025-12-14 13:31:57', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1005, '孙阳', 6, '2022-05-18', '技术支持工程师', '技术支持专员', 1, '2025-12-14 17:08:43', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1006, '周敏', 5, '2018-06-30', '系统架构师', '技术总监', 1, '2025-12-27 01:19:35', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1007, '1', 1, '2025-11-29', 'try', 'try', 6, '2025-12-28 13:53:42', 'try');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1008, '小填', 4, '2025-12-14', '干活', '干活', 1, '2025-12-27 00:59:37', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1009, '夏加聋', 1, '2025-12-28', 'try', 'try', 1, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (1010, '倪压轮', 1, '2025-12-28', 'try', 'try', 1, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (2001, '张四', 1, '2025-12-29', 'try', 'try', 2, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3001, '小王', 1, '2025-11-07', '看别人敲代码', '代码王', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3002, '钱芳', 2, '2021-08-15', '人事专员', '人事行政专员', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3003, '冯丽', 3, '2023-02-18', '招聘专员', '招聘助理', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3004, '陈强', 4, '2019-10-12', '薪酬绩效专员', '薪酬管理专员', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3005, '褚华', 5, '2022-06-25', '人力资源经理', '人事总监', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3006, '卫杰', 6, '2018-07-18', '企业文化专员', '文化建设主管', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3007, '蒋雯', 2, '2024-03-12', '员工关系专员', '员工关系助理', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (3008, '夏嘉隆', 3, '2025-12-04', '传教士', '传教', 3, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (4001, 'NewYear', 2, '2025-12-26', '翻译员', '翻译', 4, '2025-12-29 13:03:09', '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5001, '小李', 1, '2025-11-07', '敲代码', '程序猿', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5002, '小张', 1, '2025-11-07', '敲更多代码', '程序猿二号', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5003, '杨明', 3, '2023-03-05', '销售代表', '销售助理', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5004, '朱琳', 1, '2019-11-20', '区域销售经理', '销售总监', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5005, '秦宇', 5, '2022-07-10', '渠道销售专员', '渠道助理', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5006, '尤佳', 6, '2018-08-22', '销售顾问', '高级销售顾问', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5007, '许峰', 3, '2024-04-05', '商务代表', '商务拓展专员', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5008, '沈超', 1, '2020-05-25', '销售经理', '销售主管', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5009, '韩雪', 2, '2021-09-30', '客户经理', '高级客户经理', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5010, '李博文', 2, '2025-11-29', '三角洲打手', '打三角洲', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (5011, '宁秋水', 6, '2025-12-05', '黑客', '破译', 5, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6001, '何伟', 1, '2020-06-10', '财务经理', '财务主管', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6002, '吕娜', 2, '2021-10-18', '总账会计', '会计专员', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6003, '施洋', 3, '2023-04-12', '出纳', '出纳专员', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6004, '张颖', 4, '2019-12-08', '财务总监', '首席财务官（CFO）助理', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6005, '孔亮', 5, '2022-08-15', '税务专员', '税务管理助理', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6006, '曹静', 6, '2018-09-10', '审计专员', '审计主管', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (6007, '严涛', 4, '2024-05-18', '成本会计', '成本核算专员', 6, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7001, '小灰灰', 1, '2025-11-07', '吃羊', '狼', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7002, '魏芳', 2, '2021-11-25', '产品专员', '产品助理', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7003, '陶丽', 3, '2023-05-20', 'UI/UX设计师', 'UI设计专员', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7004, '姜浩', 4, '2020-01-15', '交互设计师', '设计组长', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7005, '薛强', 5, '2022-09-22', '高级产品经理', '产品总监', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7006, '侯敏', 6, '2018-10-15', '数据分析师', '数据分析主管', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7007, '彭丽', 5, '2024-06-22', '用户研究员', '用户研究专员', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (7008, '金鹏', 1, '2020-07-18', '产品经理', '产品主管', 7, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8001, '邓凯', 1, '2020-08-22', '运维经理', '运维主管', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8002, '蔡琴', 2, '2021-12-10', '网络工程师', '网络运维专员', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8003, '潘杰', 3, '2023-06-08', '网络安全工程师', '安全专员', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8004, '袁琳', 4, '2020-02-20', '系统运维工程师', '技术总监', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8005, '于洋', 5, '2022-10-30', '数据库管理员（DBA）', '数据库运维专员', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8006, '杜静', 6, '2018-11-28', '云运维工程师', '云运维主管', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8007, '程涛', 6, '2024-07-10', '安全架构师', '高级安全顾问', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8008, '倪亚伦', 3, '2025-11-29', '保安', '安保', 8, null, '');
INSERT INTO dbwork.employee (emp_id, emp_name, emp_status, emp_onboard, emp_job, emp_title, dpt_id, handle_time, transfer_reason_temp) VALUES (8009, 'NewPeople', 1, '2025-12-29', 'Job', 'work', 8, null, '');
