create table if not exists refund_histories
(
    refund_id varchar(128) not null,
    refunded_account int not null comment '払い戻し先のアカウント',
    refunded_at datetime not null comment '返金日時',
    constraint refund_histories_refund_id_uindex unique (refund_id),
    constraint refund_histories_accounts_account_id_fk foreign key (refunded_account) references accounts (account_id)
) comment '払い戻し履歴';
alter table refund_histories add primary key (refund_id);

create table if not exists refunded_points
(
    transaction_id varchar(128) not null comment '払い戻し対象の取引ID',
    tenant_id varchar(128) not null comment 'テナントID',
    currency_type varchar(128) not null comment '通貨タイプ',
    account_id int not null comment 'アカウント',
    refunded_amount int not null comment '払い戻したポイント量',
    system_in datetime not null,
    system__out datetime not null,
    refund_id varchar(128) not null comment '返金ID',
    constraint refunded_points_accounts_account_id_fk foreign key (account_id) references accounts (account_id),
    constraint refunded_points_point_transactions_transaction_id_fk foreign key (transaction_id) references point_transactions (transaction_id),
    constraint refunded_points_refund_histories_refund_id_fk foreign key (refund_id) references refund_histories (refund_id),
    constraint refunded_points_tenants_tenant_id_fk foreign key (tenant_id) references tenants (tenant_id)
) comment '払い戻しポイント';