BEGIN TRANSACTION;

DROP TABLE IF EXISTS
conversations, messages, users,
user_contacts, auction, item,
bid, watchlist
CASCADE;



CREATE TABLE users (
	user_id SERIAL,
	full_name varchar(50),
	email varchar(50) NOT NULL UNIQUE,
	phone varchar(15) NOT NULL UNIQUE,
	address_billing varchar(50) NOT NULL,
	address_shipping varchar(50) NOT NULL,
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
    FOREIGN KEY (item_id) REFERENCES item(item_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE conversations (
    conversation_id SERIAL PRIMARY KEY,
    seller_id INT,
    buyer_id INT,
    item_id INT,
	item_name VARCHAR(255) NOT NULL,

    FOREIGN KEY (seller_id) REFERENCES users(user_id),
    FOREIGN KEY (buyer_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES item(item_id),

    CONSTRAINT no_self_messaging CHECK (seller_id <> buyer_id),
    CONSTRAINT unique_combination UNIQUE (buyer_id, seller_id, item_id)
);

CREATE TABLE messages (
    message_id SERIAL PRIMARY KEY,
    from_id INT,
    to_id INT,
    conversation_id INT,
    message_body varchar(300),

    FOREIGN KEY (from_id) REFERENCES users(user_id),
    FOREIGN KEY (to_id) REFERENCES users(user_id),
    FOREIGN KEY (conversation_id) REFERENCES conversations(conversation_id)
);
-- Watchlist table
CREATE TABLE watchlist (
    watchlist_id SERIAL PRIMARY KEY,
    user_id INT,
    item_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES item(item_id)
);

--item images
CREATE TABLE item_image (
    image_id SERIAL PRIMARY KEY,
    item_id INT,
    image_data VARBINARY(MAX),
    FOREIGN KEY (item_id) REFERENCES item(item_id)
);


COMMIT TRANSACTION;
