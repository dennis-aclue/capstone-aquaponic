package de.voelkldennis.backend.jwt.constant;

public record UserImplConstant() {
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String NO_USER_FOUND_BY_USERNAME = "No user found by username: %1$s";
    public static final String NEW_USER_PASSWORD = "New user password: %1$s";
    public static final String LOGIN_SUCCESSFUL = "User %1$s logged in successfully";
    public static final String USER_READ = "user:read";
    public static final String USER_UPDATE = "user:update";
    public static final String USER_CREATE = "user:create";
    public static final String USER_DELETE = "user:delete";

}
