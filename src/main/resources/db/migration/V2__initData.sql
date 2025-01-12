
-- Create table for ingredients
CREATE TABLE ingredient (
                            id   UUID NOT NULL PRIMARY KEY,
                            name VARCHAR(255)
);

ALTER TABLE ingredient
    OWNER TO postgres;

-- Create table for subscriptions
CREATE TABLE subscription (
                              id                         UUID                  NOT NULL PRIMARY KEY,
                              is_deleted                 BOOLEAN DEFAULT FALSE NOT NULL,
                              description                TEXT,
                              minimum_for_free_transport DOUBLE PRECISION,
                              name                       TEXT                  NOT NULL
                                  CONSTRAINT subscription_name_check
                                      CHECK (name = ANY (ARRAY ['BASIC_PLAN', 'PREMIUM_PLAN', 'GOLD_PLAN'])),
                              price                      DOUBLE PRECISION,
                              transport_price            DOUBLE PRECISION,
                              type_subscription          TEXT
                                  CONSTRAINT subscription_type_subscription_check
                                      CHECK (type_subscription = ANY (ARRAY ['FREE', 'MONTHLY', 'ANNUAL']))
);

ALTER TABLE subscription
    OWNER TO postgres;

-- Create table for users
CREATE TABLE users (
                       id       UUID         NOT NULL PRIMARY KEY,
                       address  VARCHAR(255) NOT NULL,
                       email    VARCHAR(255) NOT NULL
                           CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE,
                       name     VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone_no VARCHAR(255),
                       role     SMALLINT     NOT NULL
                           CONSTRAINT users_role_check CHECK ((role >= 0) AND (role <= 2))
);

ALTER TABLE users
    OWNER TO postgres;

-- Create table for menus
CREATE TABLE menu (
                      id            UUID                  NOT NULL PRIMARY KEY,
                      is_deleted    BOOLEAN DEFAULT FALSE NOT NULL,
                      is_available  BOOLEAN,
                      name          VARCHAR(255),
                      photo         VARCHAR(255),
                      price         REAL,
                      restaurant_id UUID                  NOT NULL
                          CONSTRAINT fkdtdsdhgveq0kr46eth9t766ln REFERENCES users
);

ALTER TABLE menu
    OWNER TO postgres;

-- Create table for menu ingredients
CREATE TABLE menu_ingredient (
                                 menu_id       UUID NOT NULL
                                     CONSTRAINT fk992mtdi2vvwqhfdtci4w0p57v REFERENCES menu,
                                 ingredient_id UUID NOT NULL
                                     CONSTRAINT fkf7j488xc47ukcn9sb9532n9d1 REFERENCES ingredient
);

ALTER TABLE menu_ingredient
    OWNER TO postgres;

-- Create table for orders
CREATE TABLE orders (
                        id              UUID                  NOT NULL PRIMARY KEY,
                        is_deleted      BOOLEAN DEFAULT FALSE NOT NULL,
                        mentions        VARCHAR(255),
                        order_price     DOUBLE PRECISION,
                        status          SMALLINT
                            CONSTRAINT orders_status_check CHECK ((status >= 0) AND (status <= 3)),
                        transport_price DOUBLE PRECISION,
                        client          UUID
                            CONSTRAINT fkpg29nfndcoxxrv63n9pxpm7qk REFERENCES users,
                        delivery_man_id UUID
                            CONSTRAINT fkd2e9uy4xvifeaaljlo2876dj7 REFERENCES users
);

ALTER TABLE orders
    OWNER TO postgres;

-- Create table for order clients
CREATE TABLE order_client (
                              id              UUID                  NOT NULL PRIMARY KEY,
                              is_deleted      BOOLEAN DEFAULT FALSE NOT NULL,
                              poduct_quantity INTEGER,
                              order_id        UUID
                                  CONSTRAINT fkl2tc5hpjeo1jbx4pqpoobc87o REFERENCES orders,
                              product_id      UUID
                                  CONSTRAINT fkk9u9gwon32avj8u9ivtftqn1o REFERENCES menu
);

ALTER TABLE order_client
    OWNER TO postgres;

-- Create table for user subscriptions
CREATE TABLE user_subscription (
                                   id              UUID NOT NULL PRIMARY KEY,
                                   end_date        DATE,
                                   start_date      DATE,
                                   subscription_id UUID NOT NULL
                                       CONSTRAINT fkhaqil7thjcrmntsjy8er8akjy REFERENCES subscription,
                                   user_id         UUID NOT NULL
                                       CONSTRAINT fkn4xrjha3c12vp530y3j0ogef2 REFERENCES users
);

ALTER TABLE user_subscription
    OWNER TO postgres;


-- Populate the ingredient table
INSERT INTO ingredient (id, name)
VALUES
    (gen_random_uuid(), 'Tomato'),
    (gen_random_uuid(), 'Cheese'),
    (gen_random_uuid(), 'Basil'),
    (gen_random_uuid(), 'Chicken'),
    (gen_random_uuid(), 'Lettuce'),
    (gen_random_uuid(), 'Onion'),
    (gen_random_uuid(), 'Beef Patty'),
    (gen_random_uuid(), 'Pickles'),
    (gen_random_uuid(), 'Mayo'),
    (gen_random_uuid(), 'Bacon'),
    (gen_random_uuid(), 'Mushroom'),
    (gen_random_uuid(), 'Pineapple'),
    (gen_random_uuid(), 'Olives'),
    (gen_random_uuid(), 'Peppers');


