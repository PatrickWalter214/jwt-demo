import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class NimbusJwtUtil {

    private NimbusJwtUtil() {

    }

    public static JWT parseJwt(String jwtString) {
        try {
            return SignedJWT.parse(jwtString);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse JWT", e);
        }
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

    public static String decodeAndValidateJwt(String jwtString, RSAPublicKey publicKey) {
        try {
            SignedJWT jwt = SignedJWT.parse(jwtString);
            boolean validSignature = verifySignature(jwt, publicKey);
            String headerField = String.format("\"header\":%s", jwt.getHeader().toString());
            String payloadField = String.format("\"payload\":%s", jwt.getJWTClaimsSet().toString());
            String validSignatureField = String.format("\"validSignature\":%s", validSignature);
            return String.format("{%s,%s,%s}", headerField, payloadField, validSignatureField);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse JWT", e);
        }
    }

    private static boolean verifySignature(SignedJWT jwt, RSAPublicKey publicKey) {
        RSASSAVerifier rsassaVerifier = new RSASSAVerifier(publicKey);
        try {
            return jwt.verify(rsassaVerifier);
        } catch (JOSEException e) {
            throw new RuntimeException("Could not verify signature", e);
        }
    }
}
