import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTest {
    private OrderApi orderApi;
    private UserApi userApi;
    private static List<String> ingredients;
    private final Faker faker = new Faker();
    private String name = faker.name().username();
    private String password = faker.internet().password(3, 6);
    private String email = faker.internet().emailAddress();

    @Before
    public void setUp() {
        orderApi = new OrderApi();
        userApi = new UserApi();
    }


    @Test
    public void createOrderWithoutAuth() {
        Response responseIngredients = orderApi.getIngredients();
        ingredients = responseIngredients.jsonPath().getList("data._id");
        Order order = new Order(ingredients);
        Response response = orderApi.createOrdersWithoutToken(order);
        response
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
        System.out.println(response.body().asString());
    }

    @Test
    public void createOrderWithAuth() {
        Response responseIngredients = orderApi.getIngredients();
        ingredients = responseIngredients.jsonPath().getList("data._id");
        User user = new User(name, password, email);
        Response responseCreate = userApi.createUser(user);
        String token = responseCreate.jsonPath().getString("accessToken");
        Order order = new Order(ingredients);
        Response response = orderApi.createOrdersWithToken(order, token);
        response
                .then()
                .statusCode(SC_OK)
                .body("order.number", notNullValue());
        System.out.println(response.body().asString());
    }

    @Test
    public void createOrderWithoutIngredients() {
        Order order = new Order(ingredients);
        Response response = orderApi.createOrdersWithoutToken(order);
        response
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
        System.out.println(response.body().asString());
    }

    @Test
    public void createOrderIncorrectHashIngredients() {
        Order order = new Order(Arrays.asList("1212154540", "121212154"));
        Response response = orderApi.createOrdersWithoutToken(order);
        response
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
        System.out.println(response.body().asString());
    }

    @Test
    public void getUserOrderWithoutAuth() {
        Order order = new Order();
        Response response = orderApi.getUserOrders();
        response
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
        System.out.println(response.body().asString());
    }

    @Test
    public void getUserOrderWithAuth() {
        User user = new User(name, password, email);
        Response responseCreate = userApi.createUser(user);
        String token = responseCreate.jsonPath().getString("accessToken");
        Response responseIngredients = orderApi.getIngredients();
        ingredients = responseIngredients.jsonPath().getList("data._id");
        Order order = new Order(ingredients);
        orderApi.createOrdersWithToken(order, token);
        Response response = orderApi.getUserOrdersWithAuth(token);
        response
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        System.out.println(response.body().asString());
    }

}




