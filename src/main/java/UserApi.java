import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    @Step("Создать пользователя")
    public Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/register");
    }

    @Step("Создать пользователя c токеном")
    public Response createUserWithToken(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/register");
    }

    @Step("Залогиниться пользователем")
    public Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/login");
    }

    @Step("Изменить данные пользователя без авторизации")
    public Response changeUserDataWithoutAuth(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .patch(BASE_URL + "/api/auth/user");
    }

    @Step("Изменить данные пользователя с авторизацией")
    public Response changeUserDataWithAuth(User user, String token) {
        changeUserDataWithoutAuth(user);
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body(user)
                .patch(BASE_URL + "/api/auth/user");

    }

    @Step("Удалить пользователя")
    public Response deleteUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .delete(BASE_URL + "/api/auth/user");
    }

    @Step("Авторизоваться пользователем")
    public Response authorizationUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .delete(BASE_URL + "/api/auth/login");
    }
}
