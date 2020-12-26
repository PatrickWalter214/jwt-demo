import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ResourceLoader {

    private ResourceLoader() {

    }

    public static String loadJwt() {
        byte[] jwt = readResource("JWT.txt");
        return new String(jwt, StandardCharsets.UTF_8);
    }

    public static RSAPublicKey loadRsaPublicKey() {
        byte[] publicKeyPem = readResource("RSA_Public_Key.txt");
        byte[] publicKey = extractPublicKey(publicKeyPem);
        return generateRsaPublicKey(publicKey);
    }

    private static RSAPublicKey generateRsaPublicKey(byte[] publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Could not generate rsa public key", e);
        }
    }

    private static byte[] extractPublicKey(byte[] publicKeyPem) {
        String encodedPublicKey = new String(publicKeyPem, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        return Base64.getDecoder().decode(encodedPublicKey);
    }

    private static byte[] readResource(String resource) {
        URL resourceUrl = ResourceLoader.class.getClassLoader().getResource(resource);
        try {
            Path publicKeyPath = Paths.get(resourceUrl.toURI());
            return Files.readAllBytes(publicKeyPath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Could not read resource", e);
        }
    }
}
