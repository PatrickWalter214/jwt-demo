import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
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

    public static String decodeAndValidateJwt(String jwt, RSAPublicKey publicKey) {
        String[] jwtParts = splitJwt(jwt);
        String jwtHeaderJson = new String(Base64.getUrlDecoder().decode(jwtParts[0]), StandardCharsets.UTF_8);
        String jwtPayloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]), StandardCharsets.UTF_8);
        boolean validSignature = verifySignature(jwt, publicKey);
        String headerField = String.format("\"header\":%s", jwtHeaderJson);
        String payloadField = String.format("\"payload\":%s", jwtPayloadJson);
        String validSignatureField = String.format("\"validSignature\":%s", validSignature);
        return String.format("{%s,%s,%s}", headerField, payloadField, validSignatureField);
    }

    private static boolean verifySignature(String jwt, RSAPublicKey publicKey) {
        String[] jwtParts = splitJwt(jwt);
        String assessablePart = String.format("%s.%s", jwtParts[0], jwtParts[1]);
        byte[] signature = Base64.getUrlDecoder().decode(jwtParts[2]);
        try {
            Signature signEngine = Signature.getInstance("SHA256withRSA");
            signEngine.initVerify(publicKey);
            signEngine.update(assessablePart.getBytes(StandardCharsets.UTF_8));
            return signEngine.verify(signature);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException("Could not verify signature", e);
        }
    }

    private static String[] splitJwt(String jwt) {
        return jwt.split("\\.");
    }
}
