package de.voelkldennis.backend.jwt.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer "; // Whoever have this token he can give it to someone else
    public static final String JWT_TOKEN_HEADER = "JwtToken"; // Default custom HTTP header
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified"; // Message to verify token
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "Hobby aquaponic";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS"; // OPTIONS is the request that the server sends to the client to check if the client is allowed to send the request
    public static final String[] PUBLIC_URLS = {"/user/login", "/user/register", "/user/image/**", "/api/projects/freeGallery", "/api/projects/freeProjectCard"};
    //public static final String[] LOGIN_URLS = {"/api/projects/**"};
    //public static final String[] PUBLIC_URLS = {"**"};

}
