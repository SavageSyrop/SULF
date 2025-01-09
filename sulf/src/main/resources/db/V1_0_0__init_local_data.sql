create table users
(
    id                  BIGSERIAL           NOT NULL,
    username            VARCHAR(128) unique not null,
    password            VARCHAR(128)        not null,
    CONSTRAINT users_pk primary key (id)
);

CREATE TABLE category_budgets
(
    id      BIGSERIAL    NOT NULL,
    budget_size  real NOT NULL,
    category_name VARCHAR(128) not null,
    user_id bigint REFERENCES users (id) on delete cascade,
    CONSTRAINT category_budgets_pk PRIMARY KEY (id)
);

create table financial_operations
(
    id                  BIGSERIAL           NOT NULL,
    user_id     bigint              not null references users (id),
    category_name       VARCHAR(64)         not null,
    operation_type       VARCHAR(64)         not null,
    price  real NOT NULL,
    CONSTRAINT financial_operations_pk primary key (id)
);