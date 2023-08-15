BEGIN TRANSACTION;


INSERT INTO users (full_name, email, phone, address_billing, address_shipping, username,password_hash, role) VALUES ('TestUser1', 'test1@test.com', 666, 'testAddress1', 'testAddress1', 'user1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (full_name, email, phone, address_billing, address_shipping, username,password_hash,role) VALUES ('TestUser2', 'test2@test.com', 555,'testAddress2', 'testAddress2', 'admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (full_name, email, phone, address_billing, address_shipping, username,password_hash,role) VALUES ('User 3', 'user3@example.com', '5555555555', 'Address 3', 'Address 3', 'user3', 'hash3', 'ROLE_USER');
-- Sandbox data

-- Insert fake auctions

INSERT INTO auction (auction_name, start_time, end_time, isPrivate, privateKey)
VALUES
    ('Summer Auction', '2023-08-15 09:00:00', '2023-08-20 18:00:00',false,0),
    ('Vintage Collectibles', '2023-09-10 12:00:00', '2023-09-15 22:00:00',false,0),
    ('Art Gala', '2023-09-25 10:00:00', '2023-09-30 20:00:00',false,0),
    ('Tech Showcase', '2023-10-05 09:30:00', '2023-10-10 17:30:00',false,0),
    ('Jewelry Auction', '2023-10-20 11:00:00', '2023-10-25 21:00:00',false,0),
    ('Book Lovers', '2023-11-01 08:00:00', '2023-11-05 16:00:00',false,0),
    ('Sports Memorabilia', '2023-11-15 14:00:00', '2023-11-20 23:00:00',false,0),
    ('Holiday Gifts', '2023-12-01 10:00:00', '2023-12-10 19:00:00',false,0),
    ('Fashion Extravaganza', '2023-12-15 12:00:00', '2023-12-20 22:00:00',false,0),
    ('Home Decor', '2023-12-25 09:00:00', '2023-12-31 18:00:00',false,0),
    ('Luxury Watches', '2023-08-11 09:00:00', '2023-08-14 12:00:00', true, 123456789  );

-- Insert fake items
INSERT INTO item (auction_id, user_id, item_name, description, initial_price, current_price)
VALUES
    (1, 2, 'Beach Sunset Painting', 'A beautiful painting of a beach sunset.', 100.00, 150.00),
    (1, 2, 'Antique Pocket Watch', 'A vintage pocket watch in excellent condition.', 50.00, 75.00),
    (2, 2, 'Vintage Vinyl Records', 'Collection of classic rock vinyl records.', 75.00, 100.00),
    (2, 2, 'Antique Typewriter', 'A fully functional antique typewriter.', 80.00, 110.00),
    (3, 2, 'Abstract Artwork', 'An abstract artwork with vibrant colors and unique shapes.', 200.00, 250.00),
    (4, 2, 'Virtual Reality Headset', 'Experience the future with this advanced VR headset.', 300.00, 350.00),
    (5, 2, 'Diamond Necklace', 'Elegant diamond necklace that radiates luxury.', 500.00, 600.00),
    (6, 2, 'Classic Literature Set', 'Collection of classic novels from various authors.', 50.00, 70.00),
    (7, 2, 'Signed Sports Jersey', 'Authentic jersey signed by a legendary athlete.', 150.00, 180.00),
    (7, 2, 'Sports Memorabilia Collection', 'A collection of sports memorabilia including cards, posters, and more.', 200.00, 240.00),
    (8, 2, 'Handcrafted Jewelry', 'Unique handcrafted jewelry pieces perfect for the holidays.', 80.00, 100.00),
    (9, 2, 'Fashion Designer Dress', 'Elegant dress designed by a renowned fashion designer.', 300.00, 350.00),
    (9, 2, 'Designer Handbag', 'Luxurious handbag from a top fashion brand.', 150.00, 180.00),
    (10, 2, 'Home Decor Bundle', 'Bundle of home decor items to beautify your living space.', 100.00, 120.00),
    (10, 2, 'Artificial Christmas Tree', 'High-quality artificial Christmas tree for the holiday season.', 80.00, 100.00);



-- Insert fake bids
INSERT INTO bid (item_id, user_id, bid_amount, bid_time)
VALUES
    (1, 2, 160.00, '2023-08-18 15:30:00'),
    (1, 3, 165.00, '2023-08-18 15:35:00'),
    (1, 1, 175.00, '2023-08-19 10:45:00'),
    (2, 1, 85.00, '2023-08-16 14:20:00'),
    (3, 2, 110.00, '2023-09-12 16:10:00');



COMMIT TRANSACTION;
