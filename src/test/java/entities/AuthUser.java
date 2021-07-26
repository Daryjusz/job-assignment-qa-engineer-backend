package entities;

public class AuthUser {
    AuthUserBody user;

    public AuthUser(AuthUserBody authUserBody) {
        this.user = authUserBody;
    }

    public AuthUserBody getUser() {
        return user;
    }

    public static class AuthUserBody {

        String email;
        String password;

        public AuthUserBody(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
