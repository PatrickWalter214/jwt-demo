import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPublicKey;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTests {

    @Test
    void shouldDecodeJwt() {
        String jwt = ResourceLoader.loadJwt();
        String decodedJwt = JwtUtil.decodeJwt(jwt);
        assertThat(decodedJwt).isEqualTo(
                "{\"header\":{\"alg\":\"RS256\",\"typ\":\"JWT\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}}"
        );
    }

    @Test
    void shouldDecodeAndValidateJwt() {
        String jwt = ResourceLoader.loadJwt();
        RSAPublicKey publicKey = ResourceLoader.loadRsaPublicKey();
        String decodedJwt = JwtUtil.decodeAndValidateJwt(jwt, publicKey);
        assertThat(decodedJwt).isEqualTo(
                "{\"header\":{\"alg\":\"RS256\",\"typ\":\"JWT\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}," +
                "\"validSignature\":true}"
        );
    }
}
