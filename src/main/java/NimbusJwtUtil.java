import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public class NimbusJwtUtil {

    private NimbusJwtUtil() {

    }

    public static String decodeJwt(String jwtString) {
        try {
            JWT jwt = SignedJWT.parse(jwtString);
            String headerField = String.format("\"header\":%s", jwt.getHeader().toString());
            String payloadField = String.format("\"payload\":%s", jwt.getJWTClaimsSet().toString());
            return String.format("{%s,%s}", headerField, payloadField);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse JWT", e);
        }
    }
}
