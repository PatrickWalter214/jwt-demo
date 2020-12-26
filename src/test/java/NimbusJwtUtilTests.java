import com.nimbusds.jwt.JWT;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPublicKey;

import static org.assertj.core.api.Assertions.assertThat;

class NimbusJwtUtilTests {

    @Test
    void shouldDecodeJwt() {
        String jwt = ResourceLoader.loadJwt();
        String decodedJwt = NimbusJwtUtil.decodeJwt(jwt);
        assertThat(decodedJwt).isEqualTo(
                "{\"header\":{\"typ\":\"JWT\",\"alg\":\"RS256\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}}"
        );
    }

    @Test
    void shouldDecodeAndValidateJwt() {
        String jwt = ResourceLoader.loadJwt();
        RSAPublicKey publicKey = ResourceLoader.loadRsaPublicKey();
        String decodedJwt = NimbusJwtUtil.decodeAndValidateJwt(jwt, publicKey);
        assertThat(decodedJwt).isEqualTo(
                "{\"header\":{\"typ\":\"JWT\",\"alg\":\"RS256\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}," +
                "\"validSignature\":true}"
        );
    }

    @Test
    void shouldGetSingleClaim() throws Exception {
        String encodedJwt = ResourceLoader.loadJwt();
        JWT jwt = NimbusJwtUtil.parseJwt(encodedJwt);
        assertThat(jwt.getJWTClaimsSet().getStringClaim("name")).isEqualTo("John Doe");
    }
}
