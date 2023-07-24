import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncodeTest {

    @Test
    public void encodeString() throws UnsupportedEncodingException {
        String  str = "";
        String encodedValue = URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    }
}
