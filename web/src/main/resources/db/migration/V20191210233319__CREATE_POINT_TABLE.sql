create table point_transactions
(
    transaction_id varchar(128) not null comment 'ポイントの取引ID',
    constraint point_transactions_transaction_id_uindex unique (transaction_id)
) comment 'ポイントの取引ID';
alter table point_transactions add primary key (transaction_id);

create table if not exists produced_points
(
    produced_transaction_id varchar(128) not null comment '取引ID',
    tenant_id varchar(128) not null comment 'テナント',
    currency_type varchar(128) not null comment '通貨タイプ',
    account_id int not null comment 'アカウントID',
    produced_amount int not null comment '増加ポイント量',
    point_unit decimal(20, 10) not null comment '1ポイントの単価',
    system_in datetime not null,
    system_out datetime not null,
    user_in datetime not null,
    user_out datetime not null,
    constraint produce_points_accounts_account_id_fk foreign key (account_id) references accounts (account_id),
    constraint produce_points_point_transactions_transaction_id_fk foreign key (produced_transaction_id) references point_transactions (transaction_id),
    constraint produce_points_tenants_tenant_id_fk foreign key (tenant_id) references tenants (tenant_id)
) comment 'ポイント増加テーブル';

create table consumed_points
(
    consume_transaction_id varchar(128) not null comment '取引ID',
    tenant_id varchar(128) not null comment 'テナント',
    currency_type varchar(128) not null comment '通貨タイプ',
    usage_category varchar(128) not null comment '消費区分',
    account_id int not null comment 'アカウントID',
    consumed_amount int not null comment '消費ポイント量',
    system_in datetime not null,
    system_out datetime not null,
    user_in datetime not null,
    user_out datetime not null,
    constraint consumed_points_accounts_account_id_fk foreign key (account_id) references accounts (account_id),
    constraint consumed_points_point_transactions_transaction_id_fk foreign key (consume_transaction_id) references point_transactions (transaction_id),
    constraint consumed_points_tenants_tenant_id_fk foreign key (tenant_id) references tenants (tenant_id)
) comment 'ポイント消費テーブル';

create table used_points
(
    produced_transaction_id varchar(128) not null comment '増加トランザクションID',
    consumed_transaction_id varchar(128) not null comment '消費トランザクションID',
    tenant_id varchar(128) not null comment 'テナント',
    currency_type varchar(128) not null comment '通貨タイプ',
    account_id int not null comment 'アカウントID',
    amount int not null comment '利用額',
    system_in datetime not null,
    system_out datetime not null,
    user_in datetime not null,
    user_out datetime not null,
    constraint used_points_accounts_account_id_fk foreign key (account_id) references accounts (account_id),
    constraint used_points_consumed_points_transaction_id_fk foreign key (consumed_transaction_id) references consumed_points (consume_transaction_id),
    constraint used_points_produced_points_transaction_id_fk foreign key (produced_transaction_id) references produced_points (produced_transaction_id),
    constraint used_points_tenants_tenant_id_fk foreign key (tenant_id) references tenants (tenant_id)
) comment 'ポイント利用テーブル';