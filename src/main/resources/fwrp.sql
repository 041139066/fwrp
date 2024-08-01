DROP
DATABASE IF EXISTS fwrp;

CREATE
DATABASE fwrp;

USE
fwrp;

-- -----------------------------------------------------
-- Table Users
-- -----------------------------------------------------
CREATE TABLE Users
(
    id    INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(127) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NULL,
    type  ENUM('retailer', 'charitable', 'consumer') NOT NULL
);

-- -----------------------------------------------------
-- Table Subscriptions
-- -----------------------------------------------------
CREATE TABLE Subscriptions
(
    customer_id INT          NOT NULL PRIMARY KEY,
    location    VARCHAR(255) NOT NULL,
    method      ENUM('email', 'sms') NOT NULL,
    FOREIGN KEY (customer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table FoodInventory
-- -----------------------------------------------------
CREATE TABLE FoodInventory
(
    id            INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description   VARCHAR(255) NOT NULL,
    standard_price DOUBLE(6,2) NOT NULL,
    quantity      INT(5) NOT NULL,
    expirationDate TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    average_rating DOUBLE(2,1) NULL
);

ALTER TABLE FoodInventory ADD COLUMN is_surplus BOOLEAN DEFAULT FALSE;
ALTER TABLE FoodInventory ADD COLUMN isForDonation BOOLEAN DEFAULT FALSE;
ALTER TABLE FoodInventory ADD COLUMN isForSale BOOLEAN DEFAULT FALSE;

-- -----------------------------------------------------
-- Table FoodItems
-- -----------------------------------------------------
CREATE TABLE FoodItems
(
    id                INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    expiration_date   DATETIME NOT NULL,
    price DOUBLE(6,2) NULL,
    status            ENUM('stock', 'donation', 'sale', 'sold') NOT NULL DEFAULT 'stock',
    retailer_id       INT      NOT NULL,
    food_inventory_id INT      NOT NULL,
    FOREIGN KEY (retailer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (food_inventory_id)
        REFERENCES FoodInventory (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table ClaimedFood
-- -----------------------------------------------------
CREATE TABLE ClaimedFood
(
    food_item_id  INT       NOT NULL,
    charitable_id INT       NOT NULL,
    claim_date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (food_item_id, charitable_id),
    FOREIGN KEY (food_item_id)
        REFERENCES FoodItems (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (charitable_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);
-- -----------------------------------------------------
-- Table PurchasedFood
-- -----------------------------------------------------
CREATE TABLE PurchasedFood
(
    food_item_id  INT       NOT NULL,
    consumer_id   INT       NOT NULL,
    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (food_item_id, consumer_id),
    FOREIGN KEY (food_item_id)
        REFERENCES FoodItems (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (consumer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table FoodPreferences
-- -----------------------------------------------------
CREATE TABLE FoodPreferences
(
    consumer_id       INT NOT NULL,
    food_inventory_id INT NOT NULL,
    PRIMARY KEY (consumer_id, food_inventory_id),
    FOREIGN KEY (consumer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (food_inventory_id)
        REFERENCES FoodInventory (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table Feedbacks
-- -----------------------------------------------------
CREATE TABLE Feedbacks
(
    consumer_id       INT      NOT NULL,
    food_inventory_id INT      NOT NULL,
    rating            INT(2) NOT NULL,
    comment           TINYTEXT NOT NULL,
    PRIMARY KEY (consumer_id, food_inventory_id),
    FOREIGN KEY (consumer_id)
        REFERENCES Users (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (food_inventory_id)
        REFERENCES FoodInventory (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

INSERT INTO Users (name, email, phone, type)
VALUES ('John Doe', 'john.doe@example.com', '1234567890', 'retailer'),
       ('Jane Smith', 'jane.smith@example.com', '2345678901', 'charitable'),
       ('Emily Johnson', 'emily.johnson@example.com', '3456789012', 'consumer'),
       ('Michael Brown', 'michael.brown@example.com', '4567890123', 'consumer'),
       ('Alice Green', 'alice.green@example.com', '5678901234', 'retailer'),
       ('Bob White', 'bob.white@example.com', '6789012345', 'charitable'),
       ('Catherine Lee', 'catherine.lee@example.com', '7890123456', 'consumer'),
       ('David Wilson', 'david.wilson@example.com', '8901234567', 'consumer'),
       ('Eve Black', 'eve.black@example.com', '9012345678', 'retailer'),
       ('Frank Harris', 'frank.harris@example.com', '0123456789', 'charitable'),
       ('Grace Adams', 'grace.adams@example.com', '1236789012', 'consumer'),
       ('Henry Lewis', 'henry.lewis@example.com', '2347890123', 'retailer'),
       ('Ivy Martinez', 'ivy.martinez@example.com', '3458901234', 'charitable'),
       ('Jack Robinson', 'jack.robinson@example.com', '4569012345', 'consumer'),
       ('Karen Walker', 'karen.walker@example.com', '5670123456', 'retailer'),
       ('Leo Harris', 'leo.harris@example.com', '6781234567', 'charitable'),
       ('Mia Scott', 'mia.scott@example.com', '7892345678', 'consumer'),
       ('Nathan Young', 'nathan.young@example.com', '8903456789', 'retailer'),
       ('Olivia King', 'olivia.king@example.com', '9014567890', 'charitable'),
       ('Paul Allen', 'paul.allen@example.com', '0125678901', 'consumer'),
       ('Quincy Wright', 'quincy.wright@example.com', '1236789013', 'retailer'),
       ('Rachel Hall', 'rachel.hall@example.com', '2347890124', 'charitable'),
       ('Sam Turner', 'sam.turner@example.com', '3458901235', 'consumer'),
       ('Tina Evans', 'tina.evans@example.com', '4569012346', 'retailer');

INSERT INTO Subscriptions (customer_id, location, method)
VALUES (1, 'Chicago, IL', 'email'),
       (2, 'San Francisco, CA', 'sms'),
       (3, 'New York, NY', 'email'),
       (4, 'Los Angeles, CA', 'sms'),
       (5, 'Seattle, WA', 'email'),
       (6, 'Houston, TX', 'sms'),
       (7, 'Philadelphia, PA', 'email'),
       (8, 'Phoenix, AZ', 'sms'),
       (9, 'San Antonio, TX', 'email'),
       (10, 'San Diego, CA', 'sms'),
       (11, 'Dallas, TX', 'email'),
       (12, 'San Jose, CA', 'sms'),
       (13, 'Austin, TX', 'email'),
       (14, 'Jacksonville, FL', 'sms'),
       (15, 'San Francisco, CA', 'email'),
       (16, 'Indianapolis, IN', 'sms'),
       (17, 'Columbus, OH', 'email'),
       (18, 'Fort Worth, TX', 'sms'),
       (19, 'Charlotte, NC', 'email'),
       (20, 'Detroit, MI', 'sms'),
       (21, 'El Paso, TX', 'email'),
       (22, 'Memphis, TN', 'sms'),
       (23, 'Baltimore, MD', 'email'),
       (24, 'Boston, MA', 'sms');

INSERT INTO FoodInventory (description, standard_price, quantity, average_rating)
VALUES ('Apples', 1.50, 100, 4.5),
       ('Bread', 2.00, 50, 4.2),
       ('Milk', 1.20, 30, 4.8),
       ('Eggs', 3.00, 20, 4.0),
       ('Cheese', 4.00, 60, 4.7),
       ('Yogurt', 1.80, 40, 4.3),
       ('Chicken Breast', 5.00, 25, 4.6),
       ('Spinach', 2.50, 70, 4.4),
       ('Orange Juice', 3.00, 45, 4.5),
       ('Pasta', 1.75, 80, 4.2),
       ('Tomatoes', 2.00, 90, 4.6),
       ('Potatoes', 1.00, 150, 4.4),
       ('Carrots', 1.50, 120, 4.5),
       ('Lettuce', 1.80, 110, 4.3),
       ('Chicken Wings', 6.00, 35, 4.7),
       ('Beef Steak', 7.50, 25, 4.8),
       ('Salmon', 8.00, 20, 4.9),
       ('Pork Chops', 5.50, 30, 4.4),
       ('Olive Oil', 4.00, 50, 4.6),
       ('Rice', 2.50, 100, 4.5),
       ('Beans', 1.80, 70, 4.3),
       ('Cereal', 3.00, 60, 4.4),
       ('Juice', 2.50, 75, 4.3),
       ('Frozen Vegetables', 2.00, 40, 4.2);

INSERT INTO FoodItems (expiration_date, price, status, retailer_id, food_inventory_id)
VALUES ('2024-08-01 12:00:00', 1.50, 'stock', 1, 1),
       ('2024-07-30 12:00:00', 2.00, 'donation', 1, 2),
       ('2024-08-05 12:00:00', 1.20, 'sale', 1, 3),
       ('2024-08-10 12:00:00', 3.00, 'stock', 1, 4),
       ('2024-08-15 12:00:00', 4.00, 'stock', 1, 5),
       ('2024-07-29 12:00:00', 1.80, 'donation', 2, 6),
       ('2024-08-12 12:00:00', 5.00, 'sale', 2, 7),
       ('2024-08-20 12:00:00', 2.50, 'stock', 2, 8),
       ('2024-07-28 12:00:00', 3.00, 'sale', 3, 9),
       ('2024-08-25 12:00:00', 4.00, 'stock', 3, 10),
       ('2024-09-01 12:00:00', 1.75, 'stock', 3, 11),
       ('2024-09-10 12:00:00', 2.00, 'donation', 4, 12),
       ('2024-09-15 12:00:00', 1.50, 'sale', 4, 13),
       ('2024-08-30 12:00:00', 2.00, 'stock', 4, 14),
       ('2024-07-25 12:00:00', 3.00, 'sale', 5, 15),
       ('2024-08-05 12:00:00', 4.00, 'stock', 5, 16),
       ('2024-08-10 12:00:00', 5.00, 'sale', 5, 17),
       ('2024-09-01 12:00:00', 2.50, 'stock', 5, 18),
       ('2024-09-10 12:00:00', 1.80, 'donation', 6, 19),
       ('2024-09-15 12:00:00', 2.00, 'sale', 6, 20),
       ('2024-08-30 12:00:00', 1.75, 'stock', 6, 21),
       ('2024-08-20 12:00:00', 3.00, 'stock', 7, 22),
       ('2024-08-25 12:00:00', 4.00, 'donation', 7, 23),
       ('2024-09-05 12:00:00', 5.00, 'sale', 7, 24);

INSERT INTO ClaimedFood (food_item_id, charitable_id, claim_date)
VALUES (1, 2, '2024-07-28 12:00:00'),
       (2, 3, '2024-07-29 12:00:00'),
       (3, 4, '2024-07-30 12:00:00'),
       (4, 5, '2024-07-31 12:00:00'),
       (5, 6, '2024-08-01 12:00:00'),
       (6, 7, '2024-08-02 12:00:00'),
       (7, 8, '2024-08-03 12:00:00'),
       (8, 9, '2024-08-04 12:00:00'),
       (9, 10, '2024-08-05 12:00:00'),
       (10, 11, '2024-08-06 12:00:00'),
       (11, 12, '2024-08-07 12:00:00'),
       (12, 13, '2024-08-08 12:00:00'),
       (13, 14, '2024-08-09 12:00:00'),
       (14, 15, '2024-08-10 12:00:00'),
       (15, 16, '2024-08-11 12:00:00'),
       (16, 17, '2024-08-12 12:00:00'),
       (17, 18, '2024-08-13 12:00:00'),
       (18, 19, '2024-08-14 12:00:00'),
       (19, 20, '2024-08-15 12:00:00'),
       (20, 21, '2024-08-16 12:00:00'),
       (21, 22, '2024-08-17 12:00:00'),
       (22, 23, '2024-08-18 12:00:00'),
       (23, 24, '2024-08-19 12:00:00'),
       (24, 1, '2024-08-20 12:00:00');

INSERT INTO PurchasedFood (food_item_id, consumer_id, purchase_date)
VALUES (1, 3, '2024-07-28 12:00:00'),
       (2, 4, '2024-07-29 12:00:00'),
       (3, 5, '2024-07-30 12:00:00'),
       (4, 6, '2024-07-31 12:00:00'),
       (5, 7, '2024-08-01 12:00:00'),
       (6, 8, '2024-08-02 12:00:00'),
       (7, 9, '2024-08-03 12:00:00'),
       (8, 10, '2024-08-04 12:00:00'),
       (9, 11, '2024-08-05 12:00:00'),
       (10, 12, '2024-08-06 12:00:00'),
       (11, 13, '2024-08-07 12:00:00'),
       (12, 14, '2024-08-08 12:00:00'),
       (13, 15, '2024-08-09 12:00:00'),
       (14, 16, '2024-08-10 12:00:00'),
       (15, 17, '2024-08-11 12:00:00'),
       (16, 18, '2024-08-12 12:00:00'),
       (17, 19, '2024-08-13 12:00:00'),
       (18, 20, '2024-08-14 12:00:00'),
       (19, 21, '2024-08-15 12:00:00'),
       (20, 22, '2024-08-16 12:00:00'),
       (21, 23, '2024-08-17 12:00:00'),
       (22, 24, '2024-08-18 12:00:00'),
       (23, 1, '2024-08-19 12:00:00'),
       (24, 2, '2024-08-20 12:00:00');

INSERT INTO FoodPreferences (consumer_id, food_inventory_id)
VALUES (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (4, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (5, 13),
       (5, 14),
       (5, 15),
       (5, 16),
       (5, 17),
       (5, 18),
       (6, 19),
       (6, 20),
       (6, 21),
       (6, 22),
       (6, 23),
       (6, 24),
       (7, 1),
       (7, 2);

INSERT INTO Feedbacks (consumer_id, food_inventory_id, rating, comment)
VALUES (3, 1, 5, 'Excellent quality!'),
       (3, 2, 4, 'Good value for money.'),
       (3, 3, 5, 'Loved it!'),
       (3, 4, 3, 'It was okay.'),
       (3, 5, 4, 'Tasty and fresh.'),
       (3, 6, 4, 'Would buy again.'),
       (4, 7, 5, 'Fantastic taste!'),
       (4, 8, 4, 'Nice and fresh.'),
       (4, 9, 5, 'Highly recommended.'),
       (4, 10, 3, 'Average product.'),
       (4, 11, 4, 'Good overall.'),
       (4, 12, 4, 'Satisfied with the quality.'),
       (5, 13, 5, 'Excellent as always!'),
       (5, 14, 4, 'Great product.'),
       (5, 15, 5, 'Delicious!'),
       (5, 16, 3, 'It was okay.'),
       (5, 17, 4, 'Would buy again.'),
       (5, 18, 5, 'Loved it!'),
       (6, 19, 4, 'Good quality.'),
       (6, 20, 5, 'Very fresh.'),
       (6, 21, 4, 'Happy with the purchase.'),
       (6, 22, 3, 'Okay, but could be better.'),
       (6, 23, 5, 'Fantastic product.'),
       (6, 24, 4, 'Pretty good.'),
       (7, 1, 5, 'Great taste!'),
       (7, 2, 4, 'Would recommend.');
