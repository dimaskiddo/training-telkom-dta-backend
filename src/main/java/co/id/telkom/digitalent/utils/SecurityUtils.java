package co.id.telkom.digitalent.utils;

public class SecurityUtils {

    public static final String SECRET = "693ee065865a51c647d6f0948963c231";
    public static final long EXPIRATION_TIME = 900_000; // 15 Minutes
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_IN_URL = "/api/v1/employees/login";
    public static final String SIGN_UP_URL = "/api/v1/employees/register";

}