-- Populate the users table (with role = 2)
INSERT INTO users (id, address, email, name, password, phone_no, role)
VALUES
    (gen_random_uuid(), '123 Main St, New York', 'chef1@example.com', 'Taco Loco', 'password123', '555-123-4567', 2),
    (gen_random_uuid(), '456 Elm St, Chicago', 'chef2@example.com', 'Bucovina', 'securepass456', '555-765-4321', 2),
    (gen_random_uuid(), '789 Oak St, San Francisco', 'chef3@example.com', 'Mirifique', 'mypassword789', '555-987-6543', 2);

-- Populate the menu table (5 menus for each user)
-- Menus for Chef John
INSERT INTO menu (id, is_deleted, is_available, name, photo, price, restaurant_id)
VALUES
    (gen_random_uuid(), FALSE, TRUE, 'Margherita Pizza', 'margherita.jpg', 12.99, (SELECT id FROM users WHERE email = 'chef1@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Pepperoni Pizza', 'pepperoni.jpg', 13.99, (SELECT id FROM users WHERE email = 'chef1@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Hawaiian Pizza', 'hawaiian.jpg', 14.99, (SELECT id FROM users WHERE email = 'chef1@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Mushroom Pizza', 'mushroom.jpg', 12.49, (SELECT id FROM users WHERE email = 'chef1@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Vegetarian Pizza', 'vegetarian.jpg', 11.99, (SELECT id FROM users WHERE email = 'chef1@example.com'));

-- Menus for Chef Jane
INSERT INTO menu (id, is_deleted, is_available, name, photo, price, restaurant_id)
VALUES
    (gen_random_uuid(), FALSE, TRUE, 'Chicken Caesar Salad', 'chicken_salad.jpg', 10.99, (SELECT id FROM users WHERE email = 'chef2@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Grilled Chicken Salad', 'grilled_chicken_salad.jpg', 12.49, (SELECT id FROM users WHERE email = 'chef2@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Mediterranean Salad', 'mediterranean_salad.jpg', 11.99, (SELECT id FROM users WHERE email = 'chef2@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Spinach Salad', 'spinach_salad.jpg', 9.99, (SELECT id FROM users WHERE email = 'chef2@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Avocado Salad', 'avocado_salad.jpg', 13.99, (SELECT id FROM users WHERE email = 'chef2@example.com'));

-- Menus for Chef Mike
INSERT INTO menu (id, is_deleted, is_available, name, photo, price, restaurant_id)
VALUES
    (gen_random_uuid(), FALSE, TRUE, 'Classic Cheeseburger', 'cheeseburger.jpg', 8.99, (SELECT id FROM users WHERE email = 'chef3@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Bacon Cheeseburger', 'bacon_cheeseburger.jpg', 10.99, (SELECT id FROM users WHERE email = 'chef3@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Double Cheeseburger', 'double_cheeseburger.jpg', 12.99, (SELECT id FROM users WHERE email = 'chef3@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Mushroom Swiss Burger', 'mushroom_swiss_burger.jpg', 11.99, (SELECT id FROM users WHERE email = 'chef3@example.com')),
    (gen_random_uuid(), FALSE, TRUE, 'Veggie Burger', 'veggie_burger.jpg', 9.49, (SELECT id FROM users WHERE email = 'chef3@example.com'));

-- Populate the menu_ingredient table
-- Ingredients for all menus
-- Margherita Pizza
INSERT INTO menu_ingredient (menu_id, ingredient_id)
VALUES
    ((SELECT id FROM menu WHERE name = 'Margherita Pizza'), (SELECT id FROM ingredient WHERE name = 'Tomato')),
    ((SELECT id FROM menu WHERE name = 'Margherita Pizza'), (SELECT id FROM ingredient WHERE name = 'Cheese')),
    ((SELECT id FROM menu WHERE name = 'Margherita Pizza'), (SELECT id FROM ingredient WHERE name = 'Basil'));

-- Pepperoni Pizza
INSERT INTO menu_ingredient (menu_id, ingredient_id)
VALUES
    ((SELECT id FROM menu WHERE name = 'Pepperoni Pizza'), (SELECT id FROM ingredient WHERE name = 'Tomato')),
    ((SELECT id FROM menu WHERE name = 'Pepperoni Pizza'), (SELECT id FROM ingredient WHERE name = 'Cheese')),
    ((SELECT id FROM menu WHERE name = 'Pepperoni Pizza'), (SELECT id FROM ingredient WHERE name = 'Peppers'));

-- Repeat for other menus as needed


-- Populează tabela subscription
INSERT INTO subscription (id, name, description, price, type_subscription, transport_price, minimum_for_free_transport) VALUES
                                                                                                                            ('00000000-0000-0000-0000-000000000301', 'BASIC_PLAN', 'Pentru orice comanda, transportul este 20 de lei.', 0, 'FREE', 20, 0),
                                                                                                                            ('00000000-0000-0000-0000-000000000302', 'PREMIUM_PLAN', 'Pentru orice comanda de peste 50 de lei, transportul este gratuit. In caz contrar, se percepe o taxa de 25 de lei pentru transport.', 150, 'MONTHLY', 25, 50),
                                                                                                                            ('00000000-0000-0000-0000-000000000303', 'GOLD_PLAN', 'Pentru orice comanda de peste 70 de lei, transportul este gratuit. In caz contrar, se percepe o taxa de 30 de lei pentru transport.', 500, 'ANNUAL', 30, 70);
