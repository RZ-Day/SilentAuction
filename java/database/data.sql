BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO categories (category_name) VALUES ('Travel'), ('Concert'), ('Sport Events');

INSERT INTO items(item_number, item_description, starting_bid_amount, category_id) 
VALUES(1, '5 days 4 nights stay at Martha Vineyard', 1000.00, 1),
(2, '3 days 2 nights Disney World Vacation Package(includes hotel and admission', 1200.00, 1),
(3, 'Taylor Swift Concert', 350.00, 2),
(4, 'Sam Smith Concert', 350.00, 2),
(5, 'Super Bowl Tickets', 1000.00, 3),
(6, 'Laker Game Tickets', 500.00, 3);

COMMIT TRANSACTION;
