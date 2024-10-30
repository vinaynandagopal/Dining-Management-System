use japaneserestaurantdelivery;
INSERT INTO restaurant_menu (restaurant_id, dish_name, dish_image_url, price, non_veg, dish_type)
VALUES
-- Soup
( 1, 'Shoyu Ramen', 'http://example.com/shoyu_ramen.jpg', 700, FALSE, 'Soup'),
( 1, 'Tonkotsu Ramen', 'http://example.com/tonkotsu_ramen.jpg', 800, TRUE, 'Soup'),
( 1, 'Sukiyaki Soup', 'http://example.com/sukiyaki_soup.jpg', 950, TRUE, 'Soup'),
( 1, 'Clam Miso Soup', 'http://example.com/clam_miso_soup.jpg', 550, FALSE, 'Soup'),
( 1, 'Seaweed Soup', 'http://example.com/seaweed_soup.jpg', 400, FALSE, 'Soup'),

-- Appetizer
( 1, 'Tempura', 'http://example.com/tempura.jpg', 800, TRUE, 'Appetizer'),
( 1, 'Miso Soup', 'http://example.com/miso_soup.jpg', 400, FALSE, 'Appetizer'),
( 1, 'Takoyaki', 'http://example.com/takoyaki.jpg', 450, TRUE, 'Appetizer'),
( 1, 'Yakitori', 'http://example.com/yakitori.jpg', 500, TRUE, 'Appetizer'),
( 1, 'Gyoza', 'http://example.com/gyoza.jpg', 400, TRUE, 'Appetizer'),
( 1, 'Onigiri', 'http://example.com/onigiri.jpg', 300, FALSE, 'Appetizer'),

-- Main Course
( 1, 'Sushi Platter', 'http://example.com/sushi_platter.jpg', 850, FALSE, 'Main Course'),
( 1, 'Ramen Noodles', 'http://example.com/ramen_noodles.jpg', 650, FALSE, 'Main Course'),
( 1, 'Chicken Teriyaki', 'http://example.com/chicken_teriyaki.jpg', 750, TRUE, 'Main Course'),
( 1, 'Sashimi', 'http://example.com/sashimi.jpg', 950, FALSE, 'Main Course'),
( 1, 'Udon Noodles', 'http://example.com/udon_noodles.jpg', 600, FALSE, 'Main Course'),
( 1, 'Okonomiyaki', 'http://example.com/okonomiyaki.jpg', 700, FALSE, 'Main Course'),
( 1, 'Tonkatsu', 'http://example.com/tonkatsu.jpg', 850, TRUE, 'Main Course'),
( 1, 'Katsu Curry', 'http://example.com/katsu_curry.jpg', 900, TRUE, 'Main Course'),

-- Dessert
( 1, 'Matcha Ice Cream', 'http://example.com/matcha_ice_cream.jpg', 250, FALSE, 'Dessert'),
( 1, 'Mochi', 'http://example.com/mochi.jpg', 200, FALSE, 'Dessert'),
( 1, 'Dorayaki', 'http://example.com/dorayaki.jpg', 300, FALSE, 'Dessert'),
( 1, 'Taiyaki', 'http://example.com/taiyaki.jpg', 350, FALSE, 'Dessert'),
( 1, 'Dango', 'http://example.com/dango.jpg', 250, FALSE, 'Dessert'),
( 1, 'Anmitsu', 'http://example.com/anmitsu.jwpg', 400, FALSE, 'Dessert'),
( 1, 'Yuzu Sorbet', 'http://example.com/yuzu_sorbet.jpg', 350, FALSE, 'Dessert'),

-- Drinks
( 1, 'Green Tea', 'http://example.com/green_tea.jpg', 150, FALSE, 'Drink'),
( 1, 'Sake', 'http://example.com/sake.jpg', 500, FALSE, 'Drink'),
( 1, 'Umeshu', 'http://example.com/umeshu.jpg', 450, FALSE, 'Drink'),
( 1, 'Calpico', 'http://example.com/calpico.jpg', 200, FALSE, 'Drink'),
( 1, 'Ramune', 'http://example.com/ramune.jpg', 250, FALSE, 'Drink'),
( 1, 'Matcha Latte', 'http://example.com/matcha_latte.jpg', 300, FALSE, 'Drink');
