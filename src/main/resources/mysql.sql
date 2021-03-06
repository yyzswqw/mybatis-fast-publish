drop table if exists sql_fragment ;
create table sql_fragment
(
    `id`             bigint        not null comment '主键',
    `name`           varchar(255)  null comment '名称',
    `code`           varchar(255)  not null comment '唯一key',
    `sql_desc`       varchar(255)  null comment '描述',
    `fragment`       varchar(5000) null comment '动态sql片段',
    param_constraint varchar(5000) null comment '参数约束，json串',
    primary key (`id`),
    constraint code_unique_index
        unique (code)
)
    comment '动态sql片段表';
