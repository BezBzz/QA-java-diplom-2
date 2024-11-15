public class User {

    private String name;
    private String password;
    private String email;
  //  private String authorization;

    public User() {
    }

//    public User(String name, String password, String email, String authorization) {
//        this.name = name;
//        this.password = password;
//        this.email = email;
//        this.authorization = authorization;
//    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


//    public String getAuthorization() {
//        return authorization;
//    }
//
//    public void setAuthorization(String authorization) {
//        this.authorization = authorization;
//    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}