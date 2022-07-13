create table ecl_account_login_failed_info_
(
    id_          varchar(50)                          not null
        primary key,
    order_       bigint                               null,
    account_ref_ varchar(50)                          null,
    datetime_    datetime                             null,
    ip_          varchar(50)                          null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
);

create index ecl_user_login_failed_info_idx1
    on ecl_account_login_failed_info_ (account_ref_, datetime_);

create table ecl_account_login_log_
(
    id_          varchar(50) not null
        primary key,
    user_id_     varchar(50) null,
    order_       bigint      null,
    datetime_    datetime    null,
    ip_          varchar(50) null,
    method_      tinyint     null ,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create index ecl_account_login_log_idx1
    on ecl_account_login_log_ (order_, user_id_);

create index ecl_account_login_log_idx2
    on ecl_account_login_log_ (datetime_);

create table ecl_account_ref_
(
    ACCOUNT_ID_  varchar(50) not null
        primary key,
    USER_ID_     varchar(50) null,
    CREATE_DATE_ datetime    null,
    CREATE_USER_ varchar(50) null,
    MOD_DATE_    datetime    null,
    MOD_USER_    varchar(50) null
);

create index ecl_account_ref_idx1
    on ecl_account_ref_ (USER_ID_);

create table ecl_app_oauth2_
(
    ID_                     varchar(50)   not null
        primary key,
    SECRET_                 varchar(100)  null,
    IS_VALID_               tinyint(1)    null,
    REDIRECT_HOST_          varchar(1000) null ,
    CREATE_DATE_            datetime      null,
    CREATE_USER_            varchar(50)   null,
    MOD_DATE_               datetime      null,
    MOD_USER_               varchar(50)   null,
    redirect_host_4_mobile_ varchar(1000) null,
    encrypt_secret_         varchar(150)  null
);

create table ecl_app_pub_key_store_
(
    ID_              varchar(50)   not null
        primary key,
    IS_VALID_        tinyint(1)    null,
    IS_CHECK_EXPIRE_ tinyint(1)    null ,
    PUB_KEY_         varchar(2000) null,
    CREATE_DATE_     datetime      null,
    CREATE_USER_     varchar(50)   null,
    MOD_DATE_        datetime      null,
    MOD_USER_        varchar(50)   null
);

create table ecl_basic_kv_
(
    id_               varchar(50)                          not null
        primary key,
    group_            varchar(50)                          null,
    text_             varchar(2000)                        null,
    i18n_key_         varchar(50)                          null,
    enable_in_create_ tinyint(1)                           null,
    enable_in_update_ tinyint(1)                           null,
    enable_in_select_ tinyint(1)                           null,
    order_            int                                  null,
    create_user_      varchar(50)                          null,
    create_date_      datetime                             null,
    mod_user_         varchar(50)                          null,
    mod_date_         datetime ,
    short_name_       varchar(200)                         null,
    code_             varchar(50)                          null
);

create index ecl_basic_kv_idx1
    on ecl_basic_kv_ (group_);

create table ecl_basic_kv_category_
(
    id_          varchar(50)                            not null
        primary key,
    name_        varchar(50)                            null,
    i18n_key_    varchar(50)                            null,
    description_ varchar(2000)                          null,
    order_       int                                    null,
    create_user_ varchar(50)                            null,
    create_date_ datetime                               null,
    mod_user_    varchar(50)                            null,
    mod_date_    datetime   ,
    enabled_     tinyint(1) default 1                   null
);

create table ecl_basic_kv_group_
(
    id_          varchar(50)                            not null
        primary key,
    category_id_ varchar(50)                            null,
    name_        varchar(50)                            null,
    i18n_key_    varchar(50)                            null,
    description_ varchar(2000)                          null,
    order_       int                                    null,
    create_user_ varchar(50)                            null,
    create_date_ datetime                               null,
    mod_user_    varchar(50)                            null,
    mod_date_    datetime   ,
    enabled_     tinyint(1) default 1                   null
);

create index ecl_basic_kv_group_idx1
    on ecl_basic_kv_group_ (category_id_);

create table ecl_basic_token_store_
(
    id_          varchar(50)                          not null
        primary key,
    token_       varchar(2000)                        null,
    expire_at_   datetime                             null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
);

create table ecl_common_seq_ticket_
(
    id_               varchar(50)                          not null
        primary key,
    modular_          varchar(50)                          null,
    modular_inner_id_ varchar(50)                          null,
    seq_id_           varchar(50)                          null,
    timestamp_        bigint                               null,
    idx_              int                                  null,
    full_ticket_      varchar(50)                          null,
    create_user_      varchar(50)                          null,
    create_date_      datetime                             null,
    mod_user_         varchar(50)                          null,
    mod_date_         datetime
)
;

create index ecl_common_seq_ticket_idx1
    on ecl_common_seq_ticket_ (seq_id_);

create index ecl_common_seq_ticket_idx2
    on ecl_common_seq_ticket_ (modular_, modular_inner_id_);

create table ecl_common_sequence_
(
    id_                 varchar(50)                          not null
        primary key,
    status_             tinyint                              null,
    version_            varchar(50)                          null,
    name_               varchar(500)                         null,
    prefix_             varchar(100)                         null,
    formula_type_       tinyint                              null,
    formula_suffix_     varchar(100)                         null,
    start_idx_          int                                  null,
    length_             tinyint                              null,
    is_fix_length_show_ tinyint(1)                           null,
    suffix_             varchar(100)                         null,
    idx_reset_type_     tinyint                              null,
    idx_scope_          int                                  null ,
    idx_curr_           int                                  null,
    create_user_        varchar(50)                          null,
    create_date_        datetime                             null,
    mod_user_           varchar(50)                          null,
    mod_date_           datetime
)
;

create table ecl_component_member_
(
    id_               varchar(50)  not null,
    modular_def_      varchar(50)  not null,
    modular_inner_id_ varchar(50)  null,
    type_             tinyint      null,
    org_id_           varchar(100) null ,
    link_id_          varchar(50)  null ,
    role_             varchar(50)  null,
    express_          varchar(100) null ,
    create_user_      varchar(50)  null,
    create_date_      datetime     null,
    mod_user_         varchar(50)  null,
    mod_date_         datetime     null,
    primary key (id_, modular_def_)
)
;

create index ecl_component_member_idx1
    on ecl_component_member_ (modular_def_, express_, modular_inner_id_);

create index ecl_component_member_idx2
    on ecl_component_member_ (modular_inner_id_, modular_def_);

create index ecl_component_member_idx3
    on ecl_component_member_ (modular_def_, link_id_, modular_inner_id_);

create table ecl_db_mq_
(
    id_               varchar(50)   not null
        primary key,
    scope_            varchar(50)   not null,
    status_           tinyint       not null,
    last_action_time_ datetime      null,
    version_          varchar(50)   null,
    body_             text          null,
    create_user_      varchar(50)   null,
    create_date_      datetime      null,
    mod_user_         varchar(50)   null,
    mod_date_         datetime      null,
    comment_          varchar(5000) null,
    retry_count_      int default 0 null
);

create index ecl_db_mq_idx1
    on ecl_db_mq_ (scope_, status_, last_action_time_);

create table ecl_db_personal_delayed_mq_
(
    id_          varchar(50) not null
        primary key,
    status_      tinyint     null,
    type_        varchar(50) null,
    user_id_     varchar(50) null,
    msg_         text        null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null,
    send_type_   tinyint     null,
    datetime_    datetime    null
)
;

create index ecl_db_personal_delayed_mq_idx1
    on ecl_db_personal_delayed_mq_ (user_id_);

create table ecl_department_
(
    ID_                  varchar(50)                            not null
        primary key,
    PARENT_ID_           varchar(50)                            null,
    order_               bigint                                 null,
    CODE_                varchar(50)                            null,
    STATUS_              tinyint                                null,
    NAME_                varchar(200)                           null,
    I18N_KEY_            varchar(200)                           null,
    LEVEL_               tinyint                                null ,
    PY_IDX_              varchar(100)                           null,
    PY_FULL_             varchar(300)                           null,
    CONTACT_NAME_        varchar(50)                            null,
    TELEPHONE_           varchar(50)                            null,
    ADDRESS_             varchar(50)                            null,
    COMMENTS_            varchar(500)                           null,
    CREATE_USER_         varchar(50)                            null,
    CREATE_DATE_         datetime                               null,
    MOD_USER_            varchar(50)                            null,
    MOD_DATE_            datetime   ,
    hr_link_             varchar(50)                            null,
    is_branch_           tinyint(1)                             null,
    is_ignore_hr_sync_   tinyint(1) default 0                   not null,
    level_v2_            varchar(50)                            null,
    hr_link_retain_      varchar(50)                            null,
    is_hidden_in_dialog_ tinyint(1) default 0                   null,
    version_             varchar(50)                            null,
    category_            varchar(50)                            null,
    constraint ecl_department_idx1
        unique (CODE_)
)
;

create index ecl_department_idx2
    on ecl_department_ (PARENT_ID_);

create index ecl_department_idx3
    on ecl_department_ (hr_link_retain_);

create index ecl_department_idx4
    on ecl_department_ (ID_, version_);

create index ecl_department_idx5
    on ecl_department_ (hr_link_);

create index ecl_department_idx6
    on ecl_department_ (is_branch_);

create table ecl_department_extend_
(
    id_          varchar(70)       not null
        primary key,
    dept_id_     varchar(100)      null,
    key_         varchar(50)       null,
    val_         varchar(500)      null,
    create_user_ varchar(50)       null,
    create_date_ datetime          null,
    mod_user_    varchar(50)       null,
    mod_date_    datetime          null,
    is_hr_link_  tinyint default 0 null
);

create index ecl_department_extend_idx1
    on ecl_department_extend_ (dept_id_);

create table ecl_department_increment_
(
    id_          varchar(155) not null
        primary key,
    link_id_     varchar(50)  null,
    version_     varchar(50)  null,
    topic_       varchar(50)  null,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null
);

create index ecl_department_increment_idx1
    on ecl_department_increment_ (topic_, link_id_, version_);

create table ecl_dept_manager_
(
    id_          varchar(100)                         not null
        primary key,
    dept_id_     varchar(50)                          null,
    user_id_     varchar(50)                          null,
    order_       bigint                               null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
);

create index ecl_dept_manager_idx1
    on ecl_dept_manager_ (user_id_);

create index ecl_dept_manager_idx2
    on ecl_dept_manager_ (dept_id_);

create table ecl_dept_manager_link_
(
    id_             varchar(50)                          not null
        primary key,
    dept_id_        varchar(50)                          null,
    parent_dept_id_ varchar(50)                          null,
    create_user_    varchar(50)                          null,
    create_date_    datetime                             null,
    mod_user_       varchar(50)                          null,
    mod_date_       datetime
);

create index ecl_dept_manager_link_idx1
    on ecl_dept_manager_link_ (dept_id_);

create index ecl_dept_manager_link_idx2
    on ecl_dept_manager_link_ (parent_dept_id_);

create table ecl_dev_rest_doc_
(
    id_          varchar(100) not null
        primary key,
    group_       varchar(50)  null ,
    section_     varchar(50)  null,
    name_        varchar(50)  null,
    url_         varchar(100) null,
    method_      varchar(50)  null,
    order_       bigint       null,
    snippet_     mediumtext   null,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null
);

create index ecl_dev_rest_doc_idx1
    on ecl_dev_rest_doc_ (group_);

create table ecl_dev_rest_doc_group_
(
    group_       varchar(50) not null
        primary key,
    group_text_  varchar(50) null,
    type_        tinyint     null,
    order_       bigint      null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create table ecl_employee_scheduling_
(
    date_        date        not null
        primary key,
    type_        tinyint     null,
    comments_    varchar(50) null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create table ecl_external_app_info_
(
    ID_             varchar(50)          not null
        primary key,
    APP_NAME_       varchar(200)         null,
    CREATE_DATE_    datetime             null,
    CREATE_USER_    varchar(50)          null,
    MOD_DATE_       datetime             null,
    MOD_USER_       varchar(50)          null,
    code_           varchar(50)          null,
    branch_dept_    varchar(50)          null,
    is_portal_show_ tinyint(1) default 1 null
)
;

create index ecl_external_app_info_idx1
    on ecl_external_app_info_ (code_);

create table ecl_external_app_info_recycle_
(
    ID_          varchar(50)  not null
        primary key,
    APP_NAME_    varchar(200) null,
    CREATE_DATE_ datetime     null,
    CREATE_USER_ varchar(50)  null,
    MOD_DATE_    datetime     null,
    MOD_USER_    varchar(50)  null
)
;

create table ecl_facade_menu_
(
    id_          varchar(50)   not null
        primary key,
    parent_id_   varchar(50)   null,
    order_       int           null,
    type_        tinyint       null ,
    name_        varchar(50)   null,
    desc_        varchar(200)  null,
    i18n_key_    varchar(50)   null,
    icon_cls_    varchar(50)   null,
    HREF_        varchar(4000) null,
    param_name_  varchar(200)  null ,
    param_val_   varchar(200)  null ,
    component_   varchar(100)  null ,
    create_user_ varchar(50)   null,
    create_date_ datetime      null,
    mod_user_    varchar(50)   null,
    mod_date_    datetime      null
);

create index ecl_facade_menu_idx1
    on ecl_facade_menu_ (parent_id_);

create table ecl_facade_menu_link_
(
    id_          varchar(50) not null
        primary key,
    group_id_    varchar(50) null,
    menu_id_     varchar(50) null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create index ecl_facade_menu_link_idx1
    on ecl_facade_menu_link_ (menu_id_);

create index ecl_facade_menu_link_idx2
    on ecl_facade_menu_link_ (group_id_);

create table ecl_file_body_
(
    id_                            varchar(50)                          not null
        primary key,
    scheme_                        tinyint                              null ,
    type_                          varchar(50)                          null,
    size_                          bigint                               null,
    month_                         decimal(6)                           null,
    create_user_                   varchar(50)                          null,
    create_date_                   datetime                             null,
    mod_user_                      varchar(50)                          null,
    mod_date_                      datetime ,
    link_file_id_                  varchar(50)                          null,
    link_preview_id_               varchar(500)                         null,
    link_download_id_              varchar(50)                          null,
    web_office_file_id_            varchar(50)                          null,
    web_office_volume_id_          varchar(50)                          null,
    web_office_file_hash_          varchar(50)                          null,
    web_office_sync_               tinyint(1)                           null,
    web_office_sync_expected_time_ datetime                             null
);

create table ecl_file_header_
(
    id_               varchar(50)                          not null
        primary key,
    modular_          varchar(50)                          null ,
    modular_inner_id_ varchar(100)                         null,
    waiting_removed_  tinyint(1)                           null,
    body_id_          varchar(50)                          null,
    name_             varchar(100)                         null,
    create_user_      varchar(50)                          null,
    create_date_      datetime                             null,
    mod_user_         varchar(50)                          null,
    mod_date_         datetime
);

create index ecl_file_header_idx1
    on ecl_file_header_ (body_id_);

create index ecl_file_header_idx2
    on ecl_file_header_ (modular_inner_id_, modular_);

create table ecl_hr_dept_
(
    id_           varchar(50)  not null
        primary key,
    name_         varchar(300) null,
    parent_id_    varchar(50)  null,
    order_        bigint       null,
    status_       tinyint      null,
    create_user_  varchar(50)  null,
    create_date_  datetime     null,
    mod_user_     varchar(50)  null,
    mod_date_     datetime     null,
    code_         varchar(50)  null,
    ee_id_        varchar(50)  null,
    ref_code_     varchar(50)  null,
    level_v2_     varchar(50)  null,
    source_link_  varchar(50)  null,
    is_propagate_ tinyint      null,
    version_      varchar(50)  null
)
;

create index ecl_hr_dept_idx1
    on ecl_hr_dept_ (ee_id_);

create index ecl_hr_dept_idx2
    on ecl_hr_dept_ (parent_id_);

create table ecl_hr_dept_extend_
(
    id_          varchar(70)  not null
        primary key,
    dept_id_     varchar(100) null,
    key_         varchar(50)  null,
    val_         varchar(500) null,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null
);

create index ecl_hr_dept_extend_idx1
    on ecl_hr_dept_extend_ (dept_id_);

create table ecl_hr_ext_dept_
(
    id_          varchar(50)  not null
        primary key,
    code_        varchar(50)  null,
    name_        varchar(300) null,
    parent_id_   varchar(50)  null,
    order_       bigint       null,
    status_      tinyint      null,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null,
    ee_id_       varchar(50)  null,
    level_v2_    varchar(50)  null
)
;

create index ecl_hr_ext_dept_idx1
    on ecl_hr_ext_dept_ (ee_id_);

create table ecl_hr_ext_dept_extend_
(
    id_          varchar(70)  not null
        primary key,
    dept_id_     varchar(100) null,
    key_         varchar(50)  null,
    val_         varchar(500) null,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null
);

create index ecl_hr_ext_dept_extend_idx1
    on ecl_hr_ext_dept_extend_ (dept_id_);

create table ecl_hr_ext_role_def_
(
    code_        varchar(50) not null
        primary key,
    type_        tinyint     null,
    is_enabled_  tinyint     null,
    name_        varchar(50) null,
    order_       bigint      null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create table ecl_hr_ext_role_org_
(
    id_            varchar(50) not null
        primary key,
    user_id_       varchar(50) null,
    org_id_        varchar(50) null,
    role_code_     varchar(50) null,
    order_in_role_ bigint      null,
    create_user_   varchar(50) null,
    create_date_   datetime    null,
    mod_user_      varchar(50) null,
    mod_date_      datetime    null
);

create table ecl_hr_ext_role_sync_transaction_
(
    id_          varchar(50) not null
        primary key,
    status_      tinyint     null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create table ecl_hr_ext_sync_transaction_
(
    id_          varchar(50) not null
        primary key,
    status_      tinyint     null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
)
;

create table ecl_hr_ext_user_
(
    id_           varchar(50)  not null
        primary key,
    em_id_        varchar(50)  null,
    mi_           varchar(50)  null,
    mobile_phone_ varchar(50)  null,
    email_        varchar(50)  null,
    dept_         varchar(500) null ,
    order_        bigint       null,
    status_       tinyint      null,
    hr_account_   varchar(50)  null,
    create_date_  datetime     null,
    create_user_  varchar(50)  null,
    mod_date_     datetime     null,
    mod_user_     varchar(50)  null,
    ee_id_        varchar(50)  null,
    SSN_          varchar(50)  null,
    position_     varchar(50)  null
);

create index ecl_hr_ext_user_idx1
    on ecl_hr_ext_user_ (ee_id_);

create table ecl_hr_ext_user_dept_link_
(
    id_                varchar(100)     not null
        primary key,
    user_id_           varchar(50)      null,
    dept_id_           varchar(50)      null,
    order_             bigint           null,
    create_date_       datetime         null,
    create_user_       varchar(50)      null,
    mod_date_          datetime         null,
    mod_user_          varchar(50)      null,
    dept_select_order_ bigint default 0 null
);

create index ecl_hr_ext_user_dept_link_idx1
    on ecl_hr_ext_user_dept_link_ (user_id_);

create table ecl_hr_parser_exception_
(
    id_          varchar(60)   not null
        primary key,
    link_id_     varchar(60)   null,
    type_        tinyint       null,
    exception_   varchar(5000) null,
    create_user_ varchar(50)   null,
    create_date_ datetime      null,
    mod_user_    varchar(50)   null,
    mod_date_    datetime      null
);

create index ecl_hr_parser_exception_idx1
    on ecl_hr_parser_exception_ (link_id_);

create table ecl_hr_role_def_
(
    code_        varchar(50) not null
        primary key,
    status_      tinyint     null,
    type_        tinyint     null,
    is_enabled_  tinyint     null,
    name_        varchar(50) null,
    order_       bigint      null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create table ecl_hr_role_org_
(
    id_            varchar(50)       not null
        primary key,
    status_        tinyint           null,
    user_id_       varchar(50)       null,
    org_id_        varchar(50)       null,
    role_code_     varchar(50)       null,
    order_in_role_ bigint            null,
    is_propagate_  tinyint default 0 null,
    create_user_   varchar(50)       null,
    create_date_   datetime          null,
    mod_user_      varchar(50)       null,
    mod_date_      datetime          null
);

create table ecl_hr_user_
(
    id_           varchar(50) not null
        primary key,
    mi_           varchar(50) null,
    mobile_phone_ varchar(50) null,
    email_        varchar(50) null,
    order_        bigint      null,
    status_       tinyint     null,
    create_date_  datetime    null,
    create_user_  varchar(50) null,
    mod_date_     datetime    null,
    mod_user_     varchar(50) null,
    em_id_        varchar(50) null,
    hr_account_   varchar(50) null,
    ee_id_        varchar(50) null,
    position_     varchar(50) null,
    SSN_          varchar(50) null,
    ALIAS_        varchar(50) null,
    source_link_  varchar(50) null,
    is_propagate_ tinyint     null,
    version_      varchar(50) null
);

create index ecl_hr_user_idx1
    on ecl_hr_user_ (ee_id_);

create table ecl_hr_user_dept_link_
(
    id_                varchar(100)     not null
        primary key,
    user_id_           varchar(50)      null,
    dept_id_           varchar(50)      null,
    create_date_       datetime         null,
    create_user_       varchar(50)      null,
    mod_date_          datetime         null,
    mod_user_          varchar(50)      null,
    order_             bigint           null,
    dept_select_order_ bigint default 0 null
);

create index ecl_hr_user_dept_link_idx1
    on ecl_hr_user_dept_link_ (user_id_);

create index ecl_hr_user_dept_link_idx2
    on ecl_hr_user_dept_link_ (dept_id_, order_);

create table ecl_hr_user_extend_tag_
(
    id_          varchar(80)       not null
        primary key,
    user_id_     varchar(50)       null,
    category_    varchar(50)       null,
    value_       varchar(50)       null,
    order_       bigint            null,
    create_user_ varchar(50)       null,
    create_date_ datetime          null,
    mod_user_    varchar(50)       null,
    mod_date_    datetime          null,
    is_hr_link_  tinyint default 0 null
);

create index ecl_hr_user_extend_tag_idx1
    on ecl_hr_user_extend_tag_ (user_id_);

create table ecl_hr_user_rank_
(
    id_          varchar(50) not null
        primary key,
    user_id_     varchar(50) null,
    rank_        varchar(50) null,
    order_       bigint      null,
    create_date_ datetime    null,
    create_user_ varchar(50) null,
    mod_date_    datetime    null,
    mod_user_    varchar(50) null
);

create index ecl_hr_user_rank_idx1
    on ecl_hr_user_rank_ (user_id_);

create table ecl_i18n_
(
    id_          varchar(50)                          not null
        primary key,
    locale_      varchar(50)                          not null,
    key_         varchar(200)                         not null,
    text_        varchar(500)                         not null,
    group_       varchar(50)                          not null ,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime ,
    constraint ecl_i18n_idx1
        unique (locale_, key_)
)
;

create index ecl_i18n_idx2
    on ecl_i18n_ (mod_date_);

create index ecl_i18n_idx3
    on ecl_i18n_ (key_);

create table ecl_menu_
(
    ID_          varchar(50)   not null
        primary key,
    PARENT_ID_   varchar(50)   null,
    ORDER_       int           null,
    TYPE_        tinyint       null ,
    NAME_        varchar(50)   null,
    DESC_        varchar(200)  null,
    I18N_KEY_    varchar(50)   null,
    ICON_CLS_    varchar(50)   null,
    HREF_        varchar(4000) null,
    PARAM_NAME_  varchar(200)  null ,
    PARAM_VAL_   varchar(200)  null ,
    COMPONENT_   varchar(100)  null ,
    CREATE_USER_ varchar(50)   null,
    CREATE_DATE_ datetime      null,
    MOD_USER_    varchar(50)   null,
    MOD_DATE_    datetime      null
)
;

create index ecl_menu_idx1
    on ecl_menu_ (PARENT_ID_);

create table ecl_menu_link_
(
    id_          varchar(50) not null
        primary key,
    group_id_    varchar(50) null,
    menu_id_     varchar(50) null,
    create_user_ varchar(50) null,
    create_date_ datetime    null,
    mod_user_    varchar(50) null,
    mod_date_    datetime    null
);

create index ecl_menu_link_idx1
    on ecl_menu_link_ (menu_id_);

create index ecl_menu_link_idx2
    on ecl_menu_link_ (group_id_);

create table ecl_message_
(
    id_           varchar(50)                          not null
        primary key,
    title_        varchar(500)                         null,
    content_      varchar(2000)                        null,
    sender_id_    varchar(50)                          null,
    alert_time_   datetime                             null,
    redirect_url_ varchar(2000)                        null,
    create_user_  varchar(50)                          null,
    create_date_  datetime                             null,
    mod_user_     varchar(50)                          null,
    mod_date_     datetime
)
;

create index ecl_message_idx1
    on ecl_message_ (sender_id_);

create table ecl_modular_permission_
(
    id_          varchar(50)                          not null
        primary key,
    group_id_    varchar(50)                          null,
    modular_     varchar(50)                          null ,
    action_      varchar(50)                          null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
)
;

create index ecl_modular_permission_idx1
    on ecl_modular_permission_ (modular_, group_id_);

create index ecl_modular_permission_idx2
    on ecl_modular_permission_ (group_id_);

create table ecl_monitor_record_
(
    id_                   varchar(260)     not null
        primary key,
    node_ip_              varchar(30)      not null,
    date_                 date             not null,
    mapper_               varchar(1000)    not null,
    sql_                  varchar(6000)    null,
    execute_count_        bigint default 0 not null,
    execute_slow_count_   bigint default 0 not null,
    running_count_        int    default 0 not null,
    concurrent_max_       int    default 0 not null,
    histogram_0_10_       bigint default 0 not null,
    histogram_10_100_     bigint default 0 not null,
    histogram_100_1000_   bigint default 0 not null,
    histogram_1000_10000_ bigint default 0 not null,
    create_user_          varchar(50)      null,
    create_date_          datetime         null,
    mod_user_             varchar(50)      null,
    mod_date_             datetime         null,
    error_count_          bigint default 0 null
);

create index ecl_monitor_record_idx1
    on ecl_monitor_record_ (date_, execute_slow_count_);

create table ecl_online_document_
(
    id_          varchar(50)                          not null
        primary key,
    order_       bigint                               null,
    name_        varchar(200)                         null,
    is_valid_    tinyint(1)                           null,
    comment_     varchar(500)                         null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
);

create table ecl_org_role_
(
    id_            varchar(50)                          not null
        primary key,
    user_id_       varchar(50)                          null ,
    role_scope_    varchar(50)                          null ,
    role_          varchar(50)                          null ,
    create_user_   varchar(50)                          null,
    create_date_   datetime                             null,
    mod_user_      varchar(50)                          null,
    mod_date_      datetime ,
    order_in_role_ bigint                               null
)
;

create index ecl_org_role_idx1
    on ecl_org_role_ (user_id_);

create index ecl_org_role_idx2
    on ecl_org_role_ (role_, role_scope_);

create table ecl_organization_
(
    ID_                 varchar(100)                         not null
        primary key,
    RESOURCE_ID_        varchar(50)                          null ,
    RESOURCE_TYPE_      tinyint                              null,
    PARENT_RESOURCE_ID_ varchar(50)                          null ,
    CREATE_USER_        varchar(50)                          null,
    CREATE_DATE_        datetime                             null,
    MOD_USER_           varchar(50)                          null,
    MOD_DATE_           datetime ,
    constraint ecl_organization_idx1
        unique (RESOURCE_ID_, PARENT_RESOURCE_ID_)
)
;

create index ecl_organization_idx2
    on ecl_organization_ (PARENT_RESOURCE_ID_);

create table ecl_permission_group_
(
    id_          varchar(50)   not null
        primary key,
    code_        varchar(50)   null,
    is_valid_    tinyint(1)    null,
    name_        varchar(500)  null,
    comments_    varchar(2000) null,
    create_user_ varchar(50)   null,
    create_date_ datetime      null,
    mod_user_    varchar(50)   null,
    mod_date_    datetime      null,
    constraint ecl_permission_group_idx1
        unique (code_)
);

create table ecl_personal_user_group_
(
    id_            varchar(50)                          not null
        primary key,
    name_          varchar(50)                          null,
    order_         bigint                               null,
    owner_user_id_ varchar(50)                          null ,
    comments_      varchar(500)                         null,
    create_user_   varchar(50)                          null,
    create_date_   datetime                             null,
    mod_user_      varchar(50)                          null,
    mod_date_      datetime
)
;

create index ecl_personal_user_group_idx1
    on ecl_personal_user_group_ (owner_user_id_);

create table ecl_personal_user_group_member_
(
    id_          varchar(50)  not null
        primary key,
    group_id_    varchar(100) null,
    key_         varchar(50)  null ,
    type_        tinyint      null,
    org_id_      varchar(100) null ,
    link_id_     varchar(50)  null ,
    role_        varchar(50)  null,
    express_     varchar(100) null ,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null
)
;

create index ecl_personal_user_group_member_idx1
    on ecl_personal_user_group_member_ (express_, group_id_);

create index ecl_personal_user_group_member_idx2
    on ecl_personal_user_group_member_ (group_id_);

create index ecl_personal_user_group_member_idx3
    on ecl_personal_user_group_member_ (link_id_, group_id_);

create table ecl_role_def_
(
    CODE_           varchar(50)                             not null
        primary key,
    NAME_           varchar(50)                             null,
    I18N_KEY_       varchar(50)                             null,
    order_          bigint                                  null,
    CREATE_USER_    varchar(50)                             null,
    CREATE_DATE_    datetime                                null,
    MOD_USER_       varchar(50)                             null,
    MOD_DATE_       datetime    ,
    type_           tinyint                                 null,
    branch_dept_id_ varchar(50) default '-100'              null,
    is_hr_link_     tinyint     default 0                   null,
    is_enabled_     tinyint     default 1                   null
)
;

create index ecl_role_def_idx1
    on ecl_role_def_ (branch_dept_id_);

-- auto-generated definition
create table ecl_fast_opinion_
(
    id_          varchar(50)  not null
        primary key,
    valid_       tinyint(1)   null,
    user_id_     varchar(50)  null ,
    order_       int          null ,
    content_     varchar(500) null ,
    comments_    varchar(500) null ,
    create_date_ datetime     null,
    create_user_ varchar(50)  null,
    mod_date_    datetime     null,
    mod_user_    varchar(50)  null
)
;

create index ecl_fast_opinion_idx1
    on ecl_fast_opinion_ (user_id_);

-- auto-generated definition
create table ecl_kg_seal_detail_
(
    id_          varchar(50)                            not null
        primary key,
    name_        varchar(50)                            not null ,
    org_id_      varchar(50)                            null ,
    key_sn_      varchar(50)                            null ,
    valid_       tinyint(1) default 1                   null ,
    create_date_ datetime   ,
    create_user_ varchar(50)                            null,
    mod_date_    datetime   ,
    mod_user_    varchar(50)                            null
)
;

-- auto-generated definition
create table ecl_role_def_
(
    CODE_           varchar(50)                             not null
        primary key,
    NAME_           varchar(50)                             null,
    I18N_KEY_       varchar(50)                             null,
    order_          bigint                                  null,
    CREATE_USER_    varchar(50)                             null,
    CREATE_DATE_    datetime                                null,
    MOD_USER_       varchar(50)                             null,
    MOD_DATE_       datetime    ,
    type_           tinyint                                 null,
    branch_dept_id_ varchar(50) default '-100'              null,
    is_hr_link_     tinyint     default 0                   null,
    is_enabled_     tinyint     default 1                   null
)
;

create index ecl_role_def_idx1
    on ecl_role_def_ (branch_dept_id_);

create table ecl_seal_detail_
(
    id_               varchar(50)          not null
        primary key,
    name_             varchar(50)          not null ,
    org_id_           varchar(50)          null ,
    group_id_         varchar(50)          null ,
    manage_user_      varchar(50)          null ,
    img_base64_       text                 null ,
    thumbnail_base64_ text                 null ,
    seal_code_        varchar(50)          null ,
    esign_account_id_ varchar(50)          null ,
    esign_seal_id_    varchar(50)          null ,
    is_valid_         tinyint(1) default 1 null ,
    create_user_      varchar(50)          null,
    create_date_      datetime             null,
    mod_user_         varchar(50)          null,
    mod_date_         datetime             null
)
;

create table ecl_seal_group_
(
    id_          varchar(50)          not null
        primary key,
    name_        varchar(50)          not null ,
    org_id_      varchar(50)          not null ,
    order_       int        default 0 null ,
    is_valid_    tinyint(1) default 1 null ,
    create_user_ varchar(50)          null,
    create_date_ datetime             null,
    mod_user_    varchar(50)          null,
    mod_date_    datetime             null
)
;

create table ecl_service_component_statistic_
(
    id_                   varchar(260)     not null
        primary key,
    node_ip_              varchar(30)      not null,
    date_                 date             not null,
    method_               varchar(500)     not null,
    execute_count_        bigint default 0 not null,
    execute_slow_count_   bigint default 0 not null,
    error_count_          bigint default 0 not null,
    running_count_        int    default 0 not null,
    concurrent_max_       int    default 0 not null,
    histogram_0_10_       bigint default 0 not null,
    histogram_10_100_     bigint default 0 not null,
    histogram_100_1000_   bigint default 0 not null,
    histogram_1000_10000_ bigint default 0 not null,
    create_user_          varchar(50)      null,
    create_date_          datetime         null,
    mod_user_             varchar(50)      null,
    mod_date_             datetime         null
);

create index ecl_service_component_statistic_idx1
    on ecl_service_component_statistic_ (date_, execute_slow_count_);

create table ecl_tree_kv_
(
    id_               varchar(50)                          not null
        primary key,
    parent_id_        varchar(50)                          not null,
    order_            bigint                               null,
    text_             varchar(2000)                        null,
    i18n_key_         varchar(50)                          null,
    enable_in_create_ tinyint(1)                           null,
    enable_in_update_ tinyint(1)                           null,
    enable_in_select_ tinyint(1)                           null,
    create_user_      varchar(50)                          null,
    create_date_      datetime                             null,
    mod_user_         varchar(50)                          null,
    mod_date_         datetime ,
    short_name_       varchar(200)                         null,
    group_            varchar(50)                          null,
    code_             varchar(50)                          null,
    comments_         varchar(1000)                        null
);

create index ecl_tree_kv_idx1
    on ecl_tree_kv_ (parent_id_, order_);

create table ecl_tree_quick_link_
(
    id_          varchar(50)                          not null
        primary key,
    modular_     varchar(100)                         null ,
    node_id_     varchar(50)                          null ,
    parent_id_   varchar(50)                          null ,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
);

create index ecl_tree_quick_link_idx1
    on ecl_tree_quick_link_ (modular_, node_id_);

create index ecl_tree_quick_link_idx2
    on ecl_tree_quick_link_ (modular_, parent_id_);

create table ecl_user_
(
    ID_                  varchar(50)                            not null
        primary key,
    ALIAS_               varchar(50)                            null ,
    EM_ID_               varchar(50)                            null,
    MOBILE_PHONE_        varchar(50)                            null ,
    EMAIL_               varchar(100)                           null ,
    order_               bigint                                 null,
    MI_                  varchar(30)                            null,
    FIRST_NAME_          varchar(30)                            null,
    LAST_NAME_           varchar(30)                            null,
    PY_IDX_              varchar(30)                            null ,
    PY_FULL_             varchar(80)                            null ,
    STATUS_              tinyint                                null,
    SSN_                 varchar(50)                            null ,
    TITLE_ID_            int                                    null,
    SEX_                 tinyint                                null,
    WORK_ADDR_           varchar(50)                            null,
    HOME_ADDR_           varchar(50)                            null,
    FAX_NUM_             varchar(50)                            null,
    HIRE_DATE_           date                                   null,
    BIRTH_PLACE_         varchar(100)                           null,
    MARITAL_STATUS_      tinyint                                null,
    POLITICAL_STATUS_    tinyint                                null,
    HIGHEST_DEGREE_      tinyint                                null,
    SCHOOL_GRADUATED_    varchar(100)                           null,
    MAJOR_               varchar(100)                           null,
    PRO_DEGREE_          varchar(100)                           null,
    PRO_DEGREE_DATE_     date                                   null,
    COMMENTS_            varchar(500)                           null,
    CREATE_USER_         varchar(50)                            null,
    CREATE_DATE_         datetime                               null,
    MOD_USER_            varchar(50)                            null,
    MOD_DATE_            datetime   ,
    hr_link_             varchar(50)                            null,
    is_ignore_hr_sync_   tinyint(1) default 0                   not null,
    hr_account_          varchar(50)                            null,
    hr_link_retain_      varchar(50)                            null,
    is_hidden_in_dialog_ tinyint(1) default 0                   null,
    version_             varchar(50)                            null,
    position_            varchar(50)                            null,
    constraint ecl_user_idx1
        unique (EM_ID_)
)
;

create index ecl_user_idx2
    on ecl_user_ (PY_IDX_);

create index ecl_user_idx3
    on ecl_user_ (PY_FULL_);

create index ecl_user_idx4
    on ecl_user_ (hr_link_);

create index ecl_user_idx5
    on ecl_user_ (hr_link_retain_);

create index ecl_user_idx6
    on ecl_user_ (SSN_);

create index ecl_user_idx7
    on ecl_user_ (ID_, version_);

create index ecl_user_idx8
    on ecl_user_ (hr_account_);

create table ecl_user_account_
(
    USER_ID_                varchar(50)                  not null
        primary key,
    STATUS_                 tinyint                      null,
    PWD_                    varchar(200)                 null,
    LOGIN_LAST_             datetime                     null,
    ACCOUNT_EXPIRATION_     datetime                     null ,
    CREDENTIALS_EXPIRATION_ datetime                     null ,
    THEME_PACKAGE_          varchar(50) default 'common' null ,
    CREATE_USER_            varchar(50)                  null,
    CREATE_DATE_            datetime                     null,
    MOD_USER_               varchar(50)                  null,
    MOD_DATE_               datetime                     null,
    is_org_pwd_             tinyint(1)                   null
)
;

create table ecl_user_dept_order_
(
    id_                varchar(50)                          not null
        primary key,
    user_              varchar(50)                          null,
    dept_id_           varchar(50)                          null,
    order_             bigint                               null,
    create_user_       varchar(50)                          null,
    create_date_       datetime                             null,
    mod_user_          varchar(50)                          null,
    mod_date_          datetime ,
    dept_select_order_ bigint   default 0                   null
);

create index ecl_user_dept_order_idx1
    on ecl_user_dept_order_ (user_, order_);

create table ecl_user_extend_tag_
(
    id_          varchar(80)                          not null
        primary key,
    user_id_     varchar(50)                          null,
    category_    varchar(50)                          null,
    value_       varchar(50)                          null,
    order_       bigint                               null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime ,
    is_hr_link_  tinyint  default 0                   null
);

create index ecl_user_extend_tag_idx1
    on ecl_user_extend_tag_ (user_id_);

create table ecl_user_group_
(
    ID_          varchar(50)   not null
        primary key,
    CODE_        varchar(50)   null ,
    NAME_        varchar(50)   null,
    IS_VALID_    tinyint(1)    null,
    COMMENTS_    varchar(2000) null,
    CREATE_USER_ varchar(50)   null,
    CREATE_DATE_ datetime      null,
    MOD_USER_    varchar(50)   null,
    MOD_DATE_    datetime      null,
    constraint ecl_user_group_idx1
        unique (CODE_)
)
;

create table ecl_user_group_link_
(
    ID_          varchar(50) not null
        primary key,
    USER_ID_     varchar(50) null,
    GROUP_ID_    varchar(50) null,
    CREATE_USER_ varchar(50) null,
    CREATE_DATE_ datetime    null,
    MOD_USER_    varchar(50) null,
    MOD_DATE_    datetime    null
);

create index ecl_user_group_link_idx1
    on ecl_user_group_link_ (USER_ID_);

create index ecl_user_group_link_idx2
    on ecl_user_group_link_ (GROUP_ID_);

create table ecl_user_increment_
(
    id_          varchar(155) not null
        primary key,
    link_id_     varchar(50)  null,
    version_     varchar(50)  null,
    topic_       varchar(50)  null,
    create_user_ varchar(50)  null,
    create_date_ datetime     null,
    mod_user_    varchar(50)  null,
    mod_date_    datetime     null
);

create index ecl_user_increment_idx1
    on ecl_user_increment_ (link_id_, version_, topic_);

create table ecl_user_rank_
(
    id_          varchar(50)                          not null
        primary key,
    user_id_     varchar(50)                          null,
    rank_        varchar(50)                          null,
    order_       bigint                               null,
    create_user_ varchar(50)                          null,
    create_date_ datetime                             null,
    mod_user_    varchar(50)                          null,
    mod_date_    datetime
);

create index ecl_user_position_rank_idx1
    on ecl_user_rank_ (user_id_);

create table ecl_web_exception_detail_
(
    id_            varchar(50)                          not null
        primary key,
    app_id_        varchar(50)                          null,
    url_           varchar(500)                         null,
    error_msg_     varchar(4200)                        null,
    request_body_  mediumtext                           null,
    create_user_   varchar(50)                          null,
    create_date_   datetime                             null,
    mod_user_      varchar(50)                          null,
    mod_date_      datetime ,
    response_body_ mediumtext                           null,
    method_        varchar(500)                         null,
    server_ip_     varchar(30)                          null,
    exec_mills_    bigint                               null
);

create index ecl_web_exception_detail_idx1
    on ecl_web_exception_detail_ (url_, app_id_);

create index ecl_web_exception_detail_idx2
    on ecl_web_exception_detail_ (create_date_, app_id_);

create table ecl_web_service_statistic_
(
    id_                   varchar(260)     not null
        primary key,
    node_ip_              varchar(30)      not null,
    date_                 date             not null,
    method_               varchar(500)     not null,
    url_                  varchar(1000)    not null,
    execute_count_        bigint default 0 not null,
    execute_slow_count_   bigint default 0 not null,
    running_count_        int    default 0 not null,
    concurrent_max_       int    default 0 not null,
    histogram_0_10_       bigint default 0 not null,
    histogram_10_100_     bigint default 0 not null,
    histogram_100_1000_   bigint default 0 not null,
    histogram_1000_10000_ bigint default 0 not null,
    create_user_          varchar(50)      null,
    create_date_          datetime         null,
    mod_user_             varchar(50)      null,
    mod_date_             datetime         null,
    error_count_          bigint default 0 null
);

create index ecl_web_service_statistic_idx1
    on ecl_web_service_statistic_ (date_, execute_slow_count_);

create table ecl_msg_remind_freq_
(
    id_          varchar(50)                          not null
        primary key,
    valid_       tinyint(1)                           null,
    user_id_     varchar(50)                          not null,
    type_        varchar(50)                          null,
    create_date_ datetime,
    create_user_ varchar(50)                          null,
    mod_date_    datetime,
    mod_user_    varchar(50)                          null
);

create index ecl_msg_remind_freq_idx1
    on ecl_msg_remind_freq_ (user_id_);


/*
* 2022-3-16 个人签名
*/
create table ecl_personal_sign_
(
    id_                  varchar(50) not null,
    user_id_             varchar(50),
    file_id_             varchar(50),
    order_               bigint,
    create_date_         datetime,
    create_user_         varchar(50),
    mod_date_            datetime,
    mod_user_            varchar(50),
    primary key (id_)
);

create index ecl_personal_sign_idx1 on ecl_personal_sign_(user_id_);

alter table ecl_personal_sign_ add enabled_ tinyint default 1;



/*
* 2022-3-28 mxgraph关联模块
*/
create table ext_mxgraph_show_link_
(
    id_                  varchar(100) not null comment '主键id',
    model_               varchar(100) comment '模块标识',
    model_id_            varchar(100) comment '模块id',
    xml_                 mediumtext comment '保存的xml',
    width_               int comment '画布的宽',
    height_              int comment '画布的高',
    bg_                  varchar(100) comment '背景色',
    create_date_         datetime,
    create_user_         varchar(100),
    mod_date_            datetime,
    mod_user_            varchar(100),
    primary key (id_)
);
alter table ext_mxgraph_show_link_ comment 'xmGraph模块图形维护表';
create index ext_mxgraph_show_link_index1 on ext_mxgraph_show_link_
    (
     model_
        );
create index ext_mxgraph_show_link_index2 on ext_mxgraph_show_link_
    (
     model_id_
        );


/*
* 2022-4-18 角色成员感知
*/
alter table ecl_role_def_ add member_digest_ varchar(40);
create table ecl_role_member_notify_ (
                                         id_ varchar(50) primary key ,
                                         topic_ varchar(50) not null,
                                         role_def_ varchar(50) not null,
                                         member_digest_ varchar(40),
                                         create_date_ datetime,
                                         create_user_ varchar(50),
                                         mod_date_ datetime,
                                         mod_user_ varchar(50)
);

create index ecl_role_member_notify_idx1 on ecl_role_member_notify_(topic_,role_def_,member_digest_);

/*
* 2022-4-20 评论新增来源字段
*/
alter table ext_comment_ add client_ varchar(100) default 'e10';


/*
* 2022-4-20 普通用户请求明细
*/
create table ecl_web_request_detail_(
                                        id_ varchar(50) primary key ,
                                        user_id_ varchar(50),
                                        date_time_ datetime,
                                        server_ip_ varchar(20),
                                        url_ varchar(500),
                                        method_ varchar(500),
                                        error_msg_ varchar(4200),
                                        request_body_  mediumtext,
                                        response_body_ mediumtext,
                                        exec_mills_    bigint,
                                        create_user_   varchar(50),
                                        create_date_   datetime,
                                        mod_user_      varchar(50),
                                        mod_date_      datetime
);
create index ecl_web_request_detail_idx1 on ecl_web_request_detail_ (url_);
create index ecl_web_request_detail_idx2 on ecl_web_request_detail_ (date_time_);

/*
* 2022-4-21 普通请求记录
*/
create table ecl_web_request_detail_(
                                        id_ varchar(50) primary key ,
                                        user_id_ varchar(50),
                                        date_time_ datetime,
                                        server_ip_ varchar(20),
                                        url_ varchar(500),
                                        method_ varchar(500),
                                        error_msg_ varchar(4200),
                                        request_body_  mediumtext,
                                        response_body_ mediumtext,
                                        exec_mills_    bigint,
                                        create_user_   varchar(50),
                                        create_date_   datetime,
                                        mod_user_      varchar(50),
                                        mod_date_      datetime
);
create index ecl_web_request_detail_idx1 on ecl_web_request_detail_ (url_);
create index ecl_web_request_detail_idx2 on ecl_web_request_detail_ (date_time_);

/*
* 2022-4-21 个人签名，上传类型
*/
alter table ecl_personal_sign_ add upload_type_ varchar(20);

/*
* 2022-4-27 政务微信多应用表格
*/
create table ecl_gwx_multi_app_info_(
                                        id_ varchar(50) primary key ,
                                        name_ varchar(50),
                                        is_valid_ tinyint,
                                        type_ tinyint,
                                        link_app_id_ varchar(50),
                                        redirect_target_ varchar(200),
                                        agent_id_ varchar(50),
                                        secret_ varchar(100),
                                        comment_ varchar(200),
                                        create_user_   varchar(50),
                                        create_date_   datetime,
                                        mod_user_      varchar(50),
                                        mod_date_      datetime
);


/*
* 2022-5-12 基础数据添加权限管理
*/
create table ecl_basic_kv_category_auth_(
                                            id_ varchar(50) primary key ,
                                            auth_type_ tinyint ,
                                            kv_id_ varchar(50),
                                            type_ tinyint,
                                            org_id_ varchar(100),
                                            link_id_ varchar(50),
                                            role_ varchar(50),
                                            express_ varchar(100),
                                            create_user_ varchar(50) ,
                                            create_date_ datetime,
                                            mod_user_ varchar(50),
                                            mod_date_ datetime
);

create index ecl_basic_kv_category_auth_idx1 on ecl_basic_kv_category_auth_(kv_id_);
create index ecl_basic_kv_category_auth_idx2 on ecl_basic_kv_category_auth_(express_);

/*
* 2022-5-16 更改基础数据权限表表名
*/
alter table ecl_basic_kv_category_auth_ rename to ecl_plat_kv_auth_;



/*
* 2022-5-21 角色定义，权限管控
*/
create table ecl_role_plat_auth_(
                                    id_ varchar(50) primary key ,
                                    auth_type_ tinyint ,
                                    role_id_ varchar(50),
                                    type_ tinyint,
                                    org_id_ varchar(100),
                                    link_id_ varchar(50),
                                    role_ varchar(50),
                                    express_ varchar(100),
                                    create_user_ varchar(50) ,
                                    create_date_ datetime,
                                    mod_user_ varchar(50),
                                    mod_date_ datetime
);

create index ecl_role_plat_auth_idx1 on ecl_role_plat_auth_(role_id_);
create index ecl_role_plat_auth_idx2 on ecl_role_plat_auth_(express_);

/*
* 2022-5-27 清理角色成员变更通知辅助类，已改用mq方式
* 模块独占辅助
*/
drop table ecl_role_member_notify_;

create table ecl_modular_exclusive_(
                                       id_ varchar(70) primary key,
                                       modular_ varchar(50) ,
                                       modular_inner_id_ varchar(50),
                                       ticket_ varchar(50),
                                       last_lock_time_ datetime,
                                       create_user_ varchar(50) ,
                                       create_date_ datetime,
                                       mod_user_ varchar(50),
                                       mod_date_ datetime
);

/*
* 2022-5-30 应用添加同步时根节点控制
*/
create table ecl_app_org_sync_root_dept_(
                                            id_ varchar(50) primary key ,
                                            app_id_ varchar(50),
                                            dept_id_ varchar(50),
                                            create_user_ varchar(50) ,
                                            create_date_ datetime,
                                            mod_user_ varchar(50),
                                            mod_date_ datetime
);
create index ecl_app_org_sync_root_dept_idx1 on ecl_app_org_sync_root_dept_(app_id_);

/*
* 2022-6-1 独占编辑，添加当前占有人信息
*/
alter table ecl_modular_exclusive_ add owner_ varchar(50);

/*
* 2022-6-7 清理quartz表结构
*/
drop table qrtz_fired_triggers;
DROP TABLE QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE QRTZ_SCHEDULER_STATE;
DROP TABLE QRTZ_LOCKS;
drop table qrtz_simple_triggers;
drop table qrtz_cron_triggers;
drop table qrtz_simprop_triggers;
DROP TABLE QRTZ_BLOB_TRIGGERS;
drop table qrtz_triggers;
drop table qrtz_job_details;
drop table qrtz_calendars;


/*
* 2022-6-20
* 1. 角色定义添加职责字段
* 2. 人员添加性别字段
* 3. 为后续监控添加事务监控表
*/
alter table ecl_role_def_ add column responsibility_ varchar(2000);
alter table ecl_user_ add column gender_ varchar(50);
alter table ecl_hr_user_ add column gender_ varchar(50);
alter table ecl_hr_ext_user_ add column gender_ varchar(50);
create table ecl_db_monitor_transaction_(
                                            id_ varchar(50) primary key ,
                                            node_ip_ varchar(50),
                                            sibling_id_ varchar(50),
                                            date_time_ datetime,
                                            detail_ mediumtext,
                                            create_user_         varchar(50),
                                            create_date_         datetime,
                                            mod_user_            varchar(50),
                                            mod_date_            datetime
);

alter table ecl_db_monitor_transaction_ add column duration bigint;
alter table ecl_db_monitor_transaction_ add column start_date_time_ datetime;


/*
* 2022-6-23 补充表结构
*/
create table ecl_granted_view_dept_
(
    id_                  varchar(100) not null,
    dept_id_             varchar(50),
    user_id_             varchar(50),
    order_               bigint,
    create_user_         varchar(50),
    create_date_         datetime,
    mod_user_            varchar(50),
    mod_date_            datetime,
    primary key (id_)
);
ALTER TABLE ecl_granted_view_dept_ add INDEX ecl_granted_view_dept_idx1(user_id_);
ALTER TABLE ecl_granted_view_dept_ add INDEX ecl_granted_view_dept_idx2(dept_id_);