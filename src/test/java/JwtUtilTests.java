import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPublicKey;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTests {

    @Test
    void shouldDecodeJwt() {
        String encodedJwt = ResourceLoader.loadJwt();
        String jwt = JwtUtil.decodeJwt(encodedJwt);
        assertThat(jwt).isEqualTo(
                "{\"header\":{\"alg\":\"RS256\",\"typ\":\"JWT\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}}"
        );
    }

    @Test
    void shouldDecodeAndValidateJwt() {
        String encodedJwt = ResourceLoader.loadJwt();
        RSAPublicKey publicKey = ResourceLoader.loadRsaPublicKey();
        String jwt = JwtUtil.decodeAndValidateJwt(encodedJwt, publicKey);
        assertThat(jwt).isEqualTo(
                "{\"header\":{\"alg\":\"RS256\",\"typ\":\"JWT\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}," +
                "\"validSignature\":true}"
        );
    }
}
