BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_contacts;
DROP TABLE IF EXISTS auction;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS bid;


CREATE TABLE users (
	user_id SERIAL,
	full_name varchar(50),
	email varchar(50) NOT NULL UNIQUE,
	phone varchar(10) NOT NULL UNIQUE,
	address varchar(50) NOT NULL,
  	username varchar(50) NOT NULL UNIQUE,
  	password_hash varchar(200) NOT NULL,
  	role varchar(50) NOT NULL,
  	allow_anonymous BOOLEAN DEFAULT false,
  	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- Auction Table
CREATE TABLE auction (
    auction_id SERIAL PRIMARY KEY,
    auction_name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    isPrivate BOOLEAN NOT NULL,
    privateKey varchar(32) default 'noPrivateKeySet'
);


-- Item Table
CREATE TABLE item (
    item_id SERIAL PRIMARY KEY,
    auction_id INT,
    user_id INT,
    item_name VARCHAR(255) NOT NULL,
    description TEXT,
    initial_price DECIMAL(10, 2) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (auction_id) REFERENCES auction(auction_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);


-- Bid Table
CREATE TABLE bid (
    bid_id SERIAL PRIMARY KEY,
    item_id INT,
    user_id INT,
    bid_amount DECIMAL(10, 2) NOT NULL,
    bid_time TIMESTAMP NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


COMMIT TRANSACTION;
