--
-- 转存表中的数据 `oauth_user`
--

INSERT INTO `user` (`id`, `username`, `password`, `nick_name`, `avatar`, `is_unexpired`, `is_enabled`, `is_unlocked`,
                    `is_deleted`, `create_time`, `update_time`)
VALUES (1, 'admin', '$2a$10$2DOh9rf4IsUkUhXOLASxFe/Vqc5tivu/kk.y0Ut1KWkC6v87EcPW.', '管理员', '', 1, 1, 1, 0,
        '2024-06-19 00:00:00.000', '2024-06-11 00:00:00.000');



insert into `role` (id, name, code, remark, data_scope, is_enabled, is_deleted, create_time, create_by, update_time,
                    update_by, organization_id)
values (1, '管理员', 'admin', '超级管理员', 1, 1, 0, '2023-05-21 00:49:18', 1, '2023-05-21 00:49:18', 1, 1);

INSERT INTO user_role_mapping (id, role_id, user_id, create_time)
VALUES (2, 1, 1, '2024-07-13 20:29:05.000');

INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (1, 1, 'sys', '2024-07-13 12:18:49.139');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (2, 1, 'sys:member', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (3, 1, 'sys:member:add', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (4, 1, 'sys:member:edit', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (5, 1, 'sys:member:del', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (6, 1, 'sys:member:reset_pwd', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (7, 1, 'sys:role', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (8, 1, 'sys:role:add', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (9, 1, 'sys:role:edit', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (10, 1, 'sys:role:del', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (11, 1, 'sys:menu', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (12, 1, 'sys:menu:add', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (13, 1, 'sys:menu:edit', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (14, 1, 'sys:menu:del', '2024-07-13 12:18:49.140');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (15, 1, 'sys:dept', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (16, 1, 'sys:dept:add', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (17, 1, 'sys:dept:edit', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (18, 1, 'sys:dept:del', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (19, 1, 'sys:dict', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (20, 1, 'sys:dict_type:add', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (21, 1, 'sys:dict_type:edit', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (22, 1, 'sys:dict_type:del', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (23, 1, 'sys:dict:add', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (24, 1, 'sys:dict:edit', '2024-07-13 12:18:49.141');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (25, 1, 'sys:dict:del', '2024-07-13 12:18:49.141');


INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (26, 1812100202631475201, 'sys', '2024-07-13 12:25:58.523');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (27, 1812100202631475201, 'sys:member', '2024-07-13 12:25:58.524');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (28, 1812100202631475201, 'sys:member:add', '2024-07-13 12:25:58.524');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (29, 1812100202631475201, 'sys:member:edit', '2024-07-13 12:25:58.525');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (30, 1812100202631475201, 'sys:member:del', '2024-07-13 12:25:58.525');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (31, 1812100202631475201, 'sys:member:reset_pwd', '2024-07-13 12:25:58.526');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (32, 1812100202631475201, 'sys:role', '2024-07-13 12:25:58.526');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (33, 1812100202631475201, 'sys:role:add', '2024-07-13 12:25:58.526');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (34, 1812100202631475201, 'sys:role:edit', '2024-07-13 12:25:58.527');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (35, 1812100202631475201, 'sys:role:del', '2024-07-13 12:25:58.527');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (36, 1812100202631475201, 'sys:dept', '2024-07-13 12:25:58.528');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (37, 1812100202631475201, 'sys:dept:add', '2024-07-13 12:25:58.528');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (38, 1812100202631475201, 'sys:dept:edit', '2024-07-13 12:25:58.528');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (39, 1812100202631475201, 'sys:dept:del', '2024-07-13 12:25:58.529');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (40, 1812100202631475201, 'sys:dict', '2024-07-13 12:25:58.529');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (41, 1812100202631475201, 'sys:dict_type:add', '2024-07-13 12:25:58.529');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (42, 1812100202631475201, 'sys:dict_type:edit', '2024-07-13 12:25:58.530');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (43, 1812100202631475201, 'sys:dict_type:del', '2024-07-13 12:25:58.531');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (44, 1812100202631475201, 'sys:dict:add', '2024-07-13 12:25:58.531');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (45, 1812100202631475201, 'sys:dict:edit', '2024-07-13 12:25:58.532');
INSERT INTO role_authority_mapping (id, role_id, authority_code, create_time)
VALUES (46, 1812100202631475201, 'sys:dict:del', '2024-07-13 12:25:58.532');

