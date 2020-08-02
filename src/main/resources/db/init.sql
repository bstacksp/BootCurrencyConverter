DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login varchar(15),
                       password varchar(24)
);

DROP TABLE IF EXISTS currencies;
CREATE TABLE currencies (
                            id SERIAL PRIMARY KEY,
                            name varchar(50),
                            char_code varchar(3),
                            nominal int,
                            value real
);

DROP TABLE IF EXISTS history;
CREATE TABLE history(
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    from_currency_id BIGINT REFERENCES currencies(id),
    to_currency_id BIGINT REFERENCES currencies(id),
    value real,
    date TIMESTAMP
)