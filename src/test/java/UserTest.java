import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {
    private final Faker faker = new Faker();
    private UserApi userApi;
    private String name = faker.name().username();
    private String password = faker.internet().password(3, 6);
    private String email = faker.internet().emailAddress();

    @Before
    public void SetUp() {
        userApi = new UserApi();
    }

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Проверяем создание пользователя")
    public void createUserDone() {
        User user = new User(name, password, email);
        Response response = userApi.createUser(user);
        response
                .then()
                .body("success", equalTo(true))
                .statusCode(SC_OK);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание существующего пользователя")
    @Description("Проверяем корректное поведение при повторном создании пользователя")
    public void createExistingUser() {
        User user = new User(name, password, email);
        userApi.createUser(user);
        Response response = userApi.createUser(user);
        response
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("User already exists"));
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверяем корректное поведение при создании пользователя без пароля")
    public void createUserWithoutPassword() {
        User user = new User(name, null, email);
        Response response = userApi.createUser(user);
        response
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Вход пользователем")
    @Description("Проверяем успешный вход")
    public void loginExistingUser() {
        User user = new User(name, password, email);
        userApi.createUser(user);
        Response response = userApi.loginUser(user);
        response
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Логин с невалидными логином и паролем")
    @Description("Проверяем корректное поведение при невалидных кредах")
    public void loginWithIncorrectLoginAndPassword() {
        User user = new User("testLogin", "testPass", email);
        Response response = userApi.loginUser(user);
        response
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"));
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Изменение пользователя без авторизации")
    @Description("Проверяем измненние данных пользователя без авторизации")
    public void changeDataUserWithoutAuthorization() {
        User user = new User(name, password, email);
        userApi.createUser(user);
        Response response = userApi.changeUserDataWithoutAuth(user);
        user.setName(name);
        user.setEmail(email);
        response
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Изменение пользователя с авторизацией")
    @Description("Проверяем измненние данных пользователя с авторизацией")
    public void changeDataUserWithAuthorization() {
        User user = new User(name, password, email);
        Response response = userApi.createUser(user);
        String token = response.jsonPath().getString("accessToken");
        user.setName(name);
        user.setEmail(email);
        Response updateResponse = userApi.changeUserDataWithAuth(user, token);
        updateResponse
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
        System.out.println(response.body().asString());

    }
}
