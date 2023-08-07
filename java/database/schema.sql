BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- Auction Table
CREATE TABLE Auction (
    auction_id SERIAL PRIMARY KEY,
    auction_name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,

);

-- Item Table
CREATE TABLE Item (
    item_id SERIAL PRIMARY KEY,
    auction_id INT,
    user_id INT,
    item_name VARCHAR(255) NOT NULL,
    description TEXT,
    initial_price DECIMAL(10, 2) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (auction_id) REFERENCES Auction(auction_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)

);

-- Bid Table
CREATE TABLE Bid (
    bid_id SERIAL PRIMARY KEY,
    item_id INT,
    user_id INT,
    bid_amount DECIMAL(10, 2) NOT NULL,
    bid_time TIMESTAMP NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)

);
COMMIT TRANSACTION;
