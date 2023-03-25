DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id        BIGINT(20) NOT NULL COMMENT '主键ID',
    name      VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age       INT(11) NULL DEFAULT NULL COMMENT '年龄',
    is_enable INT(11) NULL DEFAULT NULL COMMENT '是否启用:1-启用 2-未启用',
    source    varchar(16) NULL DEFAULT NULL COMMENT '字典表的 user_source',
    gender    INT(11) NULL DEFAULT NULL COMMENT '性别:1-男 2-女',
    head_url  VARCHAR(50) NULL DEFAULT NULL COMMENT '头像',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dict;

CREATE TABLE dict
(
    id       BIGINT(20) NOT NULL COMMENT '主键ID',
    label    VARCHAR(30) NULL DEFAULT NULL COMMENT '字典文本',
    value    VARCHAR(30) NULL DEFAULT NULL COMMENT '字典值',
    dict_key varchar(16) NULL DEFAULT NULL COMMENT '字典key',
    PRIMARY KEY (id)
);