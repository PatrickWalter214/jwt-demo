import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilTests {

    @Test
    void shouldConvertJsonToNode() {
        String jwt = ResourceLoader.loadJwt();
        String decodedJwt = JwtUtil.decodeJwt(jwt);
        JsonNode jsonNode = JsonUtil.convertJsonStringToNode(decodedJwt);
        assertThat(jsonNode.get("payload").get("name").textValue()).isEqualTo("John Doe");
    }
}
