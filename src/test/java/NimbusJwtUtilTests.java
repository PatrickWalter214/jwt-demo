import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NimbusJwtUtilTests {

    @Test
    void shouldDecodeJwt() {
        String jwt = ResourceLoader.loadJwt();
        String decodedJwt = NimbusJwtUtil.decodeJwt(jwt);
        assertThat(decodedJwt).isEqualTo(
                "{\"header\":{\"typ\":\"JWT\",\"alg\":\"RS256\"}," +
                "\"payload\":{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true,\"iat\":1516239022}}"
        );
    }
}
