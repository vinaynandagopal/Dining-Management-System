package com.vinay.dms;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;
import jakarta.servlet.http.HttpSession;

@SpringBootApplication
@Controller
public class DmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

	@RequestMapping("/")
	public static String homepagesq() {
		return "Welcome to our website!";
	}

	@PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded", produces = "application/text")
	public static String login(@RequestBody MultiValueMap<String, String> formData, HttpSession session)
			throws SQLException {
		// String email=l.getEmail();
		// String password=l.getPassword();
		String email = formData.get("email").get(0);
		String password = formData.get("password").get(0);
		System.out.println(email);
		System.out.println(password);
		if (Db.checkPwd(email, password)) {
			// if code is coming here then it means user is authenticated
			session.setAttribute("email", email);
			return "redirect:/index";
		} else {
			session.setAttribute("message", "Invalid email");
		}
		return "login";

	}

	@GetMapping("/login")
	public String loginpage() {
		return "login";// will automatically detect login.html in templates folder
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("email");
		session.invalidate();
		return "redirect:/login";
	}

	@PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded", produces = "application/text")
	public static void register(@RequestBody MultiValueMap<String, String> formData) throws SQLException {
		// String email=l.getEmail();
		// String password=l.getPassword();
		String email = formData.get("email").get(0);
		String password = formData.get("password").get(0);
		String name = formData.get("name").get(0);
		String address = formData.get("address").get(0);
		String phone = formData.get("phone").get(0);
		Db.createUser(name, email, password, phone, 1, address);
	}

	@GetMapping("/register")
	public String signuppage() {
		return "register";// will automatically detect login.html in templates folder
	}

	@PostMapping(value = "/order", consumes = "application/x-www-form-urlencoded", produces = "application/text")
	public static void menu(HttpSession session, @RequestBody MultiValueMap<String, String> formData)
			throws SQLException {
		// Extract values from the formData map
		String dish_name = formData.get("dish_name").get(0);
		String dish_image_url = formData.get("dish_image_url").get(0);

		// Convert string to float
		float price = Float.parseFloat(formData.get("price").get(0));

		// Convert string to boolean
		boolean non_veg = Boolean.parseBoolean(formData.get("non_veg").get(0));

		String dish_type = formData.get("dish_type").get(0);

		// Call the database function with the appropriate parameters
		// Db.restaurant_menu(dish_name, dish_image_url, price, dish_type, non_veg);
	}

	@GetMapping("/index")
	public String index() {
		return "index"; // Spring will look for index.html in the static folder
	}

	public String menupage(HttpSession session, Model model, String category) throws SQLException {
		Object email = session.getAttribute("email");

		// if (email == null) {
		// return "redirect:/login";
		// }
		model.addAttribute("dishes", Db.getDishesOfCategory(category));
		return "menu";
	}

	@GetMapping(value = { "/soups", "/menu" })
	public String getSoupsPage(HttpSession session, Model model) throws SQLException {
		return menupage(session, model, "Soup");
	}

	@GetMapping("/appetizers")
	public String getAppetizersPage(HttpSession session, Model model) throws SQLException {
		return menupage(session, model, "Appetizer");
	}

	// New mapping for main course
	@GetMapping("/maincourse")
	public String getMainCoursePage(HttpSession session, Model model) throws SQLException {
		return menupage(session, model, "Main Course");
	}

	// New mapping for desserts
	@GetMapping("/desserts")
	public String getDessertsPage(HttpSession session, Model model) throws SQLException {
		return menupage(session, model, "Dessert");
	}

	@GetMapping("/drinks")
	public String getDrinksPage(HttpSession session, Model model) throws SQLException {
		return menupage(session, model, "Drink");
	}

	// @PostMapping(value = "/order", consumes =
	// "application/x-www-form-urlencoded", produces = "application/text")
	// public static void order(@RequestBody MultiValueMap<String, String> formData)
	// throws SQLException {
	// // Extract values from the formData map
	// String dish_name = formData.get("dish_name").get(0);
	// String dish_image_url = formData.get("dish_image_url").get(0);

	// // Convert string to float
	// float price = Float.parseFloat(formData.get("price").get(0));

	// // Convert string to boolean
	// boolean non_veg = Boolean.parseBoolean(formData.get("non_veg").get(0));

	// String dish_type = formData.get("dish_type").get(0);

	// // Call the database function with the appropriate parameters
	// Db.restaurantMenu(dish_name, dish_image_url, price, dish_type, non_veg);
	// }



	@PostMapping("/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody String orderData) {
		try {
			System.out.println(orderData);
			// Generate an order ID
			String orderId = Functions.UuidGenerator();

			// Store order data in the database as a JSON string
			Db.storeOrder(orderId, orderData);

			// Return the order ID in the response
			return ResponseEntity.ok("/viewOrder/" + orderId);
			

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order");
		}
	}

	@GetMapping("/viewOrder/{orderID}")
	public ResponseEntity<List<Dish>> viewOrder(@PathVariable String orderID) {
		try {
			// Retrieve the order JSON string from the database
			String orderData = Db.getOrderData(orderID);

			// Convert JSON string to a list of dish names
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(orderData);
			List<String> dishNames = new ArrayList<>();
			jsonNode.get("dishes").forEach(dish -> dishNames.add(dish.asText()));

			// Retrieve Dish objects based on dish names from the database
			List<Dish> dishes = Db.getDishesByNames(dishNames);

			// Return the list of dishes with details
			return ResponseEntity.ok(dishes);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	public static boolean checkLogin(HttpSession sesh) {
		return sesh.getAttribute("email") != null;

	}

}
