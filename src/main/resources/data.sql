INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name)
VALUES ('Restaurant 1'),
       ('Restaurant 2'),
       ('Restaurant 3'),
       ('Restaurant 3');

INSERT INTO menu (restaurant_id)
VALUES (1),
       (2),
       (3),
       (4);

INSERT INTO dish (name, price, calories, menu_id)
VALUES ('Dish 1', 10.0, 200, 1),
       ('Dish 2', 15.0, 300, 1),
       ('Dish 3', 12.5, 250, 1),
       ('Dish 4', 11.5, 250, 2),
       ('Dish 5', 2.5, 250, 2),
       ('Dish 6', 1.5, 250, 2),
       ('Dish 7', 120.5, 250, 3),
       ('Dish 8', 12.5, 250, 3),
       ('Dish 9', 12.6, 250, 3),
       ('Dish 10', 9.5, 250, 4),
       ('Dish 11', 2.5, 250, 4),
       ('Dish 12', 12.0, 250, 4);

INSERT INTO vote (user_id, restaurant_id, vote_date)
VALUES (1, 1, '2023-05-16'),
       (2, 2, '2023-05-16'),
       (1, 3, '2023-05-16'),
       (2, 1, '2023-05-17'),
       (1, 2, '2023-05-17'),
       (2, 3, '2023-05-17'),
       (1, 1, '2023-05-18'),
       (2, 2, '2023-05-18'),
       (1, 3, '2023-05-18');