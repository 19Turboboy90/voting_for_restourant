DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- CREATE TABLE restaurant
-- (
--     id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--     name    VARCHAR NOT NULL,
--     website TEXT    NOT NULL,
--     phone   TEXT    NOT NULL,
--     CONSTRAINT restaurant_unique_website UNIQUE (website, name)
-- );
--
-- CREATE TABLE dish
-- (
--     id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--     name          VARCHAR(80) NOT NULL,
--     price         INTEGER     NOT NULL,
--     dish_date     TIMESTAMP   NOT NULL,
--     restaurant_id INTEGER     NOT NULL,
--     CONSTRAINT dish_unique_restaurant_dish_date UNIQUE (restaurant_id, dish_date)
-- );