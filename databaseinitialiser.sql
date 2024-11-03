create database if not exists japaneserestaurantdelivery;
use japaneserestaurantdelivery;
create table if not exists users (
   email varchar(50) primary key NOT NULL,
	user_id varchar(36)NOT NULL,
    name varchar(20) NOT NULL,
    password_hash varchar(64) NOT NULL,
    phone varchar(10),
    user_type int,
    user_address varchar(100) NOT NULL
);
CREATE TABLE if not exists Restaurants (
    restaurant_id varchar(36) PRIMARY KEY,
    restaurant_name VARCHAR(20) NOT NULL,
    address varchar(100) NOT NULL,
    phone VARCHAR(10)
);

CREATE TABLE if not exists orders (
    order_id varchar(36) PRIMARY KEY,
    user_id varchar(36),
    order_string TEXT,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
    
);

create table if not exists restaurant_menu(
	restaurant_id varchar(36) NOT NULL,
	dish_name VARCHAR(20) PRIMARY KEY,
    dish_image_url VARCHAR(225) NOT NULL,
    price float NOT NULL,
    non_veg BOOL,
    dish_type VARCHAR(15),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE
);

create table if not exists catering_events(
	event_id varchar(36) PRIMARY KEY,
    user_id varchar(36) NOT NULL,
    event_date DATE NOT NULL,
    restaurant_id varchar(36) NOT NULL,
    guest_count INT NOT NULL,
    event_description VARCHAR(200),
	FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE
);



	
    

    


