package com.vinay.dms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;
import jakarta.servlet.http.HttpSession;

@SpringBootApplication
@Controller
public class DmsApplication {

	public static void main(String[] args) throws SQLException {
		// Db.initDb();
		// Db.addDishes();
		SpringApplication.run(DmsApplication.class, args);
	}

	@RequestMapping("/")
	public static String homepagesq() {
		return "Welcome to our website!";
	}

	@PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded", produces = "application/text")
	public String login(@RequestBody MultiValueMap<String, String> formData, HttpSession session) throws SQLException {
		String email = formData.get("email").get(0);
		String password = formData.get("password").get(0);

		// Check if the email exists in the database
		if (!Db.checkIfUserExists(email)) {
			session.setAttribute("message", "User not found. Please register.");
			return "redirect:/register"; // Redirects to the register page
		}

		if (Db.checkPwd(email, password)) {
			session.setAttribute("email", email); // Authenticate user
			return "redirect:/index";
		} else {
			session.setAttribute("message", "Invalid email or password");
			return "login";
		}
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

	@ResponseBody
	@PostMapping(value = "/order", consumes = { "application/json;charset=UTF-8",
			"application/json" }, produces = "text/plain")
	public static String placeOrder(HttpSession session, @RequestBody String orders)
			throws SQLException {
		System.out.println(orders);

		// Call the database function with the appropriate parameters
		// Db.restaurant_menu(dish_name, dish_image_url, price, dish_type, non_veg);
		String email = (String) session.getAttribute("email");
		String orderID = Db.addOrder(orders, email);

		return orderID;

	}

	@GetMapping("/index")
	public String index() {
		return "index"; // Spring will look for index.html in the static folder
	}

	public String menupage(HttpSession session, Model model, String category) throws SQLException {
		Object email = session.getAttribute("email");

		if (email == null) {
			return "redirect:/login";
		}
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

	@SuppressWarnings("unchecked")
	@GetMapping("/viewOrder/{orderID}")
	public String viewOrder(@PathVariable("orderID") String orderID, Model model)
			throws SQLException, JsonMappingException, JsonProcessingException {

		// Retrieve the order JSON string from the database
		String orderString = Db.getOrderData(orderID);

		// Convert JSON string to a list of dish names

		// Return the list of dishes with details

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

		// ArrayList<DishInOrder> dishes = objectMapper.readValue(orderString, new
		// TypeReference<ArrayList<DishInOrder>>() {
		// });
		Map<String, Map<String, Object>> tempMap = objectMapper.readValue(orderString, Map.class);
		ArrayList<DishInOrder> dishes = new ArrayList<>();

		for (Map.Entry<String, Map<String, Object>> entry : tempMap.entrySet()) {
			String dishName = entry.getKey();
			Map<String, Object> details = entry.getValue();

			DishInOrder dish = new DishInOrder();
			dish.setDishName(dishName);
			dish.setQuantity((Integer) details.get("quantity"));
			dish.setPrice(((Number) details.get("price")).floatValue());

			dishes.add(dish);
		}

		model.addAttribute("orders", dishes);

		float total_amount = 0;
		for (DishInOrder dish:dishes){
			total_amount += dish.price * dish.quantity;
		}

		model.addAttribute("total_amount",total_amount);
		model.addAttribute("orderID", orderID);

		return "placeorder";

	}
	
	@GetMapping("/paymentgateway")
		public String paymentgateway(){
			return "paymentgateway";
		}
	



	public static boolean checkLogin(HttpSession sesh) {
		return sesh.getAttribute("email") != null;

	}

}
