package entities;

public class User {

    UserRegistration user;

    public User(UserRegistration userRegistration) {
        this.user = userRegistration;
    }

    public static class UserRegistration {
        String username;
        String email;
        String password;

        public UserRegistration(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
