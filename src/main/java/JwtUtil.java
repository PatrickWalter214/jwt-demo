import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class JwtUtil {

    private JwtUtil() {

    }

    public static String decodeJwt(String jwt) {
        String[] jwtParts = splitJwt(jwt);
        String jwtHeaderJson = new String(Base64.getUrlDecoder().decode(jwtParts[0]), StandardCharsets.UTF_8);
        String jwtPayloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]), StandardCharsets.UTF_8);
        String headerField = String.format("\"header\":%s", jwtHeaderJson);
        String payloadField = String.format("\"payload\":%s", jwtPayloadJson);
        return String.format("{%s,%s}", headerField, payloadField);
    }

    private static String[] splitJwt(String jwt) {
        return jwt.split("\\.");
    }
}
