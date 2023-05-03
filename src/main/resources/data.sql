INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name, website, phone)
VALUES ('Karaoke', 'karaoke.ru', '+7(999)-999-99-99'),
       ('Restaurant', 'restaurant.ru', '+7(999)-111-11-11');

INSERT INTO DISH (name, price, dish_date, restaurant_id)
VALUES ('Garlic Bread', 3.50, '2023-01-10', 1),
       ('Soup of the day', 4.99, '2023-01-10', 1),
       ('Olives', 3.99, '2023-01-10', 1),
       ('Prawn salad', 4.99, '2023-01-10', 2),
       ('Mozzarella salad>', 3.9, '2023-01-10', 2),
       ('Roast chicken salad', 3.4, '2023-01-10', 2),
       ('Chicken pizza', 4.55, '2023-01-11', 1),
       ('Margherita pizza', 5.0, '2023-01-11', 1),
       ('Meat pizza', 4.47, '2023-01-11', 1),
       ('Ice cream', 1.99, '2023-01-11', 2),
       ('Banana cake', 2.39, '2023-01-11', 2),
       ('Fruit cake', 2.29, '2023-01-11', 2);

INSERT INTO VOTE (restaurant_id, user_id, date_vote)
VALUES (1, 1, '2023-01-10'),
       (2, 2, '2023-01-10'),
       (1, 1, '2023-01-11'),
       (2, 2, '2023-01-11');

