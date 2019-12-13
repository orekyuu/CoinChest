create table accounts
(
    account_id int auto_increment primary key,
    display_name varchar(256) not null comment '表示名'
) comment 'アカウント';

create table tenants
(
    tenant_id varchar(128) not null comment 'テナントID',
    tenant_name varchar(128) not null comment 'テナント表示名',
    api_key varchar(128) not null comment 'API キー',
    constraint tenants_tenant_id_uindex unique (tenant_id)
) comment 'テナント';
alter table tenants add primary key (tenant_id);
