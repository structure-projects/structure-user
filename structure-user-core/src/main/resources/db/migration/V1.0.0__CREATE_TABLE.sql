-- --------------------------------------------------------
--
-- 表的结构 `role`
--
CREATE TABLE IF NOT EXISTS `role`
(
    `id`
    bigint
(
    20
) NOT NULL COMMENT '主键id',
    `name` varchar
(
    128
) NOT NULL COMMENT '名称',
    `code` varchar
(
    64
) NOT NULL COMMENT '编码',
    `remark` varchar
(
    255
) DEFAULT NULL COMMENT '描述',
    `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)',
    `is_enabled` tinyint
(
    1
) NOT NULL DEFAULT '1' COMMENT '是否启用 1:  启用 0:未启用',
    `is_deleted` tinyint
(
    1
) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 ，1 是',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `create_by` bigint
(
    20
) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `update_by` bigint
(
    20
) DEFAULT NULL COMMENT '更新人',
    `organization_id` bigint
(
    20
) NOT NULL COMMENT '组织ID',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';
-- --------------------------------------------------------
--
-- 表的结构 `role_authority_mapping`
--

CREATE TABLE IF NOT EXISTS `role_authority_mapping`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` bigint
(
    20
) NOT NULL COMMENT '角色ID',
    `authority_code` VARCHAR
(
    32
) NOT NULL COMMENT '权限CODE',
    `create_time` datetime
(
    3
) NOT NULL COMMENT '创建时间',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4 COMMENT ='角色权限表';

-- --------------------------------------------------------
--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT ' 主键ID ',
    `username` varchar
(
    64
) NOT NULL COMMENT '用户名',
    `password` varchar
(
    128
) DEFAULT NULL COMMENT '加密后的密码',
    `nick_name` varchar
(
    64
) DEFAULT NULL COMMENT '昵称',
    `avatar` varchar
(
    255
) DEFAULT NULL COMMENT '头像',
    `intro` varchar
(
    255
) DEFAULT NULL COMMENT '简介',
    `sex` char
(
    1
) DEFAULT NULL COMMENT '性别,N 未知,M 男 ,F 女',
    `phone` varchar
(
    20
) DEFAULT NULL COMMENT '电话',
    `email` varchar
(
    255
) DEFAULT NULL COMMENT '邮箱',
    `is_unexpired` tinyint
(
    1
) NOT NULL DEFAULT '1' COMMENT '是否未过期 0：过期 1：未过期',
    `is_enabled` tinyint
(
    1
) NOT NULL DEFAULT '1' COMMENT '是否启用 1:  启用 0:未启用',
    `is_unlocked` tinyint
(
    1
) NOT NULL DEFAULT '1' COMMENT '是否未锁定 0:  锁定 1:未锁定',
    `is_deleted` tinyint
(
    1
) NOT NULL DEFAULT '0' COMMENT '是否删除 0：未删除 1：删除',
    `create_time` datetime
(
    3
) NOT NULL COMMENT '创建时间',
    `update_time` datetime
(
    3
) NOT NULL COMMENT '修改时间',
    PRIMARY KEY
(
    `id`
),
    UNIQUE KEY `uk_username`
(
    `username`
) USING BTREE
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- --------------------------------------------------------
--
-- 表的结构 `oauth_user_authority_mapping`
--

CREATE TABLE IF NOT EXISTS `user_authority_mapping`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint
(
    20
) NOT NULL COMMENT '用户ID',
    `authority_code` VARCHAR
(
    32
) NOT NULL COMMENT '权限CODE',
    `create_time` datetime
(
    3
) NOT NULL COMMENT '创建时间',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8 COMMENT ='用户权限关系表';

-- --------------------------------------------------------

--
-- 表的结构 `user_bind`
--

CREATE TABLE IF NOT EXISTS `user_bind`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` bigint
(
    20
) NOT NULL COMMENT '用户ID',
    `platform_user_id` varchar
(
    32
) NOT NULL COMMENT '平台用户ID',
    `platform_code` varchar
(
    32
) NOT NULL COMMENT '平台编码',
    `create_time` datetime
(
    3
) NOT NULL COMMENT '创建时间',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `user_role_mapping`
--

CREATE TABLE IF NOT EXISTS `user_role_mapping`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint
(
    20
) NOT NULL COMMENT '角色ID',
    `user_id` bigint
(
    20
) NOT NULL COMMENT '用户ID',
    `create_time` datetime
(
    3
) NOT NULL COMMENT '创建时间',
    PRIMARY KEY
(
    `id`
),
    UNIQUE KEY `uk_role_user`
(
    `role_id`,
    `user_id`
) USING BTREE
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关系表';

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `user_tag`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tag` varchar
(
    64
) NOT NULL COMMENT '标签名',
    `user_id` bigint
(
    20
) NOT NULL COMMENT '用户ID',
    `create_time` datetime
(
    3
) NOT NULL COMMENT '创建时间',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4 COMMENT ='用户标签表';