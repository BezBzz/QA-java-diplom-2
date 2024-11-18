import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    @Step("Создание заказа с токеном.")
    public Response createOrdersWithToken(Order order, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(order)
                .when()
                .post(BASE_URL + "/api/orders");
    }

    @Step("Создание заказа без токена.")
    public Response createOrdersWithoutToken(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(BASE_URL + "/api/orders");
    }

    @Step("Получение списка заказов пользователя.")
    public Response getUserOrders() {
        return given()
                .header("Content-type", "application/json.")
                // .header("Authorization", token)
                .get(BASE_URL + "/api/orders");
    }

    @Step("Получение списка заказов пользователя c авторизацией.")
    public Response getUserOrdersWithAuth(String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .get(BASE_URL + "/api/orders");
    }

    @Step("Получение списка ингредиентов.")
    public Response getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(BASE_URL + "/api/ingredients");
    }

}
