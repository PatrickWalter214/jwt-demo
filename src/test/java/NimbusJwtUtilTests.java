import com.nimbusds.jwt.JWT;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPublicKey;

import static org.assertj.core.api.Assertions.assertThat;

class NimbusJwtUtilTests {

    @Test
    void shouldDecodeJwt() {
        String encodedJwt = ResourceLoader.loadJwt();
        String jwt = NimbusJwtUtil.decodeJwt(encodedJwt);
        assertThat(jwt).isEqualTo(
                "{\"header\":{\"typ\":\"JWT\",\"alg\":\"RS256\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}}"
        );
    }

    @Test
    void shouldDecodeAndValidateJwt() {
        String encodedJwt = ResourceLoader.loadJwt();
        RSAPublicKey publicKey = ResourceLoader.loadRsaPublicKey();
        String jwt = NimbusJwtUtil.decodeAndValidateJwt(encodedJwt, publicKey);
        assertThat(jwt).isEqualTo(
                "{\"header\":{\"typ\":\"JWT\",\"alg\":\"RS256\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}," +
                "\"validSignature\":true}"
        );
    }

    @Test
    void shouldGetNameClaim() throws Exception {
        String encodedJwt = ResourceLoader.loadJwt();
        JWT jwt = NimbusJwtUtil.parseJwt(encodedJwt);
        assertThat(jwt.getJWTClaimsSet().getStringClaim("name")).isEqualTo("John Doe");
    }
}
