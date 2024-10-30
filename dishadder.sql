use japaneserestaurantdelivery;
INSERT INTO restaurant_menu (restaurant_id, dish_name, dish_image_url, price, non_veg, dish_type)
VALUES
-- Soup
( 1, 'Shoyu Ramen', 'https://www.justonecookbook.com/wp-content/uploads/2023/04/Spicy-Shoyu-Ramen-8055-I.jpg', 700, FALSE, 'Soup'),
( 1, 'Tonkotsu Ramen', 'https://www.foodandwine.com/thmb/0AXGLeY6dYnY8sEXFqxBa8opDrs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Tonkotsu-Ramen-FT-BLOG1122-8fe6c12d609a4fd4ab246bea3aae140e.jpg', 800, TRUE, 'Soup'),
( 1, 'Sukiyaki Soup', 'https://i2.wp.com/ramenkaonashi.com/wp-content/uploads/2020/12/1597038574277-01.jpeg?resize=678%2C381&ssl=1', 950, TRUE, 'Soup'),
( 1, 'Clam Miso Soup', 'https://www.justonecookbook.com/wp-content/uploads/2020/04/Clam-Miso-Soup-0110-I-1.jpg', 550, FALSE, 'Soup'),
( 1, 'Seaweed Soup', 'https://www.eatingwell.com/thmb/XJA2VvZ6MXNUZIrycFrUEErnQzI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/4502833-43682aa7d7584ae7a7dcc3f79ba055a5.jpg', 400, FALSE, 'Soup'),
( 1, 'Miso Soup', 'http://example.com/miso_soup.jpg', 400, FALSE, 'Soup'), 

-- Appetizer
( 1, 'Tempura', 'http://example.com/tempura.jpg', 800, TRUE, 'Appetizer'),
( 1, 'Takoyaki', 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2012/07/takoyaki-recipe-4792w.jpg', 450, TRUE, 'Appetizer'),
( 1, 'Yakitori', 'https://www.justonecookbook.com/wp-content/uploads/2024/04/Yakitori-7831-I.jpg', 500, TRUE, 'Appetizer'),
( 1, 'Gyoza', 'https://www.justonecookbook.com/wp-content/uploads/2024/04/Gyoza-7444-I-2.jpg', 400, TRUE, 'Appetizer'),
( 1, 'Onigiri', 'https://food-images.files.bbci.co.uk/food/recipes/onigiri_39079_16x9.jpg', 300, FALSE, 'Appetizer'),

-- Main Course
( 1, 'Sushi Platter', 'https://sodelicious.recipes/wp-content/uploads/2018/10/13.07.2018-R-4-lat-50-sushi-Sushi-Party-Platter.jpg', 850, FALSE, 'Main Course'),
( 1, 'Ramen Noodles', 'https://www.pacificfoods.com/wp-content/uploads/2024/10/15-Minute-Spicy-Red-Miso-Ramen.jpg', 650, FALSE, 'Main Course'),
( 1, 'Chicken Teriyaki', 'https://www.onceuponachef.com/images/2024/01/chicken-teriyaki-1200x1553.jpg', 750, TRUE, 'Main Course'),
( 1, 'Sashimi', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmRyxvh10RHeZkwSMiHLOT7qGXBMaXPsWhZA&s', 950, FALSE, 'Main Course'),
( 1, 'Udon Noodles', 'https://choosingchia.com/jessh-jessh/uploads/2021/10/Yaki-Udon-Stir-Fry-9.jpg', 600, FALSE, 'Main Course'),
( 1, 'Okonomiyaki', 'https://flouredframe.com/wp-content/uploads/2020/08/vegan-okonomiyaki-ft-image-1200x1200-1.jpg', 700, FALSE, 'Main Course'),
( 1, 'Tonkatsu', 'https://oryoki.de/media/image/news/53/md/tonkatsu-schnitzel_preview.jpg', 850, TRUE, 'Main Course'),
( 1, 'Katsu Curry', 'https://www.justonecookbook.com/wp-content/uploads/2020/05/Katsu-Curry-4993-I.jpg', 900, TRUE, 'Main Course'),

-- Dessert
( 1, 'Matcha Ice Cream', 'https://www.rotinrice.com/wp-content/uploads/2011/08/MatchaIceCream-1-500x500.jpg', 250, FALSE, 'Dessert'),
( 1, 'Mochi', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSKv_xOIbD_OC_4m0mHRyCS3wXZfZZGRPbJDA&s', 200, FALSE, 'Dessert'),
( 1, 'Dorayaki', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQI3NUUHnMRY0Rq6kkCeb0dqZjIg2mRNpVaxQ&s', 300, FALSE, 'Dessert'),
( 1, 'Taiyaki', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnBsKs4jIh8ARhCvuf1Mm1Kn_vMRoxnOnkwA&s', 350, FALSE, 'Dessert'),
( 1, 'Dango', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpjuObSEXo14c5oQOJFeKW09o5CA6ObwOJXA&s', 250, FALSE, 'Dessert'),
( 1, 'Anmitsu', 'https://japanesetaste.com/cdn/shop/articles/how-to-make-anmitsu-at-home-japanese-summer-dessert-recipe-japanese-taste.jpg?v=1707914049&width=5760', 400, FALSE, 'Dessert'),
( 1, 'Yuzu Sorbet', 'https://i0.wp.com/cookingwithteamj.com/wp-content/uploads/2017/05/DSC_1219.jpg?resize=1000%2C1500', 350, FALSE, 'Dessert'),

-- Drinks
( 1, 'Green Tea', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQslQ27WWQ4ekXKqy3Kk1ZMdpb1jpyZLv3CMA&s', 150, FALSE, 'Drink'),
( 1, 'Sake', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmLwUTIyWe3CfYP596gzzYnDb0YbszGEN2cA&s', 500, FALSE, 'Drink'),
( 1, 'Umeshu', 'https://www.radelan.com/wp-content/uploads/2016/02/SHIN-UMESHU.png', 450, FALSE, 'Drink'),
( 1, 'Calpico', 'https://i.ytimg.com/vi/96cK2917gOE/maxresdefault.jpg', 200, FALSE, 'Drink'),
( 1, 'Ramune', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVRC54k50sMlIDJ2TETV4SHa14xdGmAZgxnw&s', 250, FALSE, 'Drink'),
( 1, 'Matcha Latte', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-ijLCwg405u_dvKyaK8XTXPIYidzglDB6xQ&s', 300, FALSE, 'Drink');