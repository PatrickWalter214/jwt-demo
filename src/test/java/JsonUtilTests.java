import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilTests {

    @Test
    void shouldGetNameFieldFromJson() {
        String encodedJwt = ResourceLoader.loadJwt();
        String jwt = JwtUtil.decodeJwt(encodedJwt);
        JsonNode jsonNode = JsonUtil.convertJsonStringToNode(jwt);
        assertThat(jsonNode.get("payload").get("name").textValue()).isEqualTo("John Doe");
    }

    @Test
    void shouldConvertJsonToSampleJwt() {
        String encodedJwt = ResourceLoader.loadJwt();
        String jwt = JwtUtil.decodeJwt(encodedJwt);
        SampleJwt sampleJwt = JsonUtil.mapJsonStringToObject(jwt, SampleJwt.class);
        assertThat(sampleJwt).isEqualTo(getTestSampleJwt());
    }

    private SampleJwt getTestSampleJwt() {
        SampleJwt.SampleJwtHeader header = new SampleJwt.SampleJwtHeader();
        header.setAlg("RS256");
        header.setTyp("JWT");

        SampleJwt.SampleJwtPayload payload = new SampleJwt.SampleJwtPayload();
        payload.setSub("1234567890");
        payload.setName("John Doe");
        payload.setAdmin(true);
        payload.setIat(1516239022);

        SampleJwt sampleJwt = new SampleJwt();
        sampleJwt.setHeader(header);
        sampleJwt.setPayload(payload);
        return sampleJwt;
    }
}
