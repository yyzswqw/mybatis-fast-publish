create table SQL_FRAGMENT
(
    ID NUMBER (19) not null
        constraint SQL_FRAGMENT_PK
        primary key,
    NAME VARCHAR2 (255),
    CODE VARCHAR2 (255) not null
        constraint CODE_UNIQUE_INDEX
        unique,
    SQL_DESC VARCHAR2 (255),
    FRAGMENT VARCHAR2 (4000) not null,
    PARAM_CONSTRAINT VARCHAR2 (4000)
);

comment on table SQL_FRAGMENT is '动态sql片段表';

comment on column SQL_FRAGMENT.ID is '主键';

comment on column SQL_FRAGMENT.NAME is '名称';

comment on column SQL_FRAGMENT.CODE is '唯一key';

comment on column SQL_FRAGMENT.SQL_DESC is '描述';

comment on column SQL_FRAGMENT.FRAGMENT is '动态sql片段';

comment on column SQL_FRAGMENT.PARAM_CONSTRAINT is '参数约束，json串';

