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
        addOrder(500, "momos");
    }

    public static ResultSet executeQuery(String query) throws SQLException {

        // Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/japaneserestaurantdelivery", "root", "Password1029384756!");
        // here japaneserestaurantdelivery is database name, root is username and
        // password
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
        con.close();
        return rs;

    }

    public static boolean createUser(String name, String email, String password, String phone, int user_type,
            String address) throws SQLException {
        Connection con = getConnection();
        String user_id = Functions.UuidGenerator();
        String query = "INSERT INTO users VALUES(?,?,?,?,?,?,?);";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, user_id);
        stmt.setString(2, name);
        stmt.setString(3, email);
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

    public static boolean addOrder(float total_amount, String order_item) throws SQLException {
        Connection con = getConnection();
        String order_id = Functions.UuidGenerator();
        String delivery_id = Functions.UuidGenerator();
        String query = "INSERT INTO Orders VALUES(?,?,?,?);";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, order_id);
        stmt.setFloat(4, total_amount);
        stmt.setString(5, order_item);
        stmt.setString(6, delivery_id);
        stmt.executeUpdate();

        con.close();

        return true;
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
    String query = "SELECT order_data FROM Orders WHERE order_id = ?";
    PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, orderID);

    ResultSet rs = stmt.executeQuery();
    String orderData = null;
    if (rs.next()) {
        orderData = rs.getString("order_data");
    }
    con.close();
    return orderData;
}

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
            rs.getString("dish_type")
        );
        dishes.add(dish);
    }
    con.close();
    return dishes;
}

}
