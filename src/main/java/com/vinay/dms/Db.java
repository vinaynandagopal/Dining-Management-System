package com.vinay.dms;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Db {
    public static void main(String args[]) throws Exception {
        // System.out.println(checkPwd("pranay@gmail.com","pranay_pwd"));
        // System.out.println(checkPwd("vinaynanda06@gmail.com","Vinay_pwd"));
        // System.out.println(checkPwd("pi@gmail.com","piii"));
        // getDishesFromDb("");
        // List<String> soupDishes = Category("Appetizer");
        // System.out.println(soupDishes);
        // addOrder(500, "momos");
    }

    // public static ResultSet executeQuery(String query) throws SQLException {

    //     // Class.forName("com.mysql.jdbc.Driver");
    //     Connection con = DriverManager.getConnection(
    //             "jdbc:mysql://localhost:3306/japaneserestaurantdelivery", "root", "Password1029384756!");
    //     // here japaneserestaurantdelivery is database name, root is username and
    //     // password
    //     Statement stmt = con.createStatement();
    //     ResultSet rs = stmt.executeQuery(query);
    //     while (rs.next())
    //         System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
    //     con.close();
    //     return rs;

    // }

    public static boolean createUser(String name, String email, String password, String phone, int user_type,
            String address) throws SQLException {
        Connection con = getConnection();
        String user_id = Functions.UuidGenerator();
        String query = "INSERT INTO users VALUES(?,?,?,?,?,?,?);";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, user_id);
        stmt.setString(3, name);
        stmt.setString(4, password);
        stmt.setString(5, phone);
        stmt.setInt(6, user_type);
        stmt.setString(7, address);
        stmt.executeUpdate();

        con.close();

        return true;
    }

    public static boolean addRestaurant(String restaurant_name, String address, String phone) throws SQLException {
        Connection con = getConnection();
        String resturant_id = Functions.UuidGenerator();
        String query = "INSERT INTO Restaurants VALUES(?,?,?,?);";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, resturant_id);
        stmt.setString(2, restaurant_name);
        stmt.setString(3, address);
        stmt.setString(4, phone);
        stmt.executeUpdate();

        con.close();

        return true;
    }

    public static String addOrder(String order_string, String user_id) throws SQLException {
        Connection con = getConnection();
        String order_id = Functions.UuidGenerator();

        String query = "INSERT INTO orders VALUES(?,?,?);";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, order_id);
        stmt.setString(2, user_id);
        stmt.setString(3, order_string);
        stmt.executeUpdate();

        con.close();

        return order_id;
    }

    public static boolean insertDishIntoDb(String dish_name, String dish_image_url, float price, String dish_type,
            boolean non_veg) throws SQLException {
        Connection con = getConnection();
        String dish_id = Functions.UuidGenerator();
        String restaurant_id = Functions.UuidGenerator();
        String query = "INSERT INTO restaurantMenu VALUES(?,?,?,?,?,?,?)";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, dish_id);
        stmt.setString(2, restaurant_id);
        stmt.setString(3, dish_name);
        stmt.setString(4, dish_image_url);
        stmt.setFloat(5, price);
        stmt.setBoolean(6, non_veg);
        stmt.setString(7, dish_type);
        stmt.executeUpdate();

        con.close();

        return true;
    }

    public static boolean cateringEvents(float event_date, int guest_count, String event_description)
            throws SQLException {
        Connection con = getConnection();
        String event_id = Functions.UuidGenerator();
        String user_id = Functions.UuidGenerator();
        String restaurant_id = Functions.UuidGenerator();
        String query = "INSERT INTO catering_events VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, event_id);
        stmt.setString(2, user_id);
        stmt.setFloat(3, event_date);
        stmt.setString(4, restaurant_id);
        stmt.setInt(5, guest_count);
        stmt.setString(6, event_description);
        stmt.executeUpdate();

        con.close();

        return true;
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/japaneserestaurantdelivery", "root", "Password1029384756!");
        // here japaneserestaurantdelivery is database name, root is username and
        // password
        return con;
    }

    public static boolean checkIfUserExists(String email) throws SQLException {
        Connection con = getConnection();
        String query = "select * from users where email=?";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        boolean checkresult = result.isBeforeFirst();
        con.close();

        return checkresult;

    }

    public static boolean checkPwd(String email, String typed_pwd) throws SQLException {
        Connection con = getConnection();
        String query = "select * from users where email=? and password_hash=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, typed_pwd);
        ResultSet result = stmt.executeQuery();
        boolean checkresult = result.isBeforeFirst();
        con.close();

        return checkresult;
    }

    public static boolean userExists(String email) throws SQLException {
        // Implement database query to check if user with given email exists
        Connection con = getConnection();
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public static ArrayList<Dish> getDishesFromDb() throws SQLException {
        Connection con = getConnection();
        String query = "select * from restaurant_menu";
        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet result = stmt.executeQuery();
        ArrayList<Dish> dishes = new ArrayList<Dish>(); // Create an ArrayList object
        while (result.next()) {
            String dish_name = result.getString("dish_name");
            String restaurant_id = result.getString("restaurant_id");
            String dish_image_url = result.getString("dish_image_url");
            float price = result.getFloat("price");
            boolean non_veg = result.getBoolean("non_veg");
            String dish_type = result.getString("dish_type");

            Dish d = new Dish(dish_name, restaurant_id, dish_image_url, price, non_veg, dish_type);

            dishes.add(d);
        }
        return dishes;
    }

    public static ArrayList<Dish> getDishesOfCategory(String dish_type) throws SQLException {
        ArrayList<Dish> dishes = new ArrayList<>();
        Connection con = getConnection();
        String query = "SELECT * FROM restaurant_menu WHERE dish_type = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, dish_type);
        ResultSet result = stmt.executeQuery();

        // Iterate through the ResultSet and add dish name, price, and image URL
        // together
        while (result.next()) {
            String dish_name = result.getString("dish_name");
            String restaurant_id = result.getString("restaurant_id");
            String dish_image_url = result.getString("dish_image_url");
            float price = result.getFloat("price");
            boolean non_veg = result.getBoolean("non_veg");

            Dish d = new Dish(dish_name, restaurant_id, dish_image_url, price, non_veg, dish_type);

            dishes.add(d);
        }

        // Close the resources
        result.close();
        stmt.close();
        con.close();

        return dishes;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        String query = "SELECT * FROM orders";

        try (Connection conn = Db.getConnection(); // Assuming Db.getConnection() provides the connection
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String orderId = rs.getString("order_id");
                String userId = rs.getString("user_id");
                String restaurantId = rs.getString("restaurant_id");
                float totalAmount = rs.getFloat("total_amount");
                String orderItem = rs.getString("order_item");
                String deliveryId = rs.getString("delivery_id");

                Order order = new Order(orderId, userId, restaurantId, totalAmount, orderItem, deliveryId);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static void storeOrder(String orderId, String orderData) throws SQLException {
        Connection con = getConnection();
        String query = "INSERT INTO Orders (order_id, order_data) VALUES (?, ?);";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, orderId);
        stmt.setString(2, orderData); // Storing JSON as a string

        stmt.executeUpdate();
        con.close();
    }

    public static String getOrderData(String orderID) throws SQLException {
        Connection con = getConnection();
        String query = "SELECT order_string FROM orders WHERE order_id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, orderID);

        ResultSet rs = stmt.executeQuery();
        String orderString = null;
        if (rs.next()) {
            orderString = rs.getString("order_string");
        }
        con.close();
        return orderString;
    }

    @SuppressWarnings("unused")
    public static List<Dish> getDishesByNames(List<String> dishNames) throws SQLException {
        List<Dish> dishes = new ArrayList<>();
        Connection con = getConnection();
        String query = "SELECT * FROM restaurant_menu WHERE dish_name IN (" +
                dishNames.stream().map(name -> "?").collect(Collectors.joining(",")) + ")";
        PreparedStatement stmt = con.prepareStatement(query);

        for (int i = 0; i < dishNames.size(); i++) {
            stmt.setString(i + 1, dishNames.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Dish dish = new Dish(
                    rs.getString("dish_name"),
                    rs.getString("restaurant_id"),
                    rs.getString("dish_image_url"),
                    rs.getFloat("price"),
                    rs.getBoolean("non_veg"),
                    rs.getString("dish_type"));
            dishes.add(dish);
        }
        con.close();
        return dishes;
    }

    public static void initDb() throws SQLException {
        String table_creator = "create table if not exists users (\n" + //
                "   email varchar(50) primary key NOT NULL,\n" + //
                "\tuser_id varchar(36)NOT NULL,\n" + //
                "    name varchar(20) NOT NULL,\n" + //
                "    password_hash varchar(64) NOT NULL,\n" + //
                "    phone varchar(10),\n" + //
                "    user_type int,\n" + //
                "    user_address varchar(100) NOT NULL\n" + //
                ");\n" + //
                "CREATE TABLE if not exists Restaurants (\n" + //
                "    restaurant_id varchar(36) PRIMARY KEY,\n" + //
                "    restaurant_name VARCHAR(20) NOT NULL,\n" + //
                "    address varchar(100) NOT NULL,\n" + //
                "    phone VARCHAR(10)\n" + //
                ");\n" + //
                "\n" + //
                "CREATE TABLE if not exists orders (\n" + //
                "    order_id varchar(36) PRIMARY KEY,\n" + //
                "    user_id varchar(36),\n" + //
                "    order_string TEXT,\n" + //
                "    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE\n" + //
                "    \n" + //
                ");\n" + //
                "\n" + //
                "create table if not exists restaurant_menu(\n" + //
                "\trestaurant_id varchar(36) NOT NULL,\n" + //
                "\tdish_name VARCHAR(20) PRIMARY KEY,\n" + //
                "    dish_image_url VARCHAR(225) NOT NULL,\n" + //
                "    price float NOT NULL,\n" + //
                "    non_veg BOOL,\n" + //
                "    dish_type VARCHAR(15),\n" + //
                "    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE\n" + //
                ");\n" + //
                "\n" + //
                "create table if not exists catering_events(\n" + //
                "\tevent_id varchar(36) PRIMARY KEY,\n" + //
                "    user_id varchar(36) NOT NULL,\n" + //
                "    event_date DATE NOT NULL,\n" + //
                "    restaurant_id varchar(36) NOT NULL,\n" + //
                "    guest_count INT NOT NULL,\n" + //
                "    event_description VARCHAR(200),\n" + //
                "\tFOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,\n" + //
                "    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id) ON DELETE CASCADE\n)" //
        ;

        Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(table_creator);
        stmt.executeUpdate();

    }

    public static void addDishes() throws SQLException {
        String dish_adder = "INSERT INTO restaurant_menu (restaurant_id, dish_name, dish_image_url, price, non_veg, dish_type)\r\n"
                + //
                "VALUES\r\n" + //
                "-- Soup\r\n" + //
                "( 1, 'Shoyu Ramen', 'https://www.justonecookbook.com/wp-content/uploads/2023/04/Spicy-Shoyu-Ramen-8055-I.jpg', 700, FALSE, 'Soup'),\r\n"
                + //
                "( 1, 'Tonkotsu Ramen', 'https://www.foodandwine.com/thmb/0AXGLeY6dYnY8sEXFqxBa8opDrs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Tonkotsu-Ramen-FT-BLOG1122-8fe6c12d609a4fd4ab246bea3aae140e.jpg', 800, TRUE, 'Soup'),\r\n"
                + //
                "( 1, 'Sukiyaki Soup', 'https://i2.wp.com/ramenkaonashi.com/wp-content/uploads/2020/12/1597038574277-01.jpeg?resize=678%2C381&ssl=1', 950, TRUE, 'Soup'),\r\n"
                + //
                "( 1, 'Clam Miso Soup', 'https://www.justonecookbook.com/wp-content/uploads/2020/04/Clam-Miso-Soup-0110-I-1.jpg', 550, FALSE, 'Soup'),\r\n"
                + //
                "( 1, 'Seaweed Soup', 'https://www.eatingwell.com/thmb/XJA2VvZ6MXNUZIrycFrUEErnQzI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/4502833-43682aa7d7584ae7a7dcc3f79ba055a5.jpg', 400, FALSE, 'Soup'),\r\n"
                + //
                "( 1, 'Miso Soup', 'http://example.com/miso_soup.jpg', 400, FALSE, 'Soup'), \r\n" + //
                "\r\n" + //
                "-- Appetizer\r\n" + //
                "( 1, 'Tempura', 'http://example.com/tempura.jpg', 800, TRUE, 'Appetizer'),\r\n" + //
                "( 1, 'Takoyaki', 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2012/07/takoyaki-recipe-4792w.jpg', 450, TRUE, 'Appetizer'),\r\n"
                + //
                "( 1, 'Yakitori', 'https://www.justonecookbook.com/wp-content/uploads/2024/04/Yakitori-7831-I.jpg', 500, TRUE, 'Appetizer'),\r\n"
                + //
                "( 1, 'Gyoza', 'https://www.justonecookbook.com/wp-content/uploads/2024/04/Gyoza-7444-I-2.jpg', 400, TRUE, 'Appetizer'),\r\n"
                + //
                "( 1, 'Onigiri', 'https://food-images.files.bbci.co.uk/food/recipes/onigiri_39079_16x9.jpg', 300, FALSE, 'Appetizer'),\r\n"
                + //
                "\r\n" + //
                "-- Main Course\r\n" + //
                "( 1, 'Sushi Platter', 'https://sodelicious.recipes/wp-content/uploads/2018/10/13.07.2018-R-4-lat-50-sushi-Sushi-Party-Platter.jpg', 850, FALSE, 'Main Course'),\r\n"
                + //
                "( 1, 'Ramen Noodles', 'https://www.pacificfoods.com/wp-content/uploads/2024/10/15-Minute-Spicy-Red-Miso-Ramen.jpg', 650, FALSE, 'Main Course'),\r\n"
                + //
                "( 1, 'Chicken Teriyaki', 'https://www.onceuponachef.com/images/2024/01/chicken-teriyaki-1200x1553.jpg', 750, TRUE, 'Main Course'),\r\n"
                + //
                "( 1, 'Sashimi', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmRyxvh10RHeZkwSMiHLOT7qGXBMaXPsWhZA&s', 950, FALSE, 'Main Course'),\r\n"
                + //
                "( 1, 'Udon Noodles', 'https://choosingchia.com/jessh-jessh/uploads/2021/10/Yaki-Udon-Stir-Fry-9.jpg', 600, FALSE, 'Main Course'),\r\n"
                + //
                "( 1, 'Okonomiyaki', 'https://flouredframe.com/wp-content/uploads/2020/08/vegan-okonomiyaki-ft-image-1200x1200-1.jpg', 700, FALSE, 'Main Course'),\r\n"
                + //
                "( 1, 'Tonkatsu', 'https://oryoki.de/media/image/news/53/md/tonkatsu-schnitzel_preview.jpg', 850, TRUE, 'Main Course'),\r\n"
                + //
                "( 1, 'Katsu Curry', 'https://www.justonecookbook.com/wp-content/uploads/2020/05/Katsu-Curry-4993-I.jpg', 900, TRUE, 'Main Course'),\r\n"
                + //
                "\r\n" + //
                "-- Dessert\r\n" + //
                "( 1, 'Matcha Ice Cream', 'https://www.rotinrice.com/wp-content/uploads/2011/08/MatchaIceCream-1-500x500.jpg', 250, FALSE, 'Dessert'),\r\n"
                + //
                "( 1, 'Mochi', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSKv_xOIbD_OC_4m0mHRyCS3wXZfZZGRPbJDA&s', 200, FALSE, 'Dessert'),\r\n"
                + //
                "( 1, 'Dorayaki', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQI3NUUHnMRY0Rq6kkCeb0dqZjIg2mRNpVaxQ&s', 300, FALSE, 'Dessert'),\r\n"
                + //
                "( 1, 'Taiyaki', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnBsKs4jIh8ARhCvuf1Mm1Kn_vMRoxnOnkwA&s', 350, FALSE, 'Dessert'),\r\n"
                + //
                "( 1, 'Dango', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpjuObSEXo14c5oQOJFeKW09o5CA6ObwOJXA&s', 250, FALSE, 'Dessert'),\r\n"
                + //
                "( 1, 'Anmitsu', 'https://japanesetaste.com/cdn/shop/articles/how-to-make-anmitsu-at-home-japanese-summer-dessert-recipe-japanese-taste.jpg?v=1707914049&width=5760', 400, FALSE, 'Dessert'),\r\n"
                + //
                "( 1, 'Yuzu Sorbet', 'https://i0.wp.com/cookingwithteamj.com/wp-content/uploads/2017/05/DSC_1219.jpg?resize=1000%2C1500', 350, FALSE, 'Dessert'),\r\n"
                + //
                "\r\n" + //
                "-- Drinks\r\n" + //
                "( 1, 'Green Tea', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQslQ27WWQ4ekXKqy3Kk1ZMdpb1jpyZLv3CMA&s', 150, FALSE, 'Drink'),\r\n"
                + //
                "( 1, 'Sake', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmLwUTIyWe3CfYP596gzzYnDb0YbszGEN2cA&s', 500, FALSE, 'Drink'),\r\n"
                + //
                "( 1, 'Umeshu', 'https://www.radelan.com/wp-content/uploads/2016/02/SHIN-UMESHU.png', 450, FALSE, 'Drink'),\r\n"
                + //
                "( 1, 'Calpico', 'https://i.ytimg.com/vi/96cK2917gOE/maxresdefault.jpg', 200, FALSE, 'Drink'),\r\n" + //
                "( 1, 'Ramune', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVRC54k50sMlIDJ2TETV4SHa14xdGmAZgxnw&s', 250, FALSE, 'Drink'),\r\n"
                + //
                "( 1, 'Matcha Latte', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-ijLCwg405u_dvKyaK8XTXPIYidzglDB6xQ&s', 300, FALSE, 'Drink');";
        Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(dish_adder);
        stmt.executeUpdate();
    }

}
