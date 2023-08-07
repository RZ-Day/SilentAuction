BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE categories (
	category_id serial,
	category_name varchar (500) NOT NULL,
	CONSTRAINT PK_categories PRIMARY KEY (category_id)

);

CREATE TABLE items (
	item_id serial,
	item_number INT NOT NULL,
	item_description varchar(500) NOT NULL,
	starting_bid_amount DECIMAL (10, 2) NOT NULL,
	category_id INT NOT NULL,
	CONSTRAINT PK_item_id PRIMARY KEY(item_id),
	CONSTRAINT FK_category_id FOREIGN KEY (category_id) REFERENCES categories (category_id)
);


COMMIT TRANSACTION;
