create table users
(
    id                  BIGSERIAL           NOT NULL,
    username            VARCHAR(128) unique not null,
    password            VARCHAR(128)        not null
    CONSTRAINT users_pk primary key (id)
);

CREATE TABLE category_budgets
(
    id      BIGSERIAL    NOT NULL,
    name    VARCHAR(128) NOT NULL,
    user_id bigint REFERENCES users (id) on delete cascade,
    CONSTRAINT roles_pk PRIMARY KEY (id)
);

create table financial_operations
(
    id                  BIGSERIAL           NOT NULL,
    user_id     bigint              not null references users (id),
    title       VARCHAR(64)         not null,
    object_type varchar(32) not null,
    required_scope varchar(32) not null,
    info        VARCHAR(255),
    CONSTRAINT info_cards_pk primary key (unique_code)
);